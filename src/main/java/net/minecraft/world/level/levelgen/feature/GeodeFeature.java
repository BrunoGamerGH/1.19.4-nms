package net.minecraft.world.level.levelgen.feature;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.SystemUtils;
import net.minecraft.core.BaseBlockPosition;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.tags.TagsBlock;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BuddingAmethystBlock;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.levelgen.GeodeBlockSettings;
import net.minecraft.world.level.levelgen.GeodeCrackSettings;
import net.minecraft.world.level.levelgen.GeodeLayerSettings;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.SeededRandom;
import net.minecraft.world.level.levelgen.feature.configurations.GeodeConfiguration;
import net.minecraft.world.level.levelgen.synth.NoiseGeneratorNormal;
import net.minecraft.world.level.material.Fluid;

public class GeodeFeature extends WorldGenerator<GeodeConfiguration> {
   private static final EnumDirection[] a = EnumDirection.values();

   public GeodeFeature(Codec<GeodeConfiguration> var0) {
      super(var0);
   }

   @Override
   public boolean a(FeaturePlaceContext<GeodeConfiguration> var0) {
      GeodeConfiguration var1 = var0.f();
      RandomSource var2 = var0.d();
      BlockPosition var3 = var0.e();
      GeneratorAccessSeed var4 = var0.b();
      int var5 = var1.l;
      int var6 = var1.n;
      List<Pair<BlockPosition, Integer>> var7 = Lists.newLinkedList();
      int var8 = var1.j.a(var2);
      SeededRandom var9 = new SeededRandom(new LegacyRandomSource(var4.A()));
      NoiseGeneratorNormal var10 = NoiseGeneratorNormal.a(var9, -4, 1.0);
      List<BlockPosition> var11 = Lists.newLinkedList();
      double var12 = (double)var8 / (double)var1.i.b();
      GeodeLayerSettings var14 = var1.d;
      GeodeBlockSettings var15 = var1.c;
      GeodeCrackSettings var16 = var1.e;
      double var17 = 1.0 / Math.sqrt(var14.b);
      double var19 = 1.0 / Math.sqrt(var14.c + var12);
      double var21 = 1.0 / Math.sqrt(var14.d + var12);
      double var23 = 1.0 / Math.sqrt(var14.e + var12);
      double var25 = 1.0 / Math.sqrt(var16.c + var2.j() / 2.0 + (var8 > 3 ? var12 : 0.0));
      boolean var27 = (double)var2.i() < var16.b;
      int var28 = 0;

      for(int var29 = 0; var29 < var8; ++var29) {
         int var30 = var1.i.a(var2);
         int var31 = var1.i.a(var2);
         int var32 = var1.i.a(var2);
         BlockPosition var33 = var3.b(var30, var31, var32);
         IBlockData var34 = var4.a_(var33);
         if (var34.h() || var34.a(TagsBlock.bE)) {
            if (++var28 > var1.p) {
               return false;
            }
         }

         var7.add(Pair.of(var33, var1.k.a(var2)));
      }

      if (var27) {
         int var29 = var2.a(4);
         int var30 = var8 * 2 + 1;
         if (var29 == 0) {
            var11.add(var3.b(var30, 7, 0));
            var11.add(var3.b(var30, 5, 0));
            var11.add(var3.b(var30, 1, 0));
         } else if (var29 == 1) {
            var11.add(var3.b(0, 7, var30));
            var11.add(var3.b(0, 5, var30));
            var11.add(var3.b(0, 1, var30));
         } else if (var29 == 2) {
            var11.add(var3.b(var30, 7, var30));
            var11.add(var3.b(var30, 5, var30));
            var11.add(var3.b(var30, 1, var30));
         } else {
            var11.add(var3.b(0, 7, 0));
            var11.add(var3.b(0, 5, 0));
            var11.add(var3.b(0, 1, 0));
         }
      }

      List<BlockPosition> var29 = Lists.newArrayList();
      Predicate<IBlockData> var30 = a(var1.c.g);

      for(BlockPosition var32 : BlockPosition.a(var3.b(var5, var5, var5), var3.b(var6, var6, var6))) {
         double var33 = var10.a((double)var32.u(), (double)var32.v(), (double)var32.w()) * var1.o;
         double var35 = 0.0;
         double var37 = 0.0;

         for(Pair<BlockPosition, Integer> var40 : var7) {
            var35 += MathHelper.f(var32.j((BaseBlockPosition)var40.getFirst()) + (double)((Integer)var40.getSecond()).intValue()) + var33;
         }

         for(BlockPosition var40 : var11) {
            var37 += MathHelper.f(var32.j(var40) + (double)var16.d) + var33;
         }

         if (!(var35 < var23)) {
            if (var27 && var37 >= var25 && var35 < var17) {
               this.a(var4, var32, Blocks.a.o(), var30);

               for(EnumDirection var42 : a) {
                  BlockPosition var43 = var32.a(var42);
                  Fluid var44 = var4.b_(var43);
                  if (!var44.c()) {
                     var4.a(var43, var44.a(), 0);
                  }
               }
            } else if (var35 >= var17) {
               this.a(var4, var32, var15.a.a(var2, var32), var30);
            } else if (var35 >= var19) {
               boolean var39 = (double)var2.i() < var1.g;
               if (var39) {
                  this.a(var4, var32, var15.c.a(var2, var32), var30);
               } else {
                  this.a(var4, var32, var15.b.a(var2, var32), var30);
               }

               if ((!var1.h || var39) && (double)var2.i() < var1.f) {
                  var29.add(var32.i());
               }
            } else if (var35 >= var21) {
               this.a(var4, var32, var15.d.a(var2, var32), var30);
            } else if (var35 >= var23) {
               this.a(var4, var32, var15.e.a(var2, var32), var30);
            }
         }
      }

      List<IBlockData> var31 = var15.f;

      for(BlockPosition var33 : var29) {
         IBlockData var34 = SystemUtils.a(var31, var2);

         for(EnumDirection var38 : a) {
            if (var34.b(BlockProperties.P)) {
               var34 = var34.a(BlockProperties.P, var38);
            }

            BlockPosition var39 = var33.a(var38);
            IBlockData var40 = var4.a_(var39);
            if (var34.b(BlockProperties.C)) {
               var34 = var34.a(BlockProperties.C, Boolean.valueOf(var40.r().b()));
            }

            if (BuddingAmethystBlock.g(var40)) {
               this.a(var4, var39, var34, var30);
               break;
            }
         }
      }

      return true;
   }
}
