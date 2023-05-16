package org.bukkit.craftbukkit.v1_19_R3.block;

import com.google.common.base.Preconditions;
import net.minecraft.world.level.block.entity.SculkSensorBlockEntity;
import org.bukkit.World;
import org.bukkit.block.SculkSensor;

public class CraftSculkSensor extends CraftBlockEntityState<SculkSensorBlockEntity> implements SculkSensor {
   public CraftSculkSensor(World world, SculkSensorBlockEntity tileEntity) {
      super(world, tileEntity);
   }

   public int getLastVibrationFrequency() {
      return this.getSnapshot().d();
   }

   public void setLastVibrationFrequency(int lastVibrationFrequency) {
      Preconditions.checkArgument(lastVibrationFrequency >= 0 && lastVibrationFrequency <= 15, "Vibration frequency must be between 0-15");
      this.getSnapshot().c = lastVibrationFrequency;
   }
}
