package net.minecraft.world.entity.animal.axolotl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Dynamic;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.IntFunction;
import javax.annotation.Nullable;
import net.minecraft.SystemUtils;
import net.minecraft.core.BlockPosition;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.protocol.game.PacketDebug;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.network.syncher.DataWatcherObject;
import net.minecraft.network.syncher.DataWatcherRegistry;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.tags.TagsBlock;
import net.minecraft.tags.TagsItem;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.INamable;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyDamageScaler;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityAgeable;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityPose;
import net.minecraft.world.entity.EntitySize;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumMobSpawn;
import net.minecraft.world.entity.EnumMonsterType;
import net.minecraft.world.entity.EnumMoveType;
import net.minecraft.world.entity.GroupDataEntity;
import net.minecraft.world.entity.LerpingModel;
import net.minecraft.world.entity.VariantHolder;
import net.minecraft.world.entity.ai.BehaviorController;
import net.minecraft.world.entity.ai.attributes.AttributeProvider;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.navigation.AmphibiousPathNavigation;
import net.minecraft.world.entity.ai.navigation.NavigationAbstract;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.animal.EntityAnimal;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.World;
import net.minecraft.world.level.WorldAccess;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.event.entity.EntityPotionEffectEvent.Cause;
import org.joml.Vector3f;

public class Axolotl extends EntityAnimal implements LerpingModel, VariantHolder<Axolotl.Variant>, Bucketable {
   public static final int bS = 200;
   protected static final ImmutableList<? extends SensorType<? extends Sensor<? super Axolotl>>> bT = ImmutableList.of(
      SensorType.c, SensorType.n, SensorType.f, SensorType.o, SensorType.p
   );
   protected static final ImmutableList<? extends MemoryModuleType<?>> bV = ImmutableList.of(
      MemoryModuleType.r,
      MemoryModuleType.g,
      MemoryModuleType.h,
      MemoryModuleType.k,
      MemoryModuleType.l,
      MemoryModuleType.n,
      MemoryModuleType.m,
      MemoryModuleType.E,
      MemoryModuleType.t,
      MemoryModuleType.o,
      MemoryModuleType.p,
      MemoryModuleType.J,
      new MemoryModuleType[]{
         MemoryModuleType.y,
         MemoryModuleType.M,
         MemoryModuleType.B,
         MemoryModuleType.N,
         MemoryModuleType.O,
         MemoryModuleType.Q,
         MemoryModuleType.T,
         MemoryModuleType.Y
      }
   );
   private static final DataWatcherObject<Integer> bZ = DataWatcher.a(Axolotl.class, DataWatcherRegistry.b);
   private static final DataWatcherObject<Boolean> ca = DataWatcher.a(Axolotl.class, DataWatcherRegistry.k);
   private static final DataWatcherObject<Boolean> cb = DataWatcher.a(Axolotl.class, DataWatcherRegistry.k);
   public static final double bW = 20.0;
   public static final int bX = 1200;
   private static final int cc = 6000;
   public static final String bY = "Variant";
   private static final int cd = 1800;
   private static final int ce = 2400;
   private final Map<String, Vector3f> cf = Maps.newHashMap();
   private static final int cg = 100;

   @Override
   public int getDefaultMaxAirSupply() {
      return 6000;
   }

   public Axolotl(EntityTypes<? extends Axolotl> entitytypes, World world) {
      super(entitytypes, world);
      this.a(PathType.j, 0.0F);
      this.bK = new Axolotl.c(this);
      this.bJ = new Axolotl.b(this, 20);
      this.v(1.0F);
   }

   @Override
   public Map<String, Vector3f> a() {
      return this.cf;
   }

   @Override
   public float a(BlockPosition blockposition, IWorldReader iworldreader) {
      return 0.0F;
   }

   @Override
   protected void a_() {
      super.a_();
      this.am.a(bZ, 0);
      this.am.a(ca, false);
      this.am.a(cb, false);
   }

   @Override
   public void b(NBTTagCompound nbttagcompound) {
      super.b(nbttagcompound);
      nbttagcompound.a("Variant", this.fS().a());
      nbttagcompound.a("FromBucket", this.r());
   }

   @Override
   public void a(NBTTagCompound nbttagcompound) {
      super.a(nbttagcompound);
      this.a(Axolotl.Variant.a(nbttagcompound.h("Variant")));
      this.w(nbttagcompound.q("FromBucket"));
   }

   @Override
   public void L() {
      if (!this.fY()) {
         super.L();
      }
   }

   @Override
   public GroupDataEntity a(
      WorldAccess worldaccess,
      DifficultyDamageScaler difficultydamagescaler,
      EnumMobSpawn enummobspawn,
      @Nullable GroupDataEntity groupdataentity,
      @Nullable NBTTagCompound nbttagcompound
   ) {
      boolean flag = false;
      if (enummobspawn == EnumMobSpawn.l) {
         return groupdataentity;
      } else {
         RandomSource randomsource = worldaccess.r_();
         if (groupdataentity instanceof Axolotl.a) {
            if (((Axolotl.a)groupdataentity).a() >= 2) {
               flag = true;
            }
         } else {
            groupdataentity = new Axolotl.a(Axolotl.Variant.a(randomsource), Axolotl.Variant.a(randomsource));
         }

         this.a(((Axolotl.a)groupdataentity).a(randomsource));
         if (flag) {
            this.c_(-24000);
         }

         return super.a(worldaccess, difficultydamagescaler, enummobspawn, groupdataentity, nbttagcompound);
      }
   }

   @Override
   public void ao() {
      int i = this.cd();
      super.ao();
      if (!this.fK()) {
         this.s(i);
      }
   }

   protected void s(int i) {
      if (this.bq() && !this.aV()) {
         this.i(i - 1);
         if (this.cd() == -20) {
            this.i(0);
            this.a(this.dG().r(), 2.0F);
         }
      } else {
         this.i(this.cc());
      }
   }

   @Override
   public void q() {
      int i = this.cd() + 1800;
      this.i(Math.min(i, this.cc()));
   }

   @Override
   public int cc() {
      return this.maxAirTicks;
   }

   public Axolotl.Variant fS() {
      return Axolotl.Variant.a(this.am.a(bZ));
   }

   public void a(Axolotl.Variant axolotl_variant) {
      this.am.b(bZ, axolotl_variant.a());
   }

   private static boolean a(RandomSource randomsource) {
      return randomsource.a(1200) == 0;
   }

   @Override
   public boolean a(IWorldReader iworldreader) {
      return iworldreader.f(this);
   }

   @Override
   public boolean dK() {
      return true;
   }

   @Override
   public boolean cv() {
      return false;
   }

   @Override
   public EnumMonsterType eJ() {
      return EnumMonsterType.e;
   }

   public void x(boolean flag) {
      this.am.b(ca, flag);
   }

   public boolean fY() {
      return this.am.a(ca);
   }

   @Override
   public boolean r() {
      return this.am.a(cb);
   }

   @Override
   public void w(boolean flag) {
      this.am.b(cb, flag);
   }

   @Nullable
   @Override
   public EntityAgeable a(WorldServer worldserver, EntityAgeable entityageable) {
      Axolotl axolotl = EntityTypes.f.a((World)worldserver);
      if (axolotl != null) {
         Axolotl.Variant axolotl_variant;
         if (a(this.af)) {
            axolotl_variant = Axolotl.Variant.b(this.af);
         } else {
            axolotl_variant = this.af.h() ? this.fS() : ((Axolotl)entityageable).fS();
         }

         axolotl.a(axolotl_variant);
         axolotl.fz();
      }

      return axolotl;
   }

   @Override
   public double j(EntityLiving entityliving) {
      return 1.5 + (double)entityliving.dc() * 2.0;
   }

   @Override
   public boolean m(ItemStack itemstack) {
      return itemstack.a(TagsItem.az);
   }

   @Override
   public boolean a(EntityHuman entityhuman) {
      return true;
   }

   @Override
   protected void U() {
      this.H.ac().a("axolotlBrain");
      this.dH().a((WorldServer)this.H, this);
      this.H.ac().c();
      this.H.ac().a("axolotlActivityUpdate");
      AxolotlAi.a(this);
      this.H.ac().c();
      if (!this.fK()) {
         Optional<Integer> optional = this.dH().c(MemoryModuleType.M);
         this.x(optional.isPresent() && optional.get() > 0);
      }
   }

   public static AttributeProvider.Builder fZ() {
      return EntityInsentient.y().a(GenericAttributes.a, 14.0).a(GenericAttributes.d, 1.0).a(GenericAttributes.f, 2.0);
   }

   @Override
   protected NavigationAbstract a(World world) {
      return new AmphibiousPathNavigation(this, world);
   }

   @Override
   public boolean z(Entity entity) {
      boolean flag = entity.a(this.dG().b((EntityLiving)this), (float)((int)this.b(GenericAttributes.f)));
      if (flag) {
         this.a(this, entity);
         this.a(SoundEffects.au, 1.0F, 1.0F);
      }

      return flag;
   }

   @Override
   public boolean a(DamageSource damagesource, float f) {
      float f1 = this.eo();
      if (!this.H.B
         && !this.fK()
         && this.H.z.a(3) == 0
         && ((float)this.H.z.a(3) < f || f1 / this.eE() < 0.5F)
         && f < f1
         && this.aT()
         && (damagesource.d() != null || damagesource.c() != null)
         && !this.fY()) {
         this.by.a(MemoryModuleType.M, 200);
      }

      return super.a(damagesource, f);
   }

   @Override
   protected float b(EntityPose entitypose, EntitySize entitysize) {
      return entitysize.b * 0.655F;
   }

   @Override
   public int V() {
      return 1;
   }

   @Override
   public int W() {
      return 1;
   }

   @Override
   public EnumInteractionResult b(EntityHuman entityhuman, EnumHand enumhand) {
      return Bucketable.a(entityhuman, enumhand, this).orElse(super.b(entityhuman, enumhand));
   }

   @Override
   public void l(ItemStack itemstack) {
      Bucketable.a(this, itemstack);
      NBTTagCompound nbttagcompound = itemstack.v();
      nbttagcompound.a("Variant", this.fS().a());
      nbttagcompound.a("Age", this.h());
      BehaviorController<?> behaviorcontroller = this.dH();
      if (behaviorcontroller.a(MemoryModuleType.T)) {
         nbttagcompound.a("HuntingCooldown", behaviorcontroller.e(MemoryModuleType.T));
      }
   }

   @Override
   public void c(NBTTagCompound nbttagcompound) {
      Bucketable.a(this, nbttagcompound);
      this.a(Axolotl.Variant.a(nbttagcompound.h("Variant")));
      if (nbttagcompound.e("Age")) {
         this.c_(nbttagcompound.h("Age"));
      }

      if (nbttagcompound.e("HuntingCooldown")) {
         this.dH().a(MemoryModuleType.T, true, nbttagcompound.i("HuntingCooldown"));
      }
   }

   @Override
   public ItemStack b() {
      return new ItemStack(Items.pR);
   }

   @Override
   public SoundEffect w() {
      return SoundEffects.cy;
   }

   @Override
   public boolean eh() {
      return !this.fY() && super.eh();
   }

   public static void a(Axolotl axolotl, EntityLiving entityliving) {
      World world = axolotl.H;
      if (entityliving.ep()) {
         DamageSource damagesource = entityliving.eq();
         if (damagesource != null) {
            Entity entity = damagesource.d();
            if (entity != null && entity.ae() == EntityTypes.bt) {
               EntityHuman entityhuman = (EntityHuman)entity;
               List<EntityHuman> list = world.a(EntityHuman.class, axolotl.cD().g(20.0));
               if (list.contains(entityhuman)) {
                  axolotl.e(entityhuman);
               }
            }
         }
      }
   }

   public void e(EntityHuman entityhuman) {
      MobEffect mobeffect = entityhuman.b(MobEffects.j);
      if (mobeffect == null || mobeffect.a(2399)) {
         int i = mobeffect != null ? mobeffect.d() : 0;
         int j = Math.min(2400, 100 + i);
         entityhuman.addEffect(new MobEffect(MobEffects.j, j, 0), this, Cause.AXOLOTL);
      }

      entityhuman.d(MobEffects.d);
   }

   @Override
   public boolean Q() {
      return super.Q() || this.r();
   }

   @Override
   protected SoundEffect d(DamageSource damagesource) {
      return SoundEffects.aw;
   }

   @Nullable
   @Override
   protected SoundEffect x_() {
      return SoundEffects.av;
   }

   @Nullable
   @Override
   protected SoundEffect s() {
      return this.aT() ? SoundEffects.ay : SoundEffects.ax;
   }

   @Override
   protected SoundEffect aJ() {
      return SoundEffects.az;
   }

   @Override
   protected SoundEffect aI() {
      return SoundEffects.aA;
   }

   @Override
   protected BehaviorController.b<Axolotl> dI() {
      return BehaviorController.a(bV, bT);
   }

   @Override
   protected BehaviorController<?> a(Dynamic<?> dynamic) {
      return AxolotlAi.a(this.dI().a(dynamic));
   }

   @Override
   public BehaviorController<Axolotl> dH() {
      return super.dH();
   }

   @Override
   protected void T() {
      super.T();
      PacketDebug.a(this);
   }

   @Override
   public void h(Vec3D vec3d) {
      if (this.cT() && this.aT()) {
         this.a(this.eW(), vec3d);
         this.a(EnumMoveType.a, this.dj());
         this.f(this.dj().a(0.9));
      } else {
         super.h(vec3d);
      }
   }

   @Override
   protected void a(EntityHuman entityhuman, EnumHand enumhand, ItemStack itemstack) {
      if (itemstack.a(Items.pQ)) {
         entityhuman.a(enumhand, new ItemStack(Items.pH));
      } else {
         super.a(entityhuman, enumhand, itemstack);
      }
   }

   @Override
   public boolean h(double d0) {
      return !this.r() && !this.aa();
   }

   public static boolean a(
      EntityTypes<? extends EntityLiving> entitytypes,
      WorldAccess worldaccess,
      EnumMobSpawn enummobspawn,
      BlockPosition blockposition,
      RandomSource randomsource
   ) {
      return worldaccess.a_(blockposition.d()).a(TagsBlock.bK);
   }

   public static enum Variant implements INamable {
      a(0, "lucy", true),
      b(1, "wild", true),
      c(2, "gold", true),
      d(3, "cyan", true),
      e(4, "blue", false);

      private static final IntFunction<Axolotl.Variant> g = ByIdMap.a(Axolotl.Variant::a, values(), ByIdMap.a.a);
      public static final Codec<Axolotl.Variant> f = INamable.a(Axolotl.Variant::values);
      private final int h;
      private final String i;
      private final boolean j;

      private Variant(int i, String s, boolean flag) {
         this.h = i;
         this.i = s;
         this.j = flag;
      }

      public int a() {
         return this.h;
      }

      public String b() {
         return this.i;
      }

      @Override
      public String c() {
         return this.i;
      }

      public static Axolotl.Variant a(int i) {
         return g.apply(i);
      }

      public static Axolotl.Variant a(RandomSource randomsource) {
         return a(randomsource, true);
      }

      public static Axolotl.Variant b(RandomSource randomsource) {
         return a(randomsource, false);
      }

      private static Axolotl.Variant a(RandomSource randomsource, boolean flag) {
         Axolotl.Variant[] aaxolotl_variant = Arrays.stream(values())
            .filter(axolotl_variant -> axolotl_variant.j == flag)
            .toArray(i -> new Axolotl.Variant[i]);
         return SystemUtils.a(aaxolotl_variant, randomsource);
      }
   }

   public static class a extends EntityAgeable.a {
      public final Axolotl.Variant[] a;

      public a(Axolotl.Variant... aaxolotl_variant) {
         super(false);
         this.a = aaxolotl_variant;
      }

      public Axolotl.Variant a(RandomSource randomsource) {
         return this.a[randomsource.a(this.a.length)];
      }
   }

   private class b extends SmoothSwimmingLookControl {
      public b(Axolotl axolotl, int i) {
         super(axolotl, i);
      }

      @Override
      public void a() {
         if (!Axolotl.this.fY()) {
            super.a();
         }
      }
   }

   private static class c extends SmoothSwimmingMoveControl {
      private final Axolotl l;

      public c(Axolotl axolotl) {
         super(axolotl, 85, 10, 0.1F, 0.5F, false);
         this.l = axolotl;
      }

      @Override
      public void a() {
         if (!this.l.fY()) {
            super.a();
         }
      }
   }
}
