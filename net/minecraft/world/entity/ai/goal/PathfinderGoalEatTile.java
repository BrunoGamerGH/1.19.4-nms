package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import java.util.function.Predicate;
import net.minecraft.core.BlockPosition;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;

public class PathfinderGoalEatTile extends PathfinderGoal {
   private static final int a = 40;
   private static final Predicate<IBlockData> b = BlockStatePredicate.a(Blocks.bs);
   private final EntityInsentient c;
   private final World d;
   private int e;

   public PathfinderGoalEatTile(EntityInsentient entityinsentient) {
      this.c = entityinsentient;
      this.d = entityinsentient.H;
      this.a(EnumSet.of(PathfinderGoal.Type.a, PathfinderGoal.Type.b, PathfinderGoal.Type.c));
   }

   @Override
   public boolean a() {
      if (this.c.dZ().a(this.c.y_() ? 50 : 1000) != 0) {
         return false;
      } else {
         BlockPosition blockposition = this.c.dg();
         return b.test(this.d.a_(blockposition)) ? true : this.d.a_(blockposition.d()).a(Blocks.i);
      }
   }

   @Override
   public void c() {
      this.e = this.a(40);
      this.d.a(this.c, (byte)10);
      this.c.G().n();
   }

   @Override
   public void d() {
      this.e = 0;
   }

   @Override
   public boolean b() {
      return this.e > 0;
   }

   public int h() {
      return this.e;
   }

   @Override
   public void e() {
      this.e = Math.max(0, this.e - 1);
      if (this.e == this.a(4)) {
         BlockPosition blockposition = this.c.dg();
         if (b.test(this.d.a_(blockposition))) {
            if (!CraftEventFactory.callEntityChangeBlockEvent(this.c, blockposition, Blocks.a.o(), !this.d.W().b(GameRules.c)).isCancelled()) {
               this.d.b(blockposition, false);
            }

            this.c.J();
         } else {
            BlockPosition blockposition1 = blockposition.d();
            if (this.d.a_(blockposition1).a(Blocks.i)) {
               if (!CraftEventFactory.callEntityChangeBlockEvent(this.c, blockposition1, Blocks.a.o(), !this.d.W().b(GameRules.c)).isCancelled()) {
                  this.d.c(2001, blockposition1, Block.i(Blocks.i.o()));
                  this.d.a(blockposition1, Blocks.j.o(), 2);
               }

               this.c.J();
            }
         }
      }
   }
}
