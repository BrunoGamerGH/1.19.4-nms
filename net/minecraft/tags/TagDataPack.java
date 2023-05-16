package net.minecraft.tags;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import com.google.common.collect.ImmutableSet.Builder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mojang.datafixers.util.Either;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.JsonOps;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.packs.resources.IResource;
import net.minecraft.server.packs.resources.IResourceManager;
import org.slf4j.Logger;

public class TagDataPack<T> {
   private static final Logger a = LogUtils.getLogger();
   final Function<MinecraftKey, Optional<? extends T>> b;
   private final String c;

   public TagDataPack(Function<MinecraftKey, Optional<? extends T>> var0, String var1) {
      this.b = var0;
      this.c = var1;
   }

   public Map<MinecraftKey, List<TagDataPack.a>> a(IResourceManager var0) {
      Map<MinecraftKey, List<TagDataPack.a>> var1 = Maps.newHashMap();
      FileToIdConverter var2 = FileToIdConverter.a(this.c);

      for(Entry<MinecraftKey, List<IResource>> var4 : var2.b(var0).entrySet()) {
         MinecraftKey var5 = var4.getKey();
         MinecraftKey var6 = var2.b(var5);

         for(IResource var8 : var4.getValue()) {
            try (Reader var9 = var8.e()) {
               JsonElement var10 = JsonParser.parseReader(var9);
               List<TagDataPack.a> var11 = var1.computeIfAbsent(var6, var0x -> new ArrayList());
               TagFile var12 = (TagFile)TagFile.a.parse(new Dynamic(JsonOps.INSTANCE, var10)).getOrThrow(false, a::error);
               if (var12.b()) {
                  var11.clear();
               }

               String var13 = var8.b();
               var12.a().forEach(var2x -> var11.add(new TagDataPack.a(var2x, var13)));
            } catch (Exception var17) {
               a.error("Couldn't read tag list {} from {} in data pack {}", new Object[]{var6, var5, var8.b(), var17});
            }
         }
      }

      return var1;
   }

   private static void a(
      Map<MinecraftKey, List<TagDataPack.a>> var0,
      Multimap<MinecraftKey, MinecraftKey> var1,
      Set<MinecraftKey> var2,
      MinecraftKey var3,
      BiConsumer<MinecraftKey, List<TagDataPack.a>> var4
   ) {
      if (var2.add(var3)) {
         var1.get(var3).forEach(var4x -> a(var0, var1, var2, var4x, var4));
         List<TagDataPack.a> var5 = var0.get(var3);
         if (var5 != null) {
            var4.accept(var3, var5);
         }
      }
   }

   private static boolean a(Multimap<MinecraftKey, MinecraftKey> var0, MinecraftKey var1, MinecraftKey var2) {
      Collection<MinecraftKey> var3 = var0.get(var2);
      return var3.contains(var1) ? true : var3.stream().anyMatch(var2x -> a(var0, var1, var2x));
   }

   private static void b(Multimap<MinecraftKey, MinecraftKey> var0, MinecraftKey var1, MinecraftKey var2) {
      if (!a(var0, var1, var2)) {
         var0.put(var1, var2);
      }
   }

   private Either<Collection<TagDataPack.a>, Collection<T>> a(TagEntry.a<T> var0, List<TagDataPack.a> var1) {
      Builder<T> var2 = ImmutableSet.builder();
      List<TagDataPack.a> var3 = new ArrayList<>();

      for(TagDataPack.a var5 : var1) {
         if (!var5.a().a(var0, var2::add)) {
            var3.add(var5);
         }
      }

      return var3.isEmpty() ? Either.right(var2.build()) : Either.left(var3);
   }

   public Map<MinecraftKey, Collection<T>> a(Map<MinecraftKey, List<TagDataPack.a>> var0) {
      final Map<MinecraftKey, Collection<T>> var1 = Maps.newHashMap();
      TagEntry.a<T> var2 = new TagEntry.a<T>() {
         @Nullable
         @Override
         public T a(MinecraftKey var0) {
            return TagDataPack.this.b.apply(var0).orElse((T)null);
         }

         @Nullable
         @Override
         public Collection<T> b(MinecraftKey var0) {
            return var1.get(var0);
         }
      };
      Multimap<MinecraftKey, MinecraftKey> var3 = HashMultimap.create();
      var0.forEach((var1x, var2x) -> var2x.forEach(var2xx -> var2xx.a.a(var2xxx -> b(var3, var1x, var2xxx))));
      var0.forEach((var1x, var2x) -> var2x.forEach(var2xx -> var2xx.a.b(var2xxx -> b(var3, var1x, var2xxx))));
      Set<MinecraftKey> var4 = Sets.newHashSet();
      var0.keySet()
         .forEach(
            var5x -> a(
                  var0,
                  var3,
                  var4,
                  var5x,
                  (var2xx, var3xx) -> this.a(var2, var3xx)
                        .ifLeft(
                           var1xxx -> a.error(
                                 "Couldn't load tag {} as it is missing following references: {}",
                                 var2xx,
                                 var1xxx.stream().map(Objects::toString).collect(Collectors.joining(", "))
                              )
                        )
                        .ifRight(var2xxx -> var1.put(var2xx, var2xxx))
               )
         );
      return var1;
   }

   public Map<MinecraftKey, Collection<T>> b(IResourceManager var0) {
      return this.a(this.a(var0));
   }

   public static record a(TagEntry entry, String source) {
      final TagEntry a;
      private final String b;

      public a(TagEntry var0, String var1) {
         this.a = var0;
         this.b = var1;
      }

      @Override
      public String toString() {
         return this.a + " (from " + this.b + ")";
      }
   }
}
