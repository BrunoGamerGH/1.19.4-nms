package net.minecraft.world.level.block;

import net.minecraft.core.BlockPosition;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.phys.Vec3D;

public class BlockSlime extends BlockHalfTransparent {
   public BlockSlime(BlockBase.Info var0) {
      super(var0);
   }

   @Override
   public void a(World var0, IBlockData var1, BlockPosition var2, Entity var3, float var4) {
      if (var3.bQ()) {
         super.a(var0, var1, var2, var3, var4);
      } else {
         var3.a(var4, 0.0F, var0.af().k());
      }
   }

   @Override
   public void a(IBlockAccess var0, Entity var1) {
      if (var1.bQ()) {
         super.a(var0, var1);
      } else {
         this.a(var1);
      }
   }

   private void a(Entity var0) {
      Vec3D var1 = var0.dj();
      if (var1.d < 0.0) {
         double var2 = var0 instanceof EntityLiving ? 1.0 : 0.8;
         var0.o(var1.c, -var1.d * var2, var1.e);
      }
   }

   @Override
   public void a(World var0, BlockPosition var1, IBlockData var2, Entity var3) {
      double var4 = Math.abs(var3.dj().d);
      if (var4 < 0.1 && !var3.bP()) {
         double var6 = 0.4 + var4 * 0.2;
         var3.f(var3.dj().d(var6, 1.0, var6));
      }

      super.a(var0, var1, var2, var3);
   }
}
