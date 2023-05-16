package org.bukkit.craftbukkit.v1_19_R3.block.impl;

import java.util.Set;
import net.minecraft.world.level.block.BlockBed;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.block.state.properties.BlockStateEnum;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.Bed;
import org.bukkit.block.data.type.Bed.Part;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public final class CraftBed extends CraftBlockData implements Bed, Directional {
   private static final BlockStateEnum<?> PART = getEnum(BlockBed.class, "part");
   private static final BlockStateBoolean OCCUPIED = getBoolean(BlockBed.class, "occupied");
   private static final BlockStateEnum<?> FACING = getEnum(BlockBed.class, "facing");

   public CraftBed() {
   }

   public CraftBed(IBlockData state) {
      super(state);
   }

   public Part getPart() {
      return this.get(PART, Part.class);
   }

   public void setPart(Part part) {
      this.set(PART, part);
   }

   public boolean isOccupied() {
      return this.get(OCCUPIED);
   }

   public BlockFace getFacing() {
      return this.get(FACING, BlockFace.class);
   }

   public void setFacing(BlockFace facing) {
      this.set(FACING, facing);
   }

   public Set<BlockFace> getFaces() {
      return this.getValues(FACING, BlockFace.class);
   }
}
