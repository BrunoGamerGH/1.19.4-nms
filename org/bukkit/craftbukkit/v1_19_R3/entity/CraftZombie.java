package org.bukkit.craftbukkit.v1_19_R3.entity;

import com.google.common.base.Preconditions;
import net.minecraft.world.entity.monster.EntityZombie;
import net.minecraft.world.entity.monster.EntityZombieVillager;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Zombie;
import org.bukkit.entity.Villager.Profession;

public class CraftZombie extends CraftMonster implements Zombie {
   public CraftZombie(CraftServer server, EntityZombie entity) {
      super(server, entity);
   }

   public EntityZombie getHandle() {
      return (EntityZombie)this.entity;
   }

   @Override
   public String toString() {
      return "CraftZombie";
   }

   @Override
   public EntityType getType() {
      return EntityType.ZOMBIE;
   }

   public boolean isBaby() {
      return this.getHandle().y_();
   }

   public void setBaby(boolean flag) {
      this.getHandle().a(flag);
   }

   public boolean isVillager() {
      return this.getHandle() instanceof EntityZombieVillager;
   }

   public void setVillager(boolean flag) {
      throw new UnsupportedOperationException("Not supported.");
   }

   public void setVillagerProfession(Profession profession) {
      throw new UnsupportedOperationException("Not supported.");
   }

   public Profession getVillagerProfession() {
      return null;
   }

   public boolean isConverting() {
      return this.getHandle().fZ();
   }

   public int getConversionTime() {
      Preconditions.checkState(this.isConverting(), "Entity not converting");
      return this.getHandle().cd;
   }

   public void setConversionTime(int time) {
      if (time < 0) {
         this.getHandle().cd = -1;
         this.getHandle().aj().b(EntityZombie.bX, false);
      } else {
         this.getHandle().b(time);
      }
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

   public boolean canBreakDoors() {
      return this.getHandle().ga();
   }

   public void setCanBreakDoors(boolean flag) {
      this.getHandle().x(flag);
   }
}
