package net.minecraft.world.level.block;

import net.minecraft.core.BlockPosition;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.world.entity.projectile.IProjectile;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.phys.MovingObjectPositionBlock;

public class AmethystBlock extends Block {
   public AmethystBlock(BlockBase.Info var0) {
      super(var0);
   }

   @Override
   public void a(World var0, IBlockData var1, MovingObjectPositionBlock var2, IProjectile var3) {
      if (!var0.B) {
         BlockPosition var4 = var2.a();
         var0.a(null, var4, SoundEffects.G, SoundCategory.e, 1.0F, 0.5F + var0.z.i() * 1.2F);
         var0.a(null, var4, SoundEffects.E, SoundCategory.e, 1.0F, 0.5F + var0.z.i() * 1.2F);
      }
   }
}
