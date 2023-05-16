package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.world.item.context.BlockActionContext;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockPropertyAttachPosition;
import net.minecraft.world.level.block.state.properties.BlockStateEnum;

public class BlockAttachable extends BlockFacingHorizontal {
   public static final BlockStateEnum<BlockPropertyAttachPosition> J = BlockProperties.U;

   protected BlockAttachable(BlockBase.Info var0) {
      super(var0);
   }

   @Override
   public boolean a(IBlockData var0, IWorldReader var1, BlockPosition var2) {
      return b(var1, var2, h(var0).g());
   }

   public static boolean b(IWorldReader var0, BlockPosition var1, EnumDirection var2) {
      BlockPosition var3 = var1.a(var2);
      return var0.a_(var3).d(var0, var3, var2.g());
   }

   @Nullable
   @Override
   public IBlockData a(BlockActionContext var0) {
      for(EnumDirection var4 : var0.f()) {
         IBlockData var5;
         if (var4.o() == EnumDirection.EnumAxis.b) {
            var5 = this.o().a(J, var4 == EnumDirection.b ? BlockPropertyAttachPosition.c : BlockPropertyAttachPosition.a).a(aD, var0.g());
         } else {
            var5 = this.o().a(J, BlockPropertyAttachPosition.b).a(aD, var4.g());
         }

         if (var5.a((IWorldReader)var0.q(), var0.a())) {
            return var5;
         }
      }

      return null;
   }

   @Override
   public IBlockData a(IBlockData var0, EnumDirection var1, IBlockData var2, GeneratorAccess var3, BlockPosition var4, BlockPosition var5) {
      return h(var0).g() == var1 && !var0.a(var3, var4) ? Blocks.a.o() : super.a(var0, var1, var2, var3, var4, var5);
   }

   protected static EnumDirection h(IBlockData var0) {
      switch((BlockPropertyAttachPosition)var0.c(J)) {
         case c:
            return EnumDirection.a;
         case a:
            return EnumDirection.b;
         default:
            return var0.c(aD);
      }
   }
}
