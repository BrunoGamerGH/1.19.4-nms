package net.minecraft.world.level;

import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.world.level.lighting.LightEngine;

public interface IBlockLightAccess extends IBlockAccess {
   float a(EnumDirection var1, boolean var2);

   LightEngine l_();

   int a(BlockPosition var1, ColorResolver var2);

   default int a(EnumSkyBlock var0, BlockPosition var1) {
      return this.l_().a(var0).b(var1);
   }

   default int b(BlockPosition var0, int var1) {
      return this.l_().b(var0, var1);
   }

   default boolean g(BlockPosition var0) {
      return this.a(EnumSkyBlock.a, var0) >= this.L();
   }
}
