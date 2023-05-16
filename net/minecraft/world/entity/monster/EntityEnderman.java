package net.minecraft.world.entity.monster;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.particles.Particles;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.GameProfileSerializer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.network.syncher.DataWatcherObject;
import net.minecraft.network.syncher.DataWatcherRegistry;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.TagsBlock;
import net.minecraft.tags.TagsFluid;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.util.TimeRange;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityPose;
import net.minecraft.world.entity.EntitySize;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.IEntityAngerable;
import net.minecraft.world.entity.ai.attributes.AttributeModifiable;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeProvider;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.entity.ai.goal.PathfinderGoal;
import net.minecraft.world.entity.ai.goal.PathfinderGoalFloat;
import net.minecraft.world.entity.ai.goal.PathfinderGoalLookAtPlayer;
import net.minecraft.world.entity.ai.goal.PathfinderGoalMeleeAttack;
import net.minecraft.world.entity.ai.goal.PathfinderGoalRandomLookaround;
import net.minecraft.world.entity.ai.goal.PathfinderGoalRandomStrollLand;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalHurtByTarget;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalNearestAttackableTarget;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalUniversalAngerReset;
import net.minecraft.world.entity.ai.targeting.PathfinderTargetCondition;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.projectile.EntityPotion;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionRegistry;
import net.minecraft.world.item.alchemy.PotionUtil;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.RayTrace;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.storage.loot.LootTableInfo;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameters;
import net.minecraft.world.phys.AxisAlignedBB;
import net.minecraft.world.phys.MovingObjectPositionBlock;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;

public class EntityEnderman extends EntityMonster implements IEntityAngerable {
   private static final UUID c = UUID.fromString("020E0DFB-87AE-4653-9556-831010E291A0");
   private static final AttributeModifier d = new AttributeModifier(c, "Attacking speed boost", 0.15F, AttributeModifier.Operation.a);
   private static final int e = 400;
   private static final int bS = 600;
   private static final DataWatcherObject<Optional<IBlockData>> bT = DataWatcher.a(EntityEnderman.class, DataWatcherRegistry.j);
   private static final DataWatcherObject<Boolean> bU = DataWatcher.a(EntityEnderman.class, DataWatcherRegistry.k);
   private static final DataWatcherObject<Boolean> bV = DataWatcher.a(EntityEnderman.class, DataWatcherRegistry.k);
   private int bW = Integer.MIN_VALUE;
   private int bX;
   private static final UniformInt bY = TimeRange.a(20, 39);
   private int bZ;
   @Nullable
   private UUID ca;

   public EntityEnderman(EntityTypes<? extends EntityEnderman> entitytypes, World world) {
      super(entitytypes, world);
      this.v(1.0F);
      this.a(PathType.j, -1.0F);
   }

   @Override
   protected void x() {
      this.bN.a(0, new PathfinderGoalFloat(this));
      this.bN.a(1, new EntityEnderman.a(this));
      this.bN.a(2, new PathfinderGoalMeleeAttack(this, 1.0, false));
      this.bN.a(7, new PathfinderGoalRandomStrollLand(this, 1.0, 0.0F));
      this.bN.a(8, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
      this.bN.a(8, new PathfinderGoalRandomLookaround(this));
      this.bN.a(10, new EntityEnderman.PathfinderGoalEndermanPlaceBlock(this));
      this.bN.a(11, new EntityEnderman.PathfinderGoalEndermanPickupBlock(this));
      this.bO.a(1, new EntityEnderman.PathfinderGoalPlayerWhoLookedAtTarget(this, this::a_));
      this.bO.a(2, new PathfinderGoalHurtByTarget(this));
      this.bO.a(3, new PathfinderGoalNearestAttackableTarget<>(this, EntityEndermite.class, true, false));
      this.bO.a(4, new PathfinderGoalUniversalAngerReset<>(this, false));
   }

   public static AttributeProvider.Builder q() {
      return EntityMonster.fY().a(GenericAttributes.a, 40.0).a(GenericAttributes.d, 0.3F).a(GenericAttributes.f, 7.0).a(GenericAttributes.b, 64.0);
   }

   @Override
   public void i(@Nullable EntityLiving entityliving) {
      this.setTarget(entityliving, TargetReason.UNKNOWN, true);
   }

   @Override
   public boolean setTarget(EntityLiving entityliving, TargetReason reason, boolean fireEvent) {
      if (!super.setTarget(entityliving, reason, fireEvent)) {
         return false;
      } else {
         entityliving = this.P_();
         AttributeModifiable attributemodifiable = this.a(GenericAttributes.d);
         if (entityliving == null) {
            this.bX = 0;
            this.am.b(bU, false);
            this.am.b(bV, false);
            attributemodifiable.d(d);
         } else {
            this.bX = this.ag;
            this.am.b(bU, true);
            if (!attributemodifiable.a(d)) {
               attributemodifiable.b(d);
            }
         }

         return true;
      }
   }

   @Override
   protected void a_() {
      super.a_();
      this.am.a(bT, Optional.empty());
      this.am.a(bU, false);
      this.am.a(bV, false);
   }

   @Override
   public void c() {
      this.a(bY.a(this.af));
   }

   @Override
   public void a(int i) {
      this.bZ = i;
   }

   @Override
   public int a() {
      return this.bZ;
   }

   @Override
   public void a(@Nullable UUID uuid) {
      this.ca = uuid;
   }

   @Nullable
   @Override
   public UUID b() {
      return this.ca;
   }

   public void r() {
      if (this.ag >= this.bW + 400) {
         this.bW = this.ag;
         if (!this.aO()) {
            this.H.a(this.dl(), this.dp(), this.dr(), SoundEffects.hc, this.cX(), 2.5F, 1.0F, false);
         }
      }
   }

   @Override
   public void a(DataWatcherObject<?> datawatcherobject) {
      if (bU.equals(datawatcherobject) && this.fU() && this.H.B) {
         this.r();
      }

      super.a(datawatcherobject);
   }

   @Override
   public void b(NBTTagCompound nbttagcompound) {
      super.b(nbttagcompound);
      IBlockData iblockdata = this.fS();
      if (iblockdata != null) {
         nbttagcompound.a("carriedBlockState", GameProfileSerializer.a(iblockdata));
      }

      this.c(nbttagcompound);
   }

   @Override
   public void a(NBTTagCompound nbttagcompound) {
      super.a(nbttagcompound);
      IBlockData iblockdata = null;
      if (nbttagcompound.b("carriedBlockState", 10)) {
         iblockdata = GameProfileSerializer.a(this.H.a(Registries.e), nbttagcompound.p("carriedBlockState"));
         if (iblockdata.h()) {
            iblockdata = null;
         }
      }

      this.c(iblockdata);
      this.a(this.H, nbttagcompound);
   }

   boolean f(EntityHuman entityhuman) {
      ItemStack itemstack = entityhuman.fJ().j.get(3);
      if (itemstack.a(Blocks.ee.k())) {
         return false;
      } else {
         Vec3D vec3d = entityhuman.j(1.0F).d();
         Vec3D vec3d1 = new Vec3D(this.dl() - entityhuman.dl(), this.dp() - entityhuman.dp(), this.dr() - entityhuman.dr());
         double d0 = vec3d1.f();
         vec3d1 = vec3d1.d();
         double d1 = vec3d.b(vec3d1);
         return d1 > 1.0 - 0.025 / d0 ? entityhuman.B(this) : false;
      }
   }

   @Override
   protected float b(EntityPose entitypose, EntitySize entitysize) {
      return 2.55F;
   }

   @Override
   public void b_() {
      if (this.H.B) {
         for(int i = 0; i < 2; ++i) {
            this.H.a(Particles.Z, this.d(0.5), this.do() - 0.25, this.g(0.5), (this.af.j() - 0.5) * 2.0, -this.af.j(), (this.af.j() - 0.5) * 2.0);
         }
      }

      this.bi = false;
      if (!this.H.B) {
         this.a((WorldServer)this.H, true);
      }

      super.b_();
   }

   @Override
   public boolean eX() {
      return true;
   }

   @Override
   protected void U() {
      if (this.H.M() && this.ag >= this.bX + 600) {
         float f = this.bh();
         if (f > 0.5F && this.H.g(this.dg()) && this.af.i() * 30.0F < (f - 0.4F) * 2.0F) {
            this.i(null);
            this.w();
         }
      }

      super.U();
   }

   protected boolean w() {
      if (!this.H.k_() && this.bq()) {
         double d0 = this.dl() + (this.af.j() - 0.5) * 64.0;
         double d1 = this.dn() + (double)(this.af.a(64) - 32);
         double d2 = this.dr() + (this.af.j() - 0.5) * 64.0;
         return this.r(d0, d1, d2);
      } else {
         return false;
      }
   }

   boolean a(Entity entity) {
      Vec3D vec3d = new Vec3D(this.dl() - entity.dl(), this.e(0.5) - entity.dp(), this.dr() - entity.dr());
      vec3d = vec3d.d();
      double d0 = 16.0;
      double d1 = this.dl() + (this.af.j() - 0.5) * 8.0 - vec3d.c * 16.0;
      double d2 = this.dn() + (double)(this.af.a(16) - 8) - vec3d.d * 16.0;
      double d3 = this.dr() + (this.af.j() - 0.5) * 8.0 - vec3d.e * 16.0;
      return this.r(d1, d2, d3);
   }

   private boolean r(double d0, double d1, double d2) {
      BlockPosition.MutableBlockPosition blockposition_mutableblockposition = new BlockPosition.MutableBlockPosition(d0, d1, d2);

      while(blockposition_mutableblockposition.v() > this.H.v_() && !this.H.a_(blockposition_mutableblockposition).d().c()) {
         blockposition_mutableblockposition.c(EnumDirection.a);
      }

      IBlockData iblockdata = this.H.a_(blockposition_mutableblockposition);
      boolean flag = iblockdata.d().c();
      boolean flag1 = iblockdata.r().a(TagsFluid.a);
      if (flag && !flag1) {
         Vec3D vec3d = this.de();
         boolean flag2 = this.a(d0, d1, d2, true);
         if (flag2) {
            this.H.a(GameEvent.V, vec3d, GameEvent.a.a(this));
            if (!this.aO()) {
               this.H.a(null, this.I, this.J, this.K, SoundEffects.hd, this.cX(), 1.0F, 1.0F);
               this.a(SoundEffects.hd, 1.0F, 1.0F);
            }
         }

         return flag2;
      } else {
         return false;
      }
   }

   @Override
   protected SoundEffect s() {
      return this.fT() ? SoundEffects.hb : SoundEffects.gY;
   }

   @Override
   protected SoundEffect d(DamageSource damagesource) {
      return SoundEffects.ha;
   }

   @Override
   protected SoundEffect x_() {
      return SoundEffects.gZ;
   }

   @Override
   protected void a(DamageSource damagesource, int i, boolean flag) {
      super.a(damagesource, i, flag);
      IBlockData iblockdata = this.fS();
      if (iblockdata != null) {
         ItemStack itemstack = new ItemStack(Items.oq);
         itemstack.a(Enchantments.v, 1);
         LootTableInfo.Builder loottableinfo_builder = new LootTableInfo.Builder((WorldServer)this.H)
            .a(this.H.r_())
            .a(LootContextParameters.f, this.de())
            .a(LootContextParameters.i, itemstack)
            .b(LootContextParameters.a, this);

         for(ItemStack itemstack1 : iblockdata.a(loottableinfo_builder)) {
            this.b(itemstack1);
         }
      }
   }

   public void c(@Nullable IBlockData iblockdata) {
      this.am.b(bT, Optional.ofNullable(iblockdata));
   }

   @Nullable
   public IBlockData fS() {
      return this.am.a(bT).orElse(null);
   }

   @Override
   public boolean a(DamageSource damagesource, float f) {
      if (this.b(damagesource)) {
         return false;
      } else {
         boolean flag = damagesource.c() instanceof EntityPotion;
         if (!damagesource.a(DamageTypeTags.j) && !flag) {
            boolean flag1 = super.a(damagesource, f);
            if (!this.H.k_() && !(damagesource.d() instanceof EntityLiving) && this.af.a(10) != 0) {
               this.w();
            }

            return flag1;
         } else {
            boolean flag1 = flag && this.a(damagesource, (EntityPotion)damagesource.c(), f);

            for(int i = 0; i < 64; ++i) {
               if (this.w()) {
                  return true;
               }
            }

            return flag1;
         }
      }
   }

   private boolean a(DamageSource damagesource, EntityPotion entitypotion, float f) {
      ItemStack itemstack = entitypotion.i();
      PotionRegistry potionregistry = PotionUtil.d(itemstack);
      List<MobEffect> list = PotionUtil.a(itemstack);
      boolean flag = potionregistry == Potions.c && list.isEmpty();
      return flag ? super.a(damagesource, f) : false;
   }

   public boolean fT() {
      return this.am.a(bU);
   }

   public boolean fU() {
      return this.am.a(bV);
   }

   public void fV() {
      this.am.b(bV, true);
   }

   @Override
   public boolean Q() {
      return super.Q() || this.fS() != null;
   }

   private static class PathfinderGoalEndermanPickupBlock extends PathfinderGoal {
      private final EntityEnderman a;

      public PathfinderGoalEndermanPickupBlock(EntityEnderman entityenderman) {
         this.a = entityenderman;
      }

      @Override
      public boolean a() {
         return this.a.fS() != null ? false : (!this.a.H.W().b(GameRules.c) ? false : this.a.dZ().a(b(20)) == 0);
      }

      @Override
      public void e() {
         RandomSource randomsource = this.a.dZ();
         World world = this.a.H;
         int i = MathHelper.a(this.a.dl() - 2.0 + randomsource.j() * 4.0);
         int j = MathHelper.a(this.a.dn() + randomsource.j() * 3.0);
         int k = MathHelper.a(this.a.dr() - 2.0 + randomsource.j() * 4.0);
         BlockPosition blockposition = new BlockPosition(i, j, k);
         IBlockData iblockdata = world.a_(blockposition);
         Vec3D vec3d = new Vec3D((double)this.a.dk() + 0.5, (double)j + 0.5, (double)this.a.dq() + 0.5);
         Vec3D vec3d1 = new Vec3D((double)i + 0.5, (double)j + 0.5, (double)k + 0.5);
         MovingObjectPositionBlock movingobjectpositionblock = world.a(
            new RayTrace(vec3d, vec3d1, RayTrace.BlockCollisionOption.b, RayTrace.FluidCollisionOption.a, this.a)
         );
         boolean flag = movingobjectpositionblock.a().equals(blockposition);
         if (iblockdata.a(TagsBlock.ai) && flag && !CraftEventFactory.callEntityChangeBlockEvent(this.a, blockposition, Blocks.a.o()).isCancelled()) {
            world.a(blockposition, false);
            world.a(GameEvent.f, blockposition, GameEvent.a.a(this.a, iblockdata));
            this.a.c(iblockdata.b().o());
         }
      }
   }

   private static class PathfinderGoalEndermanPlaceBlock extends PathfinderGoal {
      private final EntityEnderman a;

      public PathfinderGoalEndermanPlaceBlock(EntityEnderman entityenderman) {
         this.a = entityenderman;
      }

      @Override
      public boolean a() {
         return this.a.fS() == null ? false : (!this.a.H.W().b(GameRules.c) ? false : this.a.dZ().a(b(2000)) == 0);
      }

      @Override
      public void e() {
         RandomSource randomsource = this.a.dZ();
         World world = this.a.H;
         int i = MathHelper.a(this.a.dl() - 1.0 + randomsource.j() * 2.0);
         int j = MathHelper.a(this.a.dn() + randomsource.j() * 2.0);
         int k = MathHelper.a(this.a.dr() - 1.0 + randomsource.j() * 2.0);
         BlockPosition blockposition = new BlockPosition(i, j, k);
         IBlockData iblockdata = world.a_(blockposition);
         BlockPosition blockposition1 = blockposition.d();
         IBlockData iblockdata1 = world.a_(blockposition1);
         IBlockData iblockdata2 = this.a.fS();
         if (iblockdata2 != null) {
            iblockdata2 = Block.b(iblockdata2, this.a.H, blockposition);
            if (this.a(world, blockposition, iblockdata2, iblockdata, iblockdata1, blockposition1)
               && !CraftEventFactory.callEntityChangeBlockEvent(this.a, blockposition, iblockdata2).isCancelled()) {
               world.a(blockposition, iblockdata2, 3);
               world.a(GameEvent.i, blockposition, GameEvent.a.a(this.a, iblockdata2));
               this.a.c(null);
            }
         }
      }

      private boolean a(
         World world, BlockPosition blockposition, IBlockData iblockdata, IBlockData iblockdata1, IBlockData iblockdata2, BlockPosition blockposition1
      ) {
         return iblockdata1.h()
            && !iblockdata2.h()
            && !iblockdata2.a(Blocks.F)
            && iblockdata2.r(world, blockposition1)
            && iblockdata.a((IWorldReader)world, blockposition)
            && world.a_(this.a, AxisAlignedBB.a(Vec3D.a(blockposition))).isEmpty();
      }
   }

   private static class PathfinderGoalPlayerWhoLookedAtTarget extends PathfinderGoalNearestAttackableTarget<EntityHuman> {
      private final EntityEnderman i;
      @Nullable
      private EntityHuman j;
      private int k;
      private int l;
      private final PathfinderTargetCondition m;
      private final PathfinderTargetCondition n = PathfinderTargetCondition.a().d();
      private final Predicate<EntityLiving> o;

      public PathfinderGoalPlayerWhoLookedAtTarget(EntityEnderman entityenderman, @Nullable Predicate<EntityLiving> predicate) {
         super(entityenderman, EntityHuman.class, 10, false, false, predicate);
         this.i = entityenderman;
         this.o = entityliving -> (entityenderman.f((EntityHuman)entityliving) || entityenderman.a_(entityliving)) && !entityenderman.w(entityliving);
         this.m = PathfinderTargetCondition.a().a(this.l()).a(this.o);
      }

      @Override
      public boolean a() {
         this.j = this.i.H.a(this.m, this.i);
         return this.j != null;
      }

      @Override
      public void c() {
         this.k = this.a(5);
         this.l = 0;
         this.i.fV();
      }

      @Override
      public void d() {
         this.j = null;
         super.d();
      }

      @Override
      public boolean b() {
         if (this.j != null) {
            if (!this.o.test(this.j)) {
               return false;
            } else {
               this.i.a(this.j, 10.0F, 10.0F);
               return true;
            }
         } else {
            if (this.c != null) {
               if (this.i.w(this.c)) {
                  return false;
               }

               if (this.n.a(this.i, this.c)) {
                  return true;
               }
            }

            return super.b();
         }
      }

      @Override
      public void e() {
         if (this.i.P_() == null) {
            super.a(null);
         }

         if (this.j != null) {
            if (--this.k <= 0) {
               this.c = this.j;
               this.j = null;
               super.c();
            }
         } else {
            if (this.c != null && !this.i.bL()) {
               if (this.i.f((EntityHuman)this.c)) {
                  if (this.c.f(this.i) < 16.0) {
                     this.i.w();
                  }

                  this.l = 0;
               } else if (this.c.f(this.i) > 256.0 && this.l++ >= this.a(30) && this.i.a(this.c)) {
                  this.l = 0;
               }
            }

            super.e();
         }
      }
   }

   private static class a extends PathfinderGoal {
      private final EntityEnderman a;
      @Nullable
      private EntityLiving b;

      public a(EntityEnderman entityenderman) {
         this.a = entityenderman;
         this.a(EnumSet.of(PathfinderGoal.Type.c, PathfinderGoal.Type.a));
      }

      @Override
      public boolean a() {
         this.b = this.a.P_();
         if (!(this.b instanceof EntityHuman)) {
            return false;
         } else {
            double d0 = this.b.f(this.a);
            return d0 > 256.0 ? false : this.a.f((EntityHuman)this.b);
         }
      }

      @Override
      public void c() {
         this.a.G().n();
      }

      @Override
      public void e() {
         this.a.C().a(this.b.dl(), this.b.dp(), this.b.dr());
      }
   }
}
