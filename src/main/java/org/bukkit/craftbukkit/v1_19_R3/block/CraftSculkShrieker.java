package org.bukkit.craftbukkit.v1_19_R3.block;

import net.minecraft.world.level.block.entity.SculkShriekerBlockEntity;
import org.bukkit.World;
import org.bukkit.block.SculkShrieker;

public class CraftSculkShrieker extends CraftBlockEntityState<SculkShriekerBlockEntity> implements SculkShrieker {
   public CraftSculkShrieker(World world, SculkShriekerBlockEntity tileEntity) {
      super(world, tileEntity);
   }

   public int getWarningLevel() {
      return this.getSnapshot().j;
   }

   public void setWarningLevel(int level) {
      this.getSnapshot().j = level;
   }
}
