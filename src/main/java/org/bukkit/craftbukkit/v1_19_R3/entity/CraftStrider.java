package org.bukkit.craftbukkit.v1_19_R3.entity;

import com.google.common.base.Preconditions;
import net.minecraft.world.entity.monster.EntityStrider;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Strider;

public class CraftStrider extends CraftAnimals implements Strider {
   public CraftStrider(CraftServer server, EntityStrider entity) {
      super(server, entity);
   }

   public boolean isShivering() {
      return this.getHandle().q();
   }

   public void setShivering(boolean shivering) {
      this.getHandle().w(shivering);
   }

   public boolean hasSaddle() {
      return this.getHandle().i();
   }

   public void setSaddle(boolean saddled) {
      this.getHandle().cc.a(saddled);
   }

   public int getBoostTicks() {
      return this.getHandle().cc.f ? this.getHandle().cc.e() : 0;
   }

   public void setBoostTicks(int ticks) {
      Preconditions.checkArgument(ticks >= 0, "ticks must be >= 0");
      this.getHandle().cc.setBoostTicks(ticks);
   }

   public int getCurrentBoostTicks() {
      return this.getHandle().cc.f ? this.getHandle().cc.g : 0;
   }

   public void setCurrentBoostTicks(int ticks) {
      if (this.getHandle().cc.f) {
         int max = this.getHandle().cc.e();
         Preconditions.checkArgument(ticks >= 0 && ticks <= max, "boost ticks must not exceed 0 or %d (inclusive)", max);
         this.getHandle().cc.g = ticks;
      }
   }

   public Material getSteerMaterial() {
      return Material.WARPED_FUNGUS_ON_A_STICK;
   }

   public EntityStrider getHandle() {
      return (EntityStrider)this.entity;
   }

   @Override
   public String toString() {
      return "CraftStrider";
   }

   @Override
   public EntityType getType() {
      return EntityType.STRIDER;
   }
}
