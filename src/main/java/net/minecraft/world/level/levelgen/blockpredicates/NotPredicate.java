package net.minecraft.world.level.levelgen.blockpredicates;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPosition;
import net.minecraft.world.level.GeneratorAccessSeed;

class NotPredicate implements BlockPredicate {
   public static final Codec<NotPredicate> a = RecordCodecBuilder.create(
      var0 -> var0.group(BlockPredicate.b.fieldOf("predicate").forGetter(var0x -> var0x.e)).apply(var0, NotPredicate::new)
   );
   private final BlockPredicate e;

   public NotPredicate(BlockPredicate var0) {
      this.e = var0;
   }

   public boolean a(GeneratorAccessSeed var0, BlockPosition var1) {
      return !this.e.test(var0, var1);
   }

   @Override
   public BlockPredicateType<?> a() {
      return BlockPredicateType.k;
   }
}
