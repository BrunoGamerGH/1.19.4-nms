package net.minecraft.resources;

import java.util.List;
import java.util.Map;
import net.minecraft.server.packs.resources.IResource;
import net.minecraft.server.packs.resources.IResourceManager;

public class FileToIdConverter {
   private final String a;
   private final String b;

   public FileToIdConverter(String var0, String var1) {
      this.a = var0;
      this.b = var1;
   }

   public static FileToIdConverter a(String var0) {
      return new FileToIdConverter(var0, ".json");
   }

   public MinecraftKey a(MinecraftKey var0) {
      return var0.c(this.a + "/" + var0.a() + this.b);
   }

   public MinecraftKey b(MinecraftKey var0) {
      String var1 = var0.a();
      return var0.c(var1.substring(this.a.length() + 1, var1.length() - this.b.length()));
   }

   public Map<MinecraftKey, IResource> a(IResourceManager var0) {
      return var0.b(this.a, var0x -> var0x.a().endsWith(this.b));
   }

   public Map<MinecraftKey, List<IResource>> b(IResourceManager var0) {
      return var0.c(this.a, var0x -> var0x.a().endsWith(this.b));
   }
}
