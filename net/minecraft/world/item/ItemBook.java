package net.minecraft.world.item;

public class ItemBook extends Item {
   public ItemBook(Item.Info var0) {
      super(var0);
   }

   @Override
   public boolean d_(ItemStack var0) {
      return var0.K() == 1;
   }

   @Override
   public int c() {
      return 1;
   }
}
