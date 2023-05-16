package net.minecraft.core;

import com.google.common.collect.Sets;
import java.util.Arrays;
import java.util.Set;

public enum EnumDirection8 {
   a(EnumDirection.c),
   b(EnumDirection.c, EnumDirection.f),
   c(EnumDirection.f),
   d(EnumDirection.d, EnumDirection.f),
   e(EnumDirection.d),
   f(EnumDirection.d, EnumDirection.e),
   g(EnumDirection.e),
   h(EnumDirection.c, EnumDirection.e);

   private final Set<EnumDirection> i;
   private final BaseBlockPosition j;

   private EnumDirection8(EnumDirection... var2) {
      this.i = Sets.immutableEnumSet(Arrays.asList(var2));
      this.j = new BaseBlockPosition(0, 0, 0);

      for(EnumDirection var6 : var2) {
         this.j.u(this.j.u() + var6.j()).t(this.j.v() + var6.k()).s(this.j.w() + var6.l());
      }
   }

   public Set<EnumDirection> a() {
      return this.i;
   }

   public int b() {
      return this.j.u();
   }

   public int c() {
      return this.j.w();
   }
}
