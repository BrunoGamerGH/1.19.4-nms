package net.minecraft.world.entity;

import java.util.EnumSet;
import java.util.Set;

public enum RelativeMovement {
   a(0),
   b(1),
   c(2),
   d(3),
   e(4);

   public static final Set<RelativeMovement> f = Set.of(values());
   public static final Set<RelativeMovement> g = Set.of(e, d);
   private final int h;

   private RelativeMovement(int var2) {
      this.h = var2;
   }

   private int a() {
      return 1 << this.h;
   }

   private boolean b(int var0) {
      return (var0 & this.a()) == this.a();
   }

   public static Set<RelativeMovement> a(int var0) {
      Set<RelativeMovement> var1 = EnumSet.noneOf(RelativeMovement.class);

      for(RelativeMovement var5 : values()) {
         if (var5.b(var0)) {
            var1.add(var5);
         }
      }

      return var1;
   }

   public static int a(Set<RelativeMovement> var0) {
      int var1 = 0;

      for(RelativeMovement var3 : var0) {
         var1 |= var3.a();
      }

      return var1;
   }
}
