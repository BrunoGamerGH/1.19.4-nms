package net.minecraft.world.entity.projectile;

import net.minecraft.core.BlockPosition;
import net.minecraft.core.particles.Particles;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.entity.TileEntityEndGateway;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.phys.MovingObjectPosition;
import net.minecraft.world.phys.MovingObjectPositionBlock;
import net.minecraft.world.phys.Vec3D;

public abstract class EntityProjectile extends IProjectile {
   protected EntityProjectile(EntityTypes<? extends EntityProjectile> entitytypes, World world) {
      super(entitytypes, world);
   }

   protected EntityProjectile(EntityTypes<? extends EntityProjectile> entitytypes, double d0, double d1, double d2, World world) {
      this(entitytypes, world);
      this.e(d0, d1, d2);
   }

   protected EntityProjectile(EntityTypes<? extends EntityProjectile> entitytypes, EntityLiving entityliving, World world) {
      this(entitytypes, entityliving.dl(), entityliving.dp() - 0.1F, entityliving.dr(), world);
      this.b((Entity)entityliving);
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
      super.l();
      MovingObjectPosition movingobjectposition = ProjectileHelper.a(this, this::a);
      boolean flag = false;
      if (movingobjectposition.c() == MovingObjectPosition.EnumMovingObjectType.b) {
         BlockPosition blockposition = ((MovingObjectPositionBlock)movingobjectposition).a();
         IBlockData iblockdata = this.H.a_(blockposition);
         if (iblockdata.a(Blocks.ed)) {
            this.d(blockposition);
            flag = true;
         } else if (iblockdata.a(Blocks.kC)) {
            TileEntity tileentity = this.H.c_(blockposition);
            if (tileentity instanceof TileEntityEndGateway && TileEntityEndGateway.a(this)) {
               TileEntityEndGateway.a(this.H, blockposition, iblockdata, this, (TileEntityEndGateway)tileentity);
            }

            flag = true;
         }
      }

      if (movingobjectposition.c() != MovingObjectPosition.EnumMovingObjectType.a && !flag) {
         this.preOnHit(movingobjectposition);
      }

      this.aL();
      Vec3D vec3d = this.dj();
      double d0 = this.dl() + vec3d.c;
      double d1 = this.dn() + vec3d.d;
      double d2 = this.dr() + vec3d.e;
      this.A();
      float f;
      if (this.aT()) {
         for(int i = 0; i < 4; ++i) {
            float f1 = 0.25F;
            this.H.a(Particles.e, d0 - vec3d.c * 0.25, d1 - vec3d.d * 0.25, d2 - vec3d.e * 0.25, vec3d.c, vec3d.d, vec3d.e);
         }

         f = 0.8F;
      } else {
         f = 0.99F;
      }

      this.f(vec3d.a((double)f));
      if (!this.aP()) {
         Vec3D vec3d1 = this.dj();
         this.o(vec3d1.c, vec3d1.d - (double)this.o(), vec3d1.e);
      }

      this.e(d0, d1, d2);
   }

   protected float o() {
      return 0.03F;
   }
}
