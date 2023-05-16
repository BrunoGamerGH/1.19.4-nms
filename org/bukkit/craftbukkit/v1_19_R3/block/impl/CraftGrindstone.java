package org.bukkit.craftbukkit.v1_19_R3.block.impl;

import java.util.Set;
import net.minecraft.world.level.block.BlockGrindstone;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockStateEnum;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.FaceAttachable;
import org.bukkit.block.data.FaceAttachable.AttachedFace;
import org.bukkit.block.data.type.Grindstone;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public final class CraftGrindstone extends CraftBlockData implements Grindstone, Directional, FaceAttachable {
   private static final BlockStateEnum<?> FACING = getEnum(BlockGrindstone.class, "facing");
   private static final BlockStateEnum<?> ATTACH_FACE = getEnum(BlockGrindstone.class, "face");

   public CraftGrindstone() {
   }

   public CraftGrindstone(IBlockData state) {
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

   public AttachedFace getAttachedFace() {
      return this.get(ATTACH_FACE, AttachedFace.class);
   }

   public void setAttachedFace(AttachedFace face) {
      this.set(ATTACH_FACE, face);
   }
}
