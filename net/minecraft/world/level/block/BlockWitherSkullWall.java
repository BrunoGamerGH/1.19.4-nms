package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;

public class BlockWitherSkullWall extends BlockSkullWall {
   protected BlockWitherSkullWall(BlockBase.Info var0) {
      super(BlockSkull.Type.b, var0);
   }

   @Override
   public void a(World var0, BlockPosition var1, IBlockData var2, @Nullable EntityLiving var3, ItemStack var4) {
      Blocks.gF.a(var0, var1, var2, var3, var4);
   }
}
