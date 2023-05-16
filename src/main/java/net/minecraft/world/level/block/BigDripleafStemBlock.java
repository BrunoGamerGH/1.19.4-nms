package net.minecraft.world.level.block;

import java.util.Optional;
import net.minecraft.BlockUtil;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.server.level.WorldServer;
import net.minecraft.tags.TagsBlock;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidTypes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;

public class BigDripleafStemBlock extends BlockFacingHorizontal implements IBlockFragilePlantElement, IBlockWaterlogged {
   private static final BlockStateBoolean e = BlockProperties.C;
   private static final int f = 6;
   protected static final VoxelShape a = Block.a(5.0, 0.0, 9.0, 11.0, 16.0, 15.0);
   protected static final VoxelShape b = Block.a(5.0, 0.0, 1.0, 11.0, 16.0, 7.0);
   protected static final VoxelShape c = Block.a(1.0, 0.0, 5.0, 7.0, 16.0, 11.0);
   protected static final VoxelShape d = Block.a(9.0, 0.0, 5.0, 15.0, 16.0, 11.0);

   protected BigDripleafStemBlock(BlockBase.Info var0) {
      super(var0);
      this.k(this.D.b().a(e, Boolean.valueOf(false)).a(aD, EnumDirection.c));
   }

   @Override
   public VoxelShape a(IBlockData var0, IBlockAccess var1, BlockPosition var2, VoxelShapeCollision var3) {
      switch((EnumDirection)var0.c(aD)) {
         case d:
            return b;
         case c:
         default:
            return a;
         case e:
            return d;
         case f:
            return c;
      }
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> var0) {
      var0.a(e, aD);
   }

   @Override
   public Fluid c_(IBlockData var0) {
      return var0.c(e) ? FluidTypes.c.a(false) : super.c_(var0);
   }

   @Override
   public boolean a(IBlockData var0, IWorldReader var1, BlockPosition var2) {
      BlockPosition var3 = var2.d();
      IBlockData var4 = var1.a_(var3);
      IBlockData var5 = var1.a_(var2.c());
      return (var4.a(this) || var4.a(TagsBlock.bt)) && (var5.a(this) || var5.a(Blocks.rx));
   }

   protected static boolean a(GeneratorAccess var0, BlockPosition var1, Fluid var2, EnumDirection var3) {
      IBlockData var4 = Blocks.ry.o().a(e, Boolean.valueOf(var2.a(FluidTypes.c))).a(aD, var3);
      return var0.a(var1, var4, 3);
   }

   @Override
   public IBlockData a(IBlockData var0, EnumDirection var1, IBlockData var2, GeneratorAccess var3, BlockPosition var4, BlockPosition var5) {
      if ((var1 == EnumDirection.a || var1 == EnumDirection.b) && !var0.a(var3, var4)) {
         var3.a(var4, this, 1);
      }

      if (var0.c(e)) {
         var3.a(var4, FluidTypes.c, FluidTypes.c.a(var3));
      }

      return super.a(var0, var1, var2, var3, var4, var5);
   }

   @Override
   public void a(IBlockData var0, WorldServer var1, BlockPosition var2, RandomSource var3) {
      if (!var0.a(var1, var2)) {
         var1.b(var2, true);
      }
   }

   @Override
   public boolean a(IWorldReader var0, BlockPosition var1, IBlockData var2, boolean var3) {
      Optional<BlockPosition> var4 = BlockUtil.a(var0, var1, var2.b(), EnumDirection.b, Blocks.rx);
      if (!var4.isPresent()) {
         return false;
      } else {
         BlockPosition var5 = var4.get().c();
         IBlockData var6 = var0.a_(var5);
         return BigDripleafBlock.a(var0, var5, var6);
      }
   }

   @Override
   public boolean a(World var0, RandomSource var1, BlockPosition var2, IBlockData var3) {
      return true;
   }

   @Override
   public void a(WorldServer var0, RandomSource var1, BlockPosition var2, IBlockData var3) {
      Optional<BlockPosition> var4 = BlockUtil.a(var0, var2, var3.b(), EnumDirection.b, Blocks.rx);
      if (var4.isPresent()) {
         BlockPosition var5 = var4.get();
         BlockPosition var6 = var5.c();
         EnumDirection var7 = var3.c(aD);
         a(var0, var5, var0.b_(var5), var7);
         BigDripleafBlock.a(var0, var6, var0.b_(var6), var7);
      }
   }

   @Override
   public ItemStack a(IBlockAccess var0, BlockPosition var1, IBlockData var2) {
      return new ItemStack(Blocks.rx);
   }
}
