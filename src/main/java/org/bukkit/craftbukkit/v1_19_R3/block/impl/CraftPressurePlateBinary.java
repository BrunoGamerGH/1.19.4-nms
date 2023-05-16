package org.bukkit.craftbukkit.v1_19_R3.block.impl;

import net.minecraft.world.level.block.BlockPressurePlateBinary;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import org.bukkit.block.data.Powerable;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public final class CraftPressurePlateBinary extends CraftBlockData implements Powerable {
   private static final BlockStateBoolean POWERED = getBoolean(BlockPressurePlateBinary.class, "powered");

   public CraftPressurePlateBinary() {
   }

   public CraftPressurePlateBinary(IBlockData state) {
      super(state);
   }

   public boolean isPowered() {
      return this.get(POWERED);
   }

   public void setPowered(boolean powered) {
      this.set(POWERED, Boolean.valueOf(powered));
   }
}
