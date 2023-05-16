package net.minecraft.core;

import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.MathHelper;

public class Vector3f {
   protected final float a;
   protected final float b;
   protected final float c;

   public Vector3f(float var0, float var1, float var2) {
      this.a = !Float.isInfinite(var0) && !Float.isNaN(var0) ? var0 % 360.0F : 0.0F;
      this.b = !Float.isInfinite(var1) && !Float.isNaN(var1) ? var1 % 360.0F : 0.0F;
      this.c = !Float.isInfinite(var2) && !Float.isNaN(var2) ? var2 % 360.0F : 0.0F;
   }

   public Vector3f(NBTTagList var0) {
      this(var0.i(0), var0.i(1), var0.i(2));
   }

   public NBTTagList a() {
      NBTTagList var0 = new NBTTagList();
      var0.add(NBTTagFloat.a(this.a));
      var0.add(NBTTagFloat.a(this.b));
      var0.add(NBTTagFloat.a(this.c));
      return var0;
   }

   @Override
   public boolean equals(Object var0) {
      if (!(var0 instanceof Vector3f)) {
         return false;
      } else {
         Vector3f var1 = (Vector3f)var0;
         return this.a == var1.a && this.b == var1.b && this.c == var1.c;
      }
   }

   public float b() {
      return this.a;
   }

   public float c() {
      return this.b;
   }

   public float d() {
      return this.c;
   }

   public float e() {
      return MathHelper.g(this.a);
   }

   public float f() {
      return MathHelper.g(this.b);
   }

   public float g() {
      return MathHelper.g(this.c);
   }
}
