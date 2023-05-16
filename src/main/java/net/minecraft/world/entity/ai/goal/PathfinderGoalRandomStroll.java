package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import javax.annotation.Nullable;
import net.minecraft.world.entity.EntityCreature;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.phys.Vec3D;

public class PathfinderGoalRandomStroll extends PathfinderGoal {
   public static final int a = 120;
   protected final EntityCreature b;
   protected double c;
   protected double d;
   protected double e;
   protected final double f;
   protected int g;
   protected boolean h;
   private final boolean i;

   public PathfinderGoalRandomStroll(EntityCreature var0, double var1) {
      this(var0, var1, 120);
   }

   public PathfinderGoalRandomStroll(EntityCreature var0, double var1, int var3) {
      this(var0, var1, var3, true);
   }

   public PathfinderGoalRandomStroll(EntityCreature var0, double var1, int var3, boolean var4) {
      this.b = var0;
      this.f = var1;
      this.g = var3;
      this.i = var4;
      this.a(EnumSet.of(PathfinderGoal.Type.a));
   }

   @Override
   public boolean a() {
      if (this.b.bM()) {
         return false;
      } else {
         if (!this.h) {
            if (this.i && this.b.ee() >= 100) {
               return false;
            }

            if (this.b.dZ().a(b(this.g)) != 0) {
               return false;
            }
         }

         Vec3D var0 = this.h();
         if (var0 == null) {
            return false;
         } else {
            this.c = var0.c;
            this.d = var0.d;
            this.e = var0.e;
            this.h = false;
            return true;
         }
      }
   }

   @Nullable
   protected Vec3D h() {
      return DefaultRandomPos.a(this.b, 10, 7);
   }

   @Override
   public boolean b() {
      return !this.b.G().l() && !this.b.bM();
   }

   @Override
   public void c() {
      this.b.G().a(this.c, this.d, this.e, this.f);
   }

   @Override
   public void d() {
      this.b.G().n();
      super.d();
   }

   public void i() {
      this.h = true;
   }

   public void c(int var0) {
      this.g = var0;
   }
}
