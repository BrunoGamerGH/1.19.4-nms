package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.particles.Particles;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.server.level.WorldServer;
import net.minecraft.stats.StatisticList;
import net.minecraft.util.RandomSource;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.TileInventory;
import net.minecraft.world.entity.monster.piglin.PiglinAI;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.inventory.ContainerChest;
import net.minecraft.world.inventory.InventoryEnderChest;
import net.minecraft.world.item.context.BlockActionContext;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.entity.TileEntityChest;
import net.minecraft.world.level.block.entity.TileEntityEnderChest;
import net.minecraft.world.level.block.entity.TileEntityTypes;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.block.state.properties.BlockStateDirection;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidTypes;
import net.minecraft.world.level.pathfinder.PathMode;
import net.minecraft.world.phys.MovingObjectPositionBlock;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;

public class BlockEnderChest extends BlockChestAbstract<TileEntityEnderChest> implements IBlockWaterlogged {
   public static final BlockStateDirection b = BlockFacingHorizontal.aD;
   public static final BlockStateBoolean c = BlockProperties.C;
   protected static final VoxelShape d = Block.a(1.0, 0.0, 1.0, 15.0, 14.0, 15.0);
   private static final IChatBaseComponent e = IChatBaseComponent.c("container.enderchest");

   protected BlockEnderChest(BlockBase.Info var0) {
      super(var0, () -> TileEntityTypes.d);
      this.k(this.D.b().a(b, EnumDirection.c).a(c, Boolean.valueOf(false)));
   }

   @Override
   public DoubleBlockFinder.Result<? extends TileEntityChest> a(IBlockData var0, World var1, BlockPosition var2, boolean var3) {
      return DoubleBlockFinder.Combiner::b;
   }

   @Override
   public VoxelShape a(IBlockData var0, IBlockAccess var1, BlockPosition var2, VoxelShapeCollision var3) {
      return d;
   }

   @Override
   public EnumRenderType b_(IBlockData var0) {
      return EnumRenderType.b;
   }

   @Override
   public IBlockData a(BlockActionContext var0) {
      Fluid var1 = var0.q().b_(var0.a());
      return this.o().a(b, var0.g().g()).a(c, Boolean.valueOf(var1.a() == FluidTypes.c));
   }

   @Override
   public EnumInteractionResult a(IBlockData var0, World var1, BlockPosition var2, EntityHuman var3, EnumHand var4, MovingObjectPositionBlock var5) {
      InventoryEnderChest var6 = var3.fW();
      TileEntity var7 = var1.c_(var2);
      if (var6 != null && var7 instanceof TileEntityEnderChest) {
         BlockPosition var8 = var2.c();
         if (var1.a_(var8).g(var1, var8)) {
            return EnumInteractionResult.a(var1.B);
         } else if (var1.B) {
            return EnumInteractionResult.a;
         } else {
            TileEntityEnderChest var9 = (TileEntityEnderChest)var7;
            var6.a(var9);
            var3.a(new TileInventory((var1x, var2x, var3x) -> ContainerChest.a(var1x, var2x, var6), e));
            var3.a(StatisticList.aj);
            PiglinAI.a(var3, true);
            return EnumInteractionResult.b;
         }
      } else {
         return EnumInteractionResult.a(var1.B);
      }
   }

   @Override
   public TileEntity a(BlockPosition var0, IBlockData var1) {
      return new TileEntityEnderChest(var0, var1);
   }

   @Nullable
   @Override
   public <T extends TileEntity> BlockEntityTicker<T> a(World var0, IBlockData var1, TileEntityTypes<T> var2) {
      return var0.B ? a(var2, TileEntityTypes.d, TileEntityEnderChest::a) : null;
   }

   @Override
   public void a(IBlockData var0, World var1, BlockPosition var2, RandomSource var3) {
      for(int var4 = 0; var4 < 3; ++var4) {
         int var5 = var3.a(2) * 2 - 1;
         int var6 = var3.a(2) * 2 - 1;
         double var7 = (double)var2.u() + 0.5 + 0.25 * (double)var5;
         double var9 = (double)((float)var2.v() + var3.i());
         double var11 = (double)var2.w() + 0.5 + 0.25 * (double)var6;
         double var13 = (double)(var3.i() * (float)var5);
         double var15 = ((double)var3.i() - 0.5) * 0.125;
         double var17 = (double)(var3.i() * (float)var6);
         var1.a(Particles.Z, var7, var9, var11, var13, var15, var17);
      }
   }

   @Override
   public IBlockData a(IBlockData var0, EnumBlockRotation var1) {
      return var0.a(b, var1.a(var0.c(b)));
   }

   @Override
   public IBlockData a(IBlockData var0, EnumBlockMirror var1) {
      return var0.a(var1.a(var0.c(b)));
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> var0) {
      var0.a(b, c);
   }

   @Override
   public Fluid c_(IBlockData var0) {
      return var0.c(c) ? FluidTypes.c.a(false) : super.c_(var0);
   }

   @Override
   public IBlockData a(IBlockData var0, EnumDirection var1, IBlockData var2, GeneratorAccess var3, BlockPosition var4, BlockPosition var5) {
      if (var0.c(c)) {
         var3.a(var4, FluidTypes.c, FluidTypes.c.a(var3));
      }

      return super.a(var0, var1, var2, var3, var4, var5);
   }

   @Override
   public boolean a(IBlockData var0, IBlockAccess var1, BlockPosition var2, PathMode var3) {
      return false;
   }

   @Override
   public void a(IBlockData var0, WorldServer var1, BlockPosition var2, RandomSource var3) {
      TileEntity var4 = var1.c_(var2);
      if (var4 instanceof TileEntityEnderChest) {
         ((TileEntityEnderChest)var4).c();
      }
   }
}
