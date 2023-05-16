package net.minecraft.world.level.block.state;

import com.google.common.collect.ArrayTable;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import com.google.common.collect.UnmodifiableIterator;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.world.level.block.state.properties.IBlockState;

public abstract class IBlockDataHolder<O, S> {
   public static final String c = "Name";
   public static final String d = "Properties";
   public static final Function<Entry<IBlockState<?>, Comparable<?>>, String> a = new Function<Entry<IBlockState<?>, Comparable<?>>, String>() {
      public String a(@Nullable Entry<IBlockState<?>, Comparable<?>> var0) {
         if (var0 == null) {
            return "<NULL>";
         } else {
            IBlockState<?> var1 = var0.getKey();
            return var1.f() + "=" + this.a(var1, var0.getValue());
         }
      }

      private <T extends Comparable<T>> String a(IBlockState<T> var0, Comparable<?> var1) {
         return var0.a((T)var1);
      }
   };
   protected final O e;
   private final ImmutableMap<IBlockState<?>, Comparable<?>> b;
   private Table<IBlockState<?>, Comparable<?>, S> g;
   protected final MapCodec<S> f;

   protected IBlockDataHolder(O var0, ImmutableMap<IBlockState<?>, Comparable<?>> var1, MapCodec<S> var2) {
      this.e = var0;
      this.b = var1;
      this.f = var2;
   }

   public <T extends Comparable<T>> S a(IBlockState<T> var0) {
      return this.a(var0, a(var0.a(), this.c(var0)));
   }

   protected static <T> T a(Collection<T> var0, T var1) {
      Iterator<T> var2 = var0.iterator();

      while(var2.hasNext()) {
         if (var2.next().equals(var1)) {
            if (var2.hasNext()) {
               return var2.next();
            }

            return var0.iterator().next();
         }
      }

      return var2.next();
   }

   @Override
   public String toString() {
      StringBuilder var0 = new StringBuilder();
      var0.append(this.e);
      if (!this.y().isEmpty()) {
         var0.append('[');
         var0.append(this.y().entrySet().stream().map(a).collect(Collectors.joining(",")));
         var0.append(']');
      }

      return var0.toString();
   }

   public Collection<IBlockState<?>> x() {
      return Collections.unmodifiableCollection(this.b.keySet());
   }

   public <T extends Comparable<T>> boolean b(IBlockState<T> var0) {
      return this.b.containsKey(var0);
   }

   public <T extends Comparable<T>> T c(IBlockState<T> var0) {
      Comparable<?> var1 = (Comparable)this.b.get(var0);
      if (var1 == null) {
         throw new IllegalArgumentException("Cannot get property " + var0 + " as it does not exist in " + this.e);
      } else {
         return var0.g().cast(var1);
      }
   }

   public <T extends Comparable<T>> Optional<T> d(IBlockState<T> var0) {
      Comparable<?> var1 = (Comparable)this.b.get(var0);
      return var1 == null ? Optional.empty() : Optional.of(var0.g().cast(var1));
   }

   public <T extends Comparable<T>, V extends T> S a(IBlockState<T> var0, V var1) {
      Comparable<?> var2 = (Comparable)this.b.get(var0);
      if (var2 == null) {
         throw new IllegalArgumentException("Cannot set property " + var0 + " as it does not exist in " + this.e);
      } else if (var2 == var1) {
         return (S)this;
      } else {
         S var3 = (S)this.g.get(var0, var1);
         if (var3 == null) {
            throw new IllegalArgumentException("Cannot set property " + var0 + " to " + var1 + " on " + this.e + ", it is not an allowed value");
         } else {
            return var3;
         }
      }
   }

   public <T extends Comparable<T>, V extends T> S b(IBlockState<T> var0, V var1) {
      Comparable<?> var2 = (Comparable)this.b.get(var0);
      if (var2 != null && var2 != var1) {
         S var3 = (S)this.g.get(var0, var1);
         if (var3 == null) {
            throw new IllegalArgumentException("Cannot set property " + var0 + " to " + var1 + " on " + this.e + ", it is not an allowed value");
         } else {
            return var3;
         }
      } else {
         return (S)this;
      }
   }

   public void a(Map<Map<IBlockState<?>, Comparable<?>>, S> var0) {
      if (this.g != null) {
         throw new IllegalStateException();
      } else {
         Table<IBlockState<?>, Comparable<?>, S> var1 = HashBasedTable.create();
         UnmodifiableIterator var3 = this.b.entrySet().iterator();

         while(var3.hasNext()) {
            Entry<IBlockState<?>, Comparable<?>> var3x = (Entry)var3.next();
            IBlockState<?> var4 = var3x.getKey();

            for(Comparable<?> var6 : var4.a()) {
               if (var6 != var3x.getValue()) {
                  var1.put(var4, var6, var0.get(this.c(var4, var6)));
               }
            }
         }

         this.g = (Table<IBlockState<?>, Comparable<?>, S>)(var1.isEmpty() ? var1 : ArrayTable.create(var1));
      }
   }

   private Map<IBlockState<?>, Comparable<?>> c(IBlockState<?> var0, Comparable<?> var1) {
      Map<IBlockState<?>, Comparable<?>> var2 = Maps.newHashMap(this.b);
      var2.put(var0, var1);
      return var2;
   }

   public ImmutableMap<IBlockState<?>, Comparable<?>> y() {
      return this.b;
   }

   protected static <O, S extends IBlockDataHolder<O, S>> Codec<S> a(Codec<O> var0, Function<O, S> var1) {
      return var0.dispatch("Name", var0x -> var0x.e, var1x -> {
         S var2 = var1.apply((O)var1x);
         return var2.y().isEmpty() ? Codec.unit(var2) : var2.f.codec().optionalFieldOf("Properties").xmap(var1xx -> var1xx.orElse(var2), Optional::of).codec();
      });
   }
}
