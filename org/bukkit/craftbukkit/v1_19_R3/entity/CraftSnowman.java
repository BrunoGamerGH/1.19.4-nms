package org.bukkit.craftbukkit.v1_19_R3.entity;

import net.minecraft.world.entity.animal.EntitySnowman;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Snowman;

public class CraftSnowman extends CraftGolem implements Snowman {
   public CraftSnowman(CraftServer server, EntitySnowman entity) {
      super(server, entity);
   }

   public boolean isDerp() {
      return !this.getHandle().r();
   }

   public void setDerp(boolean derpMode) {
      this.getHandle().w(!derpMode);
   }

   public EntitySnowman getHandle() {
      return (EntitySnowman)this.entity;
   }

   @Override
   public String toString() {
      return "CraftSnowman";
   }

   @Override
   public EntityType getType() {
      return EntityType.SNOWMAN;
   }
}
