package net.minecraft.world.level.block.piston;

import java.util.Arrays;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BlockDirectional;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EnumBlockMirror;
import net.minecraft.world.level.block.EnumBlockRotation;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockPropertyPistonType;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.block.state.properties.BlockStateEnum;
import net.minecraft.world.level.pathfinder.PathMode;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;
import net.minecraft.world.phys.shapes.VoxelShapes;

public class BlockPistonExtension extends BlockDirectional {
   public static final BlockStateEnum<BlockPropertyPistonType> b = BlockProperties.bg;
   public static final BlockStateBoolean c = BlockProperties.x;
   public static final float d = 4.0F;
   protected static final VoxelShape e = Block.a(12.0, 0.0, 0.0, 16.0, 16.0, 16.0);
   protected static final VoxelShape f = Block.a(0.0, 0.0, 0.0, 4.0, 16.0, 16.0);
   protected static final VoxelShape g = Block.a(0.0, 0.0, 12.0, 16.0, 16.0, 16.0);
   protected static final VoxelShape h = Block.a(0.0, 0.0, 0.0, 16.0, 16.0, 4.0);
   protected static final VoxelShape i = Block.a(0.0, 12.0, 0.0, 16.0, 16.0, 16.0);
   protected static final VoxelShape j = Block.a(0.0, 0.0, 0.0, 16.0, 4.0, 16.0);
   protected static final float k = 2.0F;
   protected static final float l = 6.0F;
   protected static final float m = 10.0F;
   protected static final VoxelShape n = Block.a(6.0, -4.0, 6.0, 10.0, 12.0, 10.0);
   protected static final VoxelShape E = Block.a(6.0, 4.0, 6.0, 10.0, 20.0, 10.0);
   protected static final VoxelShape F = Block.a(6.0, 6.0, -4.0, 10.0, 10.0, 12.0);
   protected static final VoxelShape G = Block.a(6.0, 6.0, 4.0, 10.0, 10.0, 20.0);
   protected static final VoxelShape H = Block.a(-4.0, 6.0, 6.0, 12.0, 10.0, 10.0);
   protected static final VoxelShape I = Block.a(4.0, 6.0, 6.0, 20.0, 10.0, 10.0);
   protected static final VoxelShape J = Block.a(6.0, 0.0, 6.0, 10.0, 12.0, 10.0);
   protected static final VoxelShape K = Block.a(6.0, 4.0, 6.0, 10.0, 16.0, 10.0);
   protected static final VoxelShape L = Block.a(6.0, 6.0, 0.0, 10.0, 10.0, 12.0);
   protected static final VoxelShape M = Block.a(6.0, 6.0, 4.0, 10.0, 10.0, 16.0);
   protected static final VoxelShape N = Block.a(0.0, 6.0, 6.0, 12.0, 10.0, 10.0);
   protected static final VoxelShape O = Block.a(4.0, 6.0, 6.0, 16.0, 10.0, 10.0);
   private static final VoxelShape[] P = a(true);
   private static final VoxelShape[] Q = a(false);

   private static VoxelShape[] a(boolean var0) {
      return Arrays.stream(EnumDirection.values()).map(var1 -> a(var1, var0)).toArray(var0x -> new VoxelShape[var0x]);
   }

   private static VoxelShape a(EnumDirection var0, boolean var1) {
      switch(var0) {
         case a:
         default:
            return VoxelShapes.a(j, var1 ? K : E);
         case b:
            return VoxelShapes.a(i, var1 ? J : n);
         case c:
            return VoxelShapes.a(h, var1 ? M : G);
         case d:
            return VoxelShapes.a(g, var1 ? L : F);
         case e:
            return VoxelShapes.a(f, var1 ? O : I);
         case f:
            return VoxelShapes.a(e, var1 ? N : H);
      }
   }

   public BlockPistonExtension(BlockBase.Info var0) {
      super(var0);
      this.k(this.D.b().a(a, EnumDirection.c).a(b, BlockPropertyPistonType.a).a(c, Boolean.valueOf(false)));
   }

   @Override
   public boolean g_(IBlockData var0) {
      return true;
   }

   @Override
   public VoxelShape a(IBlockData var0, IBlockAccess var1, BlockPosition var2, VoxelShapeCollision var3) {
      return (var0.c(c) ? P : Q)[var0.c(a).ordinal()];
   }

   private boolean a(IBlockData var0, IBlockData var1) {
      Block var2 = var0.c(b) == BlockPropertyPistonType.a ? Blocks.bx : Blocks.bq;
      return var1.a(var2) && var1.c(BlockPiston.b) && var1.c(a) == var0.c(a);
   }

   @Override
   public void a(World var0, BlockPosition var1, IBlockData var2, EntityHuman var3) {
      if (!var0.B && var3.fK().d) {
         BlockPosition var4 = var1.a(var2.c(a).g());
         if (this.a(var2, var0.a_(var4))) {
            var0.b(var4, false);
         }
      }

      super.a(var0, var1, var2, var3);
   }

   @Override
   public void a(IBlockData var0, World var1, BlockPosition var2, IBlockData var3, boolean var4) {
      if (!var0.a(var3.b())) {
         super.a(var0, var1, var2, var3, var4);
         BlockPosition var5 = var2.a(var0.c(a).g());
         if (this.a(var0, var1.a_(var5))) {
            var1.b(var5, true);
         }
      }
   }

   @Override
   public IBlockData a(IBlockData var0, EnumDirection var1, IBlockData var2, GeneratorAccess var3, BlockPosition var4, BlockPosition var5) {
      return var1.g() == var0.c(a) && !var0.a(var3, var4) ? Blocks.a.o() : super.a(var0, var1, var2, var3, var4, var5);
   }

   @Override
   public boolean a(IBlockData var0, IWorldReader var1, BlockPosition var2) {
      IBlockData var3 = var1.a_(var2.a(var0.c(a).g()));
      return this.a(var0, var3) || var3.a(Blocks.bP) && var3.c(a) == var0.c(a);
   }

   @Override
   public void a(IBlockData var0, World var1, BlockPosition var2, Block var3, BlockPosition var4, boolean var5) {
      if (var0.a((IWorldReader)var1, var2)) {
         var1.a(var2.a(var0.c(a).g()), var3, var4);
      }
   }

   @Override
   public ItemStack a(IBlockAccess var0, BlockPosition var1, IBlockData var2) {
      return new ItemStack(var2.c(b) == BlockPropertyPistonType.b ? Blocks.bq : Blocks.bx);
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
      var0.a(a, b, c);
   }

   @Override
   public boolean a(IBlockData var0, IBlockAccess var1, BlockPosition var2, PathMode var3) {
      return false;
   }
}
