package net.minecraft.server;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.ImmutableMap.Builder;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.Executor;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.commands.CustomFunction;
import net.minecraft.commands.ICommandListener;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.packs.resources.IReloadListener;
import net.minecraft.server.packs.resources.IResource;
import net.minecraft.server.packs.resources.IResourceManager;
import net.minecraft.tags.TagDataPack;
import net.minecraft.util.profiling.GameProfilerFiller;
import net.minecraft.world.phys.Vec2F;
import net.minecraft.world.phys.Vec3D;
import org.slf4j.Logger;

public class CustomFunctionManager implements IReloadListener {
   private static final Logger a = LogUtils.getLogger();
   private static final FileToIdConverter b = new FileToIdConverter("functions", ".mcfunction");
   private volatile Map<MinecraftKey, CustomFunction> c = ImmutableMap.of();
   private final TagDataPack<CustomFunction> d = new TagDataPack<>(this::a, "tags/functions");
   private volatile Map<MinecraftKey, Collection<CustomFunction>> e = Map.of();
   private final int f;
   private final CommandDispatcher<CommandListenerWrapper> g;

   public Optional<CustomFunction> a(MinecraftKey var0) {
      return Optional.ofNullable(this.c.get(var0));
   }

   public Map<MinecraftKey, CustomFunction> a() {
      return this.c;
   }

   public Collection<CustomFunction> b(MinecraftKey var0) {
      return this.e.getOrDefault(var0, List.of());
   }

   public Iterable<MinecraftKey> b() {
      return this.e.keySet();
   }

   public CustomFunctionManager(int var0, CommandDispatcher<CommandListenerWrapper> var1) {
      this.f = var0;
      this.g = var1;
   }

   @Override
   public CompletableFuture<Void> a(
      IReloadListener.a var0, IResourceManager var1, GameProfilerFiller var2, GameProfilerFiller var3, Executor var4, Executor var5
   ) {
      CompletableFuture<Map<MinecraftKey, List<TagDataPack.a>>> var6 = CompletableFuture.supplyAsync(() -> this.d.a(var1), var4);
      CompletableFuture<Map<MinecraftKey, CompletableFuture<CustomFunction>>> var7 = CompletableFuture.<Map<MinecraftKey, IResource>>supplyAsync(
            () -> b.a(var1), var4
         )
         .thenCompose(var1x -> {
            Map<MinecraftKey, CompletableFuture<CustomFunction>> var2x = Maps.newHashMap();
            CommandListenerWrapper var3x = new CommandListenerWrapper(ICommandListener.a, Vec3D.b, Vec2F.a, null, this.f, "", CommonComponents.a, null, null);
   
            for(Entry<MinecraftKey, IResource> var5x : var1x.entrySet()) {
               MinecraftKey var6x = (MinecraftKey)var5x.getKey();
               MinecraftKey var7x = b.b(var6x);
               var2x.put(var7x, CompletableFuture.supplyAsync(() -> {
                  List<String> var3xx = a((IResource)var5x.getValue());
                  return CustomFunction.a(var7x, this.g, var3x, var3xx);
               }, var4));
            }
   
            CompletableFuture<?>[] var4 = var2x.values().toArray(new CompletableFuture[0]);
            return CompletableFuture.allOf(var4).handle((var1xx, var2xx) -> var2x);
         });
      return var6.thenCombine(var7, Pair::of).thenCompose(var0::a).thenAcceptAsync(var0x -> {
         Map<MinecraftKey, CompletableFuture<CustomFunction>> var1x = (Map)var0x.getSecond();
         Builder<MinecraftKey, CustomFunction> var2x = ImmutableMap.builder();
         var1x.forEach((var1xx, var2xx) -> var2xx.handle((var2xxx, var3x) -> {
               if (var3x != null) {
                  a.error("Failed to load function {}", var1xx, var3x);
               } else {
                  var2x.put(var1xx, var2xxx);
               }

               return null;
            }).join());
         this.c = var2x.build();
         this.e = this.d.a((Map<MinecraftKey, List<TagDataPack.a>>)var0x.getFirst());
      }, var5);
   }

   private static List<String> a(IResource var0) {
      try {
         List var2;
         try (BufferedReader var1 = var0.e()) {
            var2 = var1.lines().toList();
         }

         return var2;
      } catch (IOException var6) {
         throw new CompletionException(var6);
      }
   }
}
