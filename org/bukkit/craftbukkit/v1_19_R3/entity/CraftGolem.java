package org.bukkit.craftbukkit.v1_19_R3.entity;

import net.minecraft.world.entity.animal.EntityGolem;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.entity.Golem;

public class CraftGolem extends CraftCreature implements Golem {
   public CraftGolem(CraftServer server, EntityGolem entity) {
      super(server, entity);
   }

   public EntityGolem getHandle() {
      return (EntityGolem)this.entity;
   }

   @Override
   public String toString() {
      return "CraftGolem";
   }
}
