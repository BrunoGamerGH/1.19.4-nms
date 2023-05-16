package net.minecraft.core.dispenser;

import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.ISourceBlock;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemBlock;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockActionContext;
import net.minecraft.world.item.context.BlockActionContextDirectional;
import net.minecraft.world.level.block.BlockDispenser;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.util.Vector;
import org.slf4j.Logger;

public class DispenseBehaviorShulkerBox extends DispenseBehaviorMaybe {
   private static final Logger c = LogUtils.getLogger();

   @Override
   protected ItemStack a(ISourceBlock isourceblock, ItemStack itemstack) {
      this.a(false);
      Item item = itemstack.c();
      if (item instanceof ItemBlock) {
         EnumDirection enumdirection = isourceblock.e().c(BlockDispenser.a);
         BlockPosition blockposition = isourceblock.d().a(enumdirection);
         EnumDirection enumdirection1 = isourceblock.g().w(blockposition.d()) ? enumdirection : EnumDirection.b;
         Block bukkitBlock = isourceblock.g().getWorld().getBlockAt(isourceblock.d().u(), isourceblock.d().v(), isourceblock.d().w());
         CraftItemStack craftItem = CraftItemStack.asCraftMirror(itemstack);
         BlockDispenseEvent event = new BlockDispenseEvent(bukkitBlock, craftItem.clone(), new Vector(blockposition.u(), blockposition.v(), blockposition.w()));
         if (!BlockDispenser.eventFired) {
            isourceblock.g().getCraftServer().getPluginManager().callEvent(event);
         }

         if (event.isCancelled()) {
            return itemstack;
         }

         if (!event.getItem().equals(craftItem)) {
            ItemStack eventStack = CraftItemStack.asNMSCopy(event.getItem());
            IDispenseBehavior idispensebehavior = BlockDispenser.c.get(eventStack.c());
            if (idispensebehavior != IDispenseBehavior.b && idispensebehavior != this) {
               idispensebehavior.dispense(isourceblock, eventStack);
               return itemstack;
            }
         }

         try {
            this.a(
               ((ItemBlock)item)
                  .a((BlockActionContext)(new BlockActionContextDirectional(isourceblock.g(), blockposition, enumdirection, itemstack, enumdirection1)))
                  .a()
            );
         } catch (Exception var12) {
            c.error("Error trying to place shulker box at {}", blockposition, var12);
         }
      }

      return itemstack;
   }
}
