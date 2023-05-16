package net.minecraft.world.level.block;

import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.world.InventoryUtils;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.context.BlockActionContext;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.entity.DecoratedPotBlockEntity;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.block.state.properties.BlockStateDirection;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidTypes;
import net.minecraft.world.level.pathfinder.PathMode;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;
import org.jetbrains.annotations.Nullable;

public class DecoratedPotBlock extends BlockTileEntity {
   private static final VoxelShape a = Block.a(1.0, 0.0, 1.0, 15.0, 16.0, 15.0);
   private static final BlockStateDirection b = BlockProperties.R;
   private static final BlockStateBoolean c = BlockProperties.C;

   protected DecoratedPotBlock(BlockBase.Info var0) {
      super(var0);
      this.k(this.D.b().a(b, EnumDirection.c).a(c, Boolean.valueOf(false)));
   }

   @Override
   public IBlockData a(IBlockData var0, EnumDirection var1, IBlockData var2, GeneratorAccess var3, BlockPosition var4, BlockPosition var5) {
      if (var0.c(c)) {
         var3.a(var4, FluidTypes.c, FluidTypes.c.a(var3));
      }

      return super.a(var0, var1, var2, var3, var4, var5);
   }

   @Override
   public IBlockData a(BlockActionContext var0) {
      Fluid var1 = var0.q().b_(var0.a());
      return this.o().a(b, var0.g()).a(c, Boolean.valueOf(var1.a() == FluidTypes.c));
   }

   @Override
   public boolean a(IBlockData var0, IBlockAccess var1, BlockPosition var2, PathMode var3) {
      return false;
   }

   @Override
   public VoxelShape a(IBlockData var0, IBlockAccess var1, BlockPosition var2, VoxelShapeCollision var3) {
      return a;
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> var0) {
      var0.a(b, c);
   }

   @Nullable
   @Override
   public TileEntity a(BlockPosition var0, IBlockData var1) {
      return new DecoratedPotBlockEntity(var0, var1);
   }

   @Override
   public void a(World var0, BlockPosition var1, IBlockData var2, EntityHuman var3) {
      if (!var0.B) {
         TileEntity var6 = var0.c_(var1);
         if (var6 instanceof DecoratedPotBlockEntity var4) {
            var4.a(var0, var1, var3.eK(), var3);
         }
      }

      super.a(var0, var1, var2, var3);
   }

   @Override
   public void a(IBlockData var0, World var1, BlockPosition var2, IBlockData var3, boolean var4) {
      if (!var1.B) {
         TileEntity var5 = var1.c_(var2);
         if (var5 instanceof DecoratedPotBlockEntity var6 && !var6.g()) {
            InventoryUtils.a(var1, (double)var2.u(), (double)var2.v(), (double)var2.w(), var6.d());
            var1.a(null, var2, SoundEffects.fn, SoundCategory.h, 1.0F, 1.0F);
         }
      }

      super.a(var0, var1, var2, var3, var4);
   }

   @Override
   public Fluid c_(IBlockData var0) {
      return var0.c(c) ? FluidTypes.c.a(false) : super.c_(var0);
   }
}
