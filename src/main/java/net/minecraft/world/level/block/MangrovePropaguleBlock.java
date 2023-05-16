package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockActionContext;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.grower.MangroveTreeGrower;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.block.state.properties.BlockStateInteger;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidTypes;
import net.minecraft.world.phys.Vec3D;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;

public class MangrovePropaguleBlock extends BlockSapling implements IBlockWaterlogged {
   public static final BlockStateInteger a = BlockProperties.at;
   public static final int b = 4;
   private static final VoxelShape[] g = new VoxelShape[]{
      Block.a(7.0, 13.0, 7.0, 9.0, 16.0, 9.0),
      Block.a(7.0, 10.0, 7.0, 9.0, 16.0, 9.0),
      Block.a(7.0, 7.0, 7.0, 9.0, 16.0, 9.0),
      Block.a(7.0, 3.0, 7.0, 9.0, 16.0, 9.0),
      Block.a(7.0, 0.0, 7.0, 9.0, 16.0, 9.0)
   };
   private static final BlockStateBoolean h = BlockProperties.C;
   public static final BlockStateBoolean c = BlockProperties.j;
   private static final float i = 0.85F;

   public MangrovePropaguleBlock(BlockBase.Info var0) {
      super(new MangroveTreeGrower(0.85F), var0);
      this.k(this.D.b().a(d, Integer.valueOf(0)).a(a, Integer.valueOf(0)).a(h, Boolean.valueOf(false)).a(c, Boolean.valueOf(false)));
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> var0) {
      var0.a(d).a(a).a(h).a(c);
   }

   @Override
   protected boolean d(IBlockData var0, IBlockAccess var1, BlockPosition var2) {
      return super.d(var0, var1, var2) || var0.a(Blocks.dQ);
   }

   @Nullable
   @Override
   public IBlockData a(BlockActionContext var0) {
      Fluid var1 = var0.q().b_(var0.a());
      boolean var2 = var1.a() == FluidTypes.c;
      return super.a(var0).a(h, Boolean.valueOf(var2)).a(a, Integer.valueOf(4));
   }

   @Override
   public VoxelShape a(IBlockData var0, IBlockAccess var1, BlockPosition var2, VoxelShapeCollision var3) {
      Vec3D var4 = var0.n(var1, var2);
      VoxelShape var5;
      if (!var0.c(c)) {
         var5 = g[4];
      } else {
         var5 = g[var0.c(a)];
      }

      return var5.a(var4.c, var4.d, var4.e);
   }

   @Override
   public boolean a(IBlockData var0, IWorldReader var1, BlockPosition var2) {
      return h(var0) ? var1.a_(var2.c()).a(Blocks.aK) : super.a(var0, var1, var2);
   }

   @Override
   public IBlockData a(IBlockData var0, EnumDirection var1, IBlockData var2, GeneratorAccess var3, BlockPosition var4, BlockPosition var5) {
      if (var0.c(h)) {
         var3.a(var4, FluidTypes.c, FluidTypes.c.a(var3));
      }

      return var1 == EnumDirection.b && !var0.a(var3, var4) ? Blocks.a.o() : super.a(var0, var1, var2, var3, var4, var5);
   }

   @Override
   public Fluid c_(IBlockData var0) {
      return var0.c(h) ? FluidTypes.c.a(false) : super.c_(var0);
   }

   @Override
   public void b(IBlockData var0, WorldServer var1, BlockPosition var2, RandomSource var3) {
      if (!h(var0)) {
         if (var3.a(7) == 0) {
            this.a(var1, var2, var0, var3);
         }
      } else {
         if (!n(var0)) {
            var1.a(var2, var0.a(a), 2);
         }
      }
   }

   @Override
   public boolean a(IWorldReader var0, BlockPosition var1, IBlockData var2, boolean var3) {
      return !h(var2) || !n(var2);
   }

   @Override
   public boolean a(World var0, RandomSource var1, BlockPosition var2, IBlockData var3) {
      return h(var3) ? !n(var3) : super.a(var0, var1, var2, var3);
   }

   @Override
   public void a(WorldServer var0, RandomSource var1, BlockPosition var2, IBlockData var3) {
      if (h(var3) && !n(var3)) {
         var0.a(var2, var3.a(a), 2);
      } else {
         super.a(var0, var1, var2, var3);
      }
   }

   private static boolean h(IBlockData var0) {
      return var0.c(c);
   }

   private static boolean n(IBlockData var0) {
      return var0.c(a) == 4;
   }

   public static IBlockData c() {
      return b(0);
   }

   public static IBlockData b(int var0) {
      return Blocks.E.o().a(c, Boolean.valueOf(true)).a(a, Integer.valueOf(var0));
   }
}
