package net.minecraft.world.level.block;

import java.util.function.ToIntFunction;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.block.state.properties.BlockStateInteger;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidTypes;
import net.minecraft.world.phys.MovingObjectPositionBlock;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;
import net.minecraft.world.phys.shapes.VoxelShapes;

public class LightBlock extends Block implements IBlockWaterlogged {
   public static final int a = 15;
   public static final BlockStateInteger b = BlockProperties.aP;
   public static final BlockStateBoolean c = BlockProperties.C;
   public static final ToIntFunction<IBlockData> d = var0 -> var0.c(b);

   public LightBlock(BlockBase.Info var0) {
      super(var0);
      this.k(this.D.b().a(b, Integer.valueOf(15)).a(c, Boolean.valueOf(false)));
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> var0) {
      var0.a(b, c);
   }

   @Override
   public EnumInteractionResult a(IBlockData var0, World var1, BlockPosition var2, EntityHuman var3, EnumHand var4, MovingObjectPositionBlock var5) {
      if (!var1.B && var3.gg()) {
         var1.a(var2, var0.a(b), 2);
         return EnumInteractionResult.a;
      } else {
         return EnumInteractionResult.b;
      }
   }

   @Override
   public VoxelShape a(IBlockData var0, IBlockAccess var1, BlockPosition var2, VoxelShapeCollision var3) {
      return var3.a(Items.he) ? VoxelShapes.b() : VoxelShapes.a();
   }

   @Override
   public boolean c(IBlockData var0, IBlockAccess var1, BlockPosition var2) {
      return true;
   }

   @Override
   public EnumRenderType b_(IBlockData var0) {
      return EnumRenderType.a;
   }

   @Override
   public float b(IBlockData var0, IBlockAccess var1, BlockPosition var2) {
      return 1.0F;
   }

   @Override
   public IBlockData a(IBlockData var0, EnumDirection var1, IBlockData var2, GeneratorAccess var3, BlockPosition var4, BlockPosition var5) {
      if (var0.c(c)) {
         var3.a(var4, FluidTypes.c, FluidTypes.c.a(var3));
      }

      return super.a(var0, var1, var2, var3, var4, var5);
   }

   @Override
   public Fluid c_(IBlockData var0) {
      return var0.c(c) ? FluidTypes.c.a(false) : super.c_(var0);
   }

   @Override
   public ItemStack a(IBlockAccess var0, BlockPosition var1, IBlockData var2) {
      return a(super.a(var0, var1, var2), var2.c(b));
   }

   public static ItemStack a(ItemStack var0, int var1) {
      if (var1 != 15) {
         NBTTagCompound var2 = new NBTTagCompound();
         var2.a(b.f(), String.valueOf(var1));
         var0.a("BlockStateTag", var2);
      }

      return var0;
   }
}
