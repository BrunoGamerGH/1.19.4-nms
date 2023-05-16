package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.core.BlockPosition;
import net.minecraft.world.entity.EntityCreature;
import net.minecraft.world.level.IWorldReader;

public abstract class PathfinderGoalGotoTarget extends PathfinderGoal {
   private static final int g = 1200;
   private static final int h = 1200;
   private static final int i = 200;
   protected final EntityCreature a;
   public final double b;
   protected int c;
   protected int d;
   private int j;
   protected BlockPosition e = BlockPosition.b;
   private boolean k;
   private final int l;
   private final int m;
   protected int f;

   public PathfinderGoalGotoTarget(EntityCreature var0, double var1, int var3) {
      this(var0, var1, var3, 1);
   }

   public PathfinderGoalGotoTarget(EntityCreature var0, double var1, int var3, int var4) {
      this.a = var0;
      this.b = var1;
      this.l = var3;
      this.f = 0;
      this.m = var4;
      this.a(EnumSet.of(PathfinderGoal.Type.a, PathfinderGoal.Type.c));
   }

   @Override
   public boolean a() {
      if (this.c > 0) {
         --this.c;
         return false;
      } else {
         this.c = this.a(this.a);
         return this.n();
      }
   }

   protected int a(EntityCreature var0) {
      return b(200 + var0.dZ().a(200));
   }

   @Override
   public boolean b() {
      return this.d >= -this.j && this.d <= 1200 && this.a(this.a.H, this.e);
   }

   @Override
   public void c() {
      this.h();
      this.d = 0;
      this.j = this.a.dZ().a(this.a.dZ().a(1200) + 1200) + 1200;
   }

   protected void h() {
      this.a.G().a((double)((float)this.e.u()) + 0.5, (double)(this.e.v() + 1), (double)((float)this.e.w()) + 0.5, this.b);
   }

   public double i() {
      return 1.0;
   }

   protected BlockPosition k() {
      return this.e.c();
   }

   @Override
   public boolean J_() {
      return true;
   }

   @Override
   public void e() {
      BlockPosition var0 = this.k();
      if (!var0.a(this.a.de(), this.i())) {
         this.k = false;
         ++this.d;
         if (this.l()) {
            this.a.G().a((double)((float)var0.u()) + 0.5, (double)var0.v(), (double)((float)var0.w()) + 0.5, this.b);
         }
      } else {
         this.k = true;
         --this.d;
      }
   }

   public boolean l() {
      return this.d % 40 == 0;
   }

   protected boolean m() {
      return this.k;
   }

   protected boolean n() {
      int var0 = this.l;
      int var1 = this.m;
      BlockPosition var2 = this.a.dg();
      BlockPosition.MutableBlockPosition var3 = new BlockPosition.MutableBlockPosition();

      for(int var4 = this.f; var4 <= var1; var4 = var4 > 0 ? -var4 : 1 - var4) {
         for(int var5 = 0; var5 < var0; ++var5) {
            for(int var6 = 0; var6 <= var5; var6 = var6 > 0 ? -var6 : 1 - var6) {
               for(int var7 = var6 < var5 && var6 > -var5 ? var5 : 0; var7 <= var5; var7 = var7 > 0 ? -var7 : 1 - var7) {
                  var3.a(var2, var6, var4 - 1, var7);
                  if (this.a.a(var3) && this.a(this.a.H, var3)) {
                     this.e = var3;
                     return true;
                  }
               }
            }
         }
      }

      return false;
   }

   protected abstract boolean a(IWorldReader var1, BlockPosition var2);
}
