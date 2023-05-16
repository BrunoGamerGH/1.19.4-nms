package net.minecraft.world.level.levelgen.blockpredicates;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPosition;
import net.minecraft.world.level.GeneratorAccessSeed;

class TrueBlockPredicate implements BlockPredicate {
   public static TrueBlockPredicate a = new TrueBlockPredicate();
   public static final Codec<TrueBlockPredicate> e = Codec.unit(() -> a);

   private TrueBlockPredicate() {
   }

   public boolean a(GeneratorAccessSeed var0, BlockPosition var1) {
      return true;
   }

   @Override
   public BlockPredicateType<?> a() {
      return BlockPredicateType.l;
   }
}
