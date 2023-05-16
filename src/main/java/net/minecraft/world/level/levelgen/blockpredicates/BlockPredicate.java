package net.minecraft.world.level.levelgen.blockpredicates;

import com.mojang.serialization.Codec;
import java.util.List;
import java.util.function.BiPredicate;
import net.minecraft.core.BaseBlockPosition;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.material.FluidType;
import net.minecraft.world.level.material.FluidTypes;

public interface BlockPredicate extends BiPredicate<GeneratorAccessSeed, BlockPosition> {
   Codec<BlockPredicate> b = BuiltInRegistries.O.q().dispatch(BlockPredicate::a, BlockPredicateType::codec);
   BlockPredicate c = a(Blocks.a);
   BlockPredicate d = a(Blocks.a, Blocks.G);

   BlockPredicateType<?> a();

   static BlockPredicate a(List<BlockPredicate> var0) {
      return new AllOfPredicate(var0);
   }

   static BlockPredicate a(BlockPredicate... var0) {
      return a(List.of(var0));
   }

   static BlockPredicate a(BlockPredicate var0, BlockPredicate var1) {
      return a(List.of(var0, var1));
   }

   static BlockPredicate b(List<BlockPredicate> var0) {
      return new AnyOfPredicate(var0);
   }

   static BlockPredicate b(BlockPredicate... var0) {
      return b(List.of(var0));
   }

   static BlockPredicate b(BlockPredicate var0, BlockPredicate var1) {
      return b(List.of(var0, var1));
   }

   static BlockPredicate a(BaseBlockPosition var0, List<Block> var1) {
      return new MatchingBlocksPredicate(var0, HolderSet.a(Block::r, var1));
   }

   static BlockPredicate c(List<Block> var0) {
      return a(BaseBlockPosition.g, var0);
   }

   static BlockPredicate a(BaseBlockPosition var0, Block... var1) {
      return a(var0, List.of(var1));
   }

   static BlockPredicate a(Block... var0) {
      return a(BaseBlockPosition.g, var0);
   }

   static BlockPredicate a(BaseBlockPosition var0, TagKey<Block> var1) {
      return new MatchingBlockTagPredicate(var0, var1);
   }

   static BlockPredicate a(TagKey<Block> var0) {
      return a(BaseBlockPosition.g, var0);
   }

   static BlockPredicate b(BaseBlockPosition var0, List<FluidType> var1) {
      return new MatchingFluidsPredicate(var0, HolderSet.a(FluidType::k, var1));
   }

   static BlockPredicate a(BaseBlockPosition var0, FluidType... var1) {
      return b(var0, List.of(var1));
   }

   static BlockPredicate a(FluidType... var0) {
      return a(BaseBlockPosition.g, var0);
   }

   static BlockPredicate a(BlockPredicate var0) {
      return new NotPredicate(var0);
   }

   static BlockPredicate a(BaseBlockPosition var0) {
      return new ReplaceablePredicate(var0);
   }

   static BlockPredicate b() {
      return a(BaseBlockPosition.g);
   }

   static BlockPredicate a(IBlockData var0, BaseBlockPosition var1) {
      return new WouldSurvivePredicate(var1, var0);
   }

   static BlockPredicate a(BaseBlockPosition var0, EnumDirection var1) {
      return new HasSturdyFacePredicate(var0, var1);
   }

   static BlockPredicate a(EnumDirection var0) {
      return a(BaseBlockPosition.g, var0);
   }

   static BlockPredicate b(BaseBlockPosition var0) {
      return new SolidPredicate(var0);
   }

   static BlockPredicate c() {
      return b(BaseBlockPosition.g);
   }

   static BlockPredicate d() {
      return c(BaseBlockPosition.g);
   }

   static BlockPredicate c(BaseBlockPosition var0) {
      return a(var0, FluidTypes.a);
   }

   static BlockPredicate d(BaseBlockPosition var0) {
      return new InsideWorldBoundsPredicate(var0);
   }

   static BlockPredicate e() {
      return TrueBlockPredicate.a;
   }
}
