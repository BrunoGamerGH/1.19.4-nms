package net.minecraft.world.level.levelgen.feature.trunkplacers;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.function.BiConsumer;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.VirtualLevelReadable;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.levelgen.feature.WorldGenTrees;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureTreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.WorldGenFoilagePlacer;

public class BendingTrunkPlacer extends TrunkPlacer {
   public static final Codec<BendingTrunkPlacer> a = RecordCodecBuilder.create(
      var0 -> a(var0)
            .and(
               var0.group(
                  ExtraCodecs.i.optionalFieldOf("min_height_for_leaves", 1).forGetter(var0x -> var0x.b),
                  IntProvider.b(1, 64).fieldOf("bend_length").forGetter(var0x -> var0x.h)
               )
            )
            .apply(var0, BendingTrunkPlacer::new)
   );
   private final int b;
   private final IntProvider h;

   public BendingTrunkPlacer(int var0, int var1, int var2, int var3, IntProvider var4) {
      super(var0, var1, var2);
      this.b = var3;
      this.h = var4;
   }

   @Override
   protected TrunkPlacers<?> a() {
      return TrunkPlacers.g;
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
      EnumDirection var6 = EnumDirection.EnumDirectionLimit.a.a(var2);
      int var7 = var3 - 1;
      BlockPosition.MutableBlockPosition var8 = var4.j();
      BlockPosition var9 = var8.d();
      a(var0, var1, var2, var9, var5);
      List<WorldGenFoilagePlacer.a> var10 = Lists.newArrayList();

      for(int var11 = 0; var11 <= var7; ++var11) {
         if (var11 + 1 >= var7 + var2.a(2)) {
            var8.c(var6);
         }

         if (WorldGenTrees.d(var0, var8)) {
            this.b(var0, var1, var2, var8, var5);
         }

         if (var11 >= this.b) {
            var10.add(new WorldGenFoilagePlacer.a(var8.i(), 0, false));
         }

         var8.c(EnumDirection.b);
      }

      int var11 = this.h.a(var2);

      for(int var12 = 0; var12 <= var11; ++var12) {
         if (WorldGenTrees.d(var0, var8)) {
            this.b(var0, var1, var2, var8, var5);
         }

         var10.add(new WorldGenFoilagePlacer.a(var8.i(), 0, false));
         var8.c(var6);
      }

      return var10;
   }
}
