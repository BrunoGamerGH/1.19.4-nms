package net.minecraft.world.level.levelgen.feature.treedecorators;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import java.util.List;
import net.minecraft.core.BlockPosition;
import net.minecraft.world.level.levelgen.feature.WorldGenerator;
import net.minecraft.world.level.levelgen.feature.stateproviders.WorldGenFeatureStateProvider;

public class WorldGenFeatureTreeAlterGround extends WorldGenFeatureTree {
   public static final Codec<WorldGenFeatureTreeAlterGround> a = WorldGenFeatureStateProvider.a
      .fieldOf("provider")
      .xmap(WorldGenFeatureTreeAlterGround::new, var0 -> var0.b)
      .codec();
   private final WorldGenFeatureStateProvider b;

   public WorldGenFeatureTreeAlterGround(WorldGenFeatureStateProvider var0) {
      this.b = var0;
   }

   @Override
   protected WorldGenFeatureTrees<?> a() {
      return WorldGenFeatureTrees.e;
   }

   @Override
   public void a(WorldGenFeatureTree.a var0) {
      List<BlockPosition> var1 = Lists.newArrayList();
      List<BlockPosition> var2 = var0.e();
      List<BlockPosition> var3 = var0.c();
      if (var2.isEmpty()) {
         var1.addAll(var3);
      } else if (!var3.isEmpty() && var2.get(0).v() == var3.get(0).v()) {
         var1.addAll(var3);
         var1.addAll(var2);
      } else {
         var1.addAll(var2);
      }

      if (!var1.isEmpty()) {
         int var4 = var1.get(0).v();
         var1.stream().filter(var1x -> var1x.v() == var4).forEach(var1x -> {
            this.a(var0, var1x.g().e());
            this.a(var0, var1x.g(2).e());
            this.a(var0, var1x.g().e(2));
            this.a(var0, var1x.g(2).e(2));

            for(int var2x = 0; var2x < 5; ++var2x) {
               int var3x = var0.b().a(64);
               int var4x = var3x % 8;
               int var5x = var3x / 8;
               if (var4x == 0 || var4x == 7 || var5x == 0 || var5x == 7) {
                  this.a(var0, var1x.b(-3 + var4x, 0, -3 + var5x));
               }
            }
         });
      }
   }

   private void a(WorldGenFeatureTree.a var0, BlockPosition var1) {
      for(int var2 = -2; var2 <= 2; ++var2) {
         for(int var3 = -2; var3 <= 2; ++var3) {
            if (Math.abs(var2) != 2 || Math.abs(var3) != 2) {
               this.b(var0, var1.b(var2, 0, var3));
            }
         }
      }
   }

   private void b(WorldGenFeatureTree.a var0, BlockPosition var1) {
      for(int var2 = 2; var2 >= -3; --var2) {
         BlockPosition var3 = var1.b(var2);
         if (WorldGenerator.a(var0.a(), var3)) {
            var0.a(var3, this.b.a(var0.b(), var1));
            break;
         }

         if (!var0.a(var3) && var2 < 0) {
            break;
         }
      }
   }
}
