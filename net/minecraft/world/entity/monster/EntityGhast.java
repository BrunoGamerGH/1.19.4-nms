package net.minecraft.world.entity.monster;

import java.util.EnumSet;
import net.minecraft.core.BlockPosition;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.network.syncher.DataWatcherObject;
import net.minecraft.network.syncher.DataWatcherRegistry;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityFlying;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityPose;
import net.minecraft.world.entity.EntitySize;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumMobSpawn;
import net.minecraft.world.entity.ai.attributes.AttributeProvider;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.entity.ai.control.ControllerMove;
import net.minecraft.world.entity.ai.goal.PathfinderGoal;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalNearestAttackableTarget;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.projectile.EntityLargeFireball;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.World;
import net.minecraft.world.phys.AxisAlignedBB;
import net.minecraft.world.phys.Vec3D;

public class EntityGhast extends EntityFlying implements IMonster {
   private static final DataWatcherObject<Boolean> b = DataWatcher.a(EntityGhast.class, DataWatcherRegistry.k);
   private int c = 1;

   public EntityGhast(EntityTypes<? extends EntityGhast> entitytypes, World world) {
      super(entitytypes, world);
      this.bI = 5;
      this.bK = new EntityGhast.ControllerGhast(this);
   }

   @Override
   protected void x() {
      this.bN.a(5, new EntityGhast.PathfinderGoalGhastIdleMove(this));
      this.bN.a(7, new EntityGhast.PathfinderGoalGhastMoveTowardsTarget(this));
      this.bN.a(7, new EntityGhast.PathfinderGoalGhastAttackTarget(this));
      this.bO
         .a(
            1,
            new PathfinderGoalNearestAttackableTarget<>(
               this, EntityHuman.class, 10, true, false, entityliving -> Math.abs(entityliving.dn() - this.dn()) <= 4.0
            )
         );
   }

   public boolean q() {
      return this.am.a(b);
   }

   public void w(boolean flag) {
      this.am.b(b, flag);
   }

   public int r() {
      return this.c;
   }

   @Override
   protected boolean R() {
      return true;
   }

   private static boolean h(DamageSource damagesource) {
      return damagesource.c() instanceof EntityLargeFireball && damagesource.d() instanceof EntityHuman;
   }

   @Override
   public boolean b(DamageSource damagesource) {
      return !h(damagesource) && super.b(damagesource);
   }

   @Override
   public boolean a(DamageSource damagesource, float f) {
      if (h(damagesource)) {
         super.a(damagesource, 1000.0F);
         return true;
      } else {
         return this.b(damagesource) ? false : super.a(damagesource, f);
      }
   }

   @Override
   protected void a_() {
      super.a_();
      this.am.a(b, false);
   }

   public static AttributeProvider.Builder w() {
      return EntityInsentient.y().a(GenericAttributes.a, 10.0).a(GenericAttributes.b, 100.0);
   }

   @Override
   public SoundCategory cX() {
      return SoundCategory.f;
   }

   @Override
   protected SoundEffect s() {
      return SoundEffects.iU;
   }

   @Override
   protected SoundEffect d(DamageSource damagesource) {
      return SoundEffects.iW;
   }

   @Override
   protected SoundEffect x_() {
      return SoundEffects.iV;
   }

   @Override
   protected float eN() {
      return 5.0F;
   }

   public static boolean b(
      EntityTypes<EntityGhast> entitytypes, GeneratorAccess generatoraccess, EnumMobSpawn enummobspawn, BlockPosition blockposition, RandomSource randomsource
   ) {
      return generatoraccess.ah() != EnumDifficulty.a && randomsource.a(20) == 0 && a(entitytypes, generatoraccess, enummobspawn, blockposition, randomsource);
   }

   @Override
   public int fy() {
      return 1;
   }

   @Override
   public void b(NBTTagCompound nbttagcompound) {
      super.b(nbttagcompound);
      nbttagcompound.a("ExplosionPower", (byte)this.c);
   }

   @Override
   public void a(NBTTagCompound nbttagcompound) {
      super.a(nbttagcompound);
      if (nbttagcompound.b("ExplosionPower", 99)) {
         this.c = nbttagcompound.f("ExplosionPower");
      }
   }

   @Override
   protected float b(EntityPose entitypose, EntitySize entitysize) {
      return 2.6F;
   }

   private static class ControllerGhast extends ControllerMove {
      private final EntityGhast l;
      private int m;

      public ControllerGhast(EntityGhast entityghast) {
         super(entityghast);
         this.l = entityghast;
      }

      @Override
      public void a() {
         if (this.k == ControllerMove.Operation.b && this.m-- <= 0) {
            this.m += this.l.dZ().a(5) + 2;
            Vec3D vec3d = new Vec3D(this.e - this.l.dl(), this.f - this.l.dn(), this.g - this.l.dr());
            double d0 = vec3d.f();
            vec3d = vec3d.d();
            if (this.a(vec3d, MathHelper.c(d0))) {
               this.l.f(this.l.dj().e(vec3d.a(0.1)));
            } else {
               this.k = ControllerMove.Operation.a;
            }
         }
      }

      private boolean a(Vec3D vec3d, int i) {
         AxisAlignedBB axisalignedbb = this.l.cD();

         for(int j = 1; j < i; ++j) {
            axisalignedbb = axisalignedbb.c(vec3d);
            if (!this.l.H.a(this.l, axisalignedbb)) {
               return false;
            }
         }

         return true;
      }
   }

   private static class PathfinderGoalGhastAttackTarget extends PathfinderGoal {
      private final EntityGhast b;
      public int a;

      public PathfinderGoalGhastAttackTarget(EntityGhast entityghast) {
         this.b = entityghast;
      }

      @Override
      public boolean a() {
         return this.b.P_() != null;
      }

      @Override
      public void c() {
         this.a = 0;
      }

      @Override
      public void d() {
         this.b.w(false);
      }

      @Override
      public boolean J_() {
         return true;
      }

      @Override
      public void e() {
         EntityLiving entityliving = this.b.P_();
         if (entityliving != null) {
            double d0 = 64.0;
            if (entityliving.f(this.b) < 4096.0 && this.b.B(entityliving)) {
               World world = this.b.H;
               ++this.a;
               if (this.a == 10 && !this.b.aO()) {
                  world.a(null, 1015, this.b.dg(), 0);
               }

               if (this.a == 20) {
                  double d1 = 4.0;
                  Vec3D vec3d = this.b.j(1.0F);
                  double d2 = entityliving.dl() - (this.b.dl() + vec3d.c * 4.0);
                  double d3 = entityliving.e(0.5) - (0.5 + this.b.e(0.5));
                  double d4 = entityliving.dr() - (this.b.dr() + vec3d.e * 4.0);
                  if (!this.b.aO()) {
                     world.a(null, 1016, this.b.dg(), 0);
                  }

                  EntityLargeFireball entitylargefireball = new EntityLargeFireball(world, this.b, d2, d3, d4, this.b.r());
                  entitylargefireball.bukkitYield = (float)(entitylargefireball.e = this.b.r());
                  entitylargefireball.e(this.b.dl() + vec3d.c * 4.0, this.b.e(0.5) + 0.5, entitylargefireball.dr() + vec3d.e * 4.0);
                  world.b(entitylargefireball);
                  this.a = -40;
               }
            } else if (this.a > 0) {
               --this.a;
            }

            this.b.w(this.a > 10);
         }
      }
   }

   private static class PathfinderGoalGhastIdleMove extends PathfinderGoal {
      private final EntityGhast a;

      public PathfinderGoalGhastIdleMove(EntityGhast entityghast) {
         this.a = entityghast;
         this.a(EnumSet.of(PathfinderGoal.Type.a));
      }

      @Override
      public boolean a() {
         ControllerMove controllermove = this.a.D();
         if (!controllermove.b()) {
            return true;
         } else {
            double d0 = controllermove.d() - this.a.dl();
            double d1 = controllermove.e() - this.a.dn();
            double d2 = controllermove.f() - this.a.dr();
            double d3 = d0 * d0 + d1 * d1 + d2 * d2;
            return d3 < 1.0 || d3 > 3600.0;
         }
      }

      @Override
      public boolean b() {
         return false;
      }

      @Override
      public void c() {
         RandomSource randomsource = this.a.dZ();
         double d0 = this.a.dl() + (double)((randomsource.i() * 2.0F - 1.0F) * 16.0F);
         double d1 = this.a.dn() + (double)((randomsource.i() * 2.0F - 1.0F) * 16.0F);
         double d2 = this.a.dr() + (double)((randomsource.i() * 2.0F - 1.0F) * 16.0F);
         this.a.D().a(d0, d1, d2, 1.0);
      }
   }

   private static class PathfinderGoalGhastMoveTowardsTarget extends PathfinderGoal {
      private final EntityGhast a;

      public PathfinderGoalGhastMoveTowardsTarget(EntityGhast entityghast) {
         this.a = entityghast;
         this.a(EnumSet.of(PathfinderGoal.Type.b));
      }

      @Override
      public boolean a() {
         return true;
      }

      @Override
      public boolean J_() {
         return true;
      }

      @Override
      public void e() {
         if (this.a.P_() == null) {
            Vec3D vec3d = this.a.dj();
            this.a.f(-((float)MathHelper.d(vec3d.c, vec3d.e)) * (180.0F / (float)Math.PI));
            this.a.aT = this.a.dw();
         } else {
            EntityLiving entityliving = this.a.P_();
            double d0 = 64.0;
            if (entityliving.f(this.a) < 4096.0) {
               double d1 = entityliving.dl() - this.a.dl();
               double d2 = entityliving.dr() - this.a.dr();
               this.a.f(-((float)MathHelper.d(d1, d2)) * (180.0F / (float)Math.PI));
               this.a.aT = this.a.dw();
            }
         }
      }
   }
}
