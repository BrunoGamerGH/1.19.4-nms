package net.minecraft.world.level.block.state.properties;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import net.minecraft.util.INamable;

public class BlockStateEnum<T extends Enum<T> & INamable> extends IBlockState<T> {
   private final ImmutableSet<T> a;
   private final Map<String, T> b = Maps.newHashMap();

   protected BlockStateEnum(String var0, Class<T> var1, Collection<T> var2) {
      super(var0, var1);
      this.a = ImmutableSet.copyOf(var2);

      for(T var4 : var2) {
         String var5 = var4.c();
         if (this.b.containsKey(var5)) {
            throw new IllegalArgumentException("Multiple values have the same name '" + var5 + "'");
         }

         this.b.put(var5, var4);
      }
   }

   @Override
   public Collection<T> a() {
      return this.a;
   }

   @Override
   public Optional<T> b(String var0) {
      return Optional.ofNullable(this.b.get(var0));
   }

   public String a(T var0) {
      return var0.c();
   }

   @Override
   public boolean equals(Object var0) {
      if (this == var0) {
         return true;
      } else if (var0 instanceof BlockStateEnum var1 && super.equals(var0)) {
         return this.a.equals(var1.a) && this.b.equals(var1.b);
      } else {
         return false;
      }
   }

   @Override
   public int b() {
      int var0 = super.b();
      var0 = 31 * var0 + this.a.hashCode();
      return 31 * var0 + this.b.hashCode();
   }

   public static <T extends Enum<T> & INamable> BlockStateEnum<T> a(String var0, Class<T> var1) {
      return a(var0, var1, var0x -> true);
   }

   public static <T extends Enum<T> & INamable> BlockStateEnum<T> a(String var0, Class<T> var1, Predicate<T> var2) {
      return a(var0, var1, Arrays.<T>stream(var1.getEnumConstants()).filter(var2).collect(Collectors.toList()));
   }

   public static <T extends Enum<T> & INamable> BlockStateEnum<T> a(String var0, Class<T> var1, T... var2) {
      return a(var0, var1, Lists.newArrayList(var2));
   }

   public static <T extends Enum<T> & INamable> BlockStateEnum<T> a(String var0, Class<T> var1, Collection<T> var2) {
      return new BlockStateEnum<>(var0, var1, var2);
   }
}
