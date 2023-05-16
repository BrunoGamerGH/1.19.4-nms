package org.bukkit.craftbukkit.v1_19_R3.entity;

import net.minecraft.world.entity.boss.EntityComplexPart;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.entity.ComplexEntityPart;
import org.bukkit.entity.ComplexLivingEntity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityDamageEvent;

public class CraftComplexPart extends CraftEntity implements ComplexEntityPart {
   public CraftComplexPart(CraftServer server, EntityComplexPart entity) {
      super(server, entity);
   }

   public ComplexLivingEntity getParent() {
      return (ComplexLivingEntity)this.getHandle().b.getBukkitEntity();
   }

   @Override
   public void setLastDamageCause(EntityDamageEvent cause) {
      this.getParent().setLastDamageCause(cause);
   }

   @Override
   public EntityDamageEvent getLastDamageCause() {
      return this.getParent().getLastDamageCause();
   }

   @Override
   public boolean isValid() {
      return this.getParent().isValid();
   }

   public EntityComplexPart getHandle() {
      return (EntityComplexPart)this.entity;
   }

   @Override
   public String toString() {
      return "CraftComplexPart";
   }

   public EntityType getType() {
      return EntityType.UNKNOWN;
   }
}
