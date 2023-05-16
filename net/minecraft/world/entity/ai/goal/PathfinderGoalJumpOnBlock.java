package net.minecraft.world.entity.ai.goal;

import net.minecraft.core.BlockPosition;
import net.minecraft.tags.TagsBlock;
import net.minecraft.world.entity.animal.EntityCat;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.block.BlockBed;
import net.minecraft.world.level.block.BlockFurnaceFurace;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.TileEntityChest;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockPropertyBedPart;

public class PathfinderGoalJumpOnBlock extends PathfinderGoalGotoTarget {
   private final EntityCat g;

   public PathfinderGoalJumpOnBlock(EntityCat var0, double var1) {
      super(var0, var1, 8);
      this.g = var0;
   }

   @Override
   public boolean a() {
      return this.g.q() && !this.g.fS() && super.a();
   }

   @Override
   public void c() {
      super.c();
      this.g.y(false);
   }

   @Override
   public void d() {
      super.d();
      this.g.y(false);
   }

   @Override
   public void e() {
      super.e();
      this.g.y(this.m());
   }

   @Override
   protected boolean a(IWorldReader var0, BlockPosition var1) {
      if (!var0.w(var1.c())) {
         return false;
      } else {
         IBlockData var2 = var0.a_(var1);
         if (var2.a(Blocks.cu)) {
            return TileEntityChest.a(var0, var1) < 1;
         } else {
            return var2.a(Blocks.cC) && var2.c(BlockFurnaceFurace.b)
               ? true
               : var2.a(TagsBlock.Q, var0x -> var0x.<BlockPropertyBedPart>d(BlockBed.a).map(var0xx -> var0xx != BlockPropertyBedPart.a).orElse(true));
         }
      }
   }
}
