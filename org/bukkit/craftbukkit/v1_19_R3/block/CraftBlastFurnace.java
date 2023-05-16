package org.bukkit.craftbukkit.v1_19_R3.block;

import net.minecraft.world.level.block.entity.TileEntityBlastFurnace;
import org.bukkit.World;
import org.bukkit.block.BlastFurnace;

public class CraftBlastFurnace extends CraftFurnace<TileEntityBlastFurnace> implements BlastFurnace {
   public CraftBlastFurnace(World world, TileEntityBlastFurnace tileEntity) {
      super(world, tileEntity);
   }
}
