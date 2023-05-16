package net.minecraft.world.entity.animal;

import java.util.UUID;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.particles.Particles;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.network.syncher.DataWatcherObject;
import net.minecraft.network.syncher.DataWatcherRegistry;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.tags.TagsBlock;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.util.TimeRange;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityAgeable;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityPose;
import net.minecraft.world.entity.EntitySize;
import net.minecraft.world.entity.EntityTameableAnimal;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumMobSpawn;
import net.minecraft.world.entity.IEntityAngerable;
import net.minecraft.world.entity.ai.attributes.AttributeProvider;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.entity.ai.goal.PathfinderGoalAvoidTarget;
import net.minecraft.world.entity.ai.goal.PathfinderGoalBeg;
import net.minecraft.world.entity.ai.goal.PathfinderGoalBreed;
import net.minecraft.world.entity.ai.goal.PathfinderGoalFloat;
import net.minecraft.world.entity.ai.goal.PathfinderGoalFollowOwner;
import net.minecraft.world.entity.ai.goal.PathfinderGoalLeapAtTarget;
import net.minecraft.world.entity.ai.goal.PathfinderGoalLookAtPlayer;
import net.minecraft.world.entity.ai.goal.PathfinderGoalMeleeAttack;
import net.minecraft.world.entity.ai.goal.PathfinderGoalPanic;
import net.minecraft.world.entity.ai.goal.PathfinderGoalRandomLookaround;
import net.minecraft.world.entity.ai.goal.PathfinderGoalRandomStrollLand;
import net.minecraft.world.entity.ai.goal.PathfinderGoalSit;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalHurtByTarget;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalNearestAttackableTarget;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalOwnerHurtByTarget;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalOwnerHurtTarget;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalRandomTargetNonTamed;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalUniversalAngerReset;
import net.minecraft.world.entity.animal.horse.EntityHorseAbstract;
import net.minecraft.world.entity.animal.horse.EntityLlama;
import net.minecraft.world.entity.monster.EntityCreeper;
import net.minecraft.world.entity.monster.EntityGhast;
import net.minecraft.world.entity.monster.EntitySkeletonAbstract;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.projectile.EntityArrow;
import net.minecraft.world.item.EnumColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDye;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;

public class EntityWolf extends EntityTameableAnimal implements IEntityAngerable {
   private static final DataWatcherObject<Boolean> bW = DataWatcher.a(EntityWolf.class, DataWatcherRegistry.k);
   private static final DataWatcherObject<Integer> bX = DataWatcher.a(EntityWolf.class, DataWatcherRegistry.b);
   private static final DataWatcherObject<Integer> bY = DataWatcher.a(EntityWolf.class, DataWatcherRegistry.b);
   public static final Predicate<EntityLiving> bV = entityliving -> {
      EntityTypes<?> entitytypes = entityliving.ae();
      return entitytypes == EntityTypes.aF || entitytypes == EntityTypes.aC || entitytypes == EntityTypes.N;
   };
   private static final float bZ = 8.0F;
   private static final float ca = 20.0F;
   private float cb;
   private float cc;
   private boolean cd;
   private boolean ce;
   private float cf;
   private float cg;
   private static final UniformInt ch = TimeRange.a(20, 39);
   @Nullable
   private UUID ci;

   public EntityWolf(EntityTypes<? extends EntityWolf> entitytypes, World world) {
      super(entitytypes, world);
      this.x(false);
      this.a(PathType.f, -1.0F);
      this.a(PathType.g, -1.0F);
   }

   @Override
   protected void x() {
      this.bN.a(1, new PathfinderGoalFloat(this));
      this.bN.a(1, new EntityWolf.b(1.5));
      this.bN.a(2, new PathfinderGoalSit(this));
      this.bN.a(3, new EntityWolf.a(this, EntityLlama.class, 24.0F, 1.5, 1.5));
      this.bN.a(4, new PathfinderGoalLeapAtTarget(this, 0.4F));
      this.bN.a(5, new PathfinderGoalMeleeAttack(this, 1.0, true));
      this.bN.a(6, new PathfinderGoalFollowOwner(this, 1.0, 10.0F, 2.0F, false));
      this.bN.a(7, new PathfinderGoalBreed(this, 1.0));
      this.bN.a(8, new PathfinderGoalRandomStrollLand(this, 1.0));
      this.bN.a(9, new PathfinderGoalBeg(this, 8.0F));
      this.bN.a(10, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
      this.bN.a(10, new PathfinderGoalRandomLookaround(this));
      this.bO.a(1, new PathfinderGoalOwnerHurtByTarget(this));
      this.bO.a(2, new PathfinderGoalOwnerHurtTarget(this));
      this.bO.a(3, new PathfinderGoalHurtByTarget(this).a());
      this.bO.a(4, new PathfinderGoalNearestAttackableTarget<>(this, EntityHuman.class, 10, true, false, this::a_));
      this.bO.a(5, new PathfinderGoalRandomTargetNonTamed<>(this, EntityAnimal.class, false, bV));
      this.bO.a(6, new PathfinderGoalRandomTargetNonTamed<>(this, EntityTurtle.class, false, EntityTurtle.bT));
      this.bO.a(7, new PathfinderGoalNearestAttackableTarget<>(this, EntitySkeletonAbstract.class, false));
      this.bO.a(8, new PathfinderGoalUniversalAngerReset<>(this, true));
   }

   public static AttributeProvider.Builder fY() {
      return EntityInsentient.y().a(GenericAttributes.d, 0.3F).a(GenericAttributes.a, 8.0).a(GenericAttributes.f, 2.0);
   }

   @Override
   public boolean setTarget(EntityLiving entityliving, TargetReason reason, boolean fire) {
      if (!super.setTarget(entityliving, reason, fire)) {
         return false;
      } else {
         entityliving = this.P_();
         return true;
      }
   }

   @Override
   protected void a_() {
      super.a_();
      this.am.a(bW, false);
      this.am.a(bX, EnumColor.o.a());
      this.am.a(bY, 0);
   }

   @Override
   protected void b(BlockPosition blockposition, IBlockData iblockdata) {
      this.a(SoundEffects.zV, 0.15F, 1.0F);
   }

   @Override
   public void b(NBTTagCompound nbttagcompound) {
      super.b(nbttagcompound);
      nbttagcompound.a("CollarColor", (byte)this.gb().a());
      this.c(nbttagcompound);
   }

   @Override
   public void a(NBTTagCompound nbttagcompound) {
      super.a(nbttagcompound);
      if (nbttagcompound.b("CollarColor", 99)) {
         this.a(EnumColor.a(nbttagcompound.h("CollarColor")));
      }

      this.a(this.H, nbttagcompound);
   }

   @Override
   protected SoundEffect s() {
      return this.R_() ? SoundEffects.zQ : (this.af.a(3) == 0 ? (this.q() && this.eo() < 10.0F ? SoundEffects.zW : SoundEffects.zT) : SoundEffects.zO);
   }

   @Override
   protected SoundEffect d(DamageSource damagesource) {
      return SoundEffects.zS;
   }

   @Override
   protected SoundEffect x_() {
      return SoundEffects.zP;
   }

   @Override
   protected float eN() {
      return 0.4F;
   }

   @Override
   public void b_() {
      super.b_();
      if (!this.H.B && this.cd && !this.ce && !this.fP() && this.N) {
         this.ce = true;
         this.cf = 0.0F;
         this.cg = 0.0F;
         this.H.a(this, (byte)8);
      }

      if (!this.H.B) {
         this.a((WorldServer)this.H, true);
      }
   }

   @Override
   public void l() {
      super.l();
      if (this.bq()) {
         this.cc = this.cb;
         if (this.gc()) {
            this.cb += (1.0F - this.cb) * 0.4F;
         } else {
            this.cb += (0.0F - this.cb) * 0.4F;
         }

         if (this.aV()) {
            this.cd = true;
            if (this.ce && !this.H.B) {
               this.H.a(this, (byte)56);
               this.gd();
            }
         } else if ((this.cd || this.ce) && this.ce) {
            if (this.cf == 0.0F) {
               this.a(SoundEffects.zU, this.eN(), (this.af.i() - this.af.i()) * 0.2F + 1.0F);
               this.a(GameEvent.w);
            }

            this.cg = this.cf;
            this.cf += 0.05F;
            if (this.cg >= 2.0F) {
               this.cd = false;
               this.ce = false;
               this.cg = 0.0F;
               this.cf = 0.0F;
            }

            if (this.cf > 0.4F) {
               float f = (float)this.dn();
               int i = (int)(MathHelper.a((this.cf - 0.4F) * (float) Math.PI) * 7.0F);
               Vec3D vec3d = this.dj();

               for(int j = 0; j < i; ++j) {
                  float f1 = (this.af.i() * 2.0F - 1.0F) * this.dc() * 0.5F;
                  float f2 = (this.af.i() * 2.0F - 1.0F) * this.dc() * 0.5F;
                  this.H.a(Particles.ai, this.dl() + (double)f1, (double)(f + 0.8F), this.dr() + (double)f2, vec3d.c, vec3d.d, vec3d.e);
               }
            }
         }
      }
   }

   private void gd() {
      this.ce = false;
      this.cf = 0.0F;
      this.cg = 0.0F;
   }

   @Override
   public void a(DamageSource damagesource) {
      this.cd = false;
      this.ce = false;
      this.cg = 0.0F;
      this.cf = 0.0F;
      super.a(damagesource);
   }

   public boolean fZ() {
      return this.cd;
   }

   public float C(float f) {
      return Math.min(0.5F + MathHelper.i(f, this.cg, this.cf) / 2.0F * 0.5F, 1.0F);
   }

   public float f(float f, float f1) {
      float f2 = (MathHelper.i(f, this.cg, this.cf) + f1) / 1.8F;
      if (f2 < 0.0F) {
         f2 = 0.0F;
      } else if (f2 > 1.0F) {
         f2 = 1.0F;
      }

      return MathHelper.a(f2 * (float) Math.PI) * MathHelper.a(f2 * (float) Math.PI * 11.0F) * 0.15F * (float) Math.PI;
   }

   public float D(float f) {
      return MathHelper.i(f, this.cc, this.cb) * 0.15F * (float) Math.PI;
   }

   @Override
   protected float b(EntityPose entitypose, EntitySize entitysize) {
      return entitysize.b * 0.8F;
   }

   @Override
   public int V() {
      return this.w() ? 20 : super.V();
   }

   @Override
   public boolean a(DamageSource damagesource, float f) {
      if (this.b(damagesource)) {
         return false;
      } else {
         Entity entity = damagesource.d();
         if (entity != null && !(entity instanceof EntityHuman) && !(entity instanceof EntityArrow)) {
            f = (f + 1.0F) / 2.0F;
         }

         return super.a(damagesource, f);
      }
   }

   @Override
   public boolean z(Entity entity) {
      boolean flag = entity.a(this.dG().b((EntityLiving)this), (float)((int)this.b(GenericAttributes.f)));
      if (flag) {
         this.a(this, entity);
      }

      return flag;
   }

   @Override
   public void x(boolean flag) {
      super.x(flag);
      if (flag) {
         this.a(GenericAttributes.a).a(20.0);
         this.c(this.eE());
      } else {
         this.a(GenericAttributes.a).a(8.0);
      }

      this.a(GenericAttributes.f).a(4.0);
   }

   @Override
   public EnumInteractionResult b(EntityHuman entityhuman, EnumHand enumhand) {
      ItemStack itemstack = entityhuman.b(enumhand);
      Item item = itemstack.c();
      if (this.H.B) {
         boolean flag = this.m(entityhuman) || this.q() || itemstack.a(Items.qH) && !this.q() && !this.R_();
         return flag ? EnumInteractionResult.b : EnumInteractionResult.d;
      } else {
         if (this.q()) {
            if (this.m(itemstack) && this.eo() < this.eE()) {
               if (!entityhuman.fK().d) {
                  itemstack.h(1);
               }

               this.heal((float)item.v().a(), RegainReason.EATING);
               return EnumInteractionResult.a;
            }

            if (!(item instanceof ItemDye)) {
               EnumInteractionResult enuminteractionresult = super.b(entityhuman, enumhand);
               if ((!enuminteractionresult.a() || this.y_()) && this.m(entityhuman)) {
                  this.z(!this.fS());
                  this.bi = false;
                  this.bM.n();
                  this.setTarget(null, TargetReason.FORGOT_TARGET, true);
                  return EnumInteractionResult.a;
               }

               return enuminteractionresult;
            }

            EnumColor enumcolor = ((ItemDye)item).d();
            if (enumcolor != this.gb()) {
               this.a(enumcolor);
               if (!entityhuman.fK().d) {
                  itemstack.h(1);
               }

               return EnumInteractionResult.a;
            }
         } else if (itemstack.a(Items.qH) && !this.R_()) {
            if (!entityhuman.fK().d) {
               itemstack.h(1);
            }

            if (this.af.a(3) == 0 && !CraftEventFactory.callEntityTameEvent(this, entityhuman).isCancelled()) {
               this.e(entityhuman);
               this.bM.n();
               this.i(null);
               this.z(true);
               this.H.a(this, (byte)7);
            } else {
               this.H.a(this, (byte)6);
            }

            return EnumInteractionResult.a;
         }

         return super.b(entityhuman, enumhand);
      }
   }

   @Override
   public void b(byte b0) {
      if (b0 == 8) {
         this.ce = true;
         this.cf = 0.0F;
         this.cg = 0.0F;
      } else if (b0 == 56) {
         this.gd();
      } else {
         super.b(b0);
      }
   }

   public float ga() {
      return this.R_() ? 1.5393804F : (this.q() ? (0.55F - (this.eE() - this.eo()) * 0.02F) * (float) Math.PI : (float) (Math.PI / 5));
   }

   @Override
   public boolean m(ItemStack itemstack) {
      Item item = itemstack.c();
      return item.u() && item.v().c();
   }

   @Override
   public int fy() {
      return 8;
   }

   @Override
   public int a() {
      return this.am.a(bY);
   }

   @Override
   public void a(int i) {
      this.am.b(bY, i);
   }

   @Override
   public void c() {
      this.a(ch.a(this.af));
   }

   @Nullable
   @Override
   public UUID b() {
      return this.ci;
   }

   @Override
   public void a(@Nullable UUID uuid) {
      this.ci = uuid;
   }

   public EnumColor gb() {
      return EnumColor.a(this.am.a(bX));
   }

   public void a(EnumColor enumcolor) {
      this.am.b(bX, enumcolor.a());
   }

   @Nullable
   public EntityWolf b(WorldServer worldserver, EntityAgeable entityageable) {
      EntityWolf entitywolf = EntityTypes.bn.a((World)worldserver);
      if (entitywolf != null) {
         UUID uuid = this.T_();
         if (uuid != null) {
            entitywolf.b(uuid);
            entitywolf.x(true);
         }
      }

      return entitywolf;
   }

   public void A(boolean flag) {
      this.am.b(bW, flag);
   }

   @Override
   public boolean a(EntityAnimal entityanimal) {
      if (entityanimal == this) {
         return false;
      } else if (!this.q()) {
         return false;
      } else if (!(entityanimal instanceof EntityWolf)) {
         return false;
      } else {
         EntityWolf entitywolf = (EntityWolf)entityanimal;
         return !entitywolf.q() ? false : (entitywolf.w() ? false : this.fW() && entitywolf.fW());
      }
   }

   public boolean gc() {
      return this.am.a(bW);
   }

   @Override
   public boolean a(EntityLiving entityliving, EntityLiving entityliving1) {
      if (entityliving instanceof EntityCreeper || entityliving instanceof EntityGhast) {
         return false;
      } else if (entityliving instanceof EntityWolf entitywolf) {
         return !entitywolf.q() || entitywolf.H_() != entityliving1;
      } else {
         return entityliving instanceof EntityHuman && entityliving1 instanceof EntityHuman && !((EntityHuman)entityliving1).a((EntityHuman)entityliving)
            ? false
            : (
               entityliving instanceof EntityHorseAbstract && ((EntityHorseAbstract)entityliving).gh()
                  ? false
                  : !(entityliving instanceof EntityTameableAnimal) || !((EntityTameableAnimal)entityliving).q()
            );
      }
   }

   @Override
   public boolean a(EntityHuman entityhuman) {
      return !this.R_() && super.a(entityhuman);
   }

   @Override
   public Vec3D cF() {
      return new Vec3D(0.0, (double)(0.6F * this.cE()), (double)(this.dc() * 0.4F));
   }

   public static boolean c(
      EntityTypes<EntityWolf> entitytypes, GeneratorAccess generatoraccess, EnumMobSpawn enummobspawn, BlockPosition blockposition, RandomSource randomsource
   ) {
      return generatoraccess.a_(blockposition.d()).a(TagsBlock.bR) && a(generatoraccess, blockposition);
   }

   private class a<T extends EntityLiving> extends PathfinderGoalAvoidTarget<T> {
      private final EntityWolf j;

      public a(EntityWolf entitywolf, Class oclass, float f, double d0, double d1) {
         super(entitywolf, oclass, f, d0, d1);
         this.j = entitywolf;
      }

      @Override
      public boolean a() {
         return super.a() && this.b instanceof EntityLlama ? !this.j.q() && this.a((EntityLlama)this.b) : false;
      }

      private boolean a(EntityLlama entityllama) {
         return entityllama.gc() >= EntityWolf.this.af.a(5);
      }

      @Override
      public void c() {
         EntityWolf.this.i(null);
         super.c();
      }

      @Override
      public void e() {
         EntityWolf.this.i(null);
         super.e();
      }
   }

   private class b extends PathfinderGoalPanic {
      public b(double d0) {
         super(EntityWolf.this, d0);
      }

      @Override
      protected boolean h() {
         return this.b.dv() || this.b.bK();
      }
   }
}
