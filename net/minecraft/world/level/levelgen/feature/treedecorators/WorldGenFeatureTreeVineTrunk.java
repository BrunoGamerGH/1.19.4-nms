package net.minecraft.world.level.levelgen.feature.treedecorators;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPosition;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.BlockVine;

public class WorldGenFeatureTreeVineTrunk extends WorldGenFeatureTree {
   public static final Codec<WorldGenFeatureTreeVineTrunk> a = Codec.unit(() -> WorldGenFeatureTreeVineTrunk.b);
   public static final WorldGenFeatureTreeVineTrunk b = new WorldGenFeatureTreeVineTrunk();

   @Override
   protected WorldGenFeatureTrees<?> a() {
      return WorldGenFeatureTrees.a;
   }

   @Override
   public void a(WorldGenFeatureTree.a var0) {
      RandomSource var1 = var0.b();
      var0.c().forEach(var2x -> {
         if (var1.a(3) > 0) {
            BlockPosition var3 = var2x.g();
            if (var0.a(var3)) {
               var0.a(var3, BlockVine.c);
            }
         }

         if (var1.a(3) > 0) {
            BlockPosition var3 = var2x.h();
            if (var0.a(var3)) {
               var0.a(var3, BlockVine.e);
            }
         }

         if (var1.a(3) > 0) {
            BlockPosition var3 = var2x.e();
            if (var0.a(var3)) {
               var0.a(var3, BlockVine.d);
            }
         }

         if (var1.a(3) > 0) {
            BlockPosition var3 = var2x.f();
            if (var0.a(var3)) {
               var0.a(var3, BlockVine.b);
            }
         }
      });
   }
}
