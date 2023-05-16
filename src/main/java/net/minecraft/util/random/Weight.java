package net.minecraft.util.random;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import net.minecraft.SharedConstants;
import net.minecraft.SystemUtils;
import org.slf4j.Logger;

public class Weight {
   public static final Codec<Weight> a = Codec.INT.xmap(Weight::a, Weight::a);
   private static final Weight b = new Weight(1);
   private static final Logger c = LogUtils.getLogger();
   private final int d;

   private Weight(int var0) {
      this.d = var0;
   }

   public static Weight a(int var0) {
      if (var0 == 1) {
         return b;
      } else {
         b(var0);
         return new Weight(var0);
      }
   }

   public int a() {
      return this.d;
   }

   private static void b(int var0) {
      if (var0 < 0) {
         throw (IllegalArgumentException)SystemUtils.b(new IllegalArgumentException("Weight should be >= 0"));
      } else {
         if (var0 == 0 && SharedConstants.aO) {
            c.warn("Found 0 weight, make sure this is intentional!");
         }
      }
   }

   @Override
   public String toString() {
      return Integer.toString(this.d);
   }

   @Override
   public int hashCode() {
      return Integer.hashCode(this.d);
   }

   @Override
   public boolean equals(Object var0) {
      if (this == var0) {
         return true;
      } else {
         return var0 instanceof Weight && this.d == ((Weight)var0).d;
      }
   }
}
