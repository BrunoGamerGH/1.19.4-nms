package net.minecraft.world.entity.projectile;

import net.minecraft.core.particles.Particles;
import net.minecraft.network.protocol.game.PacketPlayOutSpawnEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.animal.horse.EntityLlama;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.phys.MovingObjectPosition;
import net.minecraft.world.phys.MovingObjectPositionBlock;
import net.minecraft.world.phys.MovingObjectPositionEntity;
import net.minecraft.world.phys.Vec3D;

public class EntityLlamaSpit extends IProjectile {
   public EntityLlamaSpit(EntityTypes<? extends EntityLlamaSpit> entitytypes, World world) {
      super(entitytypes, world);
   }

   public EntityLlamaSpit(World world, EntityLlama entityllama) {
      this(EntityTypes.ak, world);
      this.b(entityllama);
      this.e(
         entityllama.dl() - (double)(entityllama.dc() + 1.0F) * 0.5 * (double)MathHelper.a(entityllama.aT * (float) (Math.PI / 180.0)),
         entityllama.dp() - 0.1F,
         entityllama.dr() + (double)(entityllama.dc() + 1.0F) * 0.5 * (double)MathHelper.b(entityllama.aT * (float) (Math.PI / 180.0))
      );
   }

   @Override
   public void l() {
      super.l();
      Vec3D vec3d = this.dj();
      MovingObjectPosition movingobjectposition = ProjectileHelper.a(this, this::a);
      this.preOnHit(movingobjectposition);
      double d0 = this.dl() + vec3d.c;
      double d1 = this.dn() + vec3d.d;
      double d2 = this.dr() + vec3d.e;
      this.A();
      float f = 0.99F;
      float f1 = 0.06F;
      if (this.H.a(this.cD()).noneMatch(BlockBase.BlockData::h)) {
         this.ai();
      } else if (this.aW()) {
         this.ai();
      } else {
         this.f(vec3d.a(0.99F));
         if (!this.aP()) {
            this.f(this.dj().b(0.0, -0.06F, 0.0));
         }

         this.e(d0, d1, d2);
      }
   }

   @Override
   protected void a(MovingObjectPositionEntity movingobjectpositionentity) {
      super.a(movingobjectpositionentity);
      Entity entity = this.v();
      if (entity instanceof EntityLiving entityliving) {
         movingobjectpositionentity.a().a(this.dG().a(this, entityliving), 1.0F);
      }
   }

   @Override
   protected void a(MovingObjectPositionBlock movingobjectpositionblock) {
      super.a(movingobjectpositionblock);
      if (!this.H.B) {
         this.ai();
      }
   }

   @Override
   protected void a_() {
   }

   @Override
   public void a(PacketPlayOutSpawnEntity packetplayoutspawnentity) {
      super.a(packetplayoutspawnentity);
      double d0 = packetplayoutspawnentity.h();
      double d1 = packetplayoutspawnentity.i();
      double d2 = packetplayoutspawnentity.j();

      for(int i = 0; i < 7; ++i) {
         double d3 = 0.4 + 0.1 * (double)i;
         this.H.a(Particles.ad, this.dl(), this.dn(), this.dr(), d0 * d3, d1, d2 * d3);
      }

      this.o(d0, d1, d2);
   }
}
