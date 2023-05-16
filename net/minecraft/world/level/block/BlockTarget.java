package net.minecraft.world.level.block;

import net.minecraft.advancements.CriterionTriggers;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.stats.StatisticList;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.EntityArrow;
import net.minecraft.world.entity.projectile.IProjectile;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockStateInteger;
import net.minecraft.world.phys.MovingObjectPositionBlock;
import net.minecraft.world.phys.Vec3D;

public class BlockTarget extends Block {
   private static final BlockStateInteger a = BlockProperties.aT;
   private static final int b = 20;
   private static final int c = 8;

   public BlockTarget(BlockBase.Info var0) {
      super(var0);
      this.k(this.D.b().a(a, Integer.valueOf(0)));
   }

   @Override
   public void a(World var0, IBlockData var1, MovingObjectPositionBlock var2, IProjectile var3) {
      int var4 = a((GeneratorAccess)var0, var1, var2, (Entity)var3);
      Entity var5 = var3.v();
      if (var5 instanceof EntityPlayer var6) {
         var6.a(StatisticList.aE);
         CriterionTriggers.L.a(var6, var3, var2.e(), var4);
      }
   }

   private static int a(GeneratorAccess var0, IBlockData var1, MovingObjectPositionBlock var2, Entity var3) {
      int var4 = a(var2, var2.e());
      int var5 = var3 instanceof EntityArrow ? 20 : 8;
      if (!var0.K().a(var2.a(), var1.b())) {
         a(var0, var1, var4, var2.a(), var5);
      }

      return var4;
   }

   private static int a(MovingObjectPositionBlock var0, Vec3D var1) {
      EnumDirection var2 = var0.b();
      double var3 = Math.abs(MathHelper.e(var1.c) - 0.5);
      double var5 = Math.abs(MathHelper.e(var1.d) - 0.5);
      double var7 = Math.abs(MathHelper.e(var1.e) - 0.5);
      EnumDirection.EnumAxis var11 = var2.o();
      double var9;
      if (var11 == EnumDirection.EnumAxis.b) {
         var9 = Math.max(var3, var7);
      } else if (var11 == EnumDirection.EnumAxis.c) {
         var9 = Math.max(var3, var5);
      } else {
         var9 = Math.max(var5, var7);
      }

      return Math.max(1, MathHelper.c(15.0 * MathHelper.a((0.5 - var9) / 0.5, 0.0, 1.0)));
   }

   private static void a(GeneratorAccess var0, IBlockData var1, int var2, BlockPosition var3, int var4) {
      var0.a(var3, var1.a(a, Integer.valueOf(var2)), 3);
      var0.a(var3, var1.b(), var4);
   }

   @Override
   public void a(IBlockData var0, WorldServer var1, BlockPosition var2, RandomSource var3) {
      if (var0.c(a) != 0) {
         var1.a(var2, var0.a(a, Integer.valueOf(0)), 3);
      }
   }

   @Override
   public int a(IBlockData var0, IBlockAccess var1, BlockPosition var2, EnumDirection var3) {
      return var0.c(a);
   }

   @Override
   public boolean f_(IBlockData var0) {
      return true;
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> var0) {
      var0.a(a);
   }

   @Override
   public void b(IBlockData var0, World var1, BlockPosition var2, IBlockData var3, boolean var4) {
      if (!var1.k_() && !var0.a(var3.b())) {
         if (var0.c(a) > 0 && !var1.K().a(var2, this)) {
            var1.a(var2, var0.a(a, Integer.valueOf(0)), 18);
         }
      }
   }
}
