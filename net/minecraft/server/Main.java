package net.minecraft.server;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.datafixers.DataFixer;
import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Lifecycle;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.net.Proxy;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BooleanSupplier;
import java.util.function.Function;
import joptsimple.OptionSet;
import net.minecraft.CrashReport;
import net.minecraft.SharedConstants;
import net.minecraft.SystemUtils;
import net.minecraft.commands.CommandDispatcher;
import net.minecraft.core.IRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.DynamicOpsNBT;
import net.minecraft.nbt.NBTBase;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.obfuscate.DontObfuscate;
import net.minecraft.resources.RegistryOps;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.dedicated.DedicatedServerProperties;
import net.minecraft.server.dedicated.DedicatedServerSettings;
import net.minecraft.server.level.progress.WorldLoadListenerLogger;
import net.minecraft.server.packs.EnumResourcePackType;
import net.minecraft.server.packs.repository.ResourcePackRepository;
import net.minecraft.server.packs.repository.ResourcePackSourceVanilla;
import net.minecraft.util.MathHelper;
import net.minecraft.util.datafix.DataConverterRegistry;
import net.minecraft.util.profiling.jfr.Environment;
import net.minecraft.util.profiling.jfr.JvmProfiler;
import net.minecraft.util.worldupdate.WorldUpgrader;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.WorldDataConfiguration;
import net.minecraft.world.level.WorldSettings;
import net.minecraft.world.level.dimension.WorldDimension;
import net.minecraft.world.level.levelgen.WorldDimensions;
import net.minecraft.world.level.levelgen.WorldOptions;
import net.minecraft.world.level.levelgen.presets.WorldPresets;
import net.minecraft.world.level.storage.Convertable;
import net.minecraft.world.level.storage.SaveData;
import net.minecraft.world.level.storage.SavedFile;
import net.minecraft.world.level.storage.WorldDataServer;
import net.minecraft.world.level.storage.WorldInfo;
import org.bukkit.configuration.file.YamlConfiguration;
import org.slf4j.Logger;

public class Main {
   private static final Logger a = LogUtils.getLogger();

   @DontObfuscate
   public static void main(OptionSet optionset) {
      SharedConstants.a();

      try {
         Path path = (Path)optionset.valueOf("pidFile");
         if (path != null) {
            a(path);
         }

         CrashReport.h();
         if (optionset.has("jfrProfile")) {
            JvmProfiler.e.a(Environment.b);
         }

         DispenserRegistry.a();
         DispenserRegistry.c();
         SystemUtils.l();
         Path path1 = Paths.get("server.properties");
         DedicatedServerSettings dedicatedserversettings = new DedicatedServerSettings(optionset);
         dedicatedserversettings.b();
         Path path2 = Paths.get("eula.txt");
         EULA eula = new EULA(path2);
         if (optionset.has("initSettings")) {
            File configFile = (File)optionset.valueOf("bukkit-settings");
            YamlConfiguration configuration = YamlConfiguration.loadConfiguration(configFile);
            configuration.options().copyDefaults(true);
            configuration.setDefaults(
               YamlConfiguration.loadConfiguration(
                  new InputStreamReader(Main.class.getClassLoader().getResourceAsStream("configurations/bukkit.yml"), Charsets.UTF_8)
               )
            );
            configuration.save(configFile);
            File commandFile = (File)optionset.valueOf("commands-settings");
            YamlConfiguration commandsConfiguration = YamlConfiguration.loadConfiguration(commandFile);
            commandsConfiguration.options().copyDefaults(true);
            commandsConfiguration.setDefaults(
               YamlConfiguration.loadConfiguration(
                  new InputStreamReader(Main.class.getClassLoader().getResourceAsStream("configurations/commands.yml"), Charsets.UTF_8)
               )
            );
            commandsConfiguration.save(commandFile);
            a.info("Initialized '{}' and '{}'", path1.toAbsolutePath(), path2.toAbsolutePath());
            return;
         }

         boolean eulaAgreed = Boolean.getBoolean("com.mojang.eula.agree");
         if (eulaAgreed) {
            System.err.println("You have used the Spigot command line EULA agreement flag.");
            System.err
               .println("By using this setting you are indicating your agreement to Mojang's EULA (https://account.mojang.com/documents/minecraft_eula).");
            System.err.println("If you do not agree to the above EULA please stop your server and remove this flag immediately.");
         }

         if (!eula.a() && !eulaAgreed) {
            a.info("You need to agree to the EULA in order to run the server. Go to eula.txt for more info.");
            return;
         }

         File file = (File)optionset.valueOf("universe");
         Services services = Services.a(new YggdrasilAuthenticationService(Proxy.NO_PROXY), file);
         String s = (String)Optional.ofNullable((String)optionset.valueOf("world")).orElse(dedicatedserversettings.a().m);
         Convertable convertable = Convertable.a(file.toPath());
         Convertable.ConversionSession convertable_conversionsession = convertable.createAccess(s, WorldDimension.b);
         WorldInfo worldinfo = convertable_conversionsession.c();
         if (worldinfo != null) {
            if (worldinfo.d()) {
               a.info("This world must be opened in an older version (like 1.6.4) to be safely converted");
               return;
            }

            if (!worldinfo.r()) {
               a.info("This world was created by an incompatible version.");
               return;
            }
         }

         boolean flag = optionset.has("safeMode");
         if (flag) {
            a.warn("Safe mode active, only vanilla datapack will be loaded");
         }

         ResourcePackRepository resourcepackrepository = ResourcePackSourceVanilla.a(convertable_conversionsession.a(SavedFile.j));
         File bukkitDataPackFolder = new File(convertable_conversionsession.a(SavedFile.j).toFile(), "bukkit");
         if (!bukkitDataPackFolder.exists()) {
            bukkitDataPackFolder.mkdirs();
         }

         File mcMeta = new File(bukkitDataPackFolder, "pack.mcmeta");

         try {
            Files.write(
               "{\n    \"pack\": {\n        \"description\": \"Data pack for resources provided by Bukkit plugins\",\n        \"pack_format\": "
                  + SharedConstants.b().a(EnumResourcePackType.b)
                  + "\n"
                  + "    }\n"
                  + "}\n",
               mcMeta,
               Charsets.UTF_8
            );
         } catch (IOException var21) {
            throw new RuntimeException("Could not initialize Bukkit datapack", var21);
         }

         AtomicReference<WorldLoader.a> worldLoader = new AtomicReference<>();

         WorldStem worldstem;
         try {
            WorldLoader.c worldloader_c = a(dedicatedserversettings.a(), convertable_conversionsession, flag, resourcepackrepository);
            worldstem = SystemUtils.<WorldStem>c(
                  executor -> WorldLoader.a(
                        worldloader_c,
                        worldloader_a -> {
                           worldLoader.set(worldloader_a);
                           IRegistry<WorldDimension> iregistry = worldloader_a.d().d(Registries.aG);
                           DynamicOps<NBTBase> dynamicops = RegistryOps.a(DynamicOpsNBT.a, worldloader_a.c());
                           Pair<SaveData, WorldDimensions.b> pair = convertable_conversionsession.a(
                              dynamicops, worldloader_a.b(), iregistry, worldloader_a.c().d()
                           );
                           if (pair != null) {
                              return new WorldLoader.b<>((SaveData)pair.getFirst(), ((WorldDimensions.b)pair.getSecond()).b());
                           } else {
                              WorldSettings worldsettings;
                              WorldOptions worldoptions;
                              WorldDimensions worlddimensions;
                              if (optionset.has("demo")) {
                                 worldsettings = MinecraftServer.f;
                                 worldoptions = WorldOptions.b;
                                 worlddimensions = WorldPresets.a(worldloader_a.c());
                              } else {
                                 DedicatedServerProperties dedicatedserverproperties = dedicatedserversettings.a();
                                 worldsettings = new WorldSettings(
                                    dedicatedserverproperties.m,
                                    dedicatedserverproperties.l,
                                    dedicatedserverproperties.u,
                                    dedicatedserverproperties.k,
                                    false,
                                    new GameRules(),
                                    worldloader_a.b()
                                 );
                                 worldoptions = optionset.has("bonusChest") ? dedicatedserverproperties.X.a(true) : dedicatedserverproperties.X;
                                 worlddimensions = dedicatedserverproperties.a(worldloader_a.c());
                              }
         
                              WorldDimensions.b var13x = worlddimensions.a(iregistry);
                              Lifecycle lifecycle = var13x.a().add(worldloader_a.c().d());
                              return new WorldLoader.b<>(new WorldDataServer(worldsettings, worldoptions, var13x.d(), lifecycle), var13x.b());
                           }
                        },
                        WorldStem::new,
                        SystemUtils.f(),
                        executor
                     )
               )
               .get();
         } catch (Exception var20) {
            a.warn("Failed to load datapacks, can't proceed with server load. You can either fix your datapacks or reset to vanilla with --safeMode", var20);
            return;
         }

         DedicatedServer var27 = MinecraftServer.a(
            (Function<Thread, DedicatedServer>)(thread -> {
               DedicatedServer dedicatedserver1 = new DedicatedServer(
                  optionset,
                  worldLoader.get(),
                  thread,
                  convertable_conversionsession,
                  resourcepackrepository,
                  worldstem,
                  dedicatedserversettings,
                  DataConverterRegistry.a(),
                  services,
                  WorldLoadListenerLogger::new
               );
               boolean flag1 = !optionset.has("nogui") && !optionset.nonOptionArguments().contains("nogui");
               if (flag1 && !GraphicsEnvironment.isHeadless()) {
                  dedicatedserver1.bj();
               }
   
               if (optionset.has("port")) {
                  int port = optionset.valueOf("port");
                  if (port > 0) {
                     dedicatedserver1.a(port);
                  }
               }
   
               return dedicatedserver1;
            })
         );
      } catch (Exception var22) {
         a.error(LogUtils.FATAL_MARKER, "Failed to start the minecraft server", var22);
      }
   }

   private static void a(Path path) {
      try {
         long i = ProcessHandle.current().pid();
         java.nio.file.Files.writeString(path, Long.toString(i));
      } catch (IOException var3) {
         throw new UncheckedIOException(var3);
      }
   }

   private static WorldLoader.c a(
      DedicatedServerProperties dedicatedserverproperties,
      Convertable.ConversionSession convertable_conversionsession,
      boolean flag,
      ResourcePackRepository resourcepackrepository
   ) {
      WorldDataConfiguration worlddataconfiguration = convertable_conversionsession.d();
      boolean flag1;
      WorldDataConfiguration worlddataconfiguration1;
      if (worlddataconfiguration != null) {
         flag1 = false;
         worlddataconfiguration1 = worlddataconfiguration;
      } else {
         flag1 = true;
         worlddataconfiguration1 = new WorldDataConfiguration(dedicatedserverproperties.T, FeatureFlags.g);
      }

      WorldLoader.d worldloader_d = new WorldLoader.d(resourcepackrepository, worlddataconfiguration1, flag, flag1);
      return new WorldLoader.c(worldloader_d, CommandDispatcher.ServerType.b, dedicatedserverproperties.B);
   }

   public static void a(
      Convertable.ConversionSession convertable_conversionsession,
      DataFixer datafixer,
      boolean flag,
      BooleanSupplier booleansupplier,
      IRegistry<WorldDimension> iregistry
   ) {
      a.info("Forcing world upgrade! {}", convertable_conversionsession.a());
      WorldUpgrader worldupgrader = new WorldUpgrader(convertable_conversionsession, datafixer, iregistry, flag);
      IChatBaseComponent ichatbasecomponent = null;

      while(!worldupgrader.b()) {
         IChatBaseComponent ichatbasecomponent1 = worldupgrader.h();
         if (ichatbasecomponent != ichatbasecomponent1) {
            ichatbasecomponent = ichatbasecomponent1;
            a.info(worldupgrader.h().getString());
         }

         int i = worldupgrader.e();
         if (i > 0) {
            int j = worldupgrader.f() + worldupgrader.g();
            a.info("{}% completed ({} / {} chunks)...", new Object[]{MathHelper.d((float)j / (float)i * 100.0F), j, i});
         }

         if (!booleansupplier.getAsBoolean()) {
            worldupgrader.a();
         } else {
            try {
               Thread.sleep(1000L);
            } catch (InterruptedException var10) {
            }
         }
      }
   }
}
