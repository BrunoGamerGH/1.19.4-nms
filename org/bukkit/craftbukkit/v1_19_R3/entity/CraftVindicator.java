package org.bukkit.craftbukkit.v1_19_R3.entity;

import net.minecraft.world.entity.monster.EntityVindicator;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Vindicator;

public class CraftVindicator extends CraftIllager implements Vindicator {
   public CraftVindicator(CraftServer server, EntityVindicator entity) {
      super(server, entity);
   }

   public EntityVindicator getHandle() {
      return (EntityVindicator)super.getHandle();
   }

   @Override
   public String toString() {
      return "CraftVindicator";
   }

   @Override
   public EntityType getType() {
      return EntityType.VINDICATOR;
   }

   public boolean isJohnny() {
      return this.getHandle().bS;
   }

   public void setJohnny(boolean johnny) {
      this.getHandle().bS = johnny;
   }
}
