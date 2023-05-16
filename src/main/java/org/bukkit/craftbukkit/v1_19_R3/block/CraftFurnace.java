package org.bukkit.craftbukkit.v1_19_R3.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.world.level.block.BlockFurnace;
import net.minecraft.world.level.block.entity.TileEntityFurnace;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Furnace;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftInventoryFurnace;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftNamespacedKey;
import org.bukkit.inventory.CookingRecipe;
import org.bukkit.inventory.FurnaceInventory;
import org.bukkit.inventory.Recipe;

public abstract class CraftFurnace<T extends TileEntityFurnace> extends CraftContainer<T> implements Furnace {
   public CraftFurnace(World world, T tileEntity) {
      super(world, tileEntity);
   }

   public FurnaceInventory getSnapshotInventory() {
      return new CraftInventoryFurnace(this.getSnapshot());
   }

   public FurnaceInventory getInventory() {
      return (FurnaceInventory)(!this.isPlaced() ? this.getSnapshotInventory() : new CraftInventoryFurnace(this.getTileEntity()));
   }

   public short getBurnTime() {
      return (short)this.getSnapshot().u;
   }

   public void setBurnTime(short burnTime) {
      this.getSnapshot().u = burnTime;
      this.data = this.data.a(BlockFurnace.b, Boolean.valueOf(burnTime > 0));
   }

   public short getCookTime() {
      return (short)this.getSnapshot().w;
   }

   public void setCookTime(short cookTime) {
      this.getSnapshot().w = cookTime;
   }

   public int getCookTimeTotal() {
      return this.getSnapshot().x;
   }

   public void setCookTimeTotal(int cookTimeTotal) {
      this.getSnapshot().x = cookTimeTotal;
   }

   public Map<CookingRecipe<?>, Integer> getRecipesUsed() {
      Builder<CookingRecipe<?>, Integer> recipesUsed = ImmutableMap.builder();

      for(Entry<MinecraftKey, Integer> entrySet : this.getSnapshot().getRecipesUsed().object2IntEntrySet()) {
         Recipe recipe = Bukkit.getRecipe(CraftNamespacedKey.fromMinecraft(entrySet.getKey()));
         CookingRecipe cookingRecipe;
         if (recipe instanceof CookingRecipe && (cookingRecipe = (CookingRecipe)recipe) == (CookingRecipe)recipe) {
            recipesUsed.put(cookingRecipe, entrySet.getValue());
         }
      }

      return recipesUsed.build();
   }
}
