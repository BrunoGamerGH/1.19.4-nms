package org.bukkit.craftbukkit.v1_19_R3.inventory;

import net.minecraft.core.BlockPosition;
import net.minecraft.world.IInventory;
import net.minecraft.world.level.GeneratorAccess;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_19_R3.block.CraftBlock;
import org.bukkit.inventory.BlockInventoryHolder;
import org.bukkit.inventory.Inventory;

public class CraftBlockInventoryHolder implements BlockInventoryHolder {
   private final Block block;
   private final Inventory inventory;

   public CraftBlockInventoryHolder(GeneratorAccess world, BlockPosition pos, IInventory inv) {
      this.block = CraftBlock.at(world, pos);
      this.inventory = new CraftInventory(inv);
   }

   public Block getBlock() {
      return this.block;
   }

   public Inventory getInventory() {
      return this.inventory;
   }
}
