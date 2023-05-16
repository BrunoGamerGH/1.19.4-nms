package org.bukkit.craftbukkit.v1_19_R3.block.data.type;

import com.google.common.collect.ImmutableSet;
import java.util.Set;
import net.minecraft.world.level.block.state.properties.BlockStateEnum;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.RedstoneWire;
import org.bukkit.block.data.type.RedstoneWire.Connection;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public abstract class CraftRedstoneWire extends CraftBlockData implements RedstoneWire {
   private static final BlockStateEnum<?> NORTH = getEnum("north");
   private static final BlockStateEnum<?> EAST = getEnum("east");
   private static final BlockStateEnum<?> SOUTH = getEnum("south");
   private static final BlockStateEnum<?> WEST = getEnum("west");

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
}
