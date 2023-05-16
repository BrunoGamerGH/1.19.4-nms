package net.minecraft.world.level.block;

import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockActionContext;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.pathfinder.PathMode;

public class BlockChorusFruit extends BlockSprawling {
   protected BlockChorusFruit(BlockBase.Info var0) {
      super(0.3125F, var0);
      this.k(
         this.D
            .b()
            .a(a, Boolean.valueOf(false))
            .a(b, Boolean.valueOf(false))
            .a(c, Boolean.valueOf(false))
            .a(d, Boolean.valueOf(false))
            .a(e, Boolean.valueOf(false))
            .a(f, Boolean.valueOf(false))
      );
   }

   @Override
   public IBlockData a(BlockActionContext var0) {
      return this.a(var0.q(), var0.a());
   }

   public IBlockData a(IBlockAccess var0, BlockPosition var1) {
      IBlockData var2 = var0.a_(var1.d());
      IBlockData var3 = var0.a_(var1.c());
      IBlockData var4 = var0.a_(var1.e());
      IBlockData var5 = var0.a_(var1.h());
      IBlockData var6 = var0.a_(var1.f());
      IBlockData var7 = var0.a_(var1.g());
      return this.o()
         .a(f, Boolean.valueOf(var2.a(this) || var2.a(Blocks.ku) || var2.a(Blocks.fy)))
         .a(e, Boolean.valueOf(var3.a(this) || var3.a(Blocks.ku)))
         .a(a, Boolean.valueOf(var4.a(this) || var4.a(Blocks.ku)))
         .a(b, Boolean.valueOf(var5.a(this) || var5.a(Blocks.ku)))
         .a(c, Boolean.valueOf(var6.a(this) || var6.a(Blocks.ku)))
         .a(d, Boolean.valueOf(var7.a(this) || var7.a(Blocks.ku)));
   }

   @Override
   public IBlockData a(IBlockData var0, EnumDirection var1, IBlockData var2, GeneratorAccess var3, BlockPosition var4, BlockPosition var5) {
      if (!var0.a(var3, var4)) {
         var3.a(var4, this, 1);
         return super.a(var0, var1, var2, var3, var4, var5);
      } else {
         boolean var6 = var2.a(this) || var2.a(Blocks.ku) || var1 == EnumDirection.a && var2.a(Blocks.fy);
         return var0.a(g.get(var1), Boolean.valueOf(var6));
      }
   }

   @Override
   public void a(IBlockData var0, WorldServer var1, BlockPosition var2, RandomSource var3) {
      if (!var0.a(var1, var2)) {
         var1.b(var2, true);
      }
   }

   @Override
   public boolean a(IBlockData var0, IWorldReader var1, BlockPosition var2) {
      IBlockData var3 = var1.a_(var2.d());
      boolean var4 = !var1.a_(var2.c()).h() && !var3.h();

      for(EnumDirection var6 : EnumDirection.EnumDirectionLimit.a) {
         BlockPosition var7 = var2.a(var6);
         IBlockData var8 = var1.a_(var7);
         if (var8.a(this)) {
            if (var4) {
               return false;
            }

            IBlockData var9 = var1.a_(var7.d());
            if (var9.a(this) || var9.a(Blocks.fy)) {
               return true;
            }
         }
      }

      return var3.a(this) || var3.a(Blocks.fy);
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> var0) {
      var0.a(a, b, c, d, e, f);
   }

   @Override
   public boolean a(IBlockData var0, IBlockAccess var1, BlockPosition var2, PathMode var3) {
      return false;
   }
}
