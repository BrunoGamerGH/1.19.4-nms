package org.bukkit.craftbukkit.v1_19_R3.block.impl;

import java.util.Set;
import net.minecraft.world.level.block.BlockChain;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.block.state.properties.BlockStateEnum;
import org.bukkit.Axis;
import org.bukkit.block.data.Orientable;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.block.data.type.Chain;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public final class CraftChain extends CraftBlockData implements Chain, Orientable, Waterlogged {
   private static final BlockStateEnum<?> AXIS = getEnum(BlockChain.class, "axis");
   private static final BlockStateBoolean WATERLOGGED = getBoolean(BlockChain.class, "waterlogged");

   public CraftChain() {
   }

   public CraftChain(IBlockData state) {
      super(state);
   }

   public Axis getAxis() {
      return this.get(AXIS, Axis.class);
   }

   public void setAxis(Axis axis) {
      this.set(AXIS, axis);
   }

   public Set<Axis> getAxes() {
      return this.getValues(AXIS, Axis.class);
   }

   public boolean isWaterlogged() {
      return this.get(WATERLOGGED);
   }

   public void setWaterlogged(boolean waterlogged) {
      this.set(WATERLOGGED, Boolean.valueOf(waterlogged));
   }
}
