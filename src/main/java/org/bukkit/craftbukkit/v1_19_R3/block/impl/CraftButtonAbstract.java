package org.bukkit.craftbukkit.v1_19_R3.block.impl;

import java.util.Set;
import net.minecraft.world.level.block.BlockButtonAbstract;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.block.state.properties.BlockStateEnum;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.FaceAttachable;
import org.bukkit.block.data.Powerable;
import org.bukkit.block.data.FaceAttachable.AttachedFace;
import org.bukkit.block.data.type.Switch;
import org.bukkit.block.data.type.Switch.Face;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public final class CraftButtonAbstract extends CraftBlockData implements Switch, Directional, FaceAttachable, Powerable {
   private static final BlockStateEnum<?> FACE = getEnum(BlockButtonAbstract.class, "face");
   private static final BlockStateEnum<?> FACING = getEnum(BlockButtonAbstract.class, "facing");
   private static final BlockStateEnum<?> ATTACH_FACE = getEnum(BlockButtonAbstract.class, "face");
   private static final BlockStateBoolean POWERED = getBoolean(BlockButtonAbstract.class, "powered");

   public CraftButtonAbstract() {
   }

   public CraftButtonAbstract(IBlockData state) {
      super(state);
   }

   public Face getFace() {
      return this.get(FACE, Face.class);
   }

   public void setFace(Face face) {
      this.set(FACE, face);
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

   public boolean isPowered() {
      return this.get(POWERED);
   }

   public void setPowered(boolean powered) {
      this.set(POWERED, Boolean.valueOf(powered));
   }
}
