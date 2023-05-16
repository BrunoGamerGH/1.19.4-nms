package org.bukkit.craftbukkit.v1_19_R3.entity;

import com.google.common.base.Preconditions;
import net.minecraft.world.entity.projectile.EntityShulkerBullet;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ShulkerBullet;
import org.bukkit.projectiles.ProjectileSource;

public class CraftShulkerBullet extends AbstractProjectile implements ShulkerBullet {
   public CraftShulkerBullet(CraftServer server, EntityShulkerBullet entity) {
      super(server, entity);
   }

   public ProjectileSource getShooter() {
      return this.getHandle().projectileSource;
   }

   public void setShooter(ProjectileSource shooter) {
      if (shooter instanceof Entity) {
         this.getHandle().b(((CraftEntity)shooter).getHandle());
      } else {
         this.getHandle().b(null);
      }

      this.getHandle().projectileSource = shooter;
   }

   public Entity getTarget() {
      return this.getHandle().getTarget() != null ? this.getHandle().getTarget().getBukkitEntity() : null;
   }

   public void setTarget(Entity target) {
      Preconditions.checkState(!this.getHandle().generation, "Cannot set target during world generation");
      this.getHandle().setTarget(target == null ? null : ((CraftEntity)target).getHandle());
   }

   @Override
   public String toString() {
      return "CraftShulkerBullet";
   }

   public EntityType getType() {
      return EntityType.SHULKER_BULLET;
   }

   public EntityShulkerBullet getHandle() {
      return (EntityShulkerBullet)this.entity;
   }
}
