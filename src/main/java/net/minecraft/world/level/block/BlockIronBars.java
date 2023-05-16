package net.minecraft.world.level.block;

import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.tags.TagsBlock;
import net.minecraft.world.item.context.BlockActionContext;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidTypes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;
import net.minecraft.world.phys.shapes.VoxelShapes;

public class BlockIronBars extends BlockTall {
   protected BlockIronBars(BlockBase.Info var0) {
      super(1.0F, 1.0F, 16.0F, 16.0F, 16.0F, var0);
      this.k(
         this.D
            .b()
            .a(a, Boolean.valueOf(false))
            .a(b, Boolean.valueOf(false))
            .a(c, Boolean.valueOf(false))
            .a(d, Boolean.valueOf(false))
            .a(e, Boolean.valueOf(false))
      );
   }

   @Override
   public IBlockData a(BlockActionContext var0) {
      IBlockAccess var1 = var0.q();
      BlockPosition var2 = var0.a();
      Fluid var3 = var0.q().b_(var0.a());
      BlockPosition var4 = var2.e();
      BlockPosition var5 = var2.f();
      BlockPosition var6 = var2.g();
      BlockPosition var7 = var2.h();
      IBlockData var8 = var1.a_(var4);
      IBlockData var9 = var1.a_(var5);
      IBlockData var10 = var1.a_(var6);
      IBlockData var11 = var1.a_(var7);
      return this.o()
         .a(a, Boolean.valueOf(this.a(var8, var8.d(var1, var4, EnumDirection.d))))
         .a(c, Boolean.valueOf(this.a(var9, var9.d(var1, var5, EnumDirection.c))))
         .a(d, Boolean.valueOf(this.a(var10, var10.d(var1, var6, EnumDirection.f))))
         .a(b, Boolean.valueOf(this.a(var11, var11.d(var1, var7, EnumDirection.e))))
         .a(e, Boolean.valueOf(var3.a() == FluidTypes.c));
   }

   @Override
   public IBlockData a(IBlockData var0, EnumDirection var1, IBlockData var2, GeneratorAccess var3, BlockPosition var4, BlockPosition var5) {
      if (var0.c(e)) {
         var3.a(var4, FluidTypes.c, FluidTypes.c.a(var3));
      }

      return var1.o().d() ? var0.a(f.get(var1), Boolean.valueOf(this.a(var2, var2.d(var3, var5, var1.g())))) : super.a(var0, var1, var2, var3, var4, var5);
   }

   @Override
   public VoxelShape b(IBlockData var0, IBlockAccess var1, BlockPosition var2, VoxelShapeCollision var3) {
      return VoxelShapes.a();
   }

   @Override
   public boolean a(IBlockData var0, IBlockData var1, EnumDirection var2) {
      if (var1.a(this)) {
         if (!var2.o().d()) {
            return true;
         }

         if (var0.c(f.get(var2)) && var1.c(f.get(var2.g()))) {
            return true;
         }
      }

      return super.a(var0, var1, var2);
   }

   public final boolean a(IBlockData var0, boolean var1) {
      return !j(var0) && var1 || var0.b() instanceof BlockIronBars || var0.a(TagsBlock.K);
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> var0) {
      var0.a(a, b, d, c, e);
   }
}
