package net.minecraft.world.inventory;

public class ContainerProperties implements IContainerProperties {
   private final int[] a;

   public ContainerProperties(int var0) {
      this.a = new int[var0];
   }

   @Override
   public int a(int var0) {
      return this.a[var0];
   }

   @Override
   public void a(int var0, int var1) {
      this.a[var0] = var1;
   }

   @Override
   public int a() {
      return this.a.length;
   }
}
