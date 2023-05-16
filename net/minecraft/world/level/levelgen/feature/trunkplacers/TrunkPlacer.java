package net.minecraft.world.level.levelgen.feature.trunkplacers;

import com.mojang.datafixers.Products.P3;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import com.mojang.serialization.codecs.RecordCodecBuilder.Mu;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.TagsBlock;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.VirtualLevelReadable;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.levelgen.feature.WorldGenTrees;
import net.minecraft.world.level.levelgen.feature.WorldGenerator;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureTreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.WorldGenFoilagePlacer;

public abstract class TrunkPlacer {
   public static final Codec<TrunkPlacer> c = BuiltInRegistries.X.q().dispatch(TrunkPlacer::a, TrunkPlacers::a);
   private static final int a = 32;
   private static final int b = 24;
   public static final int d = 80;
   protected final int e;
   protected final int f;
   protected final int g;

   protected static <P extends TrunkPlacer> P3<Mu<P>, Integer, Integer, Integer> a(Instance<P> var0) {
      return var0.group(
         Codec.intRange(0, 32).fieldOf("base_height").forGetter(var0x -> var0x.e),
         Codec.intRange(0, 24).fieldOf("height_rand_a").forGetter(var0x -> var0x.f),
         Codec.intRange(0, 24).fieldOf("height_rand_b").forGetter(var0x -> var0x.g)
      );
   }

   public TrunkPlacer(int var0, int var1, int var2) {
      this.e = var0;
      this.f = var1;
      this.g = var2;
   }

   protected abstract TrunkPlacers<?> a();

   public abstract List<WorldGenFoilagePlacer.a> a(
      VirtualLevelReadable var1,
      BiConsumer<BlockPosition, IBlockData> var2,
      RandomSource var3,
      int var4,
      BlockPosition var5,
      WorldGenFeatureTreeConfiguration var6
   );

   public int a(RandomSource var0) {
      return this.e + var0.a(this.f + 1) + var0.a(this.g + 1);
   }

   private static boolean c(VirtualLevelReadable var0, BlockPosition var1) {
      return var0.a(var1, var0x -> WorldGenerator.b(var0x) && !var0x.a(Blocks.i) && !var0x.a(Blocks.fk));
   }

   protected static void a(
      VirtualLevelReadable var0, BiConsumer<BlockPosition, IBlockData> var1, RandomSource var2, BlockPosition var3, WorldGenFeatureTreeConfiguration var4
   ) {
      if (var4.k || !c(var0, var3)) {
         var1.accept(var3, var4.c.a(var2, var3));
      }
   }

   protected boolean b(
      VirtualLevelReadable var0, BiConsumer<BlockPosition, IBlockData> var1, RandomSource var2, BlockPosition var3, WorldGenFeatureTreeConfiguration var4
   ) {
      return this.a(var0, var1, var2, var3, var4, Function.identity());
   }

   protected boolean a(
      VirtualLevelReadable var0,
      BiConsumer<BlockPosition, IBlockData> var1,
      RandomSource var2,
      BlockPosition var3,
      WorldGenFeatureTreeConfiguration var4,
      Function<IBlockData, IBlockData> var5
   ) {
      if (this.a(var0, var3)) {
         var1.accept(var3, var5.apply(var4.b.a(var2, var3)));
         return true;
      } else {
         return false;
      }
   }

   protected void a(
      VirtualLevelReadable var0,
      BiConsumer<BlockPosition, IBlockData> var1,
      RandomSource var2,
      BlockPosition.MutableBlockPosition var3,
      WorldGenFeatureTreeConfiguration var4
   ) {
      if (this.b(var0, var3)) {
         this.b(var0, var1, var2, var3, var4);
      }
   }

   protected boolean a(VirtualLevelReadable var0, BlockPosition var1) {
      return WorldGenTrees.d(var0, var1);
   }

   public boolean b(VirtualLevelReadable var0, BlockPosition var1) {
      return this.a(var0, var1) || var0.a(var1, var0x -> var0x.a(TagsBlock.s));
   }
}
