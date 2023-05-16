package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.util.MathHelper;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.util.random.WeightedRandom2;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityPose;
import net.minecraft.world.entity.EntitySize;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.navigation.NavigationAbstract;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.pathfinder.PathEntity;
import net.minecraft.world.level.pathfinder.PathfinderNormal;
import net.minecraft.world.phys.Vec3D;

public class LongJumpToRandomPos<E extends EntityInsentient> extends Behavior<E> {
   protected static final int c = 20;
   private static final int m = 40;
   protected static final int d = 8;
   private static final int n = 200;
   private static final List<Integer> o = Lists.newArrayList(new Integer[]{65, 70, 75, 80});
   private final UniformInt p;
   protected final int e;
   protected final int f;
   protected final float g;
   protected List<LongJumpToRandomPos.a> h = Lists.newArrayList();
   protected Optional<Vec3D> i = Optional.empty();
   @Nullable
   protected Vec3D j;
   protected int k;
   protected long l;
   private final Function<E, SoundEffect> q;
   private final BiPredicate<E, BlockPosition> r;

   public LongJumpToRandomPos(UniformInt var0, int var1, int var2, float var3, Function<E, SoundEffect> var4) {
      this(var0, var1, var2, var3, var4, LongJumpToRandomPos::a);
   }

   public static <E extends EntityInsentient> boolean a(E var0, BlockPosition var1) {
      World var2 = var0.H;
      BlockPosition var3 = var1.d();
      return var2.a_(var3).i(var2, var3) && var0.a(PathfinderNormal.a(var2, var1.j())) == 0.0F;
   }

   public LongJumpToRandomPos(UniformInt var0, int var1, int var2, float var3, Function<E, SoundEffect> var4, BiPredicate<E, BlockPosition> var5) {
      super(ImmutableMap.of(MemoryModuleType.n, MemoryStatus.c, MemoryModuleType.R, MemoryStatus.b, MemoryModuleType.S, MemoryStatus.b), 200);
      this.p = var0;
      this.e = var1;
      this.f = var2;
      this.g = var3;
      this.q = var4;
      this.r = var5;
   }

   protected boolean a(WorldServer var0, EntityInsentient var1) {
      boolean var2 = var1.ax() && !var1.aT() && !var1.bg() && !var0.a_(var1.dg()).a(Blocks.pc);
      if (!var2) {
         var1.dH().a(MemoryModuleType.R, this.p.a(var0.z) / 2);
      }

      return var2;
   }

   protected boolean b(WorldServer var0, EntityInsentient var1, long var2) {
      boolean var4 = this.i.isPresent() && this.i.get().equals(var1.de()) && this.k > 0 && !var1.aW() && (this.j != null || !this.h.isEmpty());
      if (!var4 && var1.dH().c(MemoryModuleType.S).isEmpty()) {
         var1.dH().a(MemoryModuleType.R, this.p.a(var0.z) / 2);
         var1.dH().b(MemoryModuleType.n);
      }

      return var4;
   }

   protected void a(WorldServer var0, E var1, long var2) {
      this.j = null;
      this.k = 20;
      this.i = Optional.of(var1.de());
      BlockPosition var4 = var1.dg();
      int var5 = var4.u();
      int var6 = var4.v();
      int var7 = var4.w();
      this.h = BlockPosition.a(var5 - this.f, var6 - this.e, var7 - this.f, var5 + this.f, var6 + this.e, var7 + this.f)
         .filter(var1x -> !var1x.equals(var4))
         .map(var1x -> new LongJumpToRandomPos.a(var1x.i(), MathHelper.c(var4.j(var1x))))
         .collect(Collectors.toCollection(Lists::newArrayList));
   }

   protected void c(WorldServer var0, E var1, long var2) {
      if (this.j != null) {
         if (var2 - this.l >= 40L) {
            var1.f(var1.aT);
            var1.p(true);
            double var4 = this.j.f();
            double var6 = var4 + var1.eR();
            var1.f(this.j.a(var6 / var4));
            var1.dH().a(MemoryModuleType.S, true);
            var0.a(null, var1, this.q.apply(var1), SoundCategory.g, 1.0F, 1.0F);
         }
      } else {
         --this.k;
         this.d(var0, var1, var2);
      }
   }

   protected void d(WorldServer var0, E var1, long var2) {
      while(!this.h.isEmpty()) {
         Optional<LongJumpToRandomPos.a> var4 = this.a(var0);
         if (!var4.isEmpty()) {
            LongJumpToRandomPos.a var5 = var4.get();
            BlockPosition var6 = var5.b();
            if (this.a(var0, var1, var6)) {
               Vec3D var7 = Vec3D.b(var6);
               Vec3D var8 = this.a(var1, var7);
               if (var8 != null) {
                  var1.dH().a(MemoryModuleType.n, new BehaviorTarget(var6));
                  NavigationAbstract var9 = var1.G();
                  PathEntity var10 = var9.a(var6, 0, 8);
                  if (var10 == null || !var10.j()) {
                     this.j = var8;
                     this.l = var2;
                     return;
                  }
               }
            }
         }
      }
   }

   protected Optional<LongJumpToRandomPos.a> a(WorldServer var0) {
      Optional<LongJumpToRandomPos.a> var1 = WeightedRandom2.a(var0.z, this.h);
      var1.ifPresent(this.h::remove);
      return var1;
   }

   private boolean a(WorldServer var0, E var1, BlockPosition var2) {
      BlockPosition var3 = var1.dg();
      int var4 = var3.u();
      int var5 = var3.w();
      return var4 == var2.u() && var5 == var2.w() ? false : this.r.test(var1, var2);
   }

   @Nullable
   protected Vec3D a(EntityInsentient var0, Vec3D var1) {
      List<Integer> var2 = Lists.newArrayList(o);
      Collections.shuffle(var2);

      for(int var4 : var2) {
         Vec3D var5 = this.a(var0, var1, var4);
         if (var5 != null) {
            return var5;
         }
      }

      return null;
   }

   @Nullable
   private Vec3D a(EntityInsentient var0, Vec3D var1, int var2) {
      Vec3D var3 = var0.de();
      Vec3D var4 = new Vec3D(var1.c - var3.c, 0.0, var1.e - var3.e).d().a(0.5);
      var1 = var1.d(var4);
      Vec3D var5 = var1.d(var3);
      float var6 = (float)var2 * (float) Math.PI / 180.0F;
      double var7 = Math.atan2(var5.e, var5.c);
      double var9 = var5.a(0.0, var5.d, 0.0).g();
      double var11 = Math.sqrt(var9);
      double var13 = var5.d;
      double var15 = Math.sin((double)(2.0F * var6));
      double var17 = 0.08;
      double var19 = Math.pow(Math.cos((double)var6), 2.0);
      double var21 = Math.sin((double)var6);
      double var23 = Math.cos((double)var6);
      double var25 = Math.sin(var7);
      double var27 = Math.cos(var7);
      double var29 = var9 * 0.08 / (var11 * var15 - 2.0 * var13 * var19);
      if (var29 < 0.0) {
         return null;
      } else {
         double var31 = Math.sqrt(var29);
         if (var31 > (double)this.g) {
            return null;
         } else {
            double var33 = var31 * var23;
            double var35 = var31 * var21;
            int var37 = MathHelper.c(var11 / var33) * 2;
            double var38 = 0.0;
            Vec3D var40 = null;
            EntitySize var41 = var0.a(EntityPose.g);

            for(int var42 = 0; var42 < var37 - 1; ++var42) {
               var38 += var11 / (double)var37;
               double var43 = var21 / var23 * var38 - Math.pow(var38, 2.0) * 0.08 / (2.0 * var29 * Math.pow(var23, 2.0));
               double var45 = var38 * var27;
               double var47 = var38 * var25;
               Vec3D var49 = new Vec3D(var3.c + var45, var3.d + var43, var3.e + var47);
               if (var40 != null && !this.a(var0, var41, var40, var49)) {
                  return null;
               }

               var40 = var49;
            }

            return new Vec3D(var33 * var27, var35, var33 * var25).a(0.95F);
         }
      }
   }

   private boolean a(EntityInsentient var0, EntitySize var1, Vec3D var2, Vec3D var3) {
      Vec3D var4 = var3.d(var2);
      double var5 = (double)Math.min(var1.a, var1.b);
      int var7 = MathHelper.c(var4.f() / var5);
      Vec3D var8 = var4.d();
      Vec3D var9 = var2;

      for(int var10 = 0; var10 < var7; ++var10) {
         var9 = var10 == var7 - 1 ? var3 : var9.e(var8.a(var5 * 0.9F));
         if (!var0.H.a(var0, var1.a(var9))) {
            return false;
         }
      }

      return true;
   }

   public static class a extends WeightedEntry.a {
      private final BlockPosition a;

      public a(BlockPosition var0, int var1) {
         super(var1);
         this.a = var0;
      }

      public BlockPosition b() {
         return this.a;
      }
   }
}
