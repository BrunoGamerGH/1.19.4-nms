package net.minecraft.world.level.levelgen.blockpredicates;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BaseBlockPosition;
import net.minecraft.world.level.block.state.IBlockData;

public class SolidPredicate extends StateTestingPredicate {
   public static final Codec<SolidPredicate> a = RecordCodecBuilder.create(var0 -> a(var0).apply(var0, SolidPredicate::new));

   public SolidPredicate(BaseBlockPosition var0) {
      super(var0);
   }

   @Override
   protected boolean a(IBlockData var0) {
      return var0.d().b();
   }

   @Override
   public BlockPredicateType<?> a() {
      return BlockPredicateType.e;
   }
}
