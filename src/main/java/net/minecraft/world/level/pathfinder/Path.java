package net.minecraft.world.level.pathfinder;

public class Path {
   private PathPoint[] a = new PathPoint[128];
   private int b;

   public PathPoint a(PathPoint var0) {
      if (var0.d >= 0) {
         throw new IllegalStateException("OW KNOWS!");
      } else {
         if (this.b == this.a.length) {
            PathPoint[] var1 = new PathPoint[this.b << 1];
            System.arraycopy(this.a, 0, var1, 0, this.b);
            this.a = var1;
         }

         this.a[this.b] = var0;
         var0.d = this.b;
         this.a(this.b++);
         return var0;
      }
   }

   public void a() {
      this.b = 0;
   }

   public PathPoint b() {
      return this.a[0];
   }

   public PathPoint c() {
      PathPoint var0 = this.a[0];
      this.a[0] = this.a[--this.b];
      this.a[this.b] = null;
      if (this.b > 0) {
         this.b(0);
      }

      var0.d = -1;
      return var0;
   }

   public void b(PathPoint var0) {
      this.a[var0.d] = this.a[--this.b];
      this.a[this.b] = null;
      if (this.b > var0.d) {
         if (this.a[var0.d].g < var0.g) {
            this.a(var0.d);
         } else {
            this.b(var0.d);
         }
      }

      var0.d = -1;
   }

   public void a(PathPoint var0, float var1) {
      float var2 = var0.g;
      var0.g = var1;
      if (var1 < var2) {
         this.a(var0.d);
      } else {
         this.b(var0.d);
      }
   }

   public int d() {
      return this.b;
   }

   private void a(int var0) {
      PathPoint var1 = this.a[var0];

      int var3;
      for(float var2 = var1.g; var0 > 0; var0 = var3) {
         var3 = var0 - 1 >> 1;
         PathPoint var4 = this.a[var3];
         if (!(var2 < var4.g)) {
            break;
         }

         this.a[var0] = var4;
         var4.d = var0;
      }

      this.a[var0] = var1;
      var1.d = var0;
   }

   private void b(int var0) {
      PathPoint var1 = this.a[var0];
      float var2 = var1.g;

      while(true) {
         int var3 = 1 + (var0 << 1);
         int var4 = var3 + 1;
         if (var3 >= this.b) {
            break;
         }

         PathPoint var5 = this.a[var3];
         float var6 = var5.g;
         PathPoint var7;
         float var8;
         if (var4 >= this.b) {
            var7 = null;
            var8 = Float.POSITIVE_INFINITY;
         } else {
            var7 = this.a[var4];
            var8 = var7.g;
         }

         if (var6 < var8) {
            if (!(var6 < var2)) {
               break;
            }

            this.a[var0] = var5;
            var5.d = var0;
            var0 = var3;
         } else {
            if (!(var8 < var2)) {
               break;
            }

            this.a[var0] = var7;
            var7.d = var0;
            var0 = var4;
         }
      }

      this.a[var0] = var1;
      var1.d = var0;
   }

   public boolean e() {
      return this.b == 0;
   }

   public PathPoint[] f() {
      PathPoint[] var0 = new PathPoint[this.d()];
      System.arraycopy(this.a, 0, var0, 0, this.d());
      return var0;
   }
}
