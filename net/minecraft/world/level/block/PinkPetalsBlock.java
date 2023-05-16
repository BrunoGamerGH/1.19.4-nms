package net.minecraft.world.level.block;

import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockActionContext;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockStateDirection;
import net.minecraft.world.level.block.state.properties.BlockStateInteger;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;

public class PinkPetalsBlock extends BlockPlant implements IBlockFragilePlantElement {
   public static final int a = 1;
   public static final int b = 4;
   public static final BlockStateDirection c = BlockProperties.R;
   public static final BlockStateInteger d = BlockProperties.S;

   protected PinkPetalsBlock(BlockBase.Info var0) {
      super(var0);
      this.k(this.D.b().a(c, EnumDirection.c).a(d, Integer.valueOf(1)));
   }

   @Override
   public IBlockData a(IBlockData var0, EnumBlockRotation var1) {
      return var0.a(c, var1.a(var0.c(c)));
   }

   @Override
   public IBlockData a(IBlockData var0, EnumBlockMirror var1) {
      return var0.a(var1.a(var0.c(c)));
   }

   @Override
   public boolean a(IBlockData var0, BlockActionContext var1) {
      return !var1.h() && var1.n().a(this.k()) && var0.c(d) < 4 ? true : super.a(var0, var1);
   }

   @Override
   public VoxelShape a(IBlockData var0, IBlockAccess var1, BlockPosition var2, VoxelShapeCollision var3) {
      return Block.a(0.0, 0.0, 0.0, 16.0, 3.0, 16.0);
   }

   @Override
   public IBlockData a(BlockActionContext var0) {
      IBlockData var1 = var0.q().a_(var0.a());
      return var1.a(this) ? var1.a(d, Integer.valueOf(Math.min(4, var1.c(d) + 1))) : this.o().a(c, var0.g().g());
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> var0) {
      var0.a(c, d);
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
   public void a(WorldServer var0, RandomSource var1, BlockPosition var2, IBlockData var3) {
      int var4 = var3.c(d);
      if (var4 < 4) {
         var0.a(var2, var3.a(d, Integer.valueOf(var4 + 1)), 2);
      } else {
         a(var0, var2, new ItemStack(this));
      }
   }
}
