package net.minecraft.world.level.levelgen.material;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.NoiseChunk;

public record MaterialRuleList(List<NoiseChunk.c> materialRuleList) implements NoiseChunk.c {
   private final List<NoiseChunk.c> a;

   public MaterialRuleList(List<NoiseChunk.c> var0) {
      this.a = var0;
   }

   @Nullable
   @Override
   public IBlockData calculate(DensityFunction.b var0) {
      for(NoiseChunk.c var2 : this.a) {
         IBlockData var3 = var2.calculate(var0);
         if (var3 != null) {
            return var3;
         }
      }

      return null;
   }
}
