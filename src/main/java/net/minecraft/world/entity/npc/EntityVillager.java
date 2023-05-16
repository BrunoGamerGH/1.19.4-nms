package net.minecraft.world.entity.npc;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.Particles;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.DynamicOpsNBT;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.protocol.game.PacketDebug;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.network.syncher.DataWatcherObject;
import net.minecraft.network.syncher.DataWatcherRegistry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.stats.StatisticList;
import net.minecraft.util.MathHelper;
import net.minecraft.util.SpawnUtil;
import net.minecraft.world.DifficultyDamageScaler;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.InventorySubcontainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityAgeable;
import net.minecraft.world.entity.EntityExperienceOrb;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityLightning;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumMobSpawn;
import net.minecraft.world.entity.GroupDataEntity;
import net.minecraft.world.entity.ReputationHandler;
import net.minecraft.world.entity.ai.BehaviorController;
import net.minecraft.world.entity.ai.attributes.AttributeProvider;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.entity.ai.behavior.Behaviors;
import net.minecraft.world.entity.ai.gossip.Reputation;
import net.minecraft.world.entity.ai.gossip.ReputationType;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;
import net.minecraft.world.entity.ai.navigation.Navigation;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorGolemLastSeen;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.ai.village.ReputationEvent;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.entity.ai.village.poi.VillagePlace;
import net.minecraft.world.entity.ai.village.poi.VillagePlaceType;
import net.minecraft.world.entity.item.EntityItem;
import net.minecraft.world.entity.monster.EntityWitch;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.entity.schedule.Schedule;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantRecipe;
import net.minecraft.world.item.trading.MerchantRecipeList;
import net.minecraft.world.level.World;
import net.minecraft.world.level.WorldAccess;
import net.minecraft.world.phys.AxisAlignedBB;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.bukkit.entity.Villager;
import org.bukkit.event.entity.VillagerReplenishTradeEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityPotionEffectEvent.Cause;
import org.bukkit.event.entity.EntityTransformEvent.TransformReason;
import org.slf4j.Logger;
import org.spigotmc.SpigotConfig;

public class EntityVillager extends EntityVillagerAbstract implements ReputationHandler, VillagerDataHolder {
   private static final Logger bY = LogUtils.getLogger();
   private static final DataWatcherObject<VillagerData> bZ = DataWatcher.a(EntityVillager.class, DataWatcherRegistry.t);
   public static final int bU = 12;
   public static final Map<Item, Integer> bV = ImmutableMap.of(Items.oF, 4, Items.ti, 1, Items.th, 1, Items.ul, 1);
   private static final int ca = 2;
   private static final Set<Item> cb = ImmutableSet.of(Items.oF, Items.ti, Items.th, Items.oE, Items.oD, Items.ul, new Item[]{Items.um});
   private static final int cc = 10;
   private static final int cd = 1200;
   private static final int ce = 24000;
   private static final int cf = 25;
   private static final int cg = 10;
   private static final int ch = 5;
   private static final long ci = 24000L;
   @VisibleForTesting
   public static final float bW = 0.5F;
   private int cj;
   private boolean ck;
   @Nullable
   private EntityHuman cl;
   private boolean cm;
   private int cn;
   private final Reputation co = new Reputation();
   private long cp;
   private long cq;
   private int cr;
   private long cs;
   private int ct;
   private long cu;
   private boolean cv;
   private static final ImmutableList<MemoryModuleType<?>> cw = ImmutableList.of(
      MemoryModuleType.b,
      MemoryModuleType.c,
      MemoryModuleType.d,
      MemoryModuleType.e,
      MemoryModuleType.g,
      MemoryModuleType.h,
      MemoryModuleType.i,
      MemoryModuleType.j,
      MemoryModuleType.k,
      MemoryModuleType.l,
      MemoryModuleType.K,
      MemoryModuleType.aO,
      new MemoryModuleType[]{
         MemoryModuleType.m,
         MemoryModuleType.n,
         MemoryModuleType.q,
         MemoryModuleType.r,
         MemoryModuleType.t,
         MemoryModuleType.v,
         MemoryModuleType.w,
         MemoryModuleType.x,
         MemoryModuleType.y,
         MemoryModuleType.A,
         MemoryModuleType.f,
         MemoryModuleType.C,
         MemoryModuleType.D,
         MemoryModuleType.E,
         MemoryModuleType.G,
         MemoryModuleType.H,
         MemoryModuleType.I,
         MemoryModuleType.F
      }
   );
   private static final ImmutableList<SensorType<? extends Sensor<? super EntityVillager>>> cx = ImmutableList.of(
      SensorType.c, SensorType.d, SensorType.b, SensorType.e, SensorType.f, SensorType.g, SensorType.h, SensorType.i, SensorType.j
   );
   public static final Map<MemoryModuleType<GlobalPos>, BiPredicate<EntityVillager, Holder<VillagePlaceType>>> bX = ImmutableMap.of(
      MemoryModuleType.b,
      (BiPredicate<EntityVillager, Holder>)(entityvillager, holder) -> holder.a(PoiTypes.n),
      MemoryModuleType.c,
      (BiPredicate<EntityVillager, Holder>)(entityvillager, holder) -> entityvillager.gd().b().b().test(holder),
      MemoryModuleType.d,
      (BiPredicate<EntityVillager, Holder>)(entityvillager, holder) -> VillagerProfession.a.test(holder),
      MemoryModuleType.e,
      (BiPredicate<EntityVillager, Holder>)(entityvillager, holder) -> holder.a(PoiTypes.o)
   );

   public EntityVillager(EntityTypes<? extends EntityVillager> entitytypes, World world) {
      this(entitytypes, world, VillagerType.c);
   }

   public EntityVillager(EntityTypes<? extends EntityVillager> entitytypes, World world, VillagerType villagertype) {
      super(entitytypes, world);
      ((Navigation)this.G()).b(true);
      this.G().a(true);
      this.s(true);
      this.a(this.gd().a(villagertype).a(VillagerProfession.b));
   }

   @Override
   public BehaviorController<EntityVillager> dH() {
      return super.dH();
   }

   @Override
   protected BehaviorController.b<EntityVillager> dI() {
      return BehaviorController.a(cw, cx);
   }

   @Override
   protected BehaviorController<?> a(Dynamic<?> dynamic) {
      BehaviorController<EntityVillager> behaviorcontroller = this.dI().a(dynamic);
      this.a(behaviorcontroller);
      return behaviorcontroller;
   }

   public void c(WorldServer worldserver) {
      BehaviorController<EntityVillager> behaviorcontroller = this.dH();
      behaviorcontroller.b(worldserver, this);
      this.by = behaviorcontroller.i();
      this.a(this.dH());
   }

   private void a(BehaviorController<EntityVillager> behaviorcontroller) {
      VillagerProfession villagerprofession = this.gd().b();
      if (this.y_()) {
         behaviorcontroller.a(Schedule.e);
         behaviorcontroller.a(Activity.d, Behaviors.a(0.5F));
      } else {
         behaviorcontroller.a(Schedule.f);
         behaviorcontroller.a(Activity.c, Behaviors.b(villagerprofession, 0.5F), ImmutableSet.of(Pair.of(MemoryModuleType.c, MemoryStatus.a)));
      }

      behaviorcontroller.a(Activity.a, Behaviors.a(villagerprofession, 0.5F));
      behaviorcontroller.a(Activity.f, Behaviors.d(villagerprofession, 0.5F), ImmutableSet.of(Pair.of(MemoryModuleType.e, MemoryStatus.a)));
      behaviorcontroller.a(Activity.e, Behaviors.c(villagerprofession, 0.5F));
      behaviorcontroller.a(Activity.b, Behaviors.e(villagerprofession, 0.5F));
      behaviorcontroller.a(Activity.g, Behaviors.f(villagerprofession, 0.5F));
      behaviorcontroller.a(Activity.i, Behaviors.g(villagerprofession, 0.5F));
      behaviorcontroller.a(Activity.h, Behaviors.h(villagerprofession, 0.5F));
      behaviorcontroller.a(Activity.j, Behaviors.i(villagerprofession, 0.5F));
      behaviorcontroller.a(ImmutableSet.of(Activity.a));
      behaviorcontroller.b(Activity.b);
      behaviorcontroller.a(Activity.b);
      behaviorcontroller.a(this.H.V(), this.H.U());
   }

   @Override
   protected void m() {
      super.m();
      if (this.H instanceof WorldServer) {
         this.c((WorldServer)this.H);
      }
   }

   public static AttributeProvider.Builder gb() {
      return EntityInsentient.y().a(GenericAttributes.d, 0.5).a(GenericAttributes.b, 48.0);
   }

   public boolean gc() {
      return this.cv;
   }

   @Override
   public void inactiveTick() {
      if (this.H.spigotConfig.tickInactiveVillagers && this.cU()) {
         this.U();
      }

      super.inactiveTick();
   }

   @Override
   protected void U() {
      this.H.ac().a("villagerBrain");
      this.dH().a((WorldServer)this.H, this);
      this.H.ac().c();
      if (this.cv) {
         this.cv = false;
      }

      if (!this.fT() && this.cj > 0) {
         --this.cj;
         if (this.cj <= 0) {
            if (this.ck) {
               this.gz();
               this.ck = false;
            }

            this.addEffect(new MobEffect(MobEffects.j, 200, 0), Cause.VILLAGER_TRADE);
         }
      }

      if (this.cl != null && this.H instanceof WorldServer) {
         ((WorldServer)this.H).a(ReputationEvent.e, this.cl, this);
         this.H.a(this, (byte)14);
         this.cl = null;
      }

      if (!this.fK() && this.af.a(100) == 0) {
         Raid raid = ((WorldServer)this.H).c(this.dg());
         if (raid != null && raid.v() && !raid.a()) {
            this.H.a(this, (byte)42);
         }
      }

      if (this.gd().b() == VillagerProfession.b && this.fT()) {
         this.fY();
      }

      super.U();
   }

   @Override
   public void l() {
      super.l();
      if (this.q() > 0) {
         this.r(this.q() - 1);
      }

      this.gB();
   }

   @Override
   public EnumInteractionResult b(EntityHuman entityhuman, EnumHand enumhand) {
      ItemStack itemstack = entityhuman.b(enumhand);
      if (itemstack.a(Items.sN) || !this.bq() || this.fT() || this.fu()) {
         return super.b(entityhuman, enumhand);
      } else if (this.y_()) {
         this.go();
         return EnumInteractionResult.a(this.H.B);
      } else {
         boolean flag = this.fU().isEmpty();
         if (enumhand == EnumHand.a) {
            if (flag && !this.H.B) {
               this.go();
            }

            entityhuman.a(StatisticList.S);
         }

         if (flag) {
            return EnumInteractionResult.a(this.H.B);
         } else {
            if (!this.H.B && !this.bT.isEmpty()) {
               this.g(entityhuman);
            }

            return EnumInteractionResult.a(this.H.B);
         }
      }
   }

   public void go() {
      this.r(40);
      if (!this.H.k_()) {
         this.a(SoundEffects.ym, this.eN(), this.eO());
      }
   }

   private void g(EntityHuman entityhuman) {
      this.h(entityhuman);
      this.e(entityhuman);
      this.a(entityhuman, this.G_(), this.gd().c());
   }

   @Override
   public void e(@Nullable EntityHuman entityhuman) {
      boolean flag = this.fS() != null && entityhuman == null;
      super.e(entityhuman);
      if (flag) {
         this.fY();
      }
   }

   @Override
   protected void fY() {
      super.fY();
      this.gp();
   }

   private void gp() {
      for(MerchantRecipe merchantrecipe : this.fU()) {
         merchantrecipe.l();
      }
   }

   @Override
   public boolean ge() {
      return true;
   }

   @Override
   public boolean ga() {
      return this.Y().B;
   }

   public void gf() {
      this.gu();

      for(MerchantRecipe merchantrecipe : this.fU()) {
         VillagerReplenishTradeEvent event = new VillagerReplenishTradeEvent((Villager)this.getBukkitEntity(), merchantrecipe.asBukkit());
         Bukkit.getPluginManager().callEvent(event);
         if (!event.isCancelled()) {
            merchantrecipe.h();
         }
      }

      this.gq();
      this.cs = this.H.U();
      ++this.ct;
   }

   private void gq() {
      MerchantRecipeList merchantrecipelist = this.fU();
      EntityHuman entityhuman = this.fS();
      if (entityhuman != null && !merchantrecipelist.isEmpty()) {
         entityhuman.a(entityhuman.bP.j, merchantrecipelist, this.gd().c(), this.r(), this.fV(), this.ge());
      }
   }

   private boolean gr() {
      for(MerchantRecipe merchantrecipe : this.fU()) {
         if (merchantrecipe.r()) {
            return true;
         }
      }

      return false;
   }

   private boolean gs() {
      return this.ct == 0 || this.ct < 2 && this.H.U() > this.cs + 2400L;
   }

   public boolean gg() {
      long i = this.cs + 12000L;
      long j = this.H.U();
      boolean flag = j > i;
      long k = this.H.V();
      if (this.cu > 0L) {
         long l = this.cu / 24000L;
         long i1 = k / 24000L;
         flag |= i1 > l;
      }

      this.cu = k;
      if (flag) {
         this.cs = j;
         this.gC();
      }

      return this.gs() && this.gr();
   }

   private void gt() {
      int i = 2 - this.ct;
      if (i > 0) {
         for(MerchantRecipe merchantrecipe : this.fU()) {
            VillagerReplenishTradeEvent event = new VillagerReplenishTradeEvent((Villager)this.getBukkitEntity(), merchantrecipe.asBukkit());
            Bukkit.getPluginManager().callEvent(event);
            if (!event.isCancelled()) {
               merchantrecipe.h();
            }
         }
      }

      for(int j = 0; j < i; ++j) {
         this.gu();
      }

      this.gq();
   }

   private void gu() {
      for(MerchantRecipe merchantrecipe : this.fU()) {
         merchantrecipe.e();
      }
   }

   private void h(EntityHuman entityhuman) {
      int i = this.f(entityhuman);
      if (i != 0) {
         for(MerchantRecipe merchantrecipe : this.fU()) {
            merchantrecipe.a(-MathHelper.d((float)i * merchantrecipe.n()));
         }
      }

      if (entityhuman.a(MobEffects.F)) {
         MobEffect mobeffect = entityhuman.b(MobEffects.F);
         int j = mobeffect.e();

         for(MerchantRecipe merchantrecipe1 : this.fU()) {
            double d0 = 0.3 + 0.0625 * (double)j;
            int k = (int)Math.floor(d0 * (double)merchantrecipe1.a().K());
            merchantrecipe1.a(-Math.max(k, 1));
         }
      }
   }

   @Override
   protected void a_() {
      super.a_();
      this.am.a(bZ, new VillagerData(VillagerType.c, VillagerProfession.b, 1));
   }

   @Override
   public void b(NBTTagCompound nbttagcompound) {
      super.b(nbttagcompound);
      DataResult<NBTBase> dataresult = VillagerData.c.encodeStart(DynamicOpsNBT.a, this.gd());
      Logger logger = bY;
      dataresult.resultOrPartial(logger::error).ifPresent(nbtbase -> nbttagcompound.a("VillagerData", nbtbase));
      nbttagcompound.a("FoodLevel", (byte)this.cn);
      nbttagcompound.a("Gossips", this.co.a(DynamicOpsNBT.a));
      nbttagcompound.a("Xp", this.cr);
      nbttagcompound.a("LastRestock", this.cs);
      nbttagcompound.a("LastGossipDecay", this.cq);
      nbttagcompound.a("RestocksToday", this.ct);
      if (this.cv) {
         nbttagcompound.a("AssignProfessionWhenSpawned", true);
      }
   }

   @Override
   public void a(NBTTagCompound nbttagcompound) {
      super.a(nbttagcompound);
      if (nbttagcompound.b("VillagerData", 10)) {
         DataResult<VillagerData> dataresult = VillagerData.c.parse(new Dynamic(DynamicOpsNBT.a, nbttagcompound.c("VillagerData")));
         Logger logger = bY;
         dataresult.resultOrPartial(logger::error).ifPresent(this::a);
      }

      if (nbttagcompound.b("Offers", 10)) {
         this.bT = new MerchantRecipeList(nbttagcompound.p("Offers"));
      }

      if (nbttagcompound.b("FoodLevel", 1)) {
         this.cn = nbttagcompound.f("FoodLevel");
      }

      NBTTagList nbttaglist = nbttagcompound.c("Gossips", 10);
      this.co.a(new Dynamic(DynamicOpsNBT.a, nbttaglist));
      if (nbttagcompound.b("Xp", 3)) {
         this.cr = nbttagcompound.h("Xp");
      }

      this.cs = nbttagcompound.i("LastRestock");
      this.cq = nbttagcompound.i("LastGossipDecay");
      this.s(true);
      if (this.H instanceof WorldServer) {
         this.c((WorldServer)this.H);
      }

      this.ct = nbttagcompound.h("RestocksToday");
      if (nbttagcompound.e("AssignProfessionWhenSpawned")) {
         this.cv = nbttagcompound.q("AssignProfessionWhenSpawned");
      }
   }

   @Override
   public boolean h(double d0) {
      return false;
   }

   @Nullable
   @Override
   protected SoundEffect s() {
      return this.fu() ? null : (this.fT() ? SoundEffects.yn : SoundEffects.yi);
   }

   @Override
   protected SoundEffect d(DamageSource damagesource) {
      return SoundEffects.yl;
   }

   @Override
   protected SoundEffect x_() {
      return SoundEffects.yk;
   }

   public void gh() {
      SoundEffect soundeffect = this.gd().b().f();
      if (soundeffect != null) {
         this.a(soundeffect, this.eN(), this.eO());
      }
   }

   @Override
   public void a(VillagerData villagerdata) {
      VillagerData villagerdata1 = this.gd();
      if (villagerdata1.b() != villagerdata.b()) {
         this.bT = null;
      }

      this.am.b(bZ, villagerdata);
   }

   @Override
   public VillagerData gd() {
      return this.am.a(bZ);
   }

   @Override
   protected void b(MerchantRecipe merchantrecipe) {
      int i = 3 + this.af.a(4);
      this.cr += merchantrecipe.o();
      this.cl = this.fS();
      if (this.gy()) {
         this.cj = 40;
         this.ck = true;
         i += 5;
      }

      if (merchantrecipe.s()) {
         this.H.b(new EntityExperienceOrb(this.H, this.dl(), this.dn() + 0.5, this.dr(), i));
      }
   }

   public void x(boolean flag) {
      this.cm = flag;
   }

   public boolean gi() {
      return this.cm;
   }

   @Override
   public void a(@Nullable EntityLiving entityliving) {
      if (entityliving != null && this.H instanceof WorldServer) {
         ((WorldServer)this.H).a(ReputationEvent.c, entityliving, this);
         if (this.bq() && entityliving instanceof EntityHuman) {
            this.H.a(this, (byte)13);
         }
      }

      super.a(entityliving);
   }

   @Override
   public void a(DamageSource damagesource) {
      if (SpigotConfig.logVillagerDeaths) {
         bY.info("Villager {} died, message: '{}'", this, damagesource.a(this).getString());
      }

      Entity entity = damagesource.d();
      if (entity != null) {
         this.a(entity);
      }

      this.gv();
      super.a(damagesource);
   }

   public void gv() {
      this.a(MemoryModuleType.b);
      this.a(MemoryModuleType.c);
      this.a(MemoryModuleType.d);
      this.a(MemoryModuleType.e);
   }

   private void a(Entity entity) {
      World world = this.H;
      if (world instanceof WorldServer worldserver) {
         Optional optional = this.by.c(MemoryModuleType.h);
         if (!optional.isEmpty()) {
            NearestVisibleLivingEntities nearestvisiblelivingentities = (NearestVisibleLivingEntities)optional.get();
            nearestvisiblelivingentities.b(ReputationHandler.class::isInstance)
               .forEach(entityliving -> worldserver.a(ReputationEvent.d, entity, (ReputationHandler)entityliving));
         }
      }
   }

   public void a(MemoryModuleType<GlobalPos> memorymoduletype) {
      if (this.H instanceof WorldServer) {
         MinecraftServer minecraftserver = ((WorldServer)this.H).n();
         this.by.c(memorymoduletype).ifPresent(globalpos -> {
            WorldServer worldserver = minecraftserver.a(globalpos.a());
            if (worldserver != null) {
               VillagePlace villageplace = worldserver.w();
               Optional<Holder<VillagePlaceType>> optional = villageplace.c(globalpos.b());
               BiPredicate<EntityVillager, Holder<VillagePlaceType>> bipredicate = bX.get(memorymoduletype);
               if (optional.isPresent() && bipredicate.test(this, optional.get())) {
                  villageplace.b(globalpos.b());
                  PacketDebug.c(worldserver, globalpos.b());
               }
            }
         });
      }
   }

   @Override
   public boolean O_() {
      return this.cn + this.gA() >= 12 && !this.fu() && this.h() == 0;
   }

   private boolean gw() {
      return this.cn < 12;
   }

   private void gx() {
      if (this.gw() && this.gA() != 0) {
         for(int i = 0; i < this.w().b(); ++i) {
            ItemStack itemstack = this.w().a(i);
            if (!itemstack.b()) {
               Integer integer = bV.get(itemstack.c());
               if (integer != null) {
                  int j = itemstack.K();

                  for(int k = j; k > 0; --k) {
                     this.cn += integer;
                     this.w().a(i, 1);
                     if (!this.gw()) {
                        return;
                     }
                  }
               }
            }
         }
      }
   }

   public int f(EntityHuman entityhuman) {
      return this.co.a(entityhuman.cs(), reputationtype -> true);
   }

   private void u(int i) {
      this.cn -= i;
   }

   public void gj() {
      this.gx();
      this.u(12);
   }

   public void b(MerchantRecipeList merchantrecipelist) {
      this.bT = merchantrecipelist;
   }

   private boolean gy() {
      int i = this.gd().c();
      return VillagerData.d(i) && this.cr >= VillagerData.c(i);
   }

   public void gz() {
      this.a(this.gd().a(this.gd().c() + 1));
      this.fZ();
   }

   @Override
   protected IChatBaseComponent cj() {
      String s = this.ae().g();
      return IChatBaseComponent.c(s + "." + BuiltInRegistries.z.b(this.gd().b()).a());
   }

   @Override
   public void b(byte b0) {
      if (b0 == 12) {
         this.a(Particles.O);
      } else if (b0 == 13) {
         this.a(Particles.b);
      } else if (b0 == 14) {
         this.a(Particles.M);
      } else if (b0 == 42) {
         this.a(Particles.ai);
      } else {
         super.b(b0);
      }
   }

   @Nullable
   @Override
   public GroupDataEntity a(
      WorldAccess worldaccess,
      DifficultyDamageScaler difficultydamagescaler,
      EnumMobSpawn enummobspawn,
      @Nullable GroupDataEntity groupdataentity,
      @Nullable NBTTagCompound nbttagcompound
   ) {
      if (enummobspawn == EnumMobSpawn.e) {
         this.a(this.gd().a(VillagerProfession.b));
      }

      if (enummobspawn == EnumMobSpawn.n || enummobspawn == EnumMobSpawn.m || enummobspawn == EnumMobSpawn.c || enummobspawn == EnumMobSpawn.o) {
         this.a(this.gd().a(VillagerType.a(worldaccess.v(this.dg()))));
      }

      if (enummobspawn == EnumMobSpawn.d) {
         this.cv = true;
      }

      return super.a(worldaccess, difficultydamagescaler, enummobspawn, groupdataentity, nbttagcompound);
   }

   @Nullable
   public EntityVillager b(WorldServer worldserver, EntityAgeable entityageable) {
      double d0 = this.af.j();
      VillagerType villagertype;
      if (d0 < 0.5) {
         villagertype = VillagerType.a(worldserver.v(this.dg()));
      } else if (d0 < 0.75) {
         villagertype = this.gd().a();
      } else {
         villagertype = ((EntityVillager)entityageable).gd().a();
      }

      EntityVillager entityvillager = new EntityVillager(EntityTypes.bf, worldserver, villagertype);
      entityvillager.a(worldserver, worldserver.d_(entityvillager.dg()), EnumMobSpawn.e, null, null);
      return entityvillager;
   }

   @Override
   public void a(WorldServer worldserver, EntityLightning entitylightning) {
      if (worldserver.ah() != EnumDifficulty.a) {
         bY.info("Villager {} was struck by lightning {}.", this, entitylightning);
         EntityWitch entitywitch = EntityTypes.bj.a((World)worldserver);
         if (entitywitch != null) {
            entitywitch.b(this.dl(), this.dn(), this.dr(), this.dw(), this.dy());
            entitywitch.a(worldserver, worldserver.d_(entitywitch.dg()), EnumMobSpawn.i, null, null);
            entitywitch.t(this.fK());
            if (this.aa()) {
               entitywitch.b(this.ab());
               entitywitch.n(this.cx());
            }

            entitywitch.fz();
            if (CraftEventFactory.callEntityTransformEvent(this, entitywitch, TransformReason.LIGHTNING).isCancelled()) {
               return;
            }

            worldserver.addFreshEntityWithPassengers(entitywitch, SpawnReason.LIGHTNING);
            this.gv();
            this.ai();
         } else {
            super.a(worldserver, entitylightning);
         }
      } else {
         super.a(worldserver, entitylightning);
      }
   }

   @Override
   protected void b(EntityItem entityitem) {
      InventoryCarrier.a(this, this, entityitem);
   }

   @Override
   public boolean k(ItemStack itemstack) {
      Item item = itemstack.c();
      return (cb.contains(item) || this.gd().b().d().contains(item)) && this.w().b(itemstack);
   }

   public boolean gk() {
      return this.gA() >= 24;
   }

   public boolean gl() {
      return this.gA() < 12;
   }

   private int gA() {
      InventorySubcontainer inventorysubcontainer = this.w();
      return bV.entrySet().stream().mapToInt(entry -> inventorysubcontainer.a_(entry.getKey()) * entry.getValue()).sum();
   }

   public boolean gm() {
      return this.w().a(ImmutableSet.of(Items.oD, Items.ti, Items.th, Items.um));
   }

   @Override
   protected void fZ() {
      VillagerData villagerdata = this.gd();
      Int2ObjectMap<VillagerTrades.IMerchantRecipeOption[]> int2objectmap = (Int2ObjectMap)VillagerTrades.a.get(villagerdata.b());
      if (int2objectmap != null && !int2objectmap.isEmpty()) {
         VillagerTrades.IMerchantRecipeOption[] avillagertrades_imerchantrecipeoption = (VillagerTrades.IMerchantRecipeOption[])int2objectmap.get(
            villagerdata.c()
         );
         if (avillagertrades_imerchantrecipeoption != null) {
            MerchantRecipeList merchantrecipelist = this.fU();
            this.a(merchantrecipelist, avillagertrades_imerchantrecipeoption, 2);
         }
      }
   }

   public void a(WorldServer worldserver, EntityVillager entityvillager, long i) {
      if ((i < this.cp || i >= this.cp + 1200L) && (i < entityvillager.cp || i >= entityvillager.cp + 1200L)) {
         this.co.a(entityvillager.co, this.af, 10);
         this.cp = i;
         entityvillager.cp = i;
         this.a(worldserver, i, 5);
      }
   }

   private void gB() {
      long i = this.H.U();
      if (this.cq == 0L) {
         this.cq = i;
      } else if (i >= this.cq + 24000L) {
         this.co.b();
         this.cq = i;
      }
   }

   public void a(WorldServer worldserver, long i, int j) {
      if (this.a(i)) {
         AxisAlignedBB axisalignedbb = this.cD().c(10.0, 10.0, 10.0);
         List<EntityVillager> list = worldserver.a(EntityVillager.class, axisalignedbb);
         List<EntityVillager> list1 = list.stream().filter(entityvillager -> entityvillager.a(i)).limit(5L).collect(Collectors.toList());
         if (list1.size() >= j
            && SpawnUtil.trySpawnMob(EntityTypes.ac, EnumMobSpawn.f, worldserver, this.dg(), 10, 8, 6, SpawnUtil.a.a, SpawnReason.VILLAGE_DEFENSE).isPresent()
            )
          {
            list.forEach(SensorGolemLastSeen::b);
         }
      }
   }

   public boolean a(long i) {
      return !this.b(this.H.U()) ? false : !this.by.a(MemoryModuleType.F);
   }

   @Override
   public void a(ReputationEvent reputationevent, Entity entity) {
      if (reputationevent == ReputationEvent.a) {
         this.co.a(entity.cs(), ReputationType.d, 20);
         this.co.a(entity.cs(), ReputationType.c, 25);
      } else if (reputationevent == ReputationEvent.e) {
         this.co.a(entity.cs(), ReputationType.e, 2);
      } else if (reputationevent == ReputationEvent.c) {
         this.co.a(entity.cs(), ReputationType.b, 25);
      } else if (reputationevent == ReputationEvent.d) {
         this.co.a(entity.cs(), ReputationType.a, 25);
      }
   }

   @Override
   public int r() {
      return this.cr;
   }

   public void t(int i) {
      this.cr = i;
   }

   private void gC() {
      this.gt();
      this.ct = 0;
   }

   public Reputation gn() {
      return this.co;
   }

   public void a(NBTBase nbtbase) {
      this.co.a(new Dynamic(DynamicOpsNBT.a, nbtbase));
   }

   @Override
   protected void T() {
      super.T();
      PacketDebug.a(this);
   }

   @Override
   public void b(BlockPosition blockposition) {
      super.b(blockposition);
      this.by.a(MemoryModuleType.G, this.H.U());
      this.by.b(MemoryModuleType.m);
      this.by.b(MemoryModuleType.E);
   }

   @Override
   public void fv() {
      super.fv();
      this.by.a(MemoryModuleType.H, this.H.U());
   }

   private boolean b(long i) {
      Optional<Long> optional = this.by.c(MemoryModuleType.G);
      return optional.isPresent() ? i - optional.get() < 24000L : false;
   }
}
