package net.minecraft.world.entity.npc;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.InventorySubcontainer;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.item.EntityItem;
import net.minecraft.world.item.ItemStack;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;

public interface InventoryCarrier {
   String c_ = "Inventory";

   InventorySubcontainer w();

   static void a(EntityInsentient entityinsentient, InventoryCarrier inventorycarrier, EntityItem entityitem) {
      ItemStack itemstack = entityitem.i();
      if (entityinsentient.k(itemstack)) {
         InventorySubcontainer inventorysubcontainer = inventorycarrier.w();
         boolean flag = inventorysubcontainer.b(itemstack);
         if (!flag) {
            return;
         }

         ItemStack remaining = new InventorySubcontainer(inventorysubcontainer).a(itemstack);
         if (CraftEventFactory.callEntityPickupItemEvent(entityinsentient, entityitem, remaining.K(), false).isCancelled()) {
            return;
         }

         entityinsentient.a(entityitem);
         int i = itemstack.K();
         ItemStack itemstack1 = inventorysubcontainer.a(itemstack);
         entityinsentient.a(entityitem, i - itemstack1.K());
         if (itemstack1.b()) {
            entityitem.ai();
         } else {
            itemstack.f(itemstack1.K());
         }
      }
   }

   default void c(NBTTagCompound nbttagcompound) {
      if (nbttagcompound.b("Inventory", 9)) {
         this.w().a(nbttagcompound.c("Inventory", 10));
      }
   }

   default void a_(NBTTagCompound nbttagcompound) {
      nbttagcompound.a("Inventory", this.w().g());
   }
}
