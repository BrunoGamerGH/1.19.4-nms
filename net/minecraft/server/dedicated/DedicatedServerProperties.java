package net.minecraft.server.dedicated;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.JsonOps;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import joptsimple.OptionSet;
import net.minecraft.core.Holder;
import net.minecraft.core.IRegistry;
import net.minecraft.core.IRegistryCustom;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatDeserializer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.DataPackConfiguration;
import net.minecraft.world.level.EnumGamemode;
import net.minecraft.world.level.WorldDataConfiguration;
import net.minecraft.world.level.levelgen.ChunkProviderFlat;
import net.minecraft.world.level.levelgen.WorldDimensions;
import net.minecraft.world.level.levelgen.WorldOptions;
import net.minecraft.world.level.levelgen.flat.GeneratorSettingsFlat;
import net.minecraft.world.level.levelgen.presets.WorldPreset;
import net.minecraft.world.level.levelgen.presets.WorldPresets;
import org.slf4j.Logger;

public class DedicatedServerProperties extends PropertyManager<DedicatedServerProperties> {
   static final Logger Z = LogUtils.getLogger();
   private static final Pattern aa = Pattern.compile("^[a-fA-F0-9]{40}$");
   private static final Splitter ab = Splitter.on(',').trimResults();
   public final boolean debug = this.a("debug", false);
   public final boolean a = this.a("online-mode", true);
   public final boolean b = this.a("prevent-proxy-connections", false);
   public final String c = this.a("server-ip", "");
   public final boolean d = this.a("spawn-animals", true);
   public final boolean e = this.a("spawn-npcs", true);
   public final boolean f = this.a("pvp", true);
   public final boolean g = this.a("allow-flight", false);
   public final String h = this.a("motd", "A Minecraft Server");
   public final boolean i = this.a("force-gamemode", false);
   public final boolean j = this.a("enforce-whitelist", false);
   public final EnumDifficulty k = this.a("difficulty", a(EnumDifficulty::a, EnumDifficulty::a), EnumDifficulty::e, EnumDifficulty.b);
   public final EnumGamemode l = this.a("gamemode", a(EnumGamemode::a, EnumGamemode::a), EnumGamemode::b, EnumGamemode.a);
   public final String m = this.a("level-name", "world");
   public final int n = this.a("server-port", 25565);
   @Nullable
   public final Boolean o = this.b("announce-player-achievements");
   public final boolean p = this.a("enable-query", false);
   public final int q = this.a("query.port", 25565);
   public final boolean r = this.a("enable-rcon", false);
   public final int s = this.a("rcon.port", 25575);
   public final String t = this.a("rcon.password", "");
   public final boolean u = this.a("hardcore", false);
   public final boolean v = this.a("allow-nether", true);
   public final boolean w = this.a("spawn-monsters", true);
   public final boolean x = this.a("use-native-transport", true);
   public final boolean y = this.a("enable-command-block", false);
   public final int z = this.a("spawn-protection", 16);
   public final int A = this.a("op-permission-level", 4);
   public final int B = this.a("function-permission-level", 2);
   public final long C = this.a("max-tick-time", TimeUnit.MINUTES.toMillis(1L));
   public final int D = this.a("max-chained-neighbor-updates", 1000000);
   public final int E = this.a("rate-limit", 0);
   public final int F = this.a("view-distance", 10);
   public final int G = this.a("simulation-distance", 10);
   public final int H = this.a("max-players", 20);
   public final int I = this.a("network-compression-threshold", 256);
   public final boolean J = this.a("broadcast-rcon-to-ops", true);
   public final boolean K = this.a("broadcast-console-to-ops", true);
   public final int L = this.a("max-world-size", integer -> MathHelper.a(integer, 1, 29999984), 29999984);
   public final boolean M = this.a("sync-chunk-writes", true);
   public final boolean N = this.a("enable-jmx-monitoring", false);
   public final boolean O = this.a("enable-status", true);
   public final boolean P = this.a("hide-online-players", false);
   public final int Q = this.a("entity-broadcast-range-percentage", integer -> MathHelper.a(integer, 10, 1000), 100);
   public final String R = this.a("text-filtering-config", "");
   public final Optional<MinecraftServer.ServerResourcePackInfo> S;
   public final DataPackConfiguration T;
   public final PropertyManager<DedicatedServerProperties>.EditableProperty<Integer> U = this.b("player-idle-timeout", 0);
   public final PropertyManager<DedicatedServerProperties>.EditableProperty<Boolean> V = this.b("white-list", false);
   public final boolean W = this.a("enforce-secure-profile", true);
   private final DedicatedServerProperties.WorldDimensionData ac;
   public final WorldOptions X;

   public DedicatedServerProperties(Properties properties, OptionSet optionset) {
      super(properties, optionset);
      String s = this.a("level-seed", "");
      boolean flag = this.a("generate-structures", true);
      long i = WorldOptions.a(s).orElse(WorldOptions.f());
      this.X = new WorldOptions(i, flag, false);
      this.ac = new DedicatedServerProperties.WorldDimensionData(
         this.a("generator-settings", s1 -> ChatDeserializer.a(!s1.isEmpty() ? s1 : "{}"), new JsonObject()),
         this.a("level-type", s1 -> s1.toLowerCase(Locale.ROOT), WorldPresets.a.a().toString())
      );
      this.S = a(
         this.a("resource-pack", ""),
         this.a("resource-pack-sha1", ""),
         this.a("resource-pack-hash"),
         this.a("require-resource-pack", false),
         this.a("resource-pack-prompt", "")
      );
      this.T = b(
         this.a("initial-enabled-packs", String.join(",", WorldDataConfiguration.c.a().a())),
         this.a("initial-disabled-packs", String.join(",", WorldDataConfiguration.c.a().b()))
      );
   }

   public static DedicatedServerProperties fromFile(Path path, OptionSet optionset) {
      return new DedicatedServerProperties(b(path), optionset);
   }

   protected DedicatedServerProperties reload(IRegistryCustom iregistrycustom, Properties properties, OptionSet optionset) {
      return new DedicatedServerProperties(properties, optionset);
   }

   @Nullable
   private static IChatBaseComponent c(String s) {
      if (!Strings.isNullOrEmpty(s)) {
         try {
            return IChatBaseComponent.ChatSerializer.a(s);
         } catch (Exception var2) {
            Z.warn("Failed to parse resource pack prompt '{}'", s, var2);
         }
      }

      return null;
   }

   private static Optional<MinecraftServer.ServerResourcePackInfo> a(String s, String s1, @Nullable String s2, boolean flag, String s3) {
      if (s.isEmpty()) {
         return Optional.empty();
      } else {
         String s4;
         if (!s1.isEmpty()) {
            s4 = s1;
            if (!Strings.isNullOrEmpty(s2)) {
               Z.warn("resource-pack-hash is deprecated and found along side resource-pack-sha1. resource-pack-hash will be ignored.");
            }
         } else if (!Strings.isNullOrEmpty(s2)) {
            Z.warn("resource-pack-hash is deprecated. Please use resource-pack-sha1 instead.");
            s4 = s2;
         } else {
            s4 = "";
         }

         if (s4.isEmpty()) {
            Z.warn("You specified a resource pack without providing a sha1 hash. Pack will be updated on the client only if you change the name of the pack.");
         } else if (!aa.matcher(s4).matches()) {
            Z.warn("Invalid sha1 for resource-pack-sha1");
         }

         IChatBaseComponent ichatbasecomponent = c(s3);
         return Optional.of(new MinecraftServer.ServerResourcePackInfo(s, s4, flag, ichatbasecomponent));
      }
   }

   private static DataPackConfiguration b(String s, String s1) {
      List<String> list = ab.splitToList(s);
      List<String> list1 = ab.splitToList(s1);
      return new DataPackConfiguration(list, list1);
   }

   private static FeatureFlagSet d(String s) {
      return FeatureFlags.d.a(ab.splitToStream(s).mapMulti((s1, consumer) -> {
         MinecraftKey minecraftkey = MinecraftKey.a(s1);
         if (minecraftkey == null) {
            Z.warn("Invalid resource location {}, ignoring", s1);
         } else {
            consumer.accept(minecraftkey);
         }
      }).collect(Collectors.toList()));
   }

   public WorldDimensions a(IRegistryCustom iregistrycustom) {
      return this.ac.a(iregistrycustom);
   }

   public static record WorldDimensionData(JsonObject generatorSettings, String levelType) {
      private final JsonObject a;
      private final String b;
      private static final Map<String, ResourceKey<WorldPreset>> c = Map.of("default", WorldPresets.a, "largebiomes", WorldPresets.c);

      public WorldDimensionData(JsonObject generatorSettings, String levelType) {
         this.a = generatorSettings;
         this.b = levelType;
      }

      public WorldDimensions a(IRegistryCustom iregistrycustom) {
         IRegistry<WorldPreset> iregistry = iregistrycustom.d(Registries.aD);
         Holder.c<WorldPreset> holder_c = iregistry.b(WorldPresets.a)
            .or(() -> iregistry.h().findAny())
            .orElseThrow(() -> new IllegalStateException("Invalid datapack contents: can't find default preset"));
         Optional<ResourceKey<WorldPreset>> optional = Optional.ofNullable(MinecraftKey.a(this.b))
            .map(minecraftkey -> ResourceKey.a(Registries.aD, minecraftkey))
            .or(() -> Optional.ofNullable(c.get(this.b)));
         Holder<WorldPreset> holder = optional.<Holder.c<WorldPreset>>flatMap(iregistry::b).orElseGet(() -> {
            DedicatedServerProperties.Z.warn("Failed to parse level-type {}, defaulting to {}", this.b, holder_c.g().a());
            return holder_c;
         });
         WorldDimensions worlddimensions = holder.a().a();
         if (holder.a(WorldPresets.b)) {
            RegistryOps<JsonElement> registryops = RegistryOps.a(JsonOps.INSTANCE, iregistrycustom);
            DataResult<GeneratorSettingsFlat> dataresult = GeneratorSettingsFlat.a.parse(new Dynamic(registryops, this.a()));
            Logger logger = DedicatedServerProperties.Z;
            Optional<GeneratorSettingsFlat> optional1 = dataresult.resultOrPartial(logger::error);
            if (optional1.isPresent()) {
               return worlddimensions.a(iregistrycustom, new ChunkProviderFlat(optional1.get()));
            }
         }

         return worlddimensions;
      }
   }
}
