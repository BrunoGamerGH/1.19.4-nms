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
import net.minecraft.world.level.block.state.properties.BlockStateDirection;
import net.minecraft.world.level.material.FluidTypes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;

public class BlockCoralFanWallAbstract extends BlockCoralFanAbstract {
   public static final BlockStateDirection a = BlockFacingHorizontal.aD;
   private static final Map<EnumDirection, VoxelShape> b = Maps.newEnumMap(
      ImmutableMap.of(
         EnumDirection.c,
         Block.a(0.0, 4.0, 5.0, 16.0, 12.0, 16.0),
         EnumDirection.d,
         Block.a(0.0, 4.0, 0.0, 16.0, 12.0, 11.0),
         EnumDirection.e,
         Block.a(5.0, 4.0, 0.0, 16.0, 12.0, 16.0),
         EnumDirection.f,
         Block.a(0.0, 4.0, 0.0, 11.0, 12.0, 16.0)
      )
   );

   protected BlockCoralFanWallAbstract(BlockBase.Info var0) {
      super(var0);
      this.k(this.D.b().a(a, EnumDirection.c).a(c, Boolean.valueOf(true)));
   }

   @Override
   public VoxelShape a(IBlockData var0, IBlockAccess var1, BlockPosition var2, VoxelShapeCollision var3) {
      return b.get(var0.c(a));
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
      var0.a(a, c);
   }

   @Override
   public IBlockData a(IBlockData var0, EnumDirection var1, IBlockData var2, GeneratorAccess var3, BlockPosition var4, BlockPosition var5) {
      if (var0.c(c)) {
         var3.a(var4, FluidTypes.c, FluidTypes.c.a(var3));
      }

      return var1.g() == var0.c(a) && !var0.a(var3, var4) ? Blocks.a.o() : var0;
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
      IBlockData var1 = super.a(var0);
      IWorldReader var2 = var0.q();
      BlockPosition var3 = var0.a();
      EnumDirection[] var4 = var0.f();

      for(EnumDirection var8 : var4) {
         if (var8.o().d()) {
            var1 = var1.a(a, var8.g());
            if (var1.a(var2, var3)) {
               return var1;
            }
         }
      }

      return null;
   }
}
