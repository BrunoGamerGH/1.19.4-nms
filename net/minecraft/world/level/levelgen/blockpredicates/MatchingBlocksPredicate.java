package net.minecraft.world.level.levelgen.blockpredicates;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BaseBlockPosition;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.IBlockData;

class MatchingBlocksPredicate extends StateTestingPredicate {
   private final HolderSet<Block> e;
   public static final Codec<MatchingBlocksPredicate> a = RecordCodecBuilder.create(
      var0 -> a(var0).and(RegistryCodecs.a(Registries.e).fieldOf("blocks").forGetter(var0x -> var0x.e)).apply(var0, MatchingBlocksPredicate::new)
   );

   public MatchingBlocksPredicate(BaseBlockPosition var0, HolderSet<Block> var1) {
      super(var0);
      this.e = var1;
   }

   @Override
   protected boolean a(IBlockData var0) {
      return var0.a(this.e);
   }

   @Override
   public BlockPredicateType<?> a() {
      return BlockPredicateType.a;
   }
}
