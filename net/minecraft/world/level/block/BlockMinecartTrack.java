package net.minecraft.world.level.block;

import net.minecraft.core.BlockPosition;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockPropertyTrackPosition;
import net.minecraft.world.level.block.state.properties.BlockStateEnum;
import net.minecraft.world.level.block.state.properties.IBlockState;

public class BlockMinecartTrack extends BlockMinecartTrackAbstract {
   public static final BlockStateEnum<BlockPropertyTrackPosition> d = BlockProperties.ag;

   protected BlockMinecartTrack(BlockBase.Info var0) {
      super(false, var0);
      this.k(this.D.b().a(d, BlockPropertyTrackPosition.a).a(c, Boolean.valueOf(false)));
   }

   @Override
   protected void a(IBlockData var0, World var1, BlockPosition var2, Block var3) {
      if (var3.o().j() && new MinecartTrackLogic(var1, var2, var0).b() == 3) {
         this.a(var1, var2, var0, false);
      }
   }

   @Override
   public IBlockState<BlockPropertyTrackPosition> c() {
      return d;
   }

   @Override
   public IBlockData a(IBlockData var0, EnumBlockRotation var1) {
      switch(var1) {
         case c:
            switch((BlockPropertyTrackPosition)var0.c(d)) {
               case c:
                  return var0.a(d, BlockPropertyTrackPosition.d);
               case d:
                  return var0.a(d, BlockPropertyTrackPosition.c);
               case e:
                  return var0.a(d, BlockPropertyTrackPosition.f);
               case f:
                  return var0.a(d, BlockPropertyTrackPosition.e);
               case g:
                  return var0.a(d, BlockPropertyTrackPosition.i);
               case h:
                  return var0.a(d, BlockPropertyTrackPosition.j);
               case i:
                  return var0.a(d, BlockPropertyTrackPosition.g);
               case j:
                  return var0.a(d, BlockPropertyTrackPosition.h);
            }
         case d:
            switch((BlockPropertyTrackPosition)var0.c(d)) {
               case c:
                  return var0.a(d, BlockPropertyTrackPosition.e);
               case d:
                  return var0.a(d, BlockPropertyTrackPosition.f);
               case e:
                  return var0.a(d, BlockPropertyTrackPosition.d);
               case f:
                  return var0.a(d, BlockPropertyTrackPosition.c);
               case g:
                  return var0.a(d, BlockPropertyTrackPosition.j);
               case h:
                  return var0.a(d, BlockPropertyTrackPosition.g);
               case i:
                  return var0.a(d, BlockPropertyTrackPosition.h);
               case j:
                  return var0.a(d, BlockPropertyTrackPosition.i);
               case a:
                  return var0.a(d, BlockPropertyTrackPosition.b);
               case b:
                  return var0.a(d, BlockPropertyTrackPosition.a);
            }
         case b:
            switch((BlockPropertyTrackPosition)var0.c(d)) {
               case c:
                  return var0.a(d, BlockPropertyTrackPosition.f);
               case d:
                  return var0.a(d, BlockPropertyTrackPosition.e);
               case e:
                  return var0.a(d, BlockPropertyTrackPosition.c);
               case f:
                  return var0.a(d, BlockPropertyTrackPosition.d);
               case g:
                  return var0.a(d, BlockPropertyTrackPosition.h);
               case h:
                  return var0.a(d, BlockPropertyTrackPosition.i);
               case i:
                  return var0.a(d, BlockPropertyTrackPosition.j);
               case j:
                  return var0.a(d, BlockPropertyTrackPosition.g);
               case a:
                  return var0.a(d, BlockPropertyTrackPosition.b);
               case b:
                  return var0.a(d, BlockPropertyTrackPosition.a);
            }
         default:
            return var0;
      }
   }

   @Override
   public IBlockData a(IBlockData var0, EnumBlockMirror var1) {
      BlockPropertyTrackPosition var2 = var0.c(d);
      switch(var1) {
         case b:
            switch(var2) {
               case e:
                  return var0.a(d, BlockPropertyTrackPosition.f);
               case f:
                  return var0.a(d, BlockPropertyTrackPosition.e);
               case g:
                  return var0.a(d, BlockPropertyTrackPosition.j);
               case h:
                  return var0.a(d, BlockPropertyTrackPosition.i);
               case i:
                  return var0.a(d, BlockPropertyTrackPosition.h);
               case j:
                  return var0.a(d, BlockPropertyTrackPosition.g);
               default:
                  return super.a(var0, var1);
            }
         case c:
            switch(var2) {
               case c:
                  return var0.a(d, BlockPropertyTrackPosition.d);
               case d:
                  return var0.a(d, BlockPropertyTrackPosition.c);
               case e:
               case f:
               default:
                  break;
               case g:
                  return var0.a(d, BlockPropertyTrackPosition.h);
               case h:
                  return var0.a(d, BlockPropertyTrackPosition.g);
               case i:
                  return var0.a(d, BlockPropertyTrackPosition.j);
               case j:
                  return var0.a(d, BlockPropertyTrackPosition.i);
            }
      }

      return super.a(var0, var1);
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> var0) {
      var0.a(d, c);
   }
}
