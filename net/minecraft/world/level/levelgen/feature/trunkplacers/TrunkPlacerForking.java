package net.minecraft.world.level.levelgen.feature.trunkplacers;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.OptionalInt;
import java.util.function.BiConsumer;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.VirtualLevelReadable;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureTreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.WorldGenFoilagePlacer;

public class TrunkPlacerForking extends TrunkPlacer {
   public static final Codec<TrunkPlacerForking> a = RecordCodecBuilder.create(var0 -> a(var0).apply(var0, TrunkPlacerForking::new));

   public TrunkPlacerForking(int var0, int var1, int var2) {
      super(var0, var1, var2);
   }

   @Override
   protected TrunkPlacers<?> a() {
      return TrunkPlacers.b;
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
      a(var0, var1, var2, var4.d(), var5);
      List<WorldGenFoilagePlacer.a> var6 = Lists.newArrayList();
      EnumDirection var7 = EnumDirection.EnumDirectionLimit.a.a(var2);
      int var8 = var3 - var2.a(4) - 1;
      int var9 = 3 - var2.a(3);
      BlockPosition.MutableBlockPosition var10 = new BlockPosition.MutableBlockPosition();
      int var11 = var4.u();
      int var12 = var4.w();
      OptionalInt var13 = OptionalInt.empty();

      for(int var14 = 0; var14 < var3; ++var14) {
         int var15 = var4.v() + var14;
         if (var14 >= var8 && var9 > 0) {
            var11 += var7.j();
            var12 += var7.l();
            --var9;
         }

         if (this.b(var0, var1, var2, var10.d(var11, var15, var12), var5)) {
            var13 = OptionalInt.of(var15 + 1);
         }
      }

      if (var13.isPresent()) {
         var6.add(new WorldGenFoilagePlacer.a(new BlockPosition(var11, var13.getAsInt(), var12), 1, false));
      }

      var11 = var4.u();
      var12 = var4.w();
      EnumDirection var14 = EnumDirection.EnumDirectionLimit.a.a(var2);
      if (var14 != var7) {
         int var15 = var8 - var2.a(2) - 1;
         int var16 = 1 + var2.a(3);
         var13 = OptionalInt.empty();

         for(int var17 = var15; var17 < var3 && var16 > 0; --var16) {
            if (var17 >= 1) {
               int var18 = var4.v() + var17;
               var11 += var14.j();
               var12 += var14.l();
               if (this.b(var0, var1, var2, var10.d(var11, var18, var12), var5)) {
                  var13 = OptionalInt.of(var18 + 1);
               }
            }

            ++var17;
         }

         if (var13.isPresent()) {
            var6.add(new WorldGenFoilagePlacer.a(new BlockPosition(var11, var13.getAsInt(), var12), 0, false));
         }
      }

      return var6;
   }
}
