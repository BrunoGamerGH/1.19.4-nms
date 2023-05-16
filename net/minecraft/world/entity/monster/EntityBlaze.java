package net.minecraft.world.entity.monster;

import java.util.EnumSet;
import net.minecraft.core.particles.Particles;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.network.syncher.DataWatcherObject;
import net.minecraft.network.syncher.DataWatcherRegistry;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.ai.attributes.AttributeProvider;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.entity.ai.goal.PathfinderGoal;
import net.minecraft.world.entity.ai.goal.PathfinderGoalLookAtPlayer;
import net.minecraft.world.entity.ai.goal.PathfinderGoalMoveTowardsRestriction;
import net.minecraft.world.entity.ai.goal.PathfinderGoalRandomLookaround;
import net.minecraft.world.entity.ai.goal.PathfinderGoalRandomStrollLand;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalHurtByTarget;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalNearestAttackableTarget;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.projectile.EntitySmallFireball;
import net.minecraft.world.level.World;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3D;

public class EntityBlaze extends EntityMonster {
   private float b = 0.5F;
   private int c;
   private static final DataWatcherObject<Byte> d = DataWatcher.a(EntityBlaze.class, DataWatcherRegistry.a);

   public EntityBlaze(EntityTypes<? extends EntityBlaze> var0, World var1) {
      super(var0, var1);
      this.a(PathType.j, -1.0F);
      this.a(PathType.i, 8.0F);
      this.a(PathType.n, 0.0F);
      this.a(PathType.o, 0.0F);
      this.bI = 10;
   }

   @Override
   protected void x() {
      this.bN.a(4, new EntityBlaze.PathfinderGoalBlazeFireball(this));
      this.bN.a(5, new PathfinderGoalMoveTowardsRestriction(this, 1.0));
      this.bN.a(7, new PathfinderGoalRandomStrollLand(this, 1.0, 0.0F));
      this.bN.a(8, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
      this.bN.a(8, new PathfinderGoalRandomLookaround(this));
      this.bO.a(1, new PathfinderGoalHurtByTarget(this).a());
      this.bO.a(2, new PathfinderGoalNearestAttackableTarget<>(this, EntityHuman.class, true));
   }

   public static AttributeProvider.Builder q() {
      return EntityMonster.fY().a(GenericAttributes.f, 6.0).a(GenericAttributes.d, 0.23F).a(GenericAttributes.b, 48.0);
   }

   @Override
   protected void a_() {
      super.a_();
      this.am.a(d, (byte)0);
   }

   @Override
   protected SoundEffect s() {
      return SoundEffects.bQ;
   }

   @Override
   protected SoundEffect d(DamageSource var0) {
      return SoundEffects.bT;
   }

   @Override
   protected SoundEffect x_() {
      return SoundEffects.bS;
   }

   @Override
   public float bh() {
      return 1.0F;
   }

   @Override
   public void b_() {
      if (!this.N && this.dj().d < 0.0) {
         this.f(this.dj().d(1.0, 0.6, 1.0));
      }

      if (this.H.B) {
         if (this.af.a(24) == 0 && !this.aO()) {
            this.H.a(this.dl() + 0.5, this.dn() + 0.5, this.dr() + 0.5, SoundEffects.bR, this.cX(), 1.0F + this.af.i(), this.af.i() * 0.7F + 0.3F, false);
         }

         for(int var0 = 0; var0 < 2; ++var0) {
            this.H.a(Particles.U, this.d(0.5), this.do(), this.g(0.5), 0.0, 0.0, 0.0);
         }
      }

      super.b_();
   }

   @Override
   public boolean eX() {
      return true;
   }

   @Override
   protected void U() {
      --this.c;
      if (this.c <= 0) {
         this.c = 100;
         this.b = (float)this.af.a(0.5, 6.891);
      }

      EntityLiving var0 = this.P_();
      if (var0 != null && var0.dp() > this.dp() + (double)this.b && this.c(var0)) {
         Vec3D var1 = this.dj();
         this.f(this.dj().b(0.0, (0.3F - var1.d) * 0.3F, 0.0));
         this.at = true;
      }

      super.U();
   }

   @Override
   public boolean bK() {
      return this.r();
   }

   private boolean r() {
      return (this.am.a(d) & 1) != 0;
   }

   void w(boolean var0) {
      byte var1 = this.am.a(d);
      if (var0) {
         var1 = (byte)(var1 | 1);
      } else {
         var1 = (byte)(var1 & -2);
      }

      this.am.b(d, var1);
   }

   static class PathfinderGoalBlazeFireball extends PathfinderGoal {
      private final EntityBlaze a;
      private int b;
      private int c;
      private int d;

      public PathfinderGoalBlazeFireball(EntityBlaze var0) {
         this.a = var0;
         this.a(EnumSet.of(PathfinderGoal.Type.a, PathfinderGoal.Type.b));
      }

      @Override
      public boolean a() {
         EntityLiving var0 = this.a.P_();
         return var0 != null && var0.bq() && this.a.c(var0);
      }

      @Override
      public void c() {
         this.b = 0;
      }

      @Override
      public void d() {
         this.a.w(false);
         this.d = 0;
      }

      @Override
      public boolean J_() {
         return true;
      }

      @Override
      public void e() {
         --this.c;
         EntityLiving var0 = this.a.P_();
         if (var0 != null) {
            boolean var1 = this.a.I().a(var0);
            if (var1) {
               this.d = 0;
            } else {
               ++this.d;
            }

            double var2 = this.a.f(var0);
            if (var2 < 4.0) {
               if (!var1) {
                  return;
               }

               if (this.c <= 0) {
                  this.c = 20;
                  this.a.z(var0);
               }

               this.a.D().a(var0.dl(), var0.dn(), var0.dr(), 1.0);
            } else if (var2 < this.h() * this.h() && var1) {
               double var4 = var0.dl() - this.a.dl();
               double var6 = var0.e(0.5) - this.a.e(0.5);
               double var8 = var0.dr() - this.a.dr();
               if (this.c <= 0) {
                  ++this.b;
                  if (this.b == 1) {
                     this.c = 60;
                     this.a.w(true);
                  } else if (this.b <= 4) {
                     this.c = 6;
                  } else {
                     this.c = 100;
                     this.b = 0;
                     this.a.w(false);
                  }

                  if (this.b > 1) {
                     double var10 = Math.sqrt(Math.sqrt(var2)) * 0.5;
                     if (!this.a.aO()) {
                        this.a.H.a(null, 1018, this.a.dg(), 0);
                     }

                     for(int var12 = 0; var12 < 1; ++var12) {
                        EntitySmallFireball var13 = new EntitySmallFireball(
                           this.a.H, this.a, this.a.dZ().a(var4, 2.297 * var10), var6, this.a.dZ().a(var8, 2.297 * var10)
                        );
                        var13.e(var13.dl(), this.a.e(0.5) + 0.5, var13.dr());
                        this.a.H.b(var13);
                     }
                  }
               }

               this.a.C().a(var0, 10.0F, 10.0F);
            } else if (this.d < 5) {
               this.a.D().a(var0.dl(), var0.dn(), var0.dr(), 1.0);
            }

            super.e();
         }
      }

      private double h() {
         return this.a.b(GenericAttributes.b);
      }
   }
}
