package net.minecraft.world.level.block;

import net.minecraft.core.BlockPosition;
import net.minecraft.core.particles.Particles;
import net.minecraft.util.RandomSource;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectList;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.phys.Vec3D;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;
import org.bukkit.event.entity.EntityPotionEffectEvent.Cause;

public class BlockWitherRose extends BlockFlowers {
   public BlockWitherRose(MobEffectList mobeffectlist, BlockBase.Info blockbase_info) {
      super(mobeffectlist, 8, blockbase_info);
   }

   @Override
   protected boolean d(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition) {
      return super.d(iblockdata, iblockaccess, blockposition) || iblockdata.a(Blocks.dV) || iblockdata.a(Blocks.dW) || iblockdata.a(Blocks.dX);
   }

   @Override
   public void a(IBlockData iblockdata, World world, BlockPosition blockposition, RandomSource randomsource) {
      VoxelShape voxelshape = this.a(iblockdata, world, blockposition, VoxelShapeCollision.a());
      Vec3D vec3d = voxelshape.a().f();
      double d0 = (double)blockposition.u() + vec3d.c;
      double d1 = (double)blockposition.w() + vec3d.e;

      for(int i = 0; i < 3; ++i) {
         if (randomsource.h()) {
            world.a(
               Particles.ab, d0 + randomsource.j() / 5.0, (double)blockposition.v() + (0.5 - randomsource.j()), d1 + randomsource.j() / 5.0, 0.0, 0.0, 0.0
            );
         }
      }
   }

   @Override
   public void a(IBlockData iblockdata, World world, BlockPosition blockposition, Entity entity) {
      if (!world.B && world.ah() != EnumDifficulty.a && entity instanceof EntityLiving entityliving && !entityliving.b(world.af().p())) {
         entityliving.addEffect(new MobEffect(MobEffects.t, 40), Cause.WITHER_ROSE);
      }
   }
}
