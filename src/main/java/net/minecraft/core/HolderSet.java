package net.minecraft.core;

import com.mojang.datafixers.util.Either;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.Function;
import java.util.stream.Stream;
import net.minecraft.SystemUtils;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.VisibleForTesting;

public interface HolderSet<T> extends Iterable<Holder<T>> {
   Stream<Holder<T>> a();

   int b();

   Either<TagKey<T>, List<Holder<T>>> c();

   Optional<Holder<T>> a(RandomSource var1);

   Holder<T> a(int var1);

   boolean a(Holder<T> var1);

   boolean a(HolderOwner<T> var1);

   Optional<TagKey<T>> d();

   @Deprecated
   @VisibleForTesting
   static <T> HolderSet.Named<T> a(HolderOwner<T> var0, TagKey<T> var1) {
      return new HolderSet.Named<>(var0, var1);
   }

   @SafeVarargs
   static <T> HolderSet.a<T> a(Holder<T>... var0) {
      return new HolderSet.a<>(List.of(var0));
   }

   static <T> HolderSet.a<T> a(List<? extends Holder<T>> var0) {
      return new HolderSet.a<>(List.copyOf(var0));
   }

   @SafeVarargs
   static <E, T> HolderSet.a<T> a(Function<E, Holder<T>> var0, E... var1) {
      return a(Stream.of(var1).map(var0).toList());
   }

   static <E, T> HolderSet.a<T> a(Function<E, Holder<T>> var0, List<E> var1) {
      return a(var1.stream().map(var0).toList());
   }

   public static class Named<T> extends HolderSet.b<T> {
      private final HolderOwner<T> a;
      private final TagKey<T> b;
      private List<Holder<T>> c = List.of();

      Named(HolderOwner<T> var0, TagKey<T> var1) {
         this.a = var0;
         this.b = var1;
      }

      void b(List<Holder<T>> var0) {
         this.c = List.copyOf(var0);
      }

      public TagKey<T> f() {
         return this.b;
      }

      @Override
      protected List<Holder<T>> e() {
         return this.c;
      }

      @Override
      public Either<TagKey<T>, List<Holder<T>>> c() {
         return Either.left(this.b);
      }

      @Override
      public Optional<TagKey<T>> d() {
         return Optional.of(this.b);
      }

      @Override
      public boolean a(Holder<T> var0) {
         return var0.a(this.b);
      }

      @Override
      public String toString() {
         return "NamedSet(" + this.b + ")[" + this.c + "]";
      }

      @Override
      public boolean a(HolderOwner<T> var0) {
         return this.a.a(var0);
      }
   }

   public static class a<T> extends HolderSet.b<T> {
      private final List<Holder<T>> a;
      @Nullable
      private Set<Holder<T>> b;

      a(List<Holder<T>> var0) {
         this.a = var0;
      }

      @Override
      protected List<Holder<T>> e() {
         return this.a;
      }

      @Override
      public Either<TagKey<T>, List<Holder<T>>> c() {
         return Either.right(this.a);
      }

      @Override
      public Optional<TagKey<T>> d() {
         return Optional.empty();
      }

      @Override
      public boolean a(Holder<T> var0) {
         if (this.b == null) {
            this.b = Set.copyOf(this.a);
         }

         return this.b.contains(var0);
      }

      @Override
      public String toString() {
         return "DirectSet[" + this.a + "]";
      }
   }

   public abstract static class b<T> implements HolderSet<T> {
      protected abstract List<Holder<T>> e();

      @Override
      public int b() {
         return this.e().size();
      }

      @Override
      public Spliterator<Holder<T>> spliterator() {
         return this.e().spliterator();
      }

      @Override
      public Iterator<Holder<T>> iterator() {
         return this.e().iterator();
      }

      @Override
      public Stream<Holder<T>> a() {
         return this.e().stream();
      }

      @Override
      public Optional<Holder<T>> a(RandomSource var0) {
         return SystemUtils.b(this.e(), var0);
      }

      @Override
      public Holder<T> a(int var0) {
         return this.e().get(var0);
      }

      @Override
      public boolean a(HolderOwner<T> var0) {
         return true;
      }
   }
}
