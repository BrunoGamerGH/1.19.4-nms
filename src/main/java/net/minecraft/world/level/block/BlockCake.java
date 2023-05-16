package net.minecraft.world.level.block;

import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.stats.StatisticList;
import net.minecraft.tags.TagsItem;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockStateInteger;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathMode;
import net.minecraft.world.phys.MovingObjectPositionBlock;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class BlockCake extends Block {
   public static final int a = 6;
   public static final BlockStateInteger b = BlockProperties.ay;
   public static final int c = b(0);
   protected static final float d = 1.0F;
   protected static final float e = 2.0F;
   protected static final VoxelShape[] f = new VoxelShape[]{
      Block.a(1.0, 0.0, 1.0, 15.0, 8.0, 15.0),
      Block.a(3.0, 0.0, 1.0, 15.0, 8.0, 15.0),
      Block.a(5.0, 0.0, 1.0, 15.0, 8.0, 15.0),
      Block.a(7.0, 0.0, 1.0, 15.0, 8.0, 15.0),
      Block.a(9.0, 0.0, 1.0, 15.0, 8.0, 15.0),
      Block.a(11.0, 0.0, 1.0, 15.0, 8.0, 15.0),
      Block.a(13.0, 0.0, 1.0, 15.0, 8.0, 15.0)
   };

   protected BlockCake(BlockBase.Info blockbase_info) {
      super(blockbase_info);
      this.k(this.D.b().a(b, Integer.valueOf(0)));
   }

   @Override
   public VoxelShape a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, VoxelShapeCollision voxelshapecollision) {
      return f[iblockdata.c(b)];
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
      Item item = itemstack.c();
      if (itemstack.a(TagsItem.ah) && iblockdata.c(b) == 0) {
         Block block = Block.a(item);
         if (block instanceof CandleBlock) {
            if (!entityhuman.f()) {
               itemstack.h(1);
            }

            world.a(null, blockposition, SoundEffects.cG, SoundCategory.e, 1.0F, 1.0F);
            world.b(blockposition, CandleCakeBlock.a(block));
            world.a(entityhuman, GameEvent.c, blockposition);
            entityhuman.b(StatisticList.c.b(item));
            return EnumInteractionResult.a;
         }
      }

      if (world.B) {
         if (a(world, blockposition, iblockdata, entityhuman).a()) {
            return EnumInteractionResult.a;
         }

         if (itemstack.b()) {
            return EnumInteractionResult.b;
         }
      }

      return a(world, blockposition, iblockdata, entityhuman);
   }

   protected static EnumInteractionResult a(GeneratorAccess generatoraccess, BlockPosition blockposition, IBlockData iblockdata, EntityHuman entityhuman) {
      if (!entityhuman.t(false)) {
         return EnumInteractionResult.d;
      } else {
         entityhuman.a(StatisticList.U);
         int oldFoodLevel = entityhuman.fT().a;
         FoodLevelChangeEvent event = CraftEventFactory.callFoodLevelChangeEvent(entityhuman, 2 + oldFoodLevel);
         if (!event.isCancelled()) {
            entityhuman.fT().a(event.getFoodLevel() - oldFoodLevel, 0.1F);
         }

         ((EntityPlayer)entityhuman).getBukkitEntity().sendHealthUpdate();
         int i = iblockdata.c(b);
         generatoraccess.a(entityhuman, GameEvent.n, blockposition);
         if (i < 6) {
            generatoraccess.a(blockposition, iblockdata.a(b, Integer.valueOf(i + 1)), 3);
         } else {
            generatoraccess.a(blockposition, false);
            generatoraccess.a(entityhuman, GameEvent.f, blockposition);
         }

         return EnumInteractionResult.a;
      }
   }

   @Override
   public IBlockData a(
      IBlockData iblockdata,
      EnumDirection enumdirection,
      IBlockData iblockdata1,
      GeneratorAccess generatoraccess,
      BlockPosition blockposition,
      BlockPosition blockposition1
   ) {
      return enumdirection == EnumDirection.a && !iblockdata.a(generatoraccess, blockposition)
         ? Blocks.a.o()
         : super.a(iblockdata, enumdirection, iblockdata1, generatoraccess, blockposition, blockposition1);
   }

   @Override
   public boolean a(IBlockData iblockdata, IWorldReader iworldreader, BlockPosition blockposition) {
      return iworldreader.a_(blockposition.d()).d().b();
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> blockstatelist_a) {
      blockstatelist_a.a(b);
   }

   @Override
   public int a(IBlockData iblockdata, World world, BlockPosition blockposition) {
      return b(iblockdata.c(b));
   }

   public static int b(int i) {
      return (7 - i) * 2;
   }

   @Override
   public boolean d_(IBlockData iblockdata) {
      return true;
   }

   @Override
   public boolean a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, PathMode pathmode) {
      return false;
   }
}
