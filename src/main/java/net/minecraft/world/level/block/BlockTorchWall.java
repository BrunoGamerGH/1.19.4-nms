package net.minecraft.world.level.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.particles.ParticleParam;
import net.minecraft.core.particles.Particles;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockActionContext;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockStateDirection;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;

public class BlockTorchWall extends BlockTorch {
   public static final BlockStateDirection a = BlockFacingHorizontal.aD;
   protected static final float b = 2.5F;
   private static final Map<EnumDirection, VoxelShape> c = Maps.newEnumMap(
      ImmutableMap.of(
         EnumDirection.c,
         Block.a(5.5, 3.0, 11.0, 10.5, 13.0, 16.0),
         EnumDirection.d,
         Block.a(5.5, 3.0, 0.0, 10.5, 13.0, 5.0),
         EnumDirection.e,
         Block.a(11.0, 3.0, 5.5, 16.0, 13.0, 10.5),
         EnumDirection.f,
         Block.a(0.0, 3.0, 5.5, 5.0, 13.0, 10.5)
      )
   );

   protected BlockTorchWall(BlockBase.Info var0, ParticleParam var1) {
      super(var0, var1);
      this.k(this.D.b().a(a, EnumDirection.c));
   }

   @Override
   public String h() {
      return this.k().a();
   }

   @Override
   public VoxelShape a(IBlockData var0, IBlockAccess var1, BlockPosition var2, VoxelShapeCollision var3) {
      return h(var0);
   }

   public static VoxelShape h(IBlockData var0) {
      return c.get(var0.c(a));
   }

   @Override
   public boolean a(IBlockData var0, IWorldReader var1, BlockPosition var2) {
      EnumDirection var3 = var0.c(a);
      BlockPosition var4 = var2.a(var3.g());
      IBlockData var5 = var1.a_(var4);
      return var5.d(var1, var4, var3);
   }

   @Nullable
   @Override
   public IBlockData a(BlockActionContext var0) {
      IBlockData var1 = this.o();
      IWorldReader var2 = var0.q();
      BlockPosition var3 = var0.a();
      EnumDirection[] var4 = var0.f();

      for(EnumDirection var8 : var4) {
         if (var8.o().d()) {
            EnumDirection var9 = var8.g();
            var1 = var1.a(a, var9);
            if (var1.a(var2, var3)) {
               return var1;
            }
         }
      }

      return null;
   }

   @Override
   public IBlockData a(IBlockData var0, EnumDirection var1, IBlockData var2, GeneratorAccess var3, BlockPosition var4, BlockPosition var5) {
      return var1.g() == var0.c(a) && !var0.a(var3, var4) ? Blocks.a.o() : var0;
   }

   @Override
   public void a(IBlockData var0, World var1, BlockPosition var2, RandomSource var3) {
      EnumDirection var4 = var0.c(a);
      double var5 = (double)var2.u() + 0.5;
      double var7 = (double)var2.v() + 0.7;
      double var9 = (double)var2.w() + 0.5;
      double var11 = 0.22;
      double var13 = 0.27;
      EnumDirection var15 = var4.g();
      var1.a(Particles.ab, var5 + 0.27 * (double)var15.j(), var7 + 0.22, var9 + 0.27 * (double)var15.l(), 0.0, 0.0, 0.0);
      var1.a(this.i, var5 + 0.27 * (double)var15.j(), var7 + 0.22, var9 + 0.27 * (double)var15.l(), 0.0, 0.0, 0.0);
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
      var0.a(a);
   }
}
