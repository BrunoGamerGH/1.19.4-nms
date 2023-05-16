package net.minecraft.world.entity.projectile;

import net.minecraft.core.particles.ParticleParam;
import net.minecraft.core.particles.Particles;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.PacketListenerPlayOut;
import net.minecraft.network.protocol.game.PacketPlayOutSpawnEntity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.level.World;
import net.minecraft.world.phys.MovingObjectPosition;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;

public abstract class EntityFireball extends IProjectile {
   public double b;
   public double c;
   public double d;
   public float bukkitYield = 1.0F;
   public boolean isIncendiary = true;

   protected EntityFireball(EntityTypes<? extends EntityFireball> entitytypes, World world) {
      super(entitytypes, world);
   }

   public EntityFireball(EntityTypes<? extends EntityFireball> entitytypes, double d0, double d1, double d2, double d3, double d4, double d5, World world) {
      this(entitytypes, world);
      this.b(d0, d1, d2, this.dw(), this.dy());
      this.an();
      this.setDirection(d3, d4, d5);
   }

   public void setDirection(double d3, double d4, double d5) {
      double d6 = Math.sqrt(d3 * d3 + d4 * d4 + d5 * d5);
      if (d6 != 0.0) {
         this.b = d3 / d6 * 0.1;
         this.c = d4 / d6 * 0.1;
         this.d = d5 / d6 * 0.1;
      }
   }

   public EntityFireball(EntityTypes<? extends EntityFireball> entitytypes, EntityLiving entityliving, double d0, double d1, double d2, World world) {
      this(entitytypes, entityliving.dl(), entityliving.dn(), entityliving.dr(), d0, d1, d2, world);
      this.b(entityliving);
      this.a(entityliving.dw(), entityliving.dy());
   }

   @Override
   protected void a_() {
   }

   @Override
   public boolean a(double d0) {
      double d1 = this.cD().a() * 4.0;
      if (Double.isNaN(d1)) {
         d1 = 4.0;
      }

      d1 *= 64.0;
      return d0 < d1 * d1;
   }

   @Override
   public void l() {
      Entity entity = this.v();
      if (this.H.B || (entity == null || !entity.dB()) && this.H.D(this.dg())) {
         super.l();
         if (this.Z_()) {
            this.f(1);
         }

         MovingObjectPosition movingobjectposition = ProjectileHelper.a(this, this::a);
         if (movingobjectposition.c() != MovingObjectPosition.EnumMovingObjectType.a) {
            this.preOnHit(movingobjectposition);
            if (this.dB()) {
               CraftEventFactory.callProjectileHitEvent(this, movingobjectposition);
            }
         }

         this.aL();
         Vec3D vec3d = this.dj();
         double d0 = this.dl() + vec3d.c;
         double d1 = this.dn() + vec3d.d;
         double d2 = this.dr() + vec3d.e;
         ProjectileHelper.a(this, 0.2F);
         float f = this.k();
         if (this.aT()) {
            for(int i = 0; i < 4; ++i) {
               float f1 = 0.25F;
               this.H.a(Particles.e, d0 - vec3d.c * 0.25, d1 - vec3d.d * 0.25, d2 - vec3d.e * 0.25, vec3d.c, vec3d.d, vec3d.e);
            }

            f = 0.8F;
         }

         this.f(vec3d.b(this.b, this.c, this.d).a((double)f));
         this.H.a(this.j(), d0, d1 + 0.5, d2, 0.0, 0.0, 0.0);
         this.e(d0, d1, d2);
      } else {
         this.ai();
      }
   }

   @Override
   protected boolean a(Entity entity) {
      return super.a(entity) && !entity.ae;
   }

   protected boolean Z_() {
      return true;
   }

   protected ParticleParam j() {
      return Particles.ab;
   }

   protected float k() {
      return 0.95F;
   }

   @Override
   public void b(NBTTagCompound nbttagcompound) {
      super.b(nbttagcompound);
      nbttagcompound.a("power", this.a(new double[]{this.b, this.c, this.d}));
   }

   @Override
   public void a(NBTTagCompound nbttagcompound) {
      super.a(nbttagcompound);
      if (nbttagcompound.b("power", 9)) {
         NBTTagList nbttaglist = nbttagcompound.c("power", 6);
         if (nbttaglist.size() == 3) {
            this.b = nbttaglist.h(0);
            this.c = nbttaglist.h(1);
            this.d = nbttaglist.h(2);
         }
      }
   }

   @Override
   public boolean bm() {
      return true;
   }

   @Override
   public float bB() {
      return 1.0F;
   }

   @Override
   public boolean a(DamageSource damagesource, float f) {
      if (this.b(damagesource)) {
         return false;
      } else {
         this.bj();
         Entity entity = damagesource.d();
         if (entity != null) {
            if (!this.H.B) {
               if (CraftEventFactory.handleNonLivingEntityDamageEvent(this, damagesource, (double)f, false)) {
                  return false;
               }

               Vec3D vec3d = entity.bC();
               this.f(vec3d);
               this.b = vec3d.c * 0.1;
               this.c = vec3d.d * 0.1;
               this.d = vec3d.e * 0.1;
               this.b(entity);
            }

            return true;
         } else {
            return false;
         }
      }
   }

   @Override
   public float bh() {
      return 1.0F;
   }

   @Override
   public Packet<PacketListenerPlayOut> S() {
      Entity entity = this.v();
      int i = entity == null ? 0 : entity.af();
      return new PacketPlayOutSpawnEntity(
         this.af(), this.cs(), this.dl(), this.dn(), this.dr(), this.dy(), this.dw(), this.ae(), i, new Vec3D(this.b, this.c, this.d), 0.0
      );
   }

   @Override
   public void a(PacketPlayOutSpawnEntity packetplayoutspawnentity) {
      super.a(packetplayoutspawnentity);
      double d0 = packetplayoutspawnentity.h();
      double d1 = packetplayoutspawnentity.i();
      double d2 = packetplayoutspawnentity.j();
      double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
      if (d3 != 0.0) {
         this.b = d0 / d3 * 0.1;
         this.c = d1 / d3 * 0.1;
         this.d = d2 / d3 * 0.1;
      }
   }
}
