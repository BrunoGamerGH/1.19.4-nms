package net.minecraft.core;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import com.google.common.collect.ImmutableMap.Builder;
import com.mojang.serialization.Lifecycle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;

public class RegistrySetBuilder {
   private final List<RegistrySetBuilder.g<?>> a = new ArrayList<>();

   static <T> HolderGetter<T> a(final HolderLookup.c<T> var0) {
      return new RegistrySetBuilder.c<T>(var0) {
         @Override
         public Optional<Holder.c<T>> a(ResourceKey<T> var0x) {
            return var0.a(var0);
         }
      };
   }

   public <T> RegistrySetBuilder a(ResourceKey<? extends IRegistry<T>> var0, Lifecycle var1, RegistrySetBuilder.e<T> var2) {
      this.a.add(new RegistrySetBuilder.g<>(var0, var1, var2));
      return this;
   }

   public <T> RegistrySetBuilder a(ResourceKey<? extends IRegistry<T>> var0, RegistrySetBuilder.e<T> var1) {
      return this.a(var0, Lifecycle.stable(), var1);
   }

   private RegistrySetBuilder.a b(IRegistryCustom var0) {
      RegistrySetBuilder.a var1 = RegistrySetBuilder.a.a(var0, this.a.stream().map(RegistrySetBuilder.g::a));
      this.a.forEach(var1x -> var1x.b(var1));
      return var1;
   }

   public HolderLookup.b a(IRegistryCustom var0) {
      RegistrySetBuilder.a var1 = this.b(var0);
      Stream<HolderLookup.c<?>> var2 = var0.b().map(var0x -> var0x.b().p());
      Stream<HolderLookup.c<?>> var3 = this.a.stream().map(var1x -> var1x.a(var1).a());
      HolderLookup.b var4 = HolderLookup.b.a(Stream.concat(var2, var3.peek(var1::a)));
      var1.b();
      var1.c();
      return var4;
   }

   public HolderLookup.b a(IRegistryCustom var0, HolderLookup.b var1) {
      RegistrySetBuilder.a var2 = this.b(var0);
      Map<ResourceKey<? extends IRegistry<?>>, RegistrySetBuilder.f<?>> var3 = new HashMap<>();
      var2.d().forEach(var1x -> var3.put(var1x.a, var1x));
      this.a.stream().map(var1x -> var1x.a(var2)).forEach(var1x -> var3.put(var1x.a, var1x));
      Stream<HolderLookup.c<?>> var4 = var0.b().map(var0x -> var0x.b().p());
      HolderLookup.b var5 = HolderLookup.b.a(Stream.concat(var4, var3.values().stream().map(RegistrySetBuilder.f::a).peek(var2::a)));
      var2.a(var1);
      var2.b();
      var2.c();
      return var5;
   }

   static record a(
      RegistrySetBuilder.b owner,
      RegistrySetBuilder.h lookup,
      Map<MinecraftKey, HolderGetter<?>> registries,
      Map<ResourceKey<?>, RegistrySetBuilder.d<?>> registeredValues,
      List<RuntimeException> errors
   ) {
      private final RegistrySetBuilder.b a;
      final RegistrySetBuilder.h b;
      final Map<MinecraftKey, HolderGetter<?>> c;
      final Map<ResourceKey<?>, RegistrySetBuilder.d<?>> d;
      final List<RuntimeException> e;

      private a(
         RegistrySetBuilder.b var0,
         RegistrySetBuilder.h var1,
         Map<MinecraftKey, HolderGetter<?>> var2,
         Map<ResourceKey<?>, RegistrySetBuilder.d<?>> var3,
         List<RuntimeException> var4
      ) {
         this.a = var0;
         this.b = var1;
         this.c = var2;
         this.d = var3;
         this.e = var4;
      }

      public static RegistrySetBuilder.a a(IRegistryCustom var0, Stream<ResourceKey<? extends IRegistry<?>>> var1) {
         RegistrySetBuilder.b var2 = new RegistrySetBuilder.b();
         List<RuntimeException> var3 = new ArrayList<>();
         RegistrySetBuilder.h var4 = new RegistrySetBuilder.h(var2);
         Builder<MinecraftKey, HolderGetter<?>> var5 = ImmutableMap.builder();
         var0.b().forEach(var1x -> var5.put(var1x.a().a(), RegistrySetBuilder.a(var1x.b().p())));
         var1.forEach(var2x -> var5.put(var2x.a(), var4));
         return new RegistrySetBuilder.a(var2, var4, var5.build(), new HashMap<>(), var3);
      }

      public <T> BootstapContext<T> a() {
         return new BootstapContext<T>() {
            @Override
            public Holder.c<T> a(ResourceKey<T> var0, T var1, Lifecycle var2) {
               RegistrySetBuilder.d<?> var3 = a.this.d.put(var0, new RegistrySetBuilder.d(var1, var2));
               if (var3 != null) {
                  a.this.e.add(new IllegalStateException("Duplicate registration for " + var0 + ", new=" + var1 + ", old=" + var3.a));
               }

               return a.this.b.c(var0);
            }

            @Override
            public <S> HolderGetter<S> a(ResourceKey<? extends IRegistry<? extends S>> var0) {
               return (HolderGetter<S>)a.this.c.getOrDefault(var0.a(), a.this.b);
            }
         };
      }

      public void b() {
         for(ResourceKey<Object> var1 : this.b.a.keySet()) {
            this.e.add(new IllegalStateException("Unreferenced key: " + var1));
         }

         this.d.forEach((var0, var1x) -> this.e.add(new IllegalStateException("Orpaned value " + var1x.a + " for key " + var0)));
      }

      public void c() {
         if (!this.e.isEmpty()) {
            IllegalStateException var0 = new IllegalStateException("Errors during registry creation");

            for(RuntimeException var2 : this.e) {
               var0.addSuppressed(var2);
            }

            throw var0;
         }
      }

      public void a(HolderOwner<?> var0) {
         this.a.b(var0);
      }

      public void a(HolderLookup.b var0) {
         Map<MinecraftKey, Optional<? extends HolderLookup<Object>>> var1 = new HashMap<>();
         Iterator<Entry<ResourceKey<Object>, Holder.c<Object>>> var2 = this.b.a.entrySet().iterator();

         while(var2.hasNext()) {
            Entry<ResourceKey<Object>, Holder.c<Object>> var3 = var2.next();
            ResourceKey<Object> var4 = var3.getKey();
            Holder.c<Object> var5 = var3.getValue();
            var1.computeIfAbsent(var4.b(), var1x -> var0.a(ResourceKey.a(var1x))).flatMap(var1x -> var1x.a(var4)).ifPresent(var2x -> {
               var5.b(var2x.a());
               var2.remove();
            });
         }
      }

      public Stream<RegistrySetBuilder.f<?>> d() {
         return this.b
            .a
            .keySet()
            .stream()
            .map(ResourceKey::b)
            .distinct()
            .map(var0 -> new RegistrySetBuilder.f(ResourceKey.a(var0), Lifecycle.stable(), Map.of()));
      }

      public RegistrySetBuilder.b e() {
         return this.a;
      }

      public RegistrySetBuilder.h f() {
         return this.b;
      }

      public Map<MinecraftKey, HolderGetter<?>> g() {
         return this.c;
      }

      public Map<ResourceKey<?>, RegistrySetBuilder.d<?>> h() {
         return this.d;
      }

      public List<RuntimeException> i() {
         return this.e;
      }
   }

   static class b implements HolderOwner<Object> {
      private final Set<HolderOwner<?>> a = Sets.newIdentityHashSet();

      @Override
      public boolean a(HolderOwner<Object> var0) {
         return this.a.contains(var0);
      }

      public void b(HolderOwner<?> var0) {
         this.a.add(var0);
      }
   }

   abstract static class c<T> implements HolderGetter<T> {
      protected final HolderOwner<T> b;

      protected c(HolderOwner<T> var0) {
         this.b = var0;
      }

      @Override
      public Optional<HolderSet.Named<T>> a(TagKey<T> var0) {
         return Optional.of(HolderSet.a(this.b, var0));
      }
   }

   static record d<T>(T value, Lifecycle lifecycle) {
      final T a;
      private final Lifecycle b;

      d(T var0, Lifecycle var1) {
         this.a = var0;
         this.b = var1;
      }
   }

   @FunctionalInterface
   public interface e<T> {
      void run(BootstapContext<T> var1);
   }

   static record f<T>(ResourceKey<? extends IRegistry<? extends T>> key, Lifecycle lifecycle, Map<ResourceKey<T>, RegistrySetBuilder.i<T>> values) {
      final ResourceKey<? extends IRegistry<? extends T>> a;
      final Lifecycle b;
      final Map<ResourceKey<T>, RegistrySetBuilder.i<T>> c;

      f(ResourceKey<? extends IRegistry<? extends T>> var0, Lifecycle var1, Map<ResourceKey<T>, RegistrySetBuilder.i<T>> var2) {
         this.a = var0;
         this.b = var1;
         this.c = var2;
      }

      public HolderLookup.c<T> a() {
         return new HolderLookup.c<T>() {
            private final Map<ResourceKey<T>, Holder.c<T>> b = f.this.c.entrySet().stream().collect(Collectors.toUnmodifiableMap(Entry::getKey, var0x -> {
               RegistrySetBuilder.i<T> var1x = (RegistrySetBuilder.i)var0x.getValue();
               Holder.c<T> var2 = var1x.b().orElseGet(() -> Holder.c.a(this, (ResourceKey<T>)var0x.getKey()));
               var2.b((T)var1x.a().a());
               return var2;
            }));

            @Override
            public ResourceKey<? extends IRegistry<? extends T>> f() {
               return f.this.a;
            }

            @Override
            public Lifecycle g() {
               return f.this.b;
            }

            @Override
            public Optional<Holder.c<T>> a(ResourceKey<T> var0) {
               return Optional.ofNullable(this.b.get(var0));
            }

            @Override
            public Stream<Holder.c<T>> b() {
               return this.b.values().stream();
            }

            @Override
            public Optional<HolderSet.Named<T>> a(TagKey<T> var0) {
               return Optional.empty();
            }

            @Override
            public Stream<HolderSet.Named<T>> d() {
               return Stream.empty();
            }
         };
      }

      public ResourceKey<? extends IRegistry<? extends T>> b() {
         return this.a;
      }

      public Lifecycle c() {
         return this.b;
      }

      public Map<ResourceKey<T>, RegistrySetBuilder.i<T>> d() {
         return this.c;
      }
   }

   static record g<T>(ResourceKey<? extends IRegistry<T>> key, Lifecycle lifecycle, RegistrySetBuilder.e<T> bootstrap) {
      private final ResourceKey<? extends IRegistry<T>> a;
      private final Lifecycle b;
      private final RegistrySetBuilder.e<T> c;

      g(ResourceKey<? extends IRegistry<T>> var0, Lifecycle var1, RegistrySetBuilder.e<T> var2) {
         this.a = var0;
         this.b = var1;
         this.c = var2;
      }

      void b(RegistrySetBuilder.a var0) {
         this.c.run(var0.a());
      }

      public RegistrySetBuilder.f<T> a(RegistrySetBuilder.a var0) {
         Map<ResourceKey<T>, RegistrySetBuilder.i<T>> var1 = new HashMap<>();
         Iterator<Entry<ResourceKey<?>, RegistrySetBuilder.d<?>>> var2 = var0.d.entrySet().iterator();

         while(var2.hasNext()) {
            Entry<ResourceKey<?>, RegistrySetBuilder.d<?>> var3 = var2.next();
            ResourceKey<?> var4 = var3.getKey();
            if (var4.b(this.a)) {
               RegistrySetBuilder.d<T> var6 = (RegistrySetBuilder.d)var3.getValue();
               Holder.c<T> var7 = var0.b.a.remove(var4);
               var1.put(var4, new RegistrySetBuilder.i<>(var6, Optional.ofNullable(var7)));
               var2.remove();
            }
         }

         return new RegistrySetBuilder.f<>(this.a, this.b, var1);
      }
   }

   static class h extends RegistrySetBuilder.c<Object> {
      final Map<ResourceKey<Object>, Holder.c<Object>> a = new HashMap<>();

      public h(HolderOwner<Object> var0) {
         super(var0);
      }

      @Override
      public Optional<Holder.c<Object>> a(ResourceKey<Object> var0) {
         return Optional.of(this.c(var0));
      }

      <T> Holder.c<T> c(ResourceKey<T> var0) {
         return this.a.computeIfAbsent(var0, var0x -> Holder.c.a(this.b, var0x));
      }
   }

   static record i<T>(RegistrySetBuilder.d<T> value, Optional<Holder.c<T>> holder) {
      private final RegistrySetBuilder.d<T> a;
      private final Optional<Holder.c<T>> b;

      i(RegistrySetBuilder.d<T> var0, Optional<Holder.c<T>> var1) {
         this.a = var0;
         this.b = var1;
      }
   }
}
