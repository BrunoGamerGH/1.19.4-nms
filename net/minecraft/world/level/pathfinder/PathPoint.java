package net.minecraft.world.level.pathfinder;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.phys.Vec3D;

public class PathPoint {
   public final int a;
   public final int b;
   public final int c;
   private final int m;
   public int d = -1;
   public float e;
   public float f;
   public float g;
   @Nullable
   public PathPoint h;
   public boolean i;
   public float j;
   public float k;
   public PathType l = PathType.a;

   public PathPoint(int var0, int var1, int var2) {
      this.a = var0;
      this.b = var1;
      this.c = var2;
      this.m = b(var0, var1, var2);
   }

   public PathPoint a(int var0, int var1, int var2) {
      PathPoint var3 = new PathPoint(var0, var1, var2);
      var3.d = this.d;
      var3.e = this.e;
      var3.f = this.f;
      var3.g = this.g;
      var3.h = this.h;
      var3.i = this.i;
      var3.j = this.j;
      var3.k = this.k;
      var3.l = this.l;
      return var3;
   }

   public static int b(int var0, int var1, int var2) {
      return var1 & 0xFF | (var0 & 32767) << 8 | (var2 & 32767) << 24 | (var0 < 0 ? Integer.MIN_VALUE : 0) | (var2 < 0 ? 32768 : 0);
   }

   public float a(PathPoint var0) {
      float var1 = (float)(var0.a - this.a);
      float var2 = (float)(var0.b - this.b);
      float var3 = (float)(var0.c - this.c);
      return MathHelper.c(var1 * var1 + var2 * var2 + var3 * var3);
   }

   public float b(PathPoint var0) {
      float var1 = (float)(var0.a - this.a);
      float var2 = (float)(var0.c - this.c);
      return MathHelper.c(var1 * var1 + var2 * var2);
   }

   public float a(BlockPosition var0) {
      float var1 = (float)(var0.u() - this.a);
      float var2 = (float)(var0.v() - this.b);
      float var3 = (float)(var0.w() - this.c);
      return MathHelper.c(var1 * var1 + var2 * var2 + var3 * var3);
   }

   public float c(PathPoint var0) {
      float var1 = (float)(var0.a - this.a);
      float var2 = (float)(var0.b - this.b);
      float var3 = (float)(var0.c - this.c);
      return var1 * var1 + var2 * var2 + var3 * var3;
   }

   public float b(BlockPosition var0) {
      float var1 = (float)(var0.u() - this.a);
      float var2 = (float)(var0.v() - this.b);
      float var3 = (float)(var0.w() - this.c);
      return var1 * var1 + var2 * var2 + var3 * var3;
   }

   public float d(PathPoint var0) {
      float var1 = (float)Math.abs(var0.a - this.a);
      float var2 = (float)Math.abs(var0.b - this.b);
      float var3 = (float)Math.abs(var0.c - this.c);
      return var1 + var2 + var3;
   }

   public float c(BlockPosition var0) {
      float var1 = (float)Math.abs(var0.u() - this.a);
      float var2 = (float)Math.abs(var0.v() - this.b);
      float var3 = (float)Math.abs(var0.w() - this.c);
      return var1 + var2 + var3;
   }

   public BlockPosition a() {
      return new BlockPosition(this.a, this.b, this.c);
   }

   public Vec3D b() {
      return new Vec3D((double)this.a, (double)this.b, (double)this.c);
   }

   @Override
   public boolean equals(Object var0) {
      if (!(var0 instanceof PathPoint)) {
         return false;
      } else {
         PathPoint var1 = (PathPoint)var0;
         return this.m == var1.m && this.a == var1.a && this.b == var1.b && this.c == var1.c;
      }
   }

   @Override
   public int hashCode() {
      return this.m;
   }

   public boolean c() {
      return this.d >= 0;
   }

   @Override
   public String toString() {
      return "Node{x=" + this.a + ", y=" + this.b + ", z=" + this.c + "}";
   }

   public void a(PacketDataSerializer var0) {
      var0.writeInt(this.a);
      var0.writeInt(this.b);
      var0.writeInt(this.c);
      var0.writeFloat(this.j);
      var0.writeFloat(this.k);
      var0.writeBoolean(this.i);
      var0.a(this.l);
      var0.writeFloat(this.g);
   }

   public static PathPoint b(PacketDataSerializer var0) {
      PathPoint var1 = new PathPoint(var0.readInt(), var0.readInt(), var0.readInt());
      a(var0, var1);
      return var1;
   }

   protected static void a(PacketDataSerializer var0, PathPoint var1) {
      var1.j = var0.readFloat();
      var1.k = var0.readFloat();
      var1.i = var0.readBoolean();
      var1.l = var0.b(PathType.class);
      var1.g = var0.readFloat();
   }
}
