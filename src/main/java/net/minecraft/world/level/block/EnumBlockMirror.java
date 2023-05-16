package net.minecraft.world.level.block;

import com.mojang.math.PointGroupO;
import com.mojang.serialization.Codec;
import net.minecraft.core.EnumDirection;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.util.INamable;

public enum EnumBlockMirror implements INamable {
   a("none", PointGroupO.a),
   b("left_right", PointGroupO.B),
   c("front_back", PointGroupO.z);

   public static final Codec<EnumBlockMirror> d = INamable.a(EnumBlockMirror::values);
   private final String e;
   private final IChatBaseComponent f;
   private final PointGroupO g;

   private EnumBlockMirror(String var2, PointGroupO var3) {
      this.e = var2;
      this.f = IChatBaseComponent.c("mirror." + var2);
      this.g = var3;
   }

   public int a(int var0, int var1) {
      int var2 = var1 / 2;
      int var3 = var0 > var2 ? var0 - var1 : var0;
      switch(this) {
         case c:
            return (var1 - var3) % var1;
         case b:
            return (var2 - var3 + var1) % var1;
         default:
            return var0;
      }
   }

   public EnumBlockRotation a(EnumDirection var0) {
      EnumDirection.EnumAxis var1 = var0.o();
      return (this != b || var1 != EnumDirection.EnumAxis.c) && (this != c || var1 != EnumDirection.EnumAxis.a) ? EnumBlockRotation.a : EnumBlockRotation.c;
   }

   public EnumDirection b(EnumDirection var0) {
      if (this == c && var0.o() == EnumDirection.EnumAxis.a) {
         return var0.g();
      } else {
         return this == b && var0.o() == EnumDirection.EnumAxis.c ? var0.g() : var0;
      }
   }

   public PointGroupO a() {
      return this.g;
   }

   public IChatBaseComponent b() {
      return this.f;
   }

   @Override
   public String c() {
      return this.e;
   }
}
