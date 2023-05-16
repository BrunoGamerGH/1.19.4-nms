package net.minecraft;

import com.google.common.annotations.VisibleForTesting;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntStack;
import java.util.Optional;
import java.util.function.Predicate;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.IBlockData;

public class BlockUtil {
   public static BlockUtil.Rectangle a(
      BlockPosition var0, EnumDirection.EnumAxis var1, int var2, EnumDirection.EnumAxis var3, int var4, Predicate<BlockPosition> var5
   ) {
      BlockPosition.MutableBlockPosition var6 = var0.j();
      EnumDirection var7 = EnumDirection.a(EnumDirection.EnumAxisDirection.b, var1);
      EnumDirection var8 = var7.g();
      EnumDirection var9 = EnumDirection.a(EnumDirection.EnumAxisDirection.b, var3);
      EnumDirection var10 = var9.g();
      int var11 = a(var5, var6.g(var0), var7, var2);
      int var12 = a(var5, var6.g(var0), var8, var2);
      int var13 = var11;
      BlockUtil.IntBounds[] var14 = new BlockUtil.IntBounds[var11 + 1 + var12];
      var14[var11] = new BlockUtil.IntBounds(a(var5, var6.g(var0), var9, var4), a(var5, var6.g(var0), var10, var4));
      int var15 = var14[var11].a;

      for(int var16 = 1; var16 <= var11; ++var16) {
         BlockUtil.IntBounds var17 = var14[var13 - (var16 - 1)];
         var14[var13 - var16] = new BlockUtil.IntBounds(
            a(var5, var6.g(var0).c(var7, var16), var9, var17.a), a(var5, var6.g(var0).c(var7, var16), var10, var17.b)
         );
      }

      for(int var16 = 1; var16 <= var12; ++var16) {
         BlockUtil.IntBounds var17 = var14[var13 + var16 - 1];
         var14[var13 + var16] = new BlockUtil.IntBounds(
            a(var5, var6.g(var0).c(var8, var16), var9, var17.a), a(var5, var6.g(var0).c(var8, var16), var10, var17.b)
         );
      }

      int var16 = 0;
      int var17 = 0;
      int var18 = 0;
      int var19 = 0;
      int[] var20 = new int[var14.length];

      for(int var21 = var15; var21 >= 0; --var21) {
         for(int var22 = 0; var22 < var14.length; ++var22) {
            BlockUtil.IntBounds var23 = var14[var22];
            int var24 = var15 - var23.a;
            int var25 = var15 + var23.b;
            var20[var22] = var21 >= var24 && var21 <= var25 ? var25 + 1 - var21 : 0;
         }

         Pair<BlockUtil.IntBounds, Integer> var22 = a(var20);
         BlockUtil.IntBounds var23 = (BlockUtil.IntBounds)var22.getFirst();
         int var24 = 1 + var23.b - var23.a;
         int var25 = var22.getSecond();
         if (var24 * var25 > var18 * var19) {
            var16 = var23.a;
            var17 = var21;
            var18 = var24;
            var19 = var25;
         }
      }

      return new BlockUtil.Rectangle(var0.a(var1, var16 - var13).a(var3, var17 - var15), var18, var19);
   }

   private static int a(Predicate<BlockPosition> var0, BlockPosition.MutableBlockPosition var1, EnumDirection var2, int var3) {
      int var4 = 0;

      while(var4 < var3 && var0.test(var1.c(var2))) {
         ++var4;
      }

      return var4;
   }

   @VisibleForTesting
   static Pair<BlockUtil.IntBounds, Integer> a(int[] var0) {
      int var1 = 0;
      int var2 = 0;
      int var3 = 0;
      IntStack var4 = new IntArrayList();
      var4.push(0);

      for(int var5 = 1; var5 <= var0.length; ++var5) {
         int var6 = var5 == var0.length ? 0 : var0[var5];

         while(!var4.isEmpty()) {
            int var7 = var0[var4.topInt()];
            if (var6 >= var7) {
               var4.push(var5);
               break;
            }

            var4.popInt();
            int var8 = var4.isEmpty() ? 0 : var4.topInt() + 1;
            if (var7 * (var5 - var8) > var3 * (var2 - var1)) {
               var2 = var5;
               var1 = var8;
               var3 = var7;
            }
         }

         if (var4.isEmpty()) {
            var4.push(var5);
         }
      }

      return new Pair(new BlockUtil.IntBounds(var1, var2 - 1), var3);
   }

   public static Optional<BlockPosition> a(IBlockAccess var0, BlockPosition var1, Block var2, EnumDirection var3, Block var4) {
      BlockPosition.MutableBlockPosition var5 = var1.j();

      IBlockData var6;
      do {
         var5.c(var3);
         var6 = var0.a_(var5);
      } while(var6.a(var2));

      return var6.a(var4) ? Optional.of(var5) : Optional.empty();
   }

   public static class IntBounds {
      public final int a;
      public final int b;

      public IntBounds(int var0, int var1) {
         this.a = var0;
         this.b = var1;
      }

      @Override
      public String toString() {
         return "IntBounds{min=" + this.a + ", max=" + this.b + "}";
      }
   }

   public static class Rectangle {
      public final BlockPosition a;
      public final int b;
      public final int c;

      public Rectangle(BlockPosition var0, int var1, int var2) {
         this.a = var0;
         this.b = var1;
         this.c = var2;
      }
   }
}
