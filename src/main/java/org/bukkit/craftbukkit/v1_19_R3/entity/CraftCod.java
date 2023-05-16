package org.bukkit.craftbukkit.v1_19_R3.entity;

import net.minecraft.world.entity.animal.EntityCod;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.entity.Cod;
import org.bukkit.entity.EntityType;

public class CraftCod extends CraftFish implements Cod {
   public CraftCod(CraftServer server, EntityCod entity) {
      super(server, entity);
   }

   public EntityCod getHandle() {
      return (EntityCod)super.getHandle();
   }

   @Override
   public String toString() {
      return "CraftCod";
   }

   @Override
   public EntityType getType() {
      return EntityType.COD;
   }
}
