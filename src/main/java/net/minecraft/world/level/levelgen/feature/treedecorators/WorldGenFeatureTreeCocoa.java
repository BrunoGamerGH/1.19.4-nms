package net.minecraft.world.level.levelgen.feature.treedecorators;

import com.mojang.serialization.Codec;
import java.util.List;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.BlockCocoa;
import net.minecraft.world.level.block.Blocks;

public class WorldGenFeatureTreeCocoa extends WorldGenFeatureTree {
   public static final Codec<WorldGenFeatureTreeCocoa> a = Codec.floatRange(0.0F, 1.0F)
      .fieldOf("probability")
      .xmap(WorldGenFeatureTreeCocoa::new, var0 -> var0.b)
      .codec();
   private final float b;

   public WorldGenFeatureTreeCocoa(float var0) {
      this.b = var0;
   }

   @Override
   protected WorldGenFeatureTrees<?> a() {
      return WorldGenFeatureTrees.c;
   }

   @Override
   public void a(WorldGenFeatureTree.a var0) {
      RandomSource var1 = var0.b();
      if (!(var1.i() >= this.b)) {
         List<BlockPosition> var2 = var0.c();
         int var3 = var2.get(0).v();
         var2.stream().filter(var1x -> var1x.v() - var3 <= 2).forEach(var2x -> {
            for(EnumDirection var4x : EnumDirection.EnumDirectionLimit.a) {
               if (var1.i() <= 0.25F) {
                  EnumDirection var5 = var4x.g();
                  BlockPosition var6 = var2x.b(var5.j(), 0, var5.l());
                  if (var0.a(var6)) {
                     var0.a(var6, Blocks.fB.o().a(BlockCocoa.b, Integer.valueOf(var1.a(3))).a(BlockCocoa.aD, var4x));
                  }
               }
            }
         });
      }
   }
}
