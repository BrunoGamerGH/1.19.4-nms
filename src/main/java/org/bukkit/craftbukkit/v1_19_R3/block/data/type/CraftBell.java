package org.bukkit.craftbukkit.v1_19_R3.block.data.type;

import net.minecraft.world.level.block.state.properties.BlockStateEnum;
import org.bukkit.block.data.type.Bell;
import org.bukkit.block.data.type.Bell.Attachment;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public abstract class CraftBell extends CraftBlockData implements Bell {
   private static final BlockStateEnum<?> ATTACHMENT = getEnum("attachment");

   public Attachment getAttachment() {
      return this.get(ATTACHMENT, Attachment.class);
   }

   public void setAttachment(Attachment leaves) {
      this.set(ATTACHMENT, leaves);
   }
}
