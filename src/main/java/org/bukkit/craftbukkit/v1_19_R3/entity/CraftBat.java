package org.bukkit.craftbukkit.v1_19_R3.entity;

import net.minecraft.world.entity.ambient.EntityBat;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.entity.Bat;
import org.bukkit.entity.EntityType;

public class CraftBat extends CraftAmbient implements Bat {
   public CraftBat(CraftServer server, EntityBat entity) {
      super(server, entity);
   }

   public EntityBat getHandle() {
      return (EntityBat)this.entity;
   }

   @Override
   public String toString() {
      return "CraftBat";
   }

   @Override
   public EntityType getType() {
      return EntityType.BAT;
   }

   public boolean isAwake() {
      return !this.getHandle().r();
   }

   public void setAwake(boolean state) {
      this.getHandle().w(!state);
   }
}
