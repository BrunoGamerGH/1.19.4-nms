package net.minecraft.world.level.storage;

public class DataVersion {
   private final int b;
   private final String c;
   public static String a = "main";

   public DataVersion(int var0) {
      this(var0, a);
   }

   public DataVersion(int var0, String var1) {
      this.b = var0;
      this.c = var1;
   }

   public boolean a() {
      return !this.c.equals(a);
   }

   public String b() {
      return this.c;
   }

   public int c() {
      return this.b;
   }

   public boolean a(DataVersion var0) {
      return this.b().equals(var0.b());
   }
}
