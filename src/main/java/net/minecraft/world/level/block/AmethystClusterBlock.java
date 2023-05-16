package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.world.item.context.BlockActionContext;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.block.state.properties.BlockStateDirection;
import net.minecraft.world.level.material.EnumPistonReaction;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidTypes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;

public class AmethystClusterBlock extends AmethystBlock implements IBlockWaterlogged {
   public static final BlockStateBoolean a = BlockProperties.C;
   public static final BlockStateDirection b = BlockProperties.P;
   protected final VoxelShape c;
   protected final VoxelShape d;
   protected final VoxelShape e;
   protected final VoxelShape f;
   protected final VoxelShape g;
   protected final VoxelShape h;

   public AmethystClusterBlock(int var0, int var1, BlockBase.Info var2) {
      super(var2);
      this.k(this.o().a(a, Boolean.valueOf(false)).a(b, EnumDirection.b));
      this.g = Block.a((double)var1, 0.0, (double)var1, (double)(16 - var1), (double)var0, (double)(16 - var1));
      this.h = Block.a((double)var1, (double)(16 - var0), (double)var1, (double)(16 - var1), 16.0, (double)(16 - var1));
      this.c = Block.a((double)var1, (double)var1, (double)(16 - var0), (double)(16 - var1), (double)(16 - var1), 16.0);
      this.d = Block.a((double)var1, (double)var1, 0.0, (double)(16 - var1), (double)(16 - var1), (double)var0);
      this.e = Block.a(0.0, (double)var1, (double)var1, (double)var0, (double)(16 - var1), (double)(16 - var1));
      this.f = Block.a((double)(16 - var0), (double)var1, (double)var1, 16.0, (double)(16 - var1), (double)(16 - var1));
   }

   @Override
   public VoxelShape a(IBlockData var0, IBlockAccess var1, BlockPosition var2, VoxelShapeCollision var3) {
      EnumDirection var4 = var0.c(b);
      switch(var4) {
         case c:
            return this.c;
         case d:
            return this.d;
         case f:
            return this.e;
         case e:
            return this.f;
         case a:
            return this.h;
         case b:
         default:
            return this.g;
      }
   }

   @Override
   public boolean a(IBlockData var0, IWorldReader var1, BlockPosition var2) {
      EnumDirection var3 = var0.c(b);
      BlockPosition var4 = var2.a(var3.g());
      return var1.a_(var4).d(var1, var4, var3);
   }

   @Override
   public IBlockData a(IBlockData var0, EnumDirection var1, IBlockData var2, GeneratorAccess var3, BlockPosition var4, BlockPosition var5) {
      if (var0.c(a)) {
         var3.a(var4, FluidTypes.c, FluidTypes.c.a(var3));
      }

      return var1 == var0.c(b).g() && !var0.a(var3, var4) ? Blocks.a.o() : super.a(var0, var1, var2, var3, var4, var5);
   }

   @Nullable
   @Override
   public IBlockData a(BlockActionContext var0) {
      GeneratorAccess var1 = var0.q();
      BlockPosition var2 = var0.a();
      return this.o().a(a, Boolean.valueOf(var1.b_(var2).a() == FluidTypes.c)).a(b, var0.k());
   }

   @Override
   public IBlockData a(IBlockData var0, EnumBlockRotation var1) {
      return var0.a(b, var1.a(var0.c(b)));
   }

   @Override
   public IBlockData a(IBlockData var0, EnumBlockMirror var1) {
      return var0.a(var1.a(var0.c(b)));
   }

   @Override
   public Fluid c_(IBlockData var0) {
      return var0.c(a) ? FluidTypes.c.a(false) : super.c_(var0);
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> var0) {
      var0.a(a, b);
   }

   @Override
   public EnumPistonReaction d(IBlockData var0) {
      return EnumPistonReaction.b;
   }
}
