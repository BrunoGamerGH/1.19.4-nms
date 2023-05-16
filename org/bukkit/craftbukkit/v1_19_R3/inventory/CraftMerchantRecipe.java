package org.bukkit.craftbukkit.v1_19_R3.inventory;

import com.google.common.base.Preconditions;
import java.util.List;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;

public class CraftMerchantRecipe extends MerchantRecipe {
   private final net.minecraft.world.item.trading.MerchantRecipe handle;

   public CraftMerchantRecipe(net.minecraft.world.item.trading.MerchantRecipe merchantRecipe) {
      super(CraftItemStack.asBukkitCopy(merchantRecipe.c), 0);
      this.handle = merchantRecipe;
      this.addIngredient(CraftItemStack.asBukkitCopy(merchantRecipe.a));
      this.addIngredient(CraftItemStack.asBukkitCopy(merchantRecipe.b));
   }

   @Deprecated
   public CraftMerchantRecipe(ItemStack result, int uses, int maxUses, boolean experienceReward, int experience, float priceMultiplier) {
      this(result, uses, maxUses, experienceReward, experience, priceMultiplier, 0, 0);
   }

   public CraftMerchantRecipe(
      ItemStack result, int uses, int maxUses, boolean experienceReward, int experience, float priceMultiplier, int demand, int specialPrice
   ) {
      super(result, uses, maxUses, experienceReward, experience, priceMultiplier, demand, specialPrice);
      this.handle = new net.minecraft.world.item.trading.MerchantRecipe(
         net.minecraft.world.item.ItemStack.b,
         net.minecraft.world.item.ItemStack.b,
         CraftItemStack.asNMSCopy(result),
         uses,
         maxUses,
         experience,
         priceMultiplier,
         demand,
         this
      );
      this.setSpecialPrice(specialPrice);
      this.setExperienceReward(experienceReward);
   }

   public int getSpecialPrice() {
      return this.handle.m();
   }

   public void setSpecialPrice(int specialPrice) {
      this.handle.g = specialPrice;
   }

   public int getDemand() {
      return this.handle.h;
   }

   public void setDemand(int demand) {
      this.handle.h = demand;
   }

   public int getUses() {
      return this.handle.d;
   }

   public void setUses(int uses) {
      this.handle.d = uses;
   }

   public int getMaxUses() {
      return this.handle.e;
   }

   public void setMaxUses(int maxUses) {
      this.handle.e = maxUses;
   }

   public boolean hasExperienceReward() {
      return this.handle.f;
   }

   public void setExperienceReward(boolean flag) {
      this.handle.f = flag;
   }

   public int getVillagerExperience() {
      return this.handle.j;
   }

   public void setVillagerExperience(int villagerExperience) {
      this.handle.j = villagerExperience;
   }

   public float getPriceMultiplier() {
      return this.handle.i;
   }

   public void setPriceMultiplier(float priceMultiplier) {
      this.handle.i = priceMultiplier;
   }

   public net.minecraft.world.item.trading.MerchantRecipe toMinecraft() {
      List<ItemStack> ingredients = this.getIngredients();
      Preconditions.checkState(!ingredients.isEmpty(), "No offered ingredients");
      this.handle.a = CraftItemStack.asNMSCopy((ItemStack)ingredients.get(0));
      if (ingredients.size() > 1) {
         this.handle.b = CraftItemStack.asNMSCopy((ItemStack)ingredients.get(1));
      }

      return this.handle;
   }

   public static CraftMerchantRecipe fromBukkit(MerchantRecipe recipe) {
      if (recipe instanceof CraftMerchantRecipe) {
         return (CraftMerchantRecipe)recipe;
      } else {
         CraftMerchantRecipe craft = new CraftMerchantRecipe(
            recipe.getResult(),
            recipe.getUses(),
            recipe.getMaxUses(),
            recipe.hasExperienceReward(),
            recipe.getVillagerExperience(),
            recipe.getPriceMultiplier(),
            recipe.getDemand(),
            recipe.getSpecialPrice()
         );
         craft.setIngredients(recipe.getIngredients());
         return craft;
      }
   }
}
