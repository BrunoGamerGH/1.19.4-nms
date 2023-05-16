package org.bukkit.craftbukkit.v1_19_R3.block.data.type;

import net.minecraft.world.level.block.state.properties.BlockStateInteger;
import org.bukkit.block.data.type.Candle;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public abstract class CraftCandle extends CraftBlockData implements Candle {
   private static final BlockStateInteger CANDLES = getInteger("candles");

   public int getCandles() {
      return this.get(CANDLES);
   }

   public void setCandles(int candles) {
      this.set(CANDLES, Integer.valueOf(candles));
   }

   public int getMaximumCandles() {
      return getMax(CANDLES);
   }
}
