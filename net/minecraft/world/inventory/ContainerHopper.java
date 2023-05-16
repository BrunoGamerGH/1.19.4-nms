package net.minecraft.world.inventory;

import net.minecraft.world.IInventory;
import net.minecraft.world.InventorySubcontainer;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.player.PlayerInventory;
import net.minecraft.world.item.ItemStack;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftInventory;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftInventoryView;

public class ContainerHopper extends Container {
   public static final int k = 5;
   private final IInventory l;
   private CraftInventoryView bukkitEntity = null;
   private PlayerInventory player;

   public CraftInventoryView getBukkitView() {
      if (this.bukkitEntity != null) {
         return this.bukkitEntity;
      } else {
         CraftInventory inventory = new CraftInventory(this.l);
         this.bukkitEntity = new CraftInventoryView(this.player.m.getBukkitEntity(), inventory, this);
         return this.bukkitEntity;
      }
   }

   public ContainerHopper(int i, PlayerInventory playerinventory) {
      this(i, playerinventory, new InventorySubcontainer(5));
   }

   public ContainerHopper(int i, PlayerInventory playerinventory, IInventory iinventory) {
      super(Containers.p, i);
      this.l = iinventory;
      this.player = playerinventory;
      a(iinventory, 5);
      iinventory.d_(playerinventory.m);
      boolean flag = true;

      for(int j = 0; j < 5; ++j) {
         this.a(new Slot(iinventory, j, 44 + j * 18, 20));
      }

      for(int var7 = 0; var7 < 3; ++var7) {
         for(int k = 0; k < 9; ++k) {
            this.a(new Slot(playerinventory, k + var7 * 9 + 9, 8 + k * 18, var7 * 18 + 51));
         }
      }

      for(int var8 = 0; var8 < 9; ++var8) {
         this.a(new Slot(playerinventory, var8, 8 + var8 * 18, 109));
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
         if (i < this.l.b()) {
            if (!this.a(itemstack1, this.l.b(), this.i.size(), true)) {
               return ItemStack.b;
            }
         } else if (!this.a(itemstack1, 0, this.l.b(), false)) {
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
}
