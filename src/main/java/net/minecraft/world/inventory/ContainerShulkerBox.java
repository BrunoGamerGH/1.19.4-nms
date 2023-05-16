package net.minecraft.world.inventory;

import net.minecraft.world.IInventory;
import net.minecraft.world.InventorySubcontainer;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.player.PlayerInventory;
import net.minecraft.world.item.ItemStack;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftInventory;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftInventoryView;

public class ContainerShulkerBox extends Container {
   private static final int k = 27;
   private final IInventory l;
   private CraftInventoryView bukkitEntity;
   private PlayerInventory player;

   public CraftInventoryView getBukkitView() {
      if (this.bukkitEntity != null) {
         return this.bukkitEntity;
      } else {
         this.bukkitEntity = new CraftInventoryView(this.player.m.getBukkitEntity(), new CraftInventory(this.l), this);
         return this.bukkitEntity;
      }
   }

   public ContainerShulkerBox(int i, PlayerInventory playerinventory) {
      this(i, playerinventory, new InventorySubcontainer(27));
   }

   public ContainerShulkerBox(int i, PlayerInventory playerinventory, IInventory iinventory) {
      super(Containers.t, i);
      a(iinventory, 27);
      this.l = iinventory;
      this.player = playerinventory;
      iinventory.d_(playerinventory.m);
      boolean flag = true;
      boolean flag1 = true;

      for(int j = 0; j < 3; ++j) {
         for(int k = 0; k < 9; ++k) {
            this.a(new SlotShulkerBox(iinventory, k + j * 9, 8 + k * 18, 18 + j * 18));
         }
      }

      for(int var8 = 0; var8 < 3; ++var8) {
         for(int k = 0; k < 9; ++k) {
            this.a(new Slot(playerinventory, k + var8 * 9 + 9, 8 + k * 18, 84 + var8 * 18));
         }
      }

      for(int var9 = 0; var9 < 9; ++var9) {
         this.a(new Slot(playerinventory, var9, 8 + var9 * 18, 142));
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
