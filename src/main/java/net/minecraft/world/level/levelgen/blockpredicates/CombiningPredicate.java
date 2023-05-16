package net.minecraft.world.level.levelgen.blockpredicates;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.function.Function;

abstract class CombiningPredicate implements BlockPredicate {
   protected final List<BlockPredicate> e;

   protected CombiningPredicate(List<BlockPredicate> var0) {
      this.e = var0;
   }

   public static <T extends CombiningPredicate> Codec<T> a(Function<List<BlockPredicate>, T> var0) {
      return RecordCodecBuilder.create(var1 -> var1.group(BlockPredicate.b.listOf().fieldOf("predicates").forGetter(var0xx -> var0xx.e)).apply(var1, var0));
   }
}
