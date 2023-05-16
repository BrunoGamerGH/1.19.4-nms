package net.minecraft.world.phys;

import com.mojang.serialization.Codec;
import java.util.EnumSet;
import java.util.List;
import net.minecraft.SystemUtils;
import net.minecraft.core.BaseBlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.IPosition;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import org.joml.Vector3f;

public class Vec3D implements IPosition {
   public static final Codec<Vec3D> a = Codec.DOUBLE
      .listOf()
      .comapFlatMap(
         var0 -> SystemUtils.a(var0, 3).map(var0x -> new Vec3D(var0x.get(0), var0x.get(1), var0x.get(2))), var0 -> List.of(var0.a(), var0.b(), var0.c())
      );
   public static final Vec3D b = new Vec3D(0.0, 0.0, 0.0);
   public final double c;
   public final double d;
   public final double e;

   public static Vec3D a(int var0) {
      double var1 = (double)(var0 >> 16 & 0xFF) / 255.0;
      double var3 = (double)(var0 >> 8 & 0xFF) / 255.0;
      double var5 = (double)(var0 & 0xFF) / 255.0;
      return new Vec3D(var1, var3, var5);
   }

   public static Vec3D a(BaseBlockPosition var0) {
      return new Vec3D((double)var0.u(), (double)var0.v(), (double)var0.w());
   }

   public static Vec3D a(BaseBlockPosition var0, double var1, double var3, double var5) {
      return new Vec3D((double)var0.u() + var1, (double)var0.v() + var3, (double)var0.w() + var5);
   }

   public static Vec3D b(BaseBlockPosition var0) {
      return a(var0, 0.5, 0.5, 0.5);
   }

   public static Vec3D c(BaseBlockPosition var0) {
      return a(var0, 0.5, 0.0, 0.5);
   }

   public static Vec3D a(BaseBlockPosition var0, double var1) {
      return a(var0, 0.5, var1, 0.5);
   }

   public Vec3D(double var0, double var2, double var4) {
      this.c = var0;
      this.d = var2;
      this.e = var4;
   }

   public Vec3D(Vector3f var0) {
      this((double)var0.x(), (double)var0.y(), (double)var0.z());
   }

   public Vec3D a(Vec3D var0) {
      return new Vec3D(var0.c - this.c, var0.d - this.d, var0.e - this.e);
   }

   public Vec3D d() {
      double var0 = Math.sqrt(this.c * this.c + this.d * this.d + this.e * this.e);
      return var0 < 1.0E-4 ? b : new Vec3D(this.c / var0, this.d / var0, this.e / var0);
   }

   public double b(Vec3D var0) {
      return this.c * var0.c + this.d * var0.d + this.e * var0.e;
   }

   public Vec3D c(Vec3D var0) {
      return new Vec3D(this.d * var0.e - this.e * var0.d, this.e * var0.c - this.c * var0.e, this.c * var0.d - this.d * var0.c);
   }

   public Vec3D d(Vec3D var0) {
      return this.a(var0.c, var0.d, var0.e);
   }

   public Vec3D a(double var0, double var2, double var4) {
      return this.b(-var0, -var2, -var4);
   }

   public Vec3D e(Vec3D var0) {
      return this.b(var0.c, var0.d, var0.e);
   }

   public Vec3D b(double var0, double var2, double var4) {
      return new Vec3D(this.c + var0, this.d + var2, this.e + var4);
   }

   public boolean a(IPosition var0, double var1) {
      return this.c(var0.a(), var0.b(), var0.c()) < var1 * var1;
   }

   public double f(Vec3D var0) {
      double var1 = var0.c - this.c;
      double var3 = var0.d - this.d;
      double var5 = var0.e - this.e;
      return Math.sqrt(var1 * var1 + var3 * var3 + var5 * var5);
   }

   public double g(Vec3D var0) {
      double var1 = var0.c - this.c;
      double var3 = var0.d - this.d;
      double var5 = var0.e - this.e;
      return var1 * var1 + var3 * var3 + var5 * var5;
   }

   public double c(double var0, double var2, double var4) {
      double var6 = var0 - this.c;
      double var8 = var2 - this.d;
      double var10 = var4 - this.e;
      return var6 * var6 + var8 * var8 + var10 * var10;
   }

   public Vec3D a(double var0) {
      return this.d(var0, var0, var0);
   }

   public Vec3D e() {
      return this.a(-1.0);
   }

   public Vec3D h(Vec3D var0) {
      return this.d(var0.c, var0.d, var0.e);
   }

   public Vec3D d(double var0, double var2, double var4) {
      return new Vec3D(this.c * var0, this.d * var2, this.e * var4);
   }

   public Vec3D a(RandomSource var0, float var1) {
      return this.b((double)((var0.i() - 0.5F) * var1), (double)((var0.i() - 0.5F) * var1), (double)((var0.i() - 0.5F) * var1));
   }

   public double f() {
      return Math.sqrt(this.c * this.c + this.d * this.d + this.e * this.e);
   }

   public double g() {
      return this.c * this.c + this.d * this.d + this.e * this.e;
   }

   public double h() {
      return Math.sqrt(this.c * this.c + this.e * this.e);
   }

   public double i() {
      return this.c * this.c + this.e * this.e;
   }

   @Override
   public boolean equals(Object var0) {
      if (this == var0) {
         return true;
      } else if (!(var0 instanceof Vec3D)) {
         return false;
      } else {
         Vec3D var1 = (Vec3D)var0;
         if (Double.compare(var1.c, this.c) != 0) {
            return false;
         } else if (Double.compare(var1.d, this.d) != 0) {
            return false;
         } else {
            return Double.compare(var1.e, this.e) == 0;
         }
      }
   }

   @Override
   public int hashCode() {
      long var1 = Double.doubleToLongBits(this.c);
      int var0 = (int)(var1 ^ var1 >>> 32);
      var1 = Double.doubleToLongBits(this.d);
      var0 = 31 * var0 + (int)(var1 ^ var1 >>> 32);
      var1 = Double.doubleToLongBits(this.e);
      return 31 * var0 + (int)(var1 ^ var1 >>> 32);
   }

   @Override
   public String toString() {
      return "(" + this.c + ", " + this.d + ", " + this.e + ")";
   }

   public Vec3D a(Vec3D var0, double var1) {
      return new Vec3D(MathHelper.d(var1, this.c, var0.c), MathHelper.d(var1, this.d, var0.d), MathHelper.d(var1, this.e, var0.e));
   }

   public Vec3D a(float var0) {
      float var1 = MathHelper.b(var0);
      float var2 = MathHelper.a(var0);
      double var3 = this.c;
      double var5 = this.d * (double)var1 + this.e * (double)var2;
      double var7 = this.e * (double)var1 - this.d * (double)var2;
      return new Vec3D(var3, var5, var7);
   }

   public Vec3D b(float var0) {
      float var1 = MathHelper.b(var0);
      float var2 = MathHelper.a(var0);
      double var3 = this.c * (double)var1 + this.e * (double)var2;
      double var5 = this.d;
      double var7 = this.e * (double)var1 - this.c * (double)var2;
      return new Vec3D(var3, var5, var7);
   }

   public Vec3D c(float var0) {
      float var1 = MathHelper.b(var0);
      float var2 = MathHelper.a(var0);
      double var3 = this.c * (double)var1 + this.d * (double)var2;
      double var5 = this.d * (double)var1 - this.c * (double)var2;
      double var7 = this.e;
      return new Vec3D(var3, var5, var7);
   }

   public static Vec3D a(Vec2F var0) {
      return a(var0.i, var0.j);
   }

   public static Vec3D a(float var0, float var1) {
      float var2 = MathHelper.b(-var1 * (float) (Math.PI / 180.0) - (float) Math.PI);
      float var3 = MathHelper.a(-var1 * (float) (Math.PI / 180.0) - (float) Math.PI);
      float var4 = -MathHelper.b(-var0 * (float) (Math.PI / 180.0));
      float var5 = MathHelper.a(-var0 * (float) (Math.PI / 180.0));
      return new Vec3D((double)(var3 * var4), (double)var5, (double)(var2 * var4));
   }

   public Vec3D a(EnumSet<EnumDirection.EnumAxis> var0) {
      double var1 = var0.contains(EnumDirection.EnumAxis.a) ? (double)MathHelper.a(this.c) : this.c;
      double var3 = var0.contains(EnumDirection.EnumAxis.b) ? (double)MathHelper.a(this.d) : this.d;
      double var5 = var0.contains(EnumDirection.EnumAxis.c) ? (double)MathHelper.a(this.e) : this.e;
      return new Vec3D(var1, var3, var5);
   }

   public double a(EnumDirection.EnumAxis var0) {
      return var0.a(this.c, this.d, this.e);
   }

   public Vec3D a(EnumDirection.EnumAxis var0, double var1) {
      double var3 = var0 == EnumDirection.EnumAxis.a ? var1 : this.c;
      double var5 = var0 == EnumDirection.EnumAxis.b ? var1 : this.d;
      double var7 = var0 == EnumDirection.EnumAxis.c ? var1 : this.e;
      return new Vec3D(var3, var5, var7);
   }

   public Vec3D a(EnumDirection var0, double var1) {
      BaseBlockPosition var3 = var0.q();
      return new Vec3D(this.c + var1 * (double)var3.u(), this.d + var1 * (double)var3.v(), this.e + var1 * (double)var3.w());
   }

   @Override
   public final double a() {
      return this.c;
   }

   @Override
   public final double b() {
      return this.d;
   }

   @Override
   public final double c() {
      return this.e;
   }

   public Vector3f j() {
      return new Vector3f((float)this.c, (float)this.d, (float)this.e);
   }
}
