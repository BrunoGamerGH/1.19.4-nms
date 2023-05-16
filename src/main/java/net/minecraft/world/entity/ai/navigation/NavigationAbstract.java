package net.minecraft.world.entity.ai.navigation;

import com.google.common.collect.ImmutableSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.core.BaseBlockPosition;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.IPosition;
import net.minecraft.network.protocol.game.PacketDebug;
import net.minecraft.tags.TagsBlock;
import net.minecraft.util.MathHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.level.ChunkCache;
import net.minecraft.world.level.RayTrace;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.pathfinder.PathEntity;
import net.minecraft.world.level.pathfinder.PathPoint;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.pathfinder.Pathfinder;
import net.minecraft.world.level.pathfinder.PathfinderAbstract;
import net.minecraft.world.level.pathfinder.PathfinderNormal;
import net.minecraft.world.phys.MovingObjectPosition;
import net.minecraft.world.phys.Vec3D;

public abstract class NavigationAbstract {
   private static final int p = 20;
   private static final int q = 100;
   private static final float r = 0.25F;
   protected final EntityInsentient a;
   protected final World b;
   @Nullable
   protected PathEntity c;
   protected double d;
   protected int e;
   protected int f;
   protected Vec3D g = Vec3D.b;
   protected BaseBlockPosition h = BaseBlockPosition.g;
   protected long i;
   protected long j;
   protected double k;
   protected float l = 0.5F;
   protected boolean m;
   protected long n;
   protected PathfinderAbstract o;
   @Nullable
   private BlockPosition s;
   private int t;
   private float u = 1.0F;
   private final Pathfinder v;
   private boolean w;

   public NavigationAbstract(EntityInsentient var0, World var1) {
      this.a = var0;
      this.b = var1;
      int var2 = MathHelper.a(var0.b(GenericAttributes.b) * 16.0);
      this.v = this.a(var2);
   }

   public void g() {
      this.u = 1.0F;
   }

   public void a(float var0) {
      this.u = var0;
   }

   @Nullable
   public BlockPosition h() {
      return this.s;
   }

   protected abstract Pathfinder a(int var1);

   public void a(double var0) {
      this.d = var0;
   }

   public void i() {
      if (this.b.U() - this.n > 20L) {
         if (this.s != null) {
            this.c = null;
            this.c = this.a(this.s, this.t);
            this.n = this.b.U();
            this.m = false;
         }
      } else {
         this.m = true;
      }
   }

   @Nullable
   public final PathEntity a(double var0, double var2, double var4, int var6) {
      return this.a(BlockPosition.a(var0, var2, var4), var6);
   }

   @Nullable
   public PathEntity a(Stream<BlockPosition> var0, int var1) {
      return this.a(var0.collect(Collectors.toSet()), 8, false, var1);
   }

   @Nullable
   public PathEntity a(Set<BlockPosition> var0, int var1) {
      return this.a(var0, 8, false, var1);
   }

   @Nullable
   public PathEntity a(BlockPosition var0, int var1) {
      return this.a(ImmutableSet.of(var0), 8, false, var1);
   }

   @Nullable
   public PathEntity a(BlockPosition var0, int var1, int var2) {
      return this.a(ImmutableSet.of(var0), 8, false, var1, (float)var2);
   }

   @Nullable
   public PathEntity a(Entity var0, int var1) {
      return this.a(ImmutableSet.of(var0.dg()), 16, true, var1);
   }

   @Nullable
   protected PathEntity a(Set<BlockPosition> var0, int var1, boolean var2, int var3) {
      return this.a(var0, var1, var2, var3, (float)this.a.b(GenericAttributes.b));
   }

   @Nullable
   protected PathEntity a(Set<BlockPosition> var0, int var1, boolean var2, int var3, float var4) {
      if (var0.isEmpty()) {
         return null;
      } else if (this.a.dn() < (double)this.b.v_()) {
         return null;
      } else if (!this.a()) {
         return null;
      } else if (this.c != null && !this.c.c() && var0.contains(this.s)) {
         return this.c;
      } else {
         this.b.ac().a("pathfind");
         BlockPosition var5 = var2 ? this.a.dg().c() : this.a.dg();
         int var6 = (int)(var4 + (float)var1);
         ChunkCache var7 = new ChunkCache(this.b, var5.b(-var6, -var6, -var6), var5.b(var6, var6, var6));
         PathEntity var8 = this.v.a(var7, this.a, var0, var4, var3, this.u);
         this.b.ac().c();
         if (var8 != null && var8.m() != null) {
            this.s = var8.m();
            this.t = var3;
            this.f();
         }

         return var8;
      }
   }

   public boolean a(double var0, double var2, double var4, double var6) {
      return this.a(this.a(var0, var2, var4, 1), var6);
   }

   public boolean a(Entity var0, double var1) {
      PathEntity var3 = this.a(var0, 1);
      return var3 != null && this.a(var3, var1);
   }

   public boolean a(@Nullable PathEntity var0, double var1) {
      if (var0 == null) {
         this.c = null;
         return false;
      } else {
         if (!var0.a(this.c)) {
            this.c = var0;
         }

         if (this.l()) {
            return false;
         } else {
            this.K_();
            if (this.c.e() <= 0) {
               return false;
            } else {
               this.d = var1;
               Vec3D var3 = this.b();
               this.f = this.e;
               this.g = var3;
               return true;
            }
         }
      }
   }

   @Nullable
   public PathEntity j() {
      return this.c;
   }

   public void c() {
      ++this.e;
      if (this.m) {
         this.i();
      }

      if (!this.l()) {
         if (this.a()) {
            this.k();
         } else if (this.c != null && !this.c.c()) {
            Vec3D var0 = this.b();
            Vec3D var1 = this.c.a(this.a);
            if (var0.d > var1.d && !this.a.ax() && MathHelper.a(var0.c) == MathHelper.a(var1.c) && MathHelper.a(var0.e) == MathHelper.a(var1.e)) {
               this.c.a();
            }
         }

         PacketDebug.a(this.b, this.a, this.c, this.l);
         if (!this.l()) {
            Vec3D var0 = this.c.a(this.a);
            this.a.D().a(var0.c, this.a(var0), var0.e, this.d);
         }
      }
   }

   protected double a(Vec3D var0) {
      BlockPosition var1 = BlockPosition.a(var0);
      return this.b.a_(var1.d()).h() ? var0.d : PathfinderNormal.a(this.b, var1);
   }

   protected void k() {
      Vec3D var0 = this.b();
      this.l = this.a.dc() > 0.75F ? this.a.dc() / 2.0F : 0.75F - this.a.dc() / 2.0F;
      BaseBlockPosition var1 = this.c.g();
      double var2 = Math.abs(this.a.dl() - ((double)var1.u() + 0.5));
      double var4 = Math.abs(this.a.dn() - (double)var1.v());
      double var6 = Math.abs(this.a.dr() - ((double)var1.w() + 0.5));
      boolean var8 = var2 < (double)this.l && var6 < (double)this.l && var4 < 1.0;
      if (var8 || this.b(this.c.h().l) && this.c(var0)) {
         this.c.a();
      }

      this.b(var0);
   }

   private boolean c(Vec3D var0) {
      if (this.c.f() + 1 >= this.c.e()) {
         return false;
      } else {
         Vec3D var1 = Vec3D.c(this.c.g());
         if (!var0.a((IPosition)var1, 2.0)) {
            return false;
         } else if (this.a(var0, this.c.a(this.a))) {
            return true;
         } else {
            Vec3D var2 = Vec3D.c(this.c.d(this.c.f() + 1));
            Vec3D var3 = var1.d(var0);
            Vec3D var4 = var2.d(var0);
            double var5 = var3.g();
            double var7 = var4.g();
            boolean var9 = var7 < var5;
            boolean var10 = var5 < 0.5;
            if (!var9 && !var10) {
               return false;
            } else {
               Vec3D var11 = var3.d();
               Vec3D var12 = var4.d();
               return var12.b(var11) < 0.0;
            }
         }
      }
   }

   protected void b(Vec3D var0) {
      if (this.e - this.f > 100) {
         float var1 = this.a.eW() >= 1.0F ? this.a.eW() : this.a.eW() * this.a.eW();
         float var2 = var1 * 100.0F * 0.25F;
         if (var0.g(this.g) < (double)(var2 * var2)) {
            this.w = true;
            this.n();
         } else {
            this.w = false;
         }

         this.f = this.e;
         this.g = var0;
      }

      if (this.c != null && !this.c.c()) {
         BaseBlockPosition var1 = this.c.g();
         long var2 = this.b.U();
         if (var1.equals(this.h)) {
            this.i += var2 - this.j;
         } else {
            this.h = var1;
            double var4 = var0.f(Vec3D.c(this.h));
            this.k = this.a.eW() > 0.0F ? var4 / (double)this.a.eW() * 20.0 : 0.0;
         }

         if (this.k > 0.0 && (double)this.i > this.k * 3.0) {
            this.e();
         }

         this.j = var2;
      }
   }

   private void e() {
      this.f();
      this.n();
   }

   private void f() {
      this.h = BaseBlockPosition.g;
      this.i = 0L;
      this.k = 0.0;
      this.w = false;
   }

   public boolean l() {
      return this.c == null || this.c.c();
   }

   public boolean m() {
      return !this.l();
   }

   public void n() {
      this.c = null;
   }

   protected abstract Vec3D b();

   protected abstract boolean a();

   protected boolean o() {
      return this.a.aW() || this.a.bg();
   }

   protected void K_() {
      if (this.c != null) {
         for(int var0 = 0; var0 < this.c.e(); ++var0) {
            PathPoint var1 = this.c.a(var0);
            PathPoint var2 = var0 + 1 < this.c.e() ? this.c.a(var0 + 1) : null;
            IBlockData var3 = this.b.a_(new BlockPosition(var1.a, var1.b, var1.c));
            if (var3.a(TagsBlock.bi)) {
               this.c.a(var0, var1.a(var1.a, var1.b + 1, var1.c));
               if (var2 != null && var1.b >= var2.b) {
                  this.c.a(var0 + 1, var1.a(var2.a, var1.b + 1, var2.c));
               }
            }
         }
      }
   }

   protected boolean a(Vec3D var0, Vec3D var1) {
      return false;
   }

   public boolean b(PathType var0) {
      return var0 != PathType.n && var0 != PathType.p && var0 != PathType.d;
   }

   protected static boolean a(EntityInsentient var0, Vec3D var1, Vec3D var2, boolean var3) {
      Vec3D var4 = new Vec3D(var2.c, var2.d + (double)var0.dd() * 0.5, var2.e);
      return var0.H
            .a(new RayTrace(var1, var4, RayTrace.BlockCollisionOption.a, var3 ? RayTrace.FluidCollisionOption.c : RayTrace.FluidCollisionOption.a, var0))
            .c()
         == MovingObjectPosition.EnumMovingObjectType.a;
   }

   public boolean a(BlockPosition var0) {
      BlockPosition var1 = var0.d();
      return this.b.a_(var1).i(this.b, var1);
   }

   public PathfinderAbstract p() {
      return this.o;
   }

   public void a(boolean var0) {
      this.o.c(var0);
   }

   public boolean q() {
      return this.o.f();
   }

   public boolean b(BlockPosition var0) {
      if (this.m) {
         return false;
      } else if (this.c != null && !this.c.c() && this.c.e() != 0) {
         PathPoint var1 = this.c.d();
         Vec3D var2 = new Vec3D(((double)var1.a + this.a.dl()) / 2.0, ((double)var1.b + this.a.dn()) / 2.0, ((double)var1.c + this.a.dr()) / 2.0);
         return var0.a(var2, (double)(this.c.e() - this.c.f()));
      } else {
         return false;
      }
   }

   public float r() {
      return this.l;
   }

   public boolean s() {
      return this.w;
   }
}
