package org.bukkit.craftbukkit.v1_19_R3.block.impl;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSet.Builder;
import java.util.Set;
import net.minecraft.world.level.block.ChiseledBookShelfBlock;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.block.state.properties.BlockStateEnum;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.ChiseledBookshelf;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public final class CraftChiseledBookShelf extends CraftBlockData implements ChiseledBookshelf, Directional {
   private static final BlockStateBoolean[] SLOT_OCCUPIED = new BlockStateBoolean[]{
      getBoolean(ChiseledBookShelfBlock.class, "slot_0_occupied"),
      getBoolean(ChiseledBookShelfBlock.class, "slot_1_occupied"),
      getBoolean(ChiseledBookShelfBlock.class, "slot_2_occupied"),
      getBoolean(ChiseledBookShelfBlock.class, "slot_3_occupied"),
      getBoolean(ChiseledBookShelfBlock.class, "slot_4_occupied"),
      getBoolean(ChiseledBookShelfBlock.class, "slot_5_occupied")
   };
   private static final BlockStateEnum<?> FACING = getEnum(ChiseledBookShelfBlock.class, "facing");

   public CraftChiseledBookShelf() {
   }

   public CraftChiseledBookShelf(IBlockData state) {
      super(state);
   }

   public boolean isSlotOccupied(int slot) {
      return this.get(SLOT_OCCUPIED[slot]);
   }

   public void setSlotOccupied(int slot, boolean has) {
      this.set(SLOT_OCCUPIED[slot], Boolean.valueOf(has));
   }

   public Set<Integer> getOccupiedSlots() {
      Builder<Integer> slots = ImmutableSet.builder();

      for(int index = 0; index < this.getMaximumOccupiedSlots(); ++index) {
         if (this.isSlotOccupied(index)) {
            slots.add(index);
         }
      }

      return slots.build();
   }

   public int getMaximumOccupiedSlots() {
      return SLOT_OCCUPIED.length;
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
