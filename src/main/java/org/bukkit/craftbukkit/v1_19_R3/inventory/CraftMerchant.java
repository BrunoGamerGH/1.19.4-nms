package org.bukkit.craftbukkit.v1_19_R3.inventory;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.trading.IMerchant;
import net.minecraft.world.item.trading.MerchantRecipeList;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Merchant;
import org.bukkit.inventory.MerchantRecipe;

public class CraftMerchant implements Merchant {
   protected final IMerchant merchant;

   public CraftMerchant(IMerchant merchant) {
      this.merchant = merchant;
   }

   public IMerchant getMerchant() {
      return this.merchant;
   }

   public List<MerchantRecipe> getRecipes() {
      return Collections.unmodifiableList(
         Lists.transform(this.merchant.fU(), new Function<net.minecraft.world.item.trading.MerchantRecipe, MerchantRecipe>() {
            public MerchantRecipe apply(net.minecraft.world.item.trading.MerchantRecipe recipe) {
               return recipe.asBukkit();
            }
         })
      );
   }

   public void setRecipes(List<MerchantRecipe> recipes) {
      MerchantRecipeList recipesList = this.merchant.fU();
      recipesList.clear();

      for(MerchantRecipe recipe : recipes) {
         recipesList.add(CraftMerchantRecipe.fromBukkit(recipe).toMinecraft());
      }
   }

   public MerchantRecipe getRecipe(int i) {
      return this.merchant.fU().get(i).asBukkit();
   }

   public void setRecipe(int i, MerchantRecipe merchantRecipe) {
      this.merchant.fU().set(i, CraftMerchantRecipe.fromBukkit(merchantRecipe).toMinecraft());
   }

   public int getRecipeCount() {
      return this.merchant.fU().size();
   }

   public boolean isTrading() {
      return this.getTrader() != null;
   }

   public HumanEntity getTrader() {
      EntityHuman eh = this.merchant.fS();
      return eh == null ? null : eh.getBukkitEntity();
   }

   @Override
   public int hashCode() {
      return this.merchant.hashCode();
   }

   @Override
   public boolean equals(Object obj) {
      return obj instanceof CraftMerchant && ((CraftMerchant)obj).merchant.equals(this.merchant);
   }
}
