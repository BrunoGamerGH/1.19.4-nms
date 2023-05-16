package org.bukkit.craftbukkit.v1_19_R3.entity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.EntityLlamaSpit;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LlamaSpit;
import org.bukkit.projectiles.ProjectileSource;

public class CraftLlamaSpit extends AbstractProjectile implements LlamaSpit {
   public CraftLlamaSpit(CraftServer server, EntityLlamaSpit entity) {
      super(server, entity);
   }

   public EntityLlamaSpit getHandle() {
      return (EntityLlamaSpit)super.getHandle();
   }

   @Override
   public String toString() {
      return "CraftLlamaSpit";
   }

   public EntityType getType() {
      return EntityType.LLAMA_SPIT;
   }

   public ProjectileSource getShooter() {
      return this.getHandle().v() != null ? (ProjectileSource)this.getHandle().v().getBukkitEntity() : null;
   }

   public void setShooter(ProjectileSource source) {
      this.getHandle().b((Entity)(source != null ? ((CraftLivingEntity)source).getHandle() : null));
   }
}
