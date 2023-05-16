package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockActionContext;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.block.state.properties.BlockStateDirection;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;

public class BlockRedstoneTorchWall extends BlockRedstoneTorch {
   public static final BlockStateDirection e = BlockFacingHorizontal.aD;
   public static final BlockStateBoolean f = BlockRedstoneTorch.a;

   protected BlockRedstoneTorchWall(BlockBase.Info var0) {
      super(var0);
      this.k(this.D.b().a(e, EnumDirection.c).a(f, Boolean.valueOf(true)));
   }

   @Override
   public String h() {
      return this.k().a();
   }

   @Override
   public VoxelShape a(IBlockData var0, IBlockAccess var1, BlockPosition var2, VoxelShapeCollision var3) {
      return BlockTorchWall.h(var0);
   }

   @Override
   public boolean a(IBlockData var0, IWorldReader var1, BlockPosition var2) {
      return Blocks.cp.a(var0, var1, var2);
   }

   @Override
   public IBlockData a(IBlockData var0, EnumDirection var1, IBlockData var2, GeneratorAccess var3, BlockPosition var4, BlockPosition var5) {
      return Blocks.cp.a(var0, var1, var2, var3, var4, var5);
   }

   @Nullable
   @Override
   public IBlockData a(BlockActionContext var0) {
      IBlockData var1 = Blocks.cp.a(var0);
      return var1 == null ? null : this.o().a(e, var1.c(e));
   }

   @Override
   public void a(IBlockData var0, World var1, BlockPosition var2, RandomSource var3) {
      if (var0.c(f)) {
         EnumDirection var4 = var0.c(e).g();
         double var5 = 0.27;
         double var7 = (double)var2.u() + 0.5 + (var3.j() - 0.5) * 0.2 + 0.27 * (double)var4.j();
         double var9 = (double)var2.v() + 0.7 + (var3.j() - 0.5) * 0.2 + 0.22;
         double var11 = (double)var2.w() + 0.5 + (var3.j() - 0.5) * 0.2 + 0.27 * (double)var4.l();
         var1.a(this.i, var7, var9, var11, 0.0, 0.0, 0.0);
      }
   }

   @Override
   protected boolean a(World var0, BlockPosition var1, IBlockData var2) {
      EnumDirection var3 = var2.c(e).g();
      return var0.a(var1.a(var3), var3);
   }

   @Override
   public int a(IBlockData var0, IBlockAccess var1, BlockPosition var2, EnumDirection var3) {
      return var0.c(f) && var0.c(e) != var3 ? 15 : 0;
   }

   @Override
   public IBlockData a(IBlockData var0, EnumBlockRotation var1) {
      return Blocks.cp.a(var0, var1);
   }

   @Override
   public IBlockData a(IBlockData var0, EnumBlockMirror var1) {
      return Blocks.cp.a(var0, var1);
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> var0) {
      var0.a(e, f);
   }
}
