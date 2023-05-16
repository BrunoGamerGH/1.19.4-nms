package net.minecraft.world.level.block;

import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.tags.TagsBlock;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;

public class BlockSoulFire extends BlockFireAbstract {
   public BlockSoulFire(BlockBase.Info var0) {
      super(var0, 2.0F);
   }

   @Override
   public IBlockData a(IBlockData var0, EnumDirection var1, IBlockData var2, GeneratorAccess var3, BlockPosition var4, BlockPosition var5) {
      return this.a(var0, var3, var4) ? this.o() : Blocks.a.o();
   }

   @Override
   public boolean a(IBlockData var0, IWorldReader var1, BlockPosition var2) {
      return h(var1.a_(var2.d()));
   }

   public static boolean h(IBlockData var0) {
      return var0.a(TagsBlock.aQ);
   }

   @Override
   protected boolean f(IBlockData var0) {
      return true;
   }
}
