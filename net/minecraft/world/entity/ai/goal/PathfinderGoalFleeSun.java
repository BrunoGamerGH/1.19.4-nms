package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityCreature;
import net.minecraft.world.entity.EnumItemSlot;
import net.minecraft.world.level.World;
import net.minecraft.world.phys.Vec3D;

public class PathfinderGoalFleeSun extends PathfinderGoal {
   protected final EntityCreature a;
   private double b;
   private double c;
   private double d;
   private final double e;
   private final World f;

   public PathfinderGoalFleeSun(EntityCreature var0, double var1) {
      this.a = var0;
      this.e = var1;
      this.f = var0.H;
      this.a(EnumSet.of(PathfinderGoal.Type.a));
   }

   @Override
   public boolean a() {
      if (this.a.P_() != null) {
         return false;
      } else if (!this.f.M()) {
         return false;
      } else if (!this.a.bK()) {
         return false;
      } else if (!this.f.g(this.a.dg())) {
         return false;
      } else {
         return !this.a.c(EnumItemSlot.f).b() ? false : this.h();
      }
   }

   protected boolean h() {
      Vec3D var0 = this.i();
      if (var0 == null) {
         return false;
      } else {
         this.b = var0.c;
         this.c = var0.d;
         this.d = var0.e;
         return true;
      }
   }

   @Override
   public boolean b() {
      return !this.a.G().l();
   }

   @Override
   public void c() {
      this.a.G().a(this.b, this.c, this.d, this.e);
   }

   @Nullable
   protected Vec3D i() {
      RandomSource var0 = this.a.dZ();
      BlockPosition var1 = this.a.dg();

      for(int var2 = 0; var2 < 10; ++var2) {
         BlockPosition var3 = var1.b(var0.a(20) - 10, var0.a(6) - 3, var0.a(20) - 10);
         if (!this.f.g(var3) && this.a.f(var3) < 0.0F) {
            return Vec3D.c(var3);
         }
      }

      return null;
   }
}
