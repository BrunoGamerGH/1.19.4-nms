package net.minecraft.world.level.levelgen.placement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.stream.Stream;
import net.minecraft.core.BlockPosition;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;

public class RandomOffsetPlacement extends PlacementModifier {
   public static final Codec<RandomOffsetPlacement> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               IntProvider.b(-16, 16).fieldOf("xz_spread").forGetter(var0x -> var0x.c), IntProvider.b(-16, 16).fieldOf("y_spread").forGetter(var0x -> var0x.d)
            )
            .apply(var0, RandomOffsetPlacement::new)
   );
   private final IntProvider c;
   private final IntProvider d;

   public static RandomOffsetPlacement a(IntProvider var0, IntProvider var1) {
      return new RandomOffsetPlacement(var0, var1);
   }

   public static RandomOffsetPlacement a(IntProvider var0) {
      return new RandomOffsetPlacement(ConstantInt.a(0), var0);
   }

   public static RandomOffsetPlacement b(IntProvider var0) {
      return new RandomOffsetPlacement(var0, ConstantInt.a(0));
   }

   private RandomOffsetPlacement(IntProvider var0, IntProvider var1) {
      this.c = var0;
      this.d = var1;
   }

   @Override
   public Stream<BlockPosition> a_(PlacementContext var0, RandomSource var1, BlockPosition var2) {
      int var3 = var2.u() + this.c.a(var1);
      int var4 = var2.v() + this.d.a(var1);
      int var5 = var2.w() + this.c.a(var1);
      return Stream.of(new BlockPosition(var3, var4, var5));
   }

   @Override
   public PlacementModifierType<?> b() {
      return PlacementModifierType.n;
   }
}
