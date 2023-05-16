package org.bukkit.craftbukkit.v1_19_R3.entity;

import net.minecraft.world.entity.projectile.EntityFireballFireball;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.entity.SizedFireball;
import org.bukkit.inventory.ItemStack;

public class CraftSizedFireball extends CraftFireball implements SizedFireball {
   public CraftSizedFireball(CraftServer server, EntityFireballFireball entity) {
      super(server, entity);
   }

   public ItemStack getDisplayItem() {
      return this.getHandle().o().b() ? new ItemStack(Material.FIRE_CHARGE) : CraftItemStack.asBukkitCopy(this.getHandle().o());
   }

   public void setDisplayItem(ItemStack item) {
      this.getHandle().a(CraftItemStack.asNMSCopy(item));
   }

   public EntityFireballFireball getHandle() {
      return (EntityFireballFireball)this.entity;
   }
}
