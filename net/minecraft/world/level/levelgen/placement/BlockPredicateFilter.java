package net.minecraft.world.level.levelgen.placement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPosition;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;

public class BlockPredicateFilter extends PlacementFilter {
   public static final Codec<BlockPredicateFilter> a = RecordCodecBuilder.create(
      var0 -> var0.group(BlockPredicate.b.fieldOf("predicate").forGetter(var0x -> var0x.c)).apply(var0, BlockPredicateFilter::new)
   );
   private final BlockPredicate c;

   private BlockPredicateFilter(BlockPredicate var0) {
      this.c = var0;
   }

   public static BlockPredicateFilter a(BlockPredicate var0) {
      return new BlockPredicateFilter(var0);
   }

   @Override
   protected boolean a(PlacementContext var0, RandomSource var1, BlockPosition var2) {
      return this.c.test(var0.d(), var2);
   }

   @Override
   public PlacementModifierType<?> b() {
      return PlacementModifierType.a;
   }
}
