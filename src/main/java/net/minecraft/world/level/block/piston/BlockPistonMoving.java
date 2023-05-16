package net.minecraft.world.level.block.piston;

import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BlockTileEntity;
import net.minecraft.world.level.block.EnumBlockMirror;
import net.minecraft.world.level.block.EnumBlockRotation;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.entity.TileEntityTypes;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockPropertyPistonType;
import net.minecraft.world.level.block.state.properties.BlockStateDirection;
import net.minecraft.world.level.block.state.properties.BlockStateEnum;
import net.minecraft.world.level.pathfinder.PathMode;
import net.minecraft.world.level.storage.loot.LootTableInfo;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameters;
import net.minecraft.world.phys.MovingObjectPositionBlock;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;
import net.minecraft.world.phys.shapes.VoxelShapes;

public class BlockPistonMoving extends BlockTileEntity {
   public static final BlockStateDirection a = BlockPistonExtension.a;
   public static final BlockStateEnum<BlockPropertyPistonType> b = BlockPistonExtension.b;

   public BlockPistonMoving(BlockBase.Info var0) {
      super(var0);
      this.k(this.D.b().a(a, EnumDirection.c).a(b, BlockPropertyPistonType.a));
   }

   @Nullable
   @Override
   public TileEntity a(BlockPosition var0, IBlockData var1) {
      return null;
   }

   public static TileEntity a(BlockPosition var0, IBlockData var1, IBlockData var2, EnumDirection var3, boolean var4, boolean var5) {
      return new TileEntityPiston(var0, var1, var2, var3, var4, var5);
   }

   @Nullable
   @Override
   public <T extends TileEntity> BlockEntityTicker<T> a(World var0, IBlockData var1, TileEntityTypes<T> var2) {
      return a(var2, TileEntityTypes.k, TileEntityPiston::a);
   }

   @Override
   public void a(IBlockData var0, World var1, BlockPosition var2, IBlockData var3, boolean var4) {
      if (!var0.a(var3.b())) {
         TileEntity var5 = var1.c_(var2);
         if (var5 instanceof TileEntityPiston) {
            ((TileEntityPiston)var5).j();
         }
      }
   }

   @Override
   public void a(GeneratorAccess var0, BlockPosition var1, IBlockData var2) {
      BlockPosition var3 = var1.a(var2.c(a).g());
      IBlockData var4 = var0.a_(var3);
      if (var4.b() instanceof BlockPiston && var4.c(BlockPiston.b)) {
         var0.a(var3, false);
      }
   }

   @Override
   public EnumInteractionResult a(IBlockData var0, World var1, BlockPosition var2, EntityHuman var3, EnumHand var4, MovingObjectPositionBlock var5) {
      if (!var1.B && var1.c_(var2) == null) {
         var1.a(var2, false);
         return EnumInteractionResult.b;
      } else {
         return EnumInteractionResult.d;
      }
   }

   @Override
   public List<ItemStack> a(IBlockData var0, LootTableInfo.Builder var1) {
      TileEntityPiston var2 = this.a(var1.a(), BlockPosition.a(var1.a(LootContextParameters.f)));
      return var2 == null ? Collections.emptyList() : var2.i().a(var1);
   }

   @Override
   public VoxelShape a(IBlockData var0, IBlockAccess var1, BlockPosition var2, VoxelShapeCollision var3) {
      return VoxelShapes.a();
   }

   @Override
   public VoxelShape c(IBlockData var0, IBlockAccess var1, BlockPosition var2, VoxelShapeCollision var3) {
      TileEntityPiston var4 = this.a(var1, var2);
      return var4 != null ? var4.a(var1, var2) : VoxelShapes.a();
   }

   @Nullable
   private TileEntityPiston a(IBlockAccess var0, BlockPosition var1) {
      TileEntity var2 = var0.c_(var1);
      return var2 instanceof TileEntityPiston ? (TileEntityPiston)var2 : null;
   }

   @Override
   public ItemStack a(IBlockAccess var0, BlockPosition var1, IBlockData var2) {
      return ItemStack.b;
   }

   @Override
   public IBlockData a(IBlockData var0, EnumBlockRotation var1) {
      return var0.a(a, var1.a(var0.c(a)));
   }

   @Override
   public IBlockData a(IBlockData var0, EnumBlockMirror var1) {
      return var0.a(var1.a(var0.c(a)));
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> var0) {
      var0.a(a, b);
   }

   @Override
   public boolean a(IBlockData var0, IBlockAccess var1, BlockPosition var2, PathMode var3) {
      return false;
   }
}
