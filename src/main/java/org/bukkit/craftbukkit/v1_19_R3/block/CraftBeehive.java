package org.bukkit.craftbukkit.v1_19_R3.block;

import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPosition;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.TileEntityBeehive;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Beehive;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftBee;
import org.bukkit.entity.Bee;

public class CraftBeehive extends CraftBlockEntityState<TileEntityBeehive> implements Beehive {
   public CraftBeehive(World world, TileEntityBeehive tileEntity) {
      super(world, tileEntity);
   }

   public Location getFlower() {
      BlockPosition flower = this.getSnapshot().m;
      return flower == null ? null : new Location(this.getWorld(), (double)flower.u(), (double)flower.v(), (double)flower.w());
   }

   public void setFlower(Location location) {
      Preconditions.checkArgument(location == null || this.getWorld().equals(location.getWorld()), "Flower must be in same world");
      this.getSnapshot().m = location == null ? null : new BlockPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ());
   }

   public boolean isFull() {
      return this.getSnapshot().f();
   }

   public boolean isSedated() {
      return this.isPlaced() && this.getTileEntity().i();
   }

   public int getEntityCount() {
      return this.getSnapshot().g();
   }

   public int getMaxEntities() {
      return this.getSnapshot().maxBees;
   }

   public void setMaxEntities(int max) {
      Preconditions.checkArgument(max > 0, "Max bees must be more than 0");
      this.getSnapshot().maxBees = max;
   }

   public List<Bee> releaseEntities() {
      this.ensureNoWorldGeneration();
      List<Bee> bees = new ArrayList();
      if (this.isPlaced()) {
         TileEntityBeehive beehive = (TileEntityBeehive)this.getTileEntityFromWorld();

         for(Entity bee : beehive.releaseBees(this.getHandle(), TileEntityBeehive.ReleaseStatus.b, true)) {
            bees.add((Bee)bee.getBukkitEntity());
         }
      }

      return bees;
   }

   public void addEntity(Bee entity) {
      Preconditions.checkArgument(entity != null, "Entity must not be null");
      this.getSnapshot().a(((CraftBee)entity).getHandle(), false);
   }
}
