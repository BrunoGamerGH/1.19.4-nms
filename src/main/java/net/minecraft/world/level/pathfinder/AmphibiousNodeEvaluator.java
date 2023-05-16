package net.minecraft.world.level.pathfinder;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.util.MathHelper;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.level.ChunkCache;
import net.minecraft.world.level.IBlockAccess;

public class AmphibiousNodeEvaluator extends PathfinderNormal {
   private final boolean m;
   private float n;
   private float o;

   public AmphibiousNodeEvaluator(boolean var0) {
      this.m = var0;
   }

   @Override
   public void a(ChunkCache var0, EntityInsentient var1) {
      super.a(var0, var1);
      var1.a(PathType.j, 0.0F);
      this.n = var1.a(PathType.c);
      var1.a(PathType.c, 6.0F);
      this.o = var1.a(PathType.k);
      var1.a(PathType.k, 4.0F);
   }

   @Override
   public void b() {
      this.b.a(PathType.c, this.n);
      this.b.a(PathType.k, this.o);
      super.b();
   }

   @Override
   public PathPoint a() {
      return !this.b.aT() ? super.a() : this.c(new BlockPosition(MathHelper.a(this.b.cD().a), MathHelper.a(this.b.cD().b + 0.5), MathHelper.a(this.b.cD().c)));
   }

   @Override
   public PathDestination a(double var0, double var2, double var4) {
      return this.a(this.b(MathHelper.a(var0), MathHelper.a(var2 + 0.5), MathHelper.a(var4)));
   }

   @Override
   public int a(PathPoint[] var0, PathPoint var1) {
      int var2 = super.a(var0, var1);
      PathType var4 = this.a(this.b, var1.a, var1.b + 1, var1.c);
      PathType var5 = this.a(this.b, var1.a, var1.b, var1.c);
      int var3;
      if (this.b.a(var4) >= 0.0F && var5 != PathType.w) {
         var3 = MathHelper.d(Math.max(1.0F, this.b.dA()));
      } else {
         var3 = 0;
      }

      double var6 = this.d(new BlockPosition(var1.a, var1.b, var1.c));
      PathPoint var8 = this.a(var1.a, var1.b + 1, var1.c, Math.max(0, var3 - 1), var6, EnumDirection.b, var5);
      PathPoint var9 = this.a(var1.a, var1.b - 1, var1.c, var3, var6, EnumDirection.a, var5);
      if (this.b(var8, var1)) {
         var0[var2++] = var8;
      }

      if (this.b(var9, var1) && var5 != PathType.e) {
         var0[var2++] = var9;
      }

      for(int var10 = 0; var10 < var2; ++var10) {
         PathPoint var11 = var0[var10];
         if (var11.l == PathType.j && this.m && var11.b < this.b.H.m_() - 10) {
            ++var11.k;
         }
      }

      return var2;
   }

   private boolean b(@Nullable PathPoint var0, PathPoint var1) {
      return this.a(var0, var1) && var0.l == PathType.j;
   }

   @Override
   protected boolean c() {
      return true;
   }

   @Override
   public PathType a(IBlockAccess var0, int var1, int var2, int var3) {
      BlockPosition.MutableBlockPosition var4 = new BlockPosition.MutableBlockPosition();
      PathType var5 = b(var0, var4.d(var1, var2, var3));
      if (var5 == PathType.j) {
         for(EnumDirection var9 : EnumDirection.values()) {
            PathType var10 = b(var0, var4.d(var1, var2, var3).c(var9));
            if (var10 == PathType.a) {
               return PathType.k;
            }
         }

         return PathType.j;
      } else {
         return a(var0, var4);
      }
   }
}
