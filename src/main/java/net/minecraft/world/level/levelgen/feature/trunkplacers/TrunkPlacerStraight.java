package net.minecraft.world.level.levelgen.feature.trunkplacers;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.function.BiConsumer;
import net.minecraft.core.BlockPosition;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.VirtualLevelReadable;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureTreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.WorldGenFoilagePlacer;

public class TrunkPlacerStraight extends TrunkPlacer {
   public static final Codec<TrunkPlacerStraight> a = RecordCodecBuilder.create(var0 -> a(var0).apply(var0, TrunkPlacerStraight::new));

   public TrunkPlacerStraight(int var0, int var1, int var2) {
      super(var0, var1, var2);
   }

   @Override
   protected TrunkPlacers<?> a() {
      return TrunkPlacers.a;
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

      for(int var6 = 0; var6 < var3; ++var6) {
         this.b(var0, var1, var2, var4.b(var6), var5);
      }

      return ImmutableList.of(new WorldGenFoilagePlacer.a(var4.b(var3), 0, false));
   }
}
