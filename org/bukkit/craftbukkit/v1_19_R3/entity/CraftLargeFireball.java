package org.bukkit.craftbukkit.v1_19_R3.entity;

import net.minecraft.world.entity.projectile.EntityLargeFireball;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LargeFireball;

public class CraftLargeFireball extends CraftSizedFireball implements LargeFireball {
   public CraftLargeFireball(CraftServer server, EntityLargeFireball entity) {
      super(server, entity);
   }

   @Override
   public void setYield(float yield) {
      super.setYield(yield);
      this.getHandle().e = (int)yield;
   }

   public EntityLargeFireball getHandle() {
      return (EntityLargeFireball)this.entity;
   }

   @Override
   public String toString() {
      return "CraftLargeFireball";
   }

   @Override
   public EntityType getType() {
      return EntityType.FIREBALL;
   }
}
