package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.server.level.WorldServer;
import net.minecraft.tags.TagsBlock;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockActionContext;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockPropertyDoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.block.state.properties.BlockStateDirection;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidTypes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;

public class SmallDripleafBlock extends BlockTallPlant implements IBlockFragilePlantElement, IBlockWaterlogged {
   private static final BlockStateBoolean e = BlockProperties.C;
   public static final BlockStateDirection b = BlockProperties.R;
   protected static final float c = 6.0F;
   protected static final VoxelShape d = Block.a(2.0, 0.0, 2.0, 14.0, 13.0, 14.0);

   public SmallDripleafBlock(BlockBase.Info var0) {
      super(var0);
      this.k(this.D.b().a(a, BlockPropertyDoubleBlockHalf.b).a(e, Boolean.valueOf(false)).a(b, EnumDirection.c));
   }

   @Override
   public VoxelShape a(IBlockData var0, IBlockAccess var1, BlockPosition var2, VoxelShapeCollision var3) {
      return d;
   }

   @Override
   protected boolean d(IBlockData var0, IBlockAccess var1, BlockPosition var2) {
      return var0.a(TagsBlock.bs) || var1.b_(var2.c()).a(FluidTypes.c) && super.d(var0, var1, var2);
   }

   @Nullable
   @Override
   public IBlockData a(BlockActionContext var0) {
      IBlockData var1 = super.a(var0);
      return var1 != null ? a(var0.q(), var0.a(), var1.a(b, var0.g().g())) : null;
   }

   @Override
   public void a(World var0, BlockPosition var1, IBlockData var2, EntityLiving var3, ItemStack var4) {
      if (!var0.k_()) {
         BlockPosition var5 = var1.c();
         IBlockData var6 = BlockTallPlant.a(var0, var5, this.o().a(a, BlockPropertyDoubleBlockHalf.a).a(b, var2.c(b)));
         var0.a(var5, var6, 3);
      }
   }

   @Override
   public Fluid c_(IBlockData var0) {
      return var0.c(e) ? FluidTypes.c.a(false) : super.c_(var0);
   }

   @Override
   public boolean a(IBlockData var0, IWorldReader var1, BlockPosition var2) {
      if (var0.c(a) == BlockPropertyDoubleBlockHalf.a) {
         return super.a(var0, var1, var2);
      } else {
         BlockPosition var3 = var2.d();
         IBlockData var4 = var1.a_(var3);
         return this.d(var4, var1, var3);
      }
   }

   @Override
   public IBlockData a(IBlockData var0, EnumDirection var1, IBlockData var2, GeneratorAccess var3, BlockPosition var4, BlockPosition var5) {
      if (var0.c(e)) {
         var3.a(var4, FluidTypes.c, FluidTypes.c.a(var3));
      }

      return super.a(var0, var1, var2, var3, var4, var5);
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> var0) {
      var0.a(a, e, b);
   }

   @Override
   public boolean a(IWorldReader var0, BlockPosition var1, IBlockData var2, boolean var3) {
      return true;
   }

   @Override
   public boolean a(World var0, RandomSource var1, BlockPosition var2, IBlockData var3) {
      return true;
   }

   @Override
   public void a(WorldServer var0, RandomSource var1, BlockPosition var2, IBlockData var3) {
      if (var3.c(BlockTallPlant.a) == BlockPropertyDoubleBlockHalf.b) {
         BlockPosition var4 = var2.c();
         var0.a(var4, var0.b_(var4).g(), 18);
         BigDripleafBlock.a(var0, var1, var2, var3.c(b));
      } else {
         BlockPosition var4 = var2.d();
         this.a(var0, var1, var4, var0.a_(var4));
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
   public float ap_() {
      return 0.1F;
   }
}
