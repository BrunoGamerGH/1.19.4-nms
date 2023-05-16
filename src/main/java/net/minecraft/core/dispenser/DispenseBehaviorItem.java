package net.minecraft.core.dispenser;

import net.minecraft.core.EnumDirection;
import net.minecraft.core.IPosition;
import net.minecraft.core.ISourceBlock;
import net.minecraft.world.entity.item.EntityItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.BlockDispenser;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftVector;
import org.bukkit.event.block.BlockDispenseEvent;

public class DispenseBehaviorItem implements IDispenseBehavior {
   private boolean dropper;

   public DispenseBehaviorItem(boolean dropper) {
      this.dropper = dropper;
   }

   public DispenseBehaviorItem() {
   }

   @Override
   public final ItemStack dispense(ISourceBlock isourceblock, ItemStack itemstack) {
      ItemStack itemstack1 = this.a(isourceblock, itemstack);
      this.a(isourceblock);
      this.a(isourceblock, isourceblock.e().c(BlockDispenser.a));
      return itemstack1;
   }

   protected ItemStack a(ISourceBlock isourceblock, ItemStack itemstack) {
      EnumDirection enumdirection = isourceblock.e().c(BlockDispenser.a);
      IPosition iposition = BlockDispenser.a(isourceblock);
      ItemStack itemstack1 = itemstack.a(1);
      if (!spawnItem(isourceblock.g(), itemstack1, 6, enumdirection, isourceblock, this.dropper)) {
         itemstack.g(1);
      }

      return itemstack;
   }

   public static boolean spawnItem(World world, ItemStack itemstack, int i, EnumDirection enumdirection, ISourceBlock isourceblock, boolean dropper) {
      if (itemstack.b()) {
         return true;
      } else {
         IPosition iposition = BlockDispenser.a(isourceblock);
         double d0 = iposition.a();
         double d1 = iposition.b();
         double d2 = iposition.c();
         if (enumdirection.o() == EnumDirection.EnumAxis.b) {
            d1 -= 0.125;
         } else {
            d1 -= 0.15625;
         }

         EntityItem entityitem = new EntityItem(world, d0, d1, d2, itemstack);
         double d3 = world.z.j() * 0.1 + 0.2;
         entityitem.o(
            world.z.a((double)enumdirection.j() * d3, 0.0172275 * (double)i),
            world.z.a(0.2, 0.0172275 * (double)i),
            world.z.a((double)enumdirection.l() * d3, 0.0172275 * (double)i)
         );
         Block block = world.getWorld().getBlockAt(isourceblock.d().u(), isourceblock.d().v(), isourceblock.d().w());
         CraftItemStack craftItem = CraftItemStack.asCraftMirror(itemstack);
         BlockDispenseEvent event = new BlockDispenseEvent(block, craftItem.clone(), CraftVector.toBukkit(entityitem.dj()));
         if (!BlockDispenser.eventFired) {
            world.getCraftServer().getPluginManager().callEvent(event);
         }

         if (event.isCancelled()) {
            return false;
         } else {
            entityitem.a(CraftItemStack.asNMSCopy(event.getItem()));
            entityitem.f(CraftVector.toNMS(event.getVelocity()));
            if (!dropper && !event.getItem().getType().equals(craftItem.getType())) {
               ItemStack eventStack = CraftItemStack.asNMSCopy(event.getItem());
               IDispenseBehavior idispensebehavior = BlockDispenser.c.get(eventStack.c());
               if (idispensebehavior != IDispenseBehavior.b && idispensebehavior.getClass() != DispenseBehaviorItem.class) {
                  idispensebehavior.dispense(isourceblock, eventStack);
               } else {
                  world.b(entityitem);
               }

               return false;
            } else {
               world.b(entityitem);
               return true;
            }
         }
      }
   }

   protected void a(ISourceBlock isourceblock) {
      isourceblock.g().c(1000, isourceblock.d(), 0);
   }

   protected void a(ISourceBlock isourceblock, EnumDirection enumdirection) {
      isourceblock.g().c(2000, isourceblock.d(), enumdirection.d());
   }
}
