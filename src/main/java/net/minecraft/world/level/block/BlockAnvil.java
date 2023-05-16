package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.stats.StatisticList;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.ITileInventory;
import net.minecraft.world.TileInventory;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.EntityFallingBlock;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.inventory.ContainerAccess;
import net.minecraft.world.inventory.ContainerAnvil;
import net.minecraft.world.item.context.BlockActionContext;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockStateDirection;
import net.minecraft.world.level.pathfinder.PathMode;
import net.minecraft.world.phys.MovingObjectPositionBlock;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;
import net.minecraft.world.phys.shapes.VoxelShapes;

public class BlockAnvil extends BlockFalling {
   public static final BlockStateDirection a = BlockFacingHorizontal.aD;
   private static final VoxelShape b = Block.a(2.0, 0.0, 2.0, 14.0, 4.0, 14.0);
   private static final VoxelShape c = Block.a(3.0, 4.0, 4.0, 13.0, 5.0, 12.0);
   private static final VoxelShape d = Block.a(4.0, 5.0, 6.0, 12.0, 10.0, 10.0);
   private static final VoxelShape e = Block.a(0.0, 10.0, 3.0, 16.0, 16.0, 13.0);
   private static final VoxelShape f = Block.a(4.0, 4.0, 3.0, 12.0, 5.0, 13.0);
   private static final VoxelShape g = Block.a(6.0, 5.0, 4.0, 10.0, 10.0, 12.0);
   private static final VoxelShape h = Block.a(3.0, 10.0, 0.0, 13.0, 16.0, 16.0);
   private static final VoxelShape i = VoxelShapes.a(b, c, d, e);
   private static final VoxelShape j = VoxelShapes.a(b, f, g, h);
   private static final IChatBaseComponent k = IChatBaseComponent.c("container.repair");
   private static final float l = 2.0F;
   private static final int m = 40;

   public BlockAnvil(BlockBase.Info var0) {
      super(var0);
      this.k(this.D.b().a(a, EnumDirection.c));
   }

   @Override
   public IBlockData a(BlockActionContext var0) {
      return this.o().a(a, var0.g().h());
   }

   @Override
   public EnumInteractionResult a(IBlockData var0, World var1, BlockPosition var2, EntityHuman var3, EnumHand var4, MovingObjectPositionBlock var5) {
      if (var1.B) {
         return EnumInteractionResult.a;
      } else {
         var3.a(var0.b(var1, var2));
         var3.a(StatisticList.aC);
         return EnumInteractionResult.b;
      }
   }

   @Nullable
   @Override
   public ITileInventory b(IBlockData var0, World var1, BlockPosition var2) {
      return new TileInventory((var2x, var3x, var4) -> new ContainerAnvil(var2x, var3x, ContainerAccess.a(var1, var2)), k);
   }

   @Override
   public VoxelShape a(IBlockData var0, IBlockAccess var1, BlockPosition var2, VoxelShapeCollision var3) {
      EnumDirection var4 = var0.c(a);
      return var4.o() == EnumDirection.EnumAxis.a ? i : j;
   }

   @Override
   protected void a(EntityFallingBlock var0) {
      var0.b(2.0F, 40);
   }

   @Override
   public void a(World var0, BlockPosition var1, IBlockData var2, IBlockData var3, EntityFallingBlock var4) {
      if (!var4.aO()) {
         var0.c(1031, var1, 0);
      }
   }

   @Override
   public void a(World var0, BlockPosition var1, EntityFallingBlock var2) {
      if (!var2.aO()) {
         var0.c(1029, var1, 0);
      }
   }

   @Override
   public DamageSource a(Entity var0) {
      return var0.dG().b(var0);
   }

   @Nullable
   public static IBlockData e(IBlockData var0) {
      if (var0.a(Blocks.gR)) {
         return Blocks.gS.o().a(a, var0.c(a));
      } else {
         return var0.a(Blocks.gS) ? Blocks.gT.o().a(a, var0.c(a)) : null;
      }
   }

   @Override
   public IBlockData a(IBlockData var0, EnumBlockRotation var1) {
      return var0.a(a, var1.a(var0.c(a)));
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> var0) {
      var0.a(a);
   }

   @Override
   public boolean a(IBlockData var0, IBlockAccess var1, BlockPosition var2, PathMode var3) {
      return false;
   }

   @Override
   public int d(IBlockData var0, IBlockAccess var1, BlockPosition var2) {
      return var0.d(var1, var2).ak;
   }
}
