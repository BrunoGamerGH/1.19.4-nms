package org.bukkit.craftbukkit.v1_19_R3.entity;

import net.minecraft.world.entity.projectile.EntityThrownExpBottle;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ThrownExpBottle;

public class CraftThrownExpBottle extends CraftThrowableProjectile implements ThrownExpBottle {
   public CraftThrownExpBottle(CraftServer server, EntityThrownExpBottle entity) {
      super(server, entity);
   }

   public EntityThrownExpBottle getHandle() {
      return (EntityThrownExpBottle)this.entity;
   }

   @Override
   public String toString() {
      return "EntityThrownExpBottle";
   }

   public EntityType getType() {
      return EntityType.THROWN_EXP_BOTTLE;
   }
}
