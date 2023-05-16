package net.minecraft.world.level.block;

import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.tags.TagsBlock;
import net.minecraft.world.item.context.BlockActionContext;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockPropertyTrackPosition;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.block.state.properties.IBlockState;
import net.minecraft.world.level.material.EnumPistonReaction;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidTypes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;

public abstract class BlockMinecartTrackAbstract extends Block implements IBlockWaterlogged {
   protected static final VoxelShape a = Block.a(0.0, 0.0, 0.0, 16.0, 2.0, 16.0);
   protected static final VoxelShape b = Block.a(0.0, 0.0, 0.0, 16.0, 8.0, 16.0);
   public static final BlockStateBoolean c = BlockProperties.C;
   private final boolean d;

   public static boolean a(World var0, BlockPosition var1) {
      return g(var0.a_(var1));
   }

   public static boolean g(IBlockData var0) {
      return var0.a(TagsBlock.M) && var0.b() instanceof BlockMinecartTrackAbstract;
   }

   protected BlockMinecartTrackAbstract(boolean var0, BlockBase.Info var1) {
      super(var1);
      this.d = var0;
   }

   public boolean b() {
      return this.d;
   }

   @Override
   public VoxelShape a(IBlockData var0, IBlockAccess var1, BlockPosition var2, VoxelShapeCollision var3) {
      BlockPropertyTrackPosition var4 = var0.a(this) ? var0.c(this.c()) : null;
      return var4 != null && var4.b() ? b : a;
   }

   @Override
   public boolean a(IBlockData var0, IWorldReader var1, BlockPosition var2) {
      return c(var1, var2.d());
   }

   @Override
   public void b(IBlockData var0, World var1, BlockPosition var2, IBlockData var3, boolean var4) {
      if (!var3.a(var0.b())) {
         this.a(var0, var1, var2, var4);
      }
   }

   protected IBlockData a(IBlockData var0, World var1, BlockPosition var2, boolean var3) {
      var0 = this.a(var1, var2, var0, true);
      if (this.d) {
         var1.a(var0, var2, this, var2, var3);
      }

      return var0;
   }

   @Override
   public void a(IBlockData var0, World var1, BlockPosition var2, Block var3, BlockPosition var4, boolean var5) {
      if (!var1.B && var1.a_(var2).a(this)) {
         BlockPropertyTrackPosition var6 = var0.c(this.c());
         if (a(var2, var1, var6)) {
            c(var0, var1, var2);
            var1.a(var2, var5);
         } else {
            this.a(var0, var1, var2, var3);
         }
      }
   }

   private static boolean a(BlockPosition var0, World var1, BlockPropertyTrackPosition var2) {
      if (!c(var1, var0.d())) {
         return true;
      } else {
         switch(var2) {
            case c:
               return !c(var1, var0.h());
            case d:
               return !c(var1, var0.g());
            case e:
               return !c(var1, var0.e());
            case f:
               return !c(var1, var0.f());
            default:
               return false;
         }
      }
   }

   protected void a(IBlockData var0, World var1, BlockPosition var2, Block var3) {
   }

   protected IBlockData a(World var0, BlockPosition var1, IBlockData var2, boolean var3) {
      if (var0.B) {
         return var2;
      } else {
         BlockPropertyTrackPosition var4 = var2.c(this.c());
         return new MinecartTrackLogic(var0, var1, var2).a(var0.r(var1), var3, var4).c();
      }
   }

   @Override
   public EnumPistonReaction d(IBlockData var0) {
      return EnumPistonReaction.a;
   }

   @Override
   public void a(IBlockData var0, World var1, BlockPosition var2, IBlockData var3, boolean var4) {
      if (!var4) {
         super.a(var0, var1, var2, var3, var4);
         if (var0.c(this.c()).b()) {
            var1.a(var2.c(), this);
         }

         if (this.d) {
            var1.a(var2, this);
            var1.a(var2.d(), this);
         }
      }
   }

   @Override
   public IBlockData a(BlockActionContext var0) {
      Fluid var1 = var0.q().b_(var0.a());
      boolean var2 = var1.a() == FluidTypes.c;
      IBlockData var3 = super.o();
      EnumDirection var4 = var0.g();
      boolean var5 = var4 == EnumDirection.f || var4 == EnumDirection.e;
      return var3.a(this.c(), var5 ? BlockPropertyTrackPosition.b : BlockPropertyTrackPosition.a).a(c, Boolean.valueOf(var2));
   }

   public abstract IBlockState<BlockPropertyTrackPosition> c();

   @Override
   public IBlockData a(IBlockData var0, EnumDirection var1, IBlockData var2, GeneratorAccess var3, BlockPosition var4, BlockPosition var5) {
      if (var0.c(c)) {
         var3.a(var4, FluidTypes.c, FluidTypes.c.a(var3));
      }

      return super.a(var0, var1, var2, var3, var4, var5);
   }

   @Override
   public Fluid c_(IBlockData var0) {
      return var0.c(c) ? FluidTypes.c.a(false) : super.c_(var0);
   }
}
