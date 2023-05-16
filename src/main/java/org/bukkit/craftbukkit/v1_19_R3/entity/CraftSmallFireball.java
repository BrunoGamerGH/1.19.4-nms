package org.bukkit.craftbukkit.v1_19_R3.entity;

import net.minecraft.world.entity.projectile.EntitySmallFireball;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.SmallFireball;

public class CraftSmallFireball extends CraftSizedFireball implements SmallFireball {
   public CraftSmallFireball(CraftServer server, EntitySmallFireball entity) {
      super(server, entity);
   }

   public EntitySmallFireball getHandle() {
      return (EntitySmallFireball)this.entity;
   }

   @Override
   public String toString() {
      return "CraftSmallFireball";
   }

   @Override
   public EntityType getType() {
      return EntityType.SMALL_FIREBALL;
   }
}
