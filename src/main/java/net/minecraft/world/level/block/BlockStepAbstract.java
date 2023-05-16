package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.tags.TagsFluid;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockActionContext;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockPropertySlabType;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.block.state.properties.BlockStateEnum;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidType;
import net.minecraft.world.level.material.FluidTypes;
import net.minecraft.world.level.pathfinder.PathMode;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;
import net.minecraft.world.phys.shapes.VoxelShapes;

public class BlockStepAbstract extends Block implements IBlockWaterlogged {
   public static final BlockStateEnum<BlockPropertySlabType> a = BlockProperties.bh;
   public static final BlockStateBoolean b = BlockProperties.C;
   protected static final VoxelShape c = Block.a(0.0, 0.0, 0.0, 16.0, 8.0, 16.0);
   protected static final VoxelShape d = Block.a(0.0, 8.0, 0.0, 16.0, 16.0, 16.0);

   public BlockStepAbstract(BlockBase.Info var0) {
      super(var0);
      this.k(this.o().a(a, BlockPropertySlabType.b).a(b, Boolean.valueOf(false)));
   }

   @Override
   public boolean g_(IBlockData var0) {
      return var0.c(a) != BlockPropertySlabType.c;
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> var0) {
      var0.a(a, b);
   }

   @Override
   public VoxelShape a(IBlockData var0, IBlockAccess var1, BlockPosition var2, VoxelShapeCollision var3) {
      BlockPropertySlabType var4 = var0.c(a);
      switch(var4) {
         case c:
            return VoxelShapes.b();
         case a:
            return d;
         default:
            return c;
      }
   }

   @Nullable
   @Override
   public IBlockData a(BlockActionContext var0) {
      BlockPosition var1 = var0.a();
      IBlockData var2 = var0.q().a_(var1);
      if (var2.a(this)) {
         return var2.a(a, BlockPropertySlabType.c).a(b, Boolean.valueOf(false));
      } else {
         Fluid var3 = var0.q().b_(var1);
         IBlockData var4 = this.o().a(a, BlockPropertySlabType.b).a(b, Boolean.valueOf(var3.a() == FluidTypes.c));
         EnumDirection var5 = var0.k();
         return var5 != EnumDirection.a && (var5 == EnumDirection.b || !(var0.l().d - (double)var1.v() > 0.5)) ? var4 : var4.a(a, BlockPropertySlabType.a);
      }
   }

   @Override
   public boolean a(IBlockData var0, BlockActionContext var1) {
      ItemStack var2 = var1.n();
      BlockPropertySlabType var3 = var0.c(a);
      if (var3 == BlockPropertySlabType.c || !var2.a(this.k())) {
         return false;
      } else if (var1.c()) {
         boolean var4 = var1.l().d - (double)var1.a().v() > 0.5;
         EnumDirection var5 = var1.k();
         if (var3 == BlockPropertySlabType.b) {
            return var5 == EnumDirection.b || var4 && var5.o().d();
         } else {
            return var5 == EnumDirection.a || !var4 && var5.o().d();
         }
      } else {
         return true;
      }
   }

   @Override
   public Fluid c_(IBlockData var0) {
      return var0.c(b) ? FluidTypes.c.a(false) : super.c_(var0);
   }

   @Override
   public boolean a(GeneratorAccess var0, BlockPosition var1, IBlockData var2, Fluid var3) {
      return var2.c(a) != BlockPropertySlabType.c ? IBlockWaterlogged.super.a(var0, var1, var2, var3) : false;
   }

   @Override
   public boolean a(IBlockAccess var0, BlockPosition var1, IBlockData var2, FluidType var3) {
      return var2.c(a) != BlockPropertySlabType.c ? IBlockWaterlogged.super.a(var0, var1, var2, var3) : false;
   }

   @Override
   public IBlockData a(IBlockData var0, EnumDirection var1, IBlockData var2, GeneratorAccess var3, BlockPosition var4, BlockPosition var5) {
      if (var0.c(b)) {
         var3.a(var4, FluidTypes.c, FluidTypes.c.a(var3));
      }

      return super.a(var0, var1, var2, var3, var4, var5);
   }

   @Override
   public boolean a(IBlockData var0, IBlockAccess var1, BlockPosition var2, PathMode var3) {
      switch(var3) {
         case a:
            return false;
         case b:
            return var1.b_(var2).a(TagsFluid.a);
         case c:
            return false;
         default:
            return false;
      }
   }
}
