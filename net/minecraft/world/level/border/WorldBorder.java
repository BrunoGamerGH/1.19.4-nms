package net.minecraft.world.level.border;

import com.google.common.collect.Lists;
import com.mojang.serialization.DynamicLike;
import java.util.List;
import net.minecraft.SystemUtils;
import net.minecraft.core.BlockPosition;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.phys.AxisAlignedBB;
import net.minecraft.world.phys.shapes.OperatorBoolean;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapes;

public class WorldBorder {
   public static final double c = 5.999997E7F;
   public static final double d = 2.9999984E7;
   private final List<IWorldBorderListener> a = Lists.newArrayList();
   private double b = 0.2;
   private double f = 5.0;
   private int g = 15;
   private int h = 5;
   private double i;
   private double j;
   int k = 29999984;
   private WorldBorder.a l = new WorldBorder.d(5.999997E7F);
   public static final WorldBorder.c e = new WorldBorder.c(0.0, 0.0, 0.2, 5.0, 5, 15, 5.999997E7F, 0L, 0.0);
   public WorldServer world;

   public boolean a(BlockPosition blockposition) {
      return (double)(blockposition.u() + 1) > this.e()
         && (double)blockposition.u() < this.g()
         && (double)(blockposition.w() + 1) > this.f()
         && (double)blockposition.w() < this.h();
   }

   public boolean a(ChunkCoordIntPair chunkcoordintpair) {
      return (double)chunkcoordintpair.f() > this.e()
         && (double)chunkcoordintpair.d() < this.g()
         && (double)chunkcoordintpair.g() > this.f()
         && (double)chunkcoordintpair.e() < this.h();
   }

   public boolean a(double d0, double d1) {
      return d0 > this.e() && d0 < this.g() && d1 > this.f() && d1 < this.h();
   }

   public boolean a(double d0, double d1, double d2) {
      return d0 > this.e() - d2 && d0 < this.g() + d2 && d1 > this.f() - d2 && d1 < this.h() + d2;
   }

   public boolean a(AxisAlignedBB axisalignedbb) {
      return axisalignedbb.d > this.e() && axisalignedbb.a < this.g() && axisalignedbb.f > this.f() && axisalignedbb.c < this.h();
   }

   public BlockPosition b(double d0, double d1, double d2) {
      return BlockPosition.a(MathHelper.a(d0, this.e(), this.g()), d1, MathHelper.a(d2, this.f(), this.h()));
   }

   public double a(Entity entity) {
      return this.b(entity.dl(), entity.dr());
   }

   public VoxelShape c() {
      return this.l.m();
   }

   public double b(double d0, double d1) {
      double d2 = d1 - this.f();
      double d3 = this.h() - d1;
      double d4 = d0 - this.e();
      double d5 = this.g() - d0;
      double d6 = Math.min(d4, d5);
      d6 = Math.min(d6, d2);
      return Math.min(d6, d3);
   }

   public boolean a(Entity entity, AxisAlignedBB axisalignedbb) {
      double d0 = Math.max(MathHelper.a(axisalignedbb.b(), axisalignedbb.d()), 1.0);
      return this.a(entity) < d0 * 2.0 && this.a(entity.dl(), entity.dr(), d0);
   }

   public BorderStatus d() {
      return this.l.i();
   }

   public double e() {
      return this.l.a();
   }

   public double f() {
      return this.l.c();
   }

   public double g() {
      return this.l.b();
   }

   public double h() {
      return this.l.d();
   }

   public double a() {
      return this.i;
   }

   public double b() {
      return this.j;
   }

   public void c(double d0, double d1) {
      this.i = d0;
      this.j = d1;
      this.l.k();

      for(IWorldBorderListener iworldborderlistener : this.l()) {
         iworldborderlistener.a(this, d0, d1);
      }
   }

   public double i() {
      return this.l.e();
   }

   public long j() {
      return this.l.g();
   }

   public double k() {
      return this.l.h();
   }

   public void a(double d0) {
      this.l = new WorldBorder.d(d0);

      for(IWorldBorderListener iworldborderlistener : this.l()) {
         iworldborderlistener.a(this, d0);
      }
   }

   public void a(double d0, double d1, long i) {
      this.l = (WorldBorder.a)(d0 == d1 ? new WorldBorder.d(d1) : new WorldBorder.b(d0, d1, i));

      for(IWorldBorderListener iworldborderlistener : this.l()) {
         iworldborderlistener.a(this, d0, d1, i);
      }
   }

   protected List<IWorldBorderListener> l() {
      return Lists.newArrayList(this.a);
   }

   public void a(IWorldBorderListener iworldborderlistener) {
      if (!this.a.contains(iworldborderlistener)) {
         this.a.add(iworldborderlistener);
      }
   }

   public void b(IWorldBorderListener iworldborderlistener) {
      this.a.remove(iworldborderlistener);
   }

   public void a(int i) {
      this.k = i;
      this.l.j();
   }

   public int m() {
      return this.k;
   }

   public double n() {
      return this.f;
   }

   public void b(double d0) {
      this.f = d0;

      for(IWorldBorderListener iworldborderlistener : this.l()) {
         iworldborderlistener.c(this, d0);
      }
   }

   public double o() {
      return this.b;
   }

   public void c(double d0) {
      this.b = d0;

      for(IWorldBorderListener iworldborderlistener : this.l()) {
         iworldborderlistener.b(this, d0);
      }
   }

   public double p() {
      return this.l.f();
   }

   public int q() {
      return this.g;
   }

   public void b(int i) {
      this.g = i;

      for(IWorldBorderListener iworldborderlistener : this.l()) {
         iworldborderlistener.a(this, i);
      }
   }

   public int r() {
      return this.h;
   }

   public void c(int i) {
      this.h = i;

      for(IWorldBorderListener iworldborderlistener : this.l()) {
         iworldborderlistener.b(this, i);
      }
   }

   public void s() {
      this.l = this.l.l();
   }

   public WorldBorder.c t() {
      return new WorldBorder.c(this);
   }

   public void a(WorldBorder.c worldborder_c) {
      this.c(worldborder_c.a(), worldborder_c.b());
      this.c(worldborder_c.c());
      this.b(worldborder_c.d());
      this.c(worldborder_c.e());
      this.b(worldborder_c.f());
      if (worldborder_c.h() > 0L) {
         this.a(worldborder_c.g(), worldborder_c.i(), worldborder_c.h());
      } else {
         this.a(worldborder_c.g());
      }
   }

   private interface a {
      double a();

      double b();

      double c();

      double d();

      double e();

      double f();

      long g();

      double h();

      BorderStatus i();

      void j();

      void k();

      WorldBorder.a l();

      VoxelShape m();
   }

   private class b implements WorldBorder.a {
      private final double b;
      private final double c;
      private final long d;
      private final long e;
      private final double f;

      b(double d0, double d1, long i) {
         this.b = d0;
         this.c = d1;
         this.f = (double)i;
         this.e = SystemUtils.b();
         this.d = this.e + i;
      }

      @Override
      public double a() {
         return MathHelper.a(WorldBorder.this.a() - this.e() / 2.0, (double)(-WorldBorder.this.k), (double)WorldBorder.this.k);
      }

      @Override
      public double c() {
         return MathHelper.a(WorldBorder.this.b() - this.e() / 2.0, (double)(-WorldBorder.this.k), (double)WorldBorder.this.k);
      }

      @Override
      public double b() {
         return MathHelper.a(WorldBorder.this.a() + this.e() / 2.0, (double)(-WorldBorder.this.k), (double)WorldBorder.this.k);
      }

      @Override
      public double d() {
         return MathHelper.a(WorldBorder.this.b() + this.e() / 2.0, (double)(-WorldBorder.this.k), (double)WorldBorder.this.k);
      }

      @Override
      public double e() {
         double d0 = (double)(SystemUtils.b() - this.e) / this.f;
         return d0 < 1.0 ? MathHelper.d(d0, this.b, this.c) : this.c;
      }

      @Override
      public double f() {
         return Math.abs(this.b - this.c) / (double)(this.d - this.e);
      }

      @Override
      public long g() {
         return this.d - SystemUtils.b();
      }

      @Override
      public double h() {
         return this.c;
      }

      @Override
      public BorderStatus i() {
         return this.c < this.b ? BorderStatus.b : BorderStatus.a;
      }

      @Override
      public void k() {
      }

      @Override
      public void j() {
      }

      @Override
      public WorldBorder.a l() {
         return (WorldBorder.a)(this.g() <= 0L ? WorldBorder.this.new d(this.c) : this);
      }

      @Override
      public VoxelShape m() {
         return VoxelShapes.a(
            VoxelShapes.c,
            VoxelShapes.a(
               Math.floor(this.a()), Double.NEGATIVE_INFINITY, Math.floor(this.c()), Math.ceil(this.b()), Double.POSITIVE_INFINITY, Math.ceil(this.d())
            ),
            OperatorBoolean.e
         );
      }
   }

   public static class c {
      private final double a;
      private final double b;
      private final double c;
      private final double d;
      private final int e;
      private final int f;
      private final double g;
      private final long h;
      private final double i;

      c(double d0, double d1, double d2, double d3, int i, int j, double d4, long k, double d5) {
         this.a = d0;
         this.b = d1;
         this.c = d2;
         this.d = d3;
         this.e = i;
         this.f = j;
         this.g = d4;
         this.h = k;
         this.i = d5;
      }

      c(WorldBorder worldborder) {
         this.a = worldborder.a();
         this.b = worldborder.b();
         this.c = worldborder.o();
         this.d = worldborder.n();
         this.e = worldborder.r();
         this.f = worldborder.q();
         this.g = worldborder.i();
         this.h = worldborder.j();
         this.i = worldborder.k();
      }

      public double a() {
         return this.a;
      }

      public double b() {
         return this.b;
      }

      public double c() {
         return this.c;
      }

      public double d() {
         return this.d;
      }

      public int e() {
         return this.e;
      }

      public int f() {
         return this.f;
      }

      public double g() {
         return this.g;
      }

      public long h() {
         return this.h;
      }

      public double i() {
         return this.i;
      }

      public static WorldBorder.c a(DynamicLike<?> dynamiclike, WorldBorder.c worldborder_c) {
         double d0 = MathHelper.a(dynamiclike.get("BorderCenterX").asDouble(worldborder_c.a), -2.9999984E7, 2.9999984E7);
         double d1 = MathHelper.a(dynamiclike.get("BorderCenterZ").asDouble(worldborder_c.b), -2.9999984E7, 2.9999984E7);
         double d2 = dynamiclike.get("BorderSize").asDouble(worldborder_c.g);
         long i = dynamiclike.get("BorderSizeLerpTime").asLong(worldborder_c.h);
         double d3 = dynamiclike.get("BorderSizeLerpTarget").asDouble(worldborder_c.i);
         double d4 = dynamiclike.get("BorderSafeZone").asDouble(worldborder_c.d);
         double d5 = dynamiclike.get("BorderDamagePerBlock").asDouble(worldborder_c.c);
         int j = dynamiclike.get("BorderWarningBlocks").asInt(worldborder_c.e);
         int k = dynamiclike.get("BorderWarningTime").asInt(worldborder_c.f);
         return new WorldBorder.c(d0, d1, d5, d4, j, k, d2, i, d3);
      }

      public void a(NBTTagCompound nbttagcompound) {
         nbttagcompound.a("BorderCenterX", this.a);
         nbttagcompound.a("BorderCenterZ", this.b);
         nbttagcompound.a("BorderSize", this.g);
         nbttagcompound.a("BorderSizeLerpTime", this.h);
         nbttagcompound.a("BorderSafeZone", this.d);
         nbttagcompound.a("BorderDamagePerBlock", this.c);
         nbttagcompound.a("BorderSizeLerpTarget", this.i);
         nbttagcompound.a("BorderWarningBlocks", (double)this.e);
         nbttagcompound.a("BorderWarningTime", (double)this.f);
      }
   }

   private class d implements WorldBorder.a {
      private final double b;
      private double c;
      private double d;
      private double e;
      private double f;
      private VoxelShape g;

      public d(double d0) {
         this.b = d0;
         this.n();
      }

      @Override
      public double a() {
         return this.c;
      }

      @Override
      public double b() {
         return this.e;
      }

      @Override
      public double c() {
         return this.d;
      }

      @Override
      public double d() {
         return this.f;
      }

      @Override
      public double e() {
         return this.b;
      }

      @Override
      public BorderStatus i() {
         return BorderStatus.c;
      }

      @Override
      public double f() {
         return 0.0;
      }

      @Override
      public long g() {
         return 0L;
      }

      @Override
      public double h() {
         return this.b;
      }

      private void n() {
         this.c = MathHelper.a(WorldBorder.this.a() - this.b / 2.0, (double)(-WorldBorder.this.k), (double)WorldBorder.this.k);
         this.d = MathHelper.a(WorldBorder.this.b() - this.b / 2.0, (double)(-WorldBorder.this.k), (double)WorldBorder.this.k);
         this.e = MathHelper.a(WorldBorder.this.a() + this.b / 2.0, (double)(-WorldBorder.this.k), (double)WorldBorder.this.k);
         this.f = MathHelper.a(WorldBorder.this.b() + this.b / 2.0, (double)(-WorldBorder.this.k), (double)WorldBorder.this.k);
         this.g = VoxelShapes.a(
            VoxelShapes.c,
            VoxelShapes.a(
               Math.floor(this.a()), Double.NEGATIVE_INFINITY, Math.floor(this.c()), Math.ceil(this.b()), Double.POSITIVE_INFINITY, Math.ceil(this.d())
            ),
            OperatorBoolean.e
         );
      }

      @Override
      public void j() {
         this.n();
      }

      @Override
      public void k() {
         this.n();
      }

      @Override
      public WorldBorder.a l() {
         return this;
      }

      @Override
      public VoxelShape m() {
         return this.g;
      }
   }
}
