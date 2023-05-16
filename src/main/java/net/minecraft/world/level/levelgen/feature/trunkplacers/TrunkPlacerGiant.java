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

public class TrunkPlacerGiant extends TrunkPlacer {
   public static final Codec<TrunkPlacerGiant> a = RecordCodecBuilder.create(var0 -> a(var0).apply(var0, TrunkPlacerGiant::new));

   public TrunkPlacerGiant(int var0, int var1, int var2) {
      super(var0, var1, var2);
   }

   @Override
   protected TrunkPlacers<?> a() {
      return TrunkPlacers.c;
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
      BlockPosition var6 = var4.d();
      a(var0, var1, var2, var6, var5);
      a(var0, var1, var2, var6.h(), var5);
      a(var0, var1, var2, var6.f(), var5);
      a(var0, var1, var2, var6.f().h(), var5);
      BlockPosition.MutableBlockPosition var7 = new BlockPosition.MutableBlockPosition();

      for(int var8 = 0; var8 < var3; ++var8) {
         this.a(var0, var1, var2, var7, var5, var4, 0, var8, 0);
         if (var8 < var3 - 1) {
            this.a(var0, var1, var2, var7, var5, var4, 1, var8, 0);
            this.a(var0, var1, var2, var7, var5, var4, 1, var8, 1);
            this.a(var0, var1, var2, var7, var5, var4, 0, var8, 1);
         }
      }

      return ImmutableList.of(new WorldGenFoilagePlacer.a(var4.b(var3), 0, true));
   }

   private void a(
      VirtualLevelReadable var0,
      BiConsumer<BlockPosition, IBlockData> var1,
      RandomSource var2,
      BlockPosition.MutableBlockPosition var3,
      WorldGenFeatureTreeConfiguration var4,
      BlockPosition var5,
      int var6,
      int var7,
      int var8
   ) {
      var3.a(var5, var6, var7, var8);
      this.a(var0, var1, var2, var3, var4);
   }
}
