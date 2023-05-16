package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.core.BlockPosition;
import net.minecraft.tags.TagsEntity;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.phys.shapes.VoxelShapes;

public class ClimbOnTopOfPowderSnowGoal extends PathfinderGoal {
   private final EntityInsentient a;
   private final World b;

   public ClimbOnTopOfPowderSnowGoal(EntityInsentient var0, World var1) {
      this.a = var0;
      this.b = var1;
      this.a(EnumSet.of(PathfinderGoal.Type.c));
   }

   @Override
   public boolean a() {
      boolean var0 = this.a.aA || this.a.az;
      if (var0 && this.a.ae().a(TagsEntity.f)) {
         BlockPosition var1 = this.a.dg().c();
         IBlockData var2 = this.b.a_(var1);
         return var2.a(Blocks.qy) || var2.k(this.b, var1) == VoxelShapes.a();
      } else {
         return false;
      }
   }

   @Override
   public boolean J_() {
      return true;
   }

   @Override
   public void e() {
      this.a.E().a();
   }
}
