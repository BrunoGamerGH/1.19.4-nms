package net.minecraft.world.level.pathfinder;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.util.profiling.GameProfilerFiller;
import net.minecraft.util.profiling.metrics.MetricCategory;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.level.ChunkCache;

public class Pathfinder {
   private static final float a = 1.5F;
   private final PathPoint[] b = new PathPoint[32];
   private final int c;
   private final PathfinderAbstract d;
   private static final boolean e = false;
   private final Path f = new Path();

   public Pathfinder(PathfinderAbstract var0, int var1) {
      this.d = var0;
      this.c = var1;
   }

   @Nullable
   public PathEntity a(ChunkCache var0, EntityInsentient var1, Set<BlockPosition> var2, float var3, int var4, float var5) {
      this.f.a();
      this.d.a(var0, var1);
      PathPoint var6 = this.d.a();
      if (var6 == null) {
         return null;
      } else {
         Map<PathDestination, BlockPosition> var7 = var2.stream()
            .collect(Collectors.toMap(var0x -> this.d.a((double)var0x.u(), (double)var0x.v(), (double)var0x.w()), Function.identity()));
         PathEntity var8 = this.a(var0.a(), var6, var7, var3, var4, var5);
         this.d.b();
         return var8;
      }
   }

   @Nullable
   private PathEntity a(GameProfilerFiller var0, PathPoint var1, Map<PathDestination, BlockPosition> var2, float var3, int var4, float var5) {
      var0.a("find_path");
      var0.a(MetricCategory.a);
      Set<PathDestination> var6 = var2.keySet();
      var1.e = 0.0F;
      var1.f = this.a(var1, var6);
      var1.g = var1.f;
      this.f.a();
      this.f.a(var1);
      Set<PathPoint> var7 = ImmutableSet.of();
      int var8 = 0;
      Set<PathDestination> var9 = Sets.newHashSetWithExpectedSize(var6.size());
      int var10 = (int)((float)this.c * var5);

      while(!this.f.e()) {
         if (++var8 >= var10) {
            break;
         }

         PathPoint var11 = this.f.c();
         var11.i = true;

         for(PathDestination var13 : var6) {
            if (var11.d(var13) <= (float)var4) {
               var13.e();
               var9.add(var13);
            }
         }

         if (!var9.isEmpty()) {
            break;
         }

         if (!(var11.a(var1) >= var3)) {
            int var12 = this.d.a(this.b, var11);

            for(int var13 = 0; var13 < var12; ++var13) {
               PathPoint var14 = this.b[var13];
               float var15 = this.a(var11, var14);
               var14.j = var11.j + var15;
               float var16 = var11.e + var15 + var14.k;
               if (var14.j < var3 && (!var14.c() || var16 < var14.e)) {
                  var14.h = var11;
                  var14.e = var16;
                  var14.f = this.a(var14, var6) * 1.5F;
                  if (var14.c()) {
                     this.f.a(var14, var14.e + var14.f);
                  } else {
                     var14.g = var14.e + var14.f;
                     this.f.a(var14);
                  }
               }
            }
         }
      }

      Optional<PathEntity> var11 = !var9.isEmpty()
         ? var9.stream().map(var1x -> this.a(var1x.d(), var2.get(var1x), true)).min(Comparator.comparingInt(PathEntity::e))
         : var6.stream()
            .map(var1x -> this.a(var1x.d(), var2.get(var1x), false))
            .min(Comparator.comparingDouble(PathEntity::n).thenComparingInt(PathEntity::e));
      var0.c();
      return !var11.isPresent() ? null : var11.get();
   }

   protected float a(PathPoint var0, PathPoint var1) {
      return var0.a(var1);
   }

   private float a(PathPoint var0, Set<PathDestination> var1) {
      float var2 = Float.MAX_VALUE;

      for(PathDestination var4 : var1) {
         float var5 = var0.a(var4);
         var4.a(var5, var0);
         var2 = Math.min(var5, var2);
      }

      return var2;
   }

   private PathEntity a(PathPoint var0, BlockPosition var1, boolean var2) {
      List<PathPoint> var3 = Lists.newArrayList();
      PathPoint var4 = var0;
      var3.add(0, var0);

      while(var4.h != null) {
         var4 = var4.h;
         var3.add(0, var4);
      }

      return new PathEntity(var3, var1, var2);
   }
}
