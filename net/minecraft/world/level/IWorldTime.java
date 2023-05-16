package net.minecraft.world.level;

import net.minecraft.world.level.dimension.DimensionManager;

public interface IWorldTime extends IWorldReader {
   long ag();

   default float am() {
      return DimensionManager.i[this.q_().b(this.ag())];
   }

   default float f(float var0) {
      return this.q_().a(this.ag());
   }

   default int an() {
      return this.q_().b(this.ag());
   }
}
