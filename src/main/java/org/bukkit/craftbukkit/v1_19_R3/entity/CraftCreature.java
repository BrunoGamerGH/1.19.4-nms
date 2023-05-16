package org.bukkit.craftbukkit.v1_19_R3.entity;

import net.minecraft.world.entity.EntityCreature;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.entity.Creature;

public class CraftCreature extends CraftMob implements Creature {
   public CraftCreature(CraftServer server, EntityCreature entity) {
      super(server, entity);
   }

   public EntityCreature getHandle() {
      return (EntityCreature)this.entity;
   }

   @Override
   public String toString() {
      return "CraftCreature";
   }
}
