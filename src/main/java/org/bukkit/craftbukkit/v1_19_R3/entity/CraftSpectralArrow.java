package org.bukkit.craftbukkit.v1_19_R3.entity;

import net.minecraft.world.entity.projectile.EntitySpectralArrow;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.SpectralArrow;

public class CraftSpectralArrow extends CraftArrow implements SpectralArrow {
   public CraftSpectralArrow(CraftServer server, EntitySpectralArrow entity) {
      super(server, entity);
   }

   public EntitySpectralArrow getHandle() {
      return (EntitySpectralArrow)this.entity;
   }

   @Override
   public String toString() {
      return "CraftSpectralArrow";
   }

   @Override
   public EntityType getType() {
      return EntityType.SPECTRAL_ARROW;
   }

   public int getGlowingTicks() {
      return this.getHandle().f;
   }

   public void setGlowingTicks(int duration) {
      this.getHandle().f = duration;
   }
}
