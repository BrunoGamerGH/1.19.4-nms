package org.bukkit.craftbukkit.v1_19_R3.entity;

import java.util.List;
import net.minecraft.world.entity.npc.EntityVillager;
import net.minecraft.world.entity.npc.EntityVillagerAbstract;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftInventory;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftMerchant;
import org.bukkit.entity.AbstractVillager;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.MerchantRecipe;

public class CraftAbstractVillager extends CraftAgeable implements AbstractVillager, InventoryHolder {
   public CraftAbstractVillager(CraftServer server, EntityVillagerAbstract entity) {
      super(server, entity);
   }

   public EntityVillagerAbstract getHandle() {
      return (EntityVillager)this.entity;
   }

   @Override
   public String toString() {
      return "CraftAbstractVillager";
   }

   public Inventory getInventory() {
      return new CraftInventory(this.getHandle().w());
   }

   private CraftMerchant getMerchant() {
      return this.getHandle().getCraftMerchant();
   }

   public List<MerchantRecipe> getRecipes() {
      return this.getMerchant().getRecipes();
   }

   public void setRecipes(List<MerchantRecipe> recipes) {
      this.getMerchant().setRecipes(recipes);
   }

   public MerchantRecipe getRecipe(int i) {
      return this.getMerchant().getRecipe(i);
   }

   public void setRecipe(int i, MerchantRecipe merchantRecipe) {
      this.getMerchant().setRecipe(i, merchantRecipe);
   }

   public int getRecipeCount() {
      return this.getMerchant().getRecipeCount();
   }

   public boolean isTrading() {
      return this.getTrader() != null;
   }

   public HumanEntity getTrader() {
      return this.getMerchant().getTrader();
   }
}
