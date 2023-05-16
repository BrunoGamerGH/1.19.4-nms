package org.bukkit.craftbukkit.v1_19_R3.block.impl;

import java.util.Set;
import net.minecraft.world.level.block.BlockEnderPortalFrame;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.block.state.properties.BlockStateEnum;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.EndPortalFrame;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public final class CraftEnderPortalFrame extends CraftBlockData implements EndPortalFrame, Directional {
   private static final BlockStateBoolean EYE = getBoolean(BlockEnderPortalFrame.class, "eye");
   private static final BlockStateEnum<?> FACING = getEnum(BlockEnderPortalFrame.class, "facing");

   public CraftEnderPortalFrame() {
   }

   public CraftEnderPortalFrame(IBlockData state) {
      super(state);
   }

   public boolean hasEye() {
      return this.get(EYE);
   }

   public void setEye(boolean eye) {
      this.set(EYE, Boolean.valueOf(eye));
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
