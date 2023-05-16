package org.bukkit.craftbukkit.v1_19_R3.entity;

import com.google.common.base.Preconditions;
import net.minecraft.core.BlockPosition;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftInventory;
import org.bukkit.entity.Allay;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.Inventory;

public class CraftAllay extends CraftCreature implements Allay {
   public CraftAllay(CraftServer server, net.minecraft.world.entity.animal.allay.Allay entity) {
      super(server, entity);
   }

   public net.minecraft.world.entity.animal.allay.Allay getHandle() {
      return (net.minecraft.world.entity.animal.allay.Allay)this.entity;
   }

   @Override
   public String toString() {
      return "CraftAllay";
   }

   @Override
   public EntityType getType() {
      return EntityType.ALLAY;
   }

   public Inventory getInventory() {
      return new CraftInventory(this.getHandle().w());
   }

   public boolean canDuplicate() {
      return this.getHandle().ga();
   }

   public void setCanDuplicate(boolean canDuplicate) {
      this.getHandle().setCanDuplicate(canDuplicate);
   }

   public long getDuplicationCooldown() {
      return this.getHandle().ci;
   }

   public void setDuplicationCooldown(long l) {
      this.getHandle().ci = l;
   }

   public void resetDuplicationCooldown() {
      this.getHandle().fZ();
   }

   public boolean isDancing() {
      return this.getHandle().fS();
   }

   public void startDancing(Location location) {
      Preconditions.checkArgument(location != null, "Location cannot be null");
      Preconditions.checkArgument(location.getBlock().getType().equals(Material.JUKEBOX), "The Block in the Location need to be a JukeBox");
      this.getHandle().b(BlockPosition.a(location.getX(), location.getY(), location.getZ()), true);
   }

   public void startDancing() {
      this.getHandle().forceDancing = true;
      this.getHandle().w(true);
   }

   public void stopDancing() {
      this.getHandle().forceDancing = false;
      this.getHandle().ch = null;
      this.getHandle().b(null, false);
   }

   public Allay duplicateAllay() {
      net.minecraft.world.entity.animal.allay.Allay nmsAllay = this.getHandle().duplicateAllay();
      return nmsAllay != null ? (Allay)nmsAllay.getBukkitEntity() : null;
   }

   public Location getJukebox() {
      BlockPosition nmsJukeboxPos = this.getHandle().ch;
      return nmsJukeboxPos != null ? new Location(this.getWorld(), (double)nmsJukeboxPos.u(), (double)nmsJukeboxPos.v(), (double)nmsJukeboxPos.w()) : null;
   }
}
