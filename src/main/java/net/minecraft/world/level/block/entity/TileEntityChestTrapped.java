package net.minecraft.world.level.block.entity;

import net.minecraft.core.BlockPosition;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.IBlockData;

public class TileEntityChestTrapped extends TileEntityChest {
   public TileEntityChestTrapped(BlockPosition var0, IBlockData var1) {
      super(TileEntityTypes.c, var0, var1);
   }

   @Override
   protected void a(World var0, BlockPosition var1, IBlockData var2, int var3, int var4) {
      super.a(var0, var1, var2, var3, var4);
      if (var3 != var4) {
         Block var5 = var2.b();
         var0.a(var1, var5);
         var0.a(var1.d(), var5);
      }
   }
}
