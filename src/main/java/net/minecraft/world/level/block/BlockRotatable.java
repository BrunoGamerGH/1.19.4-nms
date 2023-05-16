package net.minecraft.world.level.block;

import net.minecraft.core.EnumDirection;
import net.minecraft.world.item.context.BlockActionContext;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockStateEnum;

public class BlockRotatable extends Block {
   public static final BlockStateEnum<EnumDirection.EnumAxis> g = BlockProperties.I;

   public BlockRotatable(BlockBase.Info var0) {
      super(var0);
      this.k(this.o().a(g, EnumDirection.EnumAxis.b));
   }

   @Override
   public IBlockData a(IBlockData var0, EnumBlockRotation var1) {
      return b(var0, var1);
   }

   public static IBlockData b(IBlockData var0, EnumBlockRotation var1) {
      switch(var1) {
         case d:
         case b:
            switch((EnumDirection.EnumAxis)var0.c(g)) {
               case a:
                  return var0.a(g, EnumDirection.EnumAxis.c);
               case c:
                  return var0.a(g, EnumDirection.EnumAxis.a);
               default:
                  return var0;
            }
         default:
            return var0;
      }
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> var0) {
      var0.a(g);
   }

   @Override
   public IBlockData a(BlockActionContext var0) {
      return this.o().a(g, var0.k().o());
   }
}
