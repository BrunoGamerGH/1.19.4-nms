package org.bukkit.craftbukkit.v1_19_R3.entity;

import net.minecraft.world.entity.animal.EntityFish;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.entity.Fish;

public class CraftFish extends CraftWaterMob implements Fish {
   public CraftFish(CraftServer server, EntityFish entity) {
      super(server, entity);
   }

   public EntityFish getHandle() {
      return (EntityFish)this.entity;
   }

   @Override
   public String toString() {
      return "CraftFish";
   }
}
