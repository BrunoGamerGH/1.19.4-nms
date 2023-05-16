package net.minecraft.server.packs.resources;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.packs.IResourcePack;

public interface IResourceManager extends ResourceProvider {
   Set<String> a();

   List<IResource> a(MinecraftKey var1);

   Map<MinecraftKey, IResource> b(String var1, Predicate<MinecraftKey> var2);

   Map<MinecraftKey, List<IResource>> c(String var1, Predicate<MinecraftKey> var2);

   Stream<IResourcePack> b();

   public static enum Empty implements IResourceManager {
      a;

      @Override
      public Set<String> a() {
         return Set.of();
      }

      @Override
      public Optional<IResource> getResource(MinecraftKey var0) {
         return Optional.empty();
      }

      @Override
      public List<IResource> a(MinecraftKey var0) {
         return List.of();
      }

      @Override
      public Map<MinecraftKey, IResource> b(String var0, Predicate<MinecraftKey> var1) {
         return Map.of();
      }

      @Override
      public Map<MinecraftKey, List<IResource>> c(String var0, Predicate<MinecraftKey> var1) {
         return Map.of();
      }

      @Override
      public Stream<IResourcePack> b() {
         return Stream.of();
      }
   }
}
