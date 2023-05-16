package org.bukkit.craftbukkit.v1_19_R3.block.data.type;

import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.block.state.properties.BlockStateEnum;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Wall;
import org.bukkit.block.data.type.Wall.Height;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public abstract class CraftWall extends CraftBlockData implements Wall {
   private static final BlockStateBoolean UP = getBoolean("up");
   private static final BlockStateEnum<?>[] HEIGHTS = new BlockStateEnum[]{getEnum("north"), getEnum("east"), getEnum("south"), getEnum("west")};

   public boolean isUp() {
      return this.get(UP);
   }

   public void setUp(boolean up) {
      this.set(UP, Boolean.valueOf(up));
   }

   public Height getHeight(BlockFace face) {
      return this.get(HEIGHTS[face.ordinal()], Height.class);
   }

   public void setHeight(BlockFace face, Height height) {
      this.set(HEIGHTS[face.ordinal()], height);
   }
}
