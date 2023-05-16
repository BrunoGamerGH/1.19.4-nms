package net.minecraft.server.level;

import net.minecraft.core.SectionPosition;
import net.minecraft.world.level.lighting.LightEngineGraph;

public abstract class LightEngineGraphSection extends LightEngineGraph {
   protected LightEngineGraphSection(int var0, int var1, int var2) {
      super(var0, var1, var2);
   }

   @Override
   protected boolean a(long var0) {
      return var0 == Long.MAX_VALUE;
   }

   @Override
   protected void a(long var0, int var2, boolean var3) {
      for(int var4 = -1; var4 <= 1; ++var4) {
         for(int var5 = -1; var5 <= 1; ++var5) {
            for(int var6 = -1; var6 <= 1; ++var6) {
               long var7 = SectionPosition.a(var0, var4, var5, var6);
               if (var7 != var0) {
                  this.b(var0, var7, var2, var3);
               }
            }
         }
      }
   }

   @Override
   protected int a(long var0, long var2, int var4) {
      int var5 = var4;

      for(int var6 = -1; var6 <= 1; ++var6) {
         for(int var7 = -1; var7 <= 1; ++var7) {
            for(int var8 = -1; var8 <= 1; ++var8) {
               long var9 = SectionPosition.a(var0, var6, var7, var8);
               if (var9 == var0) {
                  var9 = Long.MAX_VALUE;
               }

               if (var9 != var2) {
                  int var11 = this.b(var9, var0, this.c(var9));
                  if (var5 > var11) {
                     var5 = var11;
                  }

                  if (var5 == 0) {
                     return var5;
                  }
               }
            }
         }
      }

      return var5;
   }

   @Override
   protected int b(long var0, long var2, int var4) {
      return var0 == Long.MAX_VALUE ? this.b(var2) : var4 + 1;
   }

   protected abstract int b(long var1);

   public void b(long var0, int var2, boolean var3) {
      this.a(Long.MAX_VALUE, var0, var2, var3);
   }
}
