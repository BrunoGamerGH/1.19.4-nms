package net.minecraft.world.level.pathfinder;

import net.minecraft.network.PacketDataSerializer;

public class PathDestination extends PathPoint {
   private float m = Float.MAX_VALUE;
   private PathPoint n;
   private boolean o;

   public PathDestination(PathPoint var0) {
      super(var0.a, var0.b, var0.c);
   }

   public PathDestination(int var0, int var1, int var2) {
      super(var0, var1, var2);
   }

   public void a(float var0, PathPoint var1) {
      if (var0 < this.m) {
         this.m = var0;
         this.n = var1;
      }
   }

   public PathPoint d() {
      return this.n;
   }

   public void e() {
      this.o = true;
   }

   public boolean f() {
      return this.o;
   }

   public static PathDestination c(PacketDataSerializer var0) {
      PathDestination var1 = new PathDestination(var0.readInt(), var0.readInt(), var0.readInt());
      a(var0, var1);
      return var1;
   }
}
