package net.minecraft.world.level.levelgen.feature.rootplacers;

import com.mojang.datafixers.Products.P3;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import com.mojang.serialization.codecs.RecordCodecBuilder.Mu;
import java.util.Optional;
import java.util.function.BiConsumer;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.TagsFluid;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.VirtualLevelReadable;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.levelgen.feature.WorldGenTrees;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureTreeConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.WorldGenFeatureStateProvider;

public abstract class RootPlacer {
   public static final Codec<RootPlacer> d = BuiltInRegistries.Y.q().dispatch(RootPlacer::a, RootPlacerType::a);
   protected final IntProvider e;
   protected final WorldGenFeatureStateProvider f;
   protected final Optional<AboveRootPlacement> g;

   protected static <P extends RootPlacer> P3<Mu<P>, IntProvider, WorldGenFeatureStateProvider, Optional<AboveRootPlacement>> a(Instance<P> var0) {
      return var0.group(
         IntProvider.c.fieldOf("trunk_offset_y").forGetter(var0x -> var0x.e),
         WorldGenFeatureStateProvider.a.fieldOf("root_provider").forGetter(var0x -> var0x.f),
         AboveRootPlacement.a.optionalFieldOf("above_root_placement").forGetter(var0x -> var0x.g)
      );
   }

   public RootPlacer(IntProvider var0, WorldGenFeatureStateProvider var1, Optional<AboveRootPlacement> var2) {
      this.e = var0;
      this.f = var1;
      this.g = var2;
   }

   protected abstract RootPlacerType<?> a();

   public abstract boolean a(
      VirtualLevelReadable var1,
      BiConsumer<BlockPosition, IBlockData> var2,
      RandomSource var3,
      BlockPosition var4,
      BlockPosition var5,
      WorldGenFeatureTreeConfiguration var6
   );

   protected boolean a(VirtualLevelReadable var0, BlockPosition var1) {
      return WorldGenTrees.d(var0, var1);
   }

   protected void a(
      VirtualLevelReadable var0, BiConsumer<BlockPosition, IBlockData> var1, RandomSource var2, BlockPosition var3, WorldGenFeatureTreeConfiguration var4
   ) {
      if (this.a(var0, var3)) {
         var1.accept(var3, this.a(var0, var3, this.f.a(var2, var3)));
         if (this.g.isPresent()) {
            AboveRootPlacement var5 = this.g.get();
            BlockPosition var6 = var3.c();
            if (var2.i() < var5.b() && var0.a(var6, BlockBase.BlockData::h)) {
               var1.accept(var6, this.a(var0, var6, var5.a().a(var2, var6)));
            }
         }
      }
   }

   protected IBlockData a(VirtualLevelReadable var0, BlockPosition var1, IBlockData var2) {
      if (var2.b(BlockProperties.C)) {
         boolean var3 = var0.b(var1, var0x -> var0x.a(TagsFluid.a));
         return var2.a(BlockProperties.C, Boolean.valueOf(var3));
      } else {
         return var2;
      }
   }

   public BlockPosition a(BlockPosition var0, RandomSource var1) {
      return var0.b(this.e.a(var1));
   }
}
