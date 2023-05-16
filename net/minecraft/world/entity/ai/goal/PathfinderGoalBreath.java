package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.core.BlockPosition;
import net.minecraft.util.MathHelper;
import net.minecraft.world.entity.EntityCreature;
import net.minecraft.world.entity.EnumMoveType;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.pathfinder.PathMode;
import net.minecraft.world.phys.Vec3D;

public class PathfinderGoalBreath extends PathfinderGoal {
   private final EntityCreature a;

   public PathfinderGoalBreath(EntityCreature var0) {
      this.a = var0;
      this.a(EnumSet.of(PathfinderGoal.Type.a, PathfinderGoal.Type.b));
   }

   @Override
   public boolean a() {
      return this.a.cd() < 140;
   }

   @Override
   public boolean b() {
      return this.a();
   }

   @Override
   public boolean I_() {
      return false;
   }

   @Override
   public void c() {
      this.h();
   }

   private void h() {
      Iterable<BlockPosition> var0 = BlockPosition.b(
         MathHelper.a(this.a.dl() - 1.0),
         this.a.dm(),
         MathHelper.a(this.a.dr() - 1.0),
         MathHelper.a(this.a.dl() + 1.0),
         MathHelper.a(this.a.dn() + 8.0),
         MathHelper.a(this.a.dr() + 1.0)
      );
      BlockPosition var1 = null;

      for(BlockPosition var3 : var0) {
         if (this.a(this.a.H, var3)) {
            var1 = var3;
            break;
         }
      }

      if (var1 == null) {
         var1 = BlockPosition.a(this.a.dl(), this.a.dn() + 8.0, this.a.dr());
      }

      this.a.G().a((double)var1.u(), (double)(var1.v() + 1), (double)var1.w(), 1.0);
   }

   @Override
   public void e() {
      this.h();
      this.a.a(0.02F, new Vec3D((double)this.a.bj, (double)this.a.bk, (double)this.a.bl));
      this.a.a(EnumMoveType.a, this.a.dj());
   }

   private boolean a(IWorldReader var0, BlockPosition var1) {
      IBlockData var2 = var0.a_(var1);
      return (var0.b_(var1).c() || var2.a(Blocks.mZ)) && var2.a(var0, var1, PathMode.a);
   }
}
