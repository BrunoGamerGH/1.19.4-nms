package net.minecraft.world.level.block;

import java.util.function.ToIntFunction;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockActionContext;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidTypes;

public class GlowLichenBlock extends MultifaceBlock implements IBlockFragilePlantElement, IBlockWaterlogged {
   private static final BlockStateBoolean b = BlockProperties.C;
   private final MultifaceSpreader c = new MultifaceSpreader(this);

   public GlowLichenBlock(BlockBase.Info var0) {
      super(var0);
      this.k(this.o().a(b, Boolean.valueOf(false)));
   }

   public static ToIntFunction<IBlockData> b(int var0) {
      return var1 -> MultifaceBlock.n(var1) ? var0 : 0;
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> var0) {
      super.a(var0);
      var0.a(b);
   }

   @Override
   public IBlockData a(IBlockData var0, EnumDirection var1, IBlockData var2, GeneratorAccess var3, BlockPosition var4, BlockPosition var5) {
      if (var0.c(b)) {
         var3.a(var4, FluidTypes.c, FluidTypes.c.a(var3));
      }

      return super.a(var0, var1, var2, var3, var4, var5);
   }

   @Override
   public boolean a(IBlockData var0, BlockActionContext var1) {
      return !var1.n().a(Items.fy) || super.a(var0, var1);
   }

   @Override
   public boolean a(IWorldReader var0, BlockPosition var1, IBlockData var2, boolean var3) {
      return EnumDirection.a().anyMatch(var3x -> this.c.a(var2, var0, var1, var3x.g()));
   }

   @Override
   public boolean a(World var0, RandomSource var1, BlockPosition var2, IBlockData var3) {
      return true;
   }

   @Override
   public void a(WorldServer var0, RandomSource var1, BlockPosition var2, IBlockData var3) {
      this.c.a(var3, var0, var2, var1);
   }

   @Override
   public Fluid c_(IBlockData var0) {
      return var0.c(b) ? FluidTypes.c.a(false) : super.c_(var0);
   }

   @Override
   public boolean c(IBlockData var0, IBlockAccess var1, BlockPosition var2) {
      return var0.r().c();
   }

   @Override
   public MultifaceSpreader c() {
      return this.c;
   }
}
