package org.bukkit.craftbukkit.v1_19_R3.block.impl;

import net.minecraft.world.level.block.BlockSkullPlayer;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockStateInteger;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Rotatable;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public final class CraftSkullPlayer extends CraftBlockData implements Rotatable {
   private static final BlockStateInteger ROTATION = getInteger(BlockSkullPlayer.class, "rotation");

   public CraftSkullPlayer() {
   }

   public CraftSkullPlayer(IBlockData state) {
      super(state);
   }

   public BlockFace getRotation() {
      int data = this.get(ROTATION);
      switch(data) {
         case 0:
            return BlockFace.SOUTH;
         case 1:
            return BlockFace.SOUTH_SOUTH_WEST;
         case 2:
            return BlockFace.SOUTH_WEST;
         case 3:
            return BlockFace.WEST_SOUTH_WEST;
         case 4:
            return BlockFace.WEST;
         case 5:
            return BlockFace.WEST_NORTH_WEST;
         case 6:
            return BlockFace.NORTH_WEST;
         case 7:
            return BlockFace.NORTH_NORTH_WEST;
         case 8:
            return BlockFace.NORTH;
         case 9:
            return BlockFace.NORTH_NORTH_EAST;
         case 10:
            return BlockFace.NORTH_EAST;
         case 11:
            return BlockFace.EAST_NORTH_EAST;
         case 12:
            return BlockFace.EAST;
         case 13:
            return BlockFace.EAST_SOUTH_EAST;
         case 14:
            return BlockFace.SOUTH_EAST;
         case 15:
            return BlockFace.SOUTH_SOUTH_EAST;
         default:
            throw new IllegalArgumentException("Unknown rotation " + data);
      }
   }

   public void setRotation(BlockFace rotation) {
      this.set(ROTATION, Integer.valueOf(switch(rotation) {
         case NORTH -> 8;
         case EAST -> 12;
         case SOUTH -> 0;
         case WEST -> 4;
         default -> throw new IllegalArgumentException("Illegal rotation " + rotation);
         case NORTH_EAST -> 10;
         case NORTH_WEST -> 6;
         case SOUTH_EAST -> 14;
         case SOUTH_WEST -> 2;
         case WEST_NORTH_WEST -> 5;
         case NORTH_NORTH_WEST -> 7;
         case NORTH_NORTH_EAST -> 9;
         case EAST_NORTH_EAST -> 11;
         case EAST_SOUTH_EAST -> 13;
         case SOUTH_SOUTH_EAST -> 15;
         case SOUTH_SOUTH_WEST -> 1;
         case WEST_SOUTH_WEST -> 3;
      }));
   }
}
