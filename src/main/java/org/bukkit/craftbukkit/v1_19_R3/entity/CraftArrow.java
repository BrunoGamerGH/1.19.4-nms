package org.bukkit.craftbukkit.v1_19_R3.entity;

import com.google.common.base.Preconditions;
import net.minecraft.core.BlockPosition;
import net.minecraft.world.entity.projectile.EntityArrow;
import org.apache.commons.lang.Validate;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.AbstractArrow.PickupStatus;
import org.bukkit.projectiles.ProjectileSource;

public class CraftArrow extends AbstractProjectile implements AbstractArrow {
   public CraftArrow(CraftServer server, EntityArrow entity) {
      super(server, entity);
   }

   public void setKnockbackStrength(int knockbackStrength) {
      Validate.isTrue(knockbackStrength >= 0, "Knockback cannot be negative");
      this.getHandle().b(knockbackStrength);
   }

   public int getKnockbackStrength() {
      return this.getHandle().o;
   }

   public double getDamage() {
      return this.getHandle().p();
   }

   public void setDamage(double damage) {
      Preconditions.checkArgument(damage >= 0.0, "Damage must be positive");
      this.getHandle().h(damage);
   }

   public int getPierceLevel() {
      return this.getHandle().t();
   }

   public void setPierceLevel(int pierceLevel) {
      Preconditions.checkArgument(pierceLevel >= 0 && pierceLevel <= 127, "Pierce level out of range, expected 0 < level < 127");
      this.getHandle().a((byte)pierceLevel);
   }

   public boolean isCritical() {
      return this.getHandle().r();
   }

   public void setCritical(boolean critical) {
      this.getHandle().a(critical);
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

   public boolean isInBlock() {
      return this.getHandle().b;
   }

   public Block getAttachedBlock() {
      if (!this.isInBlock()) {
         return null;
      } else {
         BlockPosition pos = this.getHandle().dg();
         return this.getWorld().getBlockAt(pos.u(), pos.v(), pos.w());
      }
   }

   public PickupStatus getPickupStatus() {
      return PickupStatus.values()[this.getHandle().d.ordinal()];
   }

   public void setPickupStatus(PickupStatus status) {
      Preconditions.checkNotNull(status, "status");
      this.getHandle().d = EntityArrow.PickupStatus.a(status.ordinal());
   }

   @Override
   public void setTicksLived(int value) {
      super.setTicksLived(value);
      this.getHandle().m = value;
   }

   public boolean isShotFromCrossbow() {
      return this.getHandle().s();
   }

   public void setShotFromCrossbow(boolean shotFromCrossbow) {
      this.getHandle().q(shotFromCrossbow);
   }

   public EntityArrow getHandle() {
      return (EntityArrow)this.entity;
   }

   @Override
   public String toString() {
      return "CraftArrow";
   }

   public EntityType getType() {
      return EntityType.UNKNOWN;
   }
}
