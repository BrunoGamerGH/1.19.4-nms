package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.particles.Particles;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.tags.TagsBlock;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.projectile.IProjectile;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.MovingObjectPositionBlock;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;

public abstract class AbstractCandleBlock extends Block {
   public static final int a = 3;
   public static final BlockStateBoolean b = BlockProperties.r;

   protected AbstractCandleBlock(BlockBase.Info blockbase_info) {
      super(blockbase_info);
   }

   protected abstract Iterable<Vec3D> a(IBlockData var1);

   public static boolean b(IBlockData iblockdata) {
      return iblockdata.b(b) && (iblockdata.a(TagsBlock.ad) || iblockdata.a(TagsBlock.bh)) && iblockdata.c(b);
   }

   @Override
   public void a(World world, IBlockData iblockdata, MovingObjectPositionBlock movingobjectpositionblock, IProjectile iprojectile) {
      if (!world.B && iprojectile.bK() && this.c(iblockdata)) {
         if (CraftEventFactory.callBlockIgniteEvent(world, movingobjectpositionblock.a(), iprojectile).isCancelled()) {
            return;
         }

         a(world, iblockdata, movingobjectpositionblock.a(), true);
      }
   }

   protected boolean c(IBlockData iblockdata) {
      return !iblockdata.c(b);
   }

   @Override
   public void a(IBlockData iblockdata, World world, BlockPosition blockposition, RandomSource randomsource) {
      if (iblockdata.c(b)) {
         this.a(iblockdata).forEach(vec3d -> a(world, vec3d.b((double)blockposition.u(), (double)blockposition.v(), (double)blockposition.w()), randomsource));
      }
   }

   private static void a(World world, Vec3D vec3d, RandomSource randomsource) {
      float f = randomsource.i();
      if (f < 0.3F) {
         world.a(Particles.ab, vec3d.c, vec3d.d, vec3d.e, 0.0, 0.0, 0.0);
         if (f < 0.17F) {
            world.a(
               vec3d.c + 0.5, vec3d.d + 0.5, vec3d.e + 0.5, SoundEffects.cY, SoundCategory.e, 1.0F + randomsource.i(), randomsource.i() * 0.7F + 0.3F, false
            );
         }
      }

      world.a(Particles.aF, vec3d.c, vec3d.d, vec3d.e, 0.0, 0.0, 0.0);
   }

   public static void a(@Nullable EntityHuman entityhuman, IBlockData iblockdata, GeneratorAccess generatoraccess, BlockPosition blockposition) {
      a(generatoraccess, iblockdata, blockposition, false);
      if (iblockdata.b() instanceof AbstractCandleBlock) {
         ((AbstractCandleBlock)iblockdata.b())
            .a(iblockdata)
            .forEach(
               vec3d -> generatoraccess.a(
                     Particles.ab,
                     (double)blockposition.u() + vec3d.a(),
                     (double)blockposition.v() + vec3d.b(),
                     (double)blockposition.w() + vec3d.c(),
                     0.0,
                     0.1F,
                     0.0
                  )
            );
      }

      generatoraccess.a(null, blockposition, SoundEffects.da, SoundCategory.e, 1.0F, 1.0F);
      generatoraccess.a(entityhuman, GameEvent.c, blockposition);
   }

   private static void a(GeneratorAccess generatoraccess, IBlockData iblockdata, BlockPosition blockposition, boolean flag) {
      generatoraccess.a(blockposition, iblockdata.a(b, Boolean.valueOf(flag)), 11);
   }
}
