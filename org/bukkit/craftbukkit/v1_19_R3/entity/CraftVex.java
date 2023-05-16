package org.bukkit.craftbukkit.v1_19_R3.entity;

import com.google.common.base.Preconditions;
import net.minecraft.core.BlockPosition;
import net.minecraft.world.entity.monster.EntityVex;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Vex;

public class CraftVex extends CraftMonster implements Vex {
   public CraftVex(CraftServer server, EntityVex entity) {
      super(server, entity);
   }

   public EntityVex getHandle() {
      return (EntityVex)super.getHandle();
   }

   @Override
   public String toString() {
      return "CraftVex";
   }

   @Override
   public EntityType getType() {
      return EntityType.VEX;
   }

   public boolean isCharging() {
      return this.getHandle().fS();
   }

   public void setCharging(boolean charging) {
      this.getHandle().w(charging);
   }

   public Location getBound() {
      BlockPosition blockPosition = this.getHandle().w();
      return blockPosition == null ? null : new Location(this.getWorld(), (double)blockPosition.u(), (double)blockPosition.v(), (double)blockPosition.w());
   }

   public void setBound(Location location) {
      if (location == null) {
         this.getHandle().g(null);
      } else {
         Preconditions.checkArgument(this.getWorld().equals(location.getWorld()), "The bound world cannot be different to the entity's world.");
         this.getHandle().g(new BlockPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ()));
      }
   }

   public int getLifeTicks() {
      return this.getHandle().bW;
   }

   public void setLifeTicks(int lifeTicks) {
      this.getHandle().b(lifeTicks);
      if (lifeTicks < 0) {
         this.getHandle().bV = false;
      }
   }

   public boolean hasLimitedLife() {
      return this.getHandle().bV;
   }
}
