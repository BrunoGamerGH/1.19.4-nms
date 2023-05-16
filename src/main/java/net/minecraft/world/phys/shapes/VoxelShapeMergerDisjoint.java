package net.minecraft.world.phys.shapes;

import it.unimi.dsi.fastutil.doubles.AbstractDoubleList;
import it.unimi.dsi.fastutil.doubles.DoubleList;

public class VoxelShapeMergerDisjoint extends AbstractDoubleList implements VoxelShapeMerger {
   private final DoubleList a;
   private final DoubleList b;
   private final boolean c;

   protected VoxelShapeMergerDisjoint(DoubleList var0, DoubleList var1, boolean var2) {
      this.a = var0;
      this.b = var1;
      this.c = var2;
   }

   @Override
   public int size() {
      return this.a.size() + this.b.size();
   }

   @Override
   public boolean a(VoxelShapeMerger.a var0) {
      return this.c ? this.b((var1x, var2, var3) -> var0.merge(var2, var1x, var3)) : this.b(var0);
   }

   private boolean b(VoxelShapeMerger.a var0) {
      int var1 = this.a.size();

      for(int var2 = 0; var2 < var1; ++var2) {
         if (!var0.merge(var2, -1, var2)) {
            return false;
         }
      }

      int var2 = this.b.size() - 1;

      for(int var3 = 0; var3 < var2; ++var3) {
         if (!var0.merge(var1 - 1, var3, var1 + var3)) {
            return false;
         }
      }

      return true;
   }

   public double getDouble(int var0) {
      return var0 < this.a.size() ? this.a.getDouble(var0) : this.b.getDouble(var0 - this.a.size());
   }

   @Override
   public DoubleList a() {
      return this;
   }
}
