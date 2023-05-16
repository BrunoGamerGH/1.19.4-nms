package net.minecraft.world.level.block.entity;

import net.minecraft.core.BlockPosition;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.level.block.state.IBlockData;

public class TileEntityComparator extends TileEntity {
   private int a;

   public TileEntityComparator(BlockPosition var0, IBlockData var1) {
      super(TileEntityTypes.s, var0, var1);
   }

   @Override
   protected void b(NBTTagCompound var0) {
      super.b(var0);
      var0.a("OutputSignal", this.a);
   }

   @Override
   public void a(NBTTagCompound var0) {
      super.a(var0);
      this.a = var0.h("OutputSignal");
   }

   public int c() {
      return this.a;
   }

   public void a(int var0) {
      this.a = var0;
   }
}
