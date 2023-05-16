package net.minecraft.world.level.levelgen;

import com.google.common.annotations.VisibleForTesting;
import net.minecraft.core.BlockPosition;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.util.RandomSource;

public interface PositionalRandomFactory {
   default RandomSource a(BlockPosition var0) {
      return this.a(var0.u(), var0.v(), var0.w());
   }

   default RandomSource a(MinecraftKey var0) {
      return this.a(var0.toString());
   }

   RandomSource a(String var1);

   RandomSource a(int var1, int var2, int var3);

   @VisibleForTesting
   void a(StringBuilder var1);
}
