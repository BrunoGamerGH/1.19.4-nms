package org.bukkit.craftbukkit.v1_19_R3.inventory;

import com.google.common.base.Preconditions;
import net.minecraft.world.IInventory;
import net.minecraft.world.inventory.ContainerAnvil;
import org.bukkit.Location;
import org.bukkit.inventory.AnvilInventory;

public class CraftInventoryAnvil extends CraftResultInventory implements AnvilInventory {
   private final Location location;
   private final ContainerAnvil container;

   public CraftInventoryAnvil(Location location, IInventory inventory, IInventory resultInventory, ContainerAnvil container) {
      super(inventory, resultInventory);
      this.location = location;
      this.container = container;
   }

   @Override
   public Location getLocation() {
      return this.location;
   }

   public String getRenameText() {
      return this.container.v;
   }

   public int getRepairCostAmount() {
      return this.container.u;
   }

   public void setRepairCostAmount(int amount) {
      this.container.u = amount;
   }

   public int getRepairCost() {
      return this.container.w.b();
   }

   public void setRepairCost(int i) {
      this.container.w.a(i);
   }

   public int getMaximumRepairCost() {
      return this.container.maximumRepairCost;
   }

   public void setMaximumRepairCost(int levels) {
      Preconditions.checkArgument(levels >= 0, "Maximum repair cost must be positive (or 0)");
      this.container.maximumRepairCost = levels;
   }
}
