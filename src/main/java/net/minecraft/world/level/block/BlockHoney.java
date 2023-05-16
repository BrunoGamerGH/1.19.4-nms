package net.minecraft.world.level.block;

import net.minecraft.advancements.CriterionTriggers;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.particles.ParticleParamBlock;
import net.minecraft.core.particles.Particles;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.item.EntityTNTPrimed;
import net.minecraft.world.entity.vehicle.EntityBoat;
import net.minecraft.world.entity.vehicle.EntityMinecartAbstract;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.phys.Vec3D;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;

public class BlockHoney extends BlockHalfTransparent {
   private static final double b = 0.13;
   private static final double c = 0.08;
   private static final double d = 0.05;
   private static final int e = 20;
   protected static final VoxelShape a = Block.a(1.0, 0.0, 1.0, 15.0, 15.0, 15.0);

   public BlockHoney(BlockBase.Info var0) {
      super(var0);
   }

   private static boolean c(Entity var0) {
      return var0 instanceof EntityLiving || var0 instanceof EntityMinecartAbstract || var0 instanceof EntityTNTPrimed || var0 instanceof EntityBoat;
   }

   @Override
   public VoxelShape c(IBlockData var0, IBlockAccess var1, BlockPosition var2, VoxelShapeCollision var3) {
      return a;
   }

   @Override
   public void a(World var0, IBlockData var1, BlockPosition var2, Entity var3, float var4) {
      var3.a(SoundEffects.kP, 1.0F, 1.0F);
      if (!var0.B) {
         var0.a(var3, (byte)54);
      }

      if (var3.a(var4, 0.2F, var0.af().k())) {
         var3.a(this.aJ.g(), this.aJ.a() * 0.5F, this.aJ.b() * 0.75F);
      }
   }

   @Override
   public void a(IBlockData var0, World var1, BlockPosition var2, Entity var3) {
      if (this.a(var2, var3)) {
         this.a(var3, var2);
         this.d(var3);
         this.a(var1, var3);
      }

      super.a(var0, var1, var2, var3);
   }

   private boolean a(BlockPosition var0, Entity var1) {
      if (var1.ax()) {
         return false;
      } else if (var1.dn() > (double)var0.v() + 0.9375 - 1.0E-7) {
         return false;
      } else if (var1.dj().d >= -0.08) {
         return false;
      } else {
         double var2 = Math.abs((double)var0.u() + 0.5 - var1.dl());
         double var4 = Math.abs((double)var0.w() + 0.5 - var1.dr());
         double var6 = 0.4375 + (double)(var1.dc() / 2.0F);
         return var2 + 1.0E-7 > var6 || var4 + 1.0E-7 > var6;
      }
   }

   private void a(Entity var0, BlockPosition var1) {
      if (var0 instanceof EntityPlayer && var0.H.U() % 20L == 0L) {
         CriterionTriggers.J.a((EntityPlayer)var0, var0.H.a_(var1));
      }
   }

   private void d(Entity var0) {
      Vec3D var1 = var0.dj();
      if (var1.d < -0.13) {
         double var2 = -0.05 / var1.d;
         var0.f(new Vec3D(var1.c * var2, -0.05, var1.e * var2));
      } else {
         var0.f(new Vec3D(var1.c, -0.05, var1.e));
      }

      var0.n();
   }

   private void a(World var0, Entity var1) {
      if (c(var1)) {
         if (var0.z.a(5) == 0) {
            var1.a(SoundEffects.kP, 1.0F, 1.0F);
         }

         if (!var0.B && var0.z.a(5) == 0) {
            var0.a(var1, (byte)53);
         }
      }
   }

   public static void a(Entity var0) {
      a(var0, 5);
   }

   public static void b(Entity var0) {
      a(var0, 10);
   }

   private static void a(Entity var0, int var1) {
      if (var0.H.B) {
         IBlockData var2 = Blocks.pc.o();

         for(int var3 = 0; var3 < var1; ++var3) {
            var0.H.a(new ParticleParamBlock(Particles.c, var2), var0.dl(), var0.dn(), var0.dr(), 0.0, 0.0, 0.0);
         }
      }
   }
}
