package net.minecraft.world.level.pathfinder;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.util.VisibleForDebug;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3D;

public class PathEntity {
   private final List<PathPoint> a;
   private PathPoint[] b = new PathPoint[0];
   private PathPoint[] c = new PathPoint[0];
   @Nullable
   private Set<PathDestination> d;
   private int e;
   private final BlockPosition f;
   private final float g;
   private final boolean h;

   public PathEntity(List<PathPoint> var0, BlockPosition var1, boolean var2) {
      this.a = var0;
      this.f = var1;
      this.g = var0.isEmpty() ? Float.MAX_VALUE : this.a.get(this.a.size() - 1).c(this.f);
      this.h = var2;
   }

   public void a() {
      ++this.e;
   }

   public boolean b() {
      return this.e <= 0;
   }

   public boolean c() {
      return this.e >= this.a.size();
   }

   @Nullable
   public PathPoint d() {
      return !this.a.isEmpty() ? this.a.get(this.a.size() - 1) : null;
   }

   public PathPoint a(int var0) {
      return this.a.get(var0);
   }

   public void b(int var0) {
      if (this.a.size() > var0) {
         this.a.subList(var0, this.a.size()).clear();
      }
   }

   public void a(int var0, PathPoint var1) {
      this.a.set(var0, var1);
   }

   public int e() {
      return this.a.size();
   }

   public int f() {
      return this.e;
   }

   public void c(int var0) {
      this.e = var0;
   }

   public Vec3D a(Entity var0, int var1) {
      PathPoint var2 = this.a.get(var1);
      double var3 = (double)var2.a + (double)((int)(var0.dc() + 1.0F)) * 0.5;
      double var5 = (double)var2.b;
      double var7 = (double)var2.c + (double)((int)(var0.dc() + 1.0F)) * 0.5;
      return new Vec3D(var3, var5, var7);
   }

   public BlockPosition d(int var0) {
      return this.a.get(var0).a();
   }

   public Vec3D a(Entity var0) {
      return this.a(var0, this.e);
   }

   public BlockPosition g() {
      return this.a.get(this.e).a();
   }

   public PathPoint h() {
      return this.a.get(this.e);
   }

   @Nullable
   public PathPoint i() {
      return this.e > 0 ? this.a.get(this.e - 1) : null;
   }

   public boolean a(@Nullable PathEntity var0) {
      if (var0 == null) {
         return false;
      } else if (var0.a.size() != this.a.size()) {
         return false;
      } else {
         for(int var1 = 0; var1 < this.a.size(); ++var1) {
            PathPoint var2 = this.a.get(var1);
            PathPoint var3 = var0.a.get(var1);
            if (var2.a != var3.a || var2.b != var3.b || var2.c != var3.c) {
               return false;
            }
         }

         return true;
      }
   }

   public boolean j() {
      return this.h;
   }

   @VisibleForDebug
   void a(PathPoint[] var0, PathPoint[] var1, Set<PathDestination> var2) {
      this.b = var0;
      this.c = var1;
      this.d = var2;
   }

   @VisibleForDebug
   public PathPoint[] k() {
      return this.b;
   }

   @VisibleForDebug
   public PathPoint[] l() {
      return this.c;
   }

   public void a(PacketDataSerializer var0) {
      if (this.d != null && !this.d.isEmpty()) {
         var0.writeBoolean(this.h);
         var0.writeInt(this.e);
         var0.writeInt(this.d.size());
         this.d.forEach(var1x -> var1x.a(var0));
         var0.writeInt(this.f.u());
         var0.writeInt(this.f.v());
         var0.writeInt(this.f.w());
         var0.writeInt(this.a.size());

         for(PathPoint var2 : this.a) {
            var2.a(var0);
         }

         var0.writeInt(this.b.length);

         for(PathPoint var4 : this.b) {
            var4.a(var0);
         }

         var0.writeInt(this.c.length);

         for(PathPoint var4 : this.c) {
            var4.a(var0);
         }
      }
   }

   public static PathEntity b(PacketDataSerializer var0) {
      boolean var1 = var0.readBoolean();
      int var2 = var0.readInt();
      int var3 = var0.readInt();
      Set<PathDestination> var4 = Sets.newHashSet();

      for(int var5 = 0; var5 < var3; ++var5) {
         var4.add(PathDestination.c(var0));
      }

      BlockPosition var5 = new BlockPosition(var0.readInt(), var0.readInt(), var0.readInt());
      List<PathPoint> var6 = Lists.newArrayList();
      int var7 = var0.readInt();

      for(int var8 = 0; var8 < var7; ++var8) {
         var6.add(PathPoint.b(var0));
      }

      PathPoint[] var8 = new PathPoint[var0.readInt()];

      for(int var9 = 0; var9 < var8.length; ++var9) {
         var8[var9] = PathPoint.b(var0);
      }

      PathPoint[] var9 = new PathPoint[var0.readInt()];

      for(int var10 = 0; var10 < var9.length; ++var10) {
         var9[var10] = PathPoint.b(var0);
      }

      PathEntity var10 = new PathEntity(var6, var5, var1);
      var10.b = var8;
      var10.c = var9;
      var10.d = var4;
      var10.e = var2;
      return var10;
   }

   @Override
   public String toString() {
      return "Path(length=" + this.a.size() + ")";
   }

   public BlockPosition m() {
      return this.f;
   }

   public float n() {
      return this.g;
   }
}
