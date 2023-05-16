package net.minecraft.world.entity.monster;

import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.particles.Particles;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.tags.TagsEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.ai.attributes.AttributeProvider;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.entity.ai.goal.PathfinderGoal;
import net.minecraft.world.entity.ai.goal.PathfinderGoalFloat;
import net.minecraft.world.entity.ai.goal.PathfinderGoalLookAtPlayer;
import net.minecraft.world.entity.ai.goal.PathfinderGoalMeleeAttack;
import net.minecraft.world.entity.ai.goal.PathfinderGoalRandomStrollLand;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalHurtByTarget;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalNearestAttackableTarget;
import net.minecraft.world.entity.animal.EntityIronGolem;
import net.minecraft.world.entity.npc.EntityVillagerAbstract;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.raid.EntityRaider;
import net.minecraft.world.entity.vehicle.EntityBoat;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BlockLeaves;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.AxisAlignedBB;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;

public class EntityRavager extends EntityRaider {
   private static final Predicate<Entity> e = entity -> entity.bq() && !(entity instanceof EntityRavager);
   private static final double bS = 0.3;
   private static final double bT = 0.35;
   private static final int bU = 8356754;
   private static final double bV = 0.5725490196078431;
   private static final double bW = 0.5137254901960784;
   private static final double bX = 0.4980392156862745;
   private static final int bY = 10;
   public static final int b = 40;
   private int bZ;
   private int ca;
   private int cb;

   public EntityRavager(EntityTypes<? extends EntityRavager> entitytypes, World world) {
      super(entitytypes, world);
      this.v(1.0F);
      this.bI = 20;
      this.a(PathType.v, 0.0F);
   }

   @Override
   protected void x() {
      super.x();
      this.bN.a(0, new PathfinderGoalFloat(this));
      this.bN.a(4, new EntityRavager.a());
      this.bN.a(5, new PathfinderGoalRandomStrollLand(this, 0.4));
      this.bN.a(6, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 6.0F));
      this.bN.a(10, new PathfinderGoalLookAtPlayer(this, EntityInsentient.class, 8.0F));
      this.bO.a(2, new PathfinderGoalHurtByTarget(this, EntityRaider.class).a());
      this.bO.a(3, new PathfinderGoalNearestAttackableTarget<>(this, EntityHuman.class, true));
      this.bO.a(4, new PathfinderGoalNearestAttackableTarget<>(this, EntityVillagerAbstract.class, true, entityliving -> !entityliving.y_()));
      this.bO.a(4, new PathfinderGoalNearestAttackableTarget<>(this, EntityIronGolem.class, true));
   }

   @Override
   protected void N() {
      boolean flag = !(this.cK() instanceof EntityInsentient) || this.cK().ae().a(TagsEntity.b);
      boolean flag1 = !(this.cV() instanceof EntityBoat);
      this.bN.a(PathfinderGoal.Type.a, flag);
      this.bN.a(PathfinderGoal.Type.c, flag && flag1);
      this.bN.a(PathfinderGoal.Type.b, flag);
      this.bN.a(PathfinderGoal.Type.d, flag);
   }

   public static AttributeProvider.Builder q() {
      return EntityMonster.fY()
         .a(GenericAttributes.a, 100.0)
         .a(GenericAttributes.d, 0.3)
         .a(GenericAttributes.c, 0.75)
         .a(GenericAttributes.f, 12.0)
         .a(GenericAttributes.g, 1.5)
         .a(GenericAttributes.b, 32.0);
   }

   @Override
   public void b(NBTTagCompound nbttagcompound) {
      super.b(nbttagcompound);
      nbttagcompound.a("AttackTick", this.bZ);
      nbttagcompound.a("StunTick", this.ca);
      nbttagcompound.a("RoarTick", this.cb);
   }

   @Override
   public void a(NBTTagCompound nbttagcompound) {
      super.a(nbttagcompound);
      this.bZ = nbttagcompound.h("AttackTick");
      this.ca = nbttagcompound.h("StunTick");
      this.cb = nbttagcompound.h("RoarTick");
   }

   @Override
   public SoundEffect X_() {
      return SoundEffects.tc;
   }

   @Override
   public int W() {
      return 45;
   }

   @Override
   public double bv() {
      return 2.1;
   }

   @Nullable
   @Override
   public EntityLiving cK() {
      if (!this.fK()) {
         Entity entity = this.cN();
         if (entity instanceof EntityLiving) {
            return (EntityLiving)entity;
         }
      }

      return null;
   }

   @Override
   public void b_() {
      super.b_();
      if (this.bq()) {
         if (this.eP()) {
            this.a(GenericAttributes.d).a(0.0);
         } else {
            double d0 = this.P_() != null ? 0.35 : 0.3;
            double d1 = this.a(GenericAttributes.d).b();
            this.a(GenericAttributes.d).a(MathHelper.d(0.1, d1, d0));
         }

         if (this.O && this.H.W().b(GameRules.c)) {
            boolean flag = false;
            AxisAlignedBB axisalignedbb = this.cD().g(0.2);

            for(BlockPosition blockposition : BlockPosition.b(
               MathHelper.a(axisalignedbb.a),
               MathHelper.a(axisalignedbb.b),
               MathHelper.a(axisalignedbb.c),
               MathHelper.a(axisalignedbb.d),
               MathHelper.a(axisalignedbb.e),
               MathHelper.a(axisalignedbb.f)
            )) {
               IBlockData iblockdata = this.H.a_(blockposition);
               Block block = iblockdata.b();
               if (block instanceof BlockLeaves && !CraftEventFactory.callEntityChangeBlockEvent(this, blockposition, Blocks.a.o()).isCancelled()) {
                  flag = this.H.a(blockposition, true, this) || flag;
               }
            }

            if (!flag && this.N) {
               this.eS();
            }
         }

         if (this.cb > 0) {
            --this.cb;
            if (this.cb == 10) {
               this.ge();
            }
         }

         if (this.bZ > 0) {
            --this.bZ;
         }

         if (this.ca > 0) {
            --this.ca;
            this.gd();
            if (this.ca == 0) {
               this.a(SoundEffects.th, 1.0F, 1.0F);
               this.cb = 20;
            }
         }
      }
   }

   private void gd() {
      if (this.af.a(6) == 0) {
         double d0 = this.dl() - (double)this.dc() * Math.sin((double)(this.aT * (float) (Math.PI / 180.0))) + (this.af.j() * 0.6 - 0.3);
         double d1 = this.dn() + (double)this.dd() - 0.3;
         double d2 = this.dr() + (double)this.dc() * Math.cos((double)(this.aT * (float) (Math.PI / 180.0))) + (this.af.j() * 0.6 - 0.3);
         this.H.a(Particles.v, d0, d1, d2, 0.4980392156862745, 0.5137254901960784, 0.5725490196078431);
      }
   }

   @Override
   protected boolean eP() {
      return super.eP() || this.bZ > 0 || this.ca > 0 || this.cb > 0;
   }

   @Override
   public boolean B(Entity entity) {
      return this.ca <= 0 && this.cb <= 0 ? super.B(entity) : false;
   }

   @Override
   protected void e(EntityLiving entityliving) {
      if (this.cb == 0) {
         if (this.af.j() < 0.5) {
            this.ca = 40;
            this.a(SoundEffects.tg, 1.0F, 1.0F);
            this.H.a(this, (byte)39);
            entityliving.g((Entity)this);
         } else {
            this.a(entityliving);
         }

         entityliving.S = true;
      }
   }

   private void ge() {
      if (this.bq()) {
         for(EntityLiving entityliving : this.H.a(EntityLiving.class, this.cD().g(4.0), e)) {
            if (!(entityliving instanceof EntityIllagerAbstract)) {
               entityliving.a(this.dG().b((EntityLiving)this), 6.0F);
            }

            this.a(entityliving);
         }

         Vec3D vec3d = this.cD().f();

         for(int i = 0; i < 40; ++i) {
            double d0 = this.af.k() * 0.2;
            double d1 = this.af.k() * 0.2;
            double d2 = this.af.k() * 0.2;
            this.H.a(Particles.Y, vec3d.c, vec3d.d, vec3d.e, d0, d1, d2);
         }

         this.a(GameEvent.v);
      }
   }

   private void a(Entity entity) {
      double d0 = entity.dl() - this.dl();
      double d1 = entity.dr() - this.dr();
      double d2 = Math.max(d0 * d0 + d1 * d1, 0.001);
      entity.j(d0 / d2 * 4.0, 0.2, d1 / d2 * 4.0);
   }

   @Override
   public void b(byte b0) {
      if (b0 == 4) {
         this.bZ = 10;
         this.a(SoundEffects.tb, 1.0F, 1.0F);
      } else if (b0 == 39) {
         this.ca = 40;
      }

      super.b(b0);
   }

   public int r() {
      return this.bZ;
   }

   public int fS() {
      return this.ca;
   }

   public int gc() {
      return this.cb;
   }

   @Override
   public boolean z(Entity entity) {
      this.bZ = 10;
      this.H.a(this, (byte)4);
      this.a(SoundEffects.tb, 1.0F, 1.0F);
      return super.z(entity);
   }

   @Nullable
   @Override
   protected SoundEffect s() {
      return SoundEffects.ta;
   }

   @Override
   protected SoundEffect d(DamageSource damagesource) {
      return SoundEffects.te;
   }

   @Override
   protected SoundEffect x_() {
      return SoundEffects.td;
   }

   @Override
   protected void b(BlockPosition blockposition, IBlockData iblockdata) {
      this.a(SoundEffects.tf, 0.15F, 1.0F);
   }

   @Override
   public boolean a(IWorldReader iworldreader) {
      return !iworldreader.d(this.cD());
   }

   @Override
   public void a(int i, boolean flag) {
   }

   @Override
   public boolean fT() {
      return false;
   }

   private class a extends PathfinderGoalMeleeAttack {
      public a() {
         super(EntityRavager.this, 1.0, true);
      }

      @Override
      protected double a(EntityLiving entityliving) {
         float f = EntityRavager.this.dc() - 0.1F;
         return (double)(f * 2.0F * f * 2.0F + entityliving.dc());
      }
   }
}
