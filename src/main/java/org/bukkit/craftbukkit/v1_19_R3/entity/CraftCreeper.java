package org.bukkit.craftbukkit.v1_19_R3.entity;

import com.google.common.base.Preconditions;
import net.minecraft.world.entity.monster.EntityCreeper;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.CreeperPowerEvent;
import org.bukkit.event.entity.CreeperPowerEvent.PowerCause;

public class CraftCreeper extends CraftMonster implements Creeper {
   public CraftCreeper(CraftServer server, EntityCreeper entity) {
      super(server, entity);
   }

   public boolean isPowered() {
      return this.getHandle().a();
   }

   public void setPowered(boolean powered) {
      PowerCause cause = powered ? PowerCause.SET_ON : PowerCause.SET_OFF;
      if (this.getHandle().generation || !this.callPowerEvent(cause)) {
         this.getHandle().setPowered(powered);
      }
   }

   private boolean callPowerEvent(PowerCause cause) {
      CreeperPowerEvent event = new CreeperPowerEvent((Creeper)this.getHandle().getBukkitEntity(), cause);
      this.server.getPluginManager().callEvent(event);
      return event.isCancelled();
   }

   public void setMaxFuseTicks(int ticks) {
      Preconditions.checkArgument(ticks >= 0, "ticks < 0");
      this.getHandle().bT = ticks;
   }

   public int getMaxFuseTicks() {
      return this.getHandle().bT;
   }

   public void setFuseTicks(int ticks) {
      Preconditions.checkArgument(ticks >= 0, "ticks < 0");
      Preconditions.checkArgument(ticks <= this.getMaxFuseTicks(), "ticks > maxFuseTicks");
      this.getHandle().bS = ticks;
   }

   public int getFuseTicks() {
      return this.getHandle().bS;
   }

   public void setExplosionRadius(int radius) {
      Preconditions.checkArgument(radius >= 0, "radius < 0");
      this.getHandle().bU = radius;
   }

   public int getExplosionRadius() {
      return this.getHandle().bU;
   }

   public void explode() {
      this.getHandle().fV();
   }

   public void ignite() {
      this.getHandle().fS();
   }

   public EntityCreeper getHandle() {
      return (EntityCreeper)this.entity;
   }

   @Override
   public String toString() {
      return "CraftCreeper";
   }

   @Override
   public EntityType getType() {
      return EntityType.CREEPER;
   }
}
