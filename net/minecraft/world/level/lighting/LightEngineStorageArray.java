package net.minecraft.world.level.lighting;

import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import javax.annotation.Nullable;
import net.minecraft.world.level.chunk.NibbleArray;

public abstract class LightEngineStorageArray<M extends LightEngineStorageArray<M>> {
   private static final int b = 2;
   private final long[] c = new long[2];
   private final NibbleArray[] d = new NibbleArray[2];
   private boolean e;
   protected final Long2ObjectOpenHashMap<NibbleArray> a;

   protected LightEngineStorageArray(Long2ObjectOpenHashMap<NibbleArray> var0) {
      this.a = var0;
      this.c();
      this.e = true;
   }

   public abstract M b();

   public void a(long var0) {
      this.a.put(var0, ((NibbleArray)this.a.get(var0)).b());
      this.c();
   }

   public boolean b(long var0) {
      return this.a.containsKey(var0);
   }

   @Nullable
   public NibbleArray c(long var0) {
      if (this.e) {
         for(int var2 = 0; var2 < 2; ++var2) {
            if (var0 == this.c[var2]) {
               return this.d[var2];
            }
         }
      }

      NibbleArray var2 = (NibbleArray)this.a.get(var0);
      if (var2 == null) {
         return null;
      } else {
         if (this.e) {
            for(int var3 = 1; var3 > 0; --var3) {
               this.c[var3] = this.c[var3 - 1];
               this.d[var3] = this.d[var3 - 1];
            }

            this.c[0] = var0;
            this.d[0] = var2;
         }

         return var2;
      }
   }

   @Nullable
   public NibbleArray d(long var0) {
      return (NibbleArray)this.a.remove(var0);
   }

   public void a(long var0, NibbleArray var2) {
      this.a.put(var0, var2);
   }

   public void c() {
      for(int var0 = 0; var0 < 2; ++var0) {
         this.c[var0] = Long.MAX_VALUE;
         this.d[var0] = null;
      }
   }

   public void d() {
      this.e = false;
   }
}
