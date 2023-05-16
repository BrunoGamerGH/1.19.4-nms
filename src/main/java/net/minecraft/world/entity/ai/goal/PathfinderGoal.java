package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.util.MathHelper;

public abstract class PathfinderGoal {
   private final EnumSet<PathfinderGoal.Type> a = EnumSet.noneOf(PathfinderGoal.Type.class);

   public abstract boolean a();

   public boolean b() {
      return this.a();
   }

   public boolean I_() {
      return true;
   }

   public void c() {
   }

   public void d() {
   }

   public boolean J_() {
      return false;
   }

   public void e() {
   }

   public void a(EnumSet<PathfinderGoal.Type> var0) {
      this.a.clear();
      this.a.addAll(var0);
   }

   @Override
   public String toString() {
      return this.getClass().getSimpleName();
   }

   public EnumSet<PathfinderGoal.Type> j() {
      return this.a;
   }

   protected int a(int var0) {
      return this.J_() ? var0 : b(var0);
   }

   protected static int b(int var0) {
      return MathHelper.e(var0, 2);
   }

   public static enum Type {
      a,
      b,
      c,
      d;
   }
}
