package net.minecraft.util;

import java.util.function.Supplier;
import org.apache.commons.lang3.ObjectUtils;

public record ModCheck(ModCheck.a confidence, String description) {
   private final ModCheck.a a;
   private final String b;

   public ModCheck(ModCheck.a var0, String var1) {
      this.a = var0;
      this.b = var1;
   }

   public static ModCheck a(String var0, Supplier<String> var1, String var2, Class<?> var3) {
      String var4 = var1.get();
      if (!var0.equals(var4)) {
         return new ModCheck(ModCheck.a.c, var2 + " brand changed to '" + var4 + "'");
      } else {
         return var3.getSigners() == null
            ? new ModCheck(ModCheck.a.b, var2 + " jar signature invalidated")
            : new ModCheck(ModCheck.a.a, var2 + " jar signature and brand is untouched");
      }
   }

   public boolean a() {
      return this.a.e;
   }

   public ModCheck a(ModCheck var0) {
      return new ModCheck((ModCheck.a)ObjectUtils.max(new ModCheck.a[]{this.a, var0.a}), this.b + "; " + var0.b);
   }

   public String b() {
      return this.a.d + " " + this.b;
   }

   public ModCheck.a c() {
      return this.a;
   }

   public String d() {
      return this.b;
   }

   public static enum a {
      a("Probably not.", false),
      b("Very likely;", true),
      c("Definitely;", true);

      final String d;
      final boolean e;

      private a(String var2, boolean var3) {
         this.d = var2;
         this.e = var3;
      }
   }
}
