package org.bukkit.craftbukkit.v1_19_R3.block;

import net.minecraft.world.level.block.entity.TileEntityBell;
import org.bukkit.World;
import org.bukkit.block.Bell;

public class CraftBell extends CraftBlockEntityState<TileEntityBell> implements Bell {
   public CraftBell(World world, TileEntityBell tileEntity) {
      super(world, tileEntity);
   }
}
