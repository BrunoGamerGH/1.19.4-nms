package net.minecraft.world.level.block;

import java.util.Map;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.world.item.context.BlockActionContext;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;

public class BlockHugeMushroom extends Block {
   public static final BlockStateBoolean a = BlockSprawling.a;
   public static final BlockStateBoolean b = BlockSprawling.b;
   public static final BlockStateBoolean c = BlockSprawling.c;
   public static final BlockStateBoolean d = BlockSprawling.d;
   public static final BlockStateBoolean e = BlockSprawling.e;
   public static final BlockStateBoolean f = BlockSprawling.f;
   private static final Map<EnumDirection, BlockStateBoolean> g = BlockSprawling.g;

   public BlockHugeMushroom(BlockBase.Info var0) {
      super(var0);
      this.k(
         this.D
            .b()
            .a(a, Boolean.valueOf(true))
            .a(b, Boolean.valueOf(true))
            .a(c, Boolean.valueOf(true))
            .a(d, Boolean.valueOf(true))
            .a(e, Boolean.valueOf(true))
            .a(f, Boolean.valueOf(true))
      );
   }

   @Override
   public IBlockData a(BlockActionContext var0) {
      IBlockAccess var1 = var0.q();
      BlockPosition var2 = var0.a();
      return this.o()
         .a(f, Boolean.valueOf(!var1.a_(var2.d()).a(this)))
         .a(e, Boolean.valueOf(!var1.a_(var2.c()).a(this)))
         .a(a, Boolean.valueOf(!var1.a_(var2.e()).a(this)))
         .a(b, Boolean.valueOf(!var1.a_(var2.h()).a(this)))
         .a(c, Boolean.valueOf(!var1.a_(var2.f()).a(this)))
         .a(d, Boolean.valueOf(!var1.a_(var2.g()).a(this)));
   }

   @Override
   public IBlockData a(IBlockData var0, EnumDirection var1, IBlockData var2, GeneratorAccess var3, BlockPosition var4, BlockPosition var5) {
      return var2.a(this) ? var0.a(g.get(var1), Boolean.valueOf(false)) : super.a(var0, var1, var2, var3, var4, var5);
   }

   @Override
   public IBlockData a(IBlockData var0, EnumBlockRotation var1) {
      return var0.a(g.get(var1.a(EnumDirection.c)), var0.c(a))
         .a(g.get(var1.a(EnumDirection.d)), var0.c(c))
         .a(g.get(var1.a(EnumDirection.f)), var0.c(b))
         .a(g.get(var1.a(EnumDirection.e)), var0.c(d))
         .a(g.get(var1.a(EnumDirection.b)), var0.c(e))
         .a(g.get(var1.a(EnumDirection.a)), var0.c(f));
   }

   @Override
   public IBlockData a(IBlockData var0, EnumBlockMirror var1) {
      return var0.a(g.get(var1.b(EnumDirection.c)), var0.c(a))
         .a(g.get(var1.b(EnumDirection.d)), var0.c(c))
         .a(g.get(var1.b(EnumDirection.f)), var0.c(b))
         .a(g.get(var1.b(EnumDirection.e)), var0.c(d))
         .a(g.get(var1.b(EnumDirection.b)), var0.c(e))
         .a(g.get(var1.b(EnumDirection.a)), var0.c(f));
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> var0) {
      var0.a(e, f, a, b, c, d);
   }
}
