package org.bukkit.craftbukkit.v1_19_R3.entity;

import net.minecraft.world.entity.monster.EntityWitch;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Witch;

public class CraftWitch extends CraftRaider implements Witch {
   public CraftWitch(CraftServer server, EntityWitch entity) {
      super(server, entity);
   }

   public EntityWitch getHandle() {
      return (EntityWitch)this.entity;
   }

   @Override
   public String toString() {
      return "CraftWitch";
   }

   @Override
   public EntityType getType() {
      return EntityType.WITCH;
   }

   public boolean isDrinkingPotion() {
      return this.getHandle().q();
   }
}
