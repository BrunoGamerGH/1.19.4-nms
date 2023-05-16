package net.minecraft.world.inventory;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.NonNullList;
import net.minecraft.world.ContainerUtil;
import net.minecraft.world.IInventory;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.IRecipe;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftHumanEntity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.InventoryHolder;

public class InventoryCraftResult implements IInventory, RecipeHolder {
   private final NonNullList<ItemStack> c;
   @Nullable
   private IRecipe<?> d;
   private int maxStack = 64;

   @Override
   public List<ItemStack> getContents() {
      return this.c;
   }

   @Override
   public InventoryHolder getOwner() {
      return null;
   }

   @Override
   public void onOpen(CraftHumanEntity who) {
   }

   @Override
   public void onClose(CraftHumanEntity who) {
   }

   @Override
   public List<HumanEntity> getViewers() {
      return new ArrayList();
   }

   @Override
   public int ab_() {
      return this.maxStack;
   }

   @Override
   public void setMaxStackSize(int size) {
      this.maxStack = size;
   }

   @Override
   public Location getLocation() {
      return null;
   }

   public InventoryCraftResult() {
      this.c = NonNullList.a(1, ItemStack.b);
   }

   @Override
   public int b() {
      return 1;
   }

   @Override
   public boolean aa_() {
      for(ItemStack itemstack : this.c) {
         if (!itemstack.b()) {
            return false;
         }
      }

      return true;
   }

   @Override
   public ItemStack a(int i) {
      return this.c.get(0);
   }

   @Override
   public ItemStack a(int i, int j) {
      return ContainerUtil.a(this.c, 0);
   }

   @Override
   public ItemStack b(int i) {
      return ContainerUtil.a(this.c, 0);
   }

   @Override
   public void a(int i, ItemStack itemstack) {
      this.c.set(0, itemstack);
   }

   @Override
   public void e() {
   }

   @Override
   public boolean a(EntityHuman entityhuman) {
      return true;
   }

   @Override
   public void a() {
      this.c.clear();
   }

   @Override
   public void a(@Nullable IRecipe<?> irecipe) {
      this.d = irecipe;
   }

   @Nullable
   @Override
   public IRecipe<?> d() {
      return this.d;
   }
}
