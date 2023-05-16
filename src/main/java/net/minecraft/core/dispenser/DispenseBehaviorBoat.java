package net.minecraft.core.dispenser;

import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.ISourceBlock;
import net.minecraft.server.level.WorldServer;
import net.minecraft.tags.TagsFluid;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.vehicle.ChestBoat;
import net.minecraft.world.entity.vehicle.EntityBoat;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.BlockDispenser;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.util.Vector;

public class DispenseBehaviorBoat extends DispenseBehaviorItem {
   private final DispenseBehaviorItem c = new DispenseBehaviorItem();
   private final EntityBoat.EnumBoatType d;
   private final boolean e;

   public DispenseBehaviorBoat(EntityBoat.EnumBoatType entityboat_enumboattype) {
      this(entityboat_enumboattype, false);
   }

   public DispenseBehaviorBoat(EntityBoat.EnumBoatType entityboat_enumboattype, boolean flag) {
      this.d = entityboat_enumboattype;
      this.e = flag;
   }

   @Override
   public ItemStack a(ISourceBlock isourceblock, ItemStack itemstack) {
      EnumDirection enumdirection = isourceblock.e().c(BlockDispenser.a);
      WorldServer worldserver = isourceblock.g();
      double d0 = isourceblock.a() + (double)((float)enumdirection.j() * 1.125F);
      double d1 = isourceblock.b() + (double)((float)enumdirection.k() * 1.125F);
      double d2 = isourceblock.c() + (double)((float)enumdirection.l() * 1.125F);
      BlockPosition blockposition = isourceblock.d().a(enumdirection);
      double d3;
      if (worldserver.b_(blockposition).a(TagsFluid.a)) {
         d3 = 1.0;
      } else {
         if (!worldserver.a_(blockposition).h() || !worldserver.b_(blockposition.d()).a(TagsFluid.a)) {
            return this.c.dispense(isourceblock, itemstack);
         }

         d3 = 0.0;
      }

      ItemStack itemstack1 = itemstack.a(1);
      Block block = worldserver.getWorld().getBlockAt(isourceblock.d().u(), isourceblock.d().v(), isourceblock.d().w());
      CraftItemStack craftItem = CraftItemStack.asCraftMirror(itemstack1);
      BlockDispenseEvent event = new BlockDispenseEvent(block, craftItem.clone(), new Vector(d0, d1 + d3, d2));
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

         Object object = this.e
            ? new ChestBoat(worldserver, event.getVelocity().getX(), event.getVelocity().getY(), event.getVelocity().getZ())
            : new EntityBoat(worldserver, event.getVelocity().getX(), event.getVelocity().getY(), event.getVelocity().getZ());
         ((EntityBoat)object).a(this.d);
         ((EntityBoat)object).f(enumdirection.p());
         if (!worldserver.b((Entity)object)) {
            itemstack.g(1);
         }

         return itemstack;
      }
   }

   @Override
   protected void a(ISourceBlock isourceblock) {
      isourceblock.g().c(1000, isourceblock.d(), 0);
   }
}
