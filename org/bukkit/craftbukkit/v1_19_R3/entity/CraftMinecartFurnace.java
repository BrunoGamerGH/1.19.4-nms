package org.bukkit.craftbukkit.v1_19_R3.entity;

import com.google.common.base.Preconditions;
import net.minecraft.world.entity.vehicle.EntityMinecartFurnace;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.minecart.PoweredMinecart;

public class CraftMinecartFurnace extends CraftMinecart implements PoweredMinecart {
   public CraftMinecartFurnace(CraftServer server, EntityMinecartFurnace entity) {
      super(server, entity);
   }

   public EntityMinecartFurnace getHandle() {
      return (EntityMinecartFurnace)this.entity;
   }

   public int getFuel() {
      return this.getHandle().f;
   }

   public void setFuel(int fuel) {
      Preconditions.checkArgument(fuel >= 0, "ticks cannot be negative");
      this.getHandle().f = fuel;
   }

   @Override
   public String toString() {
      return "CraftMinecartFurnace";
   }

   public EntityType getType() {
      return EntityType.MINECART_FURNACE;
   }
}
