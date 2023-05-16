package org.bukkit.craftbukkit.v1_19_R3.block.impl;

import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.block.state.properties.BlockStateInteger;
import org.bukkit.block.data.Lightable;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.block.data.type.Candle;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public final class CraftCandle extends CraftBlockData implements Candle, Lightable, Waterlogged {
   private static final BlockStateInteger CANDLES = getInteger(CandleBlock.class, "candles");
   private static final BlockStateBoolean LIT = getBoolean(CandleBlock.class, "lit");
   private static final BlockStateBoolean WATERLOGGED = getBoolean(CandleBlock.class, "waterlogged");

   public CraftCandle() {
   }

   public CraftCandle(IBlockData state) {
      super(state);
   }

   public int getCandles() {
      return this.get(CANDLES);
   }

   public void setCandles(int candles) {
      this.set(CANDLES, Integer.valueOf(candles));
   }

   public int getMaximumCandles() {
      return getMax(CANDLES);
   }

   public boolean isLit() {
      return this.get(LIT);
   }

   public void setLit(boolean lit) {
      this.set(LIT, Boolean.valueOf(lit));
   }

   public boolean isWaterlogged() {
      return this.get(WATERLOGGED);
   }

   public void setWaterlogged(boolean waterlogged) {
      this.set(WATERLOGGED, Boolean.valueOf(waterlogged));
   }
}
