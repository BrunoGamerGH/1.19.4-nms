package net.minecraft.world.level.block.state.properties;

import com.google.common.base.MoreObjects;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.world.level.block.state.IBlockDataHolder;

public abstract class IBlockState<T extends Comparable<T>> {
   private final Class<T> a;
   private final String b;
   @Nullable
   private Integer c;
   private final Codec<T> d = Codec.STRING
      .comapFlatMap(
         var0x -> (DataResult)this.b(var0x)
               .map(DataResult::success)
               .orElseGet(() -> (T)DataResult.error(() -> "Unable to read property: " + this + " with value: " + var0x)),
         this::a
      );
   private final Codec<IBlockState.a<T>> e = this.d.xmap(this::b, IBlockState.a::b);

   protected IBlockState(String var0, Class<T> var1) {
      this.a = var1;
      this.b = var0;
   }

   public IBlockState.a<T> b(T var0) {
      return new IBlockState.a<>(this, var0);
   }

   public IBlockState.a<T> a(IBlockDataHolder<?, ?> var0) {
      return new IBlockState.a<>(this, var0.c(this));
   }

   public Stream<IBlockState.a<T>> c() {
      return this.a().stream().map(this::b);
   }

   public Codec<T> d() {
      return this.d;
   }

   public Codec<IBlockState.a<T>> e() {
      return this.e;
   }

   public String f() {
      return this.b;
   }

   public Class<T> g() {
      return this.a;
   }

   public abstract Collection<T> a();

   public abstract String a(T var1);

   public abstract Optional<T> b(String var1);

   @Override
   public String toString() {
      return MoreObjects.toStringHelper(this).add("name", this.b).add("clazz", this.a).add("values", this.a()).toString();
   }

   @Override
   public boolean equals(Object var0) {
      if (this == var0) {
         return true;
      } else if (!(var0 instanceof IBlockState)) {
         return false;
      } else {
         IBlockState<?> var1 = (IBlockState)var0;
         return this.a.equals(var1.a) && this.b.equals(var1.b);
      }
   }

   @Override
   public final int hashCode() {
      if (this.c == null) {
         this.c = this.b();
      }

      return this.c;
   }

   public int b() {
      return 31 * this.a.hashCode() + this.b.hashCode();
   }

   public <U, S extends IBlockDataHolder<?, S>> DataResult<S> a(DynamicOps<U> var0, S var1, U var2) {
      DataResult<T> var3 = this.d.parse(var0, var2);
      return var3.map(var1x -> var1.a(this, var1x)).setPartial(var1);
   }

   public static record a<T extends Comparable<T>>(IBlockState<T> property, T value) {
      private final IBlockState<T> a;
      private final T b;

      public a(IBlockState<T> var0, T var1) {
         if (!var0.a().contains(var1)) {
            throw new IllegalArgumentException("Value " + var1 + " does not belong to property " + var0);
         } else {
            this.a = var0;
            this.b = var1;
         }
      }

      @Override
      public String toString() {
         return this.a.f() + "=" + this.a.a(this.b);
      }
   }
}
