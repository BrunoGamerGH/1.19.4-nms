package org.bukkit.craftbukkit.v1_19_R3.block.impl;

import net.minecraft.world.level.block.WeatheringCopperSlabBlock;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.block.state.properties.BlockStateEnum;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.block.data.type.Slab;
import org.bukkit.block.data.type.Slab.Type;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public final class CraftWeatheringCopperSlab extends CraftBlockData implements Slab, Waterlogged {
   private static final BlockStateEnum<?> TYPE = getEnum(WeatheringCopperSlabBlock.class, "type");
   private static final BlockStateBoolean WATERLOGGED = getBoolean(WeatheringCopperSlabBlock.class, "waterlogged");

   public CraftWeatheringCopperSlab() {
   }

   public CraftWeatheringCopperSlab(IBlockData state) {
      super(state);
   }

   public Type getType() {
      return this.get(TYPE, Type.class);
   }

   public void setType(Type type) {
      this.set(TYPE, type);
   }

   public boolean isWaterlogged() {
      return this.get(WATERLOGGED);
   }

   public void setWaterlogged(boolean waterlogged) {
      this.set(WATERLOGGED, Boolean.valueOf(waterlogged));
   }
}
