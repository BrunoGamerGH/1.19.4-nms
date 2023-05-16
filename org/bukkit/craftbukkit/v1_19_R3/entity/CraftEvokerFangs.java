package org.bukkit.craftbukkit.v1_19_R3.entity;

import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.projectile.EntityEvokerFangs;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.EvokerFangs;
import org.bukkit.entity.LivingEntity;

public class CraftEvokerFangs extends CraftEntity implements EvokerFangs {
   public CraftEvokerFangs(CraftServer server, EntityEvokerFangs entity) {
      super(server, entity);
   }

   public EntityEvokerFangs getHandle() {
      return (EntityEvokerFangs)super.getHandle();
   }

   @Override
   public String toString() {
      return "CraftEvokerFangs";
   }

   public EntityType getType() {
      return EntityType.EVOKER_FANGS;
   }

   public LivingEntity getOwner() {
      EntityLiving owner = this.getHandle().i();
      return owner == null ? null : (LivingEntity)owner.getBukkitEntity();
   }

   public void setOwner(LivingEntity owner) {
      this.getHandle().a(owner == null ? null : ((CraftLivingEntity)owner).getHandle());
   }
}
