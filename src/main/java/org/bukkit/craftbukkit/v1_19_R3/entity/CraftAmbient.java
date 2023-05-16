package org.bukkit.craftbukkit.v1_19_R3.entity;

import net.minecraft.world.entity.ambient.EntityAmbient;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.entity.Ambient;
import org.bukkit.entity.EntityType;

public class CraftAmbient extends CraftMob implements Ambient {
   public CraftAmbient(CraftServer server, EntityAmbient entity) {
      super(server, entity);
   }

   public EntityAmbient getHandle() {
      return (EntityAmbient)this.entity;
   }

   @Override
   public String toString() {
      return "CraftAmbient";
   }

   @Override
   public EntityType getType() {
      return EntityType.UNKNOWN;
   }
}
