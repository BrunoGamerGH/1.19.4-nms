package org.bukkit.craftbukkit.v1_19_R3.block.data.type;

import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import org.bukkit.block.data.type.SculkShrieker;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public abstract class CraftSculkShrieker extends CraftBlockData implements SculkShrieker {
   private static final BlockStateBoolean CAN_SUMMON = getBoolean("can_summon");
   private static final BlockStateBoolean SHRIEKING = getBoolean("shrieking");

   public boolean isCanSummon() {
      return this.get(CAN_SUMMON);
   }

   public void setCanSummon(boolean can_summon) {
      this.set(CAN_SUMMON, Boolean.valueOf(can_summon));
   }

   public boolean isShrieking() {
      return this.get(SHRIEKING);
   }

   public void setShrieking(boolean shrieking) {
      this.set(SHRIEKING, Boolean.valueOf(shrieking));
   }
}
