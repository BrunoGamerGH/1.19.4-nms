package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import javax.annotation.Nullable;

public class PathfinderGoalWrapped extends PathfinderGoal {
   private final PathfinderGoal a;
   private final int b;
   private boolean c;

   public PathfinderGoalWrapped(int var0, PathfinderGoal var1) {
      this.b = var0;
      this.a = var1;
   }

   public boolean a(PathfinderGoalWrapped var0) {
      return this.I_() && var0.i() < this.i();
   }

   @Override
   public boolean a() {
      return this.a.a();
   }

   @Override
   public boolean b() {
      return this.a.b();
   }

   @Override
   public boolean I_() {
      return this.a.I_();
   }

   @Override
   public void c() {
      if (!this.c) {
         this.c = true;
         this.a.c();
      }
   }

   @Override
   public void d() {
      if (this.c) {
         this.c = false;
         this.a.d();
      }
   }

   @Override
   public boolean J_() {
      return this.a.J_();
   }

   @Override
   protected int a(int var0) {
      return this.a.a(var0);
   }

   @Override
   public void e() {
      this.a.e();
   }

   @Override
   public void a(EnumSet<PathfinderGoal.Type> var0) {
      this.a.a(var0);
   }

   @Override
   public EnumSet<PathfinderGoal.Type> j() {
      return this.a.j();
   }

   public boolean h() {
      return this.c;
   }

   public int i() {
      return this.b;
   }

   public PathfinderGoal k() {
      return this.a;
   }

   @Override
   public boolean equals(@Nullable Object var0) {
      if (this == var0) {
         return true;
      } else {
         return var0 != null && this.getClass() == var0.getClass() ? this.a.equals(((PathfinderGoalWrapped)var0).a) : false;
      }
   }

   @Override
   public int hashCode() {
      return this.a.hashCode();
   }
}
