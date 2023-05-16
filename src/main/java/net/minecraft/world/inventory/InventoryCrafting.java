package net.minecraft.world.inventory;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.NonNullList;
import net.minecraft.world.ContainerUtil;
import net.minecraft.world.IInventory;
import net.minecraft.world.entity.player.AutoRecipeStackManager;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.IRecipe;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftHumanEntity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.InventoryHolder;

public class InventoryCrafting implements IInventory, AutoRecipeOutput {
   private final NonNullList<ItemStack> c;
   private final int d;
   private final int e;
   public final Container f;
   public List<HumanEntity> transaction = new ArrayList();
   private IRecipe currentRecipe;
   public IInventory resultInventory;
   private EntityHuman owner;
   private int maxStack = 64;

   @Override
   public List<ItemStack> getContents() {
      return this.c;
   }

   @Override
   public void onOpen(CraftHumanEntity who) {
      this.transaction.add(who);
   }

   public InventoryType getInvType() {
      return this.c.size() == 4 ? InventoryType.CRAFTING : InventoryType.WORKBENCH;
   }

   @Override
   public void onClose(CraftHumanEntity who) {
      this.transaction.remove(who);
   }

   @Override
   public List<HumanEntity> getViewers() {
      return this.transaction;
   }

   @Override
   public InventoryHolder getOwner() {
      return this.owner == null ? null : this.owner.getBukkitEntity();
   }

   @Override
   public int ab_() {
      return this.maxStack;
   }

   @Override
   public void setMaxStackSize(int size) {
      this.maxStack = size;
      this.resultInventory.setMaxStackSize(size);
   }

   @Override
   public Location getLocation() {
      return this.f instanceof ContainerWorkbench ? ((ContainerWorkbench)this.f).t.getLocation() : this.owner.getBukkitEntity().getLocation();
   }

   @Override
   public IRecipe getCurrentRecipe() {
      return this.currentRecipe;
   }

   @Override
   public void setCurrentRecipe(IRecipe currentRecipe) {
      this.currentRecipe = currentRecipe;
   }

   public InventoryCrafting(Container container, int i, int j, EntityHuman player) {
      this(container, i, j);
      this.owner = player;
   }

   public InventoryCrafting(Container container, int i, int j) {
      this.c = NonNullList.a(i * j, ItemStack.b);
      this.f = container;
      this.d = i;
      this.e = j;
   }

   @Override
   public int b() {
      return this.c.size();
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
      return i >= this.b() ? ItemStack.b : this.c.get(i);
   }

   @Override
   public ItemStack b(int i) {
      return ContainerUtil.a(this.c, i);
   }

   @Override
   public ItemStack a(int i, int j) {
      ItemStack itemstack = ContainerUtil.a(this.c, i, j);
      if (!itemstack.b()) {
         this.f.a(this);
      }

      return itemstack;
   }

   @Override
   public void a(int i, ItemStack itemstack) {
      this.c.set(i, itemstack);
      this.f.a(this);
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

   public int f() {
      return this.e;
   }

   public int g() {
      return this.d;
   }

   @Override
   public void a(AutoRecipeStackManager autorecipestackmanager) {
      for(ItemStack itemstack : this.c) {
         autorecipestackmanager.a(itemstack);
      }
   }
}
