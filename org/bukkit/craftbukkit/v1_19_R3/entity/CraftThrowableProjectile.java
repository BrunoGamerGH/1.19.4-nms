package org.bukkit.craftbukkit.v1_19_R3.entity;

import net.minecraft.world.entity.projectile.EntityProjectileThrowable;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.entity.ThrowableProjectile;
import org.bukkit.inventory.ItemStack;

public abstract class CraftThrowableProjectile extends CraftProjectile implements ThrowableProjectile {
   public CraftThrowableProjectile(CraftServer server, EntityProjectileThrowable entity) {
      super(server, entity);
   }

   public ItemStack getItem() {
      return this.getHandle().k().b()
         ? CraftItemStack.asBukkitCopy(new net.minecraft.world.item.ItemStack(this.getHandle().getDefaultItemPublic()))
         : CraftItemStack.asBukkitCopy(this.getHandle().k());
   }

   public void setItem(ItemStack item) {
      this.getHandle().a(CraftItemStack.asNMSCopy(item));
   }

   public EntityProjectileThrowable getHandle() {
      return (EntityProjectileThrowable)this.entity;
   }
}
