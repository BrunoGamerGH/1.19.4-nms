package net.minecraft.world.level.block;

import com.google.common.base.Suppliers;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import java.util.Optional;
import java.util.function.Supplier;
import net.minecraft.world.level.block.state.IBlockData;

public interface WeatheringCopper extends ChangeOverTimeBlock<WeatheringCopper.a> {
   Supplier<BiMap<Block, Block>> s_ = Suppliers.memoize(
      () -> ImmutableBiMap.builder()
            .put(Blocks.qH, Blocks.qG)
            .put(Blocks.qG, Blocks.qF)
            .put(Blocks.qF, Blocks.qE)
            .put(Blocks.qN, Blocks.qM)
            .put(Blocks.qM, Blocks.qL)
            .put(Blocks.qL, Blocks.qK)
            .put(Blocks.qV, Blocks.qU)
            .put(Blocks.qU, Blocks.qT)
            .put(Blocks.qT, Blocks.qS)
            .put(Blocks.qR, Blocks.qQ)
            .put(Blocks.qQ, Blocks.qP)
            .put(Blocks.qP, Blocks.qO)
            .build()
   );
   Supplier<BiMap<Block, Block>> t_ = Suppliers.memoize(() -> ((BiMap)s_.get()).inverse());

   static Optional<Block> a(Block var0) {
      return Optional.ofNullable((Block)((BiMap)t_.get()).get(var0));
   }

   static Block b(Block var0) {
      Block var1 = var0;

      for(Block var2 = (Block)((BiMap)t_.get()).get(var0); var2 != null; var2 = (Block)((BiMap)t_.get()).get(var2)) {
         var1 = var2;
      }

      return var1;
   }

   static Optional<IBlockData> b(IBlockData var0) {
      return a(var0.b()).map(var1 -> var1.l(var0));
   }

   static Optional<Block> c(Block var0) {
      return Optional.ofNullable((Block)((BiMap)s_.get()).get(var0));
   }

   static IBlockData c(IBlockData var0) {
      return b(var0.b()).l(var0);
   }

   @Override
   default Optional<IBlockData> a(IBlockData var0) {
      return c(var0.b()).map(var1x -> var1x.l(var0));
   }

   @Override
   default float a() {
      return this.b() == WeatheringCopper.a.a ? 0.75F : 1.0F;
   }

   public static enum a {
      a,
      b,
      c,
      d;
   }
}
