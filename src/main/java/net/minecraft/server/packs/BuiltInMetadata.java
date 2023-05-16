package net.minecraft.server.packs;

import java.util.Map;
import net.minecraft.server.packs.metadata.ResourcePackMetaParser;

public class BuiltInMetadata {
   private static final BuiltInMetadata a = new BuiltInMetadata(Map.of());
   private final Map<ResourcePackMetaParser<?>, ?> b;

   private BuiltInMetadata(Map<ResourcePackMetaParser<?>, ?> var0) {
      this.b = var0;
   }

   public <T> T a(ResourcePackMetaParser<T> var0) {
      return (T)this.b.get(var0);
   }

   public static BuiltInMetadata a() {
      return a;
   }

   public static <T> BuiltInMetadata a(ResourcePackMetaParser<T> var0, T var1) {
      return new BuiltInMetadata(Map.of(var0, var1));
   }

   public static <T1, T2> BuiltInMetadata a(ResourcePackMetaParser<T1> var0, T1 var1, ResourcePackMetaParser<T2> var2, T2 var3) {
      return new BuiltInMetadata(Map.of(var0, var1, var2, (T1)var3));
   }
}
