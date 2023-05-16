package net.minecraft.core;

import com.mojang.datafixers.util.Either;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;

public interface Holder<T> {
   T a();

   boolean b();

   boolean a(MinecraftKey var1);

   boolean a(ResourceKey<T> var1);

   boolean a(Predicate<ResourceKey<T>> var1);

   boolean a(TagKey<T> var1);

   Stream<TagKey<T>> c();

   Either<ResourceKey<T>, T> d();

   Optional<ResourceKey<T>> e();

   Holder.b f();

   boolean a(HolderOwner<T> var1);

   static <T> Holder<T> a(T var0) {
      return new Holder.a<>(var0);
   }

   public static record a<T>(T value) implements Holder<T> {
      private final T a;

      public a(T var0) {
         this.a = var0;
      }

      @Override
      public boolean b() {
         return true;
      }

      @Override
      public boolean a(MinecraftKey var0) {
         return false;
      }

      @Override
      public boolean a(ResourceKey<T> var0) {
         return false;
      }

      @Override
      public boolean a(TagKey<T> var0) {
         return false;
      }

      @Override
      public boolean a(Predicate<ResourceKey<T>> var0) {
         return false;
      }

      @Override
      public Either<ResourceKey<T>, T> d() {
         return Either.right(this.a);
      }

      @Override
      public Optional<ResourceKey<T>> e() {
         return Optional.empty();
      }

      @Override
      public Holder.b f() {
         return Holder.b.b;
      }

      @Override
      public String toString() {
         return "Direct{" + this.a + "}";
      }

      @Override
      public boolean a(HolderOwner<T> var0) {
         return true;
      }

      @Override
      public Stream<TagKey<T>> c() {
         return Stream.of();
      }
   }

   public static enum b {
      a,
      b;
   }

   public static class c<T> implements Holder<T> {
      private final HolderOwner<T> a;
      private Set<TagKey<T>> b = Set.of();
      private final Holder.c.a c;
      @Nullable
      private ResourceKey<T> d;
      @Nullable
      private T e;

      private c(Holder.c.a var0, HolderOwner<T> var1, @Nullable ResourceKey<T> var2, @Nullable T var3) {
         this.a = var1;
         this.c = var0;
         this.d = var2;
         this.e = var3;
      }

      public static <T> Holder.c<T> a(HolderOwner<T> var0, ResourceKey<T> var1) {
         return new Holder.c<>(Holder.c.a.a, var0, var1, (T)null);
      }

      @Deprecated
      public static <T> Holder.c<T> a(HolderOwner<T> var0, @Nullable T var1) {
         return new Holder.c<>(Holder.c.a.b, var0, null, var1);
      }

      public ResourceKey<T> g() {
         if (this.d == null) {
            throw new IllegalStateException("Trying to access unbound value '" + this.e + "' from registry " + this.a);
         } else {
            return this.d;
         }
      }

      @Override
      public T a() {
         if (this.e == null) {
            throw new IllegalStateException("Trying to access unbound value '" + this.d + "' from registry " + this.a);
         } else {
            return this.e;
         }
      }

      @Override
      public boolean a(MinecraftKey var0) {
         return this.g().a().equals(var0);
      }

      @Override
      public boolean a(ResourceKey<T> var0) {
         return this.g() == var0;
      }

      @Override
      public boolean a(TagKey<T> var0) {
         return this.b.contains(var0);
      }

      @Override
      public boolean a(Predicate<ResourceKey<T>> var0) {
         return var0.test(this.g());
      }

      @Override
      public boolean a(HolderOwner<T> var0) {
         return this.a.a(var0);
      }

      @Override
      public Either<ResourceKey<T>, T> d() {
         return Either.left(this.g());
      }

      @Override
      public Optional<ResourceKey<T>> e() {
         return Optional.of(this.g());
      }

      @Override
      public Holder.b f() {
         return Holder.b.a;
      }

      @Override
      public boolean b() {
         return this.d != null && this.e != null;
      }

      void b(ResourceKey<T> var0) {
         if (this.d != null && var0 != this.d) {
            throw new IllegalStateException("Can't change holder key: existing=" + this.d + ", new=" + var0);
         } else {
            this.d = var0;
         }
      }

      void b(T var0) {
         if (this.c == Holder.c.a.b && this.e != var0) {
            throw new IllegalStateException("Can't change holder " + this.d + " value: existing=" + this.e + ", new=" + var0);
         } else {
            this.e = var0;
         }
      }

      void a(Collection<TagKey<T>> var0) {
         this.b = Set.copyOf(var0);
      }

      @Override
      public Stream<TagKey<T>> c() {
         return this.b.stream();
      }

      @Override
      public String toString() {
         return "Reference{" + this.d + "=" + this.e + "}";
      }

      static enum a {
         a,
         b;
      }
   }
}
