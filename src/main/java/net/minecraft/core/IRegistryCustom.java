package net.minecraft.core;

import com.google.common.collect.ImmutableMap;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Lifecycle;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.resources.ResourceKey;
import org.slf4j.Logger;

public interface IRegistryCustom extends HolderLookup.b {
   Logger a = LogUtils.getLogger();
   IRegistryCustom.Dimension b = new IRegistryCustom.c(Map.of()).c();

   <E> Optional<IRegistry<E>> c(ResourceKey<? extends IRegistry<? extends E>> var1);

   @Override
   default <T> Optional<HolderLookup.c<T>> a(ResourceKey<? extends IRegistry<? extends T>> var0) {
      return this.c(var0).map(IRegistry::p);
   }

   default <E> IRegistry<E> d(ResourceKey<? extends IRegistry<? extends E>> var0) {
      return this.c(var0).orElseThrow(() -> new IllegalStateException("Missing registry: " + var0));
   }

   Stream<IRegistryCustom.d<?>> b();

   static IRegistryCustom.Dimension a(final IRegistry<? extends IRegistry<?>> var0) {
      return new IRegistryCustom.Dimension() {
         @Override
         public <T> Optional<IRegistry<T>> c(ResourceKey<? extends IRegistry<? extends T>> var0x) {
            IRegistry<IRegistry<T>> var1 = var0;
            return var1.d(var0);
         }

         @Override
         public Stream<IRegistryCustom.d<?>> b() {
            return var0.g().stream().map(IRegistryCustom.d::a);
         }

         @Override
         public IRegistryCustom.Dimension c() {
            return this;
         }
      };
   }

   default IRegistryCustom.Dimension c() {
      class a extends IRegistryCustom.c implements IRegistryCustom.Dimension {
         protected a(Stream var1) {
            super(var1);
         }
      }

      return new a(this.b().map(IRegistryCustom.d::c));
   }

   default Lifecycle d() {
      return (Lifecycle)this.b().map(var0 -> var0.b.d()).reduce(Lifecycle.stable(), Lifecycle::add);
   }

   public interface Dimension extends IRegistryCustom {
   }

   public static class c implements IRegistryCustom {
      private final Map<? extends ResourceKey<? extends IRegistry<?>>, ? extends IRegistry<?>> c;

      public c(List<? extends IRegistry<?>> var0) {
         this.c = var0.stream().collect(Collectors.toUnmodifiableMap(IRegistry::c, (Function<? super IRegistry, ? extends IRegistry>)(var0x -> var0x)));
      }

      public c(Map<? extends ResourceKey<? extends IRegistry<?>>, ? extends IRegistry<?>> var0) {
         this.c = Map.copyOf(var0);
      }

      public c(Stream<IRegistryCustom.d<?>> var0) {
         this.c = var0.collect(ImmutableMap.toImmutableMap(IRegistryCustom.d::a, IRegistryCustom.d::b));
      }

      @Override
      public <E> Optional<IRegistry<E>> c(ResourceKey<? extends IRegistry<? extends E>> var0) {
         return Optional.ofNullable(this.c.get(var0)).map((Function<? super IRegistry<?>, ? extends IRegistry<E>>)(var0x -> var0x));
      }

      @Override
      public Stream<IRegistryCustom.d<?>> b() {
         return this.c.entrySet().stream().map(IRegistryCustom.d::a);
      }
   }

   public static record d<T>(ResourceKey<? extends IRegistry<T>> key, IRegistry<T> value) {
      private final ResourceKey<? extends IRegistry<T>> a;
      final IRegistry<T> b;

      public d(ResourceKey<? extends IRegistry<T>> var0, IRegistry<T> var1) {
         this.a = var0;
         this.b = var1;
      }

      private static <T, R extends IRegistry<? extends T>> IRegistryCustom.d<T> a(Entry<? extends ResourceKey<? extends IRegistry<?>>, R> var0) {
         return a(var0.getKey(), var0.getValue());
      }

      private static <T> IRegistryCustom.d<T> a(ResourceKey<? extends IRegistry<?>> var0, IRegistry<?> var1) {
         return new IRegistryCustom.d<>(var0, var1);
      }

      private IRegistryCustom.d<T> c() {
         return new IRegistryCustom.d<>(this.a, this.b.l());
      }
   }
}
