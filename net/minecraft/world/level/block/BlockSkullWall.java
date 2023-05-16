package net.minecraft.world.level.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.world.item.context.BlockActionContext;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockStateDirection;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;

public class BlockSkullWall extends BlockSkullAbstract {
   public static final BlockStateDirection a = BlockFacingHorizontal.aD;
   private static final Map<EnumDirection, VoxelShape> b = Maps.newEnumMap(
      ImmutableMap.of(
         EnumDirection.c,
         Block.a(4.0, 4.0, 8.0, 12.0, 12.0, 16.0),
         EnumDirection.d,
         Block.a(4.0, 4.0, 0.0, 12.0, 12.0, 8.0),
         EnumDirection.f,
         Block.a(0.0, 4.0, 4.0, 8.0, 12.0, 12.0),
         EnumDirection.e,
         Block.a(8.0, 4.0, 4.0, 16.0, 12.0, 12.0)
      )
   );

   protected BlockSkullWall(BlockSkull.a var0, BlockBase.Info var1) {
      super(var0, var1);
      this.k(this.D.b().a(a, EnumDirection.c));
   }

   @Override
   public String h() {
      return this.k().a();
   }

   @Override
   public VoxelShape a(IBlockData var0, IBlockAccess var1, BlockPosition var2, VoxelShapeCollision var3) {
      return b.get(var0.c(a));
   }

   @Override
   public IBlockData a(BlockActionContext var0) {
      IBlockData var1 = this.o();
      IBlockAccess var2 = var0.q();
      BlockPosition var3 = var0.a();
      EnumDirection[] var4 = var0.f();

      for(EnumDirection var8 : var4) {
         if (var8.o().d()) {
            EnumDirection var9 = var8.g();
            var1 = var1.a(a, var9);
            if (!var2.a_(var3.a(var8)).a(var0)) {
               return var1;
            }
         }
      }

      return null;
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
