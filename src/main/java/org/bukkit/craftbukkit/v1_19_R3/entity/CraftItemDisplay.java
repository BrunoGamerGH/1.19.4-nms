package org.bukkit.craftbukkit.v1_19_R3.entity;

import com.google.common.base.Preconditions;
import net.minecraft.world.entity.Display;
import net.minecraft.world.item.ItemDisplayContext;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.ItemDisplay.ItemDisplayTransform;
import org.bukkit.inventory.ItemStack;

public class CraftItemDisplay extends CraftDisplay implements ItemDisplay {
   public CraftItemDisplay(CraftServer server, Display.ItemDisplay entity) {
      super(server, entity);
   }

   public Display.ItemDisplay getHandle() {
      return (Display.ItemDisplay)super.getHandle();
   }

   @Override
   public String toString() {
      return "CraftItemDisplay";
   }

   @Override
   public EntityType getType() {
      return EntityType.ITEM_DISPLAY;
   }

   public ItemStack getItemStack() {
      return CraftItemStack.asBukkitCopy(this.getHandle().o());
   }

   public void setItemStack(ItemStack item) {
      this.getHandle().a(CraftItemStack.asNMSCopy(item));
   }

   public ItemDisplayTransform getItemDisplayTransform() {
      return ItemDisplayTransform.values()[this.getHandle().p().ordinal()];
   }

   public void setItemDisplayTransform(ItemDisplayTransform display) {
      Preconditions.checkArgument(display != null, "Display cannot be null");
      this.getHandle().a(ItemDisplayContext.k.apply(display.ordinal()));
   }
}
