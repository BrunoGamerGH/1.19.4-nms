package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.level.EnumSkyBlock;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.entity.TileEntityLightDetector;
import net.minecraft.world.level.block.entity.TileEntityTypes;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.block.state.properties.BlockStateInteger;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.MovingObjectPositionBlock;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;

public class BlockDaylightDetector extends BlockTileEntity {
   public static final BlockStateInteger a = BlockProperties.aT;
   public static final BlockStateBoolean b = BlockProperties.p;
   protected static final VoxelShape c = Block.a(0.0, 0.0, 0.0, 16.0, 6.0, 16.0);

   public BlockDaylightDetector(BlockBase.Info blockbase_info) {
      super(blockbase_info);
      this.k(this.D.b().a(a, Integer.valueOf(0)).a(b, Boolean.valueOf(false)));
   }

   @Override
   public VoxelShape a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, VoxelShapeCollision voxelshapecollision) {
      return c;
   }

   @Override
   public boolean g_(IBlockData iblockdata) {
      return true;
   }

   @Override
   public int a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, EnumDirection enumdirection) {
      return iblockdata.c(a);
   }

   private static void d(IBlockData iblockdata, World world, BlockPosition blockposition) {
      int i = world.a(EnumSkyBlock.a, blockposition) - world.o_();
      float f = world.a(1.0F);
      boolean flag = iblockdata.c(b);
      if (flag) {
         i = 15 - i;
      } else if (i > 0) {
         float f1 = f < (float) Math.PI ? 0.0F : (float) (Math.PI * 2);
         f += (f1 - f) * 0.2F;
         i = Math.round((float)i * MathHelper.b(f));
      }

      i = MathHelper.a(i, 0, 15);
      if (iblockdata.c(a) != i) {
         i = CraftEventFactory.callRedstoneChange(world, blockposition, iblockdata.c(a), i).getNewCurrent();
         world.a(blockposition, iblockdata.a(a, Integer.valueOf(i)), 3);
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
      if (entityhuman.fV()) {
         if (world.B) {
            return EnumInteractionResult.a;
         } else {
            IBlockData iblockdata1 = iblockdata.a(b);
            world.a(blockposition, iblockdata1, 4);
            world.a(GameEvent.c, blockposition, GameEvent.a.a(entityhuman, iblockdata1));
            d(iblockdata1, world, blockposition);
            return EnumInteractionResult.b;
         }
      } else {
         return super.a(iblockdata, world, blockposition, entityhuman, enumhand, movingobjectpositionblock);
      }
   }

   @Override
   public EnumRenderType b_(IBlockData iblockdata) {
      return EnumRenderType.c;
   }

   @Override
   public boolean f_(IBlockData iblockdata) {
      return true;
   }

   @Override
   public TileEntity a(BlockPosition blockposition, IBlockData iblockdata) {
      return new TileEntityLightDetector(blockposition, iblockdata);
   }

   @Nullable
   @Override
   public <T extends TileEntity> BlockEntityTicker<T> a(World world, IBlockData iblockdata, TileEntityTypes<T> tileentitytypes) {
      return !world.B && world.q_().g() ? a(tileentitytypes, TileEntityTypes.q, BlockDaylightDetector::a) : null;
   }

   private static void a(World world, BlockPosition blockposition, IBlockData iblockdata, TileEntityLightDetector tileentitylightdetector) {
      if (world.U() % 20L == 0L) {
         d(iblockdata, world, blockposition);
      }
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> blockstatelist_a) {
      blockstatelist_a.a(a, b);
   }
}
