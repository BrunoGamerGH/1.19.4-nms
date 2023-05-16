package net.minecraft.world.level.block;

import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.world.item.context.BlockActionContext;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockPropertyWood;
import net.minecraft.world.level.block.state.properties.BlockStateInteger;
import net.minecraft.world.level.block.state.properties.RotationSegment;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidTypes;

public class BlockFloorSign extends BlockSign {
   public static final BlockStateInteger a = BlockProperties.ba;

   public BlockFloorSign(BlockBase.Info var0, BlockPropertyWood var1) {
      super(var0.a(var1.d()), var1);
      this.k(this.D.b().a(a, Integer.valueOf(0)).a(e, Boolean.valueOf(false)));
   }

   @Override
   public boolean a(IBlockData var0, IWorldReader var1, BlockPosition var2) {
      return var1.a_(var2.d()).d().b();
   }

   @Override
   public IBlockData a(BlockActionContext var0) {
      Fluid var1 = var0.q().b_(var0.a());
      return this.o().a(a, Integer.valueOf(RotationSegment.a(var0.i() + 180.0F))).a(e, Boolean.valueOf(var1.a() == FluidTypes.c));
   }

   @Override
   public IBlockData a(IBlockData var0, EnumDirection var1, IBlockData var2, GeneratorAccess var3, BlockPosition var4, BlockPosition var5) {
      return var1 == EnumDirection.a && !this.a(var0, var3, var4) ? Blocks.a.o() : super.a(var0, var1, var2, var3, var4, var5);
   }

   @Override
   public IBlockData a(IBlockData var0, EnumBlockRotation var1) {
      return var0.a(a, Integer.valueOf(var1.a(var0.c(a), 16)));
   }

   @Override
   public IBlockData a(IBlockData var0, EnumBlockMirror var1) {
      return var0.a(a, Integer.valueOf(var1.a(var0.c(a), 16)));
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> var0) {
      var0.a(a, e);
   }
}
