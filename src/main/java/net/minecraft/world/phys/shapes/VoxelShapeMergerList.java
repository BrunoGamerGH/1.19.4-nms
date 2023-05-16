package net.minecraft.world.phys.shapes;

import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import it.unimi.dsi.fastutil.doubles.DoubleLists;

public class VoxelShapeMergerList implements VoxelShapeMerger {
   private static final DoubleList a = DoubleLists.unmodifiable(DoubleArrayList.wrap(new double[]{0.0}));
   private final double[] b;
   private final int[] c;
   private final int[] d;
   private final int e;

   public VoxelShapeMergerList(DoubleList var0, DoubleList var1, boolean var2, boolean var3) {
      double var4 = Double.NaN;
      int var6 = var0.size();
      int var7 = var1.size();
      int var8 = var6 + var7;
      this.b = new double[var8];
      this.c = new int[var8];
      this.d = new int[var8];
      boolean var9 = !var2;
      boolean var10 = !var3;
      int var11 = 0;
      int var12 = 0;
      int var13 = 0;

      while(true) {
         boolean var14 = var12 >= var6;
         boolean var15 = var13 >= var7;
         if (var14 && var15) {
            this.e = Math.max(1, var11);
            return;
         }

         boolean var16 = !var14 && (var15 || var0.getDouble(var12) < var1.getDouble(var13) + 1.0E-7);
         if (var16) {
            ++var12;
            if (var9 && (var13 == 0 || var15)) {
               continue;
            }
         } else {
            ++var13;
            if (var10 && (var12 == 0 || var14)) {
               continue;
            }
         }

         int var17 = var12 - 1;
         int var18 = var13 - 1;
         double var19 = var16 ? var0.getDouble(var17) : var1.getDouble(var18);
         if (!(var4 >= var19 - 1.0E-7)) {
            this.c[var11] = var17;
            this.d[var11] = var18;
            this.b[var11] = var19;
            ++var11;
            var4 = var19;
         } else {
            this.c[var11 - 1] = var17;
            this.d[var11 - 1] = var18;
         }
      }
   }

   @Override
   public boolean a(VoxelShapeMerger.a var0) {
      int var1 = this.e - 1;

      for(int var2 = 0; var2 < var1; ++var2) {
         if (!var0.merge(this.c[var2], this.d[var2], var2)) {
            return false;
         }
      }

      return true;
   }

   @Override
   public int size() {
      return this.e;
   }

   @Override
   public DoubleList a() {
      return (DoubleList)(this.e <= 1 ? a : DoubleArrayList.wrap(this.b, this.e));
   }
}
