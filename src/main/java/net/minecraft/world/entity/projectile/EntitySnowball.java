package net.minecraft.world.entity.projectile;

import net.minecraft.core.particles.ParticleParam;
import net.minecraft.core.particles.ParticleParamItem;
import net.minecraft.core.particles.Particles;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.monster.EntityBlaze;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.World;
import net.minecraft.world.phys.MovingObjectPosition;
import net.minecraft.world.phys.MovingObjectPositionEntity;

public class EntitySnowball extends EntityProjectileThrowable {
   public EntitySnowball(EntityTypes<? extends EntitySnowball> var0, World var1) {
      super(var0, var1);
   }

   public EntitySnowball(World var0, EntityLiving var1) {
      super(EntityTypes.aP, var1, var0);
   }

   public EntitySnowball(World var0, double var1, double var3, double var5) {
      super(EntityTypes.aP, var1, var3, var5, var0);
   }

   @Override
   protected Item j() {
      return Items.pK;
   }

   private ParticleParam p() {
      ItemStack var0 = this.k();
      return (ParticleParam)(var0.b() ? Particles.T : new ParticleParamItem(Particles.Q, var0));
   }

   @Override
   public void b(byte var0) {
      if (var0 == 3) {
         ParticleParam var1 = this.p();

         for(int var2 = 0; var2 < 8; ++var2) {
            this.H.a(var1, this.dl(), this.dn(), this.dr(), 0.0, 0.0, 0.0);
         }
      }
   }

   @Override
   protected void a(MovingObjectPositionEntity var0) {
      super.a(var0);
      Entity var1 = var0.a();
      int var2 = var1 instanceof EntityBlaze ? 3 : 0;
      var1.a(this.dG().b(this, this.v()), (float)var2);
   }

   @Override
   protected void a(MovingObjectPosition var0) {
      super.a(var0);
      if (!this.H.B) {
         this.H.a(this, (byte)3);
         this.ai();
      }
   }
}
