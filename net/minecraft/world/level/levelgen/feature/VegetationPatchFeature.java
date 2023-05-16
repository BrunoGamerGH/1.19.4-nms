package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.configurations.VegetationPatchConfiguration;

public class VegetationPatchFeature extends WorldGenerator<VegetationPatchConfiguration> {
   public VegetationPatchFeature(Codec<VegetationPatchConfiguration> var0) {
      super(var0);
   }

   @Override
   public boolean a(FeaturePlaceContext<VegetationPatchConfiguration> var0) {
      GeneratorAccessSeed var1 = var0.b();
      VegetationPatchConfiguration var2 = var0.f();
      RandomSource var3 = var0.d();
      BlockPosition var4 = var0.e();
      Predicate<IBlockData> var5 = var1x -> var1x.a(var2.b);
      int var6 = var2.j.a(var3) + 1;
      int var7 = var2.j.a(var3) + 1;
      Set<BlockPosition> var8 = this.a(var1, var2, var3, var4, var5, var6, var7);
      this.a(var0, var1, var2, var3, var8, var6, var7);
      return !var8.isEmpty();
   }

   protected Set<BlockPosition> a(
      GeneratorAccessSeed var0, VegetationPatchConfiguration var1, RandomSource var2, BlockPosition var3, Predicate<IBlockData> var4, int var5, int var6
   ) {
      BlockPosition.MutableBlockPosition var7 = var3.j();
      BlockPosition.MutableBlockPosition var8 = var7.j();
      EnumDirection var9 = var1.e.a();
      EnumDirection var10 = var9.g();
      Set<BlockPosition> var11 = new HashSet<>();

      for(int var12 = -var5; var12 <= var5; ++var12) {
         boolean var13 = var12 == -var5 || var12 == var5;

         for(int var14 = -var6; var14 <= var6; ++var14) {
            boolean var15 = var14 == -var6 || var14 == var6;
            boolean var16 = var13 || var15;
            boolean var17 = var13 && var15;
            boolean var18 = var16 && !var17;
            if (!var17 && (!var18 || var1.k != 0.0F && !(var2.i() > var1.k))) {
               var7.a(var3, var12, 0, var14);

               for(int var19 = 0; var0.a(var7, BlockBase.BlockData::h) && var19 < var1.h; ++var19) {
                  var7.c(var9);
               }

               for(int var25 = 0; var0.a(var7, var0x -> !var0x.h()) && var25 < var1.h; ++var25) {
                  var7.c(var10);
               }

               var8.a(var7, var1.e.a());
               IBlockData var20 = var0.a_(var8);
               if (var0.w(var7) && var20.d(var0, var8, var1.e.a().g())) {
                  int var21 = var1.f.a(var2) + (var1.g > 0.0F && var2.i() < var1.g ? 1 : 0);
                  BlockPosition var22 = var8.i();
                  boolean var23 = this.a(var0, var1, var4, var2, var8, var21);
                  if (var23) {
                     var11.add(var22);
                  }
               }
            }
         }
      }

      return var11;
   }

   protected void a(
      FeaturePlaceContext<VegetationPatchConfiguration> var0,
      GeneratorAccessSeed var1,
      VegetationPatchConfiguration var2,
      RandomSource var3,
      Set<BlockPosition> var4,
      int var5,
      int var6
   ) {
      for(BlockPosition var8 : var4) {
         if (var2.i > 0.0F && var3.i() < var2.i) {
            this.a(var1, var2, var0.c(), var3, var8);
         }
      }
   }

   protected boolean a(GeneratorAccessSeed var0, VegetationPatchConfiguration var1, ChunkGenerator var2, RandomSource var3, BlockPosition var4) {
      return var1.d.a().a(var0, var2, var3, var4.a(var1.e.a().g()));
   }

   protected boolean a(
      GeneratorAccessSeed var0,
      VegetationPatchConfiguration var1,
      Predicate<IBlockData> var2,
      RandomSource var3,
      BlockPosition.MutableBlockPosition var4,
      int var5
   ) {
      for(int var6 = 0; var6 < var5; ++var6) {
         IBlockData var7 = var1.c.a(var3, var4);
         IBlockData var8 = var0.a_(var4);
         if (!var7.a(var8.b())) {
            if (!var2.test(var8)) {
               return var6 != 0;
            }

            var0.a(var4, var7, 2);
            var4.c(var1.e.a());
         }
      }

      return true;
   }
}
