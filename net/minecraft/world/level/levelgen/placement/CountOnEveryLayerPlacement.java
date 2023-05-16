package net.minecraft.world.level.levelgen.placement;

import com.mojang.serialization.Codec;
import java.util.stream.Stream;
import java.util.stream.Stream.Builder;
import net.minecraft.core.BlockPosition;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.levelgen.HeightMap;

@Deprecated
public class CountOnEveryLayerPlacement extends PlacementModifier {
   public static final Codec<CountOnEveryLayerPlacement> a = IntProvider.b(0, 256)
      .fieldOf("count")
      .xmap(CountOnEveryLayerPlacement::new, var0 -> var0.c)
      .codec();
   private final IntProvider c;

   private CountOnEveryLayerPlacement(IntProvider var0) {
      this.c = var0;
   }

   public static CountOnEveryLayerPlacement a(IntProvider var0) {
      return new CountOnEveryLayerPlacement(var0);
   }

   public static CountOnEveryLayerPlacement a(int var0) {
      return a(ConstantInt.a(var0));
   }

   @Override
   public Stream<BlockPosition> a_(PlacementContext var0, RandomSource var1, BlockPosition var2) {
      Builder<BlockPosition> var3 = Stream.builder();
      int var5 = 0;

      boolean var4;
      do {
         var4 = false;

         for(int var6 = 0; var6 < this.c.a(var1); ++var6) {
            int var7 = var1.a(16) + var2.u();
            int var8 = var1.a(16) + var2.w();
            int var9 = var0.a(HeightMap.Type.e, var7, var8);
            int var10 = a(var0, var7, var9, var8, var5);
            if (var10 != Integer.MAX_VALUE) {
               var3.add(new BlockPosition(var7, var10, var8));
               var4 = true;
            }
         }

         ++var5;
      } while(var4);

      return var3.build();
   }

   @Override
   public PlacementModifierType<?> b() {
      return PlacementModifierType.i;
   }

   private static int a(PlacementContext var0, int var1, int var2, int var3, int var4) {
      BlockPosition.MutableBlockPosition var5 = new BlockPosition.MutableBlockPosition(var1, var2, var3);
      int var6 = 0;
      IBlockData var7 = var0.a(var5);

      for(int var8 = var2; var8 >= var0.c() + 1; --var8) {
         var5.q(var8 - 1);
         IBlockData var9 = var0.a(var5);
         if (!a(var9) && a(var7) && !var9.a(Blocks.F)) {
            if (var6 == var4) {
               return var5.v() + 1;
            }

            ++var6;
         }

         var7 = var9;
      }

      return Integer.MAX_VALUE;
   }

   private static boolean a(IBlockData var0) {
      return var0.h() || var0.a(Blocks.G) || var0.a(Blocks.H);
   }
}
