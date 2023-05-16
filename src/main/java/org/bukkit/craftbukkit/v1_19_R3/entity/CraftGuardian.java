package org.bukkit.craftbukkit.v1_19_R3.entity;

import net.minecraft.world.entity.monster.EntityGuardian;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Guardian;
import org.bukkit.entity.LivingEntity;

public class CraftGuardian extends CraftMonster implements Guardian {
   public CraftGuardian(CraftServer server, EntityGuardian entity) {
      super(server, entity);
   }

   public EntityGuardian getHandle() {
      return (EntityGuardian)super.getHandle();
   }

   @Override
   public String toString() {
      return "CraftGuardian";
   }

   @Override
   public EntityType getType() {
      return EntityType.GUARDIAN;
   }

   @Override
   public void setTarget(LivingEntity target) {
      super.setTarget(target);
      if (target == null) {
         this.getHandle().b(0);
      }
   }

   public boolean setLaser(boolean activated) {
      if (activated) {
         LivingEntity target = this.getTarget();
         if (target == null) {
            return false;
         }

         this.getHandle().b(target.getEntityId());
      } else {
         this.getHandle().b(0);
      }

      return true;
   }

   public boolean hasLaser() {
      return this.getHandle().fU();
   }

   public boolean isElder() {
      return false;
   }

   public void setElder(boolean shouldBeElder) {
      throw new UnsupportedOperationException("Not supported.");
   }
}
