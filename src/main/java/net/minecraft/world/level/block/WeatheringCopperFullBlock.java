package net.minecraft.world.level.block;

import net.minecraft.core.BlockPosition;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;

public class WeatheringCopperFullBlock extends Block implements WeatheringCopper {
   private final WeatheringCopper.a d;

   public WeatheringCopperFullBlock(WeatheringCopper.a var0, BlockBase.Info var1) {
      super(var1);
      this.d = var0;
   }

   @Override
   public void b(IBlockData var0, WorldServer var1, BlockPosition var2, RandomSource var3) {
      this.a_(var0, var1, var2, var3);
   }

   @Override
   public boolean e_(IBlockData var0) {
      return WeatheringCopper.c(var0.b()).isPresent();
   }

   public WeatheringCopper.a g() {
      return this.d;
   }
}
