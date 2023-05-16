package org.bukkit.craftbukkit.v1_19_R3.inventory;

import net.minecraft.world.IInventory;
import org.bukkit.block.Jukebox;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.JukeboxInventory;

public class CraftInventoryJukebox extends CraftInventory implements JukeboxInventory {
   public CraftInventoryJukebox(IInventory inventory) {
      super(inventory);
   }

   public void setRecord(ItemStack item) {
      if (item == null) {
         this.inventory.a(0, 0);
      } else {
         this.setItem(0, item);
      }
   }

   public ItemStack getRecord() {
      return this.getItem(0);
   }

   public Jukebox getHolder() {
      return (Jukebox)this.inventory.getOwner();
   }
}
