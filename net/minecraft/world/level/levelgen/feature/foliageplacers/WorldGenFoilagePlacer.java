package net.minecraft.world.level.levelgen.feature.foliageplacers;

import com.mojang.datafixers.Products.P2;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import com.mojang.serialization.codecs.RecordCodecBuilder.Mu;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.VirtualLevelReadable;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.levelgen.feature.WorldGenTrees;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureTreeConfiguration;
import net.minecraft.world.level.material.FluidTypes;

public abstract class WorldGenFoilagePlacer {
   public static final Codec<WorldGenFoilagePlacer> d = BuiltInRegistries.W.q().dispatch(WorldGenFoilagePlacer::a, WorldGenFoilagePlacers::a);
   protected final IntProvider e;
   protected final IntProvider f;

   protected static <P extends WorldGenFoilagePlacer> P2<Mu<P>, IntProvider, IntProvider> b(Instance<P> var0) {
      return var0.group(IntProvider.b(0, 16).fieldOf("radius").forGetter(var0x -> var0x.e), IntProvider.b(0, 16).fieldOf("offset").forGetter(var0x -> var0x.f));
   }

   public WorldGenFoilagePlacer(IntProvider var0, IntProvider var1) {
      this.e = var0;
      this.f = var1;
   }

   protected abstract WorldGenFoilagePlacers<?> a();

   public void a(
      VirtualLevelReadable var0,
      WorldGenFoilagePlacer.b var1,
      RandomSource var2,
      WorldGenFeatureTreeConfiguration var3,
      int var4,
      WorldGenFoilagePlacer.a var5,
      int var6,
      int var7
   ) {
      this.a(var0, var1, var2, var3, var4, var5, var6, var7, this.a(var2));
   }

   protected abstract void a(
      VirtualLevelReadable var1,
      WorldGenFoilagePlacer.b var2,
      RandomSource var3,
      WorldGenFeatureTreeConfiguration var4,
      int var5,
      WorldGenFoilagePlacer.a var6,
      int var7,
      int var8,
      int var9
   );

   public abstract int a(RandomSource var1, int var2, WorldGenFeatureTreeConfiguration var3);

   public int a(RandomSource var0, int var1) {
      return this.e.a(var0);
   }

   private int a(RandomSource var0) {
      return this.f.a(var0);
   }

   protected abstract boolean a(RandomSource var1, int var2, int var3, int var4, int var5, boolean var6);

   protected boolean b(RandomSource var0, int var1, int var2, int var3, int var4, boolean var5) {
      int var6;
      int var7;
      if (var5) {
         var6 = Math.min(Math.abs(var1), Math.abs(var1 - 1));
         var7 = Math.min(Math.abs(var3), Math.abs(var3 - 1));
      } else {
         var6 = Math.abs(var1);
         var7 = Math.abs(var3);
      }

      return this.a(var0, var6, var2, var7, var4, var5);
   }

   protected void a(
      VirtualLevelReadable var0,
      WorldGenFoilagePlacer.b var1,
      RandomSource var2,
      WorldGenFeatureTreeConfiguration var3,
      BlockPosition var4,
      int var5,
      int var6,
      boolean var7
   ) {
      int var8 = var7 ? 1 : 0;
      BlockPosition.MutableBlockPosition var9 = new BlockPosition.MutableBlockPosition();

      for(int var10 = -var5; var10 <= var5 + var8; ++var10) {
         for(int var11 = -var5; var11 <= var5 + var8; ++var11) {
            if (!this.b(var2, var10, var6, var11, var5, var7)) {
               var9.a(var4, var10, var6, var11);
               a(var0, var1, var2, var3, var9);
            }
         }
      }
   }

   protected final void a(
      VirtualLevelReadable var0,
      WorldGenFoilagePlacer.b var1,
      RandomSource var2,
      WorldGenFeatureTreeConfiguration var3,
      BlockPosition var4,
      int var5,
      int var6,
      boolean var7,
      float var8,
      float var9
   ) {
      this.a(var0, var1, var2, var3, var4, var5, var6, var7);
      int var10 = var7 ? 1 : 0;
      BlockPosition.MutableBlockPosition var11 = new BlockPosition.MutableBlockPosition();

      for(EnumDirection var13 : EnumDirection.EnumDirectionLimit.a) {
         EnumDirection var14 = var13.h();
         int var15 = var14.f() == EnumDirection.EnumAxisDirection.a ? var5 + var10 : var5;
         var11.a(var4, 0, var6 - 1, 0).c(var14, var15).c(var13, -var5);
         int var16 = -var5;

         while(var16 < var5 + var10) {
            boolean var17 = var1.a(var11.c(EnumDirection.b));
            var11.c(EnumDirection.a);
            if (var17 && !(var2.i() > var8) && a(var0, var1, var2, var3, var11) && !(var2.i() > var9)) {
               a(var0, var1, var2, var3, var11.c(EnumDirection.a));
               var11.c(EnumDirection.b);
            }

            ++var16;
            var11.c(var13);
         }
      }
   }

   protected static boolean a(
      VirtualLevelReadable var0, WorldGenFoilagePlacer.b var1, RandomSource var2, WorldGenFeatureTreeConfiguration var3, BlockPosition var4
   ) {
      if (!WorldGenTrees.d(var0, var4)) {
         return false;
      } else {
         IBlockData var5 = var3.e.a(var2, var4);
         if (var5.b(BlockProperties.C)) {
            var5 = var5.a(BlockProperties.C, Boolean.valueOf(var0.b(var4, var0x -> var0x.a(FluidTypes.c))));
         }

         var1.a(var4, var5);
         return true;
      }
   }

   public static final class a {
      private final BlockPosition a;
      private final int b;
      private final boolean c;

      public a(BlockPosition var0, int var1, boolean var2) {
         this.a = var0;
         this.b = var1;
         this.c = var2;
      }

      public BlockPosition a() {
         return this.a;
      }

      public int b() {
         return this.b;
      }

      public boolean c() {
         return this.c;
      }
   }

   public interface b {
      void a(BlockPosition var1, IBlockData var2);

      boolean a(BlockPosition var1);
   }
}
