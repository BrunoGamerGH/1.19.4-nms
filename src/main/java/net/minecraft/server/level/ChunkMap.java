package net.minecraft.server.level;

import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.lighting.LightEngineGraph;

public abstract class ChunkMap extends LightEngineGraph {
   protected ChunkMap(int var0, int var1, int var2) {
      super(var0, var1, var2);
   }

   @Override
   protected boolean a(long var0) {
      return var0 == ChunkCoordIntPair.a;
   }

   @Override
   protected void a(long var0, int var2, boolean var3) {
      ChunkCoordIntPair var4 = new ChunkCoordIntPair(var0);
      int var5 = var4.e;
      int var6 = var4.f;

      for(int var7 = -1; var7 <= 1; ++var7) {
         for(int var8 = -1; var8 <= 1; ++var8) {
            long var9 = ChunkCoordIntPair.c(var5 + var7, var6 + var8);
            if (var9 != var0) {
               this.b(var0, var9, var2, var3);
            }
         }
      }
   }

   @Override
   protected int a(long var0, long var2, int var4) {
      int var5 = var4;
      ChunkCoordIntPair var6 = new ChunkCoordIntPair(var0);
      int var7 = var6.e;
      int var8 = var6.f;

      for(int var9 = -1; var9 <= 1; ++var9) {
         for(int var10 = -1; var10 <= 1; ++var10) {
            long var11 = ChunkCoordIntPair.c(var7 + var9, var8 + var10);
            if (var11 == var0) {
               var11 = ChunkCoordIntPair.a;
            }

            if (var11 != var2) {
               int var13 = this.b(var11, var0, this.c(var11));
               if (var5 > var13) {
                  var5 = var13;
               }

               if (var5 == 0) {
                  return var5;
               }
            }
         }
      }

      return var5;
   }

   @Override
   protected int b(long var0, long var2, int var4) {
      return var0 == ChunkCoordIntPair.a ? this.b(var2) : var4 + 1;
   }

   protected abstract int b(long var1);

   public void b(long var0, int var2, boolean var3) {
      this.a(ChunkCoordIntPair.a, var0, var2, var3);
   }
}
