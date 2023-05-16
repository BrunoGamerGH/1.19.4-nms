package net.minecraft.world.entity.ai.control;

import java.util.Optional;
import net.minecraft.util.MathHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.phys.Vec3D;

public class ControllerLook implements Control {
   protected final EntityInsentient a;
   protected float b;
   protected float c;
   protected int d;
   protected double e;
   protected double f;
   protected double g;

   public ControllerLook(EntityInsentient var0) {
      this.a = var0;
   }

   public void a(Vec3D var0) {
      this.a(var0.c, var0.d, var0.e);
   }

   public void a(Entity var0) {
      this.a(var0.dl(), b(var0), var0.dr());
   }

   public void a(Entity var0, float var1, float var2) {
      this.a(var0.dl(), b(var0), var0.dr(), var1, var2);
   }

   public void a(double var0, double var2, double var4) {
      this.a(var0, var2, var4, (float)this.a.X(), (float)this.a.V());
   }

   public void a(double var0, double var2, double var4, float var6, float var7) {
      this.e = var0;
      this.f = var2;
      this.g = var4;
      this.b = var6;
      this.c = var7;
      this.d = 2;
   }

   public void a() {
      if (this.c()) {
         this.a.e(0.0F);
      }

      if (this.d > 0) {
         --this.d;
         this.i().ifPresent(var0 -> this.a.aV = this.a(this.a.aV, var0, this.b));
         this.h().ifPresent(var0 -> this.a.e(this.a(this.a.dy(), var0, this.c)));
      } else {
         this.a.aV = this.a(this.a.aV, this.a.aT, 10.0F);
      }

      this.b();
   }

   protected void b() {
      if (!this.a.G().l()) {
         this.a.aV = MathHelper.c(this.a.aV, this.a.aT, (float)this.a.W());
      }
   }

   protected boolean c() {
      return true;
   }

   public boolean d() {
      return this.d > 0;
   }

   public double e() {
      return this.e;
   }

   public double f() {
      return this.f;
   }

   public double g() {
      return this.g;
   }

   protected Optional<Float> h() {
      double var0 = this.e - this.a.dl();
      double var2 = this.f - this.a.dp();
      double var4 = this.g - this.a.dr();
      double var6 = Math.sqrt(var0 * var0 + var4 * var4);
      return !(Math.abs(var2) > 1.0E-5F) && !(Math.abs(var6) > 1.0E-5F)
         ? Optional.empty()
         : Optional.of((float)(-(MathHelper.d(var2, var6) * 180.0F / (float)Math.PI)));
   }

   protected Optional<Float> i() {
      double var0 = this.e - this.a.dl();
      double var2 = this.g - this.a.dr();
      return !(Math.abs(var2) > 1.0E-5F) && !(Math.abs(var0) > 1.0E-5F)
         ? Optional.empty()
         : Optional.of((float)(MathHelper.d(var2, var0) * 180.0F / (float)Math.PI) - 90.0F);
   }

   protected float a(float var0, float var1, float var2) {
      float var3 = MathHelper.c(var0, var1);
      float var4 = MathHelper.a(var3, -var2, var2);
      return var0 + var4;
   }

   private static double b(Entity var0) {
      return var0 instanceof EntityLiving ? var0.dp() : (var0.cD().b + var0.cD().e) / 2.0;
   }
}
