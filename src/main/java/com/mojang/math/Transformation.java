package com.mojang.math;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.SystemUtils;
import net.minecraft.util.ExtraCodecs;
import org.apache.commons.lang3.tuple.Triple;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Quaternionfc;
import org.joml.Vector3f;
import org.joml.Vector3fc;

public final class Transformation {
   private final Matrix4f c;
   public static final Codec<Transformation> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               ExtraCodecs.c.fieldOf("translation").forGetter(var0x -> var0x.e),
               ExtraCodecs.f.fieldOf("left_rotation").forGetter(var0x -> var0x.f),
               ExtraCodecs.c.fieldOf("scale").forGetter(var0x -> var0x.g),
               ExtraCodecs.f.fieldOf("right_rotation").forGetter(var0x -> var0x.h)
            )
            .apply(var0, Transformation::new)
   );
   public static final Codec<Transformation> b = Codec.either(a, ExtraCodecs.g.xmap(Transformation::new, Transformation::c))
      .xmap(var0 -> (Transformation)var0.map(var0x -> var0x, var0x -> var0x), Either::left);
   private boolean d;
   @Nullable
   private Vector3f e;
   @Nullable
   private Quaternionf f;
   @Nullable
   private Vector3f g;
   @Nullable
   private Quaternionf h;
   private static final Transformation i = SystemUtils.a(() -> {
      Transformation var0 = new Transformation(new Matrix4f());
      var0.e = new Vector3f();
      var0.f = new Quaternionf();
      var0.g = new Vector3f(1.0F, 1.0F, 1.0F);
      var0.h = new Quaternionf();
      var0.d = true;
      return var0;
   });

   public Transformation(@Nullable Matrix4f var0) {
      if (var0 == null) {
         this.c = new Matrix4f();
      } else {
         this.c = var0;
      }
   }

   public Transformation(@Nullable Vector3f var0, @Nullable Quaternionf var1, @Nullable Vector3f var2, @Nullable Quaternionf var3) {
      this.c = a(var0, var1, var2, var3);
      this.e = var0 != null ? var0 : new Vector3f();
      this.f = var1 != null ? var1 : new Quaternionf();
      this.g = var2 != null ? var2 : new Vector3f(1.0F, 1.0F, 1.0F);
      this.h = var3 != null ? var3 : new Quaternionf();
      this.d = true;
   }

   public static Transformation a() {
      return i;
   }

   public Transformation a(Transformation var0) {
      Matrix4f var1 = this.c();
      var1.mul(var0.c());
      return new Transformation(var1);
   }

   @Nullable
   public Transformation b() {
      if (this == i) {
         return this;
      } else {
         Matrix4f var0 = this.c().invert();
         return var0.isFinite() ? new Transformation(var0) : null;
      }
   }

   private void h() {
      if (!this.d) {
         float var0 = 1.0F / this.c.m33();
         Triple<Quaternionf, Vector3f, Quaternionf> var1 = MatrixUtil.a(new Matrix3f(this.c).scale(var0));
         this.e = this.c.getTranslation(new Vector3f()).mul(var0);
         this.f = new Quaternionf((Quaternionfc)var1.getLeft());
         this.g = new Vector3f((Vector3fc)var1.getMiddle());
         this.h = new Quaternionf((Quaternionfc)var1.getRight());
         this.d = true;
      }
   }

   private static Matrix4f a(@Nullable Vector3f var0, @Nullable Quaternionf var1, @Nullable Vector3f var2, @Nullable Quaternionf var3) {
      Matrix4f var4 = new Matrix4f();
      if (var0 != null) {
         var4.translation(var0);
      }

      if (var1 != null) {
         var4.rotate(var1);
      }

      if (var2 != null) {
         var4.scale(var2);
      }

      if (var3 != null) {
         var4.rotate(var3);
      }

      return var4;
   }

   public Matrix4f c() {
      return new Matrix4f(this.c);
   }

   public Vector3f d() {
      this.h();
      return new Vector3f(this.e);
   }

   public Quaternionf e() {
      this.h();
      return new Quaternionf(this.f);
   }

   public Vector3f f() {
      this.h();
      return new Vector3f(this.g);
   }

   public Quaternionf g() {
      this.h();
      return new Quaternionf(this.h);
   }

   @Override
   public boolean equals(Object var0) {
      if (this == var0) {
         return true;
      } else if (var0 != null && this.getClass() == var0.getClass()) {
         Transformation var1 = (Transformation)var0;
         return Objects.equals(this.c, var1.c);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.c);
   }

   public Transformation a(Transformation var0, float var1) {
      Vector3f var2 = this.d();
      Quaternionf var3 = this.e();
      Vector3f var4 = this.f();
      Quaternionf var5 = this.g();
      var2.lerp(var0.d(), var1);
      var3.slerp(var0.e(), var1);
      var4.lerp(var0.f(), var1);
      var5.slerp(var0.g(), var1);
      return new Transformation(var2, var3, var4, var5);
   }
}
