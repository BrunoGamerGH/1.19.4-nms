package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.stats.StatisticList;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.item.EntityTNTPrimed;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.projectile.IProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.MovingObjectPositionBlock;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;

public class BlockTNT extends Block {
   public static final BlockStateBoolean a = BlockProperties.B;

   public BlockTNT(BlockBase.Info blockbase_info) {
      super(blockbase_info);
      this.k(this.o().a(a, Boolean.valueOf(false)));
   }

   @Override
   public void b(IBlockData iblockdata, World world, BlockPosition blockposition, IBlockData iblockdata1, boolean flag) {
      if (!iblockdata1.a(iblockdata.b()) && world.r(blockposition)) {
         a(world, blockposition);
         world.a(blockposition, false);
      }
   }

   @Override
   public void a(IBlockData iblockdata, World world, BlockPosition blockposition, Block block, BlockPosition blockposition1, boolean flag) {
      if (world.r(blockposition)) {
         a(world, blockposition);
         world.a(blockposition, false);
      }
   }

   @Override
   public void a(World world, BlockPosition blockposition, IBlockData iblockdata, EntityHuman entityhuman) {
      if (!world.k_() && !entityhuman.f() && iblockdata.c(a)) {
         a(world, blockposition);
      }

      super.a(world, blockposition, iblockdata, entityhuman);
   }

   @Override
   public void a(World world, BlockPosition blockposition, Explosion explosion) {
      if (!world.B) {
         EntityTNTPrimed entitytntprimed = new EntityTNTPrimed(
            world, (double)blockposition.u() + 0.5, (double)blockposition.v(), (double)blockposition.w() + 0.5, explosion.e()
         );
         int i = entitytntprimed.j();
         entitytntprimed.b((short)(world.z.a(i / 4) + i / 8));
         world.b(entitytntprimed);
      }
   }

   public static void a(World world, BlockPosition blockposition) {
      a(world, blockposition, null);
   }

   private static void a(World world, BlockPosition blockposition, @Nullable EntityLiving entityliving) {
      if (!world.B) {
         EntityTNTPrimed entitytntprimed = new EntityTNTPrimed(
            world, (double)blockposition.u() + 0.5, (double)blockposition.v(), (double)blockposition.w() + 0.5, entityliving
         );
         world.b(entitytntprimed);
         world.a(null, entitytntprimed.dl(), entitytntprimed.dn(), entitytntprimed.dr(), SoundEffects.xm, SoundCategory.e, 1.0F, 1.0F);
         world.a(entityliving, GameEvent.M, blockposition);
      }
   }

   @Override
   public EnumInteractionResult a(
      IBlockData iblockdata,
      World world,
      BlockPosition blockposition,
      EntityHuman entityhuman,
      EnumHand enumhand,
      MovingObjectPositionBlock movingobjectpositionblock
   ) {
      ItemStack itemstack = entityhuman.b(enumhand);
      if (!itemstack.a(Items.nA) && !itemstack.a(Items.tb)) {
         return super.a(iblockdata, world, blockposition, entityhuman, enumhand, movingobjectpositionblock);
      } else {
         a(world, blockposition, entityhuman);
         world.a(blockposition, Blocks.a.o(), 11);
         Item item = itemstack.c();
         if (!entityhuman.f()) {
            if (itemstack.a(Items.nA)) {
               itemstack.a(1, entityhuman, entityhuman1 -> entityhuman1.d(enumhand));
            } else {
               itemstack.h(1);
            }
         }

         entityhuman.b(StatisticList.c.b(item));
         return EnumInteractionResult.a(world.B);
      }
   }

   @Override
   public void a(World world, IBlockData iblockdata, MovingObjectPositionBlock movingobjectpositionblock, IProjectile iprojectile) {
      if (!world.B) {
         BlockPosition blockposition = movingobjectpositionblock.a();
         Entity entity = iprojectile.v();
         if (iprojectile.bK() && iprojectile.a(world, blockposition)) {
            if (CraftEventFactory.callEntityChangeBlockEvent(iprojectile, blockposition, Blocks.a.o()).isCancelled()) {
               return;
            }

            a(world, blockposition, entity instanceof EntityLiving ? (EntityLiving)entity : null);
            world.a(blockposition, false);
         }
      }
   }

   @Override
   public boolean a(Explosion explosion) {
      return false;
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> blockstatelist_a) {
      blockstatelist_a.a(a);
   }
}
