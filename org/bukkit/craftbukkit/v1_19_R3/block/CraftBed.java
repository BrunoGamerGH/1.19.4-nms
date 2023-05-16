package org.bukkit.craftbukkit.v1_19_R3.block;

import net.minecraft.world.level.block.entity.TileEntityBed;
import org.bukkit.DyeColor;
import org.bukkit.World;
import org.bukkit.block.Bed;

public class CraftBed extends CraftBlockEntityState<TileEntityBed> implements Bed {
   public CraftBed(World world, TileEntityBed tileEntity) {
      super(world, tileEntity);
   }

   public DyeColor getColor() {
      switch($SWITCH_TABLE$org$bukkit$Material()[this.getType().ordinal()]) {
         case 921:
            return DyeColor.WHITE;
         case 922:
            return DyeColor.ORANGE;
         case 923:
            return DyeColor.MAGENTA;
         case 924:
            return DyeColor.LIGHT_BLUE;
         case 925:
            return DyeColor.YELLOW;
         case 926:
            return DyeColor.LIME;
         case 927:
            return DyeColor.PINK;
         case 928:
            return DyeColor.GRAY;
         case 929:
            return DyeColor.LIGHT_GRAY;
         case 930:
            return DyeColor.CYAN;
         case 931:
            return DyeColor.PURPLE;
         case 932:
            return DyeColor.BLUE;
         case 933:
            return DyeColor.BROWN;
         case 934:
            return DyeColor.GREEN;
         case 935:
            return DyeColor.RED;
         case 936:
            return DyeColor.BLACK;
         default:
            throw new IllegalArgumentException("Unknown DyeColor for " + this.getType());
      }
   }

   public void setColor(DyeColor color) {
      throw new UnsupportedOperationException("Must set block type to appropriate bed colour");
   }
}
