package org.bukkit.craftbukkit.v1_19_R3.entity;

import net.minecraft.world.entity.monster.EntityGhast;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Ghast;

public class CraftGhast extends CraftFlying implements Ghast, CraftEnemy {
   public CraftGhast(CraftServer server, EntityGhast entity) {
      super(server, entity);
   }

   public EntityGhast getHandle() {
      return (EntityGhast)this.entity;
   }

   @Override
   public String toString() {
      return "CraftGhast";
   }

   @Override
   public EntityType getType() {
      return EntityType.GHAST;
   }

   public boolean isCharging() {
      return this.getHandle().q();
   }

   public void setCharging(boolean flag) {
      this.getHandle().w(flag);
   }
}
