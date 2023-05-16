package org.bukkit.craftbukkit.v1_19_R3.block;

import java.util.Objects;
import net.minecraft.core.BlockPosition;
import net.minecraft.world.level.block.entity.TileEntityEndGateway;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.EndGateway;

public class CraftEndGateway extends CraftBlockEntityState<TileEntityEndGateway> implements EndGateway {
   public CraftEndGateway(World world, TileEntityEndGateway tileEntity) {
      super(world, tileEntity);
   }

   public Location getExitLocation() {
      BlockPosition pos = this.getSnapshot().i;
      return pos == null ? null : new Location(this.isPlaced() ? this.getWorld() : null, (double)pos.u(), (double)pos.v(), (double)pos.w());
   }

   public void setExitLocation(Location location) {
      if (location == null) {
         this.getSnapshot().i = null;
      } else {
         if (!Objects.equals(location.getWorld(), this.isPlaced() ? this.getWorld() : null)) {
            throw new IllegalArgumentException("Cannot set exit location to different world");
         }

         this.getSnapshot().i = new BlockPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ());
      }
   }

   public boolean isExactTeleport() {
      return this.getSnapshot().j;
   }

   public void setExactTeleport(boolean exact) {
      this.getSnapshot().j = exact;
   }

   public long getAge() {
      return this.getSnapshot().g;
   }

   public void setAge(long age) {
      this.getSnapshot().g = age;
   }

   public void applyTo(TileEntityEndGateway endGateway) {
      super.applyTo(endGateway);
      if (this.getSnapshot().i == null) {
         endGateway.i = null;
      }
   }
}
