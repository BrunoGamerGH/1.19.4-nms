package org.bukkit.craftbukkit.v1_19_R3.block;

import net.minecraft.world.level.block.entity.TileEntityFurnaceFurnace;
import org.bukkit.World;

public class CraftFurnaceFurnace extends CraftFurnace<TileEntityFurnaceFurnace> {
   public CraftFurnaceFurnace(World world, TileEntityFurnaceFurnace tileEntity) {
      super(world, tileEntity);
   }
}
