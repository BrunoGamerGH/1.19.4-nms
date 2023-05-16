package net.minecraft.world.level.block;

import com.google.common.base.Predicates;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.world.item.context.BlockActionContext;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.pattern.ShapeDetector;
import net.minecraft.world.level.block.state.pattern.ShapeDetectorBlock;
import net.minecraft.world.level.block.state.pattern.ShapeDetectorBuilder;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.block.state.properties.BlockStateDirection;
import net.minecraft.world.level.pathfinder.PathMode;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;
import net.minecraft.world.phys.shapes.VoxelShapes;

public class BlockEnderPortalFrame extends Block {
   public static final BlockStateDirection a = BlockFacingHorizontal.aD;
   public static final BlockStateBoolean b = BlockProperties.h;
   protected static final VoxelShape c = Block.a(0.0, 0.0, 0.0, 16.0, 13.0, 16.0);
   protected static final VoxelShape d = Block.a(4.0, 13.0, 4.0, 12.0, 16.0, 12.0);
   protected static final VoxelShape e = VoxelShapes.a(c, d);
   private static ShapeDetector f;

   public BlockEnderPortalFrame(BlockBase.Info var0) {
      super(var0);
      this.k(this.D.b().a(a, EnumDirection.c).a(b, Boolean.valueOf(false)));
   }

   @Override
   public boolean g_(IBlockData var0) {
      return true;
   }

   @Override
   public VoxelShape a(IBlockData var0, IBlockAccess var1, BlockPosition var2, VoxelShapeCollision var3) {
      return var0.c(b) ? e : c;
   }

   @Override
   public IBlockData a(BlockActionContext var0) {
      return this.o().a(a, var0.g().g()).a(b, Boolean.valueOf(false));
   }

   @Override
   public boolean d_(IBlockData var0) {
      return true;
   }

   @Override
   public int a(IBlockData var0, World var1, BlockPosition var2) {
      return var0.c(b) ? 15 : 0;
   }

   @Override
   public IBlockData a(IBlockData var0, EnumBlockRotation var1) {
      return var0.a(a, var1.a(var0.c(a)));
   }

   @Override
   public IBlockData a(IBlockData var0, EnumBlockMirror var1) {
      return var0.a(var1.a(var0.c(a)));
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> var0) {
      var0.a(a, b);
   }

   public static ShapeDetector b() {
      if (f == null) {
         f = ShapeDetectorBuilder.a()
            .a("?vvv?", ">???<", ">???<", ">???<", "?^^^?")
            .a('?', ShapeDetectorBlock.a(BlockStatePredicate.a))
            .a('^', ShapeDetectorBlock.a(BlockStatePredicate.a(Blocks.fx).a(b, Predicates.equalTo(true)).a(a, Predicates.equalTo(EnumDirection.d))))
            .a('>', ShapeDetectorBlock.a(BlockStatePredicate.a(Blocks.fx).a(b, Predicates.equalTo(true)).a(a, Predicates.equalTo(EnumDirection.e))))
            .a('v', ShapeDetectorBlock.a(BlockStatePredicate.a(Blocks.fx).a(b, Predicates.equalTo(true)).a(a, Predicates.equalTo(EnumDirection.c))))
            .a('<', ShapeDetectorBlock.a(BlockStatePredicate.a(Blocks.fx).a(b, Predicates.equalTo(true)).a(a, Predicates.equalTo(EnumDirection.f))))
            .b();
      }

      return f;
   }

   @Override
   public boolean a(IBlockData var0, IBlockAccess var1, BlockPosition var2, PathMode var3) {
      return false;
   }
}
