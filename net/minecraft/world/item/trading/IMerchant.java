package net.minecraft.world.item.trading;

import java.util.OptionalInt;
import javax.annotation.Nullable;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.world.TileInventory;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.inventory.ContainerMerchant;
import net.minecraft.world.item.ItemStack;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftMerchant;

public interface IMerchant {
   void e(@Nullable EntityHuman var1);

   @Nullable
   EntityHuman fS();

   MerchantRecipeList fU();

   void a(MerchantRecipeList var1);

   void a(MerchantRecipe var1);

   void l(ItemStack var1);

   int r();

   void s(int var1);

   boolean fV();

   SoundEffect fW();

   default boolean ge() {
      return false;
   }

   default void a(EntityHuman entityhuman, IChatBaseComponent ichatbasecomponent, int i) {
      OptionalInt optionalint = entityhuman.a(
         new TileInventory((j, playerinventory, entityhuman1) -> new ContainerMerchant(j, playerinventory, this), ichatbasecomponent)
      );
      if (optionalint.isPresent()) {
         MerchantRecipeList merchantrecipelist = this.fU();
         if (!merchantrecipelist.isEmpty()) {
            entityhuman.a(optionalint.getAsInt(), merchantrecipelist, i, this.r(), this.fV(), this.ge());
         }
      }
   }

   boolean ga();

   CraftMerchant getCraftMerchant();
}
