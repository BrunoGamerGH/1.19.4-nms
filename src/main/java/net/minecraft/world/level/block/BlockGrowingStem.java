package net.minecraft.world.level.block;

import java.util.Optional;
import net.minecraft.BlockUtil;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockActionContext;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.material.FluidTypes;
import net.minecraft.world.phys.shapes.VoxelShape;

public abstract class BlockGrowingStem extends BlockGrowingAbstract implements IBlockFragilePlantElement {
   protected BlockGrowingStem(BlockBase.Info var0, EnumDirection var1, VoxelShape var2, boolean var3) {
      super(var0, var1, var2, var3);
   }

   protected IBlockData a(IBlockData var0, IBlockData var1) {
      return var1;
   }

   @Override
   public IBlockData a(IBlockData var0, EnumDirection var1, IBlockData var2, GeneratorAccess var3, BlockPosition var4, BlockPosition var5) {
      if (var1 == this.a.g() && !var0.a(var3, var4)) {
         var3.a(var4, this, 1);
      }

      BlockGrowingTop var6 = this.c();
      if (var1 == this.a && !var2.a(this) && !var2.a(var6)) {
         return this.a(var0, var6.a(var3));
      } else {
         if (this.b) {
            var3.a(var4, FluidTypes.c, FluidTypes.c.a(var3));
         }

         return super.a(var0, var1, var2, var3, var4, var5);
      }
   }

   @Override
   public ItemStack a(IBlockAccess var0, BlockPosition var1, IBlockData var2) {
      return new ItemStack(this.c());
   }

   @Override
   public boolean a(IWorldReader var0, BlockPosition var1, IBlockData var2, boolean var3) {
      Optional<BlockPosition> var4 = this.a(var0, var1, var2.b());
      return var4.isPresent() && this.c().g(var0.a_(var4.get().a(this.a)));
   }

   @Override
   public boolean a(World var0, RandomSource var1, BlockPosition var2, IBlockData var3) {
      return true;
   }

   @Override
   public void a(WorldServer var0, RandomSource var1, BlockPosition var2, IBlockData var3) {
      Optional<BlockPosition> var4 = this.a(var0, var2, var3.b());
      if (var4.isPresent()) {
         IBlockData var5 = var0.a_(var4.get());
         ((BlockGrowingTop)var5.b()).a(var0, var1, var4.get(), var5);
      }
   }

   private Optional<BlockPosition> a(IBlockAccess var0, BlockPosition var1, Block var2) {
      return BlockUtil.a(var0, var1, var2, this.a, this.c());
   }

   @Override
   public boolean a(IBlockData var0, BlockActionContext var1) {
      boolean var2 = super.a(var0, var1);
      return var2 && var1.n().a(this.c().k()) ? false : var2;
   }

   @Override
   protected Block b() {
      return this;
   }
}
