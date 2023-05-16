package org.bukkit.craftbukkit.v1_19_R3.entity;

import net.minecraft.world.entity.animal.EntityWaterAnimal;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.entity.WaterMob;

public class CraftWaterMob extends CraftCreature implements WaterMob {
   public CraftWaterMob(CraftServer server, EntityWaterAnimal entity) {
      super(server, entity);
   }

   public EntityWaterAnimal getHandle() {
      return (EntityWaterAnimal)this.entity;
   }

   @Override
   public String toString() {
      return "CraftWaterMob";
   }
}
