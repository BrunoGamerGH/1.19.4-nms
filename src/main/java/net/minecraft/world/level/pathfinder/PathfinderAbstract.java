package net.minecraft.world.level.pathfinder;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.core.BlockPosition;
import net.minecraft.util.MathHelper;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.level.ChunkCache;
import net.minecraft.world.level.IBlockAccess;

public abstract class PathfinderAbstract {
   protected ChunkCache a;
   protected EntityInsentient b;
   protected final Int2ObjectMap<PathPoint> c = new Int2ObjectOpenHashMap();
   protected int d;
   protected int e;
   protected int f;
   protected boolean g;
   protected boolean h;
   protected boolean i;
   protected boolean j;

   public void a(ChunkCache var0, EntityInsentient var1) {
      this.a = var0;
      this.b = var1;
      this.c.clear();
      this.d = MathHelper.d(var1.dc() + 1.0F);
      this.e = MathHelper.d(var1.dd() + 1.0F);
      this.f = MathHelper.d(var1.dc() + 1.0F);
   }

   public void b() {
      this.a = null;
      this.b = null;
   }

   protected PathPoint b(BlockPosition var0) {
      return this.b(var0.u(), var0.v(), var0.w());
   }

   protected PathPoint b(int var0, int var1, int var2) {
      return (PathPoint)this.c.computeIfAbsent(PathPoint.b(var0, var1, var2), var3x -> new PathPoint(var0, var1, var2));
   }

   public abstract PathPoint a();

   public abstract PathDestination a(double var1, double var3, double var5);

   protected PathDestination a(PathPoint var0) {
      return new PathDestination(var0);
   }

   public abstract int a(PathPoint[] var1, PathPoint var2);

   public abstract PathType a(IBlockAccess var1, int var2, int var3, int var4, EntityInsentient var5);

   public abstract PathType a(IBlockAccess var1, int var2, int var3, int var4);

   public void a(boolean var0) {
      this.g = var0;
   }

   public void b(boolean var0) {
      this.h = var0;
   }

   public void c(boolean var0) {
      this.i = var0;
   }

   public void d(boolean var0) {
      this.j = var0;
   }

   public boolean d() {
      return this.g;
   }

   public boolean e() {
      return this.h;
   }

   public boolean f() {
      return this.i;
   }

   public boolean g() {
      return this.j;
   }
}
