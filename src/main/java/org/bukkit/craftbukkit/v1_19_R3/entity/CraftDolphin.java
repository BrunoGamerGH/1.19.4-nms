package org.bukkit.craftbukkit.v1_19_R3.entity;

import net.minecraft.world.entity.animal.EntityDolphin;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.entity.Dolphin;
import org.bukkit.entity.EntityType;

public class CraftDolphin extends CraftWaterMob implements Dolphin {
   public CraftDolphin(CraftServer server, EntityDolphin entity) {
      super(server, entity);
   }

   public EntityDolphin getHandle() {
      return (EntityDolphin)super.getHandle();
   }

   @Override
   public String toString() {
      return "CraftDolphin";
   }

   @Override
   public EntityType getType() {
      return EntityType.DOLPHIN;
   }
}
