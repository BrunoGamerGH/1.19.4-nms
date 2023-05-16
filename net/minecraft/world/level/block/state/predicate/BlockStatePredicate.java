package net.minecraft.world.level.block.state.predicate;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.IBlockState;

public class BlockStatePredicate implements Predicate<IBlockData> {
   public static final Predicate<IBlockData> a = var0 -> true;
   private final BlockStateList<Block, IBlockData> b;
   private final Map<IBlockState<?>, Predicate<Object>> c = Maps.newHashMap();

   private BlockStatePredicate(BlockStateList<Block, IBlockData> var0) {
      this.b = var0;
   }

   public static BlockStatePredicate a(Block var0) {
      return new BlockStatePredicate(var0.n());
   }

   public boolean a(@Nullable IBlockData var0) {
      if (var0 != null && var0.b().equals(this.b.c())) {
         if (this.c.isEmpty()) {
            return true;
         } else {
            for(Entry<IBlockState<?>, Predicate<Object>> var2 : this.c.entrySet()) {
               if (!this.a(var0, var2.getKey(), var2.getValue())) {
                  return false;
               }
            }

            return true;
         }
      } else {
         return false;
      }
   }

   protected <T extends Comparable<T>> boolean a(IBlockData var0, IBlockState<T> var1, Predicate<Object> var2) {
      T var3 = var0.c(var1);
      return var2.test(var3);
   }

   public <V extends Comparable<V>> BlockStatePredicate a(IBlockState<V> var0, Predicate<Object> var1) {
      if (!this.b.d().contains(var0)) {
         throw new IllegalArgumentException(this.b + " cannot support property " + var0);
      } else {
         this.c.put(var0, var1);
         return this;
      }
   }
}
