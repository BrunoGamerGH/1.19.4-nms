package net.minecraft.world.level.lighting;

import it.unimi.dsi.fastutil.longs.Long2ByteMap;
import it.unimi.dsi.fastutil.longs.Long2ByteOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongLinkedOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongList;
import java.util.function.LongPredicate;
import net.minecraft.util.MathHelper;

public abstract class LightEngineGraph {
   private static final int a = 255;
   private final int b;
   private final LongLinkedOpenHashSet[] c;
   private final Long2ByteMap d;
   private int e;
   private volatile boolean f;

   protected LightEngineGraph(int var0, final int var1, final int var2) {
      if (var0 >= 254) {
         throw new IllegalArgumentException("Level count must be < 254.");
      } else {
         this.b = var0;
         this.c = new LongLinkedOpenHashSet[var0];

         for(int var3 = 0; var3 < var0; ++var3) {
            this.c[var3] = new LongLinkedOpenHashSet(var1, 0.5F) {
               protected void rehash(int var0) {
                  if (var0 > var1) {
                     super.rehash(var0);
                  }
               }
            };
         }

         this.d = new Long2ByteOpenHashMap(var2, 0.5F) {
            protected void rehash(int var0) {
               if (var0 > var2) {
                  super.rehash(var0);
               }
            }
         };
         this.d.defaultReturnValue((byte)-1);
         this.e = var0;
      }
   }

   private int a(int var0, int var1) {
      int var2 = var0;
      if (var0 > var1) {
         var2 = var1;
      }

      if (var2 > this.b - 1) {
         var2 = this.b - 1;
      }

      return var2;
   }

   private void a(int var0) {
      int var1 = this.e;
      this.e = var0;

      for(int var2 = var1 + 1; var2 < var0; ++var2) {
         if (!this.c[var2].isEmpty()) {
            this.e = var2;
            break;
         }
      }
   }

   protected void e(long var0) {
      int var2 = this.d.get(var0) & 255;
      if (var2 != 255) {
         int var3 = this.c(var0);
         int var4 = this.a(var3, var2);
         this.a(var0, var4, this.b, true);
         this.f = this.e < this.b;
      }
   }

   public void a(LongPredicate var0) {
      LongList var1 = new LongArrayList();
      this.d.keySet().forEach(var2x -> {
         if (var0.test(var2x)) {
            var1.add(var2x);
         }
      });
      var1.forEach(this::e);
   }

   private void a(long var0, int var2, int var3, boolean var4) {
      if (var4) {
         this.d.remove(var0);
      }

      this.c[var2].remove(var0);
      if (this.c[var2].isEmpty() && this.e == var2) {
         this.a(var3);
      }
   }

   private void a(long var0, int var2, int var3) {
      this.d.put(var0, (byte)var2);
      this.c[var3].add(var0);
      if (this.e > var3) {
         this.e = var3;
      }
   }

   protected void f(long var0) {
      this.a(var0, var0, this.b - 1, false);
   }

   protected void a(long var0, long var2, int var4, boolean var5) {
      this.a(var0, var2, var4, this.c(var2), this.d.get(var2) & 255, var5);
      this.f = this.e < this.b;
   }

   private void a(long var0, long var2, int var4, int var5, int var6, boolean var7) {
      if (!this.a(var2)) {
         var4 = MathHelper.a(var4, 0, this.b - 1);
         var5 = MathHelper.a(var5, 0, this.b - 1);
         boolean var8;
         if (var6 == 255) {
            var8 = true;
            var6 = var5;
         } else {
            var8 = false;
         }

         int var9;
         if (var7) {
            var9 = Math.min(var6, var4);
         } else {
            var9 = MathHelper.a(this.a(var2, var0, var4), 0, this.b - 1);
         }

         int var10 = this.a(var5, var6);
         if (var5 != var9) {
            int var11 = this.a(var5, var9);
            if (var10 != var11 && !var8) {
               this.a(var2, var10, var11, false);
            }

            this.a(var2, var9, var11);
         } else if (!var8) {
            this.a(var2, var10, this.b, true);
         }
      }
   }

   protected final void b(long var0, long var2, int var4, boolean var5) {
      int var6 = this.d.get(var2) & 255;
      int var7 = MathHelper.a(this.b(var0, var2, var4), 0, this.b - 1);
      if (var5) {
         this.a(var0, var2, var7, this.c(var2), var6, true);
      } else {
         int var8;
         boolean var9;
         if (var6 == 255) {
            var9 = true;
            var8 = MathHelper.a(this.c(var2), 0, this.b - 1);
         } else {
            var8 = var6;
            var9 = false;
         }

         if (var7 == var8) {
            this.a(var0, var2, this.b - 1, var9 ? var8 : this.c(var2), var6, false);
         }
      }
   }

   protected final boolean b() {
      return this.f;
   }

   protected final int b(int var0) {
      if (this.e >= this.b) {
         return var0;
      } else {
         while(this.e < this.b && var0 > 0) {
            --var0;
            LongLinkedOpenHashSet var1 = this.c[this.e];
            long var2 = var1.removeFirstLong();
            int var4 = MathHelper.a(this.c(var2), 0, this.b - 1);
            if (var1.isEmpty()) {
               this.a(this.b);
            }

            int var5 = this.d.remove(var2) & 255;
            if (var5 < var4) {
               this.a(var2, var5);
               this.a(var2, var5, true);
            } else if (var5 > var4) {
               this.a(var2, var5, this.a(this.b - 1, var5));
               this.a(var2, this.b - 1);
               this.a(var2, var4, false);
            }
         }

         this.f = this.e < this.b;
         return var0;
      }
   }

   public int c() {
      return this.d.size();
   }

   protected abstract boolean a(long var1);

   protected abstract int a(long var1, long var3, int var5);

   protected abstract void a(long var1, int var3, boolean var4);

   protected abstract int c(long var1);

   protected abstract void a(long var1, int var3);

   protected abstract int b(long var1, long var3, int var5);
}
