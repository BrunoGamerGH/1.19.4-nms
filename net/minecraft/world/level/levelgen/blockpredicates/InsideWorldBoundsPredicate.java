package net.minecraft.world.level.levelgen.blockpredicates;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BaseBlockPosition;
import net.minecraft.core.BlockPosition;
import net.minecraft.world.level.GeneratorAccessSeed;

public class InsideWorldBoundsPredicate implements BlockPredicate {
   public static final Codec<InsideWorldBoundsPredicate> a = RecordCodecBuilder.create(
      var0 -> var0.group(BaseBlockPosition.v(16).optionalFieldOf("offset", BlockPosition.b).forGetter(var0x -> var0x.e))
            .apply(var0, InsideWorldBoundsPredicate::new)
   );
   private final BaseBlockPosition e;

   public InsideWorldBoundsPredicate(BaseBlockPosition var0) {
      this.e = var0;
   }

   public boolean a(GeneratorAccessSeed var0, BlockPosition var1) {
      return !var0.u(var1.a(this.e));
   }

   @Override
   public BlockPredicateType<?> a() {
      return BlockPredicateType.h;
   }
}
