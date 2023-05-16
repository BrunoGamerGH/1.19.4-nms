package org.bukkit.craftbukkit.v1_19_R3.entity;

import com.google.common.base.Preconditions;
import net.minecraft.world.entity.Display;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.EntityType;

public class CraftBlockDisplay extends CraftDisplay implements BlockDisplay {
   public CraftBlockDisplay(CraftServer server, Display.BlockDisplay entity) {
      super(server, entity);
   }

   public Display.BlockDisplay getHandle() {
      return (Display.BlockDisplay)super.getHandle();
   }

   @Override
   public String toString() {
      return "CraftBlockDisplay";
   }

   @Override
   public EntityType getType() {
      return EntityType.BLOCK_DISPLAY;
   }

   public BlockData getBlock() {
      return CraftBlockData.fromData(this.getHandle().o());
   }

   public void setBlock(BlockData block) {
      Preconditions.checkArgument(block != null, "Block cannot be null");
      this.getHandle().b(((CraftBlockData)block).getState());
   }
}
