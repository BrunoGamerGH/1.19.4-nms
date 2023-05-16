package net.minecraft.world.level.levelgen.blockpredicates;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BaseBlockPosition;
import net.minecraft.world.level.block.state.IBlockData;

class ReplaceablePredicate extends StateTestingPredicate {
   public static final Codec<ReplaceablePredicate> a = RecordCodecBuilder.create(var0 -> a(var0).apply(var0, ReplaceablePredicate::new));

   public ReplaceablePredicate(BaseBlockPosition var0) {
      super(var0);
   }

   @Override
   protected boolean a(IBlockData var0) {
      return var0.o();
   }

   @Override
   public BlockPredicateType<?> a() {
      return BlockPredicateType.f;
   }
}
