package net.minecraft.world.inventory;

import net.minecraft.stats.StatisticList;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.IMerchant;
import net.minecraft.world.item.trading.MerchantRecipe;

public class SlotMerchantResult extends Slot {
   private final InventoryMerchant a;
   private final EntityHuman b;
   private int c;
   private final IMerchant h;

   public SlotMerchantResult(EntityHuman var0, IMerchant var1, InventoryMerchant var2, int var3, int var4, int var5) {
      super(var2, var3, var4, var5);
      this.b = var0;
      this.h = var1;
      this.a = var2;
   }

   @Override
   public boolean a(ItemStack var0) {
      return false;
   }

   @Override
   public ItemStack a(int var0) {
      if (this.f()) {
         this.c += Math.min(var0, this.e().K());
      }

      return super.a(var0);
   }

   @Override
   protected void a(ItemStack var0, int var1) {
      this.c += var1;
      this.b_(var0);
   }

   @Override
   protected void b_(ItemStack var0) {
      var0.a(this.b.H, this.b, this.c);
      this.c = 0;
   }

   @Override
   public void a(EntityHuman var0, ItemStack var1) {
      this.b_(var1);
      MerchantRecipe var2 = this.a.g();
      if (var2 != null) {
         ItemStack var3 = this.a.a(0);
         ItemStack var4 = this.a.a(1);
         if (var2.b(var3, var4) || var2.b(var4, var3)) {
            this.h.a(var2);
            var0.a(StatisticList.T);
            this.a.a(0, var3);
            this.a.a(1, var4);
         }

         this.h.s(this.h.r() + var2.o());
      }
   }
}
