package org.bukkit.craftbukkit.v1_19_R3.inventory;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.NonNullList;
import net.minecraft.world.IInventory;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftHumanEntity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.InventoryHolder;

public class CraftInventoryCustom extends CraftInventory {
   public CraftInventoryCustom(InventoryHolder owner, InventoryType type) {
      super(new CraftInventoryCustom.MinecraftInventory(owner, type));
   }

   public CraftInventoryCustom(InventoryHolder owner, InventoryType type, String title) {
      super(new CraftInventoryCustom.MinecraftInventory(owner, type, title));
   }

   public CraftInventoryCustom(InventoryHolder owner, int size) {
      super(new CraftInventoryCustom.MinecraftInventory(owner, size));
   }

   public CraftInventoryCustom(InventoryHolder owner, int size, String title) {
      super(new CraftInventoryCustom.MinecraftInventory(owner, size, title));
   }

   static class MinecraftInventory implements IInventory {
      private final NonNullList<ItemStack> items;
      private int maxStack = 64;
      private final List<HumanEntity> viewers;
      private final String title;
      private InventoryType type;
      private final InventoryHolder owner;

      public MinecraftInventory(InventoryHolder owner, InventoryType type) {
         this(owner, type.getDefaultSize(), type.getDefaultTitle());
         this.type = type;
      }

      public MinecraftInventory(InventoryHolder owner, InventoryType type, String title) {
         this(owner, type.getDefaultSize(), title);
         this.type = type;
      }

      public MinecraftInventory(InventoryHolder owner, int size) {
         this(owner, size, "Chest");
      }

      public MinecraftInventory(InventoryHolder owner, int size, String title) {
         Validate.notNull(title, "Title cannot be null");
         this.items = NonNullList.a(size, ItemStack.b);
         this.title = title;
         this.viewers = new ArrayList();
         this.owner = owner;
         this.type = InventoryType.CHEST;
      }

      @Override
      public int b() {
         return this.items.size();
      }

      @Override
      public ItemStack a(int i) {
         return this.items.get(i);
      }

      @Override
      public ItemStack a(int i, int j) {
         ItemStack stack = this.a(i);
         if (stack == ItemStack.b) {
            return stack;
         } else {
            ItemStack result;
            if (stack.K() <= j) {
               this.a(i, ItemStack.b);
               result = stack;
            } else {
               result = CraftItemStack.copyNMSStack(stack, j);
               stack.h(j);
            }

            this.e();
            return result;
         }
      }

      @Override
      public ItemStack b(int i) {
         ItemStack stack = this.a(i);
         if (stack == ItemStack.b) {
            return stack;
         } else {
            ItemStack result;
            if (stack.K() <= 1) {
               this.a(i, null);
               result = stack;
            } else {
               result = CraftItemStack.copyNMSStack(stack, 1);
               stack.h(1);
            }

            return result;
         }
      }

      @Override
      public void a(int i, ItemStack itemstack) {
         this.items.set(i, itemstack);
         if (itemstack != ItemStack.b && this.ab_() > 0 && itemstack.K() > this.ab_()) {
            itemstack.f(this.ab_());
         }
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
      public void e() {
      }

      @Override
      public boolean a(EntityHuman entityhuman) {
         return true;
      }

      @Override
      public List<ItemStack> getContents() {
         return this.items;
      }

      @Override
      public void onOpen(CraftHumanEntity who) {
         this.viewers.add(who);
      }

      @Override
      public void onClose(CraftHumanEntity who) {
         this.viewers.remove(who);
      }

      @Override
      public List<HumanEntity> getViewers() {
         return this.viewers;
      }

      public InventoryType getType() {
         return this.type;
      }

      @Override
      public InventoryHolder getOwner() {
         return this.owner;
      }

      @Override
      public boolean b(int i, ItemStack itemstack) {
         return true;
      }

      @Override
      public void d_(EntityHuman entityHuman) {
      }

      @Override
      public void c(EntityHuman entityHuman) {
      }

      @Override
      public void a() {
         this.items.clear();
      }

      @Override
      public Location getLocation() {
         return null;
      }

      public String getTitle() {
         return this.title;
      }

      @Override
      public boolean aa_() {
         for(ItemStack itemstack : this.items) {
            if (!itemstack.b()) {
               return false;
            }
         }

         return true;
      }
   }
}
