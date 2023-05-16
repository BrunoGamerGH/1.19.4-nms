package net.minecraft.world.level.levelgen.feature.trunkplacers;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.VirtualLevelReadable;
import net.minecraft.world.level.block.BlockRotatable;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureTreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.WorldGenFoilagePlacer;

public class TrunkPlacerFancy extends TrunkPlacer {
   public static final Codec<TrunkPlacerFancy> a = RecordCodecBuilder.create(var0 -> a(var0).apply(var0, TrunkPlacerFancy::new));
   private static final double b = 0.618;
   private static final double h = 1.382;
   private static final double i = 0.381;
   private static final double j = 0.328;

   public TrunkPlacerFancy(int var0, int var1, int var2) {
      super(var0, var1, var2);
   }

   @Override
   protected TrunkPlacers<?> a() {
      return TrunkPlacers.f;
   }

   @Override
   public List<WorldGenFoilagePlacer.a> a(
      VirtualLevelReadable var0,
      BiConsumer<BlockPosition, IBlockData> var1,
      RandomSource var2,
      int var3,
      BlockPosition var4,
      WorldGenFeatureTreeConfiguration var5
   ) {
      int var6 = 5;
      int var7 = var3 + 2;
      int var8 = MathHelper.a((double)var7 * 0.618);
      a(var0, var1, var2, var4.d(), var5);
      double var9 = 1.0;
      int var11 = Math.min(1, MathHelper.a(1.382 + Math.pow(1.0 * (double)var7 / 13.0, 2.0)));
      int var12 = var4.v() + var8;
      int var13 = var7 - 5;
      List<TrunkPlacerFancy.a> var14 = Lists.newArrayList();
      var14.add(new TrunkPlacerFancy.a(var4.b(var13), var12));

      for(; var13 >= 0; --var13) {
         float var15 = b(var7, var13);
         if (!(var15 < 0.0F)) {
            for(int var16 = 0; var16 < var11; ++var16) {
               double var17 = 1.0;
               double var19 = 1.0 * (double)var15 * ((double)var2.i() + 0.328);
               double var21 = (double)(var2.i() * 2.0F) * Math.PI;
               double var23 = var19 * Math.sin(var21) + 0.5;
               double var25 = var19 * Math.cos(var21) + 0.5;
               BlockPosition var27 = var4.b(MathHelper.a(var23), var13 - 1, MathHelper.a(var25));
               BlockPosition var28 = var27.b(5);
               if (this.a(var0, var1, var2, var27, var28, false, var5)) {
                  int var29 = var4.u() - var27.u();
                  int var30 = var4.w() - var27.w();
                  double var31 = (double)var27.v() - Math.sqrt((double)(var29 * var29 + var30 * var30)) * 0.381;
                  int var33 = var31 > (double)var12 ? var12 : (int)var31;
                  BlockPosition var34 = new BlockPosition(var4.u(), var33, var4.w());
                  if (this.a(var0, var1, var2, var34, var27, false, var5)) {
                     var14.add(new TrunkPlacerFancy.a(var27, var34.v()));
                  }
               }
            }
         }
      }

      this.a(var0, var1, var2, var4, var4.b(var8), true, var5);
      this.a(var0, var1, var2, var7, var4, var14, var5);
      List<WorldGenFoilagePlacer.a> var15 = Lists.newArrayList();

      for(TrunkPlacerFancy.a var17 : var14) {
         if (this.a(var7, var17.a() - var4.v())) {
            var15.add(var17.a);
         }
      }

      return var15;
   }

   private boolean a(
      VirtualLevelReadable var0,
      BiConsumer<BlockPosition, IBlockData> var1,
      RandomSource var2,
      BlockPosition var3,
      BlockPosition var4,
      boolean var5,
      WorldGenFeatureTreeConfiguration var6
   ) {
      if (!var5 && Objects.equals(var3, var4)) {
         return true;
      } else {
         BlockPosition var7 = var4.b(-var3.u(), -var3.v(), -var3.w());
         int var8 = this.a(var7);
         float var9 = (float)var7.u() / (float)var8;
         float var10 = (float)var7.v() / (float)var8;
         float var11 = (float)var7.w() / (float)var8;

         for(int var12 = 0; var12 <= var8; ++var12) {
            BlockPosition var13 = var3.b(
               MathHelper.d(0.5F + (float)var12 * var9), MathHelper.d(0.5F + (float)var12 * var10), MathHelper.d(0.5F + (float)var12 * var11)
            );
            if (var5) {
               this.a(var0, var1, var2, var13, var6, var2x -> var2x.b(BlockRotatable.g, this.a(var3, var13)));
            } else if (!this.b(var0, var13)) {
               return false;
            }
         }

         return true;
      }
   }

   private int a(BlockPosition var0) {
      int var1 = MathHelper.a(var0.u());
      int var2 = MathHelper.a(var0.v());
      int var3 = MathHelper.a(var0.w());
      return Math.max(var1, Math.max(var2, var3));
   }

   private EnumDirection.EnumAxis a(BlockPosition var0, BlockPosition var1) {
      EnumDirection.EnumAxis var2 = EnumDirection.EnumAxis.b;
      int var3 = Math.abs(var1.u() - var0.u());
      int var4 = Math.abs(var1.w() - var0.w());
      int var5 = Math.max(var3, var4);
      if (var5 > 0) {
         if (var3 == var5) {
            var2 = EnumDirection.EnumAxis.a;
         } else {
            var2 = EnumDirection.EnumAxis.c;
         }
      }

      return var2;
   }

   private boolean a(int var0, int var1) {
      return (double)var1 >= (double)var0 * 0.2;
   }

   private void a(
      VirtualLevelReadable var0,
      BiConsumer<BlockPosition, IBlockData> var1,
      RandomSource var2,
      int var3,
      BlockPosition var4,
      List<TrunkPlacerFancy.a> var5,
      WorldGenFeatureTreeConfiguration var6
   ) {
      for(TrunkPlacerFancy.a var8 : var5) {
         int var9 = var8.a();
         BlockPosition var10 = new BlockPosition(var4.u(), var9, var4.w());
         if (!var10.equals(var8.a.a()) && this.a(var3, var9 - var4.v())) {
            this.a(var0, var1, var2, var10, var8.a.a(), true, var6);
         }
      }
   }

   private static float b(int var0, int var1) {
      if ((float)var1 < (float)var0 * 0.3F) {
         return -1.0F;
      } else {
         float var2 = (float)var0 / 2.0F;
         float var3 = var2 - (float)var1;
         float var4 = MathHelper.c(var2 * var2 - var3 * var3);
         if (var3 == 0.0F) {
            var4 = var2;
         } else if (Math.abs(var3) >= var2) {
            return 0.0F;
         }

         return var4 * 0.5F;
      }
   }

   static class a {
      final WorldGenFoilagePlacer.a a;
      private final int b;

      public a(BlockPosition var0, int var1) {
         this.a = new WorldGenFoilagePlacer.a(var0, 0, false);
         this.b = var1;
      }

      public int a() {
         return this.b;
      }
   }
}
