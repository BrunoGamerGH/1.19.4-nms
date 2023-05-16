package org.bukkit.craftbukkit.v1_19_R3.entity;

import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.projectile.IProjectile;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.entity.Projectile;
import org.bukkit.projectiles.ProjectileSource;

public abstract class CraftProjectile extends AbstractProjectile implements Projectile {
   public CraftProjectile(CraftServer server, IProjectile entity) {
      super(server, entity);
   }

   public ProjectileSource getShooter() {
      return this.getHandle().projectileSource;
   }

   public void setShooter(ProjectileSource shooter) {
      if (shooter instanceof CraftLivingEntity) {
         this.getHandle().b((EntityLiving)((CraftLivingEntity)shooter).entity);
      } else {
         this.getHandle().b(null);
      }

      this.getHandle().projectileSource = shooter;
   }

   public IProjectile getHandle() {
      return (IProjectile)this.entity;
   }

   @Override
   public String toString() {
      return "CraftProjectile";
   }
}
