package net.minecraft.world.level.block.state.properties;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public class BlockStateInteger extends IBlockState<Integer> {
   private final ImmutableSet<Integer> a;
   public final int b;
   public final int c;

   protected BlockStateInteger(String var0, int var1, int var2) {
      super(var0, Integer.class);
      if (var1 < 0) {
         throw new IllegalArgumentException("Min value of " + var0 + " must be 0 or greater");
      } else if (var2 <= var1) {
         throw new IllegalArgumentException("Max value of " + var0 + " must be greater than min (" + var1 + ")");
      } else {
         this.b = var1;
         this.c = var2;
         Set<Integer> var3 = Sets.newHashSet();

         for(int var4 = var1; var4 <= var2; ++var4) {
            var3.add(var4);
         }

         this.a = ImmutableSet.copyOf(var3);
      }
   }

   @Override
   public Collection<Integer> a() {
      return this.a;
   }

   @Override
   public boolean equals(Object var0) {
      if (this == var0) {
         return true;
      } else {
         return var0 instanceof BlockStateInteger var1 && super.equals(var0) ? this.a.equals(var1.a) : false;
      }
   }

   @Override
   public int b() {
      return 31 * super.b() + this.a.hashCode();
   }

   public static BlockStateInteger a(String var0, int var1, int var2) {
      return new BlockStateInteger(var0, var1, var2);
   }

   @Override
   public Optional<Integer> b(String var0) {
      try {
         Integer var1 = Integer.valueOf(var0);
         return var1 >= this.b && var1 <= this.c ? Optional.of(var1) : Optional.empty();
      } catch (NumberFormatException var3) {
         return Optional.empty();
      }
   }

   public String a(Integer var0) {
      return var0.toString();
   }
}
