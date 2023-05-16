package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.Optional;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.TagsBlock;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BlockCoralFanWallAbstract;
import net.minecraft.world.level.block.BlockSeaPickle;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureEmptyConfiguration;

public abstract class WorldGenFeatureCoral extends WorldGenerator<WorldGenFeatureEmptyConfiguration> {
   public WorldGenFeatureCoral(Codec<WorldGenFeatureEmptyConfiguration> var0) {
      super(var0);
   }

   @Override
   public boolean a(FeaturePlaceContext<WorldGenFeatureEmptyConfiguration> var0) {
      RandomSource var1 = var0.d();
      GeneratorAccessSeed var2 = var0.b();
      BlockPosition var3 = var0.e();
      Optional<Block> var4 = BuiltInRegistries.f.b(TagsBlock.an).flatMap(var1x -> var1x.a(var1)).map(Holder::a);
      return var4.isEmpty() ? false : this.a(var2, var1, var3, var4.get().o());
   }

   protected abstract boolean a(GeneratorAccess var1, RandomSource var2, BlockPosition var3, IBlockData var4);

   protected boolean b(GeneratorAccess var0, RandomSource var1, BlockPosition var2, IBlockData var3) {
      BlockPosition var4 = var2.c();
      IBlockData var5 = var0.a_(var2);
      if ((var5.a(Blocks.G) || var5.a(TagsBlock.aq)) && var0.a_(var4).a(Blocks.G)) {
         var0.a(var2, var3, 3);
         if (var1.i() < 0.25F) {
            BuiltInRegistries.f.b(TagsBlock.aq).flatMap(var1x -> var1x.a(var1)).map(Holder::a).ifPresent(var2x -> var0.a(var4, var2x.o(), 2));
         } else if (var1.i() < 0.05F) {
            var0.a(var4, Blocks.mR.o().a(BlockSeaPickle.b, Integer.valueOf(var1.a(4) + 1)), 2);
         }

         for(EnumDirection var7 : EnumDirection.EnumDirectionLimit.a) {
            if (var1.i() < 0.2F) {
               BlockPosition var8 = var2.a(var7);
               if (var0.a_(var8).a(Blocks.G)) {
                  BuiltInRegistries.f.b(TagsBlock.ao).flatMap(var1x -> var1x.a(var1)).map(Holder::a).ifPresent(var3x -> {
                     IBlockData var4x = var3x.o();
                     if (var4x.b(BlockCoralFanWallAbstract.a)) {
                        var4x = var4x.a(BlockCoralFanWallAbstract.a, var7);
                     }

                     var0.a(var8, var4x, 2);
                  });
               }
            }
         }

         return true;
      } else {
         return false;
      }
   }
}
