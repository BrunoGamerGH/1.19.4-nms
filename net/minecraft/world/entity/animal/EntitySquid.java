package net.minecraft.world.entity.animal;

import net.minecraft.core.BlockPosition;
import net.minecraft.core.particles.ParticleParam;
import net.minecraft.core.particles.Particles;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.tags.TagsFluid;
import net.minecraft.util.MathHelper;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityPose;
import net.minecraft.world.entity.EntitySize;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumMoveType;
import net.minecraft.world.entity.ai.attributes.AttributeProvider;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.entity.ai.goal.PathfinderGoal;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.Vec3D;

public class EntitySquid extends EntityWaterAnimal {
   public float b;
   public float c;
   public float d;
   public float e;
   public float bS;
   public float bT;
   public float bU;
   public float bV;
   private float bW;
   private float bX;
   private float bY;
   private float bZ;
   private float ca;
   private float cb;

   public EntitySquid(EntityTypes<? extends EntitySquid> entitytypes, World world) {
      super(entitytypes, world);
      this.af.b((long)this.af());
      this.bX = 1.0F / (this.af.i() + 1.0F) * 0.2F;
   }

   @Override
   protected void x() {
      this.bN.a(0, new EntitySquid.PathfinderGoalSquid(this));
      this.bN.a(1, new EntitySquid.a());
   }

   public static AttributeProvider.Builder fS() {
      return EntityInsentient.y().a(GenericAttributes.a, 10.0);
   }

   @Override
   protected float b(EntityPose entitypose, EntitySize entitysize) {
      return entitysize.b * 0.5F;
   }

   @Override
   protected SoundEffect s() {
      return SoundEffects.wN;
   }

   @Override
   protected SoundEffect d(DamageSource damagesource) {
      return SoundEffects.wP;
   }

   @Override
   protected SoundEffect x_() {
      return SoundEffects.wO;
   }

   protected SoundEffect r() {
      return SoundEffects.wQ;
   }

   @Override
   public boolean a(EntityHuman entityhuman) {
      return !this.fI();
   }

   @Override
   protected float eN() {
      return 0.4F;
   }

   @Override
   protected Entity.MovementEmission aQ() {
      return Entity.MovementEmission.c;
   }

   @Override
   public void b_() {
      super.b_();
      this.c = this.b;
      this.e = this.d;
      this.bT = this.bS;
      this.bV = this.bU;
      this.bS += this.bX;
      if ((double)this.bS > Math.PI * 2) {
         if (this.H.B) {
            this.bS = (float) (Math.PI * 2);
         } else {
            this.bS -= (float) (Math.PI * 2);
            if (this.af.a(10) == 0) {
               this.bX = 1.0F / (this.af.i() + 1.0F) * 0.2F;
            }

            this.H.a(this, (byte)19);
         }
      }

      if (this.aW()) {
         if (this.bS < (float) Math.PI) {
            float f = this.bS / (float) Math.PI;
            this.bU = MathHelper.a(f * f * (float) Math.PI) * (float) Math.PI * 0.25F;
            if ((double)f > 0.75) {
               this.bW = 1.0F;
               this.bY = 1.0F;
            } else {
               this.bY *= 0.8F;
            }
         } else {
            this.bU = 0.0F;
            this.bW *= 0.9F;
            this.bY *= 0.99F;
         }

         if (!this.H.B) {
            this.o((double)(this.bZ * this.bW), (double)(this.ca * this.bW), (double)(this.cb * this.bW));
         }

         Vec3D vec3d = this.dj();
         double d0 = vec3d.h();
         this.aT += (-((float)MathHelper.d(vec3d.c, vec3d.e)) * (180.0F / (float)Math.PI) - this.aT) * 0.1F;
         this.f(this.aT);
         this.d += (float) Math.PI * this.bY * 1.5F;
         this.b += (-((float)MathHelper.d(d0, vec3d.d)) * (180.0F / (float)Math.PI) - this.b) * 0.1F;
      } else {
         this.bU = MathHelper.e(MathHelper.a(this.bS)) * (float) Math.PI * 0.25F;
         if (!this.H.B) {
            double d1 = this.dj().d;
            if (this.a(MobEffects.y)) {
               d1 = 0.05 * (double)(this.b(MobEffects.y).e() + 1);
            } else if (!this.aP()) {
               d1 -= 0.08;
            }

            this.o(0.0, d1 * 0.98F, 0.0);
         }

         this.b += (-90.0F - this.b) * 0.02F;
      }
   }

   @Override
   public boolean a(DamageSource damagesource, float f) {
      if (super.a(damagesource, f) && this.ea() != null) {
         if (!this.H.B) {
            this.w();
         }

         return true;
      } else {
         return false;
      }
   }

   private Vec3D j(Vec3D vec3d) {
      Vec3D vec3d1 = vec3d.a(this.c * (float) (Math.PI / 180.0));
      return vec3d1.b(-this.aU * (float) (Math.PI / 180.0));
   }

   private void w() {
      this.a(this.r(), this.eN(), this.eO());
      Vec3D vec3d = this.j(new Vec3D(0.0, -1.0, 0.0)).b(this.dl(), this.dn(), this.dr());

      for(int i = 0; i < 30; ++i) {
         Vec3D vec3d1 = this.j(new Vec3D((double)this.af.i() * 0.6 - 0.3, -1.0, (double)this.af.i() * 0.6 - 0.3));
         Vec3D vec3d2 = vec3d1.a(0.3 + (double)(this.af.i() * 2.0F));
         ((WorldServer)this.H).a(this.q(), vec3d.c, vec3d.d + 0.5, vec3d.e, 0, vec3d2.c, vec3d2.d, vec3d2.e, 0.1F);
      }
   }

   protected ParticleParam q() {
      return Particles.ae;
   }

   @Override
   public void h(Vec3D vec3d) {
      this.a(EnumMoveType.a, this.dj());
   }

   @Override
   public void b(byte b0) {
      if (b0 == 19) {
         this.bS = 0.0F;
      } else {
         super.b(b0);
      }
   }

   public void a(float f, float f1, float f2) {
      this.bZ = f;
      this.ca = f1;
      this.cb = f2;
   }

   public boolean fT() {
      return this.bZ != 0.0F || this.ca != 0.0F || this.cb != 0.0F;
   }

   private class PathfinderGoalSquid extends PathfinderGoal {
      private final EntitySquid b;

      public PathfinderGoalSquid(EntitySquid entitysquid) {
         this.b = entitysquid;
      }

      @Override
      public boolean a() {
         return true;
      }

      @Override
      public void e() {
         int i = this.b.ee();
         if (i > 100) {
            this.b.a(0.0F, 0.0F, 0.0F);
         } else if (this.b.dZ().a(b(50)) == 0 || !this.b.ah || !this.b.fT()) {
            float f = this.b.dZ().i() * (float) (Math.PI * 2);
            float f1 = MathHelper.b(f) * 0.2F;
            float f2 = -0.1F + this.b.dZ().i() * 0.2F;
            float f3 = MathHelper.a(f) * 0.2F;
            this.b.a(f1, f2, f3);
         }
      }
   }

   private class a extends PathfinderGoal {
      private static final float b = 3.0F;
      private static final float c = 5.0F;
      private static final float d = 10.0F;
      private int e;

      a() {
      }

      @Override
      public boolean a() {
         EntityLiving entityliving = EntitySquid.this.ea();
         return EntitySquid.this.aT() && entityliving != null ? EntitySquid.this.f(entityliving) < 100.0 : false;
      }

      @Override
      public void c() {
         this.e = 0;
      }

      @Override
      public boolean J_() {
         return true;
      }

      @Override
      public void e() {
         ++this.e;
         EntityLiving entityliving = EntitySquid.this.ea();
         if (entityliving != null) {
            Vec3D vec3d = new Vec3D(
               EntitySquid.this.dl() - entityliving.dl(), EntitySquid.this.dn() - entityliving.dn(), EntitySquid.this.dr() - entityliving.dr()
            );
            IBlockData iblockdata = EntitySquid.this.H
               .a_(BlockPosition.a(EntitySquid.this.dl() + vec3d.c, EntitySquid.this.dn() + vec3d.d, EntitySquid.this.dr() + vec3d.e));
            Fluid fluid = EntitySquid.this.H
               .b_(BlockPosition.a(EntitySquid.this.dl() + vec3d.c, EntitySquid.this.dn() + vec3d.d, EntitySquid.this.dr() + vec3d.e));
            if (fluid.a(TagsFluid.a) || iblockdata.h()) {
               double d0 = vec3d.f();
               if (d0 > 0.0) {
                  vec3d.d();
                  double d1 = 3.0;
                  if (d0 > 5.0) {
                     d1 -= (d0 - 5.0) / 5.0;
                  }

                  if (d1 > 0.0) {
                     vec3d = vec3d.a(d1);
                  }
               }

               if (iblockdata.h()) {
                  vec3d = vec3d.a(0.0, vec3d.d, 0.0);
               }

               EntitySquid.this.a((float)vec3d.c / 20.0F, (float)vec3d.d / 20.0F, (float)vec3d.e / 20.0F);
            }

            if (this.e % 10 == 5) {
               EntitySquid.this.H.a(Particles.e, EntitySquid.this.dl(), EntitySquid.this.dn(), EntitySquid.this.dr(), 0.0, 0.0, 0.0);
            }
         }
      }
   }
}
