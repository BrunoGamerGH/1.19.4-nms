package net.minecraft.world.level.block;

import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.tags.TagsBlock;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.ItemLeash;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockActionContext;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidTypes;
import net.minecraft.world.level.pathfinder.PathMode;
import net.minecraft.world.phys.MovingObjectPositionBlock;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;

public class BlockFence extends BlockTall {
   private final VoxelShape[] i;

   public BlockFence(BlockBase.Info var0) {
      super(2.0F, 2.0F, 16.0F, 16.0F, 24.0F, var0);
      this.k(
         this.D
            .b()
            .a(a, Boolean.valueOf(false))
            .a(b, Boolean.valueOf(false))
            .a(c, Boolean.valueOf(false))
            .a(d, Boolean.valueOf(false))
            .a(e, Boolean.valueOf(false))
      );
      this.i = this.a(2.0F, 1.0F, 16.0F, 6.0F, 15.0F);
   }

   @Override
   public VoxelShape f(IBlockData var0, IBlockAccess var1, BlockPosition var2) {
      return this.i[this.g(var0)];
   }

   @Override
   public VoxelShape b(IBlockData var0, IBlockAccess var1, BlockPosition var2, VoxelShapeCollision var3) {
      return this.a(var0, var1, var2, var3);
   }

   @Override
   public boolean a(IBlockData var0, IBlockAccess var1, BlockPosition var2, PathMode var3) {
      return false;
   }

   public boolean a(IBlockData var0, boolean var1, EnumDirection var2) {
      Block var3 = var0.b();
      boolean var4 = this.h(var0);
      boolean var5 = var3 instanceof BlockFenceGate && BlockFenceGate.a(var0, var2);
      return !j(var0) && var1 || var4 || var5;
   }

   private boolean h(IBlockData var0) {
      return var0.a(TagsBlock.R) && var0.a(TagsBlock.j) == this.o().a(TagsBlock.j);
   }

   @Override
   public EnumInteractionResult a(IBlockData var0, World var1, BlockPosition var2, EntityHuman var3, EnumHand var4, MovingObjectPositionBlock var5) {
      if (var1.B) {
         ItemStack var6 = var3.b(var4);
         return var6.a(Items.tM) ? EnumInteractionResult.a : EnumInteractionResult.d;
      } else {
         return ItemLeash.a(var3, var1, var2);
      }
   }

   @Override
   public IBlockData a(BlockActionContext var0) {
      IBlockAccess var1 = var0.q();
      BlockPosition var2 = var0.a();
      Fluid var3 = var0.q().b_(var0.a());
      BlockPosition var4 = var2.e();
      BlockPosition var5 = var2.h();
      BlockPosition var6 = var2.f();
      BlockPosition var7 = var2.g();
      IBlockData var8 = var1.a_(var4);
      IBlockData var9 = var1.a_(var5);
      IBlockData var10 = var1.a_(var6);
      IBlockData var11 = var1.a_(var7);
      return super.a(var0)
         .a(a, Boolean.valueOf(this.a(var8, var8.d(var1, var4, EnumDirection.d), EnumDirection.d)))
         .a(b, Boolean.valueOf(this.a(var9, var9.d(var1, var5, EnumDirection.e), EnumDirection.e)))
         .a(c, Boolean.valueOf(this.a(var10, var10.d(var1, var6, EnumDirection.c), EnumDirection.c)))
         .a(d, Boolean.valueOf(this.a(var11, var11.d(var1, var7, EnumDirection.f), EnumDirection.f)))
         .a(e, Boolean.valueOf(var3.a() == FluidTypes.c));
   }

   @Override
   public IBlockData a(IBlockData var0, EnumDirection var1, IBlockData var2, GeneratorAccess var3, BlockPosition var4, BlockPosition var5) {
      if (var0.c(e)) {
         var3.a(var4, FluidTypes.c, FluidTypes.c.a(var3));
      }

      return var1.o().e() == EnumDirection.EnumDirectionLimit.a
         ? var0.a(f.get(var1), Boolean.valueOf(this.a(var2, var2.d(var3, var5, var1.g()), var1.g())))
         : super.a(var0, var1, var2, var3, var4, var5);
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> var0) {
      var0.a(a, b, d, c, e);
   }
}
