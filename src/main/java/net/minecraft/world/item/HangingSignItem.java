package net.minecraft.world.item;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WallHangingSignBlock;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.entity.TileEntitySign;
import net.minecraft.world.level.block.state.IBlockData;

public class HangingSignItem extends ItemBlockWallable {
   public HangingSignItem(Block var0, Block var1, Item.Info var2) {
      super(var0, var1, var2, EnumDirection.b);
   }

   @Override
   protected boolean a(IWorldReader var0, IBlockData var1, BlockPosition var2) {
      Block var5 = var1.b();
      if (var5 instanceof WallHangingSignBlock var3 && !var3.b(var1, var0, var2)) {
         return false;
      }

      return super.a(var0, var1, var2);
   }

   @Override
   protected boolean a(BlockPosition var0, World var1, @Nullable EntityHuman var2, ItemStack var3, IBlockData var4) {
      boolean var5 = super.a(var0, var1, var2, var3, var4);
      if (!var1.B && !var5 && var2 != null) {
         TileEntity var8 = var1.c_(var0);
         if (var8 instanceof TileEntitySign var6) {
            var2.a(var6);
         }
      }

      return var5;
   }
}
