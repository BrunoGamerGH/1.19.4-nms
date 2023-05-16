package net.minecraft.world.level.levelgen.blockpredicates;

import com.mojang.serialization.Codec;
import net.minecraft.core.IRegistry;
import net.minecraft.core.registries.BuiltInRegistries;

public interface BlockPredicateType<P extends BlockPredicate> {
   BlockPredicateType<MatchingBlocksPredicate> a = a("matching_blocks", MatchingBlocksPredicate.a);
   BlockPredicateType<MatchingBlockTagPredicate> b = a("matching_block_tag", MatchingBlockTagPredicate.e);
   BlockPredicateType<MatchingFluidsPredicate> c = a("matching_fluids", MatchingFluidsPredicate.a);
   BlockPredicateType<HasSturdyFacePredicate> d = a("has_sturdy_face", HasSturdyFacePredicate.a);
   BlockPredicateType<SolidPredicate> e = a("solid", SolidPredicate.a);
   BlockPredicateType<ReplaceablePredicate> f = a("replaceable", ReplaceablePredicate.a);
   BlockPredicateType<WouldSurvivePredicate> g = a("would_survive", WouldSurvivePredicate.a);
   BlockPredicateType<InsideWorldBoundsPredicate> h = a("inside_world_bounds", InsideWorldBoundsPredicate.a);
   BlockPredicateType<AnyOfPredicate> i = a("any_of", AnyOfPredicate.a);
   BlockPredicateType<AllOfPredicate> j = a("all_of", AllOfPredicate.a);
   BlockPredicateType<NotPredicate> k = a("not", NotPredicate.a);
   BlockPredicateType<TrueBlockPredicate> l = a("true", TrueBlockPredicate.e);

   Codec<P> codec();

   private static <P extends BlockPredicate> BlockPredicateType<P> a(String var0, Codec<P> var1) {
      return IRegistry.a(BuiltInRegistries.O, var0, () -> var1);
   }
}
