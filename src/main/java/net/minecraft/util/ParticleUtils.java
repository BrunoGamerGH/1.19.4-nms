package net.minecraft.util;

import java.util.function.Supplier;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.particles.ParticleParam;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.World;
import net.minecraft.world.phys.Vec3D;

public class ParticleUtils {
   public static void a(World var0, BlockPosition var1, ParticleParam var2, IntProvider var3) {
      for(EnumDirection var7 : EnumDirection.values()) {
         a(var0, var1, var2, var3, var7, () -> a(var0.z), 0.55);
      }
   }

   public static void a(World var0, BlockPosition var1, ParticleParam var2, IntProvider var3, EnumDirection var4, Supplier<Vec3D> var5, double var6) {
      int var8 = var3.a(var0.z);

      for(int var9 = 0; var9 < var8; ++var9) {
         a(var0, var1, var4, var2, var5.get(), var6);
      }
   }

   private static Vec3D a(RandomSource var0) {
      return new Vec3D(MathHelper.a(var0, -0.5, 0.5), MathHelper.a(var0, -0.5, 0.5), MathHelper.a(var0, -0.5, 0.5));
   }

   public static void a(EnumDirection.EnumAxis var0, World var1, BlockPosition var2, double var3, ParticleParam var5, UniformInt var6) {
      Vec3D var7 = Vec3D.b(var2);
      boolean var8 = var0 == EnumDirection.EnumAxis.a;
      boolean var9 = var0 == EnumDirection.EnumAxis.b;
      boolean var10 = var0 == EnumDirection.EnumAxis.c;
      int var11 = var6.a(var1.z);

      for(int var12 = 0; var12 < var11; ++var12) {
         double var13 = var7.c + MathHelper.a(var1.z, -1.0, 1.0) * (var8 ? 0.5 : var3);
         double var15 = var7.d + MathHelper.a(var1.z, -1.0, 1.0) * (var9 ? 0.5 : var3);
         double var17 = var7.e + MathHelper.a(var1.z, -1.0, 1.0) * (var10 ? 0.5 : var3);
         double var19 = var8 ? MathHelper.a(var1.z, -1.0, 1.0) : 0.0;
         double var21 = var9 ? MathHelper.a(var1.z, -1.0, 1.0) : 0.0;
         double var23 = var10 ? MathHelper.a(var1.z, -1.0, 1.0) : 0.0;
         var1.a(var5, var13, var15, var17, var19, var21, var23);
      }
   }

   public static void a(World var0, BlockPosition var1, EnumDirection var2, ParticleParam var3, Vec3D var4, double var5) {
      Vec3D var7 = Vec3D.b(var1);
      int var8 = var2.j();
      int var9 = var2.k();
      int var10 = var2.l();
      double var11 = var7.c + (var8 == 0 ? MathHelper.a(var0.z, -0.5, 0.5) : (double)var8 * var5);
      double var13 = var7.d + (var9 == 0 ? MathHelper.a(var0.z, -0.5, 0.5) : (double)var9 * var5);
      double var15 = var7.e + (var10 == 0 ? MathHelper.a(var0.z, -0.5, 0.5) : (double)var10 * var5);
      double var17 = var8 == 0 ? var4.a() : 0.0;
      double var19 = var9 == 0 ? var4.b() : 0.0;
      double var21 = var10 == 0 ? var4.c() : 0.0;
      var0.a(var3, var11, var13, var15, var17, var19, var21);
   }

   public static void a(World var0, BlockPosition var1, RandomSource var2, ParticleParam var3) {
      double var4 = (double)var1.u() + var2.j();
      double var6 = (double)var1.v() - 0.05;
      double var8 = (double)var1.w() + var2.j();
      var0.a(var3, var4, var6, var8, 0.0, 0.0, 0.0);
   }
}
