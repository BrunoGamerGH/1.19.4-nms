package org.bukkit.craftbukkit.v1_19_R3.block.impl;

import com.google.common.collect.ImmutableSet;
import java.util.Set;
import net.minecraft.world.level.block.BlockRedstoneWire;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockStateEnum;
import net.minecraft.world.level.block.state.properties.BlockStateInteger;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.AnaloguePowerable;
import org.bukkit.block.data.type.RedstoneWire;
import org.bukkit.block.data.type.RedstoneWire.Connection;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public final class CraftRedstoneWire extends CraftBlockData implements RedstoneWire, AnaloguePowerable {
   private static final BlockStateEnum<?> NORTH = getEnum(BlockRedstoneWire.class, "north");
   private static final BlockStateEnum<?> EAST = getEnum(BlockRedstoneWire.class, "east");
   private static final BlockStateEnum<?> SOUTH = getEnum(BlockRedstoneWire.class, "south");
   private static final BlockStateEnum<?> WEST = getEnum(BlockRedstoneWire.class, "west");
   private static final BlockStateInteger POWER = getInteger(BlockRedstoneWire.class, "power");

   public CraftRedstoneWire() {
   }

   public CraftRedstoneWire(IBlockData state) {
      super(state);
   }

   public Connection getFace(BlockFace face) {
      switch(face) {
         case NORTH:
            return this.get(NORTH, Connection.class);
         case EAST:
            return this.get(EAST, Connection.class);
         case SOUTH:
            return this.get(SOUTH, Connection.class);
         case WEST:
            return this.get(WEST, Connection.class);
         default:
            throw new IllegalArgumentException("Cannot have face " + face);
      }
   }

   public void setFace(BlockFace face, Connection connection) {
      switch(face) {
         case NORTH:
            this.set(NORTH, connection);
            break;
         case EAST:
            this.set(EAST, connection);
            break;
         case SOUTH:
            this.set(SOUTH, connection);
            break;
         case WEST:
            this.set(WEST, connection);
            break;
         default:
            throw new IllegalArgumentException("Cannot have face " + face);
      }
   }

   public Set<BlockFace> getAllowedFaces() {
      return ImmutableSet.of(BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST);
   }

   public int getPower() {
      return this.get(POWER);
   }

   public void setPower(int power) {
      this.set(POWER, Integer.valueOf(power));
   }

   public int getMaximumPower() {
      return getMax(POWER);
   }
}
