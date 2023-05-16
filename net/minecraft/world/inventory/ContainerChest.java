package net.minecraft.world.inventory;

import net.minecraft.world.IInventory;
import net.minecraft.world.InventoryLargeChest;
import net.minecraft.world.InventorySubcontainer;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.player.PlayerInventory;
import net.minecraft.world.item.ItemStack;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftInventory;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftInventoryDoubleChest;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftInventoryPlayer;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftInventoryView;

public class ContainerChest extends Container {
   private static final int k = 9;
   private final IInventory l;
   private final int m;
   private CraftInventoryView bukkitEntity = null;
   private PlayerInventory player;

   public CraftInventoryView getBukkitView() {
      if (this.bukkitEntity != null) {
         return this.bukkitEntity;
      } else {
         CraftInventory inventory;
         if (this.l instanceof PlayerInventory) {
            inventory = new CraftInventoryPlayer((PlayerInventory)this.l);
         } else if (this.l instanceof InventoryLargeChest) {
            inventory = new CraftInventoryDoubleChest((InventoryLargeChest)this.l);
         } else {
            inventory = new CraftInventory(this.l);
         }

         this.bukkitEntity = new CraftInventoryView(this.player.m.getBukkitEntity(), inventory, this);
         return this.bukkitEntity;
      }
   }

   private ContainerChest(Containers<?> containers, int i, PlayerInventory playerinventory, int j) {
      this(containers, i, playerinventory, new InventorySubcontainer(9 * j), j);
   }

   public static ContainerChest a(int i, PlayerInventory playerinventory) {
      return new ContainerChest(Containers.a, i, playerinventory, 1);
   }

   public static ContainerChest b(int i, PlayerInventory playerinventory) {
      return new ContainerChest(Containers.b, i, playerinventory, 2);
   }

   public static ContainerChest c(int i, PlayerInventory playerinventory) {
      return new ContainerChest(Containers.c, i, playerinventory, 3);
   }

   public static ContainerChest d(int i, PlayerInventory playerinventory) {
      return new ContainerChest(Containers.d, i, playerinventory, 4);
   }

   public static ContainerChest e(int i, PlayerInventory playerinventory) {
      return new ContainerChest(Containers.e, i, playerinventory, 5);
   }

   public static ContainerChest f(int i, PlayerInventory playerinventory) {
      return new ContainerChest(Containers.f, i, playerinventory, 6);
   }

   public static ContainerChest a(int i, PlayerInventory playerinventory, IInventory iinventory) {
      return new ContainerChest(Containers.c, i, playerinventory, iinventory, 3);
   }

   public static ContainerChest b(int i, PlayerInventory playerinventory, IInventory iinventory) {
      return new ContainerChest(Containers.f, i, playerinventory, iinventory, 6);
   }

   public ContainerChest(Containers<?> containers, int i, PlayerInventory playerinventory, IInventory iinventory, int j) {
      super(containers, i);
      a(iinventory, j * 9);
      this.l = iinventory;
      this.m = j;
      iinventory.d_(playerinventory.m);
      int k = (this.m - 4) * 18;
      this.player = playerinventory;

      for(int l = 0; l < this.m; ++l) {
         for(int i1 = 0; i1 < 9; ++i1) {
            this.a(new Slot(iinventory, i1 + l * 9, 8 + i1 * 18, 18 + l * 18));
         }
      }

      for(int var9 = 0; var9 < 3; ++var9) {
         for(int i1 = 0; i1 < 9; ++i1) {
            this.a(new Slot(playerinventory, i1 + var9 * 9 + 9, 8 + i1 * 18, 103 + var9 * 18 + k));
         }
      }

      for(int var10 = 0; var10 < 9; ++var10) {
         this.a(new Slot(playerinventory, var10, 8 + var10 * 18, 161 + k));
      }
   }

   @Override
   public boolean a(EntityHuman entityhuman) {
      return !this.checkReachable ? true : this.l.a(entityhuman);
   }

   @Override
   public ItemStack a(EntityHuman entityhuman, int i) {
      ItemStack itemstack = ItemStack.b;
      Slot slot = this.i.get(i);
      if (slot != null && slot.f()) {
         ItemStack itemstack1 = slot.e();
         itemstack = itemstack1.o();
         if (i < this.m * 9) {
            if (!this.a(itemstack1, this.m * 9, this.i.size(), true)) {
               return ItemStack.b;
            }
         } else if (!this.a(itemstack1, 0, this.m * 9, false)) {
            return ItemStack.b;
         }

         if (itemstack1.b()) {
            slot.d(ItemStack.b);
         } else {
            slot.d();
         }
      }

      return itemstack;
   }

   @Override
   public void b(EntityHuman entityhuman) {
      super.b(entityhuman);
      this.l.c(entityhuman);
   }

   public IInventory l() {
      return this.l;
   }

   public int m() {
      return this.m;
   }
}
