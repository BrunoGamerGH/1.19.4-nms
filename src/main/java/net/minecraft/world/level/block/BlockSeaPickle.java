package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.server.level.WorldServer;
import net.minecraft.tags.TagsBlock;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockActionContext;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.block.state.properties.BlockStateInteger;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidTypes;
import net.minecraft.world.level.pathfinder.PathMode;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;

public class BlockSeaPickle extends BlockPlant implements IBlockFragilePlantElement, IBlockWaterlogged {
   public static final int a = 4;
   public static final BlockStateInteger b = BlockProperties.aS;
   public static final BlockStateBoolean c = BlockProperties.C;
   protected static final VoxelShape d = Block.a(6.0, 0.0, 6.0, 10.0, 6.0, 10.0);
   protected static final VoxelShape e = Block.a(3.0, 0.0, 3.0, 13.0, 6.0, 13.0);
   protected static final VoxelShape f = Block.a(2.0, 0.0, 2.0, 14.0, 6.0, 14.0);
   protected static final VoxelShape g = Block.a(2.0, 0.0, 2.0, 14.0, 7.0, 14.0);

   protected BlockSeaPickle(BlockBase.Info var0) {
      super(var0);
      this.k(this.D.b().a(b, Integer.valueOf(1)).a(c, Boolean.valueOf(true)));
   }

   @Nullable
   @Override
   public IBlockData a(BlockActionContext var0) {
      IBlockData var1 = var0.q().a_(var0.a());
      if (var1.a(this)) {
         return var1.a(b, Integer.valueOf(Math.min(4, var1.c(b) + 1)));
      } else {
         Fluid var2 = var0.q().b_(var0.a());
         boolean var3 = var2.a() == FluidTypes.c;
         return super.a(var0).a(c, Boolean.valueOf(var3));
      }
   }

   public static boolean h(IBlockData var0) {
      return !var0.c(c);
   }

   @Override
   protected boolean d(IBlockData var0, IBlockAccess var1, BlockPosition var2) {
      return !var0.k(var1, var2).a(EnumDirection.b).b() || var0.d(var1, var2, EnumDirection.b);
   }

   @Override
   public boolean a(IBlockData var0, IWorldReader var1, BlockPosition var2) {
      BlockPosition var3 = var2.d();
      return this.d(var1.a_(var3), var1, var3);
   }

   @Override
   public IBlockData a(IBlockData var0, EnumDirection var1, IBlockData var2, GeneratorAccess var3, BlockPosition var4, BlockPosition var5) {
      if (!var0.a(var3, var4)) {
         return Blocks.a.o();
      } else {
         if (var0.c(c)) {
            var3.a(var4, FluidTypes.c, FluidTypes.c.a(var3));
         }

         return super.a(var0, var1, var2, var3, var4, var5);
      }
   }

   @Override
   public boolean a(IBlockData var0, BlockActionContext var1) {
      return !var1.h() && var1.n().a(this.k()) && var0.c(b) < 4 ? true : super.a(var0, var1);
   }

   @Override
   public VoxelShape a(IBlockData var0, IBlockAccess var1, BlockPosition var2, VoxelShapeCollision var3) {
      switch(var0.c(b)) {
         case 1:
         default:
            return d;
         case 2:
            return e;
         case 3:
            return f;
         case 4:
            return g;
      }
   }

   @Override
   public Fluid c_(IBlockData var0) {
      return var0.c(c) ? FluidTypes.c.a(false) : super.c_(var0);
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> var0) {
      var0.a(b, c);
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
      if (!h(var3) && var0.a_(var2.d()).a(TagsBlock.an)) {
         int var4 = 5;
         int var5 = 1;
         int var6 = 2;
         int var7 = 0;
         int var8 = var2.u() - 2;
         int var9 = 0;

         for(int var10 = 0; var10 < 5; ++var10) {
            for(int var11 = 0; var11 < var5; ++var11) {
               int var12 = 2 + var2.v() - 1;

               for(int var13 = var12 - 2; var13 < var12; ++var13) {
                  BlockPosition var14 = new BlockPosition(var8 + var10, var13, var2.w() - var9 + var11);
                  if (var14 != var2 && var1.a(6) == 0 && var0.a_(var14).a(Blocks.G)) {
                     IBlockData var15 = var0.a_(var14.d());
                     if (var15.a(TagsBlock.an)) {
                        var0.a(var14, Blocks.mR.o().a(b, Integer.valueOf(var1.a(4) + 1)), 3);
                     }
                  }
               }
            }

            if (var7 < 2) {
               var5 += 2;
               ++var9;
            } else {
               var5 -= 2;
               --var9;
            }

            ++var7;
         }

         var0.a(var2, var3.a(b, Integer.valueOf(4)), 2);
      }
   }

   @Override
   public boolean a(IBlockData var0, IBlockAccess var1, BlockPosition var2, PathMode var3) {
      return false;
   }
}
