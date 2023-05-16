package net.minecraft.world.level.levelgen.blockpredicates;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BaseBlockPosition;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.IBlockData;

public class MatchingBlockTagPredicate extends StateTestingPredicate {
   final TagKey<Block> a;
   public static final Codec<MatchingBlockTagPredicate> e = RecordCodecBuilder.create(
      var0 -> a(var0).and(TagKey.a(Registries.e).fieldOf("tag").forGetter(var0x -> var0x.a)).apply(var0, MatchingBlockTagPredicate::new)
   );

   protected MatchingBlockTagPredicate(BaseBlockPosition var0, TagKey<Block> var1) {
      super(var0);
      this.a = var1;
   }

   @Override
   protected boolean a(IBlockData var0) {
      return var0.a(this.a);
   }

   @Override
   public BlockPredicateType<?> a() {
      return BlockPredicateType.b;
   }
}
