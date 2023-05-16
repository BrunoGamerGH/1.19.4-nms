package org.bukkit.craftbukkit.v1_19_R3.block;

import net.minecraft.world.level.block.piston.TileEntityPiston;
import org.bukkit.World;

public class CraftMovingPiston extends CraftBlockEntityState<TileEntityPiston> {
   public CraftMovingPiston(World world, TileEntityPiston tileEntity) {
      super(world, tileEntity);
   }
}
