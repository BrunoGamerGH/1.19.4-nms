package org.bukkit.craftbukkit.v1_19_R3.block.impl;

import java.util.Set;
import net.minecraft.world.level.block.BlockBell;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.block.state.properties.BlockStateEnum;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Powerable;
import org.bukkit.block.data.type.Bell;
import org.bukkit.block.data.type.Bell.Attachment;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public final class CraftBell extends CraftBlockData implements Bell, Directional, Powerable {
   private static final BlockStateEnum<?> ATTACHMENT = getEnum(BlockBell.class, "attachment");
   private static final BlockStateEnum<?> FACING = getEnum(BlockBell.class, "facing");
   private static final BlockStateBoolean POWERED = getBoolean(BlockBell.class, "powered");

   public CraftBell() {
   }

   public CraftBell(IBlockData state) {
      super(state);
   }

   public Attachment getAttachment() {
      return this.get(ATTACHMENT, Attachment.class);
   }

   public void setAttachment(Attachment leaves) {
      this.set(ATTACHMENT, leaves);
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

   public boolean isPowered() {
      return this.get(POWERED);
   }

   public void setPowered(boolean powered) {
      this.set(POWERED, Boolean.valueOf(powered));
   }
}
