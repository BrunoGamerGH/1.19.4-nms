package net.minecraft.world.entity.vehicle;

import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.tags.TagsBlock;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityPose;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.ICollisionAccess;
import net.minecraft.world.level.block.BlockTrapdoor;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.phys.AxisAlignedBB;
import net.minecraft.world.phys.Vec3D;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapes;

public class DismountUtil {
   public static int[][] a(EnumDirection var0) {
      EnumDirection var1 = var0.h();
      EnumDirection var2 = var1.g();
      EnumDirection var3 = var0.g();
      return new int[][]{
         {var1.j(), var1.l()},
         {var2.j(), var2.l()},
         {var3.j() + var1.j(), var3.l() + var1.l()},
         {var3.j() + var2.j(), var3.l() + var2.l()},
         {var0.j() + var1.j(), var0.l() + var1.l()},
         {var0.j() + var2.j(), var0.l() + var2.l()},
         {var3.j(), var3.l()},
         {var0.j(), var0.l()}
      };
   }

   public static boolean a(double var0) {
      return !Double.isInfinite(var0) && var0 < 1.0;
   }

   public static boolean a(ICollisionAccess var0, EntityLiving var1, AxisAlignedBB var2) {
      for(VoxelShape var5 : var0.d(var1, var2)) {
         if (!var5.b()) {
            return false;
         }
      }

      return var0.p_().a(var2);
   }

   public static boolean a(ICollisionAccess var0, Vec3D var1, EntityLiving var2, EntityPose var3) {
      return a(var0, var2, var2.g(var3).c(var1));
   }

   public static VoxelShape a(IBlockAccess var0, BlockPosition var1) {
      IBlockData var2 = var0.a_(var1);
      return !var2.a(TagsBlock.aM) && (!(var2.b() instanceof BlockTrapdoor) || !var2.c(BlockTrapdoor.a)) ? var2.k(var0, var1) : VoxelShapes.a();
   }

   public static double a(BlockPosition var0, int var1, Function<BlockPosition, VoxelShape> var2) {
      BlockPosition.MutableBlockPosition var3 = var0.j();
      int var4 = 0;

      while(var4 < var1) {
         VoxelShape var5 = var2.apply(var3);
         if (!var5.b()) {
            return (double)(var0.v() + var4) + var5.b(EnumDirection.EnumAxis.b);
         }

         ++var4;
         var3.c(EnumDirection.b);
      }

      return Double.POSITIVE_INFINITY;
   }

   @Nullable
   public static Vec3D a(EntityTypes<?> var0, ICollisionAccess var1, BlockPosition var2, boolean var3) {
      if (var3 && var0.a(var1.a_(var2))) {
         return null;
      } else {
         double var4 = var1.a(a((IBlockAccess)var1, var2), () -> a((IBlockAccess)var1, var2.d()));
         if (!a(var4)) {
            return null;
         } else if (var3 && var4 <= 0.0 && var0.a(var1.a_(var2.d()))) {
            return null;
         } else {
            Vec3D var6 = Vec3D.a(var2, var4);
            AxisAlignedBB var7 = var0.n().a(var6);

            for(VoxelShape var10 : var1.d(null, var7)) {
               if (!var10.b()) {
                  return null;
               }
            }

            if (var0 != EntityTypes.bt || !var1.a_(var2).a(TagsBlock.cc) && !var1.a_(var2.c()).a(TagsBlock.cc)) {
               return !var1.p_().a(var7) ? null : var6;
            } else {
               return null;
            }
         }
      }
   }
}
