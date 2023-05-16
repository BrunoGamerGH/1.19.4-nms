package net.minecraft.world.level.levelgen.feature.treedecorators;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPosition;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.BlockVine;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;

public class WorldGenFeatureTreeVineLeaves extends WorldGenFeatureTree {
   public static final Codec<WorldGenFeatureTreeVineLeaves> a = Codec.floatRange(0.0F, 1.0F)
      .fieldOf("probability")
      .xmap(WorldGenFeatureTreeVineLeaves::new, var0 -> var0.b)
      .codec();
   private final float b;

   @Override
   protected WorldGenFeatureTrees<?> a() {
      return WorldGenFeatureTrees.b;
   }

   public WorldGenFeatureTreeVineLeaves(float var0) {
      this.b = var0;
   }

   @Override
   public void a(WorldGenFeatureTree.a var0) {
      RandomSource var1 = var0.b();
      var0.d().forEach(var2x -> {
         if (var1.i() < this.b) {
            BlockPosition var3 = var2x.g();
            if (var0.a(var3)) {
               a(var3, BlockVine.c, var0);
            }
         }

         if (var1.i() < this.b) {
            BlockPosition var3 = var2x.h();
            if (var0.a(var3)) {
               a(var3, BlockVine.e, var0);
            }
         }

         if (var1.i() < this.b) {
            BlockPosition var3 = var2x.e();
            if (var0.a(var3)) {
               a(var3, BlockVine.d, var0);
            }
         }

         if (var1.i() < this.b) {
            BlockPosition var3 = var2x.f();
            if (var0.a(var3)) {
               a(var3, BlockVine.b, var0);
            }
         }
      });
   }

   private static void a(BlockPosition var0, BlockStateBoolean var1, WorldGenFeatureTree.a var2) {
      var2.a(var0, var1);
      int var3 = 4;

      for(BlockPosition var4 = var0.d(); var2.a(var4) && var3 > 0; --var3) {
         var2.a(var4, var1);
         var4 = var4.d();
      }
   }
}
