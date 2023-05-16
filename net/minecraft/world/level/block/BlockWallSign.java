package net.minecraft.world.level.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.world.item.context.BlockActionContext;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockPropertyWood;
import net.minecraft.world.level.block.state.properties.BlockStateDirection;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidTypes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;

public class BlockWallSign extends BlockSign {
   public static final BlockStateDirection a = BlockFacingHorizontal.aD;
   protected static final float b = 2.0F;
   protected static final float c = 4.5F;
   protected static final float d = 12.5F;
   private static final Map<EnumDirection, VoxelShape> h = Maps.newEnumMap(
      ImmutableMap.of(
         EnumDirection.c,
         Block.a(0.0, 4.5, 14.0, 16.0, 12.5, 16.0),
         EnumDirection.d,
         Block.a(0.0, 4.5, 0.0, 16.0, 12.5, 2.0),
         EnumDirection.f,
         Block.a(0.0, 4.5, 0.0, 2.0, 12.5, 16.0),
         EnumDirection.e,
         Block.a(14.0, 4.5, 0.0, 16.0, 12.5, 16.0)
      )
   );

   public BlockWallSign(BlockBase.Info var0, BlockPropertyWood var1) {
      super(var0.a(var1.d()), var1);
      this.k(this.D.b().a(a, EnumDirection.c).a(e, Boolean.valueOf(false)));
   }

   @Override
   public String h() {
      return this.k().a();
   }

   @Override
   public VoxelShape a(IBlockData var0, IBlockAccess var1, BlockPosition var2, VoxelShapeCollision var3) {
      return h.get(var0.c(a));
   }

   @Override
   public boolean a(IBlockData var0, IWorldReader var1, BlockPosition var2) {
      return var1.a_(var2.a(var0.c(a).g())).d().b();
   }

   @Nullable
   @Override
   public IBlockData a(BlockActionContext var0) {
      IBlockData var1 = this.o();
      Fluid var2 = var0.q().b_(var0.a());
      IWorldReader var3 = var0.q();
      BlockPosition var4 = var0.a();
      EnumDirection[] var5 = var0.f();

      for(EnumDirection var9 : var5) {
         if (var9.o().d()) {
            EnumDirection var10 = var9.g();
            var1 = var1.a(a, var10);
            if (var1.a(var3, var4)) {
               return var1.a(e, Boolean.valueOf(var2.a() == FluidTypes.c));
            }
         }
      }

      return null;
   }

   @Override
   public IBlockData a(IBlockData var0, EnumDirection var1, IBlockData var2, GeneratorAccess var3, BlockPosition var4, BlockPosition var5) {
      return var1.g() == var0.c(a) && !var0.a(var3, var4) ? Blocks.a.o() : super.a(var0, var1, var2, var3, var4, var5);
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
      var0.a(a, e);
   }
}
