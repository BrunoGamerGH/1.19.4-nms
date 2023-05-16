package net.minecraft.world.level.levelgen.placement;

import com.mojang.serialization.Codec;
import java.util.stream.Stream;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.RandomSource;

public abstract class PlacementModifier {
   public static final Codec<PlacementModifier> b = BuiltInRegistries.U.q().dispatch(PlacementModifier::b, PlacementModifierType::codec);

   public abstract Stream<BlockPosition> a_(PlacementContext var1, RandomSource var2, BlockPosition var3);

   public abstract PlacementModifierType<?> b();
}
