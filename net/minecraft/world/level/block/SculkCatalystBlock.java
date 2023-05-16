package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.particles.Particles;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.SculkCatalystBlockEntity;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.entity.TileEntityTypes;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.gameevent.GameEventListener;

public class SculkCatalystBlock extends BlockTileEntity {
   public static final int a = 8;
   public static final BlockStateBoolean b = BlockProperties.E;
   private final IntProvider c = ConstantInt.a(5);

   public SculkCatalystBlock(BlockBase.Info blockbase_info) {
      super(blockbase_info);
      this.k(this.D.b().a(b, Boolean.valueOf(false)));
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> blockstatelist_a) {
      blockstatelist_a.a(b);
   }

   @Override
   public void a(IBlockData iblockdata, WorldServer worldserver, BlockPosition blockposition, RandomSource randomsource) {
      if (iblockdata.c(b)) {
         worldserver.a(blockposition, iblockdata.a(b, Boolean.valueOf(false)), 3);
      }
   }

   public static void a(WorldServer worldserver, BlockPosition blockposition, IBlockData iblockdata, RandomSource randomsource) {
      worldserver.a(blockposition, iblockdata.a(b, Boolean.valueOf(true)), 3);
      worldserver.a(blockposition, iblockdata.b(), 8);
      worldserver.a(Particles.G, (double)blockposition.u() + 0.5, (double)blockposition.v() + 1.15, (double)blockposition.w() + 0.5, 2, 0.2, 0.0, 0.2, 0.0);
      worldserver.a(null, blockposition, SoundEffects.tX, SoundCategory.e, 2.0F, 0.6F + randomsource.i() * 0.4F);
   }

   @Nullable
   @Override
   public TileEntity a(BlockPosition blockposition, IBlockData iblockdata) {
      return new SculkCatalystBlockEntity(blockposition, iblockdata);
   }

   @Nullable
   @Override
   public <T extends TileEntity> GameEventListener a(WorldServer worldserver, T t0) {
      return t0 instanceof SculkCatalystBlockEntity ? (SculkCatalystBlockEntity)t0 : null;
   }

   @Nullable
   @Override
   public <T extends TileEntity> BlockEntityTicker<T> a(World world, IBlockData iblockdata, TileEntityTypes<T> tileentitytypes) {
      return world.B ? null : a(tileentitytypes, TileEntityTypes.J, SculkCatalystBlockEntity::a);
   }

   @Override
   public EnumRenderType b_(IBlockData iblockdata) {
      return EnumRenderType.c;
   }

   @Override
   public void a(IBlockData iblockdata, WorldServer worldserver, BlockPosition blockposition, ItemStack itemstack, boolean flag) {
      super.a(iblockdata, worldserver, blockposition, itemstack, flag);
   }

   @Override
   public int getExpDrop(IBlockData iblockdata, WorldServer worldserver, BlockPosition blockposition, ItemStack itemstack, boolean flag) {
      return flag ? this.tryDropExperience(worldserver, blockposition, itemstack, this.c) : 0;
   }
}
