package org.bukkit.craftbukkit.v1_19_R3.entity;

import net.minecraft.world.entity.animal.EntityPufferFish;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.PufferFish;

public class CraftPufferFish extends CraftFish implements PufferFish {
   public CraftPufferFish(CraftServer server, EntityPufferFish entity) {
      super(server, entity);
   }

   public EntityPufferFish getHandle() {
      return (EntityPufferFish)super.getHandle();
   }

   public int getPuffState() {
      return this.getHandle().fU();
   }

   public void setPuffState(int state) {
      this.getHandle().c(state);
   }

   @Override
   public String toString() {
      return "CraftPufferFish";
   }

   @Override
   public EntityType getType() {
      return EntityType.PUFFERFISH;
   }
}
