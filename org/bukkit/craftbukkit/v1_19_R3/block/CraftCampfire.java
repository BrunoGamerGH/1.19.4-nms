package org.bukkit.craftbukkit.v1_19_R3.block;

import net.minecraft.world.level.block.entity.TileEntityCampfire;
import org.bukkit.World;
import org.bukkit.block.Campfire;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class CraftCampfire extends CraftBlockEntityState<TileEntityCampfire> implements Campfire {
   public CraftCampfire(World world, TileEntityCampfire tileEntity) {
      super(world, tileEntity);
   }

   public int getSize() {
      return this.getSnapshot().c().size();
   }

   public ItemStack getItem(int index) {
      net.minecraft.world.item.ItemStack item = this.getSnapshot().c().get(index);
      return item.b() ? null : CraftItemStack.asCraftMirror(item);
   }

   public void setItem(int index, ItemStack item) {
      this.getSnapshot().c().set(index, CraftItemStack.asNMSCopy(item));
   }

   public int getCookTime(int index) {
      return this.getSnapshot().d[index];
   }

   public void setCookTime(int index, int cookTime) {
      this.getSnapshot().d[index] = cookTime;
   }

   public int getCookTimeTotal(int index) {
      return this.getSnapshot().e[index];
   }

   public void setCookTimeTotal(int index, int cookTimeTotal) {
      this.getSnapshot().e[index] = cookTimeTotal;
   }
}
