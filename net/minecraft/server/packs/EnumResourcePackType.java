package net.minecraft.server.packs;

public enum EnumResourcePackType {
   a("assets"),
   b("data");

   private final String c;

   private EnumResourcePackType(String var2) {
      this.c = var2;
   }

   public String a() {
      return this.c;
   }
}
