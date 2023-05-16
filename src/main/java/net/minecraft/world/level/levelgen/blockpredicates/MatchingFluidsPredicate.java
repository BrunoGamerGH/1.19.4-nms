package net.minecraft.world.level.levelgen.blockpredicates;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BaseBlockPosition;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.material.FluidType;

class MatchingFluidsPredicate extends StateTestingPredicate {
   private final HolderSet<FluidType> e;
   public static final Codec<MatchingFluidsPredicate> a = RecordCodecBuilder.create(
      var0 -> a(var0).and(RegistryCodecs.a(Registries.v).fieldOf("fluids").forGetter(var0x -> var0x.e)).apply(var0, MatchingFluidsPredicate::new)
   );

   public MatchingFluidsPredicate(BaseBlockPosition var0, HolderSet<FluidType> var1) {
      super(var0);
      this.e = var1;
   }

   @Override
   protected boolean a(IBlockData var0) {
      return var0.r().a(this.e);
   }

   @Override
   public BlockPredicateType<?> a() {
      return BlockPredicateType.c;
   }
}
