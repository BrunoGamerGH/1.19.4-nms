package net.minecraft.world.entity;

import java.util.function.Predicate;
import net.minecraft.world.IInventory;
import net.minecraft.world.item.ItemStack;

public interface SlotAccess {
   SlotAccess b = new SlotAccess() {
      @Override
      public ItemStack a() {
         return ItemStack.b;
      }

      @Override
      public boolean a(ItemStack var0) {
         return false;
      }
   };

   static SlotAccess a(final IInventory var0, final int var1, final Predicate<ItemStack> var2) {
      return new SlotAccess() {
         @Override
         public ItemStack a() {
            return var0.a(var1);
         }

         @Override
         public boolean a(ItemStack var0x) {
            if (!var2.test(var0)) {
               return false;
            } else {
               var0.a(var1, var0);
               return true;
            }
         }
      };
   }

   static SlotAccess a(IInventory var0, int var1) {
      return a(var0, var1, var0x -> true);
   }

   static SlotAccess a(final EntityLiving var0, final EnumItemSlot var1, final Predicate<ItemStack> var2) {
      return new SlotAccess() {
         @Override
         public ItemStack a() {
            return var0.c(var1);
         }

         @Override
         public boolean a(ItemStack var0x) {
            if (!var2.test(var0)) {
               return false;
            } else {
               var0.a(var1, var0);
               return true;
            }
         }
      };
   }

   static SlotAccess a(EntityLiving var0, EnumItemSlot var1) {
      return a(var0, var1, var0x -> true);
   }

   ItemStack a();

   boolean a(ItemStack var1);
}
