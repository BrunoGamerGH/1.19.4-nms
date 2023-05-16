package net.minecraft.tags;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;
import net.minecraft.core.Holder;
import net.minecraft.core.IRegistry;
import net.minecraft.core.IRegistryCustom;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.packs.resources.IReloadListener;
import net.minecraft.server.packs.resources.IResourceManager;
import net.minecraft.util.profiling.GameProfilerFiller;

public class TagRegistry implements IReloadListener {
   private static final Map<ResourceKey<? extends IRegistry<?>>, String> a = Map.of(
      Registries.e,
      "tags/blocks",
      Registries.r,
      "tags/entity_types",
      Registries.v,
      "tags/fluids",
      Registries.y,
      "tags/game_events",
      Registries.C,
      "tags/items"
   );
   private final IRegistryCustom b;
   private List<TagRegistry.a<?>> c = List.of();

   public TagRegistry(IRegistryCustom var0) {
      this.b = var0;
   }

   public List<TagRegistry.a<?>> a() {
      return this.c;
   }

   public static String a(ResourceKey<? extends IRegistry<?>> var0) {
      String var1 = a.get(var0);
      return var1 != null ? var1 : "tags/" + var0.a().a();
   }

   @Override
   public CompletableFuture<Void> a(
      IReloadListener.a var0, IResourceManager var1, GameProfilerFiller var2, GameProfilerFiller var3, Executor var4, Executor var5
   ) {
      List<? extends CompletableFuture<? extends TagRegistry.a<?>>> var6 = this.b.b().map(var2x -> this.a(var1, var4, var2x)).toList();
      return CompletableFuture.allOf(var6.toArray(var0x -> new CompletableFuture[var0x]))
         .thenCompose(var0::a)
         .thenAcceptAsync(var1x -> this.c = var6.stream().map(CompletableFuture::join).collect(Collectors.toUnmodifiableList()), var5);
   }

   private <T> CompletableFuture<TagRegistry.a<T>> a(IResourceManager var0, Executor var1, IRegistryCustom.d<T> var2) {
      ResourceKey<? extends IRegistry<T>> var3 = var2.a();
      IRegistry<T> var4 = var2.b();
      TagDataPack<Holder<T>> var5 = new TagDataPack<>(var2x -> var4.b(ResourceKey.a(var3, var2x)), a(var3));
      return CompletableFuture.supplyAsync(() -> new TagRegistry.a<>(var3, var5.b(var0)), var1);
   }

   public static record a<T>(ResourceKey<? extends IRegistry<T>> key, Map<MinecraftKey, Collection<Holder<T>>> tags) {
      private final ResourceKey<? extends IRegistry<T>> a;
      private final Map<MinecraftKey, Collection<Holder<T>>> b;

      public a(ResourceKey<? extends IRegistry<T>> var0, Map<MinecraftKey, Collection<Holder<T>>> var1) {
         this.a = var0;
         this.b = var1;
      }
   }
}
