package net.minecraft.world.phys.shapes;

import java.util.BitSet;
import net.minecraft.core.EnumDirection;

public final class VoxelShapeBitSet extends VoxelShapeDiscrete {
   private final BitSet d;
   private int e;
   private int f;
   private int g;
   private int h;
   private int i;
   private int j;

   public VoxelShapeBitSet(int var0, int var1, int var2) {
      super(var0, var1, var2);
      this.d = new BitSet(var0 * var1 * var2);
      this.e = var0;
      this.f = var1;
      this.g = var2;
   }

   public static VoxelShapeBitSet a(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8) {
      VoxelShapeBitSet var9 = new VoxelShapeBitSet(var0, var1, var2);
      var9.e = var3;
      var9.f = var4;
      var9.g = var5;
      var9.h = var6;
      var9.i = var7;
      var9.j = var8;

      for(int var10 = var3; var10 < var6; ++var10) {
         for(int var11 = var4; var11 < var7; ++var11) {
            for(int var12 = var5; var12 < var8; ++var12) {
               var9.a(var10, var11, var12, false);
            }
         }
      }

      return var9;
   }

   public VoxelShapeBitSet(VoxelShapeDiscrete var0) {
      super(var0.a, var0.b, var0.c);
      if (var0 instanceof VoxelShapeBitSet) {
         this.d = (BitSet)((VoxelShapeBitSet)var0).d.clone();
      } else {
         this.d = new BitSet(this.a * this.b * this.c);

         for(int var1 = 0; var1 < this.a; ++var1) {
            for(int var2 = 0; var2 < this.b; ++var2) {
               for(int var3 = 0; var3 < this.c; ++var3) {
                  if (var0.b(var1, var2, var3)) {
                     this.d.set(this.a(var1, var2, var3));
                  }
               }
            }
         }
      }

      this.e = var0.a(EnumDirection.EnumAxis.a);
      this.f = var0.a(EnumDirection.EnumAxis.b);
      this.g = var0.a(EnumDirection.EnumAxis.c);
      this.h = var0.b(EnumDirection.EnumAxis.a);
      this.i = var0.b(EnumDirection.EnumAxis.b);
      this.j = var0.b(EnumDirection.EnumAxis.c);
   }

   protected int a(int var0, int var1, int var2) {
      return (var0 * this.b + var1) * this.c + var2;
   }

   @Override
   public boolean b(int var0, int var1, int var2) {
      return this.d.get(this.a(var0, var1, var2));
   }

   private void a(int var0, int var1, int var2, boolean var3) {
      this.d.set(this.a(var0, var1, var2));
      if (var3) {
         this.e = Math.min(this.e, var0);
         this.f = Math.min(this.f, var1);
         this.g = Math.min(this.g, var2);
         this.h = Math.max(this.h, var0 + 1);
         this.i = Math.max(this.i, var1 + 1);
         this.j = Math.max(this.j, var2 + 1);
      }
   }

   @Override
   public void c(int var0, int var1, int var2) {
      this.a(var0, var1, var2, true);
   }

   @Override
   public boolean a() {
      return this.d.isEmpty();
   }

   @Override
   public int a(EnumDirection.EnumAxis var0) {
      return var0.a(this.e, this.f, this.g);
   }

   @Override
   public int b(EnumDirection.EnumAxis var0) {
      return var0.a(this.h, this.i, this.j);
   }

   static VoxelShapeBitSet a(
      VoxelShapeDiscrete var0, VoxelShapeDiscrete var1, VoxelShapeMerger var2, VoxelShapeMerger var3, VoxelShapeMerger var4, OperatorBoolean var5
   ) {
      VoxelShapeBitSet var6 = new VoxelShapeBitSet(var2.size() - 1, var3.size() - 1, var4.size() - 1);
      int[] var7 = new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE};
      var2.a((var7x, var8, var9) -> {
         boolean[] var10 = new boolean[]{false};
         var3.a((var10x, var11, var12) -> {
            boolean[] var13 = new boolean[]{false};
            var4.a((var12x, var13x, var14) -> {
               if (var5.apply(var0.d(var7x, var10x, var12x), var1.d(var8, var11, var13x))) {
                  var6.d.set(var6.a(var9, var12, var14));
                  var7[2] = Math.min(var7[2], var14);
                  var7[5] = Math.max(var7[5], var14);
                  var13[0] = true;
               }

               return true;
            });
            if (var13[0]) {
               var7[1] = Math.min(var7[1], var12);
               var7[4] = Math.max(var7[4], var12);
               var10[0] = true;
            }

            return true;
         });
         if (var10[0]) {
            var7[0] = Math.min(var7[0], var9);
            var7[3] = Math.max(var7[3], var9);
         }

         return true;
      });
      var6.e = var7[0];
      var6.f = var7[1];
      var6.g = var7[2];
      var6.h = var7[3] + 1;
      var6.i = var7[4] + 1;
      var6.j = var7[5] + 1;
      return var6;
   }

   protected static void a(VoxelShapeDiscrete var0, VoxelShapeDiscrete.b var1, boolean var2) {
      VoxelShapeBitSet var3 = new VoxelShapeBitSet(var0);

      for(int var4 = 0; var4 < var3.b; ++var4) {
         for(int var5 = 0; var5 < var3.a; ++var5) {
            int var6 = -1;

            for(int var7 = 0; var7 <= var3.c; ++var7) {
               if (var3.d(var5, var4, var7)) {
                  if (var2) {
                     if (var6 == -1) {
                        var6 = var7;
                     }
                  } else {
                     var1.consume(var5, var4, var7, var5 + 1, var4 + 1, var7 + 1);
                  }
               } else if (var6 != -1) {
                  int var8 = var5;
                  int var9 = var4;
                  var3.b(var6, var7, var5, var4);

                  while(var3.a(var6, var7, var8 + 1, var4)) {
                     var3.b(var6, var7, var8 + 1, var4);
                     ++var8;
                  }

                  while(var3.a(var5, var8 + 1, var6, var7, var9 + 1)) {
                     for(int var10 = var5; var10 <= var8; ++var10) {
                        var3.b(var6, var7, var10, var9 + 1);
                     }

                     ++var9;
                  }

                  var1.consume(var5, var4, var6, var8 + 1, var9 + 1, var7);
                  var6 = -1;
               }
            }
         }
      }
   }

   private boolean a(int var0, int var1, int var2, int var3) {
      if (var2 < this.a && var3 < this.b) {
         return this.d.nextClearBit(this.a(var2, var3, var0)) >= this.a(var2, var3, var1);
      } else {
         return false;
      }
   }

   private boolean a(int var0, int var1, int var2, int var3, int var4) {
      for(int var5 = var0; var5 < var1; ++var5) {
         if (!this.a(var2, var3, var5, var4)) {
            return false;
         }
      }

      return true;
   }

   private void b(int var0, int var1, int var2, int var3) {
      this.d.clear(this.a(var2, var3, var0), this.a(var2, var3, var1));
   }
}
