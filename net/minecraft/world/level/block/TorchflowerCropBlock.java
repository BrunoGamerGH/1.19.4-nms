package net.minecraft.world.level.block;

import net.minecraft.core.BlockPosition;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.IMaterial;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockStateInteger;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;

public class TorchflowerCropBlock extends BlockCrops {
   public static final int a = 2;
   public static final BlockStateInteger b = BlockProperties.ar;
   private static final float e = 3.0F;
   private static final VoxelShape[] f = new VoxelShape[]{
      Block.a(5.0, 0.0, 5.0, 11.0, 10.0, 11.0), Block.a(5.0, 0.0, 5.0, 11.0, 10.0, 11.0), Block.a(5.0, 0.0, 5.0, 11.0, 10.0, 11.0)
   };

   public TorchflowerCropBlock(BlockBase.Info var0) {
      super(var0);
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> var0) {
      var0.a(b);
   }

   @Override
   public VoxelShape a(IBlockData var0, IBlockAccess var1, BlockPosition var2, VoxelShapeCollision var3) {
      return f[var0.c(this.b())];
   }

   @Override
   public BlockStateInteger b() {
      return b;
   }

   @Override
   public int c() {
      return 2;
   }

   @Override
   protected IMaterial d() {
      return Items.uk;
   }

   @Override
   public IBlockData b(int var0) {
      return var0 == 2 ? Blocks.bR.o() : super.b(var0);
   }

   @Override
   public void b(IBlockData var0, WorldServer var1, BlockPosition var2, RandomSource var3) {
      if (var3.a(3) != 0) {
         super.b(var0, var1, var2, var3);
      }
   }

   @Override
   protected int a(World var0) {
      return super.a(var0) / 3;
   }
}
