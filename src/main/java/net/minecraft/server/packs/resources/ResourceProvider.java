package net.minecraft.server.packs.resources;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Optional;
import net.minecraft.resources.MinecraftKey;

@FunctionalInterface
public interface ResourceProvider {
   Optional<IResource> getResource(MinecraftKey var1);

   default IResource getResourceOrThrow(MinecraftKey var0) throws FileNotFoundException {
      return this.getResource(var0).orElseThrow(() -> new FileNotFoundException(var0.toString()));
   }

   default InputStream open(MinecraftKey var0) throws IOException {
      return this.getResourceOrThrow(var0).d();
   }

   default BufferedReader openAsReader(MinecraftKey var0) throws IOException {
      return this.getResourceOrThrow(var0).e();
   }

   static ResourceProvider fromMap(Map<MinecraftKey, IResource> var0) {
      return var1 -> Optional.ofNullable(var0.get(var1));
   }
}
