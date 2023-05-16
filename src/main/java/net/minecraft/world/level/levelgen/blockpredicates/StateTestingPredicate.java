package net.minecraft.world.level.levelgen.blockpredicates;

import com.mojang.datafixers.Products.P1;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import com.mojang.serialization.codecs.RecordCodecBuilder.Mu;
import net.minecraft.core.BaseBlockPosition;
import net.minecraft.core.BlockPosition;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.block.state.IBlockData;

public abstract class StateTestingPredicate implements BlockPredicate {
   protected final BaseBlockPosition f;

   protected static <P extends StateTestingPredicate> P1<Mu<P>, BaseBlockPosition> a(Instance<P> var0) {
      return var0.group(BaseBlockPosition.v(16).optionalFieldOf("offset", BaseBlockPosition.g).forGetter(var0x -> var0x.f));
   }

   protected StateTestingPredicate(BaseBlockPosition var0) {
      this.f = var0;
   }

   public final boolean a(GeneratorAccessSeed var0, BlockPosition var1) {
      return this.a(var0.a_(var1.a(this.f)));
   }

   protected abstract boolean a(IBlockData var1);
}
