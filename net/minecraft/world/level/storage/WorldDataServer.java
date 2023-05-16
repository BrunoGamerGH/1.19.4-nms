package net.minecraft.world.level.storage;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.mojang.datafixers.DataFixer;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.DataResult.PartialResult;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.CrashReportSystemDetails;
import net.minecraft.SharedConstants;
import net.minecraft.SystemUtils;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.IRegistry;
import net.minecraft.core.IRegistryCustom;
import net.minecraft.core.UUIDUtil;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.DynamicOpsNBT;
import net.minecraft.nbt.GameProfileSerializer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.protocol.game.PacketPlayOutServerDifficulty;
import net.minecraft.resources.RegistryOps;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.level.EnumGamemode;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.WorldDataConfiguration;
import net.minecraft.world.level.WorldSettings;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.dimension.WorldDimension;
import net.minecraft.world.level.levelgen.GeneratorSettings;
import net.minecraft.world.level.levelgen.WorldDimensions;
import net.minecraft.world.level.levelgen.WorldOptions;
import net.minecraft.world.level.timers.CustomFunctionCallbackTimerQueue;
import net.minecraft.world.level.timers.CustomFunctionCallbackTimers;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.slf4j.Logger;

public class WorldDataServer implements IWorldDataServer, SaveData {
   private static final Logger e = LogUtils.getLogger();
   protected static final String a = "Player";
   protected static final String b = "WorldGenSettings";
   public WorldSettings f;
   private final WorldOptions g;
   private final WorldDataServer.a h;
   private final Lifecycle i;
   private int j;
   private int k;
   private int l;
   private float m;
   private long n;
   private long o;
   @Nullable
   private final DataFixer p;
   private final int q;
   private boolean r;
   @Nullable
   private NBTTagCompound s;
   private final int t;
   private int u;
   private boolean v;
   private int w;
   private boolean x;
   private int y;
   private boolean z;
   private boolean A;
   private WorldBorder.c B;
   private NBTTagCompound C;
   @Nullable
   private NBTTagCompound D;
   private int E;
   private int F;
   @Nullable
   private UUID G;
   private final Set<String> H;
   private boolean I;
   private final CustomFunctionCallbackTimerQueue<MinecraftServer> J;
   public IRegistry<WorldDimension> customDimensions;
   private WorldServer world;
   protected NBTBase pdc;

   public void setWorld(WorldServer world) {
      if (this.world == null) {
         this.world = world;
         world.getWorld().readBukkitValues(this.pdc);
         this.pdc = null;
      }
   }

   private WorldDataServer(
      @Nullable DataFixer datafixer,
      int i,
      @Nullable NBTTagCompound nbttagcompound,
      boolean flag,
      int j,
      int k,
      int l,
      float f,
      long i1,
      long j1,
      int k1,
      int l1,
      int i2,
      boolean flag1,
      int j2,
      boolean flag2,
      boolean flag3,
      boolean flag4,
      WorldBorder.c worldborder_c,
      int k2,
      int l2,
      @Nullable UUID uuid,
      Set<String> set,
      CustomFunctionCallbackTimerQueue<MinecraftServer> customfunctioncallbacktimerqueue,
      @Nullable NBTTagCompound nbttagcompound1,
      NBTTagCompound nbttagcompound2,
      WorldSettings worldsettings,
      WorldOptions worldoptions,
      WorldDataServer.a worlddataserver_a,
      Lifecycle lifecycle
   ) {
      this.p = datafixer;
      this.I = flag;
      this.j = j;
      this.k = k;
      this.l = l;
      this.m = f;
      this.n = i1;
      this.o = j1;
      this.t = k1;
      this.u = l1;
      this.w = i2;
      this.v = flag1;
      this.y = j2;
      this.x = flag2;
      this.z = flag3;
      this.A = flag4;
      this.B = worldborder_c;
      this.E = k2;
      this.F = l2;
      this.G = uuid;
      this.H = set;
      this.s = nbttagcompound;
      this.q = i;
      this.J = customfunctioncallbacktimerqueue;
      this.D = nbttagcompound1;
      this.C = nbttagcompound2;
      this.f = worldsettings;
      this.g = worldoptions;
      this.h = worlddataserver_a;
      this.i = lifecycle;
   }

   public WorldDataServer(WorldSettings worldsettings, WorldOptions worldoptions, WorldDataServer.a worlddataserver_a, Lifecycle lifecycle) {
      this(
         null,
         SharedConstants.b().d().c(),
         null,
         false,
         0,
         0,
         0,
         0.0F,
         0L,
         0L,
         19133,
         0,
         0,
         false,
         0,
         false,
         false,
         false,
         WorldBorder.e,
         0,
         0,
         null,
         Sets.newLinkedHashSet(),
         new CustomFunctionCallbackTimerQueue<>(CustomFunctionCallbackTimers.a),
         null,
         new NBTTagCompound(),
         worldsettings.h(),
         worldoptions,
         worlddataserver_a,
         lifecycle
      );
   }

   public static <T> WorldDataServer a(
      Dynamic<T> dynamic,
      DataFixer datafixer,
      int i,
      @Nullable NBTTagCompound nbttagcompound,
      WorldSettings worldsettings,
      LevelVersion levelversion,
      WorldDataServer.a worlddataserver_a,
      WorldOptions worldoptions,
      Lifecycle lifecycle
   ) {
      long j = dynamic.get("Time").asLong(0L);
      NBTTagCompound nbttagcompound1 = (NBTTagCompound)((Dynamic)dynamic.get("DragonFight")
            .result()
            .orElseGet(() -> (T)dynamic.get("DimensionData").get("1").get("DragonFight").orElseEmptyMap()))
         .convert(DynamicOpsNBT.a)
         .getValue();
      return new WorldDataServer(
         datafixer,
         i,
         nbttagcompound,
         dynamic.get("WasModded").asBoolean(false),
         dynamic.get("SpawnX").asInt(0),
         dynamic.get("SpawnY").asInt(0),
         dynamic.get("SpawnZ").asInt(0),
         dynamic.get("SpawnAngle").asFloat(0.0F),
         j,
         dynamic.get("DayTime").asLong(j),
         levelversion.a(),
         dynamic.get("clearWeatherTime").asInt(0),
         dynamic.get("rainTime").asInt(0),
         dynamic.get("raining").asBoolean(false),
         dynamic.get("thunderTime").asInt(0),
         dynamic.get("thundering").asBoolean(false),
         dynamic.get("initialized").asBoolean(true),
         dynamic.get("DifficultyLocked").asBoolean(false),
         WorldBorder.c.a(dynamic, WorldBorder.e),
         dynamic.get("WanderingTraderSpawnDelay").asInt(0),
         dynamic.get("WanderingTraderSpawnChance").asInt(0),
         (UUID)dynamic.get("WanderingTraderId").read(UUIDUtil.a).result().orElse((T)null),
         dynamic.get("ServerBrands")
            .asStream()
            .flatMap(dynamic1 -> dynamic1.asString().result().stream())
            .collect(Collectors.toCollection(Sets::newLinkedHashSet)),
         new CustomFunctionCallbackTimerQueue<>(CustomFunctionCallbackTimers.a, dynamic.get("ScheduledEvents").asStream()),
         (NBTTagCompound)dynamic.get("CustomBossEvents").orElseEmptyMap().getValue(),
         nbttagcompound1,
         worldsettings,
         worldoptions,
         worlddataserver_a,
         lifecycle
      );
   }

   @Override
   public NBTTagCompound a(IRegistryCustom iregistrycustom, @Nullable NBTTagCompound nbttagcompound) {
      this.M();
      if (nbttagcompound == null) {
         nbttagcompound = this.s;
      }

      NBTTagCompound nbttagcompound1 = new NBTTagCompound();
      this.a(iregistrycustom, nbttagcompound1, nbttagcompound);
      return nbttagcompound1;
   }

   private void a(IRegistryCustom iregistrycustom, NBTTagCompound nbttagcompound, @Nullable NBTTagCompound nbttagcompound1) {
      NBTTagList nbttaglist = new NBTTagList();
      Stream<NBTTagString> stream = this.H.stream().map(NBTTagString::a);
      stream.forEach(nbttaglist::add);
      nbttagcompound.a("ServerBrands", nbttaglist);
      nbttagcompound.a("WasModded", this.I);
      NBTTagCompound nbttagcompound2 = new NBTTagCompound();
      nbttagcompound2.a("Name", SharedConstants.b().c());
      nbttagcompound2.a("Id", SharedConstants.b().d().c());
      nbttagcompound2.a("Snapshot", !SharedConstants.b().g());
      nbttagcompound2.a("Series", SharedConstants.b().d().b());
      nbttagcompound.a("Version", nbttagcompound2);
      GameProfileSerializer.g(nbttagcompound);
      DynamicOps<NBTBase> dynamicops = RegistryOps.a(DynamicOpsNBT.a, iregistrycustom);
      DataResult<NBTBase> dataresult = GeneratorSettings.a(
         dynamicops, this.g, new WorldDimensions(this.customDimensions != null ? this.customDimensions : iregistrycustom.d(Registries.aG))
      );
      Logger logger = e;
      dataresult.resultOrPartial(SystemUtils.a("WorldGenSettings: ", logger::error)).ifPresent(nbtbase -> nbttagcompound.a("WorldGenSettings", nbtbase));
      nbttagcompound.a("GameType", this.f.b().a());
      nbttagcompound.a("SpawnX", this.j);
      nbttagcompound.a("SpawnY", this.k);
      nbttagcompound.a("SpawnZ", this.l);
      nbttagcompound.a("SpawnAngle", this.m);
      nbttagcompound.a("Time", this.n);
      nbttagcompound.a("DayTime", this.o);
      nbttagcompound.a("LastPlayed", SystemUtils.d());
      nbttagcompound.a("LevelName", this.f.a());
      nbttagcompound.a("version", 19133);
      nbttagcompound.a("clearWeatherTime", this.u);
      nbttagcompound.a("rainTime", this.w);
      nbttagcompound.a("raining", this.v);
      nbttagcompound.a("thunderTime", this.y);
      nbttagcompound.a("thundering", this.x);
      nbttagcompound.a("hardcore", this.f.c());
      nbttagcompound.a("allowCommands", this.f.e());
      nbttagcompound.a("initialized", this.z);
      this.B.a(nbttagcompound);
      nbttagcompound.a("Difficulty", (byte)this.f.d().a());
      nbttagcompound.a("DifficultyLocked", this.A);
      nbttagcompound.a("GameRules", this.f.f().a());
      nbttagcompound.a("DragonFight", this.C);
      if (nbttagcompound1 != null) {
         nbttagcompound.a("Player", nbttagcompound1);
      }

      DataResult<NBTBase> dataresult1 = WorldDataConfiguration.b.encodeStart(DynamicOpsNBT.a, this.f.g());
      dataresult1.get()
         .ifLeft(nbtbase -> nbttagcompound.a((NBTTagCompound)nbtbase))
         .ifRight(partialresult -> e.warn("Failed to encode configuration {}", partialresult.message()));
      if (this.D != null) {
         nbttagcompound.a("CustomBossEvents", this.D);
      }

      nbttagcompound.a("ScheduledEvents", this.J.b());
      nbttagcompound.a("WanderingTraderSpawnDelay", this.E);
      nbttagcompound.a("WanderingTraderSpawnChance", this.F);
      if (this.G != null) {
         nbttagcompound.a("WanderingTraderId", this.G);
      }

      nbttagcompound.a("Bukkit.Version", Bukkit.getName() + "/" + Bukkit.getVersion() + "/" + Bukkit.getBukkitVersion());
      this.world.getWorld().storeBukkitValues(nbttagcompound);
   }

   @Override
   public int a() {
      return this.j;
   }

   @Override
   public int b() {
      return this.k;
   }

   @Override
   public int c() {
      return this.l;
   }

   @Override
   public float d() {
      return this.m;
   }

   @Override
   public long e() {
      return this.n;
   }

   @Override
   public long f() {
      return this.o;
   }

   private void M() {
      if (!this.r && this.s != null) {
         if (this.q < SharedConstants.b().d().c()) {
            if (this.p == null) {
               throw (NullPointerException)SystemUtils.b(new NullPointerException("Fixer Upper not set inside LevelData, and the player tag is not upgraded."));
            }

            this.s = DataFixTypes.b.a(this.p, this.s, this.q);
         }

         this.r = true;
      }
   }

   @Override
   public NBTTagCompound y() {
      this.M();
      return this.s;
   }

   @Override
   public void b(int i) {
      this.j = i;
   }

   @Override
   public void c(int i) {
      this.k = i;
   }

   @Override
   public void d(int i) {
      this.l = i;
   }

   @Override
   public void a(float f) {
      this.m = f;
   }

   @Override
   public void a(long i) {
      this.n = i;
   }

   @Override
   public void b(long i) {
      this.o = i;
   }

   @Override
   public void a(BlockPosition blockposition, float f) {
      this.j = blockposition.u();
      this.k = blockposition.v();
      this.l = blockposition.w();
      this.m = f;
   }

   @Override
   public String g() {
      return this.f.a();
   }

   @Override
   public int z() {
      return this.t;
   }

   @Override
   public int h() {
      return this.u;
   }

   @Override
   public void a(int i) {
      this.u = i;
   }

   @Override
   public boolean i() {
      return this.x;
   }

   @Override
   public void a(boolean flag) {
      if (this.x != flag) {
         World world = Bukkit.getWorld(this.g());
         if (world != null) {
            ThunderChangeEvent thunder = new ThunderChangeEvent(world, flag);
            Bukkit.getServer().getPluginManager().callEvent(thunder);
            if (thunder.isCancelled()) {
               return;
            }
         }

         this.x = flag;
      }
   }

   @Override
   public int j() {
      return this.y;
   }

   @Override
   public void e(int i) {
      this.y = i;
   }

   @Override
   public boolean k() {
      return this.v;
   }

   @Override
   public void b(boolean flag) {
      if (this.v != flag) {
         World world = Bukkit.getWorld(this.g());
         if (world != null) {
            WeatherChangeEvent weather = new WeatherChangeEvent(world, flag);
            Bukkit.getServer().getPluginManager().callEvent(weather);
            if (weather.isCancelled()) {
               return;
            }
         }

         this.v = flag;
      }
   }

   @Override
   public int l() {
      return this.w;
   }

   @Override
   public void f(int i) {
      this.w = i;
   }

   @Override
   public EnumGamemode m() {
      return this.f.b();
   }

   @Override
   public void a(EnumGamemode enumgamemode) {
      this.f = this.f.a(enumgamemode);
   }

   @Override
   public boolean n() {
      return this.f.c();
   }

   @Override
   public boolean o() {
      return this.f.e();
   }

   @Override
   public boolean p() {
      return this.z;
   }

   @Override
   public void c(boolean flag) {
      this.z = flag;
   }

   @Override
   public GameRules q() {
      return this.f.f();
   }

   @Override
   public WorldBorder.c r() {
      return this.B;
   }

   @Override
   public void a(WorldBorder.c worldborder_c) {
      this.B = worldborder_c;
   }

   @Override
   public EnumDifficulty s() {
      return this.f.d();
   }

   @Override
   public void a(EnumDifficulty enumdifficulty) {
      this.f = this.f.a(enumdifficulty);
      PacketPlayOutServerDifficulty packet = new PacketPlayOutServerDifficulty(this.s(), this.t());

      for(EntityPlayer player : this.world.v()) {
         player.b.a(packet);
      }
   }

   @Override
   public boolean t() {
      return this.A;
   }

   @Override
   public void d(boolean flag) {
      this.A = flag;
   }

   @Override
   public CustomFunctionCallbackTimerQueue<MinecraftServer> u() {
      return this.J;
   }

   @Override
   public void a(CrashReportSystemDetails crashreportsystemdetails, LevelHeightAccessor levelheightaccessor) {
      IWorldDataServer.super.a(crashreportsystemdetails, levelheightaccessor);
      SaveData.super.a(crashreportsystemdetails);
   }

   @Override
   public WorldOptions A() {
      return this.g;
   }

   @Override
   public boolean B() {
      return this.h == WorldDataServer.a.b;
   }

   @Override
   public boolean C() {
      return this.h == WorldDataServer.a.c;
   }

   @Override
   public Lifecycle D() {
      return this.i;
   }

   @Override
   public NBTTagCompound E() {
      return this.C;
   }

   @Override
   public void a(NBTTagCompound nbttagcompound) {
      this.C = nbttagcompound;
   }

   @Override
   public WorldDataConfiguration F() {
      return this.f.g();
   }

   @Override
   public void a(WorldDataConfiguration worlddataconfiguration) {
      this.f = this.f.a(worlddataconfiguration);
   }

   @Nullable
   @Override
   public NBTTagCompound G() {
      return this.D;
   }

   @Override
   public void b(@Nullable NBTTagCompound nbttagcompound) {
      this.D = nbttagcompound;
   }

   @Override
   public int v() {
      return this.E;
   }

   @Override
   public void g(int i) {
      this.E = i;
   }

   @Override
   public int w() {
      return this.F;
   }

   @Override
   public void h(int i) {
      this.F = i;
   }

   @Nullable
   @Override
   public UUID x() {
      return this.G;
   }

   @Override
   public void a(UUID uuid) {
      this.G = uuid;
   }

   @Override
   public void a(String s, boolean flag) {
      this.H.add(s);
      this.I |= flag;
   }

   @Override
   public boolean H() {
      return this.I;
   }

   @Override
   public Set<String> I() {
      return ImmutableSet.copyOf(this.H);
   }

   @Override
   public IWorldDataServer J() {
      return this;
   }

   @Override
   public WorldSettings K() {
      return this.f.h();
   }

   public void checkName(String name) {
      if (!this.f.a.equals(name)) {
         this.f.a = name;
      }
   }

   @Deprecated
   public static enum a {
      a,
      b,
      c;
   }
}
