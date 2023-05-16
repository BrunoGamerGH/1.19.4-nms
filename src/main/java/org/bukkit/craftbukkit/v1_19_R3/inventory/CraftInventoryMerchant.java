package org.bukkit.craftbukkit.v1_19_R3.inventory;

import net.minecraft.world.inventory.InventoryMerchant;
import net.minecraft.world.item.trading.IMerchant;
import org.bukkit.inventory.Merchant;
import org.bukkit.inventory.MerchantInventory;
import org.bukkit.inventory.MerchantRecipe;

public class CraftInventoryMerchant extends CraftInventory implements MerchantInventory {
   private final IMerchant merchant;

   public CraftInventoryMerchant(IMerchant merchant, InventoryMerchant inventory) {
      super(inventory);
      this.merchant = merchant;
   }

   public int getSelectedRecipeIndex() {
      return this.getInventory().f;
   }

   public MerchantRecipe getSelectedRecipe() {
      net.minecraft.world.item.trading.MerchantRecipe nmsRecipe = this.getInventory().g();
      return nmsRecipe == null ? null : nmsRecipe.asBukkit();
   }

   public InventoryMerchant getInventory() {
      return (InventoryMerchant)this.inventory;
   }

   public Merchant getMerchant() {
      return this.merchant.getCraftMerchant();
   }
}
