package net.minecraft.world.level.levelgen.feature.trunkplacers;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.function.BiConsumer;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.VirtualLevelReadable;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.levelgen.feature.WorldGenTrees;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureTreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.WorldGenFoilagePlacer;

public class TrunkPlacerDarkOak extends TrunkPlacer {
   public static final Codec<TrunkPlacerDarkOak> a = RecordCodecBuilder.create(var0 -> a(var0).apply(var0, TrunkPlacerDarkOak::new));

   public TrunkPlacerDarkOak(int var0, int var1, int var2) {
      super(var0, var1, var2);
   }

   @Override
   protected TrunkPlacers<?> a() {
      return TrunkPlacers.e;
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
      List<WorldGenFoilagePlacer.a> var6 = Lists.newArrayList();
      BlockPosition var7 = var4.d();
      a(var0, var1, var2, var7, var5);
      a(var0, var1, var2, var7.h(), var5);
      a(var0, var1, var2, var7.f(), var5);
      a(var0, var1, var2, var7.f().h(), var5);
      EnumDirection var8 = EnumDirection.EnumDirectionLimit.a.a(var2);
      int var9 = var3 - var2.a(4);
      int var10 = 2 - var2.a(3);
      int var11 = var4.u();
      int var12 = var4.v();
      int var13 = var4.w();
      int var14 = var11;
      int var15 = var13;
      int var16 = var12 + var3 - 1;

      for(int var17 = 0; var17 < var3; ++var17) {
         if (var17 >= var9 && var10 > 0) {
            var14 += var8.j();
            var15 += var8.l();
            --var10;
         }

         int var18 = var12 + var17;
         BlockPosition var19 = new BlockPosition(var14, var18, var15);
         if (WorldGenTrees.c(var0, var19)) {
            this.b(var0, var1, var2, var19, var5);
            this.b(var0, var1, var2, var19.h(), var5);
            this.b(var0, var1, var2, var19.f(), var5);
            this.b(var0, var1, var2, var19.h().f(), var5);
         }
      }

      var6.add(new WorldGenFoilagePlacer.a(new BlockPosition(var14, var16, var15), 0, true));

      for(int var17 = -1; var17 <= 2; ++var17) {
         for(int var18 = -1; var18 <= 2; ++var18) {
            if ((var17 < 0 || var17 > 1 || var18 < 0 || var18 > 1) && var2.a(3) <= 0) {
               int var19 = var2.a(3) + 2;

               for(int var20 = 0; var20 < var19; ++var20) {
                  this.b(var0, var1, var2, new BlockPosition(var11 + var17, var16 - var20 - 1, var13 + var18), var5);
               }

               var6.add(new WorldGenFoilagePlacer.a(new BlockPosition(var14 + var17, var16, var15 + var18), 0, false));
            }
         }
      }

      return var6;
   }
}
