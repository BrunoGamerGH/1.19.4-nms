package net.minecraft.world.entity.animal.frog;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Dynamic;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriterionTriggers;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.protocol.game.PacketDebug;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.network.syncher.DataWatcherObject;
import net.minecraft.network.syncher.DataWatcherRegistry;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.stats.StatisticList;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagsBlock;
import net.minecraft.tags.TagsEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Unit;
import net.minecraft.world.DifficultyDamageScaler;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityAgeable;
import net.minecraft.world.entity.EntityExperienceOrb;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityPose;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumMobSpawn;
import net.minecraft.world.entity.EnumMoveType;
import net.minecraft.world.entity.GroupDataEntity;
import net.minecraft.world.entity.VariantHolder;
import net.minecraft.world.entity.ai.BehaviorController;
import net.minecraft.world.entity.ai.attributes.AttributeProvider;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.entity.ai.control.ControllerLook;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.navigation.AmphibiousPathNavigation;
import net.minecraft.world.entity.ai.navigation.NavigationAbstract;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.animal.EntityAnimal;
import net.minecraft.world.entity.animal.FrogVariant;
import net.minecraft.world.entity.monster.EntitySlime;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.World;
import net.minecraft.world.level.WorldAccess;
import net.minecraft.world.level.biome.BiomeBase;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.pathfinder.AmphibiousNodeEvaluator;
import net.minecraft.world.level.pathfinder.PathPoint;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.pathfinder.Pathfinder;
import net.minecraft.world.phys.Vec3D;

public class Frog extends EntityAnimal implements VariantHolder<FrogVariant> {
   public static final RecipeItemStack bS = RecipeItemStack.a(Items.pY);
   protected static final ImmutableList<SensorType<? extends Sensor<? super Frog>>> bT = ImmutableList.of(
      SensorType.c, SensorType.f, SensorType.t, SensorType.r, SensorType.u
   );
   protected static final ImmutableList<MemoryModuleType<?>> bV = ImmutableList.of(
      MemoryModuleType.n,
      MemoryModuleType.g,
      MemoryModuleType.h,
      MemoryModuleType.m,
      MemoryModuleType.E,
      MemoryModuleType.t,
      MemoryModuleType.r,
      MemoryModuleType.R,
      MemoryModuleType.S,
      MemoryModuleType.o,
      MemoryModuleType.N,
      MemoryModuleType.O,
      new MemoryModuleType[]{
         MemoryModuleType.Q,
         MemoryModuleType.x,
         MemoryModuleType.y,
         MemoryModuleType.B,
         MemoryModuleType.W,
         MemoryModuleType.X,
         MemoryModuleType.Y,
         MemoryModuleType.Z
      }
   );
   private static final DataWatcherObject<FrogVariant> cb = DataWatcher.a(Frog.class, DataWatcherRegistry.x);
   private static final DataWatcherObject<OptionalInt> cc = DataWatcher.a(Frog.class, DataWatcherRegistry.u);
   private static final int cd = 5;
   public static final String bW = "variant";
   public final AnimationState bX = new AnimationState();
   public final AnimationState bY = new AnimationState();
   public final AnimationState bZ = new AnimationState();
   public final AnimationState ca = new AnimationState();

   public Frog(EntityTypes<? extends EntityAnimal> var0, World var1) {
      super(var0, var1);
      this.bJ = new Frog.a(this);
      this.a(PathType.j, 4.0F);
      this.a(PathType.e, -1.0F);
      this.bK = new SmoothSwimmingMoveControl(this, 85, 10, 0.02F, 0.1F, true);
      this.v(1.0F);
   }

   @Override
   protected BehaviorController.b<Frog> dI() {
      return BehaviorController.a(bV, bT);
   }

   @Override
   protected BehaviorController<?> a(Dynamic<?> var0) {
      return FrogAi.a(this.dI().a(var0));
   }

   @Override
   public BehaviorController<Frog> dH() {
      return super.dH();
   }

   @Override
   protected void a_() {
      super.a_();
      this.am.a(cb, FrogVariant.a);
      this.am.a(cc, OptionalInt.empty());
   }

   @Override
   public void q() {
      this.am.b(cc, OptionalInt.empty());
   }

   public Optional<Entity> r() {
      return this.am.a(cc).stream().mapToObj(this.H::a).filter(Objects::nonNull).findFirst();
   }

   @Override
   public void a(Entity var0) {
      this.am.b(cc, OptionalInt.of(var0.af()));
   }

   @Override
   public int X() {
      return 35;
   }

   @Override
   public int W() {
      return 5;
   }

   public FrogVariant w() {
      return this.am.a(cb);
   }

   public void a(FrogVariant var0) {
      this.am.b(cb, var0);
   }

   @Override
   public void b(NBTTagCompound var0) {
      super.b(var0);
      var0.a("variant", BuiltInRegistries.aj.b(this.w()).toString());
   }

   @Override
   public void a(NBTTagCompound var0) {
      super.a(var0);
      FrogVariant var1 = BuiltInRegistries.aj.a(MinecraftKey.a(var0.l("variant")));
      if (var1 != null) {
         this.a(var1);
      }
   }

   @Override
   public boolean dK() {
      return true;
   }

   @Override
   protected void U() {
      this.H.ac().a("frogBrain");
      this.dH().a((WorldServer)this.H, this);
      this.H.ac().c();
      this.H.ac().a("frogActivityUpdate");
      FrogAi.a(this);
      this.H.ac().c();
      super.U();
   }

   @Override
   public void l() {
      if (this.H.k_()) {
         this.ca.a(this.aW() && !this.aP.c(), this.ag);
      }

      super.l();
   }

   @Override
   public void a(DataWatcherObject<?> var0) {
      if (ar.equals(var0)) {
         EntityPose var1 = this.al();
         if (var1 == EntityPose.g) {
            this.bX.a(this.ag);
         } else {
            this.bX.a();
         }

         if (var1 == EntityPose.i) {
            this.bY.a(this.ag);
         } else {
            this.bY.a();
         }

         if (var1 == EntityPose.j) {
            this.bZ.a(this.ag);
         } else {
            this.bZ.a();
         }
      }

      super.a(var0);
   }

   @Override
   protected void g(float var0) {
      float var1;
      if (this.bX.c()) {
         var1 = 0.0F;
      } else {
         var1 = Math.min(var0 * 25.0F, 1.0F);
      }

      this.aP.a(var1, 0.4F);
   }

   @Nullable
   @Override
   public EntityAgeable a(WorldServer var0, EntityAgeable var1) {
      Frog var2 = EntityTypes.O.a((World)var0);
      if (var2 != null) {
         FrogAi.a(var2, var0.r_());
      }

      return var2;
   }

   @Override
   public boolean y_() {
      return false;
   }

   @Override
   public void a(boolean var0) {
   }

   @Override
   public void a(WorldServer var0, EntityAnimal var1) {
      EntityPlayer var2 = this.fV();
      if (var2 == null) {
         var2 = var1.fV();
      }

      if (var2 != null) {
         var2.a(StatisticList.P);
         CriterionTriggers.o.a(var2, this, var1, null);
      }

      this.c_(6000);
      var1.c_(6000);
      this.fX();
      var1.fX();
      this.dH().a(MemoryModuleType.X, Unit.a);
      var0.a(this, (byte)18);
      if (var0.W().b(GameRules.f)) {
         var0.b(new EntityExperienceOrb(var0, this.dl(), this.dn(), this.dr(), this.dZ().a(7) + 1));
      }
   }

   @Override
   public GroupDataEntity a(WorldAccess var0, DifficultyDamageScaler var1, EnumMobSpawn var2, @Nullable GroupDataEntity var3, @Nullable NBTTagCompound var4) {
      Holder<BiomeBase> var5 = var0.v(this.dg());
      if (var5.a(BiomeTags.ag)) {
         this.a(FrogVariant.c);
      } else if (var5.a(BiomeTags.ah)) {
         this.a(FrogVariant.b);
      } else {
         this.a(FrogVariant.a);
      }

      FrogAi.a(this, var0.r_());
      return super.a(var0, var1, var2, var3, var4);
   }

   public static AttributeProvider.Builder fS() {
      return EntityInsentient.y().a(GenericAttributes.d, 1.0).a(GenericAttributes.a, 10.0).a(GenericAttributes.f, 10.0);
   }

   @Nullable
   @Override
   protected SoundEffect s() {
      return SoundEffects.iv;
   }

   @Nullable
   @Override
   protected SoundEffect d(DamageSource var0) {
      return SoundEffects.iy;
   }

   @Nullable
   @Override
   protected SoundEffect x_() {
      return SoundEffects.iw;
   }

   @Override
   protected void b(BlockPosition var0, IBlockData var1) {
      this.a(SoundEffects.iB, 0.15F, 1.0F);
   }

   @Override
   public boolean cv() {
      return false;
   }

   @Override
   protected void T() {
      super.T();
      PacketDebug.a(this);
   }

   @Override
   protected int d(float var0, float var1) {
      return super.d(var0, var1) - 5;
   }

   @Override
   public void h(Vec3D var0) {
      if (this.cT() && this.aT()) {
         this.a(this.eW(), var0);
         this.a(EnumMoveType.a, this.dj());
         this.f(this.dj().a(0.9));
      } else {
         super.h(var0);
      }
   }

   public static boolean m(EntityLiving var0) {
      if (var0 instanceof EntitySlime var1 && var1.fU() != 1) {
         return false;
      }

      return var0.ae().a(TagsEntity.k);
   }

   @Override
   protected NavigationAbstract a(World var0) {
      return new Frog.c(this, var0);
   }

   @Override
   public boolean m(ItemStack var0) {
      return bS.a(var0);
   }

   public static boolean c(EntityTypes<? extends EntityAnimal> var0, GeneratorAccess var1, EnumMobSpawn var2, BlockPosition var3, RandomSource var4) {
      return var1.a_(var3.d()).a(TagsBlock.bS) && a(var1, var3);
   }

   class a extends ControllerLook {
      a(EntityInsentient var1) {
         super(var1);
      }

      @Override
      protected boolean c() {
         return Frog.this.r().isEmpty();
      }
   }

   static class b extends AmphibiousNodeEvaluator {
      private final BlockPosition.MutableBlockPosition m = new BlockPosition.MutableBlockPosition();

      public b(boolean var0) {
         super(var0);
      }

      @Override
      public PathPoint a() {
         return !this.b.aT() ? super.a() : this.c(new BlockPosition(MathHelper.a(this.b.cD().a), MathHelper.a(this.b.cD().b), MathHelper.a(this.b.cD().c)));
      }

      @Override
      public PathType a(IBlockAccess var0, int var1, int var2, int var3) {
         this.m.d(var1, var2 - 1, var3);
         IBlockData var4 = var0.a_(this.m);
         return var4.a(TagsBlock.bF) ? PathType.b : super.a(var0, var1, var2, var3);
      }
   }

   static class c extends AmphibiousPathNavigation {
      c(Frog var0, World var1) {
         super(var0, var1);
      }

      @Override
      public boolean b(PathType var0) {
         return var0 != PathType.k && super.b(var0);
      }

      @Override
      protected Pathfinder a(int var0) {
         this.o = new Frog.b(true);
         this.o.a(true);
         return new Pathfinder(this.o, var0);
      }
   }
}
