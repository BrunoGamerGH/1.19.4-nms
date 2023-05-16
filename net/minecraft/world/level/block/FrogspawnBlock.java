package net.minecraft.world.level.block;

import com.google.common.annotations.VisibleForTesting;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.animal.frog.Tadpole;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidTypes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;

public class FrogspawnBlock extends Block {
   private static final int b = 2;
   private static final int c = 5;
   private static final int d = 3600;
   private static final int e = 12000;
   protected static final VoxelShape a = Block.a(0.0, 0.0, 0.0, 16.0, 1.5, 16.0);
   private static int f = 3600;
   private static int g = 12000;

   public FrogspawnBlock(BlockBase.Info var0) {
      super(var0);
   }

   @Override
   public VoxelShape a(IBlockData var0, IBlockAccess var1, BlockPosition var2, VoxelShapeCollision var3) {
      return a;
   }

   @Override
   public boolean a(IBlockData var0, IWorldReader var1, BlockPosition var2) {
      return a(var1, var2.d());
   }

   @Override
   public void b(IBlockData var0, World var1, BlockPosition var2, IBlockData var3, boolean var4) {
      var1.a(var2, this, a(var1.r_()));
   }

   private static int a(RandomSource var0) {
      return var0.b(f, g);
   }

   @Override
   public IBlockData a(IBlockData var0, EnumDirection var1, IBlockData var2, GeneratorAccess var3, BlockPosition var4, BlockPosition var5) {
      return !this.a(var0, var3, var4) ? Blocks.a.o() : super.a(var0, var1, var2, var3, var4, var5);
   }

   @Override
   public void a(IBlockData var0, WorldServer var1, BlockPosition var2, RandomSource var3) {
      if (!this.a(var0, var1, var2)) {
         this.a((World)var1, var2);
      } else {
         this.a(var1, var2, var3);
      }
   }

   @Override
   public void a(IBlockData var0, World var1, BlockPosition var2, Entity var3) {
      if (var3.ae().equals(EntityTypes.L)) {
         this.a(var1, var2);
      }
   }

   private static boolean a(IBlockAccess var0, BlockPosition var1) {
      Fluid var2 = var0.b_(var1);
      Fluid var3 = var0.b_(var1.c());
      return var2.a() == FluidTypes.c && var3.a() == FluidTypes.a;
   }

   private void a(WorldServer var0, BlockPosition var1, RandomSource var2) {
      this.a((World)var0, var1);
      var0.a(null, var1, SoundEffects.is, SoundCategory.e, 1.0F, 1.0F);
      this.b(var0, var1, var2);
   }

   private void a(World var0, BlockPosition var1) {
      var0.b(var1, false);
   }

   private void b(WorldServer var0, BlockPosition var1, RandomSource var2) {
      int var3 = var2.b(2, 6);

      for(int var4 = 1; var4 <= var3; ++var4) {
         Tadpole var5 = EntityTypes.aW.a((World)var0);
         if (var5 != null) {
            double var6 = (double)var1.u() + this.b(var2);
            double var8 = (double)var1.w() + this.b(var2);
            int var10 = var2.b(1, 361);
            var5.b(var6, (double)var1.v() - 0.5, var8, (float)var10, 0.0F);
            var5.fz();
            var0.b(var5);
         }
      }
   }

   private double b(RandomSource var0) {
      double var1 = (double)(Tadpole.c / 2.0F);
      return MathHelper.a(var0.j(), var1, 1.0 - var1);
   }

   @VisibleForTesting
   public static void a(int var0, int var1) {
      f = var0;
      g = var1;
   }

   @VisibleForTesting
   public static void b() {
      f = 3600;
      g = 12000;
   }
}
