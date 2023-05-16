package net.minecraft.core;

import com.mojang.serialization.Lifecycle;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.flag.FeatureElement;
import net.minecraft.world.flag.FeatureFlagSet;

public interface HolderLookup<T> extends HolderGetter<T> {
   Stream<Holder.c<T>> b();

   default Stream<ResourceKey<T>> c() {
      return this.b().map(Holder.c::g);
   }

   Stream<HolderSet.Named<T>> d();

   default Stream<TagKey<T>> e() {
      return this.d().map(HolderSet.Named::f);
   }

   default HolderLookup<T> a(final Predicate<T> var0) {
      return new HolderLookup.a<T>(this) {
         @Override
         public Optional<Holder.c<T>> a(ResourceKey<T> var0x) {
            return this.c.a(var0).filter(var1x -> var0.test(var1x.a()));
         }

         @Override
         public Stream<Holder.c<T>> b() {
            return this.c.b().filter(var1 -> var0.test(var1.a()));
         }
      };
   }

   public static class a<T> implements HolderLookup<T> {
      protected final HolderLookup<T> c;

      public a(HolderLookup<T> var0) {
         this.c = var0;
      }

      @Override
      public Optional<Holder.c<T>> a(ResourceKey<T> var0) {
         return this.c.a(var0);
      }

      @Override
      public Stream<Holder.c<T>> b() {
         return this.c.b();
      }

      @Override
      public Optional<HolderSet.Named<T>> a(TagKey<T> var0) {
         return this.c.a(var0);
      }

      @Override
      public Stream<HolderSet.Named<T>> d() {
         return this.c.d();
      }
   }

   public interface b {
      <T> Optional<HolderLookup.c<T>> a(ResourceKey<? extends IRegistry<? extends T>> var1);

      default <T> HolderLookup.c<T> b(ResourceKey<? extends IRegistry<? extends T>> var0) {
         return this.<T>a(var0).orElseThrow(() -> new IllegalStateException("Registry " + var0.a() + " not found"));
      }

      default HolderGetter.a a() {
         return new HolderGetter.a() {
            @Override
            public <T> Optional<HolderGetter<T>> a(ResourceKey<? extends IRegistry<? extends T>> var0) {
               return b.this.a(var0).map(var0x -> var0x);
            }
         };
      }

      static HolderLookup.b a(Stream<HolderLookup.c<?>> var0) {
         final Map<ResourceKey<? extends IRegistry<?>>, HolderLookup.c<?>> var1 = var0.collect(
            Collectors.toUnmodifiableMap(HolderLookup.c::f, (Function<? super HolderLookup.c, ? extends HolderLookup.c>)(var0x -> var0x))
         );
         return new HolderLookup.b() {
            @Override
            public <T> Optional<HolderLookup.c<T>> a(ResourceKey<? extends IRegistry<? extends T>> var0) {
               return Optional.ofNullable((HolderLookup.c<T>)var1.get(var0));
            }
         };
      }
   }

   public interface c<T> extends HolderLookup<T>, HolderOwner<T> {
      ResourceKey<? extends IRegistry<? extends T>> f();

      Lifecycle g();

      default HolderLookup<T> a(FeatureFlagSet var0) {
         return (HolderLookup<T>)(FeatureElement.bv.contains(this.f()) ? this.a(var1x -> ((FeatureElement)var1x).a(var0)) : this);
      }

      public abstract static class a<T> implements HolderLookup.c<T> {
         protected abstract HolderLookup.c<T> a();

         @Override
         public ResourceKey<? extends IRegistry<? extends T>> f() {
            return this.a().f();
         }

         @Override
         public Lifecycle g() {
            return this.a().g();
         }

         @Override
         public Optional<Holder.c<T>> a(ResourceKey<T> var0) {
            return this.a().a(var0);
         }

         @Override
         public Stream<Holder.c<T>> b() {
            return this.a().b();
         }

         @Override
         public Optional<HolderSet.Named<T>> a(TagKey<T> var0) {
            return this.a().a(var0);
         }

         @Override
         public Stream<HolderSet.Named<T>> d() {
            return this.a().d();
         }
      }
   }
}
