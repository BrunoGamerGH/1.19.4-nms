package org.bukkit.craftbukkit.v1_19_R3.entity;

import net.minecraft.world.entity.monster.EntityZombieHusk;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Husk;

public class CraftHusk extends CraftZombie implements Husk {
   public CraftHusk(CraftServer server, EntityZombieHusk entity) {
      super(server, entity);
   }

   @Override
   public String toString() {
      return "CraftHusk";
   }

   @Override
   public EntityType getType() {
      return EntityType.HUSK;
   }
}
