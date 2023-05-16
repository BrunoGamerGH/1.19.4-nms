package org.bukkit.craftbukkit.v1_19_R3.entity;

import net.minecraft.world.entity.animal.EntitySquid;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Squid;

public class CraftSquid extends CraftWaterMob implements Squid {
   public CraftSquid(CraftServer server, EntitySquid entity) {
      super(server, entity);
   }

   public EntitySquid getHandle() {
      return (EntitySquid)this.entity;
   }

   @Override
   public String toString() {
      return "CraftSquid";
   }

   @Override
   public EntityType getType() {
      return EntityType.SQUID;
   }
}
