package org.bukkit.craftbukkit.v1_19_R3.block.impl;

import java.util.Set;
import net.minecraft.world.level.block.piston.BlockPistonMoving;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockStateEnum;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.TechnicalPiston;
import org.bukkit.block.data.type.TechnicalPiston.Type;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public final class CraftPistonMoving extends CraftBlockData implements TechnicalPiston, Directional {
   private static final BlockStateEnum<?> TYPE = getEnum(BlockPistonMoving.class, "type");
   private static final BlockStateEnum<?> FACING = getEnum(BlockPistonMoving.class, "facing");

   public CraftPistonMoving() {
   }

   public CraftPistonMoving(IBlockData state) {
      super(state);
   }

   public Type getType() {
      return this.get(TYPE, Type.class);
   }

   public void setType(Type type) {
      this.set(TYPE, type);
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
