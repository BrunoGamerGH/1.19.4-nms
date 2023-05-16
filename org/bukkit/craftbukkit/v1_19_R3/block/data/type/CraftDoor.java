package org.bukkit.craftbukkit.v1_19_R3.block.data.type;

import net.minecraft.world.level.block.state.properties.BlockStateEnum;
import org.bukkit.block.data.type.Door;
import org.bukkit.block.data.type.Door.Hinge;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public abstract class CraftDoor extends CraftBlockData implements Door {
   private static final BlockStateEnum<?> HINGE = getEnum("hinge");

   public Hinge getHinge() {
      return this.get(HINGE, Hinge.class);
   }

   public void setHinge(Hinge hinge) {
      this.set(HINGE, hinge);
   }
}
