package net.minecraft.world.entity.ai.goal;

import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.tags.TagsFluid;
import net.minecraft.util.MathHelper;
import net.minecraft.world.entity.animal.EntityDolphin;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.Vec3D;

public class PathfinderGoalWaterJump extends PathfinderGoalWaterJumpAbstract {
   private static final int[] a = new int[]{0, 1, 4, 5, 6, 7};
   private final EntityDolphin b;
   private final int c;
   private boolean d;

   public PathfinderGoalWaterJump(EntityDolphin var0, int var1) {
      this.b = var0;
      this.c = b(var1);
   }

   @Override
   public boolean a() {
      if (this.b.dZ().a(this.c) != 0) {
         return false;
      } else {
         EnumDirection var0 = this.b.cB();
         int var1 = var0.j();
         int var2 = var0.l();
         BlockPosition var3 = this.b.dg();

         for(int var7 : a) {
            if (!this.a(var3, var1, var2, var7) || !this.b(var3, var1, var2, var7)) {
               return false;
            }
         }

         return true;
      }
   }

   private boolean a(BlockPosition var0, int var1, int var2, int var3) {
      BlockPosition var4 = var0.b(var1 * var3, 0, var2 * var3);
      return this.b.H.b_(var4).a(TagsFluid.a) && !this.b.H.a_(var4).d().c();
   }

   private boolean b(BlockPosition var0, int var1, int var2, int var3) {
      return this.b.H.a_(var0.b(var1 * var3, 1, var2 * var3)).h() && this.b.H.a_(var0.b(var1 * var3, 2, var2 * var3)).h();
   }

   @Override
   public boolean b() {
      double var0 = this.b.dj().d;
      return (!(var0 * var0 < 0.03F) || this.b.dy() == 0.0F || !(Math.abs(this.b.dy()) < 10.0F) || !this.b.aT()) && !this.b.ax();
   }

   @Override
   public boolean I_() {
      return false;
   }

   @Override
   public void c() {
      EnumDirection var0 = this.b.cB();
      this.b.f(this.b.dj().b((double)var0.j() * 0.6, 0.7, (double)var0.l() * 0.6));
      this.b.G().n();
   }

   @Override
   public void d() {
      this.b.e(0.0F);
   }

   @Override
   public void e() {
      boolean var0 = this.d;
      if (!var0) {
         Fluid var1 = this.b.H.b_(this.b.dg());
         this.d = var1.a(TagsFluid.a);
      }

      if (this.d && !var0) {
         this.b.a(SoundEffects.fR, 1.0F, 1.0F);
      }

      Vec3D var1 = this.b.dj();
      if (var1.d * var1.d < 0.03F && this.b.dy() != 0.0F) {
         this.b.e(MathHelper.j(0.2F, this.b.dy(), 0.0F));
      } else if (var1.f() > 1.0E-5F) {
         double var2 = var1.h();
         double var4 = Math.atan2(-var1.d, var2) * 180.0F / (float)Math.PI;
         this.b.e((float)var4);
      }
   }
}
