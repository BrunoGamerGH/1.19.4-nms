package net.minecraft.world.level.block.state.pattern;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.MoreObjects;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.BaseBlockPosition;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.world.level.IWorldReader;

public class ShapeDetector {
   private final Predicate<ShapeDetectorBlock>[][][] a;
   private final int b;
   private final int c;
   private final int d;

   public ShapeDetector(Predicate<ShapeDetectorBlock>[][][] var0) {
      this.a = var0;
      this.b = var0.length;
      if (this.b > 0) {
         this.c = var0[0].length;
         if (this.c > 0) {
            this.d = var0[0][0].length;
         } else {
            this.d = 0;
         }
      } else {
         this.c = 0;
         this.d = 0;
      }
   }

   public int a() {
      return this.b;
   }

   public int b() {
      return this.c;
   }

   public int c() {
      return this.d;
   }

   @VisibleForTesting
   public Predicate<ShapeDetectorBlock>[][][] d() {
      return this.a;
   }

   @Nullable
   @VisibleForTesting
   public ShapeDetector.ShapeDetectorCollection a(IWorldReader var0, BlockPosition var1, EnumDirection var2, EnumDirection var3) {
      LoadingCache<BlockPosition, ShapeDetectorBlock> var4 = a(var0, false);
      return this.a(var1, var2, var3, var4);
   }

   @Nullable
   private ShapeDetector.ShapeDetectorCollection a(
      BlockPosition var0, EnumDirection var1, EnumDirection var2, LoadingCache<BlockPosition, ShapeDetectorBlock> var3
   ) {
      for(int var4 = 0; var4 < this.d; ++var4) {
         for(int var5 = 0; var5 < this.c; ++var5) {
            for(int var6 = 0; var6 < this.b; ++var6) {
               if (!this.a[var6][var5][var4].test((ShapeDetectorBlock)var3.getUnchecked(a(var0, var1, var2, var4, var5, var6)))) {
                  return null;
               }
            }
         }
      }

      return new ShapeDetector.ShapeDetectorCollection(var0, var1, var2, var3, this.d, this.c, this.b);
   }

   @Nullable
   public ShapeDetector.ShapeDetectorCollection a(IWorldReader var0, BlockPosition var1) {
      LoadingCache<BlockPosition, ShapeDetectorBlock> var2 = a(var0, false);
      int var3 = Math.max(Math.max(this.d, this.c), this.b);

      for(BlockPosition var5 : BlockPosition.a(var1, var1.b(var3 - 1, var3 - 1, var3 - 1))) {
         for(EnumDirection var9 : EnumDirection.values()) {
            for(EnumDirection var13 : EnumDirection.values()) {
               if (var13 != var9 && var13 != var9.g()) {
                  ShapeDetector.ShapeDetectorCollection var14 = this.a(var5, var9, var13, var2);
                  if (var14 != null) {
                     return var14;
                  }
               }
            }
         }
      }

      return null;
   }

   public static LoadingCache<BlockPosition, ShapeDetectorBlock> a(IWorldReader var0, boolean var1) {
      return CacheBuilder.newBuilder().build(new ShapeDetector.BlockLoader(var0, var1));
   }

   protected static BlockPosition a(BlockPosition var0, EnumDirection var1, EnumDirection var2, int var3, int var4, int var5) {
      if (var1 != var2 && var1 != var2.g()) {
         BaseBlockPosition var6 = new BaseBlockPosition(var1.j(), var1.k(), var1.l());
         BaseBlockPosition var7 = new BaseBlockPosition(var2.j(), var2.k(), var2.l());
         BaseBlockPosition var8 = var6.d(var7);
         return var0.b(
            var7.u() * -var4 + var8.u() * var3 + var6.u() * var5,
            var7.v() * -var4 + var8.v() * var3 + var6.v() * var5,
            var7.w() * -var4 + var8.w() * var3 + var6.w() * var5
         );
      } else {
         throw new IllegalArgumentException("Invalid forwards & up combination");
      }
   }

   static class BlockLoader extends CacheLoader<BlockPosition, ShapeDetectorBlock> {
      private final IWorldReader a;
      private final boolean b;

      public BlockLoader(IWorldReader var0, boolean var1) {
         this.a = var0;
         this.b = var1;
      }

      public ShapeDetectorBlock a(BlockPosition var0) {
         return new ShapeDetectorBlock(this.a, var0, this.b);
      }
   }

   public static class ShapeDetectorCollection {
      private final BlockPosition a;
      private final EnumDirection b;
      private final EnumDirection c;
      private final LoadingCache<BlockPosition, ShapeDetectorBlock> d;
      private final int e;
      private final int f;
      private final int g;

      public ShapeDetectorCollection(
         BlockPosition var0, EnumDirection var1, EnumDirection var2, LoadingCache<BlockPosition, ShapeDetectorBlock> var3, int var4, int var5, int var6
      ) {
         this.a = var0;
         this.b = var1;
         this.c = var2;
         this.d = var3;
         this.e = var4;
         this.f = var5;
         this.g = var6;
      }

      public BlockPosition a() {
         return this.a;
      }

      public EnumDirection b() {
         return this.b;
      }

      public EnumDirection c() {
         return this.c;
      }

      public int d() {
         return this.e;
      }

      public int e() {
         return this.f;
      }

      public int f() {
         return this.g;
      }

      public ShapeDetectorBlock a(int var0, int var1, int var2) {
         return (ShapeDetectorBlock)this.d.getUnchecked(ShapeDetector.a(this.a, this.b(), this.c(), var0, var1, var2));
      }

      @Override
      public String toString() {
         return MoreObjects.toStringHelper(this).add("up", this.c).add("forwards", this.b).add("frontTopLeft", this.a).toString();
      }
   }
}
