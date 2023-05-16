package org.bukkit.craftbukkit.v1_19_R3.inventory;

import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.IMerchant;
import net.minecraft.world.item.trading.MerchantRecipe;
import net.minecraft.world.item.trading.MerchantRecipeList;
import org.apache.commons.lang.Validate;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftChatMessage;

public class CraftMerchantCustom extends CraftMerchant {
   public CraftMerchantCustom(String title) {
      super(new CraftMerchantCustom.MinecraftMerchant(title));
      this.getMerchant().craftMerchant = this;
   }

   @Override
   public String toString() {
      return "CraftMerchantCustom";
   }

   public CraftMerchantCustom.MinecraftMerchant getMerchant() {
      return (CraftMerchantCustom.MinecraftMerchant)super.getMerchant();
   }

   public static class MinecraftMerchant implements IMerchant {
      private final IChatBaseComponent title;
      private final MerchantRecipeList trades = new MerchantRecipeList();
      private EntityHuman tradingPlayer;
      protected CraftMerchant craftMerchant;

      public MinecraftMerchant(String title) {
         Validate.notNull(title, "Title cannot be null");
         this.title = CraftChatMessage.fromString(title)[0];
      }

      @Override
      public CraftMerchant getCraftMerchant() {
         return this.craftMerchant;
      }

      @Override
      public void e(EntityHuman entityhuman) {
         this.tradingPlayer = entityhuman;
      }

      @Override
      public EntityHuman fS() {
         return this.tradingPlayer;
      }

      @Override
      public MerchantRecipeList fU() {
         return this.trades;
      }

      @Override
      public void a(MerchantRecipe merchantrecipe) {
         merchantrecipe.j();
      }

      @Override
      public void l(ItemStack itemstack) {
      }

      public IChatBaseComponent getScoreboardDisplayName() {
         return this.title;
      }

      @Override
      public int r() {
         return 0;
      }

      @Override
      public void s(int i) {
      }

      @Override
      public boolean fV() {
         return false;
      }

      @Override
      public SoundEffect fW() {
         return SoundEffects.yo;
      }

      @Override
      public void a(MerchantRecipeList merchantrecipelist) {
      }

      @Override
      public boolean ga() {
         return false;
      }
   }
}
