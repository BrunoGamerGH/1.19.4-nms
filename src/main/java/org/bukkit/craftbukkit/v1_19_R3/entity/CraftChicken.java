package org.bukkit.craftbukkit.v1_19_R3.entity;

import net.minecraft.world.entity.animal.EntityChicken;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.EntityType;

public class CraftChicken extends CraftAnimals implements Chicken {
   public CraftChicken(CraftServer server, EntityChicken entity) {
      super(server, entity);
   }

   public EntityChicken getHandle() {
      return (EntityChicken)this.entity;
   }

   @Override
   public String toString() {
      return "CraftChicken";
   }

   @Override
   public EntityType getType() {
      return EntityType.CHICKEN;
   }
}
