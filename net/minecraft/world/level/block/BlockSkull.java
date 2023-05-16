package net.minecraft.world.level.block;

import net.minecraft.core.BlockPosition;
import net.minecraft.world.item.context.BlockActionContext;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockStateInteger;
import net.minecraft.world.level.block.state.properties.RotationSegment;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;
import net.minecraft.world.phys.shapes.VoxelShapes;

public class BlockSkull extends BlockSkullAbstract {
   public static final int a = RotationSegment.a();
   private static final int e = a + 1;
   public static final BlockStateInteger b = BlockProperties.ba;
   protected static final VoxelShape c = Block.a(4.0, 0.0, 4.0, 12.0, 8.0, 12.0);
   protected static final VoxelShape d = Block.a(3.0, 0.0, 3.0, 13.0, 8.0, 13.0);

   protected BlockSkull(BlockSkull.a var0, BlockBase.Info var1) {
      super(var0, var1);
      this.k(this.D.b().a(b, Integer.valueOf(0)));
   }

   @Override
   public VoxelShape a(IBlockData var0, IBlockAccess var1, BlockPosition var2, VoxelShapeCollision var3) {
      return this.b() == BlockSkull.Type.f ? d : c;
   }

   @Override
   public VoxelShape f(IBlockData var0, IBlockAccess var1, BlockPosition var2) {
      return VoxelShapes.a();
   }

   @Override
   public IBlockData a(BlockActionContext var0) {
      return this.o().a(b, Integer.valueOf(RotationSegment.a(var0.i())));
   }

   @Override
   public IBlockData a(IBlockData var0, EnumBlockRotation var1) {
      return var0.a(b, Integer.valueOf(var1.a(var0.c(b), e)));
   }

   @Override
   public IBlockData a(IBlockData var0, EnumBlockMirror var1) {
      return var0.a(b, Integer.valueOf(var1.a(var0.c(b), e)));
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> var0) {
      var0.a(b);
   }

   public static enum Type implements BlockSkull.a {
      a,
      b,
      c,
      d,
      e,
      f,
      g;
   }

   public interface a {
   }
}
