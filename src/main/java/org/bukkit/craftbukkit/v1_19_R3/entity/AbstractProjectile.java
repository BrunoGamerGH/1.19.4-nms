package org.bukkit.craftbukkit.v1_19_R3.entity;

import net.minecraft.world.entity.Entity;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.entity.Projectile;

public abstract class AbstractProjectile extends CraftEntity implements Projectile {
   private boolean doesBounce = false;

   public AbstractProjectile(CraftServer server, Entity entity) {
      super(server, entity);
   }

   public boolean doesBounce() {
      return this.doesBounce;
   }

   public void setBounce(boolean doesBounce) {
      this.doesBounce = doesBounce;
   }
}
