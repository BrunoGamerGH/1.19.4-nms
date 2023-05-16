package org.bukkit.craftbukkit.v1_19_R3.block.impl;

import java.util.Set;
import net.minecraft.world.level.block.BlockSmoker;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.block.state.properties.BlockStateEnum;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Lightable;
import org.bukkit.block.data.type.Furnace;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public final class CraftSmoker extends CraftBlockData implements Furnace, Directional, Lightable {
   private static final BlockStateEnum<?> FACING = getEnum(BlockSmoker.class, "facing");
   private static final BlockStateBoolean LIT = getBoolean(BlockSmoker.class, "lit");

   public CraftSmoker() {
   }

   public CraftSmoker(IBlockData state) {
      super(state);
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

   public boolean isLit() {
      return this.get(LIT);
   }

   public void setLit(boolean lit) {
      this.set(LIT, Boolean.valueOf(lit));
   }
}
