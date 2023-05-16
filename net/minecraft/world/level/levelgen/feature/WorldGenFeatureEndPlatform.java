package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPosition;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureEmptyConfiguration;

public class WorldGenFeatureEndPlatform extends WorldGenerator<WorldGenFeatureEmptyConfiguration> {
   private static final BlockPosition a = new BlockPosition(8, 3, 8);
   private static final ChunkCoordIntPair b = new ChunkCoordIntPair(a);
   private static final int c = 16;
   private static final int d = 1;

   public WorldGenFeatureEndPlatform(Codec<WorldGenFeatureEmptyConfiguration> var0) {
      super(var0);
   }

   private static int a(int var0, int var1, int var2, int var3) {
      return Math.max(Math.abs(var0 - var2), Math.abs(var1 - var3));
   }

   @Override
   public boolean a(FeaturePlaceContext<WorldGenFeatureEmptyConfiguration> var0) {
      GeneratorAccessSeed var1 = var0.b();
      ChunkCoordIntPair var2 = new ChunkCoordIntPair(var0.e());
      if (a(var2.e, var2.f, b.e, b.f) > 1) {
         return true;
      } else {
         BlockPosition var3 = a.h(var0.e().v() + a.v());
         BlockPosition.MutableBlockPosition var4 = new BlockPosition.MutableBlockPosition();

         for(int var5 = var2.e(); var5 <= var2.g(); ++var5) {
            for(int var6 = var2.d(); var6 <= var2.f(); ++var6) {
               if (a(var3.u(), var3.w(), var6, var5) <= 16) {
                  var4.d(var6, var3.v(), var5);
                  if (var4.equals(var3)) {
                     var1.a(var4, Blocks.m.o(), 2);
                  } else {
                     var1.a(var4, Blocks.b.o(), 2);
                  }
               }
            }
         }

         return true;
      }
   }
}
