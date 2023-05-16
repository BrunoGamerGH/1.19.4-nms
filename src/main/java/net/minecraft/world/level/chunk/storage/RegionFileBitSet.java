package net.minecraft.world.level.chunk.storage;

import com.google.common.annotations.VisibleForTesting;
import it.unimi.dsi.fastutil.ints.IntArraySet;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntSet;
import java.util.BitSet;

public class RegionFileBitSet {
   private final BitSet a = new BitSet();

   public void a(int var0, int var1) {
      this.a.set(var0, var0 + var1);
   }

   public void b(int var0, int var1) {
      this.a.clear(var0, var0 + var1);
   }

   public int a(int var0) {
      int var1 = 0;

      while(true) {
         int var2 = this.a.nextClearBit(var1);
         int var3 = this.a.nextSetBit(var2);
         if (var3 == -1 || var3 - var2 >= var0) {
            this.a(var2, var0);
            return var2;
         }

         var1 = var3;
      }
   }

   @VisibleForTesting
   public IntSet a() {
      return this.a.stream().collect(IntArraySet::new, IntCollection::add, IntCollection::addAll);
   }
}
