package net.minecraft.world.level.levelgen.structure.templatesystem;

import com.mojang.serialization.Codec;
import javax.annotation.Nullable;
import net.minecraft.SystemUtils;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.tags.TagsBlock;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BlockStairs;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockPropertyHalf;

public class DefinedStructureProcessorBlockAge extends DefinedStructureProcessor {
   public static final Codec<DefinedStructureProcessorBlockAge> a = Codec.FLOAT
      .fieldOf("mossiness")
      .xmap(DefinedStructureProcessorBlockAge::new, var0 -> var0.f)
      .codec();
   private static final float b = 0.5F;
   private static final float c = 0.5F;
   private static final float d = 0.15F;
   private static final IBlockData[] e = new IBlockData[]{Blocks.jC.o(), Blocks.jJ.o()};
   private final float f;

   public DefinedStructureProcessorBlockAge(float var0) {
      this.f = var0;
   }

   @Nullable
   @Override
   public DefinedStructure.BlockInfo a(
      IWorldReader var0, BlockPosition var1, BlockPosition var2, DefinedStructure.BlockInfo var3, DefinedStructure.BlockInfo var4, DefinedStructureInfo var5
   ) {
      RandomSource var6 = var5.b(var4.a);
      IBlockData var7 = var4.b;
      BlockPosition var8 = var4.a;
      IBlockData var9 = null;
      if (var7.a(Blocks.eH) || var7.a(Blocks.b) || var7.a(Blocks.eK)) {
         var9 = this.a(var6);
      } else if (var7.a(TagsBlock.I)) {
         var9 = this.a(var6, var4.b);
      } else if (var7.a(TagsBlock.J)) {
         var9 = this.b(var6);
      } else if (var7.a(TagsBlock.K)) {
         var9 = this.c(var6);
      } else if (var7.a(Blocks.cn)) {
         var9 = this.d(var6);
      }

      return var9 != null ? new DefinedStructure.BlockInfo(var8, var9, var4.c) : var4;
   }

   @Nullable
   private IBlockData a(RandomSource var0) {
      if (var0.i() >= 0.5F) {
         return null;
      } else {
         IBlockData[] var1 = new IBlockData[]{Blocks.eJ.o(), a(var0, Blocks.fi)};
         IBlockData[] var2 = new IBlockData[]{Blocks.eI.o(), a(var0, Blocks.nc)};
         return this.a(var0, var1, var2);
      }
   }

   @Nullable
   private IBlockData a(RandomSource var0, IBlockData var1) {
      EnumDirection var2 = var1.c(BlockStairs.a);
      BlockPropertyHalf var3 = var1.c(BlockStairs.b);
      if (var0.i() >= 0.5F) {
         return null;
      } else {
         IBlockData[] var4 = new IBlockData[]{Blocks.nc.o().a(BlockStairs.a, var2).a(BlockStairs.b, var3), Blocks.nq.o()};
         return this.a(var0, e, var4);
      }
   }

   @Nullable
   private IBlockData b(RandomSource var0) {
      return var0.i() < this.f ? Blocks.nq.o() : null;
   }

   @Nullable
   private IBlockData c(RandomSource var0) {
      return var0.i() < this.f ? Blocks.nE.o() : null;
   }

   @Nullable
   private IBlockData d(RandomSource var0) {
      return var0.i() < 0.15F ? Blocks.pg.o() : null;
   }

   private static IBlockData a(RandomSource var0, Block var1) {
      return var1.o().a(BlockStairs.a, EnumDirection.EnumDirectionLimit.a.a(var0)).a(BlockStairs.b, SystemUtils.a(BlockPropertyHalf.values(), var0));
   }

   private IBlockData a(RandomSource var0, IBlockData[] var1, IBlockData[] var2) {
      return var0.i() < this.f ? a(var0, var2) : a(var0, var1);
   }

   private static IBlockData a(RandomSource var0, IBlockData[] var1) {
      return var1[var0.a(var1.length)];
   }

   @Override
   protected DefinedStructureStructureProcessorType<?> a() {
      return DefinedStructureStructureProcessorType.g;
   }
}
