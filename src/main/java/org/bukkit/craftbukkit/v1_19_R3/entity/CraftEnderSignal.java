package org.bukkit.craftbukkit.v1_19_R3.entity;

import com.google.common.base.Preconditions;
import net.minecraft.core.BlockPosition;
import net.minecraft.world.entity.projectile.EntityEnderSignal;
import net.minecraft.world.item.Items;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.entity.EnderSignal;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

public class CraftEnderSignal extends CraftEntity implements EnderSignal {
   public CraftEnderSignal(CraftServer server, EntityEnderSignal entity) {
      super(server, entity);
   }

   public EntityEnderSignal getHandle() {
      return (EntityEnderSignal)this.entity;
   }

   @Override
   public String toString() {
      return "CraftEnderSignal";
   }

   public EntityType getType() {
      return EntityType.ENDER_SIGNAL;
   }

   public Location getTargetLocation() {
      return new Location(this.getWorld(), this.getHandle().c, this.getHandle().d, this.getHandle().e, this.getHandle().dw(), this.getHandle().dy());
   }

   public void setTargetLocation(Location location) {
      Preconditions.checkArgument(this.getWorld().equals(location.getWorld()), "Cannot target EnderSignal across worlds");
      this.getHandle().a(BlockPosition.a(location.getX(), location.getY(), location.getZ()));
   }

   public boolean getDropItem() {
      return this.getHandle().g;
   }

   public void setDropItem(boolean shouldDropItem) {
      this.getHandle().g = shouldDropItem;
   }

   public ItemStack getItem() {
      return CraftItemStack.asBukkitCopy(this.getHandle().i());
   }

   public void setItem(ItemStack item) {
      this.getHandle().a(item != null ? CraftItemStack.asNMSCopy(item) : Items.rz.ad_());
   }

   public int getDespawnTimer() {
      return this.getHandle().f;
   }

   public void setDespawnTimer(int time) {
      this.getHandle().f = time;
   }
}
