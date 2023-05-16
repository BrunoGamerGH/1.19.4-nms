package org.bukkit.craftbukkit.v1_19_R3.entity;

import net.minecraft.world.entity.monster.EntityZoglin;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Zoglin;

public class CraftZoglin extends CraftMonster implements Zoglin {
   public CraftZoglin(CraftServer server, EntityZoglin entity) {
      super(server, entity);
   }

   public boolean isBaby() {
      return this.getHandle().y_();
   }

   public void setBaby(boolean flag) {
      this.getHandle().a(flag);
   }

   public EntityZoglin getHandle() {
      return (EntityZoglin)this.entity;
   }

   @Override
   public String toString() {
      return "CraftZoglin";
   }

   @Override
   public EntityType getType() {
      return EntityType.ZOGLIN;
   }

   public int getAge() {
      return this.getHandle().y_() ? -1 : 0;
   }

   public void setAge(int i) {
      this.getHandle().a(i < 0);
   }

   public void setAgeLock(boolean b) {
   }

   public boolean getAgeLock() {
      return false;
   }

   public void setBaby() {
      this.getHandle().a(true);
   }

   public void setAdult() {
      this.getHandle().a(false);
   }

   public boolean isAdult() {
      return !this.getHandle().y_();
   }

   public boolean canBreed() {
      return false;
   }

   public void setBreed(boolean b) {
   }
}
