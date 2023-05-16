package net.minecraft.world.level.levelgen.blockpredicates;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BaseBlockPosition;
import net.minecraft.core.BlockPosition;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.block.state.IBlockData;

public class WouldSurvivePredicate implements BlockPredicate {
   public static final Codec<WouldSurvivePredicate> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               BaseBlockPosition.v(16).optionalFieldOf("offset", BaseBlockPosition.g).forGetter(var0x -> var0x.e),
               IBlockData.b.fieldOf("state").forGetter(var0x -> var0x.f)
            )
            .apply(var0, WouldSurvivePredicate::new)
   );
   private final BaseBlockPosition e;
   private final IBlockData f;

   protected WouldSurvivePredicate(BaseBlockPosition var0, IBlockData var1) {
      this.e = var0;
      this.f = var1;
   }

   public boolean a(GeneratorAccessSeed var0, BlockPosition var1) {
      return this.f.a(var0, var1.a(this.e));
   }

   @Override
   public BlockPredicateType<?> a() {
      return BlockPredicateType.g;
   }
}
