package net.minecraft.world.phys.shapes;

import net.minecraft.core.EnumDirection;
import net.minecraft.util.MathHelper;

public final class VoxelShapeDiscreteSlice extends VoxelShapeDiscrete {
   private final VoxelShapeDiscrete d;
   private final int e;
   private final int f;
   private final int g;
   private final int h;
   private final int i;
   private final int j;

   protected VoxelShapeDiscreteSlice(VoxelShapeDiscrete var0, int var1, int var2, int var3, int var4, int var5, int var6) {
      super(var4 - var1, var5 - var2, var6 - var3);
      this.d = var0;
      this.e = var1;
      this.f = var2;
      this.g = var3;
      this.h = var4;
      this.i = var5;
      this.j = var6;
   }

   @Override
   public boolean b(int var0, int var1, int var2) {
      return this.d.b(this.e + var0, this.f + var1, this.g + var2);
   }

   @Override
   public void c(int var0, int var1, int var2) {
      this.d.c(this.e + var0, this.f + var1, this.g + var2);
   }

   @Override
   public int a(EnumDirection.EnumAxis var0) {
      return this.a(var0, this.d.a(var0));
   }

   @Override
   public int b(EnumDirection.EnumAxis var0) {
      return this.a(var0, this.d.b(var0));
   }

   private int a(EnumDirection.EnumAxis var0, int var1) {
      int var2 = var0.a(this.e, this.f, this.g);
      int var3 = var0.a(this.h, this.i, this.j);
      return MathHelper.a(var1, var2, var3) - var2;
   }
}
