package net.minecraft.world.level.levelgen.placement;

import com.mojang.serialization.Codec;
import java.util.stream.Stream;
import net.minecraft.core.BlockPosition;
import net.minecraft.util.RandomSource;

public class InSquarePlacement extends PlacementModifier {
   private static final InSquarePlacement c = new InSquarePlacement();
   public static final Codec<InSquarePlacement> a = Codec.unit(() -> c);

   public static InSquarePlacement a() {
      return c;
   }

   @Override
   public Stream<BlockPosition> a_(PlacementContext var0, RandomSource var1, BlockPosition var2) {
      int var3 = var1.a(16) + var2.u();
      int var4 = var1.a(16) + var2.w();
      return Stream.of(new BlockPosition(var3, var2.v(), var4));
   }

   @Override
   public PlacementModifierType<?> b() {
      return PlacementModifierType.m;
   }
}
