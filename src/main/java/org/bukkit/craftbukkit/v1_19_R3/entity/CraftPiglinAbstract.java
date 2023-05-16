package org.bukkit.craftbukkit.v1_19_R3.entity;

import com.google.common.base.Preconditions;
import net.minecraft.world.entity.monster.piglin.EntityPiglinAbstract;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.entity.PiglinAbstract;

public class CraftPiglinAbstract extends CraftMonster implements PiglinAbstract {
   public CraftPiglinAbstract(CraftServer server, EntityPiglinAbstract entity) {
      super(server, entity);
   }

   public boolean isImmuneToZombification() {
      return this.getHandle().r();
   }

   public void setImmuneToZombification(boolean flag) {
      this.getHandle().w(flag);
   }

   public int getConversionTime() {
      Preconditions.checkState(this.isConverting(), "Entity not converting");
      return this.getHandle().e;
   }

   public void setConversionTime(int time) {
      if (time < 0) {
         this.getHandle().e = -1;
         this.getHandle().w(false);
      } else {
         this.getHandle().e = time;
      }
   }

   public boolean isConverting() {
      return this.getHandle().fS();
   }

   public boolean isBaby() {
      return this.getHandle().y_();
   }

   public void setBaby(boolean flag) {
      this.getHandle().a(flag);
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

   public EntityPiglinAbstract getHandle() {
      return (EntityPiglinAbstract)super.getHandle();
   }
}
