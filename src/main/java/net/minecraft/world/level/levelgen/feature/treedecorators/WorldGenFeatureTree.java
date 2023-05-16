package net.minecraft.world.level.levelgen.feature.treedecorators;

import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.Comparator;
import java.util.Set;
import java.util.function.BiConsumer;
import net.minecraft.core.BaseBlockPosition;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.VirtualLevelReadable;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;

public abstract class WorldGenFeatureTree {
   public static final Codec<WorldGenFeatureTree> h = BuiltInRegistries.Z.q().dispatch(WorldGenFeatureTree::a, WorldGenFeatureTrees::a);

   protected abstract WorldGenFeatureTrees<?> a();

   public abstract void a(WorldGenFeatureTree.a var1);

   public static final class a {
      private final VirtualLevelReadable a;
      private final BiConsumer<BlockPosition, IBlockData> b;
      private final RandomSource c;
      private final ObjectArrayList<BlockPosition> d;
      private final ObjectArrayList<BlockPosition> e;
      private final ObjectArrayList<BlockPosition> f;

      public a(
         VirtualLevelReadable var0,
         BiConsumer<BlockPosition, IBlockData> var1,
         RandomSource var2,
         Set<BlockPosition> var3,
         Set<BlockPosition> var4,
         Set<BlockPosition> var5
      ) {
         this.a = var0;
         this.b = var1;
         this.c = var2;
         this.f = new ObjectArrayList(var5);
         this.d = new ObjectArrayList(var3);
         this.e = new ObjectArrayList(var4);
         this.d.sort(Comparator.comparingInt(BaseBlockPosition::v));
         this.e.sort(Comparator.comparingInt(BaseBlockPosition::v));
         this.f.sort(Comparator.comparingInt(BaseBlockPosition::v));
      }

      public void a(BlockPosition var0, BlockStateBoolean var1) {
         this.a(var0, Blocks.fe.o().a(var1, Boolean.valueOf(true)));
      }

      public void a(BlockPosition var0, IBlockData var1) {
         this.b.accept(var0, var1);
      }

      public boolean a(BlockPosition var0) {
         return this.a.a(var0, BlockBase.BlockData::h);
      }

      public VirtualLevelReadable a() {
         return this.a;
      }

      public RandomSource b() {
         return this.c;
      }

      public ObjectArrayList<BlockPosition> c() {
         return this.d;
      }

      public ObjectArrayList<BlockPosition> d() {
         return this.e;
      }

      public ObjectArrayList<BlockPosition> e() {
         return this.f;
      }
   }
}
