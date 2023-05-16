package net.minecraft.world.level.levelgen.blockpredicates;

import com.mojang.serialization.Codec;
import java.util.List;
import net.minecraft.core.BlockPosition;
import net.minecraft.world.level.GeneratorAccessSeed;

class AnyOfPredicate extends CombiningPredicate {
   public static final Codec<AnyOfPredicate> a = a(AnyOfPredicate::new);

   public AnyOfPredicate(List<BlockPredicate> var0) {
      super(var0);
   }

   public boolean a(GeneratorAccessSeed var0, BlockPosition var1) {
      for(BlockPredicate var3 : this.e) {
         if (var3.test(var0, var1)) {
            return true;
         }
      }

      return false;
   }

   @Override
   public BlockPredicateType<?> a() {
      return BlockPredicateType.i;
   }
}
