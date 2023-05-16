package net.minecraft.world.level.block;

import net.minecraft.core.BlockPosition;
import net.minecraft.server.level.WorldServer;
import net.minecraft.tags.TagsBlock;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;

public class BlockNetherrack extends Block implements IBlockFragilePlantElement {
   public BlockNetherrack(BlockBase.Info var0) {
      super(var0);
   }

   @Override
   public boolean a(IWorldReader var0, BlockPosition var1, IBlockData var2, boolean var3) {
      if (!var0.a_(var1.c()).a((IBlockAccess)var0, var1)) {
         return false;
      } else {
         for(BlockPosition var5 : BlockPosition.a(var1.b(-1, -1, -1), var1.b(1, 1, 1))) {
            if (var0.a_(var5).a(TagsBlock.aI)) {
               return true;
            }
         }

         return false;
      }
   }

   @Override
   public boolean a(World var0, RandomSource var1, BlockPosition var2, IBlockData var3) {
      return true;
   }

   @Override
   public void a(WorldServer var0, RandomSource var1, BlockPosition var2, IBlockData var3) {
      boolean var4 = false;
      boolean var5 = false;

      for(BlockPosition var7 : BlockPosition.a(var2.b(-1, -1, -1), var2.b(1, 1, 1))) {
         IBlockData var8 = var0.a_(var7);
         if (var8.a(Blocks.oj)) {
            var5 = true;
         }

         if (var8.a(Blocks.os)) {
            var4 = true;
         }

         if (var5 && var4) {
            break;
         }
      }

      if (var5 && var4) {
         var0.a(var2, var1.h() ? Blocks.oj.o() : Blocks.os.o(), 3);
      } else if (var5) {
         var0.a(var2, Blocks.oj.o(), 3);
      } else if (var4) {
         var0.a(var2, Blocks.os.o(), 3);
      }
   }
}
