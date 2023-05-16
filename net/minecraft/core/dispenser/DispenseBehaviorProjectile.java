package net.minecraft.core.dispenser;

import net.minecraft.core.EnumDirection;
import net.minecraft.core.IPosition;
import net.minecraft.core.ISourceBlock;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.entity.projectile.IProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.BlockDispenser;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_19_R3.projectiles.CraftBlockProjectileSource;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.util.Vector;

public abstract class DispenseBehaviorProjectile extends DispenseBehaviorItem {
   @Override
   public ItemStack a(ISourceBlock isourceblock, ItemStack itemstack) {
      WorldServer worldserver = isourceblock.g();
      IPosition iposition = BlockDispenser.a(isourceblock);
      EnumDirection enumdirection = isourceblock.e().c(BlockDispenser.a);
      IProjectile iprojectile = this.a(worldserver, iposition, itemstack);
      ItemStack itemstack1 = itemstack.a(1);
      Block block = worldserver.getWorld().getBlockAt(isourceblock.d().u(), isourceblock.d().v(), isourceblock.d().w());
      CraftItemStack craftItem = CraftItemStack.asCraftMirror(itemstack1);
      BlockDispenseEvent event = new BlockDispenseEvent(
         block, craftItem.clone(), new Vector((double)enumdirection.j(), (double)((float)enumdirection.k() + 0.1F), (double)enumdirection.l())
      );
      if (!BlockDispenser.eventFired) {
         worldserver.getCraftServer().getPluginManager().callEvent(event);
      }

      if (event.isCancelled()) {
         itemstack.g(1);
         return itemstack;
      } else {
         if (!event.getItem().equals(craftItem)) {
            itemstack.g(1);
            ItemStack eventStack = CraftItemStack.asNMSCopy(event.getItem());
            IDispenseBehavior idispensebehavior = BlockDispenser.c.get(eventStack.c());
            if (idispensebehavior != IDispenseBehavior.b && idispensebehavior != this) {
               idispensebehavior.dispense(isourceblock, eventStack);
               return itemstack;
            }
         }

         iprojectile.c(event.getVelocity().getX(), event.getVelocity().getY(), event.getVelocity().getZ(), this.b(), this.a());
         iprojectile.projectileSource = new CraftBlockProjectileSource(isourceblock.f());
         worldserver.b(iprojectile);
         return itemstack;
      }
   }

   @Override
   protected void a(ISourceBlock isourceblock) {
      isourceblock.g().c(1002, isourceblock.d(), 0);
   }

   protected abstract IProjectile a(World var1, IPosition var2, ItemStack var3);

   protected float a() {
      return 6.0F;
   }

   protected float b() {
      return 1.1F;
   }
}
