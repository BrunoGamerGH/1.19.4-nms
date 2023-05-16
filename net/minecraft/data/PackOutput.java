package net.minecraft.data;

import java.nio.file.Path;
import net.minecraft.resources.MinecraftKey;

public class PackOutput {
   private final Path a;

   public PackOutput(Path var0) {
      this.a = var0;
   }

   public Path a() {
      return this.a;
   }

   public Path a(PackOutput.b var0) {
      return this.a().resolve(var0.d);
   }

   public PackOutput.a a(PackOutput.b var0, String var1) {
      return new PackOutput.a(this, var0, var1);
   }

   public static class a {
      private final Path a;
      private final String b;

      a(PackOutput var0, PackOutput.b var1, String var2) {
         this.a = var0.a(var1);
         this.b = var2;
      }

      public Path a(MinecraftKey var0, String var1) {
         return this.a.resolve(var0.b()).resolve(this.b).resolve(var0.a() + "." + var1);
      }

      public Path a(MinecraftKey var0) {
         return this.a.resolve(var0.b()).resolve(this.b).resolve(var0.a() + ".json");
      }
   }

   public static enum b {
      a("data"),
      b("assets"),
      c("reports");

      final String d;

      private b(String var2) {
         this.d = var2;
      }
   }
}
