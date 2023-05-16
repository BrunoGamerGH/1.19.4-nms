package org.bukkit.craftbukkit.v1_19_R3.entity;

import net.minecraft.world.entity.monster.EntityEndermite;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.entity.Endermite;
import org.bukkit.entity.EntityType;

public class CraftEndermite extends CraftMonster implements Endermite {
   public CraftEndermite(CraftServer server, EntityEndermite entity) {
      super(server, entity);
   }

   public EntityEndermite getHandle() {
      return (EntityEndermite)super.getHandle();
   }

   @Override
   public String toString() {
      return "CraftEndermite";
   }

   @Override
   public EntityType getType() {
      return EntityType.ENDERMITE;
   }

   public boolean isPlayerSpawned() {
      return false;
   }

   public void setPlayerSpawned(boolean playerSpawned) {
   }
}
