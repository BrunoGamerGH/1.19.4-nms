package org.bukkit.craftbukkit.v1_19_R3.block.impl;

import net.minecraft.world.level.block.SculkSensorBlock;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.block.state.properties.BlockStateEnum;
import net.minecraft.world.level.block.state.properties.BlockStateInteger;
import org.bukkit.block.data.AnaloguePowerable;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.block.data.type.SculkSensor;
import org.bukkit.block.data.type.SculkSensor.Phase;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public final class CraftSculkSensor extends CraftBlockData implements SculkSensor, AnaloguePowerable, Waterlogged {
   private static final BlockStateEnum<?> PHASE = getEnum(SculkSensorBlock.class, "sculk_sensor_phase");
   private static final BlockStateInteger POWER = getInteger(SculkSensorBlock.class, "power");
   private static final BlockStateBoolean WATERLOGGED = getBoolean(SculkSensorBlock.class, "waterlogged");

   public CraftSculkSensor() {
   }

   public CraftSculkSensor(IBlockData state) {
      super(state);
   }

   public Phase getPhase() {
      return this.get(PHASE, Phase.class);
   }

   public void setPhase(Phase phase) {
      this.set(PHASE, phase);
   }

   public int getPower() {
      return this.get(POWER);
   }

   public void setPower(int power) {
      this.set(POWER, Integer.valueOf(power));
   }

   public int getMaximumPower() {
      return getMax(POWER);
   }

   public boolean isWaterlogged() {
      return this.get(WATERLOGGED);
   }

   public void setWaterlogged(boolean waterlogged) {
      this.set(WATERLOGGED, Boolean.valueOf(waterlogged));
   }
}
