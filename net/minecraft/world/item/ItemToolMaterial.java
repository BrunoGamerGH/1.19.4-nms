package net.minecraft.world.item;

public class ItemToolMaterial extends Item {
   private final ToolMaterial a;

   public ItemToolMaterial(ToolMaterial var0, Item.Info var1) {
      super(var1.b(var0.a()));
      this.a = var0;
   }

   public ToolMaterial i() {
      return this.a;
   }

   @Override
   public int c() {
      return this.a.e();
   }

   @Override
   public boolean a(ItemStack var0, ItemStack var1) {
      return this.a.f().a(var1) || super.a(var0, var1);
   }
}
