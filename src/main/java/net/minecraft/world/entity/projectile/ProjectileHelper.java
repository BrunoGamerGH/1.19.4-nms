package net.minecraft.world.entity.projectile;

import java.util.Optional;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.RayTrace;
import net.minecraft.world.level.World;
import net.minecraft.world.phys.AxisAlignedBB;
import net.minecraft.world.phys.MovingObjectPosition;
import net.minecraft.world.phys.MovingObjectPositionEntity;
import net.minecraft.world.phys.Vec3D;

public final class ProjectileHelper {
   public static MovingObjectPosition a(Entity var0, Predicate<Entity> var1) {
      Vec3D var2 = var0.dj();
      World var3 = var0.H;
      Vec3D var4 = var0.de();
      Vec3D var5 = var4.e(var2);
      MovingObjectPosition var6 = var3.a(new RayTrace(var4, var5, RayTrace.BlockCollisionOption.a, RayTrace.FluidCollisionOption.a, var0));
      if (var6.c() != MovingObjectPosition.EnumMovingObjectType.a) {
         var5 = var6.e();
      }

      MovingObjectPosition var7 = a(var3, var0, var4, var5, var0.cD().b(var0.dj()).g(1.0), var1);
      if (var7 != null) {
         var6 = var7;
      }

      return var6;
   }

   @Nullable
   public static MovingObjectPositionEntity a(Entity var0, Vec3D var1, Vec3D var2, AxisAlignedBB var3, Predicate<Entity> var4, double var5) {
      World var7 = var0.H;
      double var8 = var5;
      Entity var10 = null;
      Vec3D var11 = null;

      for(Entity var13 : var7.a(var0, var3, var4)) {
         AxisAlignedBB var14 = var13.cD().g((double)var13.bB());
         Optional<Vec3D> var15 = var14.b(var1, var2);
         if (var14.d(var1)) {
            if (var8 >= 0.0) {
               var10 = var13;
               var11 = var15.orElse(var1);
               var8 = 0.0;
            }
         } else if (var15.isPresent()) {
            Vec3D var16 = var15.get();
            double var17 = var1.g(var16);
            if (var17 < var8 || var8 == 0.0) {
               if (var13.cS() == var0.cS()) {
                  if (var8 == 0.0) {
                     var10 = var13;
                     var11 = var16;
                  }
               } else {
                  var10 = var13;
                  var11 = var16;
                  var8 = var17;
               }
            }
         }
      }

      return var10 == null ? null : new MovingObjectPositionEntity(var10, var11);
   }

   @Nullable
   public static MovingObjectPositionEntity a(World var0, Entity var1, Vec3D var2, Vec3D var3, AxisAlignedBB var4, Predicate<Entity> var5) {
      return a(var0, var1, var2, var3, var4, var5, 0.3F);
   }

   @Nullable
   public static MovingObjectPositionEntity a(World var0, Entity var1, Vec3D var2, Vec3D var3, AxisAlignedBB var4, Predicate<Entity> var5, float var6) {
      double var7 = Double.MAX_VALUE;
      Entity var9 = null;

      for(Entity var11 : var0.a(var1, var4, var5)) {
         AxisAlignedBB var12 = var11.cD().g((double)var6);
         Optional<Vec3D> var13 = var12.b(var2, var3);
         if (var13.isPresent()) {
            double var14 = var2.g(var13.get());
            if (var14 < var7) {
               var9 = var11;
               var7 = var14;
            }
         }
      }

      return var9 == null ? null : new MovingObjectPositionEntity(var9);
   }

   public static void a(Entity var0, float var1) {
      Vec3D var2 = var0.dj();
      if (var2.g() != 0.0) {
         double var3 = var2.h();
         var0.f((float)(MathHelper.d(var2.e, var2.c) * 180.0F / (float)Math.PI) + 90.0F);
         var0.e((float)(MathHelper.d(var3, var2.d) * 180.0F / (float)Math.PI) - 90.0F);

         while(var0.dy() - var0.M < -180.0F) {
            var0.M -= 360.0F;
         }

         while(var0.dy() - var0.M >= 180.0F) {
            var0.M += 360.0F;
         }

         while(var0.dw() - var0.L < -180.0F) {
            var0.L -= 360.0F;
         }

         while(var0.dw() - var0.L >= 180.0F) {
            var0.L += 360.0F;
         }

         var0.e(MathHelper.i(var1, var0.M, var0.dy()));
         var0.f(MathHelper.i(var1, var0.L, var0.dw()));
      }
   }

   public static EnumHand a(EntityLiving var0, Item var1) {
      return var0.eK().a(var1) ? EnumHand.a : EnumHand.b;
   }

   public static EntityArrow a(EntityLiving var0, ItemStack var1, float var2) {
      ItemArrow var3 = (ItemArrow)(var1.c() instanceof ItemArrow ? var1.c() : Items.nD);
      EntityArrow var4 = var3.a(var0.H, var1, var0);
      var4.a(var0, var2);
      if (var1.a(Items.ur) && var4 instanceof EntityTippedArrow) {
         ((EntityTippedArrow)var4).a(var1);
      }

      return var4;
   }
}
