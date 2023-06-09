package org.bukkit.craftbukkit.v1_19_R3.entity;

import net.minecraft.world.entity.monster.EntityRavager;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Ravager;

public class CraftRavager extends CraftRaider implements Ravager {
   public CraftRavager(CraftServer server, EntityRavager entity) {
      super(server, entity);
   }

   public EntityRavager getHandle() {
      return (EntityRavager)super.getHandle();
   }

   @Override
   public EntityType getType() {
      return EntityType.RAVAGER;
   }

   @Override
   public String toString() {
      return "CraftRavager";
   }
}
