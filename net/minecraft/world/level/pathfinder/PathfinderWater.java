package net.minecraft.world.level.pathfinder;

import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.tags.TagsFluid;
import net.minecraft.util.MathHelper;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.level.ChunkCache;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.material.Fluid;

public class PathfinderWater extends PathfinderAbstract {
   private final boolean k;
   private final Long2ObjectMap<PathType> l = new Long2ObjectOpenHashMap();

   public PathfinderWater(boolean var0) {
      this.k = var0;
   }

   @Override
   public void a(ChunkCache var0, EntityInsentient var1) {
      super.a(var0, var1);
      this.l.clear();
   }

   @Override
   public void b() {
      super.b();
      this.l.clear();
   }

   @Override
   public PathPoint a() {
      return this.b(MathHelper.a(this.b.cD().a), MathHelper.a(this.b.cD().b + 0.5), MathHelper.a(this.b.cD().c));
   }

   @Override
   public PathDestination a(double var0, double var2, double var4) {
      return this.a(this.b(MathHelper.a(var0), MathHelper.a(var2), MathHelper.a(var4)));
   }

   @Override
   public int a(PathPoint[] var0, PathPoint var1) {
      int var2 = 0;
      Map<EnumDirection, PathPoint> var3 = Maps.newEnumMap(EnumDirection.class);

      for(EnumDirection var7 : EnumDirection.values()) {
         PathPoint var8 = this.a(var1.a + var7.j(), var1.b + var7.k(), var1.c + var7.l());
         var3.put(var7, var8);
         if (this.b(var8)) {
            var0[var2++] = var8;
         }
      }

      for(EnumDirection var5 : EnumDirection.EnumDirectionLimit.a) {
         EnumDirection var6 = var5.h();
         PathPoint var7 = this.a(var1.a + var5.j() + var6.j(), var1.b, var1.c + var5.l() + var6.l());
         if (this.a(var7, var3.get(var5), var3.get(var6))) {
            var0[var2++] = var7;
         }
      }

      return var2;
   }

   protected boolean b(@Nullable PathPoint var0) {
      return var0 != null && !var0.i;
   }

   protected boolean a(@Nullable PathPoint var0, @Nullable PathPoint var1, @Nullable PathPoint var2) {
      return this.b(var0) && var1 != null && var1.k >= 0.0F && var2 != null && var2.k >= 0.0F;
   }

   @Nullable
   protected PathPoint a(int var0, int var1, int var2) {
      PathPoint var3 = null;
      PathType var4 = this.c(var0, var1, var2);
      if (this.k && var4 == PathType.u || var4 == PathType.j) {
         float var5 = this.b.a(var4);
         if (var5 >= 0.0F) {
            var3 = this.b(var0, var1, var2);
            var3.l = var4;
            var3.k = Math.max(var3.k, var5);
            if (this.a.b_(new BlockPosition(var0, var1, var2)).c()) {
               var3.k += 8.0F;
            }
         }
      }

      return var3;
   }

   protected PathType c(int var0, int var1, int var2) {
      return (PathType)this.l.computeIfAbsent(BlockPosition.a(var0, var1, var2), var3x -> this.a(this.a, var0, var1, var2));
   }

   @Override
   public PathType a(IBlockAccess var0, int var1, int var2, int var3) {
      return this.a(var0, var1, var2, var3, this.b);
   }

   @Override
   public PathType a(IBlockAccess var0, int var1, int var2, int var3, EntityInsentient var4) {
      BlockPosition.MutableBlockPosition var5 = new BlockPosition.MutableBlockPosition();

      for(int var6 = var1; var6 < var1 + this.d; ++var6) {
         for(int var7 = var2; var7 < var2 + this.e; ++var7) {
            for(int var8 = var3; var8 < var3 + this.f; ++var8) {
               Fluid var9 = var0.b_(var5.d(var6, var7, var8));
               IBlockData var10 = var0.a_(var5.d(var6, var7, var8));
               if (var9.c() && var10.a(var0, var5.d(), PathMode.b) && var10.h()) {
                  return PathType.u;
               }

               if (!var9.a(TagsFluid.a)) {
                  return PathType.a;
               }
            }
         }
      }

      IBlockData var6 = var0.a_(var5);
      return var6.a(var0, var5, PathMode.b) ? PathType.j : PathType.a;
   }
}
