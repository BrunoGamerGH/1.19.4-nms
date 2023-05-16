package org.bukkit.craftbukkit.v1_19_R3.entity;

import net.minecraft.world.entity.animal.EntityIronGolem;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.IronGolem;

public class CraftIronGolem extends CraftGolem implements IronGolem {
   public CraftIronGolem(CraftServer server, EntityIronGolem entity) {
      super(server, entity);
   }

   public EntityIronGolem getHandle() {
      return (EntityIronGolem)this.entity;
   }

   @Override
   public String toString() {
      return "CraftIronGolem";
   }

   public boolean isPlayerCreated() {
      return this.getHandle().fT();
   }

   public void setPlayerCreated(boolean playerCreated) {
      this.getHandle().x(playerCreated);
   }

   @Override
   public EntityType getType() {
      return EntityType.IRON_GOLEM;
   }
}
