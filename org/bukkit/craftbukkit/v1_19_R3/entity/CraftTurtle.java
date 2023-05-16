package org.bukkit.craftbukkit.v1_19_R3.entity;

import net.minecraft.world.entity.animal.EntityTurtle;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Turtle;

public class CraftTurtle extends CraftAnimals implements Turtle {
   public CraftTurtle(CraftServer server, EntityTurtle entity) {
      super(server, entity);
   }

   public EntityTurtle getHandle() {
      return (EntityTurtle)super.getHandle();
   }

   @Override
   public String toString() {
      return "CraftTurtle";
   }

   @Override
   public EntityType getType() {
      return EntityType.TURTLE;
   }

   public boolean hasEgg() {
      return this.getHandle().q();
   }

   public boolean isLayingEgg() {
      return this.getHandle().r();
   }
}
