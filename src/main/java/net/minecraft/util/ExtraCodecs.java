package net.minecraft.util;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableList;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Decoder;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.MapLike;
import com.mojang.serialization.RecordBuilder;
import com.mojang.serialization.Codec.ResultFunction;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.floats.FloatArrayList;
import it.unimi.dsi.fastutil.floats.FloatList;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Base64;
import java.util.BitSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Stream;
import net.minecraft.SystemUtils;
import net.minecraft.core.HolderSet;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.resources.MinecraftKey;
import org.apache.commons.lang3.mutable.MutableObject;
import org.joml.AxisAngle4f;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class ExtraCodecs {
   public static final Codec<JsonElement> a = Codec.PASSTHROUGH
      .xmap(var0 -> (JsonElement)var0.convert(JsonOps.INSTANCE).getValue(), var0 -> new Dynamic(JsonOps.INSTANCE, var0));
   public static final Codec<IChatBaseComponent> b = a.flatXmap(var0 -> {
      try {
         return DataResult.success(IChatBaseComponent.ChatSerializer.a(var0));
      } catch (JsonParseException var2) {
         return DataResult.error(var2::getMessage);
      }
   }, var0 -> {
      try {
         return DataResult.success(IChatBaseComponent.ChatSerializer.c(var0));
      } catch (IllegalArgumentException var2) {
         return DataResult.error(var2::getMessage);
      }
   });
   public static final Codec<Vector3f> c = Codec.FLOAT
      .listOf()
      .comapFlatMap(
         var0 -> SystemUtils.a(var0, 3).map(var0x -> new Vector3f(var0x.get(0), var0x.get(1), var0x.get(2))), var0 -> List.of(var0.x(), var0.y(), var0.z())
      );
   public static final Codec<Quaternionf> d = Codec.FLOAT
      .listOf()
      .comapFlatMap(
         var0 -> SystemUtils.a(var0, 4).map(var0x -> new Quaternionf(var0x.get(0), var0x.get(1), var0x.get(2), var0x.get(3))),
         var0 -> List.of(var0.x, var0.y, var0.z, var0.w)
      );
   public static final Codec<AxisAngle4f> e = RecordCodecBuilder.create(
      var0 -> var0.group(
               Codec.FLOAT.fieldOf("angle").forGetter(var0x -> var0x.angle), c.fieldOf("axis").forGetter(var0x -> new Vector3f(var0x.x, var0x.y, var0x.z))
            )
            .apply(var0, AxisAngle4f::new)
   );
   public static final Codec<Quaternionf> f = Codec.either(d, e.xmap(Quaternionf::new, AxisAngle4f::new))
      .xmap(var0 -> (Quaternionf)var0.map(var0x -> var0x, var0x -> var0x), Either::left);
   public static Codec<Matrix4f> g = Codec.FLOAT.listOf().comapFlatMap(var0 -> SystemUtils.a(var0, 16).map(var0x -> {
         Matrix4f var1 = new Matrix4f();

         for(int var2 = 0; var2 < var0x.size(); ++var2) {
            var1.setRowColumn(var2 >> 2, var2 & 3, var0x.get(var2));
         }

         return var1.determineProperties();
      }), var0 -> {
      FloatList var1 = new FloatArrayList(16);

      for(int var2 = 0; var2 < 16; ++var2) {
         var1.add(var0.getRowColumn(var2 >> 2, var2 & 3));
      }

      return var1;
   });
   public static final Codec<Integer> h = a(0, Integer.MAX_VALUE, var0 -> "Value must be non-negative: " + var0);
   public static final Codec<Integer> i = a(1, Integer.MAX_VALUE, var0 -> "Value must be positive: " + var0);
   public static final Codec<Float> j = a(0.0F, Float.MAX_VALUE, var0 -> "Value must be positive: " + var0);
   public static final Codec<Pattern> k = Codec.STRING.comapFlatMap(var0 -> {
      try {
         return DataResult.success(Pattern.compile(var0));
      } catch (PatternSyntaxException var2) {
         return DataResult.error(() -> "Invalid regex pattern '" + var0 + "': " + var2.getMessage());
      }
   }, Pattern::pattern);
   public static final Codec<Instant> l = a(DateTimeFormatter.ISO_INSTANT);
   public static final Codec<byte[]> m = Codec.STRING.comapFlatMap(var0 -> {
      try {
         return DataResult.success(Base64.getDecoder().decode(var0));
      } catch (IllegalArgumentException var2) {
         return DataResult.error(() -> "Malformed base64 string");
      }
   }, var0 -> Base64.getEncoder().encodeToString(var0));
   public static final Codec<ExtraCodecs.d> n = Codec.STRING
      .comapFlatMap(
         var0 -> var0.startsWith("#")
               ? MinecraftKey.b(var0.substring(1)).map(var0x -> new ExtraCodecs.d(var0x, true))
               : MinecraftKey.b(var0).map(var0x -> new ExtraCodecs.d(var0x, false)),
         ExtraCodecs.d::c
      );
   public static final Function<Optional<Long>, OptionalLong> o = var0 -> var0.map(OptionalLong::of).orElseGet(OptionalLong::empty);
   public static final Function<OptionalLong, Optional<Long>> p = var0 -> var0.isPresent() ? Optional.of(var0.getAsLong()) : Optional.empty();
   public static final Codec<BitSet> q = Codec.LONG_STREAM.xmap(var0 -> BitSet.valueOf(var0.toArray()), var0 -> Arrays.stream(var0.toLongArray()));
   private static final Codec<Property> u = RecordCodecBuilder.create(
      var0 -> var0.group(
               Codec.STRING.fieldOf("name").forGetter(Property::getName),
               Codec.STRING.fieldOf("value").forGetter(Property::getValue),
               Codec.STRING.optionalFieldOf("signature").forGetter(var0x -> Optional.ofNullable(var0x.getSignature()))
            )
            .apply(var0, (var0x, var1, var2) -> new Property(var0x, var1, (String)var2.orElse(null)))
   );
   @VisibleForTesting
   public static final Codec<PropertyMap> r = Codec.either(Codec.unboundedMap(Codec.STRING, Codec.STRING.listOf()), u.listOf()).xmap(var0 -> {
      PropertyMap var1 = new PropertyMap();
      var0.ifLeft(var1x -> var1x.forEach((var1xx, var2) -> {
            for(String var4 : var2) {
               var1.put(var1xx, new Property(var1xx, var4));
            }
         })).ifRight(var1x -> {
         for(Property var3 : var1x) {
            var1.put(var3.getName(), var3);
         }
      });
      return var1;
   }, var0 -> Either.right(var0.values().stream().toList()));
   public static final Codec<GameProfile> s = RecordCodecBuilder.create(
      var0 -> var0.group(
               Codec.mapPair(
                     UUIDUtil.c.xmap(Optional::of, var0x -> (UUID)var0x.orElse(null)).optionalFieldOf("id", Optional.empty()),
                     Codec.STRING.xmap(Optional::of, var0x -> (String)var0x.orElse(null)).optionalFieldOf("name", Optional.empty())
                  )
                  .flatXmap(ExtraCodecs::a, ExtraCodecs::a)
                  .forGetter(Function.identity()),
               r.optionalFieldOf("properties", new PropertyMap()).forGetter(GameProfile::getProperties)
            )
            .apply(var0, (var0x, var1) -> {
               var1.forEach((var1x, var2) -> var0x.getProperties().put(var1x, var2));
               return var0x;
            })
   );
   public static final Codec<String> t = a(
      Codec.STRING, var0 -> var0.isEmpty() ? DataResult.error(() -> "Expected non-empty string") : DataResult.success(var0)
   );

   public static <F, S> Codec<Either<F, S>> a(Codec<F> var0, Codec<S> var1) {
      return new ExtraCodecs.e(var0, var1);
   }

   public static <P, I> Codec<I> a(Codec<P> var0, String var1, String var2, BiFunction<P, P, DataResult<I>> var3, Function<I, P> var4, Function<I, P> var5) {
      Codec<I> var6 = Codec.list(var0).comapFlatMap(var1x -> SystemUtils.a(var1x, 2).flatMap(var1xx -> {
            P var2x = var1xx.get(0);
            P var3x = var1xx.get(1);
            return (DataResult)var3.apply((P)var2x, (P)var3x);
         }), var2x -> ImmutableList.of(var4.apply((I)var2x), var5.apply((I)var2x)));
      Codec<I> var7 = RecordCodecBuilder.create(
            var3x -> var3x.group(var0.fieldOf(var1).forGetter(Pair::getFirst), var0.fieldOf(var2).forGetter(Pair::getSecond)).apply(var3x, Pair::of)
         )
         .comapFlatMap(
            var1x -> (DataResult)var3.apply((P)var1x.getFirst(), (P)var1x.getSecond()), var2x -> Pair.of(var4.apply((I)var2x), var5.apply((I)var2x))
         );
      Codec<I> var8 = new ExtraCodecs.b(var6, var7).xmap(var0x -> var0x.map(var0xx -> var0xx, var0xx -> var0xx), Either::left);
      return Codec.either(var0, var8)
         .comapFlatMap(var1x -> (DataResult)var1x.map(var1xx -> (DataResult)var3.apply((P)var1xx, (P)var1xx), DataResult::success), var2x -> {
            P var3x = var4.apply((I)var2x);
            P var4x = var5.apply((I)var2x);
            return Objects.equals(var3x, var4x) ? Either.left(var3x) : Either.right(var2x);
         });
   }

   public static <A> ResultFunction<A> a(final A var0) {
      return new ResultFunction<A>() {
         public <T> DataResult<Pair<A, T>> apply(DynamicOps<T> var0x, T var1, DataResult<Pair<A, T>> var2) {
            MutableObject<String> var3 = new MutableObject();
            Optional<Pair<A, T>> var4 = var2.resultOrPartial(var3::setValue);
            return var4.isPresent() ? var2 : DataResult.error(() -> "(" + (String)var3.getValue() + " -> using default)", Pair.of(var0, var1));
         }

         public <T> DataResult<T> coApply(DynamicOps<T> var0x, A var1, DataResult<T> var2) {
            return var2;
         }

         @Override
         public String toString() {
            return "OrElsePartial[" + var0 + "]";
         }
      };
   }

   public static <E> Codec<E> a(ToIntFunction<E> var0, IntFunction<E> var1, int var2) {
      return Codec.INT
         .flatXmap(
            var1x -> (DataResult)Optional.ofNullable(var1.apply(var1x))
                  .map(DataResult::success)
                  .orElseGet(() -> DataResult.error(() -> "Unknown element id: " + var1x)),
            var2x -> {
               int var3 = var0.applyAsInt((E)var2x);
               return var3 == var2 ? DataResult.error(() -> "Element with unknown id: " + var2x) : DataResult.success(var3);
            }
         );
   }

   public static <E> Codec<E> a(Function<E, String> var0, Function<String, E> var1) {
      return Codec.STRING
         .flatXmap(
            var1x -> (DataResult)Optional.ofNullable(var1.apply(var1x))
                  .map(DataResult::success)
                  .orElseGet(() -> DataResult.error(() -> "Unknown element name:" + var1x)),
            var1x -> (DataResult)Optional.ofNullable(var0.apply((E)var1x))
                  .map(DataResult::success)
                  .orElseGet(() -> DataResult.error(() -> "Element with unknown name: " + var1x))
         );
   }

   public static <E> Codec<E> b(final Codec<E> var0, final Codec<E> var1) {
      return new Codec<E>() {
         public <T> DataResult<T> encode(E var0x, DynamicOps<T> var1x, T var2) {
            return var1.compressMaps() ? var1.encode(var0, var1, var2) : var0.encode(var0, var1, var2);
         }

         public <T> DataResult<Pair<E, T>> decode(DynamicOps<T> var0x, T var1x) {
            return var0.compressMaps() ? var1.decode(var0, var1) : var0.decode(var0, var1);
         }

         @Override
         public String toString() {
            return var0 + " orCompressed " + var1;
         }
      };
   }

   public static <E> Codec<E> a(Codec<E> var0, final Function<E, Lifecycle> var1, final Function<E, Lifecycle> var2) {
      return var0.mapResult(new ResultFunction<E>() {
         public <T> DataResult<Pair<E, T>> apply(DynamicOps<T> var0, T var1x, DataResult<Pair<E, T>> var2x) {
            return (DataResult<Pair<E, T>>)var2.result().map(var2xxx -> var2.setLifecycle((Lifecycle)var1.apply((E)var2xxx.getFirst()))).orElse((T)var2);
         }

         public <T> DataResult<T> coApply(DynamicOps<T> var0, E var1x, DataResult<T> var2x) {
            return var2.setLifecycle((Lifecycle)var2.apply(var1));
         }

         @Override
         public String toString() {
            return "WithLifecycle[" + var1 + " " + var2 + "]";
         }
      });
   }

   public static <T> Codec<T> a(Codec<T> var0, Function<T, DataResult<T>> var1) {
      return var0.flatXmap(var1, var1);
   }

   private static Codec<Integer> a(int var0, int var1, Function<Integer, String> var2) {
      return a(Codec.INT, var3 -> var3.compareTo(var0) >= 0 && var3.compareTo(var1) <= 0 ? DataResult.success(var3) : DataResult.error(() -> var2.apply(var3)));
   }

   public static Codec<Integer> a(int var0, int var1) {
      return a(var0, var1, var2 -> "Value must be within range [" + var0 + ";" + var1 + "]: " + var2);
   }

   private static Codec<Float> a(float var0, float var1, Function<Float, String> var2) {
      return a(
         Codec.FLOAT, var3 -> var3.compareTo(var0) > 0 && var3.compareTo(var1) <= 0 ? DataResult.success(var3) : DataResult.error(() -> var2.apply(var3))
      );
   }

   public static <T> Codec<List<T>> a(Codec<List<T>> var0) {
      return a(
         var0,
         (Function<List<T>, DataResult<List<T>>>)(var0x -> var0x.isEmpty() ? DataResult.error(() -> "List must have contents") : DataResult.success(var0x))
      );
   }

   public static <T> Codec<HolderSet<T>> b(Codec<HolderSet<T>> var0) {
      return a(
         var0,
         (Function<HolderSet<T>, DataResult<HolderSet<T>>>)(var0x -> var0x.c().right().filter(List::isEmpty).isPresent()
               ? DataResult.error(() -> "List must have contents")
               : DataResult.success(var0x))
      );
   }

   public static <A> Codec<A> a(Supplier<Codec<A>> var0) {
      return new ExtraCodecs.c<>(var0);
   }

   public static <E> MapCodec<E> a(final Function<DynamicOps<?>, DataResult<E>> var0) {
      class a extends MapCodec<E> {
         public <T> RecordBuilder<T> encode(E var0x, DynamicOps<T> var1, RecordBuilder<T> var2) {
            return var2;
         }

         public <T> DataResult<E> decode(DynamicOps<T> var0x, MapLike<T> var1) {
            return (DataResult<E>)var0.apply((T)var0);
         }

         public String toString() {
            return "ContextRetrievalCodec[" + var0 + "]";
         }

         public <T> Stream<T> keys(DynamicOps<T> var0x) {
            return Stream.empty();
         }
      }

      return new a();
   }

   public static <E, L extends Collection<E>, T> Function<L, DataResult<L>> b(Function<E, T> var0) {
      return var1 -> {
         Iterator<E> var2 = var1.iterator();
         if (var2.hasNext()) {
            T var3 = var0.apply(var2.next());

            while(var2.hasNext()) {
               E var4 = var2.next();
               T var5 = var0.apply(var4);
               if (var5 != var3) {
                  return DataResult.error(() -> "Mixed type list: element " + var4 + " had type " + var5 + ", but list is of type " + var3);
               }
            }
         }

         return DataResult.success(var1, Lifecycle.stable());
      };
   }

   public static <A> Codec<A> c(final Codec<A> var0) {
      return Codec.of(var0, new Decoder<A>() {
         public <T> DataResult<Pair<A, T>> decode(DynamicOps<T> var0x, T var1) {
            try {
               return var0.decode(var0, var1);
            } catch (Exception var4) {
               return DataResult.error(() -> "Caught exception decoding " + var1 + ": " + var4.getMessage());
            }
         }
      });
   }

   public static Codec<Instant> a(DateTimeFormatter var0) {
      return Codec.STRING.comapFlatMap(var1 -> {
         try {
            return DataResult.success(Instant.from(var0.parse(var1)));
         } catch (Exception var3) {
            return DataResult.error(var3::getMessage);
         }
      }, var0::format);
   }

   public static MapCodec<OptionalLong> a(MapCodec<Optional<Long>> var0) {
      return var0.xmap(o, p);
   }

   private static DataResult<GameProfile> a(Pair<Optional<UUID>, Optional<String>> var0) {
      try {
         return DataResult.success(new GameProfile((UUID)((Optional)var0.getFirst()).orElse(null), (String)((Optional)var0.getSecond()).orElse(null)));
      } catch (Throwable var2) {
         return DataResult.error(var2::getMessage);
      }
   }

   private static DataResult<Pair<Optional<UUID>, Optional<String>>> a(GameProfile var0) {
      return DataResult.success(Pair.of(Optional.ofNullable(var0.getId()), Optional.ofNullable(var0.getName())));
   }

   public static Codec<String> b(int var0, int var1) {
      return a(
         Codec.STRING,
         var2 -> {
            int var3 = var2.length();
            if (var3 < var0) {
               return DataResult.error(() -> "String \"" + var2 + "\" is too short: " + var3 + ", expected range [" + var0 + "-" + var1 + "]");
            } else {
               return var3 > var1
                  ? DataResult.error(() -> "String \"" + var2 + "\" is too long: " + var3 + ", expected range [" + var0 + "-" + var1 + "]")
                  : DataResult.success(var2);
            }
         }
      );
   }

   static final class b<F, S> implements Codec<Either<F, S>> {
      private final Codec<F> a;
      private final Codec<S> b;

      public b(Codec<F> var0, Codec<S> var1) {
         this.a = var0;
         this.b = var1;
      }

      public <T> DataResult<Pair<Either<F, S>, T>> decode(DynamicOps<T> var0, T var1) {
         DataResult<Pair<Either<F, S>, T>> var2 = this.a.decode(var0, var1).map(var0x -> var0x.mapFirst(Either::left));
         if (!var2.error().isPresent()) {
            return var2;
         } else {
            DataResult<Pair<Either<F, S>, T>> var3 = this.b.decode(var0, var1).map(var0x -> var0x.mapFirst(Either::right));
            return !var3.error().isPresent() ? var3 : var2.apply2((var0x, var1x) -> var1x, var3);
         }
      }

      public <T> DataResult<T> a(Either<F, S> var0, DynamicOps<T> var1, T var2) {
         return (DataResult<T>)var0.map(var2x -> this.a.encode(var2x, var1, var2), var2x -> this.b.encode(var2x, var1, var2));
      }

      @Override
      public boolean equals(Object var0) {
         if (this == var0) {
            return true;
         } else if (var0 != null && this.getClass() == var0.getClass()) {
            ExtraCodecs.b<?, ?> var1 = (ExtraCodecs.b)var0;
            return Objects.equals(this.a, var1.a) && Objects.equals(this.b, var1.b);
         } else {
            return false;
         }
      }

      @Override
      public int hashCode() {
         return Objects.hash(this.a, this.b);
      }

      @Override
      public String toString() {
         return "EitherCodec[" + this.a + ", " + this.b + "]";
      }
   }

   static record c<A>(Supplier<Codec<A>> delegate) implements Codec<A> {
      private final Supplier<Codec<A>> a;

      c(Supplier<Codec<A>> var0) {
         Supplier<Codec<A>> var2 = Suppliers.memoize(var0::get);
         this.a = var2;
      }

      public <T> DataResult<Pair<A, T>> decode(DynamicOps<T> var0, T var1) {
         return ((Codec)this.a.get()).decode(var0, var1);
      }

      public <T> DataResult<T> encode(A var0, DynamicOps<T> var1, T var2) {
         return ((Codec)this.a.get()).encode(var0, var1, var2);
      }
   }

   public static record d(MinecraftKey id, boolean tag) {
      private final MinecraftKey a;
      private final boolean b;

      public d(MinecraftKey var0, boolean var1) {
         this.a = var0;
         this.b = var1;
      }

      @Override
      public String toString() {
         return this.c();
      }

      private String c() {
         return this.b ? "#" + this.a : this.a.toString();
      }
   }

   static final class e<F, S> implements Codec<Either<F, S>> {
      private final Codec<F> a;
      private final Codec<S> b;

      public e(Codec<F> var0, Codec<S> var1) {
         this.a = var0;
         this.b = var1;
      }

      public <T> DataResult<Pair<Either<F, S>, T>> decode(DynamicOps<T> var0, T var1) {
         DataResult<Pair<Either<F, S>, T>> var2 = this.a.decode(var0, var1).map(var0x -> var0x.mapFirst(Either::left));
         DataResult<Pair<Either<F, S>, T>> var3 = this.b.decode(var0, var1).map(var0x -> var0x.mapFirst(Either::right));
         Optional<Pair<Either<F, S>, T>> var4 = var2.result();
         Optional<Pair<Either<F, S>, T>> var5 = var3.result();
         if (var4.isPresent() && var5.isPresent()) {
            return DataResult.error(
               () -> "Both alternatives read successfully, can not pick the correct one; first: " + var4.get() + " second: " + var5.get(), (Pair)var4.get()
            );
         } else {
            return var4.isPresent() ? var2 : var3;
         }
      }

      public <T> DataResult<T> a(Either<F, S> var0, DynamicOps<T> var1, T var2) {
         return (DataResult<T>)var0.map(var2x -> this.a.encode(var2x, var1, var2), var2x -> this.b.encode(var2x, var1, var2));
      }

      @Override
      public boolean equals(Object var0) {
         if (this == var0) {
            return true;
         } else if (var0 != null && this.getClass() == var0.getClass()) {
            ExtraCodecs.e<?, ?> var1 = (ExtraCodecs.e)var0;
            return Objects.equals(this.a, var1.a) && Objects.equals(this.b, var1.b);
         } else {
            return false;
         }
      }

      @Override
      public int hashCode() {
         return Objects.hash(this.a, this.b);
      }

      @Override
      public String toString() {
         return "XorCodec[" + this.a + ", " + this.b + "]";
      }
   }
}
