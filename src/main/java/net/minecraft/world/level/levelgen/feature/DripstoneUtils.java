package net.minecraft.world.level.levelgen.feature;

import java.util.function.Consumer;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.tags.TagsBlock;
import net.minecraft.util.MathHelper;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PointedDripstoneBlock;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.DripstoneThickness;

public class DripstoneUtils {
   protected static double a(double var0, double var2, double var4, double var6) {
      if (var0 < var6) {
         var0 = var6;
      }

      double var8 = 0.384;
      double var10 = var0 / var2 * 0.384;
      double var12 = 0.75 * Math.pow(var10, 1.3333333333333333);
      double var14 = Math.pow(var10, 0.6666666666666666);
      double var16 = 0.3333333333333333 * Math.log(var10);
      double var18 = var4 * (var12 - var14 - var16);
      var18 = Math.max(var18, 0.0);
      return var18 / 0.384 * var2;
   }

   protected static boolean a(GeneratorAccessSeed var0, BlockPosition var1, int var2) {
      if (b(var0, var1)) {
         return false;
      } else {
         float var3 = 6.0F;
         float var4 = 6.0F / (float)var2;

         for(float var5 = 0.0F; var5 < (float) (Math.PI * 2); var5 += var4) {
            int var6 = (int)(MathHelper.b(var5) * (float)var2);
            int var7 = (int)(MathHelper.a(var5) * (float)var2);
            if (b(var0, var1.b(var6, 0, var7))) {
               return false;
            }
         }

         return true;
      }
   }

   protected static boolean a(GeneratorAccess var0, BlockPosition var1) {
      return var0.a(var1, DripstoneUtils::c);
   }

   protected static boolean b(GeneratorAccess var0, BlockPosition var1) {
      return var0.a(var1, DripstoneUtils::e);
   }

   protected static void a(EnumDirection var0, int var1, boolean var2, Consumer<IBlockData> var3) {
      if (var1 >= 3) {
         var3.accept(a(var0, DripstoneThickness.e));

         for(int var4 = 0; var4 < var1 - 3; ++var4) {
            var3.accept(a(var0, DripstoneThickness.d));
         }
      }

      if (var1 >= 2) {
         var3.accept(a(var0, DripstoneThickness.c));
      }

      if (var1 >= 1) {
         var3.accept(a(var0, var2 ? DripstoneThickness.a : DripstoneThickness.b));
      }
   }

   protected static void a(GeneratorAccess var0, BlockPosition var1, EnumDirection var2, int var3, boolean var4) {
      if (b(var0.a_(var1.a(var2.g())))) {
         BlockPosition.MutableBlockPosition var5 = var1.j();
         a(var2, var3, var4, var3x -> {
            if (var3x.a(Blocks.rn)) {
               var3x = var3x.a(PointedDripstoneBlock.c, Boolean.valueOf(var0.B(var5)));
            }

            var0.a(var5, var3x, 2);
            var5.c(var2);
         });
      }
   }

   protected static boolean c(GeneratorAccess var0, BlockPosition var1) {
      IBlockData var2 = var0.a_(var1);
      if (var2.a(TagsBlock.bn)) {
         var0.a(var1, Blocks.ro.o(), 2);
         return true;
      } else {
         return false;
      }
   }

   private static IBlockData a(EnumDirection var0, DripstoneThickness var1) {
      return Blocks.rn.o().a(PointedDripstoneBlock.a, var0).a(PointedDripstoneBlock.b, var1);
   }

   public static boolean a(IBlockData var0) {
      return b(var0) || var0.a(Blocks.H);
   }

   public static boolean b(IBlockData var0) {
      return var0.a(Blocks.ro) || var0.a(TagsBlock.bn);
   }

   public static boolean c(IBlockData var0) {
      return var0.h() || var0.a(Blocks.G);
   }

   public static boolean d(IBlockData var0) {
      return !var0.h() && !var0.a(Blocks.G);
   }

   public static boolean e(IBlockData var0) {
      return var0.h() || var0.a(Blocks.G) || var0.a(Blocks.H);
   }
}
