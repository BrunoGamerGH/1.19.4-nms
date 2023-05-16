package net.minecraft.world.inventory;

import net.minecraft.world.IInventory;
import net.minecraft.world.entity.animal.horse.EntityHorseAbstract;
import net.minecraft.world.entity.animal.horse.EntityHorseChestedAbstract;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.player.PlayerInventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftInventoryView;
import org.bukkit.inventory.InventoryView;

public class ContainerHorse extends Container {
   private final IInventory k;
   private final EntityHorseAbstract l;
   CraftInventoryView bukkitEntity;
   PlayerInventory player;

   @Override
   public InventoryView getBukkitView() {
      return this.bukkitEntity != null
         ? this.bukkitEntity
         : (this.bukkitEntity = new CraftInventoryView(this.player.m.getBukkitEntity(), this.k.getOwner().getInventory(), this));
   }

   public ContainerHorse(int i, PlayerInventory playerinventory, IInventory iinventory, final EntityHorseAbstract entityhorseabstract) {
      super(null, i);
      this.player = playerinventory;
      this.k = iinventory;
      this.l = entityhorseabstract;
      boolean flag = true;
      iinventory.d_(playerinventory.m);
      boolean flag1 = true;
      this.a(new Slot(iinventory, 0, 8, 18) {
         @Override
         public boolean a(ItemStack itemstack) {
            return itemstack.a(Items.mV) && !this.f() && entityhorseabstract.g();
         }

         @Override
         public boolean b() {
            return entityhorseabstract.g();
         }
      });
      this.a(new Slot(iinventory, 1, 8, 36) {
         @Override
         public boolean a(ItemStack itemstack) {
            return entityhorseabstract.l(itemstack);
         }

         @Override
         public boolean b() {
            return entityhorseabstract.gB();
         }

         @Override
         public int a() {
            return 1;
         }
      });
      if (this.a(entityhorseabstract)) {
         for(int j = 0; j < 3; ++j) {
            for(int k = 0; k < ((EntityHorseChestedAbstract)entityhorseabstract).ga(); ++k) {
               this.a(new Slot(iinventory, 2 + k + j * ((EntityHorseChestedAbstract)entityhorseabstract).ga(), 80 + k * 18, 18 + j * 18));
            }
         }
      }

      for(int j = 0; j < 3; ++j) {
         for(int k = 0; k < 9; ++k) {
            this.a(new Slot(playerinventory, k + j * 9 + 9, 8 + k * 18, 102 + j * 18 + -18));
         }
      }

      for(int var10 = 0; var10 < 9; ++var10) {
         this.a(new Slot(playerinventory, var10, 8 + var10 * 18, 142));
      }
   }

   @Override
   public boolean a(EntityHuman entityhuman) {
      return !this.l.b(this.k) && this.k.a(entityhuman) && this.l.bq() && this.l.e(entityhuman) < 8.0F;
   }

   private boolean a(EntityHorseAbstract entityhorseabstract) {
      return entityhorseabstract instanceof EntityHorseChestedAbstract && ((EntityHorseChestedAbstract)entityhorseabstract).r();
   }

   @Override
   public ItemStack a(EntityHuman entityhuman, int i) {
      ItemStack itemstack = ItemStack.b;
      Slot slot = this.i.get(i);
      if (slot != null && slot.f()) {
         ItemStack itemstack1 = slot.e();
         itemstack = itemstack1.o();
         int j = this.k.b();
         if (i < j) {
            if (!this.a(itemstack1, j, this.i.size(), true)) {
               return ItemStack.b;
            }
         } else if (this.b(1).a(itemstack1) && !this.b(1).f()) {
            if (!this.a(itemstack1, 1, 2, false)) {
               return ItemStack.b;
            }
         } else if (this.b(0).a(itemstack1)) {
            if (!this.a(itemstack1, 0, 1, false)) {
               return ItemStack.b;
            }
         } else if (j <= 2 || !this.a(itemstack1, 2, j, false)) {
            int k = j + 27;
            int l = k + 9;
            if (i >= k && i < l) {
               if (!this.a(itemstack1, j, k, false)) {
                  return ItemStack.b;
               }
            } else if (i >= j && i < k) {
               if (!this.a(itemstack1, k, l, false)) {
                  return ItemStack.b;
               }
            } else if (!this.a(itemstack1, k, k, false)) {
               return ItemStack.b;
            }

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
      this.k.c(entityhuman);
   }
}
