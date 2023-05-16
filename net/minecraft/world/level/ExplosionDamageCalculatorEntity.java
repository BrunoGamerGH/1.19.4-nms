package net.minecraft.world.level;

import java.util.Optional;
import net.minecraft.core.BlockPosition;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.material.Fluid;

public class ExplosionDamageCalculatorEntity extends ExplosionDamageCalculator {
   private final Entity a;

   public ExplosionDamageCalculatorEntity(Entity var0) {
      this.a = var0;
   }

   @Override
   public Optional<Float> a(Explosion var0, IBlockAccess var1, BlockPosition var2, IBlockData var3, Fluid var4) {
      return super.a(var0, var1, var2, var3, var4).map(var5x -> this.a.a(var0, var1, var2, var3, var4, var5x));
   }

   @Override
   public boolean a(Explosion var0, IBlockAccess var1, BlockPosition var2, IBlockData var3, float var4) {
      return this.a.a(var0, var1, var2, var3, var4);
   }
}
