package net.minecraft.world.level.levelgen.blockpredicates;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BaseBlockPosition;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.world.level.GeneratorAccessSeed;

public class HasSturdyFacePredicate implements BlockPredicate {
   private final BaseBlockPosition e;
   private final EnumDirection f;
   public static final Codec<HasSturdyFacePredicate> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               BaseBlockPosition.v(16).optionalFieldOf("offset", BaseBlockPosition.g).forGetter(var0x -> var0x.e),
               EnumDirection.g.fieldOf("direction").forGetter(var0x -> var0x.f)
            )
            .apply(var0, HasSturdyFacePredicate::new)
   );

   public HasSturdyFacePredicate(BaseBlockPosition var0, EnumDirection var1) {
      this.e = var0;
      this.f = var1;
   }

   public boolean a(GeneratorAccessSeed var0, BlockPosition var1) {
      BlockPosition var2 = var1.a(this.e);
      return var0.a_(var2).d(var0, var2, this.f);
   }

   @Override
   public BlockPredicateType<?> a() {
      return BlockPredicateType.d;
   }
}
