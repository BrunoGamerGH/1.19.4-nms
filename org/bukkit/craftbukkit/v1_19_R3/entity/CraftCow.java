package org.bukkit.craftbukkit.v1_19_R3.entity;

import net.minecraft.world.entity.animal.EntityCow;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.entity.Cow;
import org.bukkit.entity.EntityType;

public class CraftCow extends CraftAnimals implements Cow {
   public CraftCow(CraftServer server, EntityCow entity) {
      super(server, entity);
   }

   public EntityCow getHandle() {
      return (EntityCow)this.entity;
   }

   @Override
   public String toString() {
      return "CraftCow";
   }

   @Override
   public EntityType getType() {
      return EntityType.COW;
   }
}
