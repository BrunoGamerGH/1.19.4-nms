package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.server.level.WorldServer;
import net.minecraft.tags.TagsFluid;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockActionContext;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockPropertyDoubleBlockHalf;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidType;
import net.minecraft.world.level.material.FluidTypes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;

public class SeagrassBlock extends BlockPlant implements IBlockFragilePlantElement, IFluidContainer {
   protected static final float a = 6.0F;
   protected static final VoxelShape b = Block.a(2.0, 0.0, 2.0, 14.0, 12.0, 14.0);

   protected SeagrassBlock(BlockBase.Info var0) {
      super(var0);
   }

   @Override
   public VoxelShape a(IBlockData var0, IBlockAccess var1, BlockPosition var2, VoxelShapeCollision var3) {
      return b;
   }

   @Override
   protected boolean d(IBlockData var0, IBlockAccess var1, BlockPosition var2) {
      return var0.d(var1, var2, EnumDirection.b) && !var0.a(Blocks.kG);
   }

   @Nullable
   @Override
   public IBlockData a(BlockActionContext var0) {
      Fluid var1 = var0.q().b_(var0.a());
      return var1.a(TagsFluid.a) && var1.e() == 8 ? super.a(var0) : null;
   }

   @Override
   public IBlockData a(IBlockData var0, EnumDirection var1, IBlockData var2, GeneratorAccess var3, BlockPosition var4, BlockPosition var5) {
      IBlockData var6 = super.a(var0, var1, var2, var3, var4, var5);
      if (!var6.h()) {
         var3.a(var4, FluidTypes.c, FluidTypes.c.a(var3));
      }

      return var6;
   }

   @Override
   public boolean a(IWorldReader var0, BlockPosition var1, IBlockData var2, boolean var3) {
      return true;
   }

   @Override
   public boolean a(World var0, RandomSource var1, BlockPosition var2, IBlockData var3) {
      return true;
   }

   @Override
   public Fluid c_(IBlockData var0) {
      return FluidTypes.c.a(false);
   }

   @Override
   public void a(WorldServer var0, RandomSource var1, BlockPosition var2, IBlockData var3) {
      IBlockData var4 = Blocks.bw.o();
      IBlockData var5 = var4.a(TallSeagrassBlock.b, BlockPropertyDoubleBlockHalf.a);
      BlockPosition var6 = var2.c();
      if (var0.a_(var6).a(Blocks.G)) {
         var0.a(var2, var4, 2);
         var0.a(var6, var5, 2);
      }
   }

   @Override
   public boolean a(IBlockAccess var0, BlockPosition var1, IBlockData var2, FluidType var3) {
      return false;
   }

   @Override
   public boolean a(GeneratorAccess var0, BlockPosition var1, IBlockData var2, Fluid var3) {
      return false;
   }
}
