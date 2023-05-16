package net.minecraft.core;

import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Lifecycle;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenCustomHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.SystemUtils;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.DispenserRegistry;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;

public class RegistryMaterials<T> implements IRegistryWritable<T> {
   private static final Logger b = LogUtils.getLogger();
   final ResourceKey<? extends IRegistry<T>> c;
   private final ObjectList<Holder.c<T>> d = new ObjectArrayList(256);
   private final Object2IntMap<T> e = SystemUtils.a(new Object2IntOpenCustomHashMap(SystemUtils.k()), var0x -> var0x.defaultReturnValue(-1));
   private final Map<MinecraftKey, Holder.c<T>> f = new HashMap<>();
   private final Map<ResourceKey<T>, Holder.c<T>> g = new HashMap<>();
   private final Map<T, Holder.c<T>> h = new IdentityHashMap<>();
   private final Map<T, Lifecycle> i = new IdentityHashMap<>();
   private Lifecycle j;
   private volatile Map<TagKey<T>, HolderSet.Named<T>> k = new IdentityHashMap<>();
   private boolean l;
   @Nullable
   private Map<T, Holder.c<T>> m;
   @Nullable
   private List<Holder.c<T>> n;
   private int o;
   private final HolderLookup.c<T> p = new HolderLookup.c<T>() {
      @Override
      public ResourceKey<? extends IRegistry<? extends T>> f() {
         return RegistryMaterials.this.c;
      }

      @Override
      public Lifecycle g() {
         return RegistryMaterials.this.d();
      }

      @Override
      public Optional<Holder.c<T>> a(ResourceKey<T> var0) {
         return RegistryMaterials.this.b(var0);
      }

      @Override
      public Stream<Holder.c<T>> b() {
         return RegistryMaterials.this.h();
      }

      @Override
      public Optional<HolderSet.Named<T>> a(TagKey<T> var0) {
         return RegistryMaterials.this.b(var0);
      }

      @Override
      public Stream<HolderSet.Named<T>> d() {
         return RegistryMaterials.this.i().map(Pair::getSecond);
      }
   };

   public RegistryMaterials(ResourceKey<? extends IRegistry<T>> var0, Lifecycle var1) {
      this(var0, var1, false);
   }

   public RegistryMaterials(ResourceKey<? extends IRegistry<T>> var0, Lifecycle var1, boolean var2) {
      DispenserRegistry.a(() -> "registry " + var0);
      this.c = var0;
      this.j = var1;
      if (var2) {
         this.m = new IdentityHashMap<>();
      }
   }

   @Override
   public ResourceKey<? extends IRegistry<T>> c() {
      return this.c;
   }

   @Override
   public String toString() {
      return "Registry[" + this.c + " (" + this.j + ")]";
   }

   private List<Holder.c<T>> a() {
      if (this.n == null) {
         this.n = this.d.stream().filter(Objects::nonNull).toList();
      }

      return this.n;
   }

   private void v() {
      if (this.l) {
         throw new IllegalStateException("Registry is already frozen");
      }
   }

   private void g(ResourceKey<T> var0) {
      if (this.l) {
         throw new IllegalStateException("Registry is already frozen (trying to add key " + var0 + ")");
      }
   }

   public Holder.c<T> a(int var0, ResourceKey<T> var1, T var2, Lifecycle var3) {
      this.g(var1);
      Validate.notNull(var1);
      Validate.notNull(var2);
      if (this.f.containsKey(var1.a())) {
         SystemUtils.b(new IllegalStateException("Adding duplicate key '" + var1 + "' to registry"));
      }

      if (this.h.containsKey(var2)) {
         SystemUtils.b(new IllegalStateException("Adding duplicate value '" + var2 + "' to registry"));
      }

      Holder.c<T> var4;
      if (this.m != null) {
         var4 = this.m.remove(var2);
         if (var4 == null) {
            throw new AssertionError("Missing intrusive holder for " + var1 + ":" + var2);
         }

         var4.b(var1);
      } else {
         var4 = this.g.computeIfAbsent(var1, var0x -> Holder.c.a(this.o(), var0x));
      }

      this.g.put(var1, var4);
      this.f.put(var1.a(), var4);
      this.h.put(var2, var4);
      this.d.size(Math.max(this.d.size(), var0 + 1));
      this.d.set(var0, var4);
      this.e.put(var2, var0);
      if (this.o <= var0) {
         this.o = var0 + 1;
      }

      this.i.put(var2, var3);
      this.j = this.j.add(var3);
      this.n = null;
      return var4;
   }

   @Override
   public Holder.c<T> a(ResourceKey<T> var0, T var1, Lifecycle var2) {
      return this.a(this.o, var0, var1, var2);
   }

   @Nullable
   @Override
   public MinecraftKey b(T var0) {
      Holder.c<T> var1 = this.h.get(var0);
      return var1 != null ? var1.g().a() : null;
   }

   @Override
   public Optional<ResourceKey<T>> c(T var0) {
      return Optional.ofNullable(this.h.get(var0)).map(Holder.c::g);
   }

   @Override
   public int a(@Nullable T var0) {
      return this.e.getInt(var0);
   }

   @Nullable
   @Override
   public T a(@Nullable ResourceKey<T> var0) {
      return a(this.g.get(var0));
   }

   @Nullable
   @Override
   public T a(int var0) {
      return var0 >= 0 && var0 < this.d.size() ? a((Holder.c<T>)this.d.get(var0)) : null;
   }

   @Override
   public Optional<Holder.c<T>> c(int var0) {
      return var0 >= 0 && var0 < this.d.size() ? Optional.ofNullable((Holder.c<T>)this.d.get(var0)) : Optional.empty();
   }

   @Override
   public Optional<Holder.c<T>> b(ResourceKey<T> var0) {
      return Optional.ofNullable(this.g.get(var0));
   }

   @Override
   public Holder<T> d(T var0) {
      Holder.c<T> var1 = this.h.get(var0);
      return (Holder<T>)(var1 != null ? var1 : Holder.a(var0));
   }

   Holder.c<T> h(ResourceKey<T> var0) {
      return this.g.computeIfAbsent(var0, var0x -> {
         if (this.m != null) {
            throw new IllegalStateException("This registry can't create new holders without value");
         } else {
            this.g(var0x);
            return Holder.c.a(this.o(), var0x);
         }
      });
   }

   @Override
   public int b() {
      return this.g.size();
   }

   @Override
   public Lifecycle e(T var0) {
      return (Lifecycle)this.i.get(var0);
   }

   @Override
   public Lifecycle d() {
      return this.j;
   }

   @Override
   public Iterator<T> iterator() {
      return Iterators.transform(this.a().iterator(), Holder::a);
   }

   @Nullable
   @Override
   public T a(@Nullable MinecraftKey var0) {
      Holder.c<T> var1 = this.f.get(var0);
      return a(var1);
   }

   @Nullable
   private static <T> T a(@Nullable Holder.c<T> var0) {
      return var0 != null ? var0.a() : null;
   }

   @Override
   public Set<MinecraftKey> e() {
      return Collections.unmodifiableSet(this.f.keySet());
   }

   @Override
   public Set<ResourceKey<T>> f() {
      return Collections.unmodifiableSet(this.g.keySet());
   }

   @Override
   public Set<Entry<ResourceKey<T>, T>> g() {
      return Collections.unmodifiableSet(Maps.transformValues(this.g, Holder::a).entrySet());
   }

   @Override
   public Stream<Holder.c<T>> h() {
      return this.a().stream();
   }

   @Override
   public Stream<Pair<TagKey<T>, HolderSet.Named<T>>> i() {
      return this.k.entrySet().stream().map(var0 -> Pair.of((TagKey)var0.getKey(), (HolderSet.Named)var0.getValue()));
   }

   @Override
   public HolderSet.Named<T> a(TagKey<T> var0) {
      HolderSet.Named<T> var1 = this.k.get(var0);
      if (var1 == null) {
         var1 = this.d(var0);
         Map<TagKey<T>, HolderSet.Named<T>> var2 = new IdentityHashMap<>(this.k);
         var2.put(var0, var1);
         this.k = var2;
      }

      return var1;
   }

   private HolderSet.Named<T> d(TagKey<T> var0) {
      return new HolderSet.Named<>(this.o(), var0);
   }

   @Override
   public Stream<TagKey<T>> j() {
      return this.k.keySet().stream();
   }

   @Override
   public boolean k() {
      return this.g.isEmpty();
   }

   @Override
   public Optional<Holder.c<T>> a(RandomSource var0) {
      return SystemUtils.b(this.a(), var0);
   }

   @Override
   public boolean c(MinecraftKey var0) {
      return this.f.containsKey(var0);
   }

   @Override
   public boolean c(ResourceKey<T> var0) {
      return this.g.containsKey(var0);
   }

   @Override
   public IRegistry<T> l() {
      if (this.l) {
         return this;
      } else {
         this.l = true;
         this.h.forEach((var0x, var1x) -> var1x.b(var0x));
         List<MinecraftKey> var0 = this.g.entrySet().stream().filter(var0x -> !var0x.getValue().b()).map(var0x -> var0x.getKey().a()).sorted().toList();
         if (!var0.isEmpty()) {
            throw new IllegalStateException("Unbound values in registry " + this.c() + ": " + var0);
         } else {
            if (this.m != null) {
               if (!this.m.isEmpty()) {
                  throw new IllegalStateException("Some intrusive holders were not registered: " + this.m.values());
               }

               this.m = null;
            }

            return this;
         }
      }
   }

   @Override
   public Holder.c<T> f(T var0) {
      if (this.m == null) {
         throw new IllegalStateException("This registry can't create intrusive holders");
      } else {
         this.v();
         return this.m.computeIfAbsent(var0, var0x -> Holder.c.a(this.p(), var0x));
      }
   }

   @Override
   public Optional<HolderSet.Named<T>> b(TagKey<T> var0) {
      return Optional.ofNullable(this.k.get(var0));
   }

   @Override
   public void a(Map<TagKey<T>, List<Holder<T>>> var0) {
      Map<Holder.c<T>, List<TagKey<T>>> var1 = new IdentityHashMap<>();
      this.g.values().forEach(var1x -> var1.put(var1x, new ArrayList<>()));
      var0.forEach((var1x, var2x) -> {
         for(Holder<T> var4x : var2x) {
            if (!var4x.a(this.p())) {
               throw new IllegalStateException("Can't create named set " + var1x + " containing value " + var4x + " from outside registry " + this);
            }

            if (!(var4x instanceof Holder.c)) {
               throw new IllegalStateException("Found direct holder " + var4x + " value in tag " + var1x);
            }

            Holder.c<T> var5 = (Holder.c)var4x;
            var1.get(var5).add(var1x);
         }
      });
      Set<TagKey<T>> var2 = Sets.difference(this.k.keySet(), var0.keySet());
      if (!var2.isEmpty()) {
         b.warn(
            "Not all defined tags for registry {} are present in data pack: {}",
            this.c(),
            var2.stream().map(var0x -> var0x.b().toString()).sorted().collect(Collectors.joining(", "))
         );
      }

      Map<TagKey<T>, HolderSet.Named<T>> var3 = new IdentityHashMap<>(this.k);
      var0.forEach((var1x, var2x) -> var3.computeIfAbsent(var1x, this::d).b(var2x));
      var1.forEach(Holder.c::a);
      this.k = var3;
   }

   @Override
   public void m() {
      this.k.values().forEach(var0 -> var0.b(List.of()));
      this.g.values().forEach(var0 -> var0.a(Set.of()));
   }

   @Override
   public HolderGetter<T> n() {
      this.v();
      return new HolderGetter<T>() {
         @Override
         public Optional<Holder.c<T>> a(ResourceKey<T> var0) {
            return Optional.of(this.b(var0));
         }

         @Override
         public Holder.c<T> b(ResourceKey<T> var0) {
            return RegistryMaterials.this.h(var0);
         }

         @Override
         public Optional<HolderSet.Named<T>> a(TagKey<T> var0) {
            return Optional.of(this.b(var0));
         }

         @Override
         public HolderSet.Named<T> b(TagKey<T> var0) {
            return RegistryMaterials.this.a(var0);
         }
      };
   }

   @Override
   public HolderOwner<T> o() {
      return this.p;
   }

   @Override
   public HolderLookup.c<T> p() {
      return this.p;
   }
}
