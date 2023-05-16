package org.bukkit.craftbukkit.v1_19_R3.entity;

import net.minecraft.core.BlockPosition;
import net.minecraft.world.entity.boss.enderdragon.EntityEnderCrystal;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.EntityType;

public class CraftEnderCrystal extends CraftEntity implements EnderCrystal {
   public CraftEnderCrystal(CraftServer server, EntityEnderCrystal entity) {
      super(server, entity);
   }

   public boolean isShowingBottom() {
      return this.getHandle().j();
   }

   public void setShowingBottom(boolean showing) {
      this.getHandle().a(showing);
   }

   public Location getBeamTarget() {
      BlockPosition pos = this.getHandle().i();
      return pos == null ? null : new Location(this.getWorld(), (double)pos.u(), (double)pos.v(), (double)pos.w());
   }

   public void setBeamTarget(Location location) {
      if (location == null) {
         this.getHandle().a(null);
      } else {
         if (location.getWorld() != this.getWorld()) {
            throw new IllegalArgumentException("Cannot set beam target location to different world");
         }

         this.getHandle().a(new BlockPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ()));
      }
   }

   public EntityEnderCrystal getHandle() {
      return (EntityEnderCrystal)this.entity;
   }

   @Override
   public String toString() {
      return "CraftEnderCrystal";
   }

   public EntityType getType() {
      return EntityType.ENDER_CRYSTAL;
   }
}
