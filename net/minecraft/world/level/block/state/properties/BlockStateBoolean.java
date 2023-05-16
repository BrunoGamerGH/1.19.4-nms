package net.minecraft.world.level.block.state.properties;

import com.google.common.collect.ImmutableSet;
import java.util.Collection;
import java.util.Optional;

public class BlockStateBoolean extends IBlockState<Boolean> {
   private final ImmutableSet<Boolean> a = ImmutableSet.of(true, false);

   protected BlockStateBoolean(String var0) {
      super(var0, Boolean.class);
   }

   @Override
   public Collection<Boolean> a() {
      return this.a;
   }

   public static BlockStateBoolean a(String var0) {
      return new BlockStateBoolean(var0);
   }

   @Override
   public Optional<Boolean> b(String var0) {
      return !"true".equals(var0) && !"false".equals(var0) ? Optional.empty() : Optional.of(Boolean.valueOf(var0));
   }

   public String a(Boolean var0) {
      return var0.toString();
   }

   @Override
   public boolean equals(Object var0) {
      if (this == var0) {
         return true;
      } else {
         return var0 instanceof BlockStateBoolean var1 && super.equals(var0) ? this.a.equals(var1.a) : false;
      }
   }

   @Override
   public int b() {
      return 31 * super.b() + this.a.hashCode();
   }
}
