package org.bukkit.craftbukkit.v1_19_R3.block.data.type;

import net.minecraft.world.level.block.state.properties.BlockStateEnum;
import org.bukkit.block.data.type.SculkSensor;
import org.bukkit.block.data.type.SculkSensor.Phase;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public abstract class CraftSculkSensor extends CraftBlockData implements SculkSensor {
   private static final BlockStateEnum<?> PHASE = getEnum("sculk_sensor_phase");

   public Phase getPhase() {
      return this.get(PHASE, Phase.class);
   }

   public void setPhase(Phase phase) {
      this.set(PHASE, phase);
   }
}
