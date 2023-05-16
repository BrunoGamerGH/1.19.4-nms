package net.minecraft.world.level.block.state;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSortedMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.UnmodifiableIterator;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Decoder;
import com.mojang.serialization.Encoder;
import com.mojang.serialization.MapCodec;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.world.level.block.state.properties.IBlockState;

public class BlockStateList<O, S extends IBlockDataHolder<O, S>> {
   static final Pattern a = Pattern.compile("^[a-z0-9_]+$");
   private final O b;
   private final ImmutableSortedMap<String, IBlockState<?>> c;
   private final ImmutableList<S> d;

   protected BlockStateList(Function<O, S> var0, O var1, BlockStateList.b<O, S> var2, Map<String, IBlockState<?>> var3) {
      this.b = var1;
      this.c = ImmutableSortedMap.copyOf(var3);
      Supplier<S> var4 = () -> var0.apply(var1);
      MapCodec<S> var5 = MapCodec.of(Encoder.empty(), Decoder.unit(var4));

      Entry<String, IBlockState<?>> var7;
      for(UnmodifiableIterator var6 = this.c.entrySet().iterator(); var6.hasNext(); var5 = a(var5, var4, var7.getKey(), var7.getValue())) {
         var7 = (Entry)var6.next();
      }

      MapCodec<S> var6 = var5;
      var7 = Maps.newLinkedHashMap();
      List<S> var8 = Lists.newArrayList();
      Stream<List<Pair<IBlockState<?>, Comparable<?>>>> var9 = Stream.of(Collections.emptyList());

      IBlockState<?> var11;
      for(UnmodifiableIterator var11 = this.c.values().iterator(); var11.hasNext(); var9 = var9.flatMap(var1x -> var11.a().stream().map(var2x -> {
            List<Pair<IBlockState<?>, Comparable<?>>> var3x = Lists.newArrayList(var1x);
            var3x.add(Pair.of(var11, var2x));
            return var3x;
         }))) {
         var11 = (IBlockState)var11.next();
      }

      var9.forEach(var5x -> {
         ImmutableMap<IBlockState<?>, Comparable<?>> var6x = var5x.stream().collect(ImmutableMap.toImmutableMap(Pair::getFirst, Pair::getSecond));
         S var7x = var2.create(var1, var6x, var6);
         var7.put(var6x, var7x);
         var8.add((S)var7x);
      });

      for(S var11 : var8) {
         var11.a(var7);
      }

      this.d = ImmutableList.copyOf(var8);
   }

   private static <S extends IBlockDataHolder<?, S>, T extends Comparable<T>> MapCodec<S> a(
      MapCodec<S> var0, Supplier<S> var1, String var2, IBlockState<T> var3
   ) {
      return Codec.mapPair(var0, var3.e().fieldOf(var2).orElseGet(var0x -> {
         }, () -> var3.a(var1.get())))
         .xmap(
            var1x -> (IBlockDataHolder)((IBlockDataHolder)var1x.getFirst()).a(var3, ((IBlockState.a)var1x.getSecond()).b()),
            var1x -> Pair.of(var1x, var3.a(var1x))
         );
   }

   public ImmutableList<S> a() {
      return this.d;
   }

   public S b() {
      return (S)this.d.get(0);
   }

   public O c() {
      return this.b;
   }

   public Collection<IBlockState<?>> d() {
      return this.c.values();
   }

   @Override
   public String toString() {
      return MoreObjects.toStringHelper(this)
         .add("block", this.b)
         .add("properties", this.c.values().stream().map(IBlockState::f).collect(Collectors.toList()))
         .toString();
   }

   @Nullable
   public IBlockState<?> a(String var0) {
      return (IBlockState<?>)this.c.get(var0);
   }

   public static class a<O, S extends IBlockDataHolder<O, S>> {
      private final O a;
      private final Map<String, IBlockState<?>> b = Maps.newHashMap();

      public a(O var0) {
         this.a = var0;
      }

      public BlockStateList.a<O, S> a(IBlockState<?>... var0) {
         for(IBlockState<?> var4 : var0) {
            this.a(var4);
            this.b.put(var4.f(), var4);
         }

         return this;
      }

      private <T extends Comparable<T>> void a(IBlockState<T> var0) {
         String var1 = var0.f();
         if (!BlockStateList.a.matcher(var1).matches()) {
            throw new IllegalArgumentException(this.a + " has invalidly named property: " + var1);
         } else {
            Collection<T> var2 = var0.a();
            if (var2.size() <= 1) {
               throw new IllegalArgumentException(this.a + " attempted use property " + var1 + " with <= 1 possible values");
            } else {
               for(T var4 : var2) {
                  String var5 = var0.a(var4);
                  if (!BlockStateList.a.matcher(var5).matches()) {
                     throw new IllegalArgumentException(this.a + " has property: " + var1 + " with invalidly named value: " + var5);
                  }
               }

               if (this.b.containsKey(var1)) {
                  throw new IllegalArgumentException(this.a + " has duplicate property: " + var1);
               }
            }
         }
      }

      public BlockStateList<O, S> a(Function<O, S> var0, BlockStateList.b<O, S> var1) {
         return new BlockStateList<>(var0, this.a, var1, this.b);
      }
   }

   public interface b<O, S> {
      S create(O var1, ImmutableMap<IBlockState<?>, Comparable<?>> var2, MapCodec<S> var3);
   }
}
