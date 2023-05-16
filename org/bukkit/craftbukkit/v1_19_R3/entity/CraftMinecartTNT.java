package org.bukkit.craftbukkit.v1_19_R3.entity;

import net.minecraft.world.entity.vehicle.EntityMinecartTNT;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.minecart.ExplosiveMinecart;

final class CraftMinecartTNT extends CraftMinecart implements ExplosiveMinecart {
   CraftMinecartTNT(CraftServer server, EntityMinecartTNT entity) {
      super(server, entity);
   }

   @Override
   public String toString() {
      return "CraftMinecartTNT";
   }

   public EntityType getType() {
      return EntityType.MINECART_TNT;
   }
}
