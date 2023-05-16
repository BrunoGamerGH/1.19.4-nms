package org.bukkit.craftbukkit.v1_19_R3.entity;

import net.minecraft.world.entity.projectile.EntityThrownTrident;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Trident;
import org.bukkit.inventory.ItemStack;

public class CraftTrident extends CraftArrow implements Trident {
   public CraftTrident(CraftServer server, EntityThrownTrident entity) {
      super(server, entity);
   }

   public EntityThrownTrident getHandle() {
      return (EntityThrownTrident)super.getHandle();
   }

   public ItemStack getItem() {
      return CraftItemStack.asBukkitCopy(this.getHandle().i);
   }

   public void setItem(ItemStack itemStack) {
      this.getHandle().i = CraftItemStack.asNMSCopy(itemStack);
   }

   @Override
   public String toString() {
      return "CraftTrident";
   }

   @Override
   public EntityType getType() {
      return EntityType.TRIDENT;
   }
}
