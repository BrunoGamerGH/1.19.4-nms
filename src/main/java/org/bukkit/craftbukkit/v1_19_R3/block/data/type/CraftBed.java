package org.bukkit.craftbukkit.v1_19_R3.block.data.type;

import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.block.state.properties.BlockStateEnum;
import org.bukkit.block.data.type.Bed;
import org.bukkit.block.data.type.Bed.Part;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public abstract class CraftBed extends CraftBlockData implements Bed {
   private static final BlockStateEnum<?> PART = getEnum("part");
   private static final BlockStateBoolean OCCUPIED = getBoolean("occupied");

   public Part getPart() {
      return this.get(PART, Part.class);
   }

   public void setPart(Part part) {
      this.set(PART, part);
   }

   public boolean isOccupied() {
      return this.get(OCCUPIED);
   }
}
