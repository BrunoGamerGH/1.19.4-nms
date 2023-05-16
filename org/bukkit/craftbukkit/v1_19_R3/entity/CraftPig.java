package org.bukkit.craftbukkit.v1_19_R3.entity;

import com.google.common.base.Preconditions;
import net.minecraft.world.entity.animal.EntityPig;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Pig;

public class CraftPig extends CraftAnimals implements Pig {
   public CraftPig(CraftServer server, EntityPig entity) {
      super(server, entity);
   }

   public boolean hasSaddle() {
      return this.getHandle().i();
   }

   public void setSaddle(boolean saddled) {
      this.getHandle().bW.a(saddled);
   }

   public int getBoostTicks() {
      return this.getHandle().bW.f ? this.getHandle().bW.e() : 0;
   }

   public void setBoostTicks(int ticks) {
      Preconditions.checkArgument(ticks >= 0, "ticks must be >= 0");
      this.getHandle().bW.setBoostTicks(ticks);
   }

   public int getCurrentBoostTicks() {
      return this.getHandle().bW.f ? this.getHandle().bW.g : 0;
   }

   public void setCurrentBoostTicks(int ticks) {
      if (this.getHandle().bW.f) {
         int max = this.getHandle().bW.e();
         Preconditions.checkArgument(ticks >= 0 && ticks <= max, "boost ticks must not exceed 0 or %d (inclusive)", max);
         this.getHandle().bW.g = ticks;
      }
   }

   public Material getSteerMaterial() {
      return Material.CARROT_ON_A_STICK;
   }

   public EntityPig getHandle() {
      return (EntityPig)this.entity;
   }

   @Override
   public String toString() {
      return "CraftPig";
   }

   @Override
   public EntityType getType() {
      return EntityType.PIG;
   }
}
