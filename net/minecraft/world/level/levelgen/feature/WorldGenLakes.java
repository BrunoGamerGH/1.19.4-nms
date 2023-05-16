package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPosition;
import net.minecraft.tags.TagsBlock;
import net.minecraft.tags.TagsFluid;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.WorldGenFeatureStateProvider;
import net.minecraft.world.level.material.Material;

@Deprecated
public class WorldGenLakes extends WorldGenerator<WorldGenLakes.a> {
   private static final IBlockData a = Blocks.mY.o();

   public WorldGenLakes(Codec<WorldGenLakes.a> var0) {
      super(var0);
   }

   @Override
   public boolean a(FeaturePlaceContext<WorldGenLakes.a> var0) {
      BlockPosition var1 = var0.e();
      GeneratorAccessSeed var2 = var0.b();
      RandomSource var3 = var0.d();
      WorldGenLakes.a var4 = var0.f();
      if (var1.v() <= var2.v_() + 4) {
         return false;
      } else {
         var1 = var1.c(4);
         boolean[] var5 = new boolean[2048];
         int var6 = var3.a(4) + 4;

         for(int var7 = 0; var7 < var6; ++var7) {
            double var8 = var3.j() * 6.0 + 3.0;
            double var10 = var3.j() * 4.0 + 2.0;
            double var12 = var3.j() * 6.0 + 3.0;
            double var14 = var3.j() * (16.0 - var8 - 2.0) + 1.0 + var8 / 2.0;
            double var16 = var3.j() * (8.0 - var10 - 4.0) + 2.0 + var10 / 2.0;
            double var18 = var3.j() * (16.0 - var12 - 2.0) + 1.0 + var12 / 2.0;

            for(int var20 = 1; var20 < 15; ++var20) {
               for(int var21 = 1; var21 < 15; ++var21) {
                  for(int var22 = 1; var22 < 7; ++var22) {
                     double var23 = ((double)var20 - var14) / (var8 / 2.0);
                     double var25 = ((double)var22 - var16) / (var10 / 2.0);
                     double var27 = ((double)var21 - var18) / (var12 / 2.0);
                     double var29 = var23 * var23 + var25 * var25 + var27 * var27;
                     if (var29 < 1.0) {
                        var5[(var20 * 16 + var21) * 8 + var22] = true;
                     }
                  }
               }
            }
         }

         IBlockData var7 = var4.a().a(var3, var1);

         for(int var8 = 0; var8 < 16; ++var8) {
            for(int var9 = 0; var9 < 16; ++var9) {
               for(int var10 = 0; var10 < 8; ++var10) {
                  boolean var11 = !var5[(var8 * 16 + var9) * 8 + var10]
                     && (
                        var8 < 15 && var5[((var8 + 1) * 16 + var9) * 8 + var10]
                           || var8 > 0 && var5[((var8 - 1) * 16 + var9) * 8 + var10]
                           || var9 < 15 && var5[(var8 * 16 + var9 + 1) * 8 + var10]
                           || var9 > 0 && var5[(var8 * 16 + (var9 - 1)) * 8 + var10]
                           || var10 < 7 && var5[(var8 * 16 + var9) * 8 + var10 + 1]
                           || var10 > 0 && var5[(var8 * 16 + var9) * 8 + (var10 - 1)]
                     );
                  if (var11) {
                     Material var12 = var2.a_(var1.b(var8, var10, var9)).d();
                     if (var10 >= 4 && var12.a()) {
                        return false;
                     }

                     if (var10 < 4 && !var12.b() && var2.a_(var1.b(var8, var10, var9)) != var7) {
                        return false;
                     }
                  }
               }
            }
         }

         for(int var8 = 0; var8 < 16; ++var8) {
            for(int var9 = 0; var9 < 16; ++var9) {
               for(int var10 = 0; var10 < 8; ++var10) {
                  if (var5[(var8 * 16 + var9) * 8 + var10]) {
                     BlockPosition var11 = var1.b(var8, var10, var9);
                     if (this.c(var2.a_(var11))) {
                        boolean var12 = var10 >= 4;
                        var2.a(var11, var12 ? a : var7, 2);
                        if (var12) {
                           var2.a(var11, a.b(), 0);
                           this.a(var2, var11);
                        }
                     }
                  }
               }
            }
         }

         IBlockData var8 = var4.b().a(var3, var1);
         if (!var8.h()) {
            for(int var9 = 0; var9 < 16; ++var9) {
               for(int var10 = 0; var10 < 16; ++var10) {
                  for(int var11 = 0; var11 < 8; ++var11) {
                     boolean var12 = !var5[(var9 * 16 + var10) * 8 + var11]
                        && (
                           var9 < 15 && var5[((var9 + 1) * 16 + var10) * 8 + var11]
                              || var9 > 0 && var5[((var9 - 1) * 16 + var10) * 8 + var11]
                              || var10 < 15 && var5[(var9 * 16 + var10 + 1) * 8 + var11]
                              || var10 > 0 && var5[(var9 * 16 + (var10 - 1)) * 8 + var11]
                              || var11 < 7 && var5[(var9 * 16 + var10) * 8 + var11 + 1]
                              || var11 > 0 && var5[(var9 * 16 + var10) * 8 + (var11 - 1)]
                        );
                     if (var12 && (var11 < 4 || var3.a(2) != 0)) {
                        IBlockData var13 = var2.a_(var1.b(var9, var11, var10));
                        if (var13.d().b() && !var13.a(TagsBlock.bD)) {
                           BlockPosition var14 = var1.b(var9, var11, var10);
                           var2.a(var14, var8, 2);
                           this.a(var2, var14);
                        }
                     }
                  }
               }
            }
         }

         if (var7.r().a(TagsFluid.a)) {
            for(int var9 = 0; var9 < 16; ++var9) {
               for(int var10 = 0; var10 < 16; ++var10) {
                  int var11 = 4;
                  BlockPosition var12 = var1.b(var9, 4, var10);
                  if (var2.v(var12).a().a(var2, var12, false) && this.c(var2.a_(var12))) {
                     var2.a(var12, Blocks.dN.o(), 2);
                  }
               }
            }
         }

         return true;
      }
   }

   private boolean c(IBlockData var0) {
      return !var0.a(TagsBlock.bC);
   }

   public static record a(WorldGenFeatureStateProvider fluid, WorldGenFeatureStateProvider barrier) implements WorldGenFeatureConfiguration {
      private final WorldGenFeatureStateProvider b;
      private final WorldGenFeatureStateProvider c;
      public static final Codec<WorldGenLakes.a> a = RecordCodecBuilder.create(
         var0 -> var0.group(
                  WorldGenFeatureStateProvider.a.fieldOf("fluid").forGetter(WorldGenLakes.a::a),
                  WorldGenFeatureStateProvider.a.fieldOf("barrier").forGetter(WorldGenLakes.a::b)
               )
               .apply(var0, WorldGenLakes.a::new)
      );

      public a(WorldGenFeatureStateProvider var0, WorldGenFeatureStateProvider var1) {
         this.b = var0;
         this.c = var1;
      }

      public WorldGenFeatureStateProvider a() {
         return this.b;
      }

      public WorldGenFeatureStateProvider b() {
         return this.c;
      }
   }
}
