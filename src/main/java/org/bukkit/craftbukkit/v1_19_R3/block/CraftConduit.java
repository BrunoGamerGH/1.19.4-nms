package org.bukkit.craftbukkit.v1_19_R3.block;

import net.minecraft.world.level.block.entity.TileEntityConduit;
import org.bukkit.World;
import org.bukkit.block.Conduit;

public class CraftConduit extends CraftBlockEntityState<TileEntityConduit> implements Conduit {
   public CraftConduit(World world, TileEntityConduit tileEntity) {
      super(world, tileEntity);
   }
}
