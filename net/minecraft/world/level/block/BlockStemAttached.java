package net.minecraft.world.level.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockStateDirection;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;

public class BlockStemAttached extends BlockPlant {
   public static final BlockStateDirection a = BlockFacingHorizontal.aD;
   protected static final float b = 2.0F;
   private static final Map<EnumDirection, VoxelShape> c = Maps.newEnumMap(
      ImmutableMap.of(
         EnumDirection.d,
         Block.a(6.0, 0.0, 6.0, 10.0, 10.0, 16.0),
         EnumDirection.e,
         Block.a(0.0, 0.0, 6.0, 10.0, 10.0, 10.0),
         EnumDirection.c,
         Block.a(6.0, 0.0, 0.0, 10.0, 10.0, 10.0),
         EnumDirection.f,
         Block.a(6.0, 0.0, 6.0, 16.0, 10.0, 10.0)
      )
   );
   private final BlockStemmed d;
   private final Supplier<Item> e;

   protected BlockStemAttached(BlockStemmed var0, Supplier<Item> var1, BlockBase.Info var2) {
      super(var2);
      this.k(this.D.b().a(a, EnumDirection.c));
      this.d = var0;
      this.e = var1;
   }

   @Override
   public VoxelShape a(IBlockData var0, IBlockAccess var1, BlockPosition var2, VoxelShapeCollision var3) {
      return c.get(var0.c(a));
   }

   @Override
   public IBlockData a(IBlockData var0, EnumDirection var1, IBlockData var2, GeneratorAccess var3, BlockPosition var4, BlockPosition var5) {
      return !var2.a(this.d) && var1 == var0.c(a) ? this.d.b().o().a(BlockStem.b, Integer.valueOf(7)) : super.a(var0, var1, var2, var3, var4, var5);
   }

   @Override
   protected boolean d(IBlockData var0, IBlockAccess var1, BlockPosition var2) {
      return var0.a(Blocks.cB);
   }

   @Override
   public ItemStack a(IBlockAccess var0, BlockPosition var1, IBlockData var2) {
      return new ItemStack(this.e.get());
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
