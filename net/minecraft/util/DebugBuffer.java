package net.minecraft.util;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReferenceArray;

public class DebugBuffer<T> {
   private final AtomicReferenceArray<T> a;
   private final AtomicInteger b;

   public DebugBuffer(int var0) {
      this.a = new AtomicReferenceArray<>(var0);
      this.b = new AtomicInteger(0);
   }

   public void a(T var0) {
      int var1 = this.a.length();

      int var2;
      int var3;
      do {
         var2 = this.b.get();
         var3 = (var2 + 1) % var1;
      } while(!this.b.compareAndSet(var2, var3));

      this.a.set(var3, var0);
   }

   public List<T> a() {
      int var0 = this.b.get();
      Builder<T> var1 = ImmutableList.builder();

      for(int var2 = 0; var2 < this.a.length(); ++var2) {
         int var3 = Math.floorMod(var0 - var2, this.a.length());
         T var4 = this.a.get(var3);
         if (var4 != null) {
            var1.add(var4);
         }
      }

      return var1.build();
   }
}
