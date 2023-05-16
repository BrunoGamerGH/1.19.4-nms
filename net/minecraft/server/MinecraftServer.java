package net.minecraft.server;

import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.io.Files;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.datafixers.DataFixer;
import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Lifecycle;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.net.Proxy;
import java.nio.file.Path;
import java.security.KeyPair;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.LockSupport;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import javax.imageio.ImageIO;
import jline.console.ConsoleReader;
import joptsimple.OptionSet;
import net.minecraft.CrashReport;
import net.minecraft.ReportedException;
import net.minecraft.SharedConstants;
import net.minecraft.SystemReport;
import net.minecraft.SystemUtils;
import net.minecraft.commands.CommandDispatcher;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.commands.ICommandListener;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.IRegistry;
import net.minecraft.core.IRegistryCustom;
import net.minecraft.core.LayeredRegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.features.MiscOverworldFeatures;
import net.minecraft.gametest.framework.GameTestHarnessTicker;
import net.minecraft.nbt.DynamicOpsNBT;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.chat.ChatDecorator;
import net.minecraft.network.chat.ChatMessageType;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.protocol.game.PacketPlayOutServerDifficulty;
import net.minecraft.network.protocol.game.PacketPlayOutUpdateTime;
import net.minecraft.network.protocol.status.ServerPing;
import net.minecraft.obfuscate.DontObfuscate;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.bossevents.BossBattleCustomData;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.dedicated.DedicatedServerProperties;
import net.minecraft.server.level.ChunkProviderServer;
import net.minecraft.server.level.DemoPlayerInteractManager;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.PlayerInteractManager;
import net.minecraft.server.level.TicketType;
import net.minecraft.server.level.WorldProviderNormal;
import net.minecraft.server.level.WorldServer;
import net.minecraft.server.level.progress.WorldLoadListener;
import net.minecraft.server.level.progress.WorldLoadListenerFactory;
import net.minecraft.server.network.ITextFilter;
import net.minecraft.server.network.ServerConnection;
import net.minecraft.server.packs.EnumResourcePackType;
import net.minecraft.server.packs.IResourcePack;
import net.minecraft.server.packs.repository.ResourcePackLoader;
import net.minecraft.server.packs.repository.ResourcePackRepository;
import net.minecraft.server.packs.resources.IReloadableResourceManager;
import net.minecraft.server.packs.resources.IResourceManager;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.players.OpListEntry;
import net.minecraft.server.players.PlayerList;
import net.minecraft.server.players.UserCache;
import net.minecraft.server.players.WhiteList;
import net.minecraft.util.CircularTimer;
import net.minecraft.util.CryptographyException;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MinecraftEncryption;
import net.minecraft.util.ModCheck;
import net.minecraft.util.NativeModuleLister;
import net.minecraft.util.RandomSource;
import net.minecraft.util.SignatureValidator;
import net.minecraft.util.Unit;
import net.minecraft.util.datafix.DataConverterRegistry;
import net.minecraft.util.profiling.GameProfilerFiller;
import net.minecraft.util.profiling.GameProfilerTick;
import net.minecraft.util.profiling.MethodProfilerResults;
import net.minecraft.util.profiling.MethodProfilerResultsEmpty;
import net.minecraft.util.profiling.MethodProfilerResultsField;
import net.minecraft.util.profiling.jfr.JvmProfiler;
import net.minecraft.util.profiling.jfr.callback.ProfiledDuration;
import net.minecraft.util.profiling.metrics.profiling.ActiveMetricsRecorder;
import net.minecraft.util.profiling.metrics.profiling.InactiveMetricsRecorder;
import net.minecraft.util.profiling.metrics.profiling.MetricsRecorder;
import net.minecraft.util.profiling.metrics.profiling.ServerMetricsSamplersProvider;
import net.minecraft.util.profiling.metrics.storage.MetricsPersister;
import net.minecraft.util.thread.IAsyncTaskHandlerReentrant;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.entity.ai.village.VillageSiege;
import net.minecraft.world.entity.npc.MobSpawnerCat;
import net.minecraft.world.entity.npc.MobSpawnerTrader;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.crafting.CraftingManager;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.DataPackConfiguration;
import net.minecraft.world.level.EnumGamemode;
import net.minecraft.world.level.ForcedChunk;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.MobSpawner;
import net.minecraft.world.level.World;
import net.minecraft.world.level.WorldDataConfiguration;
import net.minecraft.world.level.WorldSettings;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.dimension.WorldDimension;
import net.minecraft.world.level.levelgen.HeightMap;
import net.minecraft.world.level.levelgen.MobSpawnerPatrol;
import net.minecraft.world.level.levelgen.MobSpawnerPhantom;
import net.minecraft.world.level.levelgen.WorldDimensions;
import net.minecraft.world.level.levelgen.WorldOptions;
import net.minecraft.world.level.levelgen.feature.WorldGenFeatureConfigured;
import net.minecraft.world.level.levelgen.presets.WorldPresets;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.level.storage.Convertable;
import net.minecraft.world.level.storage.IWorldDataServer;
import net.minecraft.world.level.storage.PersistentCommandStorage;
import net.minecraft.world.level.storage.SaveData;
import net.minecraft.world.level.storage.SavedFile;
import net.minecraft.world.level.storage.WorldData;
import net.minecraft.world.level.storage.WorldDataServer;
import net.minecraft.world.level.storage.WorldNBTStorage;
import net.minecraft.world.level.storage.WorldPersistentData;
import net.minecraft.world.level.storage.loot.ItemModifierManager;
import net.minecraft.world.level.storage.loot.LootPredicateManager;
import net.minecraft.world.level.storage.loot.LootTableRegistry;
import net.minecraft.world.phys.Vec2F;
import net.minecraft.world.phys.Vec3D;
import net.minecraft.world.scores.PersistentScoreboard;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World.Environment;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.RemoteConsoleCommandSender;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.craftbukkit.v1_19_R3.SpigotTimings;
import org.bukkit.craftbukkit.v1_19_R3.generator.CraftWorldInfo;
import org.bukkit.craftbukkit.v1_19_R3.scoreboard.CraftScoreboardManager;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftChatMessage;
import org.bukkit.craftbukkit.v1_19_R3.util.LazyPlayerSet;
import org.bukkit.craftbukkit.v1_19_R3.util.ServerShutdownThread;
import org.bukkit.event.player.AsyncPlayerChatPreviewEvent;
import org.bukkit.event.server.ServerLoadEvent;
import org.bukkit.event.server.ServerLoadEvent.LoadType;
import org.bukkit.event.world.WorldInitEvent;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.WorldInfo;
import org.bukkit.plugin.PluginLoadOrder;
import org.slf4j.Logger;
import org.spigotmc.CustomTimingsHandler;
import org.spigotmc.SpigotConfig;
import org.spigotmc.WatchdogThread;

public abstract class MinecraftServer extends IAsyncTaskHandlerReentrant<TickTask> implements ICommandListener, AutoCloseable {
   public static final Logger n = LogUtils.getLogger();
   public static final String b = "vanilla";
   private static final float o = 0.8F;
   private static final int p = 100;
   public static final int c = 50;
   private static final int q = 2000;
   private static final int r = 15000;
   private static final long s = 5000000000L;
   private static final int t = 12;
   public static final int d = 11;
   private static final int u = 441;
   private static final int v = 6000;
   private static final int w = 3;
   public static final int e = 29999984;
   public static final WorldSettings f = new WorldSettings(
      "Demo World", EnumGamemode.a, false, EnumDifficulty.c, false, new GameRules(), WorldDataConfiguration.c
   );
   private static final long x = 50L;
   public static final GameProfile g = new GameProfile(SystemUtils.c, "Anonymous Player");
   public Convertable.ConversionSession h;
   public final WorldNBTStorage i;
   private final List<Runnable> y = Lists.newArrayList();
   private MetricsRecorder z;
   private GameProfilerFiller A;
   private Consumer<MethodProfilerResults> B;
   private Consumer<Path> C;
   private boolean D;
   @Nullable
   private MinecraftServer.TimeProfiler E;
   private boolean F;
   private ServerConnection G;
   public final WorldLoadListenerFactory H;
   @Nullable
   private ServerPing I;
   @Nullable
   private ServerPing.a J;
   private final RandomSource K;
   public final DataFixer L;
   private String M;
   private int N;
   private final LayeredRegistryAccess<RegistryLayer> O;
   private Map<ResourceKey<World>, WorldServer> P;
   private PlayerList Q;
   private volatile boolean R;
   private boolean S;
   private int T;
   protected final Proxy j;
   private boolean U;
   private boolean V;
   private boolean W;
   private boolean X;
   @Nullable
   private String Y;
   private int Z;
   public final long[] k;
   @Nullable
   private KeyPair aa;
   @Nullable
   private GameProfile ab;
   private boolean ac;
   private volatile boolean ad;
   private long ae;
   protected final Services l;
   private long af;
   public final Thread ag;
   private long ah;
   private long ai;
   private boolean aj;
   private final ResourcePackRepository ak;
   private final ScoreboardServer al;
   @Nullable
   private PersistentCommandStorage am;
   private final BossBattleCustomData an;
   private final CustomFunctionData ao;
   private final CircularTimer ap;
   private boolean aq;
   private float ar;
   public final Executor as;
   @Nullable
   private String at;
   public MinecraftServer.ReloadableResources au;
   private final StructureTemplateManager av;
   protected SaveData m;
   private volatile boolean aw;
   public final WorldLoader.a worldLoader;
   public CraftServer server;
   public OptionSet options;
   public ConsoleCommandSender console;
   public RemoteConsoleCommandSender remoteConsole;
   public ConsoleReader reader;
   public static int currentTick = (int)(System.currentTimeMillis() / 50L);
   public Queue<Runnable> processQueue = new ConcurrentLinkedQueue<>();
   public int autosavePeriod;
   public CommandDispatcher vanillaCommandDispatcher;
   private boolean forceTicks;
   public static final int TPS = 20;
   public static final int TICK_TIME = 50000000;
   private static final int SAMPLE_INTERVAL = 100;
   public final double[] recentTps = new double[3];
   private boolean hasStopped = false;
   private final Object stopLock = new Object();
   public final ExecutorService chatExecutor = Executors.newCachedThreadPool(
      new ThreadFactoryBuilder().setDaemon(true).setNameFormat("Async Chat Thread - #%d").build()
   );

   public static <S extends MinecraftServer> S a(Function<Thread, S> function) {
      AtomicReference<S> atomicreference = new AtomicReference<>();
      Thread thread = new Thread(() -> atomicreference.get().w(), "Server thread");
      thread.setUncaughtExceptionHandler((thread1, throwable) -> n.error("Uncaught exception in server thread", throwable));
      if (Runtime.getRuntime().availableProcessors() > 4) {
         thread.setPriority(8);
      }

      S s0 = function.apply(thread);
      atomicreference.set(s0);
      thread.start();
      return s0;
   }

   public MinecraftServer(
      OptionSet options,
      WorldLoader.a worldLoader,
      Thread thread,
      Convertable.ConversionSession convertable_conversionsession,
      ResourcePackRepository resourcepackrepository,
      WorldStem worldstem,
      Proxy proxy,
      DataFixer datafixer,
      Services services,
      WorldLoadListenerFactory worldloadlistenerfactory
   ) {
      super("Server");
      this.z = InactiveMetricsRecorder.a;
      this.A = this.z.f();
      this.B = methodprofilerresults -> this.aR();
      this.C = path -> {
      };
      this.K = RandomSource.a();
      this.N = -1;
      this.P = Maps.newLinkedHashMap();
      this.R = true;
      this.k = new long[100];
      this.ah = SystemUtils.b();
      this.al = new ScoreboardServer(this);
      this.an = new BossBattleCustomData();
      this.ap = new CircularTimer();
      this.O = worldstem.c();
      this.m = worldstem.d();
      this.j = proxy;
      this.ak = resourcepackrepository;
      this.au = new MinecraftServer.ReloadableResources(worldstem.a(), worldstem.b());
      this.l = services;
      if (services.d() != null) {
         services.d().a(this);
      }

      this.H = worldloadlistenerfactory;
      this.h = convertable_conversionsession;
      this.i = convertable_conversionsession.b();
      this.L = datafixer;
      this.ao = new CustomFunctionData(this, this.au.b.a());
      HolderGetter<Block> holdergetter = this.O.a().<Block>d(Registries.e).p().a(this.m.L());
      this.av = new StructureTemplateManager(worldstem.a(), convertable_conversionsession, datafixer, holdergetter);
      this.ag = thread;
      this.as = SystemUtils.f();
      this.options = options;
      this.worldLoader = worldLoader;
      this.vanillaCommandDispatcher = worldstem.b().d;
      if (System.console() == null && System.getProperty("jline.terminal") == null) {
         System.setProperty("jline.terminal", "jline.UnsupportedTerminal");
         org.bukkit.craftbukkit.Main.useJline = false;
      }

      try {
         this.reader = new ConsoleReader(System.in, System.out);
         this.reader.setExpandEvents(false);
      } catch (Throwable var14) {
         try {
            System.setProperty("jline.terminal", "jline.UnsupportedTerminal");
            System.setProperty("user.language", "en");
            org.bukkit.craftbukkit.Main.useJline = false;
            this.reader = new ConsoleReader(System.in, System.out);
            this.reader.setExpandEvents(false);
         } catch (IOException var13) {
            n.warn(null, var13);
         }
      }

      Runtime.getRuntime().addShutdownHook(new ServerShutdownThread(this));
   }

   private void a(WorldPersistentData worldpersistentdata) {
      ScoreboardServer scoreboardserver = this.aF();
      Function<NBTTagCompound, PersistentScoreboard> function = scoreboardserver::a;
      ScoreboardServer scoreboardserver1 = this.aF();
      worldpersistentdata.a(function, scoreboardserver1::b, "scoreboard");
   }

   protected abstract boolean e() throws IOException;

   protected void loadLevel(String s) {
      if (!JvmProfiler.e.c()) {
      }

      boolean flag = false;
      ProfiledDuration profiledduration = JvmProfiler.e.e();
      this.loadWorld0(s);
      if (profiledduration != null) {
         profiledduration.finish();
      }

      if (flag) {
         try {
            JvmProfiler.e.b();
         } catch (Throwable var5) {
            n.warn("Failed to stop JFR profiling", var5);
         }
      }
   }

   private void loadWorld0(String s) {
      Convertable.ConversionSession worldSession = this.h;
      IRegistry<WorldDimension> dimensions = this.O.a().d(Registries.aG);

      for(WorldDimension worldDimension : dimensions) {
         ResourceKey<WorldDimension> dimensionKey = dimensions.c(worldDimension).get();
         int dimension = 0;
         if (dimensionKey == WorldDimension.c) {
            if (!this.B()) {
               continue;
            }

            dimension = -1;
         } else if (dimensionKey == WorldDimension.d) {
            if (!this.server.getAllowEnd()) {
               continue;
            }

            dimension = 1;
         } else if (dimensionKey != WorldDimension.b) {
            dimension = -999;
         }

         String worldType = dimension == -999
            ? dimensionKey.a().b() + "_" + dimensionKey.a().a()
            : Environment.getEnvironment(dimension).toString().toLowerCase();
         String name = dimensionKey == WorldDimension.b ? s : s + "_" + worldType;
         if (dimension != 0) {
            File newWorld = Convertable.getStorageFolder(new File(name).toPath(), dimensionKey).toFile();
            File oldWorld = Convertable.getStorageFolder(new File(s).toPath(), dimensionKey).toFile();
            File oldLevelDat = new File(new File(s), "level.dat");
            if (!newWorld.isDirectory() && oldWorld.isDirectory() && oldLevelDat.isFile()) {
               n.info("---- Migration of old " + worldType + " folder required ----");
               n.info(
                  "Unfortunately due to the way that Minecraft implemented multiworld support in 1.6, Bukkit requires that you move your "
                     + worldType
                     + " folder to a new location in order to operate correctly."
               );
               n.info("We will move this folder for you, but it will mean that you need to move it back should you wish to stop using Bukkit in the future.");
               n.info("Attempting to move " + oldWorld + " to " + newWorld + "...");
               if (newWorld.exists()) {
                  n.warn("A file or folder already exists at " + newWorld + "!");
                  n.info("---- Migration of old " + worldType + " folder failed ----");
               } else if (newWorld.getParentFile().mkdirs()) {
                  if (oldWorld.renameTo(newWorld)) {
                     n.info("Success! To restore " + worldType + " in the future, simply move " + newWorld + " to " + oldWorld);

                     try {
                        Files.copy(oldLevelDat, new File(new File(name), "level.dat"));
                        FileUtils.copyDirectory(new File(new File(s), "data"), new File(new File(name), "data"));
                     } catch (IOException var32) {
                        n.warn("Unable to migrate world data.");
                     }

                     n.info("---- Migration of old " + worldType + " folder complete ----");
                  } else {
                     n.warn("Could not move folder " + oldWorld + " to " + newWorld + "!");
                     n.info("---- Migration of old " + worldType + " folder failed ----");
                  }
               } else {
                  n.warn("Could not create path for " + newWorld + "!");
                  n.info("---- Migration of old " + worldType + " folder failed ----");
               }
            }

            try {
               worldSession = Convertable.a(this.server.getWorldContainer().toPath()).createAccess(name, dimensionKey);
            } catch (IOException var31) {
               throw new RuntimeException(var31);
            }
         }

         ChunkGenerator gen = this.server.getGenerator(name);
         BiomeProvider biomeProvider = this.server.getBiomeProvider(name);
         WorldLoader.a worldloader_a = this.worldLoader;
         IRegistry<WorldDimension> iregistry = worldloader_a.d().d(Registries.aG);
         DynamicOps<NBTBase> dynamicops = RegistryOps.a(DynamicOpsNBT.a, worldloader_a.c());
         Pair<SaveData, WorldDimensions.b> pair = worldSession.a(dynamicops, worldloader_a.b(), iregistry, worldloader_a.c().d());
         WorldDataServer worlddata;
         if (pair != null) {
            worlddata = (WorldDataServer)pair.getFirst();
         } else {
            WorldSettings worldsettings;
            WorldOptions worldoptions;
            WorldDimensions worlddimensions;
            if (this.R()) {
               worldsettings = f;
               worldoptions = WorldOptions.b;
               worlddimensions = WorldPresets.a(worldloader_a.c());
            } else {
               DedicatedServerProperties dedicatedserverproperties = ((DedicatedServer)this).a();
               worldsettings = new WorldSettings(
                  dedicatedserverproperties.m,
                  dedicatedserverproperties.l,
                  dedicatedserverproperties.u,
                  dedicatedserverproperties.k,
                  false,
                  new GameRules(),
                  worldloader_a.b()
               );
               worldoptions = this.options.has("bonusChest") ? dedicatedserverproperties.X.a(true) : dedicatedserverproperties.X;
               worlddimensions = dedicatedserverproperties.a(worldloader_a.c());
            }

            WorldDimensions.b worlddimensions_b = worlddimensions.a(iregistry);
            Lifecycle lifecycle = worlddimensions_b.a().add(worldloader_a.c().d());
            worlddata = new WorldDataServer(worldsettings, worldoptions, worlddimensions_b.d(), lifecycle);
         }

         worlddata.checkName(name);
         if (this.options.has("forceUpgrade")) {
            Main.a(worldSession, DataConverterRegistry.a(), this.options.has("eraseCache"), () -> true, iregistry);
         }

         boolean flag = worlddata.C();
         WorldOptions worldoptions = worlddata.A();
         long i = worldoptions.b();
         long j = BiomeManager.a(i);
         List<MobSpawner> list = ImmutableList.of(
            new MobSpawnerPhantom(), new MobSpawnerPatrol(), new MobSpawnerCat(), new VillageSiege(), new MobSpawnerTrader(worlddata)
         );
         WorldDimension worlddimension = dimensions.a(dimensionKey);
         WorldInfo worldInfo = new CraftWorldInfo(worlddata, worldSession, Environment.getEnvironment(dimension), worlddimension.a().a());
         if (biomeProvider == null && gen != null) {
            biomeProvider = gen.getDefaultBiomeProvider(worldInfo);
         }

         ResourceKey<World> worldKey = ResourceKey.a(Registries.aF, dimensionKey.a());
         WorldServer world;
         if (dimensionKey == WorldDimension.b) {
            this.m = worlddata;
            this.m.a(((DedicatedServer)this).a().l);
            WorldLoadListener worldloadlistener = this.H.create(11);
            world = new WorldServer(
               this,
               this.as,
               worldSession,
               worlddata,
               worldKey,
               worlddimension,
               worldloadlistener,
               flag,
               j,
               list,
               true,
               Environment.getEnvironment(dimension),
               gen,
               biomeProvider
            );
            WorldPersistentData worldpersistentdata = world.s();
            this.a(worldpersistentdata);
            this.server.scoreboardManager = new CraftScoreboardManager(this, world.f());
            this.am = new PersistentCommandStorage(worldpersistentdata);
         } else {
            WorldLoadListener worldloadlistener = this.H.create(11);
            world = new WorldServer(
               this,
               this.as,
               worldSession,
               worlddata,
               worldKey,
               worlddimension,
               worldloadlistener,
               flag,
               j,
               ImmutableList.of(),
               true,
               Environment.getEnvironment(dimension),
               gen,
               biomeProvider
            );
         }

         worlddata.a(this.getServerModName(), this.K().a());
         this.initWorld(world, worlddata, this.m, worldoptions);
         this.addLevel(world);
         this.ac().a(world);
         if (worlddata.G() != null) {
            this.aL().a(worlddata.G());
         }
      }

      this.r();

      for(WorldServer worldserver : this.F()) {
         this.prepareLevels(worldserver.k().a.E, worldserver);
         worldserver.L.a();
         this.server.getPluginManager().callEvent(new WorldLoadEvent(worldserver.getWorld()));
      }

      this.server.enablePlugins(PluginLoadOrder.POSTWORLD);
      this.server.getPluginManager().callEvent(new ServerLoadEvent(LoadType.STARTUP));
      this.G.acceptConnections();
   }

   protected void r() {
   }

   public void initWorld(WorldServer worldserver, IWorldDataServer iworlddataserver, SaveData saveData, WorldOptions worldoptions) {
      boolean flag = saveData.C();
      if (worldserver.generator != null) {
         worldserver.getWorld().getPopulators().addAll(worldserver.generator.getDefaultPopulators(worldserver.getWorld()));
      }

      WorldBorder worldborder = worldserver.p_();
      worldborder.a(iworlddataserver.r());
      this.server.getPluginManager().callEvent(new WorldInitEvent(worldserver.getWorld()));
      if (!iworlddataserver.p()) {
         try {
            a(worldserver, iworlddataserver, worldoptions.d(), flag);
            iworlddataserver.c(true);
            if (flag) {
               this.a(this.m);
            }
         } catch (Throwable var11) {
            CrashReport crashreport = CrashReport.a(var11, "Exception initializing level");

            try {
               worldserver.a(crashreport);
            } catch (Throwable var10) {
            }

            throw new ReportedException(crashreport);
         }

         iworlddataserver.c(true);
      }
   }

   private static void a(WorldServer worldserver, IWorldDataServer iworlddataserver, boolean flag, boolean flag1) {
      if (flag1) {
         iworlddataserver.a(BlockPosition.b.b(80), 0.0F);
      } else {
         ChunkProviderServer chunkproviderserver = worldserver.k();
         ChunkCoordIntPair chunkcoordintpair = new ChunkCoordIntPair(chunkproviderserver.i().b().a());
         if (worldserver.generator != null) {
            Random rand = new Random(worldserver.A());
            Location spawn = worldserver.generator.getFixedSpawnLocation(worldserver.getWorld(), rand);
            if (spawn != null) {
               if (spawn.getWorld() != worldserver.getWorld()) {
                  throw new IllegalStateException(
                     "Cannot set spawn point for " + iworlddataserver.g() + " to be in another world (" + spawn.getWorld().getName() + ")"
                  );
               }

               iworlddataserver.a(new BlockPosition(spawn.getBlockX(), spawn.getBlockY(), spawn.getBlockZ()), spawn.getYaw());
               return;
            }
         }

         int i = chunkproviderserver.g().a(worldserver);
         if (i < worldserver.v_()) {
            BlockPosition blockposition = chunkcoordintpair.l();
            i = worldserver.a(HeightMap.Type.b, blockposition.u() + 8, blockposition.w() + 8);
         }

         iworlddataserver.a(chunkcoordintpair.l().b(8, i, 8), 0.0F);
         int j = 0;
         int k = 0;
         int l = 0;
         int i1 = -1;
         boolean flag2 = true;

         for(int j1 = 0; j1 < MathHelper.h(11); ++j1) {
            if (j >= -5 && j <= 5 && k >= -5 && k <= 5) {
               BlockPosition blockposition1 = WorldProviderNormal.a(worldserver, new ChunkCoordIntPair(chunkcoordintpair.e + j, chunkcoordintpair.f + k));
               if (blockposition1 != null) {
                  iworlddataserver.a(blockposition1, 0.0F);
                  break;
               }
            }

            if (j == k || j < 0 && j == -k || j > 0 && j == 1 - k) {
               int k1 = l;
               l = -i1;
               i1 = k1;
            }

            j += l;
            k += i1;
         }

         if (flag) {
            worldserver.u_()
               .c(Registries.aq)
               .flatMap(iregistry -> iregistry.b(MiscOverworldFeatures.m))
               .ifPresent(
                  holder_c -> holder_c.a()
                        .a(
                           worldserver,
                           chunkproviderserver.g(),
                           worldserver.z,
                           new BlockPosition(iworlddataserver.a(), iworlddataserver.b(), iworlddataserver.c())
                        )
               );
         }
      }
   }

   private void a(SaveData savedata) {
      savedata.a(EnumDifficulty.a);
      savedata.d(true);
      IWorldDataServer iworlddataserver = savedata.J();
      iworlddataserver.b(false);
      iworlddataserver.a(false);
      iworlddataserver.a(1000000000);
      iworlddataserver.b(6000L);
      iworlddataserver.a(EnumGamemode.d);
   }

   public void prepareLevels(WorldLoadListener worldloadlistener, WorldServer worldserver) {
      this.forceTicks = true;
      n.info("Preparing start region for dimension {}", worldserver.ab().a());
      BlockPosition blockposition = worldserver.Q();
      worldloadlistener.a(new ChunkCoordIntPair(blockposition));
      ChunkProviderServer chunkproviderserver = worldserver.k();
      chunkproviderserver.a().a(500);
      this.ah = SystemUtils.b();
      if (worldserver.getWorld().getKeepSpawnInMemory()) {
         chunkproviderserver.a(TicketType.a, new ChunkCoordIntPair(blockposition), 11, Unit.a);

         while(chunkproviderserver.b() != 441) {
            this.executeModerately();
         }
      }

      this.executeModerately();
      WorldServer worldserver1 = worldserver;
      ForcedChunk forcedchunk = worldserver.s().a(ForcedChunk::b, "chunks");
      if (forcedchunk != null) {
         LongIterator longiterator = forcedchunk.a().iterator();

         while(longiterator.hasNext()) {
            long i = longiterator.nextLong();
            ChunkCoordIntPair chunkcoordintpair = new ChunkCoordIntPair(i);
            worldserver1.k().a(chunkcoordintpair, true);
         }
      }

      this.executeModerately();
      worldloadlistener.b();
      chunkproviderserver.a().a(5);
      worldserver.b(this.Q(), this.W());
      this.forceTicks = false;
   }

   public EnumGamemode h_() {
      return this.m.m();
   }

   public boolean h() {
      return this.m.n();
   }

   public abstract int i();

   public abstract int j();

   public abstract boolean k();

   public boolean a(boolean flag, boolean flag1, boolean flag2) {
      boolean flag3 = false;

      for(WorldServer worldserver : this.F()) {
         if (!flag) {
            n.info("Saving chunks for level '{}'/{}", worldserver, worldserver.ab().a());
         }

         worldserver.a(null, flag1, worldserver.e && !flag2);
         flag3 = true;
      }

      if (flag1) {
         for(WorldServer worldserver2 : this.F()) {
            n.info("ThreadedAnvilChunkStorage ({}): All chunks are saved", worldserver2.k().a.n());
         }

         n.info("ThreadedAnvilChunkStorage: All dimensions are saved");
      }

      return flag3;
   }

   public boolean b(boolean flag, boolean flag1, boolean flag2) {
      boolean flag3;
      try {
         this.aw = true;
         this.ac().h();
         flag3 = this.a(flag, flag1, flag2);
      } finally {
         this.aw = false;
      }

      return flag3;
   }

   @Override
   public void close() {
      this.t();
   }

   public final boolean hasStopped() {
      synchronized(this.stopLock) {
         return this.hasStopped;
      }
   }

   public void t() {
      synchronized(this.stopLock) {
         if (this.hasStopped) {
            return;
         }

         this.hasStopped = true;
      }

      if (this.z.e()) {
         this.aT();
      }

      n.info("Stopping server");
      if (this.server != null) {
         this.server.disablePlugins();
      }

      if (this.ad() != null) {
         this.ad().b();
      }

      this.aw = true;
      if (this.Q != null) {
         n.info("Saving players");
         this.Q.h();
         this.Q.s();

         try {
            Thread.sleep(100L);
         } catch (InterruptedException var6) {
         }
      }

      n.info("Saving worlds");

      for(WorldServer worldserver : this.F()) {
         if (worldserver != null) {
            worldserver.e = false;
         }
      }

      while(this.P.values().stream().anyMatch(worldserver1 -> worldserver1.k().a.f())) {
         this.ah = SystemUtils.b() + 1L;

         for(WorldServer worldserver : this.F()) {
            worldserver.k().o();
            worldserver.k().a(() -> true, false);
         }

         this.i_();
      }

      this.a(false, true, false);

      for(WorldServer worldserver : this.F()) {
         if (worldserver != null) {
            try {
               worldserver.close();
            } catch (IOException var5) {
               n.error("Exception closing the level", var5);
            }
         }
      }

      this.aw = false;
      this.au.close();

      try {
         this.h.close();
      } catch (IOException var4) {
         n.error("Failed to unlock level {}", this.h.a(), var4);
      }

      if (SpigotConfig.saveUserCacheOnStopOnly) {
         n.info("Saving usercache.json");
         this.ap().c();
      }
   }

   public String u() {
      return this.M;
   }

   public void a_(String s) {
      this.M = s;
   }

   public boolean v() {
      return this.R;
   }

   public void a(boolean flag) {
      this.R = false;
      if (flag) {
         try {
            this.ag.join();
         } catch (InterruptedException var3) {
            n.error("Error while shutting down", var3);
         }
      }
   }

   private static double calcTps(double avg, double exp, double tps) {
      return avg * exp + tps * (1.0 - exp);
   }

   protected void w() {
      try {
         if (!this.e()) {
            throw new IllegalStateException("Failed to initialize server");
         }

         this.ah = SystemUtils.b();
         this.J = this.bj().orElse(null);
         this.I = this.bk();
         Arrays.fill(this.recentTps, 20.0);
         long tickSection = SystemUtils.b();
         long tickCount = 1L;

         while(this.R) {
            long curTime;
            long i = (curTime = SystemUtils.b()) - this.ah;
            if (i > 5000L && this.ah - this.ae >= 30000L) {
               long j = i / 50L;
               if (this.server.getWarnOnOverload()) {
                  n.warn("Can't keep up! Is the server overloaded? Running {}ms or {} ticks behind", i, j);
               }

               this.ah += j * 50L;
               this.ae = this.ah;
            }

            if (tickCount++ % 100L == 0L) {
               double currentTps = 1000.0 / (double)(curTime - tickSection) * 100.0;
               this.recentTps[0] = calcTps(this.recentTps[0], 0.92, currentTps);
               this.recentTps[1] = calcTps(this.recentTps[1], 0.9835, currentTps);
               this.recentTps[2] = calcTps(this.recentTps[2], 0.9945, currentTps);
               tickSection = curTime;
            }

            if (this.F) {
               this.F = false;
               this.E = new MinecraftServer.TimeProfiler(SystemUtils.c(), this.T);
            }

            currentTick = (int)(System.currentTimeMillis() / 50L);
            this.ah += 50L;
            this.bv();
            this.A.a("tick");
            this.a(this::bh);
            this.A.b("nextTickWait");
            this.aj = true;
            this.ai = Math.max(SystemUtils.b() + 50L, this.ah);
            this.i_();
            this.A.c();
            this.bw();
            this.ad = true;
            JvmProfiler.e.a(this.ar);
         }
      } catch (Throwable var74) {
         n.error("Encountered an unexpected exception", var74);
         if (var74.getCause() != null) {
            n.error("\tCause of unexpected exception was", var74.getCause());
         }

         CrashReport crashreport = a(var74);
         this.b(crashreport.g());
         File file = new File(new File(this.z(), "crash-reports"), "crash-" + SystemUtils.e() + "-server.txt");
         if (crashreport.a(file)) {
            n.error("This crash report has been saved to: {}", file.getAbsolutePath());
         } else {
            n.error("We were unable to save this crash report to disk.");
         }

         this.a(crashreport);
      } finally {
         try {
            this.S = true;
            this.t();
         } catch (Throwable var72) {
            n.error("Exception stopping the server", var72);
         } finally {
            if (this.l.d() != null) {
               this.l.d().a();
            }

            WatchdogThread.doStop();

            try {
               this.reader.getTerminal().restore();
            } catch (Exception var71) {
            }

            this.g();
         }
      }
   }

   private static CrashReport a(Throwable throwable) {
      ReportedException reportedexception = null;

      for(Throwable throwable1 = throwable; throwable1 != null; throwable1 = throwable1.getCause()) {
         if (throwable1 instanceof ReportedException reportedexception1) {
            reportedexception = reportedexception1;
         }
      }

      CrashReport crashreport;
      if (reportedexception != null) {
         crashreport = reportedexception.a();
         if (reportedexception != throwable) {
            crashreport.a("Wrapped in").a("Wrapping exception", throwable);
         }
      } else {
         crashreport = new CrashReport("Exception in server tick loop", throwable);
      }

      return crashreport;
   }

   private boolean bh() {
      return this.forceTicks || this.bt() || SystemUtils.b() < (this.aj ? this.ai : this.ah);
   }

   private void executeModerately() {
      this.br();
      LockSupport.parkNanos("executing tasks", 1000L);
   }

   protected void i_() {
      this.br();
      this.c(() -> !this.bh());
   }

   public TickTask a(Runnable runnable) {
      return new TickTask(this.T, runnable);
   }

   protected boolean a(TickTask ticktask) {
      return ticktask.a() + 3 < this.T || this.bh();
   }

   @Override
   public boolean x() {
      boolean flag = this.bi();
      this.aj = flag;
      return flag;
   }

   private boolean bi() {
      if (super.x()) {
         return true;
      } else {
         if (this.bh()) {
            for(WorldServer worldserver : this.F()) {
               if (worldserver.k().d()) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   public void b(TickTask ticktask) {
      this.aP().d("runTask");
      super.d(ticktask);
   }

   private Optional<ServerPing.a> bj() {
      Optional<Path> optional = Optional.of(this.c("server-icon.png").toPath())
         .filter(path -> java.nio.file.Files.isRegularFile(path))
         .or(() -> this.h.e().filter(path -> java.nio.file.Files.isRegularFile(path)));
      return optional.flatMap(path -> {
         try {
            BufferedImage bufferedimage = ImageIO.read(path.toFile());
            Preconditions.checkState(bufferedimage.getWidth() == 64, "Must be 64 pixels wide");
            Preconditions.checkState(bufferedimage.getHeight() == 64, "Must be 64 pixels high");
            ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
            ImageIO.write(bufferedimage, "PNG", bytearrayoutputstream);
            return Optional.of(new ServerPing.a(bytearrayoutputstream.toByteArray()));
         } catch (Exception var3) {
            n.error("Couldn't load server icon", var3);
            return Optional.empty();
         }
      });
   }

   public Optional<Path> y() {
      return this.h.e();
   }

   public File z() {
      return new File(".");
   }

   public void a(CrashReport crashreport) {
   }

   public void g() {
   }

   public void a(BooleanSupplier booleansupplier) {
      SpigotTimings.serverTickTimer.startTiming();
      long i = SystemUtils.c();
      ++this.T;
      this.b(booleansupplier);
      if (i - this.af >= 5000000000L) {
         this.af = i;
         this.I = this.bk();
      }

      if (this.autosavePeriod > 0 && this.T % this.autosavePeriod == 0) {
         SpigotTimings.worldSaveTimer.startTiming();
         n.debug("Autosave started");
         this.A.a("save");
         this.b(true, false, false);
         this.A.c();
         n.debug("Autosave finished");
         SpigotTimings.worldSaveTimer.stopTiming();
      }

      this.A.a("tallying");
      long j = this.k[this.T % 100] = SystemUtils.c() - i;
      this.ar = this.ar * 0.8F + (float)j / 1000000.0F * 0.19999999F;
      long k = SystemUtils.c();
      this.ap.a(k - i);
      this.A.c();
      WatchdogThread.tick();
      SpigotTimings.serverTickTimer.stopTiming();
      CustomTimingsHandler.tick();
   }

   private ServerPing bk() {
      ServerPing.ServerPingPlayerSample serverping_serverpingplayersample = this.bl();
      return new ServerPing(
         IChatBaseComponent.a(this.Y),
         Optional.of(serverping_serverpingplayersample),
         Optional.of(ServerPing.ServerData.a()),
         Optional.ofNullable(this.J),
         this.aw()
      );
   }

   private ServerPing.ServerPingPlayerSample bl() {
      List<EntityPlayer> list = this.Q.t();
      int i = this.I();
      if (this.aj()) {
         return new ServerPing.ServerPingPlayerSample(i, list.size(), List.of());
      } else {
         int j = Math.min(list.size(), 12);
         ObjectArrayList<GameProfile> objectarraylist = new ObjectArrayList(j);
         int k = MathHelper.a(this.K, 0, list.size() - j);

         for(int l = 0; l < j; ++l) {
            EntityPlayer entityplayer = list.get(k + l);
            objectarraylist.add(entityplayer.V() ? entityplayer.fI() : g);
         }

         SystemUtils.b(objectarraylist, this.K);
         return new ServerPing.ServerPingPlayerSample(i, list.size(), objectarraylist);
      }
   }

   public void b(BooleanSupplier booleansupplier) {
      SpigotTimings.schedulerTimer.startTiming();
      this.server.getScheduler().mainThreadHeartbeat(this.T);
      SpigotTimings.schedulerTimer.stopTiming();
      this.A.a("commandFunctions");
      SpigotTimings.commandFunctionsTimer.startTiming();
      this.aA().c();
      SpigotTimings.commandFunctionsTimer.stopTiming();
      this.A.b("levels");
      Iterator iterator = this.F().iterator();
      SpigotTimings.processQueueTimer.startTiming();

      while(!this.processQueue.isEmpty()) {
         this.processQueue.remove().run();
      }

      SpigotTimings.processQueueTimer.stopTiming();
      SpigotTimings.timeUpdateTimer.startTiming();
      if (this.T % 20 == 0) {
         for(int i = 0; i < this.ac().k.size(); ++i) {
            EntityPlayer entityplayer = this.ac().k.get(i);
            entityplayer.b.a(new PacketPlayOutUpdateTime(entityplayer.H.U(), entityplayer.getPlayerTime(), entityplayer.H.W().b(GameRules.k)));
         }
      }

      SpigotTimings.timeUpdateTimer.stopTiming();

      while(iterator.hasNext()) {
         WorldServer worldserver = (WorldServer)iterator.next();
         this.A.a(() -> worldserver + " " + worldserver.ab().a());
         this.A.a("tick");

         try {
            worldserver.timings.doTick.startTiming();
            worldserver.a(booleansupplier);
            worldserver.timings.doTick.stopTiming();
         } catch (Throwable var8) {
            Throwable throwable = var8;

            CrashReport crashreport;
            try {
               crashreport = CrashReport.a(throwable, "Exception ticking world");
            } catch (Throwable var7) {
               throw new RuntimeException("Error generating crash report", var7);
            }

            worldserver.a(crashreport);
            throw new ReportedException(crashreport);
         }

         this.A.c();
         this.A.c();
      }

      this.A.b("connection");
      SpigotTimings.connectionTimer.startTiming();
      this.ad().c();
      SpigotTimings.connectionTimer.stopTiming();
      this.A.b("players");
      SpigotTimings.playerListTimer.startTiming();
      this.Q.d();
      SpigotTimings.playerListTimer.stopTiming();
      if (SharedConstants.aO) {
         GameTestHarnessTicker.a.b();
      }

      this.A.b("server gui refresh");
      SpigotTimings.tickablesTimer.startTiming();

      for(int i = 0; i < this.y.size(); ++i) {
         this.y.get(i).run();
      }

      SpigotTimings.tickablesTimer.stopTiming();
      this.A.c();
   }

   private void b(WorldServer worldserver) {
      this.Q.a(new PacketPlayOutUpdateTime(worldserver.U(), worldserver.V(), worldserver.W().b(GameRules.k)), worldserver.ab());
   }

   public void A() {
      this.A.a("timeSync");

      for(WorldServer worldserver : this.F()) {
         this.b(worldserver);
      }

      this.A.c();
   }

   public boolean B() {
      return true;
   }

   public void b(Runnable runnable) {
      this.y.add(runnable);
   }

   protected void b(String s) {
      this.at = s;
   }

   public boolean C() {
      return !this.ag.isAlive();
   }

   public File c(String s) {
      return new File(this.z(), s);
   }

   public final WorldServer D() {
      return this.P.get(World.h);
   }

   @Nullable
   public WorldServer a(ResourceKey<World> resourcekey) {
      return this.P.get(resourcekey);
   }

   public void addLevel(WorldServer level) {
      Map<ResourceKey<World>, WorldServer> oldLevels = this.P;
      Map<ResourceKey<World>, WorldServer> newLevels = Maps.newLinkedHashMap(oldLevels);
      newLevels.put(level.ab(), level);
      this.P = Collections.unmodifiableMap(newLevels);
   }

   public void removeLevel(WorldServer level) {
      Map<ResourceKey<World>, WorldServer> oldLevels = this.P;
      Map<ResourceKey<World>, WorldServer> newLevels = Maps.newLinkedHashMap(oldLevels);
      newLevels.remove(level.ab());
      this.P = Collections.unmodifiableMap(newLevels);
   }

   public Set<ResourceKey<World>> E() {
      return this.P.keySet();
   }

   public Iterable<WorldServer> F() {
      return this.P.values();
   }

   public String G() {
      return SharedConstants.b().c();
   }

   public int H() {
      return this.Q.m();
   }

   public int I() {
      return this.Q.n();
   }

   public String[] J() {
      return this.Q.e();
   }

   @DontObfuscate
   public String getServerModName() {
      return "Spigot";
   }

   public SystemReport b(SystemReport systemreport) {
      systemreport.a("Server Running", () -> Boolean.toString(this.R));
      if (this.Q != null) {
         systemreport.a("Player Count", () -> {
            int i = this.Q.m();
            return i + " / " + this.Q.n() + "; " + this.Q.t();
         });
      }

      systemreport.a("Data Packs", () -> this.ak.f().stream().map(resourcepackloader -> {
            String s = resourcepackloader.f();
            return s + (resourcepackloader.c().a() ? "" : " (incompatible)");
         }).collect(Collectors.joining(", ")));
      systemreport.a("Enabled Feature Flags", () -> FeatureFlags.d.b(this.m.L()).stream().map(MinecraftKey::toString).collect(Collectors.joining(", ")));
      systemreport.a("World Generation", () -> this.m.D().toString());
      if (this.at != null) {
         systemreport.a("Server Id", () -> this.at);
      }

      return this.a(systemreport);
   }

   public abstract SystemReport a(SystemReport var1);

   public ModCheck K() {
      return ModCheck.a("vanilla", this::getServerModName, "Server", MinecraftServer.class);
   }

   @Override
   public void a(IChatBaseComponent ichatbasecomponent) {
      n.info(ichatbasecomponent.getString());
   }

   public KeyPair L() {
      return this.aa;
   }

   public int M() {
      return this.N;
   }

   public void a(int i) {
      this.N = i;
   }

   @Nullable
   public GameProfile N() {
      return this.ab;
   }

   public void b(@Nullable GameProfile gameprofile) {
      this.ab = gameprofile;
   }

   public boolean O() {
      return this.ab != null;
   }

   protected void P() {
      n.info("Generating keypair");

      try {
         this.aa = MinecraftEncryption.b();
      } catch (CryptographyException var2) {
         throw new IllegalStateException("Failed to generate key pair", var2);
      }
   }

   public void a(EnumDifficulty enumdifficulty, boolean flag) {
      if (flag || !this.m.t()) {
         this.m.a(this.m.n() ? EnumDifficulty.d : enumdifficulty);
         this.bu();
         this.ac().t().forEach(this::c);
      }
   }

   public int b(int i) {
      return i;
   }

   private void bu() {
      for(WorldServer worldserver : this.F()) {
         worldserver.b(this.Q(), this.W());
      }
   }

   public void b(boolean flag) {
      this.m.d(flag);
      this.ac().t().forEach(this::c);
   }

   private void c(EntityPlayer entityplayer) {
      WorldData worlddata = entityplayer.x().n_();
      entityplayer.b.a(new PacketPlayOutServerDifficulty(worlddata.s(), worlddata.t()));
   }

   public boolean Q() {
      return this.m.s() != EnumDifficulty.a;
   }

   public boolean R() {
      return this.ac;
   }

   public void c(boolean flag) {
      this.ac = flag;
   }

   public Optional<MinecraftServer.ServerResourcePackInfo> S() {
      return Optional.empty();
   }

   public boolean T() {
      return this.S().filter(MinecraftServer.ServerResourcePackInfo::c).isPresent();
   }

   public abstract boolean l();

   public abstract int m();

   public boolean U() {
      return this.U;
   }

   public void d(boolean flag) {
      this.U = flag;
   }

   public boolean V() {
      return this.V;
   }

   public void e(boolean flag) {
      this.V = flag;
   }

   public boolean W() {
      return true;
   }

   public boolean X() {
      return true;
   }

   public abstract boolean n();

   public boolean Y() {
      return this.W;
   }

   public void f(boolean flag) {
      this.W = flag;
   }

   public boolean Z() {
      return this.X;
   }

   public void g(boolean flag) {
      this.X = flag;
   }

   public abstract boolean o();

   public String aa() {
      return this.Y;
   }

   public void d(String s) {
      this.Y = s;
   }

   public boolean ab() {
      return this.S;
   }

   public PlayerList ac() {
      return this.Q;
   }

   public void a(PlayerList playerlist) {
      this.Q = playerlist;
   }

   public abstract boolean p();

   public void a(EnumGamemode enumgamemode) {
      this.m.a(enumgamemode);
   }

   @Nullable
   public ServerConnection ad() {
      return this.G == null ? (this.G = new ServerConnection(this)) : this.G;
   }

   public boolean ae() {
      return this.ad;
   }

   public boolean af() {
      return false;
   }

   public boolean a(@Nullable EnumGamemode enumgamemode, boolean flag, int i) {
      return false;
   }

   public int ag() {
      return this.T;
   }

   public int ah() {
      return 16;
   }

   public boolean a(WorldServer worldserver, BlockPosition blockposition, EntityHuman entityhuman) {
      return false;
   }

   public boolean ai() {
      return true;
   }

   public boolean aj() {
      return false;
   }

   public Proxy ak() {
      return this.j;
   }

   public int al() {
      return this.Z;
   }

   public void c(int i) {
      this.Z = i;
   }

   public MinecraftSessionService am() {
      return this.l.a();
   }

   public SignatureValidator an() {
      return this.l.b();
   }

   public GameProfileRepository ao() {
      return this.l.c();
   }

   public UserCache ap() {
      return this.l.d();
   }

   @Nullable
   public ServerPing aq() {
      return this.I;
   }

   public void ar() {
      this.af = 0L;
   }

   public int as() {
      return 29999984;
   }

   @Override
   public boolean at() {
      return super.at() && !this.ab();
   }

   @Override
   public void c(Runnable runnable) {
      if (this.ab()) {
         throw new RejectedExecutionException("Server already shutting down");
      } else {
         super.c(runnable);
      }
   }

   @Override
   public Thread au() {
      return this.ag;
   }

   public int av() {
      return 256;
   }

   public boolean aw() {
      return false;
   }

   public long ax() {
      return this.ah;
   }

   public DataFixer ay() {
      return this.L;
   }

   public int a(@Nullable WorldServer worldserver) {
      return worldserver != null ? worldserver.W().c(GameRules.r) : 10;
   }

   public AdvancementDataWorld az() {
      return this.au.b.g();
   }

   public CustomFunctionData aA() {
      return this.ao;
   }

   public CompletableFuture<Void> a(Collection<String> collection) {
      IRegistryCustom.Dimension iregistrycustom_dimension = this.O.b(RegistryLayer.d);
      CompletableFuture<Void> completablefuture = CompletableFuture.supplyAsync(
            () -> {
               Stream<String> stream = collection.stream();
               ResourcePackRepository resourcepackrepository = this.ak;
               return stream.<ResourcePackLoader>map(resourcepackrepository::c)
                  .filter(Objects::nonNull)
                  .map(ResourcePackLoader::e)
                  .collect(ImmutableList.toImmutableList());
            },
            this
         )
         .thenCompose(
            immutablelist -> {
               ResourceManager resourcemanager = new ResourceManager(EnumResourcePackType.b, immutablelist);
               return DataPackResources.a(
                     resourcemanager,
                     iregistrycustom_dimension,
                     this.m.L(),
                     this.l() ? CommandDispatcher.ServerType.b : CommandDispatcher.ServerType.c,
                     this.j(),
                     this.as,
                     this
                  )
                  .whenComplete((datapackresources, throwable) -> {
                     if (throwable != null) {
                        resourcemanager.close();
                     }
                  })
                  .thenApply(datapackresources -> new MinecraftServer.ReloadableResources(resourcemanager, datapackresources));
            }
         )
         .thenAcceptAsync(minecraftserver_reloadableresources -> {
            this.au.close();
            this.au = minecraftserver_reloadableresources;
            this.server.syncCommands();
            this.ak.a(collection);
            WorldDataConfiguration worlddataconfiguration = new WorldDataConfiguration(a(this.ak), this.m.L());
            this.m.a(worlddataconfiguration);
            this.au.b.a(this.aX());
            this.ac().h();
            this.ac().u();
            this.ao.a(this.au.b.a());
            this.av.a(this.au.a);
         }, this);
      if (this.bn()) {
         this.c(completablefuture::isDone);
      }

      return completablefuture;
   }

   public static WorldDataConfiguration a(
      ResourcePackRepository resourcepackrepository, DataPackConfiguration datapackconfiguration, boolean flag, FeatureFlagSet featureflagset
   ) {
      resourcepackrepository.a();
      if (flag) {
         resourcepackrepository.a(Collections.singleton("vanilla"));
         return WorldDataConfiguration.c;
      } else {
         Set<String> set = Sets.newLinkedHashSet();

         for(String s : datapackconfiguration.a()) {
            if (resourcepackrepository.d(s)) {
               set.add(s);
            } else {
               n.warn("Missing data pack {}", s);
            }
         }

         for(ResourcePackLoader resourcepackloader : resourcepackrepository.c()) {
            String s1 = resourcepackloader.f();
            if (!datapackconfiguration.b().contains(s1)) {
               FeatureFlagSet featureflagset1 = resourcepackloader.d();
               boolean flag1 = set.contains(s1);
               if (!flag1 && resourcepackloader.j().a()) {
                  if (featureflagset1.a(featureflagset)) {
                     n.info("Found new data pack {}, loading it automatically", s1);
                     set.add(s1);
                  } else {
                     n.info("Found new data pack {}, but can't load it due to missing features {}", s1, FeatureFlags.a(featureflagset, featureflagset1));
                  }
               }

               if (flag1 && !featureflagset1.a(featureflagset)) {
                  n.warn(
                     "Pack {} requires features {} that are not enabled for this world, disabling pack.", s1, FeatureFlags.a(featureflagset, featureflagset1)
                  );
                  set.remove(s1);
               }
            }
         }

         if (set.isEmpty()) {
            n.info("No datapacks selected, forcing vanilla");
            set.add("vanilla");
         }

         resourcepackrepository.a(set);
         DataPackConfiguration datapackconfiguration1 = a(resourcepackrepository);
         FeatureFlagSet featureflagset2 = resourcepackrepository.e();
         return new WorldDataConfiguration(datapackconfiguration1, featureflagset2);
      }
   }

   private static DataPackConfiguration a(ResourcePackRepository resourcepackrepository) {
      Collection<String> collection = resourcepackrepository.d();
      List<String> list = ImmutableList.copyOf(collection);
      List<String> list1 = resourcepackrepository.b().stream().filter(s -> !collection.contains(s)).collect(ImmutableList.toImmutableList());
      return new DataPackConfiguration(list, list1);
   }

   public void a(CommandListenerWrapper commandlistenerwrapper) {
      if (this.aM()) {
         PlayerList playerlist = commandlistenerwrapper.l().ac();
         WhiteList whitelist = playerlist.i();

         for(EntityPlayer entityplayer : Lists.newArrayList(playerlist.t())) {
            if (!whitelist.a(entityplayer.fI())) {
               entityplayer.b.b(IChatBaseComponent.c("multiplayer.disconnect.not_whitelisted"));
            }
         }
      }
   }

   public ResourcePackRepository aB() {
      return this.ak;
   }

   public CommandDispatcher aC() {
      return this.au.b.f();
   }

   public CommandListenerWrapper aD() {
      WorldServer worldserver = this.D();
      return new CommandListenerWrapper(
         this, worldserver == null ? Vec3D.b : Vec3D.a(worldserver.Q()), Vec2F.a, worldserver, 4, "Server", IChatBaseComponent.b("Server"), this, null
      );
   }

   @Override
   public boolean d_() {
      return true;
   }

   @Override
   public boolean j_() {
      return true;
   }

   @Override
   public abstract boolean M_();

   public CraftingManager aE() {
      return this.au.b.e();
   }

   public ScoreboardServer aF() {
      return this.al;
   }

   public PersistentCommandStorage aG() {
      if (this.am == null) {
         throw new NullPointerException("Called before server init");
      } else {
         return this.am;
      }
   }

   public LootTableRegistry aH() {
      return this.au.b.c();
   }

   public LootPredicateManager aI() {
      return this.au.b.b();
   }

   public ItemModifierManager aJ() {
      return this.au.b.d();
   }

   public GameRules aK() {
      return this.D().W();
   }

   public BossBattleCustomData aL() {
      return this.an;
   }

   public boolean aM() {
      return this.aq;
   }

   public void h(boolean flag) {
      this.aq = flag;
   }

   public float aN() {
      return this.ar;
   }

   public int c(GameProfile gameprofile) {
      if (this.ac().f(gameprofile)) {
         OpListEntry oplistentry = this.ac().k().b(gameprofile);
         return oplistentry != null ? oplistentry.a() : (this.a(gameprofile) ? 4 : (this.O() ? (this.ac().v() ? 4 : 0) : this.i()));
      } else {
         return 0;
      }
   }

   public CircularTimer aO() {
      return this.ap;
   }

   public GameProfilerFiller aP() {
      return this.A;
   }

   public abstract boolean a(GameProfile var1);

   public void a(Path path) throws IOException {
   }

   private void b(Path path) {
      Path path1 = path.resolve("levels");

      try {
         for(Entry<ResourceKey<World>, WorldServer> entry : this.P.entrySet()) {
            MinecraftKey minecraftkey = entry.getKey().a();
            Path path2 = path1.resolve(minecraftkey.b()).resolve(minecraftkey.a());
            java.nio.file.Files.createDirectories(path2);
            entry.getValue().a(path2);
         }

         this.d(path.resolve("gamerules.txt"));
         this.e(path.resolve("classpath.txt"));
         this.c(path.resolve("stats.txt"));
         this.f(path.resolve("threads.txt"));
         this.a(path.resolve("server.properties.txt"));
         this.g(path.resolve("modules.txt"));
      } catch (IOException var7) {
         n.warn("Failed to save debug report", var7);
      }
   }

   private void c(Path path) throws IOException {
      try (BufferedWriter bufferedwriter = java.nio.file.Files.newBufferedWriter(path)) {
         bufferedwriter.write(String.format(Locale.ROOT, "pending_tasks: %d\n", this.bo()));
         bufferedwriter.write(String.format(Locale.ROOT, "average_tick_time: %f\n", this.aN()));
         bufferedwriter.write(String.format(Locale.ROOT, "tick_times: %s\n", Arrays.toString(this.k)));
         bufferedwriter.write(String.format(Locale.ROOT, "queue: %s\n", SystemUtils.f()));
      }
   }

   private void d(Path path) throws IOException {
      try (BufferedWriter bufferedwriter = java.nio.file.Files.newBufferedWriter(path)) {
         final List<String> list = Lists.newArrayList();
         final GameRules gamerules = this.aK();
         GameRules.a(
            new GameRules.GameRuleVisitor() {
               @Override
               public <T extends GameRules.GameRuleValue<T>> void a(
                  GameRules.GameRuleKey<T> gamerules_gamerulekey, GameRules.GameRuleDefinition<T> gamerules_gameruledefinition
               ) {
                  list.add(String.format(Locale.ROOT, "%s=%s\n", gamerules_gamerulekey.a(), gamerules.<T>a(gamerules_gamerulekey)));
               }
            }
         );

         for(String s : list) {
            bufferedwriter.write(s);
         }
      }
   }

   private void e(Path path) throws IOException {
      try (BufferedWriter bufferedwriter = java.nio.file.Files.newBufferedWriter(path)) {
         String s = System.getProperty("java.class.path");
         String s1 = System.getProperty("path.separator");

         for(String s2 : Splitter.on(s1).split(s)) {
            bufferedwriter.write(s2);
            bufferedwriter.write("\n");
         }
      }
   }

   private void f(Path path) throws IOException {
      ThreadMXBean threadmxbean = ManagementFactory.getThreadMXBean();
      ThreadInfo[] athreadinfo = threadmxbean.dumpAllThreads(true, true);
      Arrays.sort(athreadinfo, Comparator.comparing(ThreadInfo::getThreadName));

      try (BufferedWriter bufferedwriter = java.nio.file.Files.newBufferedWriter(path)) {
         for(ThreadInfo threadinfo : athreadinfo) {
            bufferedwriter.write(threadinfo.toString());
            bufferedwriter.write(10);
         }
      }
   }

   private void g(Path path) throws IOException {
      try (BufferedWriter bufferedwriter = java.nio.file.Files.newBufferedWriter(path)) {
         ArrayList<NativeModuleLister.a> arraylist;
         try {
            arraylist = Lists.newArrayList(NativeModuleLister.a());
         } catch (Throwable var7) {
            n.warn("Failed to list native modules", var7);
            return;
         }

         arraylist.sort(Comparator.comparing(nativemodulelister_ax -> nativemodulelister_ax.a));

         for(NativeModuleLister.a nativemodulelister_a : arraylist) {
            bufferedwriter.write(nativemodulelister_a.toString());
            bufferedwriter.write(10);
         }
      }
   }

   @Override
   public boolean bn() {
      return super.bn() || this.ab();
   }

   public boolean isDebugging() {
      return false;
   }

   @Deprecated
   public static MinecraftServer getServer() {
      return Bukkit.getServer() instanceof CraftServer ? ((CraftServer)Bukkit.getServer()).getServer() : null;
   }

   private void bv() {
      if (this.D) {
         this.z = ActiveMetricsRecorder.a(
            new ServerMetricsSamplersProvider(SystemUtils.a, this.l()), SystemUtils.a, SystemUtils.g(), new MetricsPersister("server"), this.B, path -> {
               this.h(() -> this.b(path.resolve("server")));
               this.C.accept(path);
            }
         );
         this.D = false;
      }

      this.A = GameProfilerTick.a(this.z.f(), GameProfilerTick.a("Server"));
      this.z.c();
      this.A.a();
   }

   private void bw() {
      this.A.b();
      this.z.d();
   }

   public boolean aQ() {
      return this.z.e();
   }

   public void a(Consumer<MethodProfilerResults> consumer, Consumer<Path> consumer1) {
      this.B = methodprofilerresults -> {
         this.aR();
         consumer.accept(methodprofilerresults);
      };
      this.C = consumer1;
      this.D = true;
   }

   public void aR() {
      this.z = InactiveMetricsRecorder.a;
   }

   public void aS() {
      this.z.a();
   }

   public void aT() {
      this.z.b();
      this.A = this.z.f();
   }

   public Path a(SavedFile savedfile) {
      return this.h.a(savedfile);
   }

   public boolean aU() {
      return true;
   }

   public StructureTemplateManager aV() {
      return this.av;
   }

   public SaveData aW() {
      return this.m;
   }

   public IRegistryCustom.Dimension aX() {
      return this.O.a();
   }

   public LayeredRegistryAccess<RegistryLayer> aY() {
      return this.O;
   }

   public ITextFilter a(EntityPlayer entityplayer) {
      return ITextFilter.a;
   }

   public PlayerInteractManager b(EntityPlayer entityplayer) {
      return (PlayerInteractManager)(this.R() ? new DemoPlayerInteractManager(entityplayer) : new PlayerInteractManager(entityplayer));
   }

   @Nullable
   public EnumGamemode aZ() {
      return null;
   }

   public IResourceManager ba() {
      return this.au.a;
   }

   public boolean bb() {
      return this.aw;
   }

   public boolean bc() {
      return this.F || this.E != null;
   }

   public void bd() {
      this.F = true;
   }

   public MethodProfilerResults be() {
      if (this.E == null) {
         return MethodProfilerResultsEmpty.a;
      } else {
         MethodProfilerResults methodprofilerresults = this.E.a(SystemUtils.c(), this.T);
         this.E = null;
         return methodprofilerresults;
      }
   }

   public int bf() {
      return 1000000;
   }

   public void a(IChatBaseComponent ichatbasecomponent, ChatMessageType.a chatmessagetype_a, @Nullable String s) {
      String s1 = chatmessagetype_a.a(ichatbasecomponent).getString();
      if (s != null) {
         n.info("[{}] {}", s, s1);
      } else {
         n.info("{}", s1);
      }
   }

   public ChatDecorator bg() {
      return (entityplayer, ichatbasecomponent) -> entityplayer == null
            ? CompletableFuture.completedFuture(ichatbasecomponent)
            : CompletableFuture.supplyAsync(
               () -> {
                  AsyncPlayerChatPreviewEvent event = new AsyncPlayerChatPreviewEvent(
                     true, entityplayer.getBukkitEntity(), CraftChatMessage.fromComponent(ichatbasecomponent), new LazyPlayerSet(this)
                  );
                  String originalFormat = event.getFormat();
                  String originalMessage = event.getMessage();
                  this.server.getPluginManager().callEvent(event);
                  return originalFormat.equals(event.getFormat())
                        && originalMessage.equals(event.getMessage())
                        && event.getPlayer().getName().equalsIgnoreCase(event.getPlayer().getDisplayName())
                     ? ichatbasecomponent
                     : CraftChatMessage.fromStringOrNull(String.format(event.getFormat(), event.getPlayer().getDisplayName(), event.getMessage()));
               },
               this.chatExecutor
            );
   }

   public static record ReloadableResources(IReloadableResourceManager resourceManager, DataPackResources managers) implements AutoCloseable {
      private final IReloadableResourceManager a;
      private final DataPackResources b;

      public ReloadableResources(IReloadableResourceManager resourceManager, DataPackResources managers) {
         this.a = resourceManager;
         this.b = managers;
      }

      @Override
      public void close() {
         this.a.close();
      }
   }

   public static record ServerResourcePackInfo(String url, String hash, boolean isRequired, @Nullable IChatBaseComponent prompt) {
      private final String a;
      private final String b;
      private final boolean c;
      @Nullable
      private final IChatBaseComponent d;

      public ServerResourcePackInfo(String url, String hash, boolean isRequired, @Nullable IChatBaseComponent prompt) {
         this.a = url;
         this.b = hash;
         this.c = isRequired;
         this.d = prompt;
      }
   }

   private static class TimeProfiler {
      final long a;
      final int b;

      TimeProfiler(long i, int j) {
         this.a = i;
         this.b = j;
      }

      MethodProfilerResults a(final long i, final int j) {
         return new MethodProfilerResults() {
            @Override
            public List<MethodProfilerResultsField> a(String s) {
               return Collections.emptyList();
            }

            @Override
            public boolean a(Path path) {
               return false;
            }

            @Override
            public long a() {
               return TimeProfiler.this.a;
            }

            @Override
            public int b() {
               return TimeProfiler.this.b;
            }

            @Override
            public long c() {
               return i;
            }

            @Override
            public int d() {
               return j;
            }

            @Override
            public String e() {
               return "";
            }
         };
      }
   }
}
