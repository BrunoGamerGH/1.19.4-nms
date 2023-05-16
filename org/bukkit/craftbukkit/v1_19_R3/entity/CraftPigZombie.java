package org.bukkit.craftbukkit.v1_19_R3.entity;

import net.minecraft.world.entity.monster.EntityPigZombie;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.PigZombie;

public class CraftPigZombie extends CraftZombie implements PigZombie {
   public CraftPigZombie(CraftServer server, EntityPigZombie entity) {
      super(server, entity);
   }

   public int getAnger() {
      return this.getHandle().a();
   }

   public void setAnger(int level) {
      this.getHandle().a(level);
   }

   public void setAngry(boolean angry) {
      this.setAnger(angry ? 400 : 0);
   }

   public boolean isAngry() {
      return this.getAnger() > 0;
   }

   public EntityPigZombie getHandle() {
      return (EntityPigZombie)this.entity;
   }

   @Override
   public String toString() {
      return "CraftPigZombie";
   }

   @Override
   public EntityType getType() {
      return EntityType.ZOMBIFIED_PIGLIN;
   }

   @Override
   public boolean isConverting() {
      return false;
   }

   @Override
   public int getConversionTime() {
      throw new UnsupportedOperationException("Not supported by this Entity.");
   }

   @Override
   public void setConversionTime(int time) {
      throw new UnsupportedOperationException("Not supported by this Entity.");
   }
}
