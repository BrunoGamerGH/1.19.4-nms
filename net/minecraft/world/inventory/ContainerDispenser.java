package net.minecraft.world.inventory;

import net.minecraft.world.IInventory;
import net.minecraft.world.InventorySubcontainer;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.player.PlayerInventory;
import net.minecraft.world.item.ItemStack;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftInventory;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftInventoryView;

public class ContainerDispenser extends Container {
   private static final int k = 9;
   private static final int l = 9;
   private static final int m = 36;
   private static final int n = 36;
   private static final int o = 45;
   public final IInventory p;
   private CraftInventoryView bukkitEntity = null;
   private PlayerInventory player;

   public ContainerDispenser(int i, PlayerInventory playerinventory) {
      this(i, playerinventory, new InventorySubcontainer(9));
   }

   public ContainerDispenser(int i, PlayerInventory playerinventory, IInventory iinventory) {
      super(Containers.g, i);
      this.player = playerinventory;
      a(iinventory, 9);
      this.p = iinventory;
      iinventory.d_(playerinventory.m);

      for(int j = 0; j < 3; ++j) {
         for(int k = 0; k < 3; ++k) {
            this.a(new Slot(iinventory, k + j * 3, 62 + k * 18, 17 + j * 18));
         }
      }

      for(int var6 = 0; var6 < 3; ++var6) {
         for(int k = 0; k < 9; ++k) {
            this.a(new Slot(playerinventory, k + var6 * 9 + 9, 8 + k * 18, 84 + var6 * 18));
         }
      }

      for(int var7 = 0; var7 < 9; ++var7) {
         this.a(new Slot(playerinventory, var7, 8 + var7 * 18, 142));
      }
   }

   @Override
   public boolean a(EntityHuman entityhuman) {
      return !this.checkReachable ? true : this.p.a(entityhuman);
   }

   @Override
   public ItemStack a(EntityHuman entityhuman, int i) {
      ItemStack itemstack = ItemStack.b;
      Slot slot = this.i.get(i);
      if (slot != null && slot.f()) {
         ItemStack itemstack1 = slot.e();
         itemstack = itemstack1.o();
         if (i < 9) {
            if (!this.a(itemstack1, 9, 45, true)) {
               return ItemStack.b;
            }
         } else if (!this.a(itemstack1, 0, 9, false)) {
            return ItemStack.b;
         }

         if (itemstack1.b()) {
            slot.d(ItemStack.b);
         } else {
            slot.d();
         }

         if (itemstack1.K() == itemstack.K()) {
            return ItemStack.b;
         }

         slot.a(entityhuman, itemstack1);
      }

      return itemstack;
   }

   @Override
   public void b(EntityHuman entityhuman) {
      super.b(entityhuman);
      this.p.c(entityhuman);
   }

   public CraftInventoryView getBukkitView() {
      if (this.bukkitEntity != null) {
         return this.bukkitEntity;
      } else {
         CraftInventory inventory = new CraftInventory(this.p);
         this.bukkitEntity = new CraftInventoryView(this.player.m.getBukkitEntity(), inventory, this);
         return this.bukkitEntity;
      }
   }
}
