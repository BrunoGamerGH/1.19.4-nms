package net.minecraft.world.level.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import java.util.Map;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.tags.TagsBlock;
import net.minecraft.world.item.context.BlockActionContext;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockPropertyWallHeight;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.block.state.properties.BlockStateEnum;
import net.minecraft.world.level.block.state.properties.IBlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidTypes;
import net.minecraft.world.level.pathfinder.PathMode;
import net.minecraft.world.phys.shapes.OperatorBoolean;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;
import net.minecraft.world.phys.shapes.VoxelShapes;

public class BlockCobbleWall extends Block implements IBlockWaterlogged {
   public static final BlockStateBoolean a = BlockProperties.J;
   public static final BlockStateEnum<BlockPropertyWallHeight> b = BlockProperties.W;
   public static final BlockStateEnum<BlockPropertyWallHeight> c = BlockProperties.X;
   public static final BlockStateEnum<BlockPropertyWallHeight> d = BlockProperties.Y;
   public static final BlockStateEnum<BlockPropertyWallHeight> e = BlockProperties.Z;
   public static final BlockStateBoolean f = BlockProperties.C;
   private final Map<IBlockData, VoxelShape> g;
   private final Map<IBlockData, VoxelShape> h;
   private static final int i = 3;
   private static final int j = 14;
   private static final int k = 4;
   private static final int l = 1;
   private static final int m = 7;
   private static final int n = 9;
   private static final VoxelShape E = Block.a(7.0, 0.0, 7.0, 9.0, 16.0, 9.0);
   private static final VoxelShape F = Block.a(7.0, 0.0, 0.0, 9.0, 16.0, 9.0);
   private static final VoxelShape G = Block.a(7.0, 0.0, 7.0, 9.0, 16.0, 16.0);
   private static final VoxelShape H = Block.a(0.0, 0.0, 7.0, 9.0, 16.0, 9.0);
   private static final VoxelShape I = Block.a(7.0, 0.0, 7.0, 16.0, 16.0, 9.0);

   public BlockCobbleWall(BlockBase.Info var0) {
      super(var0);
      this.k(
         this.D
            .b()
            .a(a, Boolean.valueOf(true))
            .a(c, BlockPropertyWallHeight.a)
            .a(b, BlockPropertyWallHeight.a)
            .a(d, BlockPropertyWallHeight.a)
            .a(e, BlockPropertyWallHeight.a)
            .a(f, Boolean.valueOf(false))
      );
      this.g = this.a(4.0F, 3.0F, 16.0F, 0.0F, 14.0F, 16.0F);
      this.h = this.a(4.0F, 3.0F, 24.0F, 0.0F, 24.0F, 24.0F);
   }

   private static VoxelShape a(VoxelShape var0, BlockPropertyWallHeight var1, VoxelShape var2, VoxelShape var3) {
      if (var1 == BlockPropertyWallHeight.c) {
         return VoxelShapes.a(var0, var3);
      } else {
         return var1 == BlockPropertyWallHeight.b ? VoxelShapes.a(var0, var2) : var0;
      }
   }

   private Map<IBlockData, VoxelShape> a(float var0, float var1, float var2, float var3, float var4, float var5) {
      float var6 = 8.0F - var0;
      float var7 = 8.0F + var0;
      float var8 = 8.0F - var1;
      float var9 = 8.0F + var1;
      VoxelShape var10 = Block.a((double)var6, 0.0, (double)var6, (double)var7, (double)var2, (double)var7);
      VoxelShape var11 = Block.a((double)var8, (double)var3, 0.0, (double)var9, (double)var4, (double)var9);
      VoxelShape var12 = Block.a((double)var8, (double)var3, (double)var8, (double)var9, (double)var4, 16.0);
      VoxelShape var13 = Block.a(0.0, (double)var3, (double)var8, (double)var9, (double)var4, (double)var9);
      VoxelShape var14 = Block.a((double)var8, (double)var3, (double)var8, 16.0, (double)var4, (double)var9);
      VoxelShape var15 = Block.a((double)var8, (double)var3, 0.0, (double)var9, (double)var5, (double)var9);
      VoxelShape var16 = Block.a((double)var8, (double)var3, (double)var8, (double)var9, (double)var5, 16.0);
      VoxelShape var17 = Block.a(0.0, (double)var3, (double)var8, (double)var9, (double)var5, (double)var9);
      VoxelShape var18 = Block.a((double)var8, (double)var3, (double)var8, 16.0, (double)var5, (double)var9);
      Builder<IBlockData, VoxelShape> var19 = ImmutableMap.builder();

      for(Boolean var21 : a.a()) {
         for(BlockPropertyWallHeight var23 : b.a()) {
            for(BlockPropertyWallHeight var25 : c.a()) {
               for(BlockPropertyWallHeight var27 : e.a()) {
                  for(BlockPropertyWallHeight var29 : d.a()) {
                     VoxelShape var30 = VoxelShapes.a();
                     var30 = a(var30, var23, var14, var18);
                     var30 = a(var30, var27, var13, var17);
                     var30 = a(var30, var25, var11, var15);
                     var30 = a(var30, var29, var12, var16);
                     if (var21) {
                        var30 = VoxelShapes.a(var30, var10);
                     }

                     IBlockData var31 = this.o().a(a, var21).a(b, var23).a(e, var27).a(c, var25).a(d, var29);
                     var19.put(var31.a(f, Boolean.valueOf(false)), var30);
                     var19.put(var31.a(f, Boolean.valueOf(true)), var30);
                  }
               }
            }
         }
      }

      return var19.build();
   }

   @Override
   public VoxelShape a(IBlockData var0, IBlockAccess var1, BlockPosition var2, VoxelShapeCollision var3) {
      return this.g.get(var0);
   }

   @Override
   public VoxelShape c(IBlockData var0, IBlockAccess var1, BlockPosition var2, VoxelShapeCollision var3) {
      return this.h.get(var0);
   }

   @Override
   public boolean a(IBlockData var0, IBlockAccess var1, BlockPosition var2, PathMode var3) {
      return false;
   }

   private boolean a(IBlockData var0, boolean var1, EnumDirection var2) {
      Block var3 = var0.b();
      boolean var4 = var3 instanceof BlockFenceGate && BlockFenceGate.a(var0, var2);
      return var0.a(TagsBlock.K) || !j(var0) && var1 || var3 instanceof BlockIronBars || var4;
   }

   @Override
   public IBlockData a(BlockActionContext var0) {
      IWorldReader var1 = var0.q();
      BlockPosition var2 = var0.a();
      Fluid var3 = var0.q().b_(var0.a());
      BlockPosition var4 = var2.e();
      BlockPosition var5 = var2.h();
      BlockPosition var6 = var2.f();
      BlockPosition var7 = var2.g();
      BlockPosition var8 = var2.c();
      IBlockData var9 = var1.a_(var4);
      IBlockData var10 = var1.a_(var5);
      IBlockData var11 = var1.a_(var6);
      IBlockData var12 = var1.a_(var7);
      IBlockData var13 = var1.a_(var8);
      boolean var14 = this.a(var9, var9.d(var1, var4, EnumDirection.d), EnumDirection.d);
      boolean var15 = this.a(var10, var10.d(var1, var5, EnumDirection.e), EnumDirection.e);
      boolean var16 = this.a(var11, var11.d(var1, var6, EnumDirection.c), EnumDirection.c);
      boolean var17 = this.a(var12, var12.d(var1, var7, EnumDirection.f), EnumDirection.f);
      IBlockData var18 = this.o().a(f, Boolean.valueOf(var3.a() == FluidTypes.c));
      return this.a(var1, var18, var8, var13, var14, var15, var16, var17);
   }

   @Override
   public IBlockData a(IBlockData var0, EnumDirection var1, IBlockData var2, GeneratorAccess var3, BlockPosition var4, BlockPosition var5) {
      if (var0.c(f)) {
         var3.a(var4, FluidTypes.c, FluidTypes.c.a(var3));
      }

      if (var1 == EnumDirection.a) {
         return super.a(var0, var1, var2, var3, var4, var5);
      } else {
         return var1 == EnumDirection.b ? this.a(var3, var0, var5, var2) : this.a(var3, var4, var0, var5, var2, var1);
      }
   }

   private static boolean a(IBlockData var0, IBlockState<BlockPropertyWallHeight> var1) {
      return var0.c(var1) != BlockPropertyWallHeight.a;
   }

   private static boolean a(VoxelShape var0, VoxelShape var1) {
      return !VoxelShapes.c(var1, var0, OperatorBoolean.e);
   }

   private IBlockData a(IWorldReader var0, IBlockData var1, BlockPosition var2, IBlockData var3) {
      boolean var4 = a(var1, c);
      boolean var5 = a(var1, b);
      boolean var6 = a(var1, d);
      boolean var7 = a(var1, e);
      return this.a(var0, var1, var2, var3, var4, var5, var6, var7);
   }

   private IBlockData a(IWorldReader var0, BlockPosition var1, IBlockData var2, BlockPosition var3, IBlockData var4, EnumDirection var5) {
      EnumDirection var6 = var5.g();
      boolean var7 = var5 == EnumDirection.c ? this.a(var4, var4.d(var0, var3, var6), var6) : a(var2, c);
      boolean var8 = var5 == EnumDirection.f ? this.a(var4, var4.d(var0, var3, var6), var6) : a(var2, b);
      boolean var9 = var5 == EnumDirection.d ? this.a(var4, var4.d(var0, var3, var6), var6) : a(var2, d);
      boolean var10 = var5 == EnumDirection.e ? this.a(var4, var4.d(var0, var3, var6), var6) : a(var2, e);
      BlockPosition var11 = var1.c();
      IBlockData var12 = var0.a_(var11);
      return this.a(var0, var2, var11, var12, var7, var8, var9, var10);
   }

   private IBlockData a(IWorldReader var0, IBlockData var1, BlockPosition var2, IBlockData var3, boolean var4, boolean var5, boolean var6, boolean var7) {
      VoxelShape var8 = var3.k(var0, var2).a(EnumDirection.a);
      IBlockData var9 = this.a(var1, var4, var5, var6, var7, var8);
      return var9.a(a, Boolean.valueOf(this.a(var9, var3, var8)));
   }

   private boolean a(IBlockData var0, IBlockData var1, VoxelShape var2) {
      boolean var3 = var1.b() instanceof BlockCobbleWall && var1.c(a);
      if (var3) {
         return true;
      } else {
         BlockPropertyWallHeight var4 = var0.c(c);
         BlockPropertyWallHeight var5 = var0.c(d);
         BlockPropertyWallHeight var6 = var0.c(b);
         BlockPropertyWallHeight var7 = var0.c(e);
         boolean var8 = var5 == BlockPropertyWallHeight.a;
         boolean var9 = var7 == BlockPropertyWallHeight.a;
         boolean var10 = var6 == BlockPropertyWallHeight.a;
         boolean var11 = var4 == BlockPropertyWallHeight.a;
         boolean var12 = var11 && var8 && var9 && var10 || var11 != var8 || var9 != var10;
         if (var12) {
            return true;
         } else {
            boolean var13 = var4 == BlockPropertyWallHeight.c && var5 == BlockPropertyWallHeight.c
               || var6 == BlockPropertyWallHeight.c && var7 == BlockPropertyWallHeight.c;
            if (var13) {
               return false;
            } else {
               return var1.a(TagsBlock.aL) || a(var2, E);
            }
         }
      }
   }

   private IBlockData a(IBlockData var0, boolean var1, boolean var2, boolean var3, boolean var4, VoxelShape var5) {
      return var0.a(c, this.a(var1, var5, F)).a(b, this.a(var2, var5, I)).a(d, this.a(var3, var5, G)).a(e, this.a(var4, var5, H));
   }

   private BlockPropertyWallHeight a(boolean var0, VoxelShape var1, VoxelShape var2) {
      if (var0) {
         return a(var1, var2) ? BlockPropertyWallHeight.c : BlockPropertyWallHeight.b;
      } else {
         return BlockPropertyWallHeight.a;
      }
   }

   @Override
   public Fluid c_(IBlockData var0) {
      return var0.c(f) ? FluidTypes.c.a(false) : super.c_(var0);
   }

   @Override
   public boolean c(IBlockData var0, IBlockAccess var1, BlockPosition var2) {
      return !var0.c(f);
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> var0) {
      var0.a(a, c, b, e, d, f);
   }

   @Override
   public IBlockData a(IBlockData var0, EnumBlockRotation var1) {
      switch(var1) {
         case c:
            return var0.a(c, var0.c(d)).a(b, var0.c(e)).a(d, var0.c(c)).a(e, var0.c(b));
         case d:
            return var0.a(c, var0.c(b)).a(b, var0.c(d)).a(d, var0.c(e)).a(e, var0.c(c));
         case b:
            return var0.a(c, var0.c(e)).a(b, var0.c(c)).a(d, var0.c(b)).a(e, var0.c(d));
         default:
            return var0;
      }
   }

   @Override
   public IBlockData a(IBlockData var0, EnumBlockMirror var1) {
      switch(var1) {
         case b:
            return var0.a(c, var0.c(d)).a(d, var0.c(c));
         case c:
            return var0.a(b, var0.c(e)).a(e, var0.c(b));
         default:
            return super.a(var0, var1);
      }
   }
}
