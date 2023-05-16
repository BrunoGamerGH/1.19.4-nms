package org.bukkit.craftbukkit.v1_19_R3.block.data;

import net.minecraft.world.level.block.state.properties.BlockStateEnum;
import org.bukkit.block.data.FaceAttachable;
import org.bukkit.block.data.FaceAttachable.AttachedFace;

public abstract class CraftFaceAttachable extends CraftBlockData implements FaceAttachable {
   private static final BlockStateEnum<?> ATTACH_FACE = getEnum("face");

   public AttachedFace getAttachedFace() {
      return this.get(ATTACH_FACE, AttachedFace.class);
   }

   public void setAttachedFace(AttachedFace face) {
      this.set(ATTACH_FACE, face);
   }
}
