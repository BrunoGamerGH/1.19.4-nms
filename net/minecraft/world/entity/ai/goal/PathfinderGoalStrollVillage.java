package net.minecraft.world.entity.ai.goal;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.SectionPosition;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.entity.EntityCreature;
import net.minecraft.world.entity.ai.behavior.BehaviorUtil;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.phys.Vec3D;

public class PathfinderGoalStrollVillage extends PathfinderGoalRandomStroll {
   private static final int i = 10;
   private static final int j = 7;

   public PathfinderGoalStrollVillage(EntityCreature var0, double var1, boolean var3) {
      super(var0, var1, 10, var3);
   }

   @Override
   public boolean a() {
      WorldServer var0 = (WorldServer)this.b.H;
      BlockPosition var1 = this.b.dg();
      return var0.b(var1) ? false : super.a();
   }

   @Nullable
   @Override
   protected Vec3D h() {
      WorldServer var0 = (WorldServer)this.b.H;
      BlockPosition var1 = this.b.dg();
      SectionPosition var2 = SectionPosition.a(var1);
      SectionPosition var3 = BehaviorUtil.a(var0, var2, 2);
      return var3 != var2 ? DefaultRandomPos.a(this.b, 10, 7, Vec3D.c(var3.q()), (float) (Math.PI / 2)) : null;
   }
}
