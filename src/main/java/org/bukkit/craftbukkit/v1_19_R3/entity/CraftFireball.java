package org.bukkit.craftbukkit.v1_19_R3.entity;

import net.minecraft.world.entity.projectile.EntityFireball;
import org.apache.commons.lang.Validate;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;

public class CraftFireball extends AbstractProjectile implements Fireball {
   public CraftFireball(CraftServer server, EntityFireball entity) {
      super(server, entity);
   }

   public float getYield() {
      return this.getHandle().bukkitYield;
   }

   public boolean isIncendiary() {
      return this.getHandle().isIncendiary;
   }

   public void setIsIncendiary(boolean isIncendiary) {
      this.getHandle().isIncendiary = isIncendiary;
   }

   public void setYield(float yield) {
      this.getHandle().bukkitYield = yield;
   }

   public ProjectileSource getShooter() {
      return this.getHandle().projectileSource;
   }

   public void setShooter(ProjectileSource shooter) {
      if (shooter instanceof CraftLivingEntity) {
         this.getHandle().b(((CraftLivingEntity)shooter).getHandle());
      } else {
         this.getHandle().b(null);
      }

      this.getHandle().projectileSource = shooter;
   }

   public Vector getDirection() {
      return new Vector(this.getHandle().b, this.getHandle().c, this.getHandle().d);
   }

   public void setDirection(Vector direction) {
      Validate.notNull(direction, "Direction can not be null");
      this.getHandle().setDirection(direction.getX(), direction.getY(), direction.getZ());
      this.update();
   }

   public EntityFireball getHandle() {
      return (EntityFireball)this.entity;
   }

   @Override
   public String toString() {
      return "CraftFireball";
   }

   public EntityType getType() {
      return EntityType.UNKNOWN;
   }
}
