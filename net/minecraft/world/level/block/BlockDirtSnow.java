package net.minecraft.world.level.block;

import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.tags.TagsBlock;
import net.minecraft.world.item.context.BlockActionContext;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;

public class BlockDirtSnow extends Block {
   public static final BlockStateBoolean a = BlockProperties.z;

   protected BlockDirtSnow(BlockBase.Info var0) {
      super(var0);
      this.k(this.D.b().a(a, Boolean.valueOf(false)));
   }

   @Override
   public IBlockData a(IBlockData var0, EnumDirection var1, IBlockData var2, GeneratorAccess var3, BlockPosition var4, BlockPosition var5) {
      return var1 == EnumDirection.b ? var0.a(a, Boolean.valueOf(h(var2))) : super.a(var0, var1, var2, var3, var4, var5);
   }

   @Override
   public IBlockData a(BlockActionContext var0) {
      IBlockData var1 = var0.q().a_(var0.a().c());
      return this.o().a(a, Boolean.valueOf(h(var1)));
   }

   private static boolean h(IBlockData var0) {
      return var0.a(TagsBlock.bu);
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> var0) {
      var0.a(a);
   }
}
