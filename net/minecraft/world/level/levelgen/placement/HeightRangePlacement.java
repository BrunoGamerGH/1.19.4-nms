package net.minecraft.world.level.levelgen.placement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.stream.Stream;
import net.minecraft.core.BlockPosition;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.heightproviders.TrapezoidHeight;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;

public class HeightRangePlacement extends PlacementModifier {
   public static final Codec<HeightRangePlacement> a = RecordCodecBuilder.create(
      var0 -> var0.group(HeightProvider.c.fieldOf("height").forGetter(var0x -> var0x.c)).apply(var0, HeightRangePlacement::new)
   );
   private final HeightProvider c;

   private HeightRangePlacement(HeightProvider var0) {
      this.c = var0;
   }

   public static HeightRangePlacement a(HeightProvider var0) {
      return new HeightRangePlacement(var0);
   }

   public static HeightRangePlacement a(VerticalAnchor var0, VerticalAnchor var1) {
      return a(UniformHeight.a(var0, var1));
   }

   public static HeightRangePlacement b(VerticalAnchor var0, VerticalAnchor var1) {
      return a(TrapezoidHeight.a(var0, var1));
   }

   @Override
   public Stream<BlockPosition> a_(PlacementContext var0, RandomSource var1, BlockPosition var2) {
      return Stream.of(var2.h(this.c.a(var1, var0)));
   }

   @Override
   public PlacementModifierType<?> b() {
      return PlacementModifierType.l;
   }
}
