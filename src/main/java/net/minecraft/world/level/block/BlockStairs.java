package net.minecraft.world.level.block;

import java.util.stream.IntStream;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.context.BlockActionContext;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockPropertyHalf;
import net.minecraft.world.level.block.state.properties.BlockPropertyStairsShape;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.block.state.properties.BlockStateDirection;
import net.minecraft.world.level.block.state.properties.BlockStateEnum;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidTypes;
import net.minecraft.world.level.pathfinder.PathMode;
import net.minecraft.world.phys.MovingObjectPositionBlock;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;
import net.minecraft.world.phys.shapes.VoxelShapes;

public class BlockStairs extends Block implements IBlockWaterlogged {
   public static final BlockStateDirection a = BlockFacingHorizontal.aD;
   public static final BlockStateEnum<BlockPropertyHalf> b = BlockProperties.af;
   public static final BlockStateEnum<BlockPropertyStairsShape> c = BlockProperties.bi;
   public static final BlockStateBoolean d = BlockProperties.C;
   protected static final VoxelShape e = BlockStepAbstract.d;
   protected static final VoxelShape f = BlockStepAbstract.c;
   protected static final VoxelShape g = Block.a(0.0, 0.0, 0.0, 8.0, 8.0, 8.0);
   protected static final VoxelShape h = Block.a(0.0, 0.0, 8.0, 8.0, 8.0, 16.0);
   protected static final VoxelShape i = Block.a(0.0, 8.0, 0.0, 8.0, 16.0, 8.0);
   protected static final VoxelShape j = Block.a(0.0, 8.0, 8.0, 8.0, 16.0, 16.0);
   protected static final VoxelShape k = Block.a(8.0, 0.0, 0.0, 16.0, 8.0, 8.0);
   protected static final VoxelShape l = Block.a(8.0, 0.0, 8.0, 16.0, 8.0, 16.0);
   protected static final VoxelShape m = Block.a(8.0, 8.0, 0.0, 16.0, 16.0, 8.0);
   protected static final VoxelShape n = Block.a(8.0, 8.0, 8.0, 16.0, 16.0, 16.0);
   protected static final VoxelShape[] E = a(e, g, k, h, l);
   protected static final VoxelShape[] F = a(f, i, m, j, n);
   private static final int[] G = new int[]{12, 5, 3, 10, 14, 13, 7, 11, 13, 7, 11, 14, 8, 4, 1, 2, 4, 1, 2, 8};
   private final Block H;
   private final IBlockData I;

   private static VoxelShape[] a(VoxelShape var0, VoxelShape var1, VoxelShape var2, VoxelShape var3, VoxelShape var4) {
      return IntStream.range(0, 16).mapToObj(var5 -> a(var5, var0, var1, var2, var3, var4)).toArray(var0x -> new VoxelShape[var0x]);
   }

   private static VoxelShape a(int var0, VoxelShape var1, VoxelShape var2, VoxelShape var3, VoxelShape var4, VoxelShape var5) {
      VoxelShape var6 = var1;
      if ((var0 & 1) != 0) {
         var6 = VoxelShapes.a(var1, var2);
      }

      if ((var0 & 2) != 0) {
         var6 = VoxelShapes.a(var6, var3);
      }

      if ((var0 & 4) != 0) {
         var6 = VoxelShapes.a(var6, var4);
      }

      if ((var0 & 8) != 0) {
         var6 = VoxelShapes.a(var6, var5);
      }

      return var6;
   }

   protected BlockStairs(IBlockData var0, BlockBase.Info var1) {
      super(var1);
      this.k(this.D.b().a(a, EnumDirection.c).a(b, BlockPropertyHalf.b).a(c, BlockPropertyStairsShape.a).a(d, Boolean.valueOf(false)));
      this.H = var0.b();
      this.I = var0;
   }

   @Override
   public boolean g_(IBlockData var0) {
      return true;
   }

   @Override
   public VoxelShape a(IBlockData var0, IBlockAccess var1, BlockPosition var2, VoxelShapeCollision var3) {
      return (var0.c(b) == BlockPropertyHalf.a ? E : F)[G[this.n(var0)]];
   }

   private int n(IBlockData var0) {
      return var0.c(c).ordinal() * 4 + var0.c(a).e();
   }

   @Override
   public void a(IBlockData var0, World var1, BlockPosition var2, RandomSource var3) {
      this.H.a(var0, var1, var2, var3);
   }

   @Override
   public void a(IBlockData var0, World var1, BlockPosition var2, EntityHuman var3) {
      this.I.a(var1, var2, var3);
   }

   @Override
   public void a(GeneratorAccess var0, BlockPosition var1, IBlockData var2) {
      this.H.a(var0, var1, var2);
   }

   @Override
   public float e() {
      return this.H.e();
   }

   @Override
   public void b(IBlockData var0, World var1, BlockPosition var2, IBlockData var3, boolean var4) {
      if (!var0.a(var0.b())) {
         var1.a(this.I, var2, Blocks.a, var2, false);
         this.H.b(this.I, var1, var2, var3, false);
      }
   }

   @Override
   public void a(IBlockData var0, World var1, BlockPosition var2, IBlockData var3, boolean var4) {
      if (!var0.a(var3.b())) {
         this.I.b(var1, var2, var3, var4);
      }
   }

   @Override
   public void a(World var0, BlockPosition var1, IBlockData var2, Entity var3) {
      this.H.a(var0, var1, var2, var3);
   }

   @Override
   public boolean e_(IBlockData var0) {
      return this.H.e_(var0);
   }

   @Override
   public void b(IBlockData var0, WorldServer var1, BlockPosition var2, RandomSource var3) {
      this.H.b(var0, var1, var2, var3);
   }

   @Override
   public void a(IBlockData var0, WorldServer var1, BlockPosition var2, RandomSource var3) {
      this.H.a(var0, var1, var2, var3);
   }

   @Override
   public EnumInteractionResult a(IBlockData var0, World var1, BlockPosition var2, EntityHuman var3, EnumHand var4, MovingObjectPositionBlock var5) {
      return this.I.a(var1, var3, var4, var5);
   }

   @Override
   public void a(World var0, BlockPosition var1, Explosion var2) {
      this.H.a(var0, var1, var2);
   }

   @Override
   public IBlockData a(BlockActionContext var0) {
      EnumDirection var1 = var0.k();
      BlockPosition var2 = var0.a();
      Fluid var3 = var0.q().b_(var2);
      IBlockData var4 = this.o()
         .a(a, var0.g())
         .a(b, var1 != EnumDirection.a && (var1 == EnumDirection.b || !(var0.l().d - (double)var2.v() > 0.5)) ? BlockPropertyHalf.b : BlockPropertyHalf.a)
         .a(d, Boolean.valueOf(var3.a() == FluidTypes.c));
      return var4.a(c, i(var4, var0.q(), var2));
   }

   @Override
   public IBlockData a(IBlockData var0, EnumDirection var1, IBlockData var2, GeneratorAccess var3, BlockPosition var4, BlockPosition var5) {
      if (var0.c(d)) {
         var3.a(var4, FluidTypes.c, FluidTypes.c.a(var3));
      }

      return var1.o().d() ? var0.a(c, i(var0, var3, var4)) : super.a(var0, var1, var2, var3, var4, var5);
   }

   private static BlockPropertyStairsShape i(IBlockData var0, IBlockAccess var1, BlockPosition var2) {
      EnumDirection var3 = var0.c(a);
      IBlockData var4 = var1.a_(var2.a(var3));
      if (h(var4) && var0.c(b) == var4.c(b)) {
         EnumDirection var5 = var4.c(a);
         if (var5.o() != var0.c(a).o() && c(var0, var1, var2, var5.g())) {
            if (var5 == var3.i()) {
               return BlockPropertyStairsShape.d;
            }

            return BlockPropertyStairsShape.e;
         }
      }

      IBlockData var5 = var1.a_(var2.a(var3.g()));
      if (h(var5) && var0.c(b) == var5.c(b)) {
         EnumDirection var6 = var5.c(a);
         if (var6.o() != var0.c(a).o() && c(var0, var1, var2, var6)) {
            if (var6 == var3.i()) {
               return BlockPropertyStairsShape.b;
            }

            return BlockPropertyStairsShape.c;
         }
      }

      return BlockPropertyStairsShape.a;
   }

   private static boolean c(IBlockData var0, IBlockAccess var1, BlockPosition var2, EnumDirection var3) {
      IBlockData var4 = var1.a_(var2.a(var3));
      return !h(var4) || var4.c(a) != var0.c(a) || var4.c(b) != var0.c(b);
   }

   public static boolean h(IBlockData var0) {
      return var0.b() instanceof BlockStairs;
   }

   @Override
   public IBlockData a(IBlockData var0, EnumBlockRotation var1) {
      return var0.a(a, var1.a(var0.c(a)));
   }

   @Override
   public IBlockData a(IBlockData var0, EnumBlockMirror var1) {
      EnumDirection var2 = var0.c(a);
      BlockPropertyStairsShape var3 = var0.c(c);
      switch(var1) {
         case b:
            if (var2.o() == EnumDirection.EnumAxis.c) {
               switch(var3) {
                  case b:
                     return var0.a(EnumBlockRotation.c).a(c, BlockPropertyStairsShape.c);
                  case c:
                     return var0.a(EnumBlockRotation.c).a(c, BlockPropertyStairsShape.b);
                  case d:
                     return var0.a(EnumBlockRotation.c).a(c, BlockPropertyStairsShape.e);
                  case e:
                     return var0.a(EnumBlockRotation.c).a(c, BlockPropertyStairsShape.d);
                  default:
                     return var0.a(EnumBlockRotation.c);
               }
            }
            break;
         case c:
            if (var2.o() == EnumDirection.EnumAxis.a) {
               switch(var3) {
                  case b:
                     return var0.a(EnumBlockRotation.c).a(c, BlockPropertyStairsShape.b);
                  case c:
                     return var0.a(EnumBlockRotation.c).a(c, BlockPropertyStairsShape.c);
                  case d:
                     return var0.a(EnumBlockRotation.c).a(c, BlockPropertyStairsShape.e);
                  case e:
                     return var0.a(EnumBlockRotation.c).a(c, BlockPropertyStairsShape.d);
                  case a:
                     return var0.a(EnumBlockRotation.c);
               }
            }
      }

      return super.a(var0, var1);
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> var0) {
      var0.a(a, b, c, d);
   }

   @Override
   public Fluid c_(IBlockData var0) {
      return var0.c(d) ? FluidTypes.c.a(false) : super.c_(var0);
   }

   @Override
   public boolean a(IBlockData var0, IBlockAccess var1, BlockPosition var2, PathMode var3) {
      return false;
   }
}
