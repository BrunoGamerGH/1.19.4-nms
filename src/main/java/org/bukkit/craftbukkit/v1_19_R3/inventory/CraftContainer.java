package org.bukkit.craftbukkit.v1_19_R3.inventory;

import net.minecraft.world.IInventory;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.player.PlayerInventory;
import net.minecraft.world.inventory.Container;
import net.minecraft.world.inventory.ContainerAnvil;
import net.minecraft.world.inventory.ContainerBeacon;
import net.minecraft.world.inventory.ContainerBlastFurnace;
import net.minecraft.world.inventory.ContainerBrewingStand;
import net.minecraft.world.inventory.ContainerCartography;
import net.minecraft.world.inventory.ContainerChest;
import net.minecraft.world.inventory.ContainerDispenser;
import net.minecraft.world.inventory.ContainerEnchantTable;
import net.minecraft.world.inventory.ContainerFurnaceFurnace;
import net.minecraft.world.inventory.ContainerGrindstone;
import net.minecraft.world.inventory.ContainerHopper;
import net.minecraft.world.inventory.ContainerLectern;
import net.minecraft.world.inventory.ContainerLoom;
import net.minecraft.world.inventory.ContainerMerchant;
import net.minecraft.world.inventory.ContainerProperties;
import net.minecraft.world.inventory.ContainerShulkerBox;
import net.minecraft.world.inventory.ContainerSmoker;
import net.minecraft.world.inventory.ContainerStonecutter;
import net.minecraft.world.inventory.ContainerWorkbench;
import net.minecraft.world.inventory.Containers;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;

public class CraftContainer extends Container {
   private final InventoryView view;
   private InventoryType cachedType;
   private Container delegate;

   public CraftContainer(InventoryView view, EntityHuman player, int id) {
      super(getNotchInventoryType(view.getTopInventory()), id);
      this.view = view;
      IInventory top = ((CraftInventory)view.getTopInventory()).getInventory();
      PlayerInventory bottom = (PlayerInventory)((CraftInventory)view.getBottomInventory()).getInventory();
      this.cachedType = view.getType();
      this.setupSlots(top, bottom, player);
   }

   public CraftContainer(final Inventory inventory, final EntityHuman player, int id) {
      this(
         new InventoryView() {
            public Inventory getTopInventory() {
               return inventory;
            }
   
            public Inventory getBottomInventory() {
               return this.getPlayer().getInventory();
            }
   
            public HumanEntity getPlayer() {
               return player.getBukkitEntity();
            }
   
            public InventoryType getType() {
               return inventory.getType();
            }
   
            public String getTitle() {
               return inventory instanceof CraftInventoryCustom
                  ? ((CraftInventoryCustom.MinecraftInventory)((CraftInventory)inventory).getInventory()).getTitle()
                  : inventory.getType().getDefaultTitle();
            }
         },
         player,
         id
      );
   }

   @Override
   public InventoryView getBukkitView() {
      return this.view;
   }

   public static Containers getNotchInventoryType(Inventory inventory) {
      switch(inventory.getType()) {
         case CHEST:
         case PLAYER:
         case ENDER_CHEST:
         case BARREL:
            switch(inventory.getSize()) {
               case 9:
                  return Containers.a;
               case 18:
                  return Containers.b;
               case 27:
                  return Containers.c;
               case 36:
               case 41:
                  return Containers.d;
               case 45:
                  return Containers.e;
               case 54:
                  return Containers.f;
               default:
                  throw new IllegalArgumentException("Unsupported custom inventory size " + inventory.getSize());
            }
         case DISPENSER:
            return Containers.g;
         case DROPPER:
            return Containers.g;
         case FURNACE:
            return Containers.n;
         case WORKBENCH:
            return Containers.l;
         case CRAFTING:
         case CREATIVE:
         case MERCHANT:
            throw new IllegalArgumentException("Can't open a " + inventory.getType() + " inventory!");
         case ENCHANTING:
            return Containers.m;
         case BREWING:
            return Containers.k;
         case ANVIL:
            return Containers.h;
         case SMITHING:
            return Containers.u;
         case BEACON:
            return Containers.i;
         case HOPPER:
            return Containers.p;
         case SHULKER_BOX:
            return Containers.t;
         case BLAST_FURNACE:
            return Containers.j;
         case LECTERN:
            return Containers.q;
         case SMOKER:
            return Containers.w;
         case LOOM:
            return Containers.r;
         case CARTOGRAPHY:
            return Containers.x;
         case GRINDSTONE:
            return Containers.o;
         case STONECUTTER:
            return Containers.y;
         case COMPOSTER:
         case CHISELED_BOOKSHELF:
         case JUKEBOX:
         default:
            return Containers.c;
         case SMITHING_NEW:
            return Containers.v;
      }
   }

   private void setupSlots(IInventory top, PlayerInventory bottom, EntityHuman entityhuman) {
      int windowId = -1;
      switch(this.cachedType) {
         case CHEST:
         case PLAYER:
         case ENDER_CHEST:
         case BARREL:
            this.delegate = new ContainerChest(Containers.c, windowId, bottom, top, top.b() / 9);
            break;
         case DISPENSER:
         case DROPPER:
            this.delegate = new ContainerDispenser(windowId, bottom, top);
            break;
         case FURNACE:
            this.delegate = new ContainerFurnaceFurnace(windowId, bottom, top, new ContainerProperties(4));
            break;
         case WORKBENCH:
         case CRAFTING:
            this.setupWorkbench(top, bottom);
            break;
         case ENCHANTING:
            this.delegate = new ContainerEnchantTable(windowId, bottom);
            break;
         case BREWING:
            this.delegate = new ContainerBrewingStand(windowId, bottom, top, new ContainerProperties(2));
         case CREATIVE:
         case COMPOSTER:
         case CHISELED_BOOKSHELF:
         case JUKEBOX:
         default:
            break;
         case MERCHANT:
            this.delegate = new ContainerMerchant(windowId, bottom);
            break;
         case ANVIL:
         case SMITHING:
            this.setupAnvil(top, bottom);
            break;
         case BEACON:
            this.delegate = new ContainerBeacon(windowId, bottom);
            break;
         case HOPPER:
            this.delegate = new ContainerHopper(windowId, bottom, top);
            break;
         case SHULKER_BOX:
            this.delegate = new ContainerShulkerBox(windowId, bottom, top);
            break;
         case BLAST_FURNACE:
            this.delegate = new ContainerBlastFurnace(windowId, bottom, top, new ContainerProperties(4));
            break;
         case LECTERN:
            this.delegate = new ContainerLectern(windowId, top, new ContainerProperties(1), bottom);
            break;
         case SMOKER:
            this.delegate = new ContainerSmoker(windowId, bottom, top, new ContainerProperties(4));
            break;
         case LOOM:
            this.delegate = new ContainerLoom(windowId, bottom);
            break;
         case CARTOGRAPHY:
            this.delegate = new ContainerCartography(windowId, bottom);
            break;
         case GRINDSTONE:
            this.delegate = new ContainerGrindstone(windowId, bottom);
            break;
         case STONECUTTER:
            this.delegate = new ContainerStonecutter(windowId, bottom);
            break;
         case SMITHING_NEW:
            this.setupSmithing(top, bottom);
      }

      if (this.delegate != null) {
         this.l = this.delegate.l;
         this.i = this.delegate.i;
         this.o = this.delegate.o;
      }

      switch(this.cachedType) {
         case WORKBENCH:
            this.delegate = new ContainerWorkbench(windowId, bottom);
            break;
         case ANVIL:
            this.delegate = new ContainerAnvil(windowId, bottom);
      }
   }

   private void setupWorkbench(IInventory top, IInventory bottom) {
      this.a(new Slot(top, 0, 124, 35));

      for(int row = 0; row < 3; ++row) {
         for(int col = 0; col < 3; ++col) {
            this.a(new Slot(top, 1 + col + row * 3, 30 + col * 18, 17 + row * 18));
         }
      }

      for(int var5 = 0; var5 < 3; ++var5) {
         for(int col = 0; col < 9; ++col) {
            this.a(new Slot(bottom, col + var5 * 9 + 9, 8 + col * 18, 84 + var5 * 18));
         }
      }

      for(int col = 0; col < 9; ++col) {
         this.a(new Slot(bottom, col, 8 + col * 18, 142));
      }
   }

   private void setupAnvil(IInventory top, IInventory bottom) {
      this.a(new Slot(top, 0, 27, 47));
      this.a(new Slot(top, 1, 76, 47));
      this.a(new Slot(top, 2, 134, 47));

      for(int row = 0; row < 3; ++row) {
         for(int col = 0; col < 9; ++col) {
            this.a(new Slot(bottom, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
         }
      }

      for(int var5 = 0; var5 < 9; ++var5) {
         this.a(new Slot(bottom, var5, 8 + var5 * 18, 142));
      }
   }

   private void setupSmithing(IInventory top, IInventory bottom) {
      this.a(new Slot(top, 0, 8, 48));
      this.a(new Slot(top, 1, 26, 48));
      this.a(new Slot(top, 2, 44, 48));
      this.a(new Slot(top, 3, 98, 48));

      for(int row = 0; row < 3; ++row) {
         for(int col = 0; col < 9; ++col) {
            this.a(new Slot(bottom, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
         }
      }

      for(int var5 = 0; var5 < 9; ++var5) {
         this.a(new Slot(bottom, var5, 8 + var5 * 18, 142));
      }
   }

   @Override
   public ItemStack a(EntityHuman entityhuman, int i) {
      return this.delegate != null ? this.delegate.a(entityhuman, i) : ItemStack.b;
   }

   @Override
   public boolean a(EntityHuman entity) {
      return true;
   }

   @Override
   public Containers<?> a() {
      return getNotchInventoryType(this.view.getTopInventory());
   }
}
