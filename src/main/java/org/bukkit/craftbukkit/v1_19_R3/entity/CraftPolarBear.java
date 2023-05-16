package org.bukkit.craftbukkit.v1_19_R3.entity;

import net.minecraft.world.entity.animal.EntityPolarBear;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.PolarBear;

public class CraftPolarBear extends CraftAnimals implements PolarBear {
   public CraftPolarBear(CraftServer server, EntityPolarBear entity) {
      super(server, entity);
   }

   public EntityPolarBear getHandle() {
      return (EntityPolarBear)this.entity;
   }

   @Override
   public String toString() {
      return "CraftPolarBear";
   }

   @Override
   public EntityType getType() {
      return EntityType.POLAR_BEAR;
   }
}
