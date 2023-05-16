package net.minecraft.world.level.levelgen.placement;

import java.util.stream.Stream;
import net.minecraft.core.BlockPosition;
import net.minecraft.util.RandomSource;

public abstract class PlacementFilter extends PlacementModifier {
   @Override
   public final Stream<BlockPosition> a_(PlacementContext var0, RandomSource var1, BlockPosition var2) {
      return this.a(var0, var1, var2) ? Stream.of(var2) : Stream.of();
   }

   protected abstract boolean a(PlacementContext var1, RandomSource var2, BlockPosition var3);
}
