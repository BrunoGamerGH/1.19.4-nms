package org.bukkit.craftbukkit.v1_19_R3.block.data.type;

import net.minecraft.world.level.block.state.properties.BlockStateEnum;
import org.bukkit.block.data.type.Switch;
import org.bukkit.block.data.type.Switch.Face;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public abstract class CraftSwitch extends CraftBlockData implements Switch {
   private static final BlockStateEnum<?> FACE = getEnum("face");

   public Face getFace() {
      return this.get(FACE, Face.class);
   }

   public void setFace(Face face) {
      this.set(FACE, face);
   }
}
