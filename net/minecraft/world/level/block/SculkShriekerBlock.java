package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockActionContext;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.SculkShriekerBlockEntity;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.entity.TileEntityTypes;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidTypes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.bukkit.event.block.Action;

public class SculkShriekerBlock extends BlockTileEntity implements IBlockWaterlogged {
   public static final BlockStateBoolean a = BlockProperties.F;
   public static final BlockStateBoolean b = BlockProperties.C;
   public static final BlockStateBoolean c = BlockProperties.G;
   protected static final VoxelShape d = Block.a(0.0, 0.0, 0.0, 16.0, 8.0, 16.0);
   public static final double e = d.c(EnumDirection.EnumAxis.b);

   public SculkShriekerBlock(BlockBase.Info blockbase_info) {
      super(blockbase_info);
      this.k(this.D.b().a(a, Boolean.valueOf(false)).a(b, Boolean.valueOf(false)).a(c, Boolean.valueOf(false)));
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> blockstatelist_a) {
      blockstatelist_a.a(a);
      blockstatelist_a.a(b);
      blockstatelist_a.a(c);
   }

   @Override
   public void a(World world, BlockPosition blockposition, IBlockData iblockdata, Entity entity) {
      if (world instanceof WorldServer worldserver) {
         EntityPlayer entityplayer = SculkShriekerBlockEntity.a(entity);
         if (entityplayer != null) {
            if (CraftEventFactory.callPlayerInteractEvent(entityplayer, Action.PHYSICAL, blockposition, null, null, null).isCancelled()) {
               return;
            }

            worldserver.a(blockposition, TileEntityTypes.K).ifPresent(sculkshriekerblockentity -> sculkshriekerblockentity.a(worldserver, entityplayer));
         }
      }

      super.a(world, blockposition, iblockdata, entity);
   }

   @Override
   public void a(IBlockData iblockdata, World world, BlockPosition blockposition, IBlockData iblockdata1, boolean flag) {
      if (world instanceof WorldServer worldserver && iblockdata.c(a) && !iblockdata.a(iblockdata1.b())) {
         worldserver.a(blockposition, TileEntityTypes.K).ifPresent(sculkshriekerblockentity -> sculkshriekerblockentity.a(worldserver));
      }

      super.a(iblockdata, world, blockposition, iblockdata1, flag);
   }

   @Override
   public void a(IBlockData iblockdata, WorldServer worldserver, BlockPosition blockposition, RandomSource randomsource) {
      if (iblockdata.c(a)) {
         worldserver.a(blockposition, iblockdata.a(a, Boolean.valueOf(false)), 3);
         worldserver.a(blockposition, TileEntityTypes.K).ifPresent(sculkshriekerblockentity -> sculkshriekerblockentity.a(worldserver));
      }
   }

   @Override
   public EnumRenderType b_(IBlockData iblockdata) {
      return EnumRenderType.c;
   }

   @Override
   public VoxelShape c(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, VoxelShapeCollision voxelshapecollision) {
      return d;
   }

   @Override
   public VoxelShape f(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition) {
      return d;
   }

   @Override
   public boolean g_(IBlockData iblockdata) {
      return true;
   }

   @Nullable
   @Override
   public TileEntity a(BlockPosition blockposition, IBlockData iblockdata) {
      return new SculkShriekerBlockEntity(blockposition, iblockdata);
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
      if (iblockdata.c(b)) {
         generatoraccess.a(blockposition, FluidTypes.c, FluidTypes.c.a(generatoraccess));
      }

      return super.a(iblockdata, enumdirection, iblockdata1, generatoraccess, blockposition, blockposition1);
   }

   @Nullable
   @Override
   public IBlockData a(BlockActionContext blockactioncontext) {
      return this.o().a(b, Boolean.valueOf(blockactioncontext.q().b_(blockactioncontext.a()).a() == FluidTypes.c));
   }

   @Override
   public Fluid c_(IBlockData iblockdata) {
      return iblockdata.c(b) ? FluidTypes.c.a(false) : super.c_(iblockdata);
   }

   @Override
   public void a(IBlockData iblockdata, WorldServer worldserver, BlockPosition blockposition, ItemStack itemstack, boolean flag) {
      super.a(iblockdata, worldserver, blockposition, itemstack, flag);
   }

   @Override
   public int getExpDrop(IBlockData iblockdata, WorldServer worldserver, BlockPosition blockposition, ItemStack itemstack, boolean flag) {
      return flag ? this.tryDropExperience(worldserver, blockposition, itemstack, ConstantInt.a(5)) : 0;
   }

   @Nullable
   @Override
   public <T extends TileEntity> GameEventListener a(WorldServer worldserver, T t0) {
      return t0 instanceof SculkShriekerBlockEntity sculkshriekerblockentity ? sculkshriekerblockentity.c() : null;
   }

   @Nullable
   @Override
   public <T extends TileEntity> BlockEntityTicker<T> a(World world, IBlockData iblockdata, TileEntityTypes<T> tileentitytypes) {
      return !world.B
         ? BlockTileEntity.a(
            tileentitytypes, TileEntityTypes.K, (world1, blockposition, iblockdata1, sculkshriekerblockentity) -> sculkshriekerblockentity.c().a(world1)
         )
         : null;
   }
}
