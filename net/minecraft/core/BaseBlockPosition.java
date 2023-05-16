package net.minecraft.core;

import com.google.common.base.MoreObjects;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import java.util.function.Function;
import java.util.stream.IntStream;
import javax.annotation.concurrent.Immutable;
import net.minecraft.SystemUtils;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.MathHelper;

@Immutable
public class BaseBlockPosition implements Comparable<BaseBlockPosition> {
   public static final Codec<BaseBlockPosition> f = Codec.INT_STREAM
      .comapFlatMap(
         var0 -> SystemUtils.a(var0, 3).map(var0x -> new BaseBlockPosition(var0x[0], var0x[1], var0x[2])), var0 -> IntStream.of(var0.u(), var0.v(), var0.w())
      );
   public static final BaseBlockPosition g = new BaseBlockPosition(0, 0, 0);
   private int a;
   private int b;
   private int c;

   public static Codec<BaseBlockPosition> v(int var0) {
      return ExtraCodecs.a(
         f,
         (Function<BaseBlockPosition, DataResult<BaseBlockPosition>>)(var1 -> Math.abs(var1.u()) < var0
                  && Math.abs(var1.v()) < var0
                  && Math.abs(var1.w()) < var0
               ? DataResult.success(var1)
               : DataResult.error(() -> "Position out of range, expected at most " + var0 + ": " + var1))
      );
   }

   public BaseBlockPosition(int var0, int var1, int var2) {
      this.a = var0;
      this.b = var1;
      this.c = var2;
   }

   @Override
   public boolean equals(Object var0) {
      if (this == var0) {
         return true;
      } else if (!(var0 instanceof BaseBlockPosition)) {
         return false;
      } else {
         BaseBlockPosition var1 = (BaseBlockPosition)var0;
         if (this.u() != var1.u()) {
            return false;
         } else if (this.v() != var1.v()) {
            return false;
         } else {
            return this.w() == var1.w();
         }
      }
   }

   @Override
   public int hashCode() {
      return (this.v() + this.w() * 31) * 31 + this.u();
   }

   public int i(BaseBlockPosition var0) {
      if (this.v() == var0.v()) {
         return this.w() == var0.w() ? this.u() - var0.u() : this.w() - var0.w();
      } else {
         return this.v() - var0.v();
      }
   }

   public int u() {
      return this.a;
   }

   public int v() {
      return this.b;
   }

   public int w() {
      return this.c;
   }

   protected BaseBlockPosition u(int var0) {
      this.a = var0;
      return this;
   }

   protected BaseBlockPosition t(int var0) {
      this.b = var0;
      return this;
   }

   protected BaseBlockPosition s(int var0) {
      this.c = var0;
      return this;
   }

   public BaseBlockPosition c(int var0, int var1, int var2) {
      return var0 == 0 && var1 == 0 && var2 == 0 ? this : new BaseBlockPosition(this.u() + var0, this.v() + var1, this.w() + var2);
   }

   public BaseBlockPosition f(BaseBlockPosition var0) {
      return this.c(var0.u(), var0.v(), var0.w());
   }

   public BaseBlockPosition e(BaseBlockPosition var0) {
      return this.c(-var0.u(), -var0.v(), -var0.w());
   }

   public BaseBlockPosition o(int var0) {
      if (var0 == 1) {
         return this;
      } else {
         return var0 == 0 ? g : new BaseBlockPosition(this.u() * var0, this.v() * var0, this.w() * var0);
      }
   }

   public BaseBlockPosition p() {
      return this.n(1);
   }

   public BaseBlockPosition n(int var0) {
      return this.b(EnumDirection.b, var0);
   }

   public BaseBlockPosition o() {
      return this.m(1);
   }

   public BaseBlockPosition m(int var0) {
      return this.b(EnumDirection.a, var0);
   }

   public BaseBlockPosition n() {
      return this.l(1);
   }

   public BaseBlockPosition l(int var0) {
      return this.b(EnumDirection.c, var0);
   }

   public BaseBlockPosition m() {
      return this.k(1);
   }

   public BaseBlockPosition k(int var0) {
      return this.b(EnumDirection.d, var0);
   }

   public BaseBlockPosition l() {
      return this.j(1);
   }

   public BaseBlockPosition j(int var0) {
      return this.b(EnumDirection.e, var0);
   }

   public BaseBlockPosition k() {
      return this.i(1);
   }

   public BaseBlockPosition i(int var0) {
      return this.b(EnumDirection.f, var0);
   }

   public BaseBlockPosition b(EnumDirection var0) {
      return this.b(var0, 1);
   }

   public BaseBlockPosition b(EnumDirection var0, int var1) {
      return var1 == 0 ? this : new BaseBlockPosition(this.u() + var0.j() * var1, this.v() + var0.k() * var1, this.w() + var0.l() * var1);
   }

   public BaseBlockPosition b(EnumDirection.EnumAxis var0, int var1) {
      if (var1 == 0) {
         return this;
      } else {
         int var2 = var0 == EnumDirection.EnumAxis.a ? var1 : 0;
         int var3 = var0 == EnumDirection.EnumAxis.b ? var1 : 0;
         int var4 = var0 == EnumDirection.EnumAxis.c ? var1 : 0;
         return new BaseBlockPosition(this.u() + var2, this.v() + var3, this.w() + var4);
      }
   }

   public BaseBlockPosition d(BaseBlockPosition var0) {
      return new BaseBlockPosition(
         this.v() * var0.w() - this.w() * var0.v(), this.w() * var0.u() - this.u() * var0.w(), this.u() * var0.v() - this.v() * var0.u()
      );
   }

   public boolean a(BaseBlockPosition var0, double var1) {
      return this.j(var0) < MathHelper.k(var1);
   }

   public boolean a(IPosition var0, double var1) {
      return this.b(var0) < MathHelper.k(var1);
   }

   public double j(BaseBlockPosition var0) {
      return this.d((double)var0.u(), (double)var0.v(), (double)var0.w());
   }

   public double b(IPosition var0) {
      return this.c(var0.a(), var0.b(), var0.c());
   }

   public double c(double var0, double var2, double var4) {
      double var6 = (double)this.u() + 0.5 - var0;
      double var8 = (double)this.v() + 0.5 - var2;
      double var10 = (double)this.w() + 0.5 - var4;
      return var6 * var6 + var8 * var8 + var10 * var10;
   }

   public double d(double var0, double var2, double var4) {
      double var6 = (double)this.u() - var0;
      double var8 = (double)this.v() - var2;
      double var10 = (double)this.w() - var4;
      return var6 * var6 + var8 * var8 + var10 * var10;
   }

   public int k(BaseBlockPosition var0) {
      float var1 = (float)Math.abs(var0.u() - this.u());
      float var2 = (float)Math.abs(var0.v() - this.v());
      float var3 = (float)Math.abs(var0.w() - this.w());
      return (int)(var1 + var2 + var3);
   }

   public int a(EnumDirection.EnumAxis var0) {
      return var0.a(this.a, this.b, this.c);
   }

   @Override
   public String toString() {
      return MoreObjects.toStringHelper(this).add("x", this.u()).add("y", this.v()).add("z", this.w()).toString();
   }

   public String x() {
      return this.u() + ", " + this.v() + ", " + this.w();
   }
}
