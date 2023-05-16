package net.minecraft.world.entity;

import net.minecraft.core.BlockPosition;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.phys.Vec3D;

public abstract class EntityFlying extends EntityInsentient {
   protected EntityFlying(EntityTypes<? extends EntityFlying> var0, World var1) {
      super(var0, var1);
   }

   @Override
   protected void a(double var0, boolean var2, IBlockData var3, BlockPosition var4) {
   }

   @Override
   public void h(Vec3D var0) {
      if (this.cT()) {
         if (this.aT()) {
            this.a(0.02F, var0);
            this.a(EnumMoveType.a, this.dj());
            this.f(this.dj().a(0.8F));
         } else if (this.bg()) {
            this.a(0.02F, var0);
            this.a(EnumMoveType.a, this.dj());
            this.f(this.dj().a(0.5));
         } else {
            float var1 = 0.91F;
            if (this.N) {
               var1 = this.H.a_(BlockPosition.a(this.dl(), this.dn() - 1.0, this.dr())).b().i() * 0.91F;
            }

            float var2 = 0.16277137F / (var1 * var1 * var1);
            var1 = 0.91F;
            if (this.N) {
               var1 = this.H.a_(BlockPosition.a(this.dl(), this.dn() - 1.0, this.dr())).b().i() * 0.91F;
            }

            this.a(this.N ? 0.1F * var2 : 0.02F, var0);
            this.a(EnumMoveType.a, this.dj());
            this.f(this.dj().a((double)var1));
         }
      }

      this.q(false);
   }

   @Override
   public boolean z_() {
      return false;
   }
}
