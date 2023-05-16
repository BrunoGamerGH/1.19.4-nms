package org.bukkit.craftbukkit.v1_19_R3.entity;

import java.util.UUID;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.EntityItem;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

public class CraftItem extends CraftEntity implements Item {
   private final EntityItem item;

   public CraftItem(CraftServer server, Entity entity, EntityItem item) {
      super(server, entity);
      this.item = item;
   }

   public CraftItem(CraftServer server, EntityItem entity) {
      this(server, entity, entity);
   }

   public ItemStack getItemStack() {
      return CraftItemStack.asCraftMirror(this.item.i());
   }

   public void setItemStack(ItemStack stack) {
      this.item.a(CraftItemStack.asNMSCopy(stack));
   }

   public int getPickupDelay() {
      return this.item.h;
   }

   public void setPickupDelay(int delay) {
      this.item.h = Math.min(delay, 32767);
   }

   public void setUnlimitedLifetime(boolean unlimited) {
      if (unlimited) {
         this.item.g = -32768;
      } else {
         this.item.g = this.getTicksLived();
      }
   }

   public boolean isUnlimitedLifetime() {
      return this.item.g == -32768;
   }

   @Override
   public void setTicksLived(int value) {
      super.setTicksLived(value);
      if (!this.isUnlimitedLifetime()) {
         this.item.g = value;
      }
   }

   public void setOwner(UUID uuid) {
      this.item.b(uuid);
   }

   public UUID getOwner() {
      return this.item.k;
   }

   public void setThrower(UUID uuid) {
      this.item.c(uuid);
   }

   public UUID getThrower() {
      return this.item.j;
   }

   @Override
   public String toString() {
      return "CraftItem";
   }

   public EntityType getType() {
      return EntityType.DROPPED_ITEM;
   }
}
