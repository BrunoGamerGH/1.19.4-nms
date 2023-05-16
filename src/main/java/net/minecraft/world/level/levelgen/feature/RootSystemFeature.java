package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.function.Predicate;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.tags.TagsFluid;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.configurations.RootSystemConfiguration;

public class RootSystemFeature extends WorldGenerator<RootSystemConfiguration> {
   public RootSystemFeature(Codec<RootSystemConfiguration> var0) {
      super(var0);
   }

   @Override
   public boolean a(FeaturePlaceContext<RootSystemConfiguration> var0) {
      GeneratorAccessSeed var1 = var0.b();
      BlockPosition var2 = var0.e();
      if (!var1.a_(var2).h()) {
         return false;
      } else {
         RandomSource var3 = var0.d();
         BlockPosition var4 = var0.e();
         RootSystemConfiguration var5 = var0.f();
         BlockPosition.MutableBlockPosition var6 = var4.j();
         if (a(var1, var0.c(), var5, var3, var6, var4)) {
            a(var1, var5, var3, var4, var6);
         }

         return true;
      }
   }

   private static boolean a(GeneratorAccessSeed var0, RootSystemConfiguration var1, BlockPosition var2) {
      BlockPosition.MutableBlockPosition var3 = var2.j();

      for(int var4 = 1; var4 <= var1.c; ++var4) {
         var3.c(EnumDirection.b);
         IBlockData var5 = var0.a_(var3);
         if (!a(var5, var4, var1.n)) {
            return false;
         }
      }

      return true;
   }

   private static boolean a(IBlockData var0, int var1, int var2) {
      if (var0.h()) {
         return true;
      } else {
         int var3 = var1 + 1;
         return var3 <= var2 && var0.r().a(TagsFluid.a);
      }
   }

   private static boolean a(
      GeneratorAccessSeed var0,
      ChunkGenerator var1,
      RootSystemConfiguration var2,
      RandomSource var3,
      BlockPosition.MutableBlockPosition var4,
      BlockPosition var5
   ) {
      for(int var6 = 0; var6 < var2.h; ++var6) {
         var4.c(EnumDirection.b);
         if (var2.o.test(var0, var4) && a(var0, var2, var4)) {
            BlockPosition var7 = var4.d();
            if (var0.b_(var7).a(TagsFluid.b) || !var0.a_(var7).d().b()) {
               return false;
            }

            if (var2.b.a().a(var0, var1, var3, var4)) {
               a(var5, var5.v() + var6, var0, var2, var3);
               return true;
            }
         }
      }

      return false;
   }

   private static void a(BlockPosition var0, int var1, GeneratorAccessSeed var2, RootSystemConfiguration var3, RandomSource var4) {
      int var5 = var0.u();
      int var6 = var0.w();
      BlockPosition.MutableBlockPosition var7 = var0.j();

      for(int var8 = var0.v(); var8 < var1; ++var8) {
         a(var2, var3, var4, var5, var6, var7.d(var5, var8, var6));
      }
   }

   private static void a(
      GeneratorAccessSeed var0, RootSystemConfiguration var1, RandomSource var2, int var3, int var4, BlockPosition.MutableBlockPosition var5
   ) {
      int var6 = var1.d;
      Predicate<IBlockData> var7 = var1x -> var1x.a(var1.e);

      for(int var8 = 0; var8 < var1.g; ++var8) {
         var5.a(var5, var2.a(var6) - var2.a(var6), 0, var2.a(var6) - var2.a(var6));
         if (var7.test(var0.a_(var5))) {
            var0.a(var5, var1.f.a(var2, var5), 2);
         }

         var5.p(var3);
         var5.r(var4);
      }
   }

   private static void a(
      GeneratorAccessSeed var0, RootSystemConfiguration var1, RandomSource var2, BlockPosition var3, BlockPosition.MutableBlockPosition var4
   ) {
      int var5 = var1.i;
      int var6 = var1.j;

      for(int var7 = 0; var7 < var1.l; ++var7) {
         var4.a(var3, var2.a(var5) - var2.a(var5), var2.a(var6) - var2.a(var6), var2.a(var5) - var2.a(var5));
         if (var0.w(var4)) {
            IBlockData var8 = var1.k.a(var2, var4);
            if (var8.a(var0, var4) && var0.a_(var4.c()).d(var0, var4, EnumDirection.a)) {
               var0.a(var4, var8, 2);
            }
         }
      }
   }
}
