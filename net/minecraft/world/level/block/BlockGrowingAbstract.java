package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockActionContext;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;

public abstract class BlockGrowingAbstract extends Block {
   protected final EnumDirection a;
   protected final boolean b;
   protected final VoxelShape c;

   protected BlockGrowingAbstract(BlockBase.Info var0, EnumDirection var1, VoxelShape var2, boolean var3) {
      super(var0);
      this.a = var1;
      this.c = var2;
      this.b = var3;
   }

   @Nullable
   @Override
   public IBlockData a(BlockActionContext var0) {
      IBlockData var1 = var0.q().a_(var0.a().a(this.a));
      return !var1.a(this.c()) && !var1.a(this.b()) ? this.a(var0.q()) : this.b().o();
   }

   public IBlockData a(GeneratorAccess var0) {
      return this.o();
   }

   @Override
   public boolean a(IBlockData var0, IWorldReader var1, BlockPosition var2) {
      BlockPosition var3 = var2.a(this.a.g());
      IBlockData var4 = var1.a_(var3);
      if (!this.h(var4)) {
         return false;
      } else {
         return var4.a(this.c()) || var4.a(this.b()) || var4.d(var1, var3, this.a);
      }
   }

   @Override
   public void a(IBlockData var0, WorldServer var1, BlockPosition var2, RandomSource var3) {
      if (!var0.a(var1, var2)) {
         var1.b(var2, true);
      }
   }

   protected boolean h(IBlockData var0) {
      return true;
   }

   @Override
   public VoxelShape a(IBlockData var0, IBlockAccess var1, BlockPosition var2, VoxelShapeCollision var3) {
      return this.c;
   }

   protected abstract BlockGrowingTop c();

   protected abstract Block b();
}
