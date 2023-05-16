package net.minecraft.world.level.pathfinder;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import java.util.EnumSet;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.tags.TagsBlock;
import net.minecraft.tags.TagsFluid;
import net.minecraft.util.MathHelper;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.level.ChunkCache;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BlockCampfire;
import net.minecraft.world.level.block.BlockDoor;
import net.minecraft.world.level.block.BlockFenceGate;
import net.minecraft.world.level.block.BlockLeaves;
import net.minecraft.world.level.block.BlockMinecartTrackAbstract;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidTypes;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.AxisAlignedBB;
import net.minecraft.world.phys.Vec3D;
import net.minecraft.world.phys.shapes.VoxelShape;

public class PathfinderNormal extends PathfinderAbstract {
   public static final double k = 0.5;
   private static final double m = 1.125;
   protected float l;
   private final Long2ObjectMap<PathType> n = new Long2ObjectOpenHashMap();
   private final Object2BooleanMap<AxisAlignedBB> o = new Object2BooleanOpenHashMap();

   @Override
   public void a(ChunkCache var0, EntityInsentient var1) {
      super.a(var0, var1);
      this.l = var1.a(PathType.j);
   }

   @Override
   public void b() {
      this.b.a(PathType.j, this.l);
      this.n.clear();
      this.o.clear();
      super.b();
   }

   @Override
   public PathPoint a() {
      BlockPosition.MutableBlockPosition var1 = new BlockPosition.MutableBlockPosition();
      int var0 = this.b.dm();
      IBlockData var2 = this.a.a_(var1.b(this.b.dl(), (double)var0, this.b.dr()));
      if (!this.b.a(var2.r())) {
         if (this.f() && this.b.aT()) {
            while(true) {
               if (!var2.a(Blocks.G) && var2.r() != FluidTypes.c.a(false)) {
                  --var0;
                  break;
               }

               var2 = this.a.a_(var1.b(this.b.dl(), (double)(++var0), this.b.dr()));
            }
         } else if (this.b.ax()) {
            var0 = MathHelper.a(this.b.dn() + 0.5);
         } else {
            BlockPosition var3 = this.b.dg();

            while((this.a.a_(var3).h() || this.a.a_(var3).a(this.a, var3, PathMode.a)) && var3.v() > this.b.H.v_()) {
               var3 = var3.d();
            }

            var0 = var3.c().v();
         }
      } else {
         while(this.b.a(var2.r())) {
            var2 = this.a.a_(var1.b(this.b.dl(), (double)(++var0), this.b.dr()));
         }

         --var0;
      }

      BlockPosition var3 = this.b.dg();
      if (!this.a(var1.d(var3.u(), var0, var3.w()))) {
         AxisAlignedBB var4 = this.b.cD();
         if (this.a(var1.b(var4.a, (double)var0, var4.c))
            || this.a(var1.b(var4.a, (double)var0, var4.f))
            || this.a(var1.b(var4.d, (double)var0, var4.c))
            || this.a(var1.b(var4.d, (double)var0, var4.f))) {
            return this.c(var1);
         }
      }

      return this.c(new BlockPosition(var3.u(), var0, var3.w()));
   }

   protected PathPoint c(BlockPosition var0) {
      PathPoint var1 = this.b(var0);
      var1.l = this.a(this.b, var1.a());
      var1.k = this.b.a(var1.l);
      return var1;
   }

   protected boolean a(BlockPosition var0) {
      PathType var1 = this.a(this.b, var0);
      return var1 != PathType.b && this.b.a(var1) >= 0.0F;
   }

   @Override
   public PathDestination a(double var0, double var2, double var4) {
      return this.a(this.b(MathHelper.a(var0), MathHelper.a(var2), MathHelper.a(var4)));
   }

   @Override
   public int a(PathPoint[] var0, PathPoint var1) {
      int var2 = 0;
      int var3 = 0;
      PathType var4 = this.a(this.b, var1.a, var1.b + 1, var1.c);
      PathType var5 = this.a(this.b, var1.a, var1.b, var1.c);
      if (this.b.a(var4) >= 0.0F && var5 != PathType.w) {
         var3 = MathHelper.d(Math.max(1.0F, this.b.dA()));
      }

      double var6 = this.d(new BlockPosition(var1.a, var1.b, var1.c));
      PathPoint var8 = this.a(var1.a, var1.b, var1.c + 1, var3, var6, EnumDirection.d, var5);
      if (this.a(var8, var1)) {
         var0[var2++] = var8;
      }

      PathPoint var9 = this.a(var1.a - 1, var1.b, var1.c, var3, var6, EnumDirection.e, var5);
      if (this.a(var9, var1)) {
         var0[var2++] = var9;
      }

      PathPoint var10 = this.a(var1.a + 1, var1.b, var1.c, var3, var6, EnumDirection.f, var5);
      if (this.a(var10, var1)) {
         var0[var2++] = var10;
      }

      PathPoint var11 = this.a(var1.a, var1.b, var1.c - 1, var3, var6, EnumDirection.c, var5);
      if (this.a(var11, var1)) {
         var0[var2++] = var11;
      }

      PathPoint var12 = this.a(var1.a - 1, var1.b, var1.c - 1, var3, var6, EnumDirection.c, var5);
      if (this.a(var1, var9, var11, var12)) {
         var0[var2++] = var12;
      }

      PathPoint var13 = this.a(var1.a + 1, var1.b, var1.c - 1, var3, var6, EnumDirection.c, var5);
      if (this.a(var1, var10, var11, var13)) {
         var0[var2++] = var13;
      }

      PathPoint var14 = this.a(var1.a - 1, var1.b, var1.c + 1, var3, var6, EnumDirection.d, var5);
      if (this.a(var1, var9, var8, var14)) {
         var0[var2++] = var14;
      }

      PathPoint var15 = this.a(var1.a + 1, var1.b, var1.c + 1, var3, var6, EnumDirection.d, var5);
      if (this.a(var1, var10, var8, var15)) {
         var0[var2++] = var15;
      }

      return var2;
   }

   protected boolean a(@Nullable PathPoint var0, PathPoint var1) {
      return var0 != null && !var0.i && (var0.k >= 0.0F || var1.k < 0.0F);
   }

   protected boolean a(PathPoint var0, @Nullable PathPoint var1, @Nullable PathPoint var2, @Nullable PathPoint var3) {
      if (var3 == null || var2 == null || var1 == null) {
         return false;
      } else if (var3.i) {
         return false;
      } else if (var2.b > var0.b || var1.b > var0.b) {
         return false;
      } else if (var1.l != PathType.d && var2.l != PathType.d && var3.l != PathType.d) {
         boolean var4 = var2.l == PathType.h && var1.l == PathType.h && (double)this.b.dc() < 0.5;
         return var3.k >= 0.0F && (var2.b < var0.b || var2.k >= 0.0F || var4) && (var1.b < var0.b || var1.k >= 0.0F || var4);
      } else {
         return false;
      }
   }

   private static boolean a(PathType var0) {
      return var0 == PathType.h || var0 == PathType.s || var0 == PathType.t;
   }

   private boolean b(PathPoint var0) {
      AxisAlignedBB var1 = this.b.cD();
      Vec3D var2 = new Vec3D(
         (double)var0.a - this.b.dl() + var1.b() / 2.0, (double)var0.b - this.b.dn() + var1.c() / 2.0, (double)var0.c - this.b.dr() + var1.d() / 2.0
      );
      int var3 = MathHelper.c(var2.f() / var1.a());
      var2 = var2.a((double)(1.0F / (float)var3));

      for(int var4 = 1; var4 <= var3; ++var4) {
         var1 = var1.c(var2);
         if (this.a(var1)) {
            return false;
         }
      }

      return true;
   }

   protected double d(BlockPosition var0) {
      return (this.f() || this.c()) && this.a.b_(var0).a(TagsFluid.a) ? (double)var0.v() + 0.5 : a(this.a, var0);
   }

   public static double a(IBlockAccess var0, BlockPosition var1) {
      BlockPosition var2 = var1.d();
      VoxelShape var3 = var0.a_(var2).k(var0, var2);
      return (double)var2.v() + (var3.b() ? 0.0 : var3.c(EnumDirection.EnumAxis.b));
   }

   protected boolean c() {
      return false;
   }

   @Nullable
   protected PathPoint a(int var0, int var1, int var2, int var3, double var4, EnumDirection var6, PathType var7) {
      PathPoint var8 = null;
      BlockPosition.MutableBlockPosition var9 = new BlockPosition.MutableBlockPosition();
      double var10 = this.d(var9.d(var0, var1, var2));
      if (var10 - var4 > this.h()) {
         return null;
      } else {
         PathType var12 = this.a(this.b, var0, var1, var2);
         float var13 = this.b.a(var12);
         double var14 = (double)this.b.dc() / 2.0;
         if (var13 >= 0.0F) {
            var8 = this.a(var0, var1, var2, var12, var13);
         }

         if (a(var7) && var8 != null && var8.k >= 0.0F && !this.b(var8)) {
            var8 = null;
         }

         if (var12 != PathType.c && (!this.c() || var12 != PathType.j)) {
            if ((var8 == null || var8.k < 0.0F)
               && var3 > 0
               && (var12 != PathType.h || this.g())
               && var12 != PathType.m
               && var12 != PathType.e
               && var12 != PathType.f) {
               var8 = this.a(var0, var1 + 1, var2, var3 - 1, var4, var6, var7);
               if (var8 != null && (var8.l == PathType.b || var8.l == PathType.c) && this.b.dc() < 1.0F) {
                  double var16 = (double)(var0 - var6.j()) + 0.5;
                  double var18 = (double)(var2 - var6.l()) + 0.5;
                  AxisAlignedBB var20 = new AxisAlignedBB(
                     var16 - var14,
                     this.d(var9.b(var16, (double)(var1 + 1), var18)) + 0.001,
                     var18 - var14,
                     var16 + var14,
                     (double)this.b.dd() + this.d(var9.b((double)var8.a, (double)var8.b, (double)var8.c)) - 0.002,
                     var18 + var14
                  );
                  if (this.a(var20)) {
                     var8 = null;
                  }
               }
            }

            if (!this.c() && var12 == PathType.j && !this.f()) {
               if (this.a(this.b, var0, var1 - 1, var2) != PathType.j) {
                  return var8;
               }

               while(var1 > this.b.H.v_()) {
                  var12 = this.a(this.b, var0, --var1, var2);
                  if (var12 != PathType.j) {
                     return var8;
                  }

                  var8 = this.a(var0, var1, var2, var12, this.b.a(var12));
               }
            }

            if (var12 == PathType.b) {
               int var16 = 0;
               int var17 = var1;

               while(var12 == PathType.b) {
                  if (--var1 < this.b.H.v_()) {
                     return this.a(var0, var17, var2);
                  }

                  if (var16++ >= this.b.cp()) {
                     return this.a(var0, var1, var2);
                  }

                  var12 = this.a(this.b, var0, var1, var2);
                  var13 = this.b.a(var12);
                  if (var12 != PathType.b && var13 >= 0.0F) {
                     var8 = this.a(var0, var1, var2, var12, var13);
                     break;
                  }

                  if (var13 < 0.0F) {
                     return this.a(var0, var1, var2);
                  }
               }
            }

            if (a(var12) && var8 == null) {
               var8 = this.b(var0, var1, var2);
               var8.i = true;
               var8.l = var12;
               var8.k = var12.a();
            }

            return var8;
         } else {
            return var8;
         }
      }
   }

   private double h() {
      return Math.max(1.125, (double)this.b.dA());
   }

   private PathPoint a(int var0, int var1, int var2, PathType var3, float var4) {
      PathPoint var5 = this.b(var0, var1, var2);
      var5.l = var3;
      var5.k = Math.max(var5.k, var4);
      return var5;
   }

   private PathPoint a(int var0, int var1, int var2) {
      PathPoint var3 = this.b(var0, var1, var2);
      var3.l = PathType.a;
      var3.k = -1.0F;
      return var3;
   }

   private boolean a(AxisAlignedBB var0) {
      return this.o.computeIfAbsent(var0, var1x -> !this.a.a(this.b, var0));
   }

   @Override
   public PathType a(IBlockAccess var0, int var1, int var2, int var3, EntityInsentient var4) {
      EnumSet<PathType> var5 = EnumSet.noneOf(PathType.class);
      PathType var6 = PathType.a;
      var6 = this.a(var0, var1, var2, var3, var5, var6, var4.dg());
      if (var5.contains(PathType.h)) {
         return PathType.h;
      } else if (var5.contains(PathType.m)) {
         return PathType.m;
      } else {
         PathType var7 = PathType.a;

         for(PathType var9 : var5) {
            if (var4.a(var9) < 0.0F) {
               return var9;
            }

            if (var4.a(var9) >= var4.a(var7)) {
               var7 = var9;
            }
         }

         return var6 == PathType.b && var4.a(var7) == 0.0F && this.d <= 1 ? PathType.b : var7;
      }
   }

   public PathType a(IBlockAccess var0, int var1, int var2, int var3, EnumSet<PathType> var4, PathType var5, BlockPosition var6) {
      for(int var7 = 0; var7 < this.d; ++var7) {
         for(int var8 = 0; var8 < this.e; ++var8) {
            for(int var9 = 0; var9 < this.f; ++var9) {
               int var10 = var7 + var1;
               int var11 = var8 + var2;
               int var12 = var9 + var3;
               PathType var13 = this.a(var0, var10, var11, var12);
               var13 = this.a(var0, var6, var13);
               if (var7 == 0 && var8 == 0 && var9 == 0) {
                  var5 = var13;
               }

               var4.add(var13);
            }
         }
      }

      return var5;
   }

   protected PathType a(IBlockAccess var0, BlockPosition var1, PathType var2) {
      boolean var3 = this.d();
      if (var2 == PathType.s && this.e() && var3) {
         var2 = PathType.d;
      }

      if (var2 == PathType.r && !var3) {
         var2 = PathType.a;
      }

      if (var2 == PathType.l && !(var0.a_(var1).b() instanceof BlockMinecartTrackAbstract) && !(var0.a_(var1.d()).b() instanceof BlockMinecartTrackAbstract)) {
         var2 = PathType.m;
      }

      return var2;
   }

   protected PathType a(EntityInsentient var0, BlockPosition var1) {
      return this.a(var0, var1.u(), var1.v(), var1.w());
   }

   protected PathType a(EntityInsentient var0, int var1, int var2, int var3) {
      return (PathType)this.n.computeIfAbsent(BlockPosition.a(var1, var2, var3), var4x -> this.a(this.a, var1, var2, var3, var0));
   }

   @Override
   public PathType a(IBlockAccess var0, int var1, int var2, int var3) {
      return a(var0, new BlockPosition.MutableBlockPosition(var1, var2, var3));
   }

   public static PathType a(IBlockAccess var0, BlockPosition.MutableBlockPosition var1) {
      int var2 = var1.u();
      int var3 = var1.v();
      int var4 = var1.w();
      PathType var5 = b(var0, var1);
      if (var5 == PathType.b && var3 >= var0.v_() + 1) {
         PathType var6 = b(var0, var1.d(var2, var3 - 1, var4));
         var5 = var6 != PathType.c && var6 != PathType.b && var6 != PathType.j && var6 != PathType.i ? PathType.c : PathType.b;
         if (var6 == PathType.o) {
            var5 = PathType.o;
         }

         if (var6 == PathType.q) {
            var5 = PathType.q;
         }

         if (var6 == PathType.w) {
            var5 = PathType.w;
         }

         if (var6 == PathType.f) {
            var5 = PathType.g;
         }
      }

      if (var5 == PathType.c) {
         var5 = a(var0, var1.d(var2, var3, var4), var5);
      }

      return var5;
   }

   public static PathType a(IBlockAccess var0, BlockPosition.MutableBlockPosition var1, PathType var2) {
      int var3 = var1.u();
      int var4 = var1.v();
      int var5 = var1.w();

      for(int var6 = -1; var6 <= 1; ++var6) {
         for(int var7 = -1; var7 <= 1; ++var7) {
            for(int var8 = -1; var8 <= 1; ++var8) {
               if (var6 != 0 || var8 != 0) {
                  var1.d(var3 + var6, var4 + var7, var5 + var8);
                  IBlockData var9 = var0.a_(var1);
                  if (var9.a(Blocks.dP) || var9.a(Blocks.oe)) {
                     return PathType.p;
                  }

                  if (a(var9)) {
                     return PathType.n;
                  }

                  if (var0.b_(var1).a(TagsFluid.a)) {
                     return PathType.k;
                  }
               }
            }
         }
      }

      return var2;
   }

   protected static PathType b(IBlockAccess var0, BlockPosition var1) {
      IBlockData var2 = var0.a_(var1);
      Block var3 = var2.b();
      Material var4 = var2.d();
      if (var2.h()) {
         return PathType.b;
      } else if (var2.a(TagsBlock.O) || var2.a(Blocks.fl) || var2.a(Blocks.rx)) {
         return PathType.e;
      } else if (var2.a(Blocks.qy)) {
         return PathType.f;
      } else if (var2.a(Blocks.dP) || var2.a(Blocks.oe)) {
         return PathType.q;
      } else if (var2.a(Blocks.pc)) {
         return PathType.w;
      } else if (var2.a(Blocks.fB)) {
         return PathType.x;
      } else {
         Fluid var5 = var0.b_(var1);
         if (var5.a(TagsFluid.b)) {
            return PathType.i;
         } else if (a(var2)) {
            return PathType.o;
         } else if (BlockDoor.n(var2) && !var2.c(BlockDoor.b)) {
            return PathType.s;
         } else if (var3 instanceof BlockDoor && var4 == Material.K && !var2.c(BlockDoor.b)) {
            return PathType.t;
         } else if (var3 instanceof BlockDoor && var2.c(BlockDoor.b)) {
            return PathType.r;
         } else if (var3 instanceof BlockMinecartTrackAbstract) {
            return PathType.l;
         } else if (var3 instanceof BlockLeaves) {
            return PathType.v;
         } else if (!var2.a(TagsBlock.R) && !var2.a(TagsBlock.K) && (!(var3 instanceof BlockFenceGate) || var2.c(BlockFenceGate.a))) {
            if (!var2.a(var0, var1, PathMode.a)) {
               return PathType.a;
            } else {
               return var5.a(TagsFluid.a) ? PathType.j : PathType.b;
            }
         } else {
            return PathType.h;
         }
      }
   }

   public static boolean a(IBlockData var0) {
      return var0.a(TagsBlock.aH) || var0.a(Blocks.H) || var0.a(Blocks.kG) || BlockCampfire.g(var0) || var0.a(Blocks.fu);
   }
}
