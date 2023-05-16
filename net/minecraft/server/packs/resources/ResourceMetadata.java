package net.minecraft.server.packs.resources;

import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import net.minecraft.server.packs.metadata.ResourcePackMetaParser;
import net.minecraft.util.ChatDeserializer;

public interface ResourceMetadata {
   ResourceMetadata a = new ResourceMetadata() {
      @Override
      public <T> Optional<T> a(ResourcePackMetaParser<T> var0) {
         return Optional.empty();
      }
   };
   IoSupplier<ResourceMetadata> b = () -> a;

   static ResourceMetadata a(InputStream var0) throws IOException {
      ResourceMetadata var3;
      try (BufferedReader var1 = new BufferedReader(new InputStreamReader(var0, StandardCharsets.UTF_8))) {
         final JsonObject var2 = ChatDeserializer.a(var1);
         var3 = new ResourceMetadata() {
            @Override
            public <T> Optional<T> a(ResourcePackMetaParser<T> var0) {
               String var1 = var0.a();
               return var2.has(var1) ? Optional.of(var0.a(ChatDeserializer.t(var2, var1))) : Optional.empty();
            }
         };
      }

      return var3;
   }

   <T> Optional<T> a(ResourcePackMetaParser<T> var1);
}
