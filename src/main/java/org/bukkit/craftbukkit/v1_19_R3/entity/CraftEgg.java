package org.bukkit.craftbukkit.v1_19_R3.entity;

import net.minecraft.world.entity.projectile.EntityEgg;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.entity.Egg;
import org.bukkit.entity.EntityType;

public class CraftEgg extends CraftThrowableProjectile implements Egg {
   public CraftEgg(CraftServer server, EntityEgg entity) {
      super(server, entity);
   }

   public EntityEgg getHandle() {
      return (EntityEgg)this.entity;
   }

   @Override
   public String toString() {
      return "CraftEgg";
   }

   public EntityType getType() {
      return EntityType.EGG;
   }
}
