package net.minecraft.world.level.block;

import java.util.function.BiPredicate;
import java.util.function.Function;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.entity.TileEntityTypes;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockStateDirection;

public class DoubleBlockFinder {
   public static <S extends TileEntity> DoubleBlockFinder.Result<S> a(
      TileEntityTypes<S> var0,
      Function<IBlockData, DoubleBlockFinder.BlockType> var1,
      Function<IBlockData, EnumDirection> var2,
      BlockStateDirection var3,
      IBlockData var4,
      GeneratorAccess var5,
      BlockPosition var6,
      BiPredicate<GeneratorAccess, BlockPosition> var7
   ) {
      S var8 = var0.a(var5, var6);
      if (var8 == null) {
         return DoubleBlockFinder.Combiner::b;
      } else if (var7.test(var5, var6)) {
         return DoubleBlockFinder.Combiner::b;
      } else {
         DoubleBlockFinder.BlockType var9 = var1.apply(var4);
         boolean var10 = var9 == DoubleBlockFinder.BlockType.a;
         boolean var11 = var9 == DoubleBlockFinder.BlockType.b;
         if (var10) {
            return new DoubleBlockFinder.Result.Single<>(var8);
         } else {
            BlockPosition var12 = var6.a(var2.apply(var4));
            IBlockData var13 = var5.a_(var12);
            if (var13.a(var4.b())) {
               DoubleBlockFinder.BlockType var14 = var1.apply(var13);
               if (var14 != DoubleBlockFinder.BlockType.a && var9 != var14 && var13.c(var3) == var4.c(var3)) {
                  if (var7.test(var5, var12)) {
                     return DoubleBlockFinder.Combiner::b;
                  }

                  S var15 = var0.a(var5, var12);
                  if (var15 != null) {
                     S var16 = var11 ? var8 : var15;
                     S var17 = var11 ? var15 : var8;
                     return new DoubleBlockFinder.Result.Double<>(var16, var17);
                  }
               }
            }

            return new DoubleBlockFinder.Result.Single<>(var8);
         }
      }
   }

   public static enum BlockType {
      a,
      b,
      c;
   }

   public interface Combiner<S, T> {
      T a(S var1, S var2);

      T a(S var1);

      T b();
   }

   public interface Result<S> {
      <T> T apply(DoubleBlockFinder.Combiner<? super S, T> var1);

      public static final class Double<S> implements DoubleBlockFinder.Result<S> {
         private final S a;
         private final S b;

         public Double(S var0, S var1) {
            this.a = var0;
            this.b = var1;
         }

         @Override
         public <T> T apply(DoubleBlockFinder.Combiner<? super S, T> var0) {
            return var0.a(this.a, this.b);
         }
      }

      public static final class Single<S> implements DoubleBlockFinder.Result<S> {
         private final S a;

         public Single(S var0) {
            this.a = var0;
         }

         @Override
         public <T> T apply(DoubleBlockFinder.Combiner<? super S, T> var0) {
            return var0.a(this.a);
         }
      }
   }
}
