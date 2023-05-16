package net.minecraft.world.entity.ai.control;

import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.tags.TagsBlock;
import net.minecraft.util.MathHelper;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.entity.ai.navigation.NavigationAbstract;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.pathfinder.PathfinderAbstract;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ControllerMove implements Control {
   public static final float a = 5.0E-4F;
   public static final float b = 2.5000003E-7F;
   protected static final int c = 90;
   protected final EntityInsentient d;
   protected double e;
   protected double f;
   protected double g;
   protected double h;
   protected float i;
   protected float j;
   protected ControllerMove.Operation k = ControllerMove.Operation.a;

   public ControllerMove(EntityInsentient var0) {
      this.d = var0;
   }

   public boolean b() {
      return this.k == ControllerMove.Operation.b;
   }

   public double c() {
      return this.h;
   }

   public void a(double var0, double var2, double var4, double var6) {
      this.e = var0;
      this.f = var2;
      this.g = var4;
      this.h = var6;
      if (this.k != ControllerMove.Operation.d) {
         this.k = ControllerMove.Operation.b;
      }
   }

   public void a(float var0, float var1) {
      this.k = ControllerMove.Operation.c;
      this.i = var0;
      this.j = var1;
      this.h = 0.25;
   }

   public void a() {
      if (this.k == ControllerMove.Operation.c) {
         float var0 = (float)this.d.b(GenericAttributes.d);
         float var1 = (float)this.h * var0;
         float var2 = this.i;
         float var3 = this.j;
         float var4 = MathHelper.c(var2 * var2 + var3 * var3);
         if (var4 < 1.0F) {
            var4 = 1.0F;
         }

         var4 = var1 / var4;
         var2 *= var4;
         var3 *= var4;
         float var5 = MathHelper.a(this.d.dw() * (float) (Math.PI / 180.0));
         float var6 = MathHelper.b(this.d.dw() * (float) (Math.PI / 180.0));
         float var7 = var2 * var6 - var3 * var5;
         float var8 = var3 * var6 + var2 * var5;
         if (!this.b(var7, var8)) {
            this.i = 1.0F;
            this.j = 0.0F;
         }

         this.d.h(var1);
         this.d.y(this.i);
         this.d.A(this.j);
         this.k = ControllerMove.Operation.a;
      } else if (this.k == ControllerMove.Operation.b) {
         this.k = ControllerMove.Operation.a;
         double var0 = this.e - this.d.dl();
         double var2 = this.g - this.d.dr();
         double var4 = this.f - this.d.dn();
         double var6 = var0 * var0 + var4 * var4 + var2 * var2;
         if (var6 < 2.5000003E-7F) {
            this.d.y(0.0F);
            return;
         }

         float var8 = (float)(MathHelper.d(var2, var0) * 180.0F / (float)Math.PI) - 90.0F;
         this.d.f(this.a(this.d.dw(), var8, 90.0F));
         this.d.h((float)(this.h * this.d.b(GenericAttributes.d)));
         BlockPosition var9 = this.d.dg();
         IBlockData var10 = this.d.H.a_(var9);
         VoxelShape var11 = var10.k(this.d.H, var9);
         if (var4 > (double)this.d.dA() && var0 * var0 + var2 * var2 < (double)Math.max(1.0F, this.d.dc())
            || !var11.b() && this.d.dn() < var11.c(EnumDirection.EnumAxis.b) + (double)var9.v() && !var10.a(TagsBlock.o) && !var10.a(TagsBlock.R)) {
            this.d.E().a();
            this.k = ControllerMove.Operation.d;
         }
      } else if (this.k == ControllerMove.Operation.d) {
         this.d.h((float)(this.h * this.d.b(GenericAttributes.d)));
         if (this.d.ax()) {
            this.k = ControllerMove.Operation.a;
         }
      } else {
         this.d.y(0.0F);
      }
   }

   private boolean b(float var0, float var1) {
      NavigationAbstract var2 = this.d.G();
      if (var2 != null) {
         PathfinderAbstract var3 = var2.p();
         if (var3 != null && var3.a(this.d.H, MathHelper.a(this.d.dl() + (double)var0), this.d.dm(), MathHelper.a(this.d.dr() + (double)var1)) != PathType.c) {
            return false;
         }
      }

      return true;
   }

   protected float a(float var0, float var1, float var2) {
      float var3 = MathHelper.g(var1 - var0);
      if (var3 > var2) {
         var3 = var2;
      }

      if (var3 < -var2) {
         var3 = -var2;
      }

      float var4 = var0 + var3;
      if (var4 < 0.0F) {
         var4 += 360.0F;
      } else if (var4 > 360.0F) {
         var4 -= 360.0F;
      }

      return var4;
   }

   public double d() {
      return this.e;
   }

   public double e() {
      return this.f;
   }

   public double f() {
      return this.g;
   }

   protected static enum Operation {
      a,
      b,
      c,
      d;
   }
}
