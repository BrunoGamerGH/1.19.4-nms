package net.minecraft.world.level.block;

import java.util.Optional;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.particles.Particles;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.tags.TagsEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EnumItemSlot;
import net.minecraft.world.entity.item.EntityFallingBlock;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.pathfinder.PathMode;
import net.minecraft.world.phys.Vec3D;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;
import net.minecraft.world.phys.shapes.VoxelShapeCollisionEntity;
import net.minecraft.world.phys.shapes.VoxelShapes;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;

public class PowderSnowBlock extends Block implements IFluidSource {
   private static final float a = 0.083333336F;
   private static final float b = 0.9F;
   private static final float c = 1.5F;
   private static final float d = 2.5F;
   private static final VoxelShape e = VoxelShapes.a(0.0, 0.0, 0.0, 1.0, 0.9F, 1.0);
   private static final double f = 4.0;
   private static final double g = 7.0;

   public PowderSnowBlock(BlockBase.Info blockbase_info) {
      super(blockbase_info);
   }

   @Override
   public boolean a(IBlockData iblockdata, IBlockData iblockdata1, EnumDirection enumdirection) {
      return iblockdata1.a(this) ? true : super.a(iblockdata, iblockdata1, enumdirection);
   }

   @Override
   public VoxelShape f(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition) {
      return VoxelShapes.a();
   }

   @Override
   public void a(IBlockData iblockdata, World world, BlockPosition blockposition, Entity entity) {
      if (!(entity instanceof EntityLiving) || entity.dh().a(this)) {
         entity.a(iblockdata, new Vec3D(0.9F, 1.5, 0.9F));
         if (world.B) {
            RandomSource randomsource = world.r_();
            boolean flag = entity.ab != entity.dl() || entity.ad != entity.dr();
            if (flag && randomsource.h()) {
               world.a(
                  Particles.aG,
                  entity.dl(),
                  (double)(blockposition.v() + 1),
                  entity.dr(),
                  (double)(MathHelper.b(randomsource, -1.0F, 1.0F) * 0.083333336F),
                  0.05F,
                  (double)(MathHelper.b(randomsource, -1.0F, 1.0F) * 0.083333336F)
               );
            }
         }
      }

      entity.o(true);
      if (!world.B) {
         if (entity.bK() && entity.a(world, blockposition)) {
            if (CraftEventFactory.callEntityChangeBlockEvent(
                  entity, blockposition, Blocks.a.o(), !world.W().b(GameRules.c) && !(entity instanceof EntityHuman)
               )
               .isCancelled()) {
               return;
            }

            world.b(blockposition, false);
         }

         entity.a_(false);
      }
   }

   @Override
   public void a(World world, IBlockData iblockdata, BlockPosition blockposition, Entity entity, float f) {
      if ((double)f >= 4.0 && entity instanceof EntityLiving entityliving) {
         EntityLiving.a entityliving_a = entityliving.ey();
         SoundEffect soundeffect = (double)f < 7.0 ? entityliving_a.a() : entityliving_a.b();
         entity.a(soundeffect, 1.0F, 1.0F);
      }
   }

   @Override
   public VoxelShape c(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, VoxelShapeCollision voxelshapecollision) {
      if (voxelshapecollision instanceof VoxelShapeCollisionEntity voxelshapecollisionentity) {
         Entity entity = voxelshapecollisionentity.c();
         if (entity != null) {
            if (entity.aa > 2.5F) {
               return e;
            }

            boolean flag = entity instanceof EntityFallingBlock;
            if (flag || a(entity) && voxelshapecollision.a(VoxelShapes.b(), blockposition, false) && !voxelshapecollision.b()) {
               return super.c(iblockdata, iblockaccess, blockposition, voxelshapecollision);
            }
         }
      }

      return VoxelShapes.a();
   }

   @Override
   public VoxelShape b(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, VoxelShapeCollision voxelshapecollision) {
      return VoxelShapes.a();
   }

   public static boolean a(Entity entity) {
      return entity.ae().a(TagsEntity.f) ? true : (entity instanceof EntityLiving ? ((EntityLiving)entity).c(EnumItemSlot.c).a(Items.oJ) : false);
   }

   @Override
   public ItemStack c(GeneratorAccess generatoraccess, BlockPosition blockposition, IBlockData iblockdata) {
      generatoraccess.a(blockposition, Blocks.a.o(), 11);
      if (!generatoraccess.k_()) {
         generatoraccess.c(2001, blockposition, Block.i(iblockdata));
      }

      return new ItemStack(Items.pJ);
   }

   @Override
   public Optional<SoundEffect> an_() {
      return Optional.of(SoundEffects.cB);
   }

   @Override
   public boolean a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, PathMode pathmode) {
      return true;
   }
}
