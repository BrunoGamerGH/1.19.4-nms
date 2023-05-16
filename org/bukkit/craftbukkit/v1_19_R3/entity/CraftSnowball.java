package org.bukkit.craftbukkit.v1_19_R3.entity;

import net.minecraft.world.entity.projectile.EntitySnowball;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Snowball;

public class CraftSnowball extends CraftThrowableProjectile implements Snowball {
   public CraftSnowball(CraftServer server, EntitySnowball entity) {
      super(server, entity);
   }

   public EntitySnowball getHandle() {
      return (EntitySnowball)this.entity;
   }

   @Override
   public String toString() {
      return "CraftSnowball";
   }

   public EntityType getType() {
      return EntityType.SNOWBALL;
   }
}
