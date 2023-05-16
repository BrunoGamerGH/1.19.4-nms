package net.minecraft.server.network;

import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.network.chat.FilterMask;

public record FilteredText(String raw, FilterMask mask) {
   private final String b;
   private final FilterMask c;
   public static final FilteredText a = a("");

   public FilteredText(String var0, FilterMask var1) {
      this.b = var0;
      this.c = var1;
   }

   public static FilteredText a(String var0) {
      return new FilteredText(var0, FilterMask.c);
   }

   public static FilteredText b(String var0) {
      return new FilteredText(var0, FilterMask.b);
   }

   @Nullable
   public String a() {
      return this.c.a(this.b);
   }

   public String b() {
      return Objects.requireNonNullElse(this.a(), "");
   }

   public boolean c() {
      return !this.c.a();
   }

   public String d() {
      return this.b;
   }

   public FilterMask e() {
      return this.c;
   }
}
