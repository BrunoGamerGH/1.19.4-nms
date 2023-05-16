package net.minecraft.core;

import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Keyable;
import com.mojang.serialization.Lifecycle;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Map.Entry;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.annotation.Nullable;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.RandomSource;

public interface IRegistry<T> extends Keyable, Registry<T> {
   ResourceKey<? extends IRegistry<T>> c();

   default Codec<T> q() {
      Codec<T> var0 = MinecraftKey.a
         .flatXmap(
            var0x -> (DataResult)Optional.ofNullable(this.a(var0x))
                  .map(DataResult::success)
                  .orElseGet(() -> (T)DataResult.error(() -> "Unknown registry key in " + this.c() + ": " + var0x)),
            var0x -> (DataResult)this.c((T)var0x)
                  .map(ResourceKey::a)
                  .map(DataResult::success)
                  .orElseGet(() -> (T)DataResult.error(() -> "Unknown registry element in " + this.c() + ":" + var0x))
         );
      Codec<T> var1 = ExtraCodecs.a(var0x -> this.c(var0x).isPresent() ? this.a(var0x) : -1, this::a, -1);
      return ExtraCodecs.a(ExtraCodecs.b(var0, var1), this::e, this::e);
   }

   default Codec<Holder<T>> r() {
      Codec<Holder<T>> var0 = MinecraftKey.a
         .flatXmap(
            var0x -> (DataResult)this.b(ResourceKey.a(this.c(), var0x))
                  .map(DataResult::success)
                  .orElseGet(() -> (T)DataResult.error(() -> "Unknown registry key in " + this.c() + ": " + var0x)),
            var0x -> (DataResult)var0x.e()
                  .map(ResourceKey::a)
                  .map(DataResult::success)
                  .orElseGet(() -> (T)DataResult.error(() -> "Unknown registry element in " + this.c() + ":" + var0x))
         );
      return ExtraCodecs.a(var0, var0x -> this.e(var0x.a()), var0x -> this.e(var0x.a()));
   }

   default <U> Stream<U> keys(DynamicOps<U> var0) {
      return this.e().stream().map(var1x -> (U)var0.createString(var1x.toString()));
   }

   @Nullable
   MinecraftKey b(T var1);

   Optional<ResourceKey<T>> c(T var1);

   @Override
   int a(@Nullable T var1);

   @Nullable
   T a(@Nullable ResourceKey<T> var1);

   @Nullable
   T a(@Nullable MinecraftKey var1);

   Lifecycle e(T var1);

   Lifecycle d();

   default Optional<T> b(@Nullable MinecraftKey var0) {
      return Optional.ofNullable(this.a(var0));
   }

   default Optional<T> d(@Nullable ResourceKey<T> var0) {
      return Optional.ofNullable(this.a(var0));
   }

   default T e(ResourceKey<T> var0) {
      T var1 = this.a(var0);
      if (var1 == null) {
         throw new IllegalStateException("Missing key in " + this.c() + ": " + var0);
      } else {
         return var1;
      }
   }

   Set<MinecraftKey> e();

   Set<Entry<ResourceKey<T>, T>> g();

   Set<ResourceKey<T>> f();

   Optional<Holder.c<T>> a(RandomSource var1);

   default Stream<T> s() {
      return StreamSupport.stream(this.spliterator(), false);
   }

   boolean c(MinecraftKey var1);

   boolean c(ResourceKey<T> var1);

   static <T> T a(IRegistry<? super T> var0, String var1, T var2) {
      return a(var0, new MinecraftKey(var1), var2);
   }

   static <V, T extends V> T a(IRegistry<V> var0, MinecraftKey var1, T var2) {
      return a(var0, ResourceKey.a(var0.c(), var1), var2);
   }

   static <V, T extends V> T a(IRegistry<V> var0, ResourceKey<V> var1, T var2) {
      ((IRegistryWritable)var0).a(var1, (V)var2, Lifecycle.stable());
      return var2;
   }

   static <T> Holder.c<T> b(IRegistry<T> var0, ResourceKey<T> var1, T var2) {
      return ((IRegistryWritable)var0).a(var1, var2, Lifecycle.stable());
   }

   static <T> Holder.c<T> b(IRegistry<T> var0, MinecraftKey var1, T var2) {
      return b(var0, ResourceKey.a(var0.c(), var1), var2);
   }

   static <V, T extends V> T a(IRegistry<V> var0, int var1, String var2, T var3) {
      ((IRegistryWritable)var0).b(var1, ResourceKey.a(var0.c(), new MinecraftKey(var2)), (V)var3, Lifecycle.stable());
      return var3;
   }

   IRegistry<T> l();

   Holder.c<T> f(T var1);

   Optional<Holder.c<T>> c(int var1);

   Optional<Holder.c<T>> b(ResourceKey<T> var1);

   Holder<T> d(T var1);

   default Holder.c<T> f(ResourceKey<T> var0) {
      return this.b(var0).orElseThrow(() -> new IllegalStateException("Missing key in " + this.c() + ": " + var0));
   }

   Stream<Holder.c<T>> h();

   Optional<HolderSet.Named<T>> b(TagKey<T> var1);

   default Iterable<Holder<T>> c(TagKey<T> var0) {
      return (Iterable<Holder<T>>)DataFixUtils.orElse(this.b(var0), List.of());
   }

   HolderSet.Named<T> a(TagKey<T> var1);

   Stream<Pair<TagKey<T>, HolderSet.Named<T>>> i();

   Stream<TagKey<T>> j();

   void m();

   void a(Map<TagKey<T>, List<Holder<T>>> var1);

   default Registry<Holder<T>> t() {
      return new Registry<Holder<T>>() {
         public int a(Holder<T> var0) {
            return IRegistry.this.a(var0.a());
         }

         @Nullable
         public Holder<T> c(int var0) {
            return (Holder<T>)IRegistry.this.c(var0).orElse((T)null);
         }

         @Override
         public int b() {
            return IRegistry.this.b();
         }

         @Override
         public Iterator<Holder<T>> iterator() {
            return IRegistry.this.h().map(var0 -> var0).iterator();
         }
      };
   }

   HolderOwner<T> o();

   HolderLookup.c<T> p();

   default HolderLookup.c<T> u() {
      return new HolderLookup.c.a<T>() {
         @Override
         protected HolderLookup.c<T> a() {
            return IRegistry.this.p();
         }

         @Override
         public Optional<HolderSet.Named<T>> a(TagKey<T> var0) {
            return Optional.of(this.b(var0));
         }

         @Override
         public HolderSet.Named<T> b(TagKey<T> var0) {
            return IRegistry.this.a(var0);
         }
      };
   }
}
