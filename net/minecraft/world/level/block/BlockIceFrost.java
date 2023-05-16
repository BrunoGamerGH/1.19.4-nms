package net.minecraft.world.level.block;

import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockStateInteger;

public class BlockIceFrost extends BlockIce {
   public static final int a = 3;
   public static final BlockStateInteger b = BlockProperties.as;
   private static final int c = 4;
   private static final int d = 2;

   public BlockIceFrost(BlockBase.Info var0) {
      super(var0);
      this.k(this.D.b().a(b, Integer.valueOf(0)));
   }

   @Override
   public void b(IBlockData var0, WorldServer var1, BlockPosition var2, RandomSource var3) {
      this.a(var0, var1, var2, var3);
   }

   @Override
   public void a(IBlockData var0, WorldServer var1, BlockPosition var2, RandomSource var3) {
      if ((var3.a(3) == 0 || this.a(var1, var2, 4)) && var1.C(var2) > 11 - var0.c(b) - var0.b(var1, var2) && this.e(var0, var1, var2)) {
         BlockPosition.MutableBlockPosition var4 = new BlockPosition.MutableBlockPosition();

         for(EnumDirection var8 : EnumDirection.values()) {
            var4.a(var2, var8);
            IBlockData var9 = var1.a_(var4);
            if (var9.a(this) && !this.e(var9, var1, var4)) {
               var1.a(var4, this, MathHelper.a(var3, 20, 40));
            }
         }
      } else {
         var1.a(var2, this, MathHelper.a(var3, 20, 40));
      }
   }

   private boolean e(IBlockData var0, World var1, BlockPosition var2) {
      int var3 = var0.c(b);
      if (var3 < 3) {
         var1.a(var2, var0.a(b, Integer.valueOf(var3 + 1)), 2);
         return false;
      } else {
         this.d(var0, var1, var2);
         return true;
      }
   }

   @Override
   public void a(IBlockData var0, World var1, BlockPosition var2, Block var3, BlockPosition var4, boolean var5) {
      if (var3.o().a(this) && this.a(var1, var2, 2)) {
         this.d(var0, var1, var2);
      }

      super.a(var0, var1, var2, var3, var4, var5);
   }

   private boolean a(IBlockAccess var0, BlockPosition var1, int var2) {
      int var3 = 0;
      BlockPosition.MutableBlockPosition var4 = new BlockPosition.MutableBlockPosition();

      for(EnumDirection var8 : EnumDirection.values()) {
         var4.a(var1, var8);
         if (var0.a_(var4).a(this)) {
            if (++var3 >= var2) {
               return false;
            }
         }
      }

      return true;
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> var0) {
      var0.a(b);
   }

   @Override
   public ItemStack a(IBlockAccess var0, BlockPosition var1, IBlockData var2) {
      return ItemStack.b;
   }
}
