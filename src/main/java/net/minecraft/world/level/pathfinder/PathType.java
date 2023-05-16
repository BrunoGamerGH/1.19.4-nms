package net.minecraft.world.level.pathfinder;

public enum PathType {
   a(-1.0F),
   b(0.0F),
   c(0.0F),
   d(0.0F),
   e(0.0F),
   f(-1.0F),
   g(0.0F),
   h(-1.0F),
   i(-1.0F),
   j(8.0F),
   k(8.0F),
   l(0.0F),
   m(-1.0F),
   n(8.0F),
   o(16.0F),
   p(8.0F),
   q(-1.0F),
   r(0.0F),
   s(-1.0F),
   t(-1.0F),
   u(4.0F),
   v(-1.0F),
   w(8.0F),
   x(0.0F);

   private final float y;

   private PathType(float var2) {
      this.y = var2;
   }

   public float a() {
      return this.y;
   }
}
