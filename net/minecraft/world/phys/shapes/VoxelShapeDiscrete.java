package net.minecraft.world.phys.shapes;

import net.minecraft.core.EnumAxisCycle;
import net.minecraft.core.EnumDirection;

public abstract class VoxelShapeDiscrete {
   private static final EnumDirection.EnumAxis[] d = EnumDirection.EnumAxis.values();
   protected final int a;
   protected final int b;
   protected final int c;

   protected VoxelShapeDiscrete(int var0, int var1, int var2) {
      if (var0 >= 0 && var1 >= 0 && var2 >= 0) {
         this.a = var0;
         this.b = var1;
         this.c = var2;
      } else {
         throw new IllegalArgumentException("Need all positive sizes: x: " + var0 + ", y: " + var1 + ", z: " + var2);
      }
   }

   public boolean a(EnumAxisCycle var0, int var1, int var2, int var3) {
      return this.d(
         var0.a(var1, var2, var3, EnumDirection.EnumAxis.a),
         var0.a(var1, var2, var3, EnumDirection.EnumAxis.b),
         var0.a(var1, var2, var3, EnumDirection.EnumAxis.c)
      );
   }

   public boolean d(int var0, int var1, int var2) {
      if (var0 < 0 || var1 < 0 || var2 < 0) {
         return false;
      } else {
         return var0 < this.a && var1 < this.b && var2 < this.c ? this.b(var0, var1, var2) : false;
      }
   }

   public boolean b(EnumAxisCycle var0, int var1, int var2, int var3) {
      return this.b(
         var0.a(var1, var2, var3, EnumDirection.EnumAxis.a),
         var0.a(var1, var2, var3, EnumDirection.EnumAxis.b),
         var0.a(var1, var2, var3, EnumDirection.EnumAxis.c)
      );
   }

   public abstract boolean b(int var1, int var2, int var3);

   public abstract void c(int var1, int var2, int var3);

   public boolean a() {
      for(EnumDirection.EnumAxis var3 : d) {
         if (this.a(var3) >= this.b(var3)) {
            return true;
         }
      }

      return false;
   }

   public abstract int a(EnumDirection.EnumAxis var1);

   public abstract int b(EnumDirection.EnumAxis var1);

   public int a(EnumDirection.EnumAxis var0, int var1, int var2) {
      int var3 = this.c(var0);
      if (var1 >= 0 && var2 >= 0) {
         EnumDirection.EnumAxis var4 = EnumAxisCycle.b.a(var0);
         EnumDirection.EnumAxis var5 = EnumAxisCycle.c.a(var0);
         if (var1 < this.c(var4) && var2 < this.c(var5)) {
            EnumAxisCycle var6 = EnumAxisCycle.a(EnumDirection.EnumAxis.a, var0);

            for(int var7 = 0; var7 < var3; ++var7) {
               if (this.b(var6, var7, var1, var2)) {
                  return var7;
               }
            }

            return var3;
         } else {
            return var3;
         }
      } else {
         return var3;
      }
   }

   public int b(EnumDirection.EnumAxis var0, int var1, int var2) {
      if (var1 >= 0 && var2 >= 0) {
         EnumDirection.EnumAxis var3 = EnumAxisCycle.b.a(var0);
         EnumDirection.EnumAxis var4 = EnumAxisCycle.c.a(var0);
         if (var1 < this.c(var3) && var2 < this.c(var4)) {
            int var5 = this.c(var0);
            EnumAxisCycle var6 = EnumAxisCycle.a(EnumDirection.EnumAxis.a, var0);

            for(int var7 = var5 - 1; var7 >= 0; --var7) {
               if (this.b(var6, var7, var1, var2)) {
                  return var7 + 1;
               }
            }

            return 0;
         } else {
            return 0;
         }
      } else {
         return 0;
      }
   }

   public int c(EnumDirection.EnumAxis var0) {
      return var0.a(this.a, this.b, this.c);
   }

   public int b() {
      return this.c(EnumDirection.EnumAxis.a);
   }

   public int c() {
      return this.c(EnumDirection.EnumAxis.b);
   }

   public int d() {
      return this.c(EnumDirection.EnumAxis.c);
   }

   public void a(VoxelShapeDiscrete.b var0, boolean var1) {
      this.a(var0, EnumAxisCycle.a, var1);
      this.a(var0, EnumAxisCycle.b, var1);
      this.a(var0, EnumAxisCycle.c, var1);
   }

   private void a(VoxelShapeDiscrete.b var0, EnumAxisCycle var1, boolean var2) {
      EnumAxisCycle var4 = var1.a();
      int var5 = this.c(var4.a(EnumDirection.EnumAxis.a));
      int var6 = this.c(var4.a(EnumDirection.EnumAxis.b));
      int var7 = this.c(var4.a(EnumDirection.EnumAxis.c));

      for(int var8 = 0; var8 <= var5; ++var8) {
         for(int var9 = 0; var9 <= var6; ++var9) {
            int var3 = -1;

            for(int var10 = 0; var10 <= var7; ++var10) {
               int var11 = 0;
               int var12 = 0;

               for(int var13 = 0; var13 <= 1; ++var13) {
                  for(int var14 = 0; var14 <= 1; ++var14) {
                     if (this.a(var4, var8 + var13 - 1, var9 + var14 - 1, var10)) {
                        ++var11;
                        var12 ^= var13 ^ var14;
                     }
                  }
               }

               if (var11 == 1 || var11 == 3 || var11 == 2 && (var12 & 1) == 0) {
                  if (var2) {
                     if (var3 == -1) {
                        var3 = var10;
                     }
                  } else {
                     var0.consume(
                        var4.a(var8, var9, var10, EnumDirection.EnumAxis.a),
                        var4.a(var8, var9, var10, EnumDirection.EnumAxis.b),
                        var4.a(var8, var9, var10, EnumDirection.EnumAxis.c),
                        var4.a(var8, var9, var10 + 1, EnumDirection.EnumAxis.a),
                        var4.a(var8, var9, var10 + 1, EnumDirection.EnumAxis.b),
                        var4.a(var8, var9, var10 + 1, EnumDirection.EnumAxis.c)
                     );
                  }
               } else if (var3 != -1) {
                  var0.consume(
                     var4.a(var8, var9, var3, EnumDirection.EnumAxis.a),
                     var4.a(var8, var9, var3, EnumDirection.EnumAxis.b),
                     var4.a(var8, var9, var3, EnumDirection.EnumAxis.c),
                     var4.a(var8, var9, var10, EnumDirection.EnumAxis.a),
                     var4.a(var8, var9, var10, EnumDirection.EnumAxis.b),
                     var4.a(var8, var9, var10, EnumDirection.EnumAxis.c)
                  );
                  var3 = -1;
               }
            }
         }
      }
   }

   public void b(VoxelShapeDiscrete.b var0, boolean var1) {
      VoxelShapeBitSet.a(this, var0, var1);
   }

   public void a(VoxelShapeDiscrete.a var0) {
      this.a(var0, EnumAxisCycle.a);
      this.a(var0, EnumAxisCycle.b);
      this.a(var0, EnumAxisCycle.c);
   }

   private void a(VoxelShapeDiscrete.a var0, EnumAxisCycle var1) {
      EnumAxisCycle var2 = var1.a();
      EnumDirection.EnumAxis var3 = var2.a(EnumDirection.EnumAxis.c);
      int var4 = this.c(var2.a(EnumDirection.EnumAxis.a));
      int var5 = this.c(var2.a(EnumDirection.EnumAxis.b));
      int var6 = this.c(var3);
      EnumDirection var7 = EnumDirection.a(var3, EnumDirection.EnumAxisDirection.b);
      EnumDirection var8 = EnumDirection.a(var3, EnumDirection.EnumAxisDirection.a);

      for(int var9 = 0; var9 < var4; ++var9) {
         for(int var10 = 0; var10 < var5; ++var10) {
            boolean var11 = false;

            for(int var12 = 0; var12 <= var6; ++var12) {
               boolean var13 = var12 != var6 && this.b(var2, var9, var10, var12);
               if (!var11 && var13) {
                  var0.consume(
                     var7,
                     var2.a(var9, var10, var12, EnumDirection.EnumAxis.a),
                     var2.a(var9, var10, var12, EnumDirection.EnumAxis.b),
                     var2.a(var9, var10, var12, EnumDirection.EnumAxis.c)
                  );
               }

               if (var11 && !var13) {
                  var0.consume(
                     var8,
                     var2.a(var9, var10, var12 - 1, EnumDirection.EnumAxis.a),
                     var2.a(var9, var10, var12 - 1, EnumDirection.EnumAxis.b),
                     var2.a(var9, var10, var12 - 1, EnumDirection.EnumAxis.c)
                  );
               }

               var11 = var13;
            }
         }
      }
   }

   public interface a {
      void consume(EnumDirection var1, int var2, int var3, int var4);
   }

   public interface b {
      void consume(int var1, int var2, int var3, int var4, int var5, int var6);
   }
}
