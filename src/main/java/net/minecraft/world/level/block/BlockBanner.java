package net.minecraft.world.level.block;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.world.item.EnumColor;
import net.minecraft.world.item.context.BlockActionContext;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockStateInteger;
import net.minecraft.world.level.block.state.properties.RotationSegment;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;

public class BlockBanner extends BlockBannerAbstract {
   public static final BlockStateInteger a = BlockProperties.ba;
   private static final Map<EnumColor, Block> b = Maps.newHashMap();
   private static final VoxelShape c = Block.a(4.0, 0.0, 4.0, 12.0, 16.0, 12.0);

   public BlockBanner(EnumColor var0, BlockBase.Info var1) {
      super(var0, var1);
      this.k(this.D.b().a(a, Integer.valueOf(0)));
      b.put(var0, this);
   }

   @Override
   public boolean a(IBlockData var0, IWorldReader var1, BlockPosition var2) {
      return var1.a_(var2.d()).d().b();
   }

   @Override
   public VoxelShape a(IBlockData var0, IBlockAccess var1, BlockPosition var2, VoxelShapeCollision var3) {
      return c;
   }

   @Override
   public IBlockData a(BlockActionContext var0) {
      return this.o().a(a, Integer.valueOf(RotationSegment.a(var0.i() + 180.0F)));
   }

   @Override
   public IBlockData a(IBlockData var0, EnumDirection var1, IBlockData var2, GeneratorAccess var3, BlockPosition var4, BlockPosition var5) {
      return var1 == EnumDirection.a && !var0.a(var3, var4) ? Blocks.a.o() : super.a(var0, var1, var2, var3, var4, var5);
   }

   @Override
   public IBlockData a(IBlockData var0, EnumBlockRotation var1) {
      return var0.a(a, Integer.valueOf(var1.a(var0.c(a), 16)));
   }

   @Override
   public IBlockData a(IBlockData var0, EnumBlockMirror var1) {
      return var0.a(a, Integer.valueOf(var1.a(var0.c(a), 16)));
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> var0) {
      var0.a(a);
   }

   public static Block a(EnumColor var0) {
      return b.getOrDefault(var0, Blocks.iI);
   }
}
