package net.minecraft.world.flag;

import it.unimi.dsi.fastutil.HashCommon;
import java.util.Arrays;
import java.util.Collection;
import javax.annotation.Nullable;

public final class FeatureFlagSet {
   private static final FeatureFlagSet b = new FeatureFlagSet(null, 0L);
   public static final int a = 64;
   @Nullable
   private final FeatureFlagUniverse c;
   private final long d;

   private FeatureFlagSet(@Nullable FeatureFlagUniverse var0, long var1) {
      this.c = var0;
      this.d = var1;
   }

   static FeatureFlagSet a(FeatureFlagUniverse var0, Collection<FeatureFlag> var1) {
      if (var1.isEmpty()) {
         return b;
      } else {
         long var2 = a(var0, 0L, var1);
         return new FeatureFlagSet(var0, var2);
      }
   }

   public static FeatureFlagSet a() {
      return b;
   }

   public static FeatureFlagSet a(FeatureFlag var0) {
      return new FeatureFlagSet(var0.a, var0.b);
   }

   public static FeatureFlagSet a(FeatureFlag var0, FeatureFlag... var1) {
      long var2 = var1.length == 0 ? var0.b : a(var0.a, var0.b, Arrays.asList(var1));
      return new FeatureFlagSet(var0.a, var2);
   }

   private static long a(FeatureFlagUniverse var0, long var1, Iterable<FeatureFlag> var3) {
      for(FeatureFlag var5 : var3) {
         if (var0 != var5.a) {
            throw new IllegalStateException("Mismatched feature universe, expected '" + var0 + "', but got '" + var5.a + "'");
         }

         var1 |= var5.b;
      }

      return var1;
   }

   public boolean b(FeatureFlag var0) {
      if (this.c != var0.a) {
         return false;
      } else {
         return (this.d & var0.b) != 0L;
      }
   }

   public boolean a(FeatureFlagSet var0) {
      if (this.c == null) {
         return true;
      } else if (this.c != var0.c) {
         return false;
      } else {
         return (this.d & ~var0.d) == 0L;
      }
   }

   public FeatureFlagSet b(FeatureFlagSet var0) {
      if (this.c == null) {
         return var0;
      } else if (var0.c == null) {
         return this;
      } else if (this.c != var0.c) {
         throw new IllegalArgumentException("Mismatched set elements: '" + this.c + "' != '" + var0.c + "'");
      } else {
         return new FeatureFlagSet(this.c, this.d | var0.d);
      }
   }

   @Override
   public boolean equals(Object var0) {
      if (this == var0) {
         return true;
      } else {
         if (var0 instanceof FeatureFlagSet var1 && this.c == var1.c && this.d == var1.d) {
            return true;
         }

         return false;
      }
   }

   @Override
   public int hashCode() {
      return (int)HashCommon.mix(this.d);
   }
}
