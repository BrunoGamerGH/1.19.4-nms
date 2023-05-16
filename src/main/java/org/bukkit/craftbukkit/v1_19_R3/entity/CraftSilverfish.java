package org.bukkit.craftbukkit.v1_19_R3.entity;

import net.minecraft.world.entity.monster.EntitySilverfish;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Silverfish;

public class CraftSilverfish extends CraftMonster implements Silverfish {
   public CraftSilverfish(CraftServer server, EntitySilverfish entity) {
      super(server, entity);
   }

   public EntitySilverfish getHandle() {
      return (EntitySilverfish)this.entity;
   }

   @Override
   public String toString() {
      return "CraftSilverfish";
   }

   @Override
   public EntityType getType() {
      return EntityType.SILVERFISH;
   }
}
