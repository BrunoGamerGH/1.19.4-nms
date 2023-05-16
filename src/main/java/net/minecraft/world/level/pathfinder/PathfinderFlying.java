package net.minecraft.world.level.pathfinder;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import java.util.EnumSet;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.util.MathHelper;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.level.ChunkCache;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.phys.AxisAlignedBB;

public class PathfinderFlying extends PathfinderNormal {
   private final Long2ObjectMap<PathType> m = new Long2ObjectOpenHashMap();
   private static final float n = 1.5F;
   private static final int o = 10;

   @Override
   public void a(ChunkCache var0, EntityInsentient var1) {
      super.a(var0, var1);
      this.m.clear();
      this.l = var1.a(PathType.j);
   }

   @Override
   public void b() {
      this.b.a(PathType.j, this.l);
      this.m.clear();
      super.b();
   }

   @Override
   public PathPoint a() {
      int var0;
      if (this.f() && this.b.aT()) {
         var0 = this.b.dm();
         BlockPosition.MutableBlockPosition var1 = new BlockPosition.MutableBlockPosition(this.b.dl(), (double)var0, this.b.dr());

         for(IBlockData var2 = this.a.a_(var1); var2.a(Blocks.G); var2 = this.a.a_(var1)) {
            var1.b(this.b.dl(), (double)(++var0), this.b.dr());
         }
      } else {
         var0 = MathHelper.a(this.b.dn() + 0.5);
      }

      BlockPosition var1 = BlockPosition.a(this.b.dl(), (double)var0, this.b.dr());
      if (!this.a(var1)) {
         for(BlockPosition var3 : this.a(this.b)) {
            if (this.a(var3)) {
               return super.c(var3);
            }
         }
      }

      return super.c(var1);
   }

   @Override
   protected boolean a(BlockPosition var0) {
      PathType var1 = this.a(this.b, var0);
      return this.b.a(var1) >= 0.0F;
   }

   @Override
   public PathDestination a(double var0, double var2, double var4) {
      return this.a(this.b(MathHelper.a(var0), MathHelper.a(var2), MathHelper.a(var4)));
   }

   @Override
   public int a(PathPoint[] var0, PathPoint var1) {
      int var2 = 0;
      PathPoint var3 = this.a(var1.a, var1.b, var1.c + 1);
      if (this.c(var3)) {
         var0[var2++] = var3;
      }

      PathPoint var4 = this.a(var1.a - 1, var1.b, var1.c);
      if (this.c(var4)) {
         var0[var2++] = var4;
      }

      PathPoint var5 = this.a(var1.a + 1, var1.b, var1.c);
      if (this.c(var5)) {
         var0[var2++] = var5;
      }

      PathPoint var6 = this.a(var1.a, var1.b, var1.c - 1);
      if (this.c(var6)) {
         var0[var2++] = var6;
      }

      PathPoint var7 = this.a(var1.a, var1.b + 1, var1.c);
      if (this.c(var7)) {
         var0[var2++] = var7;
      }

      PathPoint var8 = this.a(var1.a, var1.b - 1, var1.c);
      if (this.c(var8)) {
         var0[var2++] = var8;
      }

      PathPoint var9 = this.a(var1.a, var1.b + 1, var1.c + 1);
      if (this.c(var9) && this.b(var3) && this.b(var7)) {
         var0[var2++] = var9;
      }

      PathPoint var10 = this.a(var1.a - 1, var1.b + 1, var1.c);
      if (this.c(var10) && this.b(var4) && this.b(var7)) {
         var0[var2++] = var10;
      }

      PathPoint var11 = this.a(var1.a + 1, var1.b + 1, var1.c);
      if (this.c(var11) && this.b(var5) && this.b(var7)) {
         var0[var2++] = var11;
      }

      PathPoint var12 = this.a(var1.a, var1.b + 1, var1.c - 1);
      if (this.c(var12) && this.b(var6) && this.b(var7)) {
         var0[var2++] = var12;
      }

      PathPoint var13 = this.a(var1.a, var1.b - 1, var1.c + 1);
      if (this.c(var13) && this.b(var3) && this.b(var8)) {
         var0[var2++] = var13;
      }

      PathPoint var14 = this.a(var1.a - 1, var1.b - 1, var1.c);
      if (this.c(var14) && this.b(var4) && this.b(var8)) {
         var0[var2++] = var14;
      }

      PathPoint var15 = this.a(var1.a + 1, var1.b - 1, var1.c);
      if (this.c(var15) && this.b(var5) && this.b(var8)) {
         var0[var2++] = var15;
      }

      PathPoint var16 = this.a(var1.a, var1.b - 1, var1.c - 1);
      if (this.c(var16) && this.b(var6) && this.b(var8)) {
         var0[var2++] = var16;
      }

      PathPoint var17 = this.a(var1.a + 1, var1.b, var1.c - 1);
      if (this.c(var17) && this.b(var6) && this.b(var5)) {
         var0[var2++] = var17;
      }

      PathPoint var18 = this.a(var1.a + 1, var1.b, var1.c + 1);
      if (this.c(var18) && this.b(var3) && this.b(var5)) {
         var0[var2++] = var18;
      }

      PathPoint var19 = this.a(var1.a - 1, var1.b, var1.c - 1);
      if (this.c(var19) && this.b(var6) && this.b(var4)) {
         var0[var2++] = var19;
      }

      PathPoint var20 = this.a(var1.a - 1, var1.b, var1.c + 1);
      if (this.c(var20) && this.b(var3) && this.b(var4)) {
         var0[var2++] = var20;
      }

      PathPoint var21 = this.a(var1.a + 1, var1.b + 1, var1.c - 1);
      if (this.c(var21) && this.b(var17) && this.b(var6) && this.b(var5) && this.b(var7) && this.b(var12) && this.b(var11)) {
         var0[var2++] = var21;
      }

      PathPoint var22 = this.a(var1.a + 1, var1.b + 1, var1.c + 1);
      if (this.c(var22) && this.b(var18) && this.b(var3) && this.b(var5) && this.b(var7) && this.b(var9) && this.b(var11)) {
         var0[var2++] = var22;
      }

      PathPoint var23 = this.a(var1.a - 1, var1.b + 1, var1.c - 1);
      if (this.c(var23) && this.b(var19) && this.b(var6) && this.b(var4) && this.b(var7) && this.b(var12) && this.b(var10)) {
         var0[var2++] = var23;
      }

      PathPoint var24 = this.a(var1.a - 1, var1.b + 1, var1.c + 1);
      if (this.c(var24) && this.b(var20) && this.b(var3) && this.b(var4) && this.b(var7) && this.b(var9) && this.b(var10)) {
         var0[var2++] = var24;
      }

      PathPoint var25 = this.a(var1.a + 1, var1.b - 1, var1.c - 1);
      if (this.c(var25) && this.b(var17) && this.b(var6) && this.b(var5) && this.b(var8) && this.b(var16) && this.b(var15)) {
         var0[var2++] = var25;
      }

      PathPoint var26 = this.a(var1.a + 1, var1.b - 1, var1.c + 1);
      if (this.c(var26) && this.b(var18) && this.b(var3) && this.b(var5) && this.b(var8) && this.b(var13) && this.b(var15)) {
         var0[var2++] = var26;
      }

      PathPoint var27 = this.a(var1.a - 1, var1.b - 1, var1.c - 1);
      if (this.c(var27) && this.b(var19) && this.b(var6) && this.b(var4) && this.b(var8) && this.b(var16) && this.b(var14)) {
         var0[var2++] = var27;
      }

      PathPoint var28 = this.a(var1.a - 1, var1.b - 1, var1.c + 1);
      if (this.c(var28) && this.b(var20) && this.b(var3) && this.b(var4) && this.b(var8) && this.b(var13) && this.b(var14)) {
         var0[var2++] = var28;
      }

      return var2;
   }

   private boolean b(@Nullable PathPoint var0) {
      return var0 != null && var0.k >= 0.0F;
   }

   private boolean c(@Nullable PathPoint var0) {
      return var0 != null && !var0.i;
   }

   @Nullable
   @Override
   protected PathPoint a(int var0, int var1, int var2) {
      PathPoint var3 = null;
      PathType var4 = this.c(var0, var1, var2);
      float var5 = this.b.a(var4);
      if (var5 >= 0.0F) {
         var3 = this.b(var0, var1, var2);
         var3.l = var4;
         var3.k = Math.max(var3.k, var5);
         if (var4 == PathType.c) {
            ++var3.k;
         }
      }

      return var3;
   }

   private PathType c(int var0, int var1, int var2) {
      return (PathType)this.m.computeIfAbsent(BlockPosition.a(var0, var1, var2), var3x -> this.a(this.a, var0, var1, var2, this.b));
   }

   @Override
   public PathType a(IBlockAccess var0, int var1, int var2, int var3, EntityInsentient var4) {
      EnumSet<PathType> var5 = EnumSet.noneOf(PathType.class);
      PathType var6 = PathType.a;
      BlockPosition var7 = var4.dg();
      var6 = super.a(var0, var1, var2, var3, var5, var6, var7);
      if (var5.contains(PathType.h)) {
         return PathType.h;
      } else {
         PathType var8 = PathType.a;

         for(PathType var10 : var5) {
            if (var4.a(var10) < 0.0F) {
               return var10;
            }

            if (var4.a(var10) >= var4.a(var8)) {
               var8 = var10;
            }
         }

         return var6 == PathType.b && var4.a(var8) == 0.0F ? PathType.b : var8;
      }
   }

   @Override
   public PathType a(IBlockAccess var0, int var1, int var2, int var3) {
      BlockPosition.MutableBlockPosition var4 = new BlockPosition.MutableBlockPosition();
      PathType var5 = b(var0, var4.d(var1, var2, var3));
      if (var5 == PathType.b && var2 >= var0.v_() + 1) {
         PathType var6 = b(var0, var4.d(var1, var2 - 1, var3));
         if (var6 == PathType.o || var6 == PathType.i) {
            var5 = PathType.o;
         } else if (var6 == PathType.q) {
            var5 = PathType.q;
         } else if (var6 == PathType.x) {
            var5 = PathType.x;
         } else if (var6 == PathType.h) {
            if (!var4.equals(this.b.dg())) {
               var5 = PathType.h;
            }
         } else {
            var5 = var6 != PathType.c && var6 != PathType.b && var6 != PathType.j ? PathType.c : PathType.b;
         }
      }

      if (var5 == PathType.c || var5 == PathType.b) {
         var5 = a(var0, var4.d(var1, var2, var3), var5);
      }

      return var5;
   }

   private Iterable<BlockPosition> a(EntityInsentient var0) {
      float var1 = 1.0F;
      AxisAlignedBB var2 = var0.cD();
      boolean var3 = var2.a() < 1.0;
      if (!var3) {
         return List.of(
            BlockPosition.a(var2.a, (double)var0.dm(), var2.c),
            BlockPosition.a(var2.a, (double)var0.dm(), var2.f),
            BlockPosition.a(var2.d, (double)var0.dm(), var2.c),
            BlockPosition.a(var2.d, (double)var0.dm(), var2.f)
         );
      } else {
         double var4 = Math.max(0.0, (1.5 - var2.d()) / 2.0);
         double var6 = Math.max(0.0, (1.5 - var2.b()) / 2.0);
         double var8 = Math.max(0.0, (1.5 - var2.c()) / 2.0);
         AxisAlignedBB var10 = var2.c(var6, var8, var4);
         return BlockPosition.a(
            var0.dZ(),
            10,
            MathHelper.a(var10.a),
            MathHelper.a(var10.b),
            MathHelper.a(var10.c),
            MathHelper.a(var10.d),
            MathHelper.a(var10.e),
            MathHelper.a(var10.f)
         );
      }
   }
}
