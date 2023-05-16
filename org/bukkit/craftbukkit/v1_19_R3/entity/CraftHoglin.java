package org.bukkit.craftbukkit.v1_19_R3.entity;

import com.google.common.base.Preconditions;
import net.minecraft.world.entity.monster.hoglin.EntityHoglin;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Hoglin;

public class CraftHoglin extends CraftAnimals implements Hoglin, CraftEnemy {
   public CraftHoglin(CraftServer server, EntityHoglin entity) {
      super(server, entity);
   }

   public boolean isImmuneToZombification() {
      return this.getHandle().fZ();
   }

   public void setImmuneToZombification(boolean flag) {
      this.getHandle().w(flag);
   }

   public boolean isAbleToBeHunted() {
      return this.getHandle().cg;
   }

   public void setIsAbleToBeHunted(boolean flag) {
      this.getHandle().cg = flag;
   }

   public int getConversionTime() {
      Preconditions.checkState(this.isConverting(), "Entity not converting");
      return this.getHandle().cf;
   }

   public void setConversionTime(int time) {
      if (time < 0) {
         this.getHandle().cf = -1;
         this.getHandle().w(false);
      } else {
         this.getHandle().cf = time;
      }
   }

   public boolean isConverting() {
      return this.getHandle().w();
   }

   public EntityHoglin getHandle() {
      return (EntityHoglin)this.entity;
   }

   @Override
   public String toString() {
      return "CraftHoglin";
   }

   @Override
   public EntityType getType() {
      return EntityType.HOGLIN;
   }
}
