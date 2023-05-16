package net.minecraft.world.level.levelgen.feature.trunkplacers;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.function.BiConsumer;
import net.minecraft.core.BlockPosition;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.VirtualLevelReadable;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureTreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.WorldGenFoilagePlacer;

public class TrunkPlacerMegaJungle extends TrunkPlacerGiant {
   public static final Codec<TrunkPlacerMegaJungle> b = RecordCodecBuilder.create(var0 -> a(var0).apply(var0, TrunkPlacerMegaJungle::new));

   public TrunkPlacerMegaJungle(int var0, int var1, int var2) {
      super(var0, var1, var2);
   }

   @Override
   protected TrunkPlacers<?> a() {
      return TrunkPlacers.d;
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
      var6.addAll(super.a(var0, var1, var2, var3, var4, var5));

      for(int var7 = var3 - 2 - var2.a(4); var7 > var3 / 2; var7 -= 2 + var2.a(4)) {
         float var8 = var2.i() * (float) (Math.PI * 2);
         int var9 = 0;
         int var10 = 0;

         for(int var11 = 0; var11 < 5; ++var11) {
            var9 = (int)(1.5F + MathHelper.b(var8) * (float)var11);
            var10 = (int)(1.5F + MathHelper.a(var8) * (float)var11);
            BlockPosition var12 = var4.b(var9, var7 - 3 + var11 / 2, var10);
            this.b(var0, var1, var2, var12, var5);
         }

         var6.add(new WorldGenFoilagePlacer.a(var4.b(var9, var7, var10), -2, false));
      }

      return var6;
   }
}
