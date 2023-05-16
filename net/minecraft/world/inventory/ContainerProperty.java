package net.minecraft.world.inventory;

public abstract class ContainerProperty {
   private int a;

   public static ContainerProperty a(final IContainerProperties var0, final int var1) {
      return new ContainerProperty() {
         @Override
         public int b() {
            return var0.a(var1);
         }

         @Override
         public void a(int var0x) {
            var0.a(var1, var0);
         }
      };
   }

   public static ContainerProperty a(final int[] var0, final int var1) {
      return new ContainerProperty() {
         @Override
         public int b() {
            return var0[var1];
         }

         @Override
         public void a(int var0x) {
            var0[var1] = var0;
         }
      };
   }

   public static ContainerProperty a() {
      return new ContainerProperty() {
         private int a;

         @Override
         public int b() {
            return this.a;
         }

         @Override
         public void a(int var0) {
            this.a = var0;
         }
      };
   }

   public abstract int b();

   public abstract void a(int var1);

   public boolean c() {
      int var0 = this.b();
      boolean var1 = var0 != this.a;
      this.a = var0;
      return var1;
   }
}
