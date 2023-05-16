package net.minecraft.world.level;

import java.util.Optional;
import net.minecraft.core.BlockPosition;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.material.Fluid;

public class ExplosionDamageCalculator {
   public Optional<Float> a(Explosion var0, IBlockAccess var1, BlockPosition var2, IBlockData var3, Fluid var4) {
      return var3.h() && var4.c() ? Optional.empty() : Optional.of(Math.max(var3.b().e(), var4.i()));
   }

   public boolean a(Explosion var0, IBlockAccess var1, BlockPosition var2, IBlockData var3, float var4) {
      return true;
   }
}
