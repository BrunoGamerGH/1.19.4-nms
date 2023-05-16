package org.bukkit.craftbukkit.v1_19_R3.inventory;

import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import net.minecraft.world.IInventory;
import net.minecraft.world.entity.player.PlayerInventory;
import net.minecraft.world.inventory.InventoryCrafting;
import net.minecraft.world.inventory.InventoryEnderChest;
import net.minecraft.world.inventory.InventoryMerchant;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.BlockComposter;
import net.minecraft.world.level.block.entity.ChiseledBookShelfBlockEntity;
import net.minecraft.world.level.block.entity.IHopper;
import net.minecraft.world.level.block.entity.TileEntityBarrel;
import net.minecraft.world.level.block.entity.TileEntityBlastFurnace;
import net.minecraft.world.level.block.entity.TileEntityBrewingStand;
import net.minecraft.world.level.block.entity.TileEntityDispenser;
import net.minecraft.world.level.block.entity.TileEntityDropper;
import net.minecraft.world.level.block.entity.TileEntityFurnace;
import net.minecraft.world.level.block.entity.TileEntityJukeBox;
import net.minecraft.world.level.block.entity.TileEntityLectern;
import net.minecraft.world.level.block.entity.TileEntityShulkerBox;
import net.minecraft.world.level.block.entity.TileEntitySmoker;
import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftLegacy;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class CraftInventory implements Inventory {
   protected final IInventory inventory;

   public CraftInventory(IInventory inventory) {
      this.inventory = inventory;
   }

   public IInventory getInventory() {
      return this.inventory;
   }

   public int getSize() {
      return this.getInventory().b();
   }

   public org.bukkit.inventory.ItemStack getItem(int index) {
      ItemStack item = this.getInventory().a(index);
      return item.b() ? null : CraftItemStack.asCraftMirror(item);
   }

   protected org.bukkit.inventory.ItemStack[] asCraftMirror(List<ItemStack> mcItems) {
      int size = mcItems.size();
      org.bukkit.inventory.ItemStack[] items = new org.bukkit.inventory.ItemStack[size];

      for(int i = 0; i < size; ++i) {
         ItemStack mcItem = mcItems.get(i);
         items[i] = mcItem.b() ? null : CraftItemStack.asCraftMirror(mcItem);
      }

      return items;
   }

   public org.bukkit.inventory.ItemStack[] getStorageContents() {
      return this.getContents();
   }

   public void setStorageContents(org.bukkit.inventory.ItemStack[] items) throws IllegalArgumentException {
      this.setContents(items);
   }

   public org.bukkit.inventory.ItemStack[] getContents() {
      List<ItemStack> mcItems = this.getInventory().getContents();
      return this.asCraftMirror(mcItems);
   }

   public void setContents(org.bukkit.inventory.ItemStack[] items) {
      if (this.getSize() < items.length) {
         throw new IllegalArgumentException("Invalid inventory size; expected " + this.getSize() + " or less");
      } else {
         for(int i = 0; i < this.getSize(); ++i) {
            if (i >= items.length) {
               this.setItem(i, null);
            } else {
               this.setItem(i, items[i]);
            }
         }
      }
   }

   public void setItem(int index, org.bukkit.inventory.ItemStack item) {
      this.getInventory().a(index, CraftItemStack.asNMSCopy(item));
   }

   public boolean contains(Material material) {
      Validate.notNull(material, "Material cannot be null");
      material = CraftLegacy.fromLegacy(material);

      org.bukkit.inventory.ItemStack[] var5;
      for(org.bukkit.inventory.ItemStack item : var5 = this.getStorageContents()) {
         if (item != null && item.getType() == material) {
            return true;
         }
      }

      return false;
   }

   public boolean contains(org.bukkit.inventory.ItemStack item) {
      if (item == null) {
         return false;
      } else {
         org.bukkit.inventory.ItemStack[] var5;
         for(org.bukkit.inventory.ItemStack i : var5 = this.getStorageContents()) {
            if (item.equals(i)) {
               return true;
            }
         }

         return false;
      }
   }

   public boolean contains(Material material, int amount) {
      Validate.notNull(material, "Material cannot be null");
      material = CraftLegacy.fromLegacy(material);
      if (amount <= 0) {
         return true;
      } else {
         org.bukkit.inventory.ItemStack[] var6;
         for(org.bukkit.inventory.ItemStack item : var6 = this.getStorageContents()) {
            if (item != null && item.getType() == material && (amount -= item.getAmount()) <= 0) {
               return true;
            }
         }

         return false;
      }
   }

   public boolean contains(org.bukkit.inventory.ItemStack item, int amount) {
      if (item == null) {
         return false;
      } else if (amount <= 0) {
         return true;
      } else {
         org.bukkit.inventory.ItemStack[] var6;
         for(org.bukkit.inventory.ItemStack i : var6 = this.getStorageContents()) {
            if (item.equals(i)) {
               if (--amount <= 0) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   public boolean containsAtLeast(org.bukkit.inventory.ItemStack item, int amount) {
      if (item == null) {
         return false;
      } else if (amount <= 0) {
         return true;
      } else {
         org.bukkit.inventory.ItemStack[] var6;
         for(org.bukkit.inventory.ItemStack i : var6 = this.getStorageContents()) {
            if (item.isSimilar(i) && (amount -= i.getAmount()) <= 0) {
               return true;
            }
         }

         return false;
      }
   }

   public HashMap<Integer, org.bukkit.inventory.ItemStack> all(Material material) {
      Validate.notNull(material, "Material cannot be null");
      material = CraftLegacy.fromLegacy(material);
      HashMap<Integer, org.bukkit.inventory.ItemStack> slots = new HashMap<>();
      org.bukkit.inventory.ItemStack[] inventory = this.getStorageContents();

      for(int i = 0; i < inventory.length; ++i) {
         org.bukkit.inventory.ItemStack item = inventory[i];
         if (item != null && item.getType() == material) {
            slots.put(i, item);
         }
      }

      return slots;
   }

   public HashMap<Integer, org.bukkit.inventory.ItemStack> all(org.bukkit.inventory.ItemStack item) {
      HashMap<Integer, org.bukkit.inventory.ItemStack> slots = new HashMap<>();
      if (item != null) {
         org.bukkit.inventory.ItemStack[] inventory = this.getStorageContents();

         for(int i = 0; i < inventory.length; ++i) {
            if (item.equals(inventory[i])) {
               slots.put(i, inventory[i]);
            }
         }
      }

      return slots;
   }

   public int first(Material material) {
      Validate.notNull(material, "Material cannot be null");
      material = CraftLegacy.fromLegacy(material);
      org.bukkit.inventory.ItemStack[] inventory = this.getStorageContents();

      for(int i = 0; i < inventory.length; ++i) {
         org.bukkit.inventory.ItemStack item = inventory[i];
         if (item != null && item.getType() == material) {
            return i;
         }
      }

      return -1;
   }

   public int first(org.bukkit.inventory.ItemStack item) {
      return this.first(item, true);
   }

   private int first(org.bukkit.inventory.ItemStack item, boolean withAmount) {
      if (item == null) {
         return -1;
      } else {
         org.bukkit.inventory.ItemStack[] inventory = this.getStorageContents();

         for(int i = 0; i < inventory.length; ++i) {
            if (inventory[i] != null && (withAmount ? item.equals(inventory[i]) : item.isSimilar(inventory[i]))) {
               return i;
            }
         }

         return -1;
      }
   }

   public int firstEmpty() {
      org.bukkit.inventory.ItemStack[] inventory = this.getStorageContents();

      for(int i = 0; i < inventory.length; ++i) {
         if (inventory[i] == null) {
            return i;
         }
      }

      return -1;
   }

   public boolean isEmpty() {
      return this.inventory.aa_();
   }

   public int firstPartial(Material material) {
      Validate.notNull(material, "Material cannot be null");
      material = CraftLegacy.fromLegacy(material);
      org.bukkit.inventory.ItemStack[] inventory = this.getStorageContents();

      for(int i = 0; i < inventory.length; ++i) {
         org.bukkit.inventory.ItemStack item = inventory[i];
         if (item != null && item.getType() == material && item.getAmount() < item.getMaxStackSize()) {
            return i;
         }
      }

      return -1;
   }

   private int firstPartial(org.bukkit.inventory.ItemStack item) {
      org.bukkit.inventory.ItemStack[] inventory = this.getStorageContents();
      org.bukkit.inventory.ItemStack filteredItem = CraftItemStack.asCraftCopy(item);
      if (item == null) {
         return -1;
      } else {
         for(int i = 0; i < inventory.length; ++i) {
            org.bukkit.inventory.ItemStack cItem = inventory[i];
            if (cItem != null && cItem.getAmount() < cItem.getMaxStackSize() && cItem.isSimilar(filteredItem)) {
               return i;
            }
         }

         return -1;
      }
   }

   public HashMap<Integer, org.bukkit.inventory.ItemStack> addItem(org.bukkit.inventory.ItemStack... items) {
      Validate.noNullElements(items, "Item cannot be null");
      HashMap<Integer, org.bukkit.inventory.ItemStack> leftover = new HashMap<>();

      for(int i = 0; i < items.length; ++i) {
         org.bukkit.inventory.ItemStack item = items[i];

         while(true) {
            int firstPartial = this.firstPartial(item);
            if (firstPartial == -1) {
               int firstFree = this.firstEmpty();
               if (firstFree == -1) {
                  leftover.put(i, item);
                  break;
               }

               if (item.getAmount() <= this.getMaxItemStack()) {
                  this.setItem(firstFree, item);
                  break;
               }

               CraftItemStack stack = CraftItemStack.asCraftCopy(item);
               stack.setAmount(this.getMaxItemStack());
               this.setItem(firstFree, stack);
               item.setAmount(item.getAmount() - this.getMaxItemStack());
            } else {
               org.bukkit.inventory.ItemStack partialItem = this.getItem(firstPartial);
               int amount = item.getAmount();
               int partialAmount = partialItem.getAmount();
               int maxAmount = partialItem.getMaxStackSize();
               if (amount + partialAmount <= maxAmount) {
                  partialItem.setAmount(amount + partialAmount);
                  this.setItem(firstPartial, partialItem);
                  break;
               }

               partialItem.setAmount(maxAmount);
               this.setItem(firstPartial, partialItem);
               item.setAmount(amount + partialAmount - maxAmount);
            }
         }
      }

      return leftover;
   }

   public HashMap<Integer, org.bukkit.inventory.ItemStack> removeItem(org.bukkit.inventory.ItemStack... items) {
      Validate.notNull(items, "Items cannot be null");
      HashMap<Integer, org.bukkit.inventory.ItemStack> leftover = new HashMap<>();

      for(int i = 0; i < items.length; ++i) {
         org.bukkit.inventory.ItemStack item = items[i];
         int toDelete = item.getAmount();

         do {
            int first = this.first(item, false);
            if (first == -1) {
               item.setAmount(toDelete);
               leftover.put(i, item);
               break;
            }

            org.bukkit.inventory.ItemStack itemStack = this.getItem(first);
            int amount = itemStack.getAmount();
            if (amount <= toDelete) {
               toDelete -= amount;
               this.clear(first);
            } else {
               itemStack.setAmount(amount - toDelete);
               this.setItem(first, itemStack);
               toDelete = 0;
            }
         } while(toDelete <= 0);
      }

      return leftover;
   }

   private int getMaxItemStack() {
      return this.getInventory().ab_();
   }

   public void remove(Material material) {
      Validate.notNull(material, "Material cannot be null");
      material = CraftLegacy.fromLegacy(material);
      org.bukkit.inventory.ItemStack[] items = this.getStorageContents();

      for(int i = 0; i < items.length; ++i) {
         if (items[i] != null && items[i].getType() == material) {
            this.clear(i);
         }
      }
   }

   public void remove(org.bukkit.inventory.ItemStack item) {
      org.bukkit.inventory.ItemStack[] items = this.getStorageContents();

      for(int i = 0; i < items.length; ++i) {
         if (items[i] != null && items[i].equals(item)) {
            this.clear(i);
         }
      }
   }

   public void clear(int index) {
      this.setItem(index, null);
   }

   public void clear() {
      for(int i = 0; i < this.getSize(); ++i) {
         this.clear(i);
      }
   }

   public ListIterator<org.bukkit.inventory.ItemStack> iterator() {
      return new InventoryIterator(this);
   }

   public ListIterator<org.bukkit.inventory.ItemStack> iterator(int index) {
      if (index < 0) {
         index += this.getSize() + 1;
      }

      return new InventoryIterator(this, index);
   }

   public List<HumanEntity> getViewers() {
      return this.inventory.getViewers();
   }

   public InventoryType getType() {
      if (this.inventory instanceof InventoryCrafting) {
         return this.inventory.b() >= 9 ? InventoryType.WORKBENCH : InventoryType.CRAFTING;
      } else if (this.inventory instanceof PlayerInventory) {
         return InventoryType.PLAYER;
      } else if (this.inventory instanceof TileEntityDropper) {
         return InventoryType.DROPPER;
      } else if (this.inventory instanceof TileEntityDispenser) {
         return InventoryType.DISPENSER;
      } else if (this.inventory instanceof TileEntityBlastFurnace) {
         return InventoryType.BLAST_FURNACE;
      } else if (this.inventory instanceof TileEntitySmoker) {
         return InventoryType.SMOKER;
      } else if (this.inventory instanceof TileEntityFurnace) {
         return InventoryType.FURNACE;
      } else if (this instanceof CraftInventoryEnchanting) {
         return InventoryType.ENCHANTING;
      } else if (this.inventory instanceof TileEntityBrewingStand) {
         return InventoryType.BREWING;
      } else if (this.inventory instanceof CraftInventoryCustom.MinecraftInventory) {
         return ((CraftInventoryCustom.MinecraftInventory)this.inventory).getType();
      } else if (this.inventory instanceof InventoryEnderChest) {
         return InventoryType.ENDER_CHEST;
      } else if (this.inventory instanceof InventoryMerchant) {
         return InventoryType.MERCHANT;
      } else if (this instanceof CraftInventoryBeacon) {
         return InventoryType.BEACON;
      } else if (this instanceof CraftInventoryAnvil) {
         return InventoryType.ANVIL;
      } else if (this instanceof CraftInventorySmithing) {
         return InventoryType.SMITHING;
      } else if (this.inventory instanceof IHopper) {
         return InventoryType.HOPPER;
      } else if (this.inventory instanceof TileEntityShulkerBox) {
         return InventoryType.SHULKER_BOX;
      } else if (this.inventory instanceof TileEntityBarrel) {
         return InventoryType.BARREL;
      } else if (this.inventory instanceof TileEntityLectern.LecternInventory) {
         return InventoryType.LECTERN;
      } else if (this.inventory instanceof ChiseledBookShelfBlockEntity) {
         return InventoryType.CHISELED_BOOKSHELF;
      } else if (this instanceof CraftInventoryLoom) {
         return InventoryType.LOOM;
      } else if (this instanceof CraftInventoryCartography) {
         return InventoryType.CARTOGRAPHY;
      } else if (this instanceof CraftInventoryGrindstone) {
         return InventoryType.GRINDSTONE;
      } else if (this instanceof CraftInventoryStonecutter) {
         return InventoryType.STONECUTTER;
      } else if (this.inventory instanceof BlockComposter.ContainerEmpty
         || this.inventory instanceof BlockComposter.ContainerInput
         || this.inventory instanceof BlockComposter.ContainerOutput) {
         return InventoryType.COMPOSTER;
      } else if (this.inventory instanceof TileEntityJukeBox) {
         return InventoryType.JUKEBOX;
      } else {
         return this instanceof CraftInventorySmithingNew ? InventoryType.SMITHING_NEW : InventoryType.CHEST;
      }
   }

   public InventoryHolder getHolder() {
      return this.inventory.getOwner();
   }

   public int getMaxStackSize() {
      return this.inventory.ab_();
   }

   public void setMaxStackSize(int size) {
      this.inventory.setMaxStackSize(size);
   }

   @Override
   public int hashCode() {
      return this.inventory.hashCode();
   }

   @Override
   public boolean equals(Object obj) {
      return obj instanceof CraftInventory && ((CraftInventory)obj).inventory.equals(this.inventory);
   }

   public Location getLocation() {
      return this.inventory.getLocation();
   }
}
