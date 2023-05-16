package org.bukkit.craftbukkit.v1_19_R3.block;

import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.world.item.EnumColor;
import net.minecraft.world.level.block.BlockShulkerBox;
import net.minecraft.world.level.block.entity.TileEntityShulkerBox;
import org.bukkit.DyeColor;
import org.bukkit.World;
import org.bukkit.block.ShulkerBox;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftInventory;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftMagicNumbers;
import org.bukkit.inventory.Inventory;

public class CraftShulkerBox extends CraftLootable<TileEntityShulkerBox> implements ShulkerBox {
   public CraftShulkerBox(World world, TileEntityShulkerBox tileEntity) {
      super(world, tileEntity);
   }

   public Inventory getSnapshotInventory() {
      return new CraftInventory(this.getSnapshot());
   }

   public Inventory getInventory() {
      return (Inventory)(!this.isPlaced() ? this.getSnapshotInventory() : new CraftInventory(this.getTileEntity()));
   }

   public DyeColor getColor() {
      EnumColor color = ((BlockShulkerBox)CraftMagicNumbers.getBlock(this.getType())).k;
      return color == null ? null : DyeColor.getByWoolData((byte)color.a());
   }

   public void open() {
      this.requirePlaced();
      if (!this.getTileEntity().opened && this.getWorldHandle() instanceof net.minecraft.world.level.World) {
         net.minecraft.world.level.World world = this.getTileEntity().k();
         world.a(this.getPosition(), this.getTileEntity().q().b(), 1, 1);
         world.a(null, this.getPosition(), SoundEffects.uK, SoundCategory.e, 0.5F, world.z.i() * 0.1F + 0.9F);
      }

      this.getTileEntity().opened = true;
   }

   public void close() {
      this.requirePlaced();
      if (this.getTileEntity().opened && this.getWorldHandle() instanceof net.minecraft.world.level.World) {
         net.minecraft.world.level.World world = this.getTileEntity().k();
         world.a(this.getPosition(), this.getTileEntity().q().b(), 1, 0);
         world.a(null, this.getPosition(), SoundEffects.uK, SoundCategory.e, 0.5F, world.z.i() * 0.1F + 0.9F);
      }

      this.getTileEntity().opened = false;
   }
}
