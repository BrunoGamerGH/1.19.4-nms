package net.minecraft.world.level.block.entity;

import net.minecraft.world.inventory.IContainerProperties;

class TileEntityLectern$2 implements IContainerProperties {
   TileEntityLectern$2(TileEntityLectern var0) {
      this.a = var0;
   }

   @Override
   public int a(int var0) {
      return var0 == 0 ? this.a.h : 0;
   }

   @Override
   public void a(int var0, int var1) {
      if (var0 == 0) {
         this.a.a(var1);
      }
   }

   @Override
   public int a() {
      return 1;
   }
}
