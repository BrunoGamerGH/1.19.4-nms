package org.bukkit.craftbukkit.v1_19_R3.block.impl;

import java.util.Set;
import net.minecraft.world.level.block.piston.BlockPistonExtension;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.block.state.properties.BlockStateEnum;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.PistonHead;
import org.bukkit.block.data.type.TechnicalPiston;
import org.bukkit.block.data.type.TechnicalPiston.Type;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public final class CraftPistonExtension extends CraftBlockData implements PistonHead, TechnicalPiston, Directional {
   private static final BlockStateBoolean SHORT = getBoolean(BlockPistonExtension.class, "short");
   private static final BlockStateEnum<?> TYPE = getEnum(BlockPistonExtension.class, "type");
   private static final BlockStateEnum<?> FACING = getEnum(BlockPistonExtension.class, "facing");

   public CraftPistonExtension() {
   }

   public CraftPistonExtension(IBlockData state) {
      super(state);
   }

   public boolean isShort() {
      return this.get(SHORT);
   }

   public void setShort(boolean _short) {
      this.set(SHORT, Boolean.valueOf(_short));
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
