package org.bukkit.craftbukkit.v1_19_R3.block;

import net.minecraft.world.level.block.BlockLectern;
import net.minecraft.world.level.block.entity.TileEntityLectern;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Lectern;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftInventoryLectern;
import org.bukkit.inventory.Inventory;

public class CraftLectern extends CraftBlockEntityState<TileEntityLectern> implements Lectern {
   public CraftLectern(World world, TileEntityLectern tileEntity) {
      super(world, tileEntity);
   }

   public int getPage() {
      return this.getSnapshot().g();
   }

   public void setPage(int page) {
      this.getSnapshot().a(page);
   }

   public Inventory getSnapshotInventory() {
      return new CraftInventoryLectern(this.getSnapshot().e);
   }

   public Inventory getInventory() {
      return (Inventory)(!this.isPlaced() ? this.getSnapshotInventory() : new CraftInventoryLectern(this.getTileEntity().e));
   }

   @Override
   public boolean update(boolean force, boolean applyPhysics) {
      boolean result = super.update(force, applyPhysics);
      if (result && this.getType() == Material.LECTERN && this.getWorldHandle() instanceof net.minecraft.world.level.World) {
         BlockLectern.a(this.world.getHandle(), this.getPosition(), this.getHandle());
      }

      return result;
   }
}
