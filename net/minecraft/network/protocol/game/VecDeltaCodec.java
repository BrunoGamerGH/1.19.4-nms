package net.minecraft.network.protocol.game;

import net.minecraft.world.phys.Vec3D;
import org.jetbrains.annotations.VisibleForTesting;

public class VecDeltaCodec {
   private static final double a = 4096.0;
   private Vec3D b;

   public VecDeltaCodec() {
      this.b = Vec3D.b;
   }

   @VisibleForTesting
   static long a(double var0) {
      return Math.round(var0 * 4096.0);
   }

   @VisibleForTesting
   static double a(long var0) {
      return (double)var0 / 4096.0;
   }

   public Vec3D a(long var0, long var2, long var4) {
      if (var0 == 0L && var2 == 0L && var4 == 0L) {
         return this.b;
      } else {
         double var6 = var0 == 0L ? this.b.c : a(a(this.b.c) + var0);
         double var8 = var2 == 0L ? this.b.d : a(a(this.b.d) + var2);
         double var10 = var4 == 0L ? this.b.e : a(a(this.b.e) + var4);
         return new Vec3D(var6, var8, var10);
      }
   }

   public long a(Vec3D var0) {
      return a(var0.c) - a(this.b.c);
   }

   public long b(Vec3D var0) {
      return a(var0.d) - a(this.b.d);
   }

   public long c(Vec3D var0) {
      return a(var0.e) - a(this.b.e);
   }

   public Vec3D d(Vec3D var0) {
      return var0.d(this.b);
   }

   public void e(Vec3D var0) {
      this.b = var0;
   }
}
