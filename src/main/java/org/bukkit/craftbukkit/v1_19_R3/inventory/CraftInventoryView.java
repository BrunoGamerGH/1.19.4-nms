package org.bukkit.craftbukkit.v1_19_R3.inventory;

import net.minecraft.world.inventory.Container;
import org.bukkit.GameMode;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftHumanEntity;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftChatMessage;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

public class CraftInventoryView extends InventoryView {
   private final Container container;
   private final CraftHumanEntity player;
   private final CraftInventory viewing;

   public CraftInventoryView(HumanEntity player, Inventory viewing, Container container) {
      this.player = (CraftHumanEntity)player;
      this.viewing = (CraftInventory)viewing;
      this.container = container;
   }

   public Inventory getTopInventory() {
      return this.viewing;
   }

   public Inventory getBottomInventory() {
      return this.player.getInventory();
   }

   public HumanEntity getPlayer() {
      return this.player;
   }

   public InventoryType getType() {
      InventoryType type = this.viewing.getType();
      return type == InventoryType.CRAFTING && this.player.getGameMode() == GameMode.CREATIVE ? InventoryType.CREATIVE : type;
   }

   public void setItem(int slot, ItemStack item) {
      net.minecraft.world.item.ItemStack stack = CraftItemStack.asNMSCopy(item);
      if (slot >= 0) {
         this.container.b(slot).e(stack);
      } else {
         this.player.getHandle().a(stack, false);
      }
   }

   public ItemStack getItem(int slot) {
      return slot < 0 ? null : CraftItemStack.asCraftMirror(this.container.b(slot).e());
   }

   public String getTitle() {
      return CraftChatMessage.fromComponent(this.container.getTitle());
   }

   public boolean isInTop(int rawSlot) {
      return rawSlot < this.viewing.getSize();
   }

   public Container getHandle() {
      return this.container;
   }
}
