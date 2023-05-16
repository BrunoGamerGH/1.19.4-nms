package org.bukkit.craftbukkit.v1_19_R3.inventory.util;

import net.minecraft.core.BlockPosition;
import net.minecraft.world.IInventory;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.TileEntityBlastFurnace;
import net.minecraft.world.level.block.entity.TileEntityBrewingStand;
import net.minecraft.world.level.block.entity.TileEntityDispenser;
import net.minecraft.world.level.block.entity.TileEntityDropper;
import net.minecraft.world.level.block.entity.TileEntityFurnace;
import net.minecraft.world.level.block.entity.TileEntityFurnaceFurnace;
import net.minecraft.world.level.block.entity.TileEntityHopper;
import net.minecraft.world.level.block.entity.TileEntityLectern;
import net.minecraft.world.level.block.entity.TileEntityLootable;
import net.minecraft.world.level.block.entity.TileEntitySmoker;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftInventory;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftInventoryBrewer;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftInventoryFurnace;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftChatMessage;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public abstract class CraftTileInventoryConverter implements CraftInventoryCreator.InventoryConverter {
   public abstract IInventory getTileEntity();

   @Override
   public Inventory createInventory(InventoryHolder holder, InventoryType type) {
      return this.getInventory(this.getTileEntity());
   }

   @Override
   public Inventory createInventory(InventoryHolder holder, InventoryType type, String title) {
      IInventory te = this.getTileEntity();
      if (te instanceof TileEntityLootable) {
         ((TileEntityLootable)te).a(CraftChatMessage.fromStringOrNull(title));
      }

      return this.getInventory(te);
   }

   public Inventory getInventory(IInventory tileEntity) {
      return new CraftInventory(tileEntity);
   }

   public static class BlastFurnace extends CraftTileInventoryConverter {
      @Override
      public IInventory getTileEntity() {
         return new TileEntityBlastFurnace(BlockPosition.b, Blocks.nS.o());
      }
   }

   public static class BrewingStand extends CraftTileInventoryConverter {
      @Override
      public IInventory getTileEntity() {
         return new TileEntityBrewingStand(BlockPosition.b, Blocks.fr.o());
      }

      @Override
      public Inventory createInventory(InventoryHolder holder, InventoryType type, String title) {
         IInventory tileEntity = this.getTileEntity();
         if (tileEntity instanceof TileEntityBrewingStand) {
            ((TileEntityBrewingStand)tileEntity).a(CraftChatMessage.fromStringOrNull(title));
         }

         return this.getInventory(tileEntity);
      }

      @Override
      public Inventory getInventory(IInventory tileEntity) {
         return new CraftInventoryBrewer(tileEntity);
      }
   }

   public static class Dispenser extends CraftTileInventoryConverter {
      @Override
      public IInventory getTileEntity() {
         return new TileEntityDispenser(BlockPosition.b, Blocks.aT.o());
      }
   }

   public static class Dropper extends CraftTileInventoryConverter {
      @Override
      public IInventory getTileEntity() {
         return new TileEntityDropper(BlockPosition.b, Blocks.hh.o());
      }
   }

   public static class Furnace extends CraftTileInventoryConverter {
      @Override
      public IInventory getTileEntity() {
         TileEntityFurnace furnace = new TileEntityFurnaceFurnace(BlockPosition.b, Blocks.cC.o());
         return furnace;
      }

      @Override
      public Inventory createInventory(InventoryHolder owner, InventoryType type, String title) {
         IInventory tileEntity = this.getTileEntity();
         ((TileEntityFurnace)tileEntity).a(CraftChatMessage.fromStringOrNull(title));
         return this.getInventory(tileEntity);
      }

      @Override
      public Inventory getInventory(IInventory tileEntity) {
         return new CraftInventoryFurnace((TileEntityFurnace)tileEntity);
      }
   }

   public static class Hopper extends CraftTileInventoryConverter {
      @Override
      public IInventory getTileEntity() {
         return new TileEntityHopper(BlockPosition.b, Blocks.hb.o());
      }
   }

   public static class Lectern extends CraftTileInventoryConverter {
      @Override
      public IInventory getTileEntity() {
         return (new TileEntityLectern(BlockPosition.b, Blocks.nW.o())).e;
      }
   }

   public static class Smoker extends CraftTileInventoryConverter {
      @Override
      public IInventory getTileEntity() {
         return new TileEntitySmoker(BlockPosition.b, Blocks.nR.o());
      }
   }
}
