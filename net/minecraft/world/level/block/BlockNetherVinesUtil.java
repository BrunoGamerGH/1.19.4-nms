package net.minecraft.world.level.block;

import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.IBlockData;

public class BlockNetherVinesUtil {
   private static final double b = 0.826;
   public static final double a = 0.1;

   public static boolean a(IBlockData var0) {
      return var0.h();
   }

   public static int a(RandomSource var0) {
      double var1 = 1.0;

      int var3;
      for(var3 = 0; var0.j() < var1; ++var3) {
         var1 *= 0.826;
      }

      return var3;
   }
}
