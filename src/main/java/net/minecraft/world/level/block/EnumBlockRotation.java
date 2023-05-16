package net.minecraft.world.level.block;

import com.mojang.math.PointGroupO;
import com.mojang.serialization.Codec;
import java.util.List;
import net.minecraft.SystemUtils;
import net.minecraft.core.EnumDirection;
import net.minecraft.util.INamable;
import net.minecraft.util.RandomSource;

public enum EnumBlockRotation implements INamable {
   a("none", PointGroupO.a),
   b("clockwise_90", PointGroupO.u),
   c("180", PointGroupO.c),
   d("counterclockwise_90", PointGroupO.v);

   public static final Codec<EnumBlockRotation> e = INamable.a(EnumBlockRotation::values);
   private final String f;
   private final PointGroupO g;

   private EnumBlockRotation(String var2, PointGroupO var3) {
      this.f = var2;
      this.g = var3;
   }

   public EnumBlockRotation a(EnumBlockRotation var0) {
      switch(var0) {
         case c:
            switch(this) {
               case a:
                  return c;
               case b:
                  return d;
               case c:
                  return a;
               case d:
                  return b;
            }
         case d:
            switch(this) {
               case a:
                  return d;
               case b:
                  return a;
               case c:
                  return b;
               case d:
                  return c;
            }
         case b:
            switch(this) {
               case a:
                  return b;
               case b:
                  return c;
               case c:
                  return d;
               case d:
                  return a;
            }
         default:
            return this;
      }
   }

   public PointGroupO a() {
      return this.g;
   }

   public EnumDirection a(EnumDirection var0) {
      if (var0.o() == EnumDirection.EnumAxis.b) {
         return var0;
      } else {
         switch(this) {
            case b:
               return var0.h();
            case c:
               return var0.g();
            case d:
               return var0.i();
            default:
               return var0;
         }
      }
   }

   public int a(int var0, int var1) {
      switch(this) {
         case b:
            return (var0 + var1 / 4) % var1;
         case c:
            return (var0 + var1 / 2) % var1;
         case d:
            return (var0 + var1 * 3 / 4) % var1;
         default:
            return var0;
      }
   }

   public static EnumBlockRotation a(RandomSource var0) {
      return SystemUtils.a(values(), var0);
   }

   public static List<EnumBlockRotation> b(RandomSource var0) {
      return SystemUtils.b(values(), var0);
   }

   @Override
   public String c() {
      return this.f;
   }
}
