package org.bukkit.craftbukkit.v1_19_R3.block;

import net.minecraft.world.level.block.entity.TileEntityEnderChest;
import net.minecraft.world.level.block.state.IBlockData;
import org.bukkit.World;
import org.bukkit.block.EnderChest;

public class CraftEnderChest extends CraftBlockEntityState<TileEntityEnderChest> implements EnderChest {
   public CraftEnderChest(World world, TileEntityEnderChest tileEntity) {
      super(world, tileEntity);
   }

   public void open() {
      this.requirePlaced();
      if (!this.getTileEntity().b.opened && this.getWorldHandle() instanceof net.minecraft.world.level.World) {
         IBlockData block = this.getTileEntity().q();
         int openCount = this.getTileEntity().b.a();
         this.getTileEntity().b.onAPIOpen((net.minecraft.world.level.World)this.getWorldHandle(), this.getPosition(), block);
         this.getTileEntity()
            .b
            .openerAPICountChanged((net.minecraft.world.level.World)this.getWorldHandle(), this.getPosition(), block, openCount, openCount + 1);
      }

      this.getTileEntity().b.opened = true;
   }

   public void close() {
      this.requirePlaced();
      if (this.getTileEntity().b.opened && this.getWorldHandle() instanceof net.minecraft.world.level.World) {
         IBlockData block = this.getTileEntity().q();
         int openCount = this.getTileEntity().b.a();
         this.getTileEntity().b.onAPIClose((net.minecraft.world.level.World)this.getWorldHandle(), this.getPosition(), block);
         this.getTileEntity().b.openerAPICountChanged((net.minecraft.world.level.World)this.getWorldHandle(), this.getPosition(), block, openCount, 0);
      }

      this.getTileEntity().b.opened = false;
   }
}
