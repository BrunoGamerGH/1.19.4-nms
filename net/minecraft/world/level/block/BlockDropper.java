package net.minecraft.world.level.block;

import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.SourceBlock;
import net.minecraft.core.dispenser.DispenseBehaviorItem;
import net.minecraft.core.dispenser.IDispenseBehavior;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.IInventory;
import net.minecraft.world.InventoryLargeChest;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.entity.TileEntityDispenser;
import net.minecraft.world.level.block.entity.TileEntityDropper;
import net.minecraft.world.level.block.entity.TileEntityHopper;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftInventoryDoubleChest;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.inventory.Inventory;

public class BlockDropper extends BlockDispenser {
   private static final IDispenseBehavior c = new DispenseBehaviorItem(true);

   public BlockDropper(BlockBase.Info blockbase_info) {
      super(blockbase_info);
   }

   @Override
   protected IDispenseBehavior a(ItemStack itemstack) {
      return c;
   }

   @Override
   public TileEntity a(BlockPosition blockposition, IBlockData iblockdata) {
      return new TileEntityDropper(blockposition, iblockdata);
   }

   @Override
   public void a(WorldServer worldserver, BlockPosition blockposition) {
      SourceBlock sourceblock = new SourceBlock(worldserver, blockposition);
      TileEntityDispenser tileentitydispenser = sourceblock.f();
      int i = tileentitydispenser.a(worldserver.z);
      if (i < 0) {
         worldserver.c(1001, blockposition, 0);
      } else {
         ItemStack itemstack = tileentitydispenser.a(i);
         if (!itemstack.b()) {
            EnumDirection enumdirection = worldserver.a_(blockposition).c(a);
            IInventory iinventory = TileEntityHopper.a(worldserver, blockposition.a(enumdirection));
            ItemStack itemstack1;
            if (iinventory == null) {
               itemstack1 = c.dispense(sourceblock, itemstack);
            } else {
               CraftItemStack oitemstack = CraftItemStack.asCraftMirror(itemstack.o().a(1));
               Inventory destinationInventory;
               if (iinventory instanceof InventoryLargeChest) {
                  destinationInventory = new CraftInventoryDoubleChest((InventoryLargeChest)iinventory);
               } else {
                  destinationInventory = iinventory.getOwner().getInventory();
               }

               InventoryMoveItemEvent event = new InventoryMoveItemEvent(
                  tileentitydispenser.getOwner().getInventory(), oitemstack.clone(), destinationInventory, true
               );
               worldserver.getCraftServer().getPluginManager().callEvent(event);
               if (event.isCancelled()) {
                  return;
               }

               itemstack1 = TileEntityHopper.a(tileentitydispenser, iinventory, CraftItemStack.asNMSCopy(event.getItem()), enumdirection.g());
               if (event.getItem().equals(oitemstack) && itemstack1.b()) {
                  itemstack1 = itemstack.o();
                  itemstack1.h(1);
               } else {
                  itemstack1 = itemstack.o();
               }
            }

            tileentitydispenser.a(i, itemstack1);
         }
      }
   }
}
