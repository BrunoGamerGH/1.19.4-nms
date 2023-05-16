package net.minecraft.world.level.block;

import java.util.Collection;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.material.FluidTypes;

public interface SculkBehaviour {
   SculkBehaviour r_ = new SculkBehaviour() {
      @Override
      public boolean a(GeneratorAccess var0, BlockPosition var1, IBlockData var2, @Nullable Collection<EnumDirection> var3, boolean var4) {
         if (var3 == null) {
            return ((SculkVeinBlock)Blocks.qB).d().a(var0.a_(var1), var0, var1, var4) > 0L;
         } else if (!var3.isEmpty()) {
            return !var2.h() && !var2.r().b(FluidTypes.c) ? false : SculkVeinBlock.a(var0, var1, var2, var3);
         } else {
            return SculkBehaviour.super.a(var0, var1, var2, var3, var4);
         }
      }

      @Override
      public int a(SculkSpreader.a var0, GeneratorAccess var1, BlockPosition var2, RandomSource var3, SculkSpreader var4, boolean var5) {
         return var0.c() > 0 ? var0.b() : 0;
      }

      @Override
      public int i_(int var0) {
         return Math.max(var0 - 1, 0);
      }
   };

   default byte a() {
      return 1;
   }

   default void a(GeneratorAccess var0, IBlockData var1, BlockPosition var2, RandomSource var3) {
   }

   default boolean a(GeneratorAccess var0, BlockPosition var1, RandomSource var2) {
      return false;
   }

   default boolean a(GeneratorAccess var0, BlockPosition var1, IBlockData var2, @Nullable Collection<EnumDirection> var3, boolean var4) {
      return ((MultifaceBlock)Blocks.qB).c().a(var2, var0, var1, var4) > 0L;
   }

   default boolean b() {
      return true;
   }

   default int i_(int var0) {
      return 1;
   }

   int a(SculkSpreader.a var1, GeneratorAccess var2, BlockPosition var3, RandomSource var4, SculkSpreader var5, boolean var6);
}
