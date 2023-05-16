package net.minecraft.util.profiling.jfr;

import net.minecraft.server.MinecraftServer;

public enum Environment {
   a("client"),
   b("server");

   private final String c;

   private Environment(String var2) {
      this.c = var2;
   }

   public static Environment a(MinecraftServer var0) {
      return var0.l() ? b : a;
   }

   public String a() {
      return this.c;
   }
}
