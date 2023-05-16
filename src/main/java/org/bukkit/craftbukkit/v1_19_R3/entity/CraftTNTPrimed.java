package org.bukkit.craftbukkit.v1_19_R3.entity;

import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.item.EntityTNTPrimed;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.TNTPrimed;

public class CraftTNTPrimed extends CraftEntity implements TNTPrimed {
   public CraftTNTPrimed(CraftServer server, EntityTNTPrimed entity) {
      super(server, entity);
   }

   public float getYield() {
      return this.getHandle().yield;
   }

   public boolean isIncendiary() {
      return this.getHandle().isIncendiary;
   }

   public void setIsIncendiary(boolean isIncendiary) {
      this.getHandle().isIncendiary = isIncendiary;
   }

   public void setYield(float yield) {
      this.getHandle().yield = yield;
   }

   public int getFuseTicks() {
      return this.getHandle().j();
   }

   public void setFuseTicks(int fuseTicks) {
      this.getHandle().b(fuseTicks);
   }

   public EntityTNTPrimed getHandle() {
      return (EntityTNTPrimed)this.entity;
   }

   @Override
   public String toString() {
      return "CraftTNTPrimed";
   }

   public EntityType getType() {
      return EntityType.PRIMED_TNT;
   }

   public Entity getSource() {
      EntityLiving source = this.getHandle().i();
      return source != null ? source.getBukkitEntity() : null;
   }

   public void setSource(Entity source) {
      if (source instanceof LivingEntity) {
         this.getHandle().d = ((CraftLivingEntity)source).getHandle();
      } else {
         this.getHandle().d = null;
      }
   }
}
