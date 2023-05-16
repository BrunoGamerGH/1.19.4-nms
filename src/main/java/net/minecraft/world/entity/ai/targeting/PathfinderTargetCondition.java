package net.minecraft.world.entity.ai.targeting;

import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityLiving;

public class PathfinderTargetCondition {
   public static final PathfinderTargetCondition a = a();
   private static final double b = 2.0;
   private final boolean c;
   private double d = -1.0;
   private boolean e = true;
   private boolean f = true;
   @Nullable
   private Predicate<EntityLiving> g;

   private PathfinderTargetCondition(boolean var0) {
      this.c = var0;
   }

   public static PathfinderTargetCondition a() {
      return new PathfinderTargetCondition(true);
   }

   public static PathfinderTargetCondition b() {
      return new PathfinderTargetCondition(false);
   }

   public PathfinderTargetCondition c() {
      PathfinderTargetCondition var0 = this.c ? a() : b();
      var0.d = this.d;
      var0.e = this.e;
      var0.f = this.f;
      var0.g = this.g;
      return var0;
   }

   public PathfinderTargetCondition a(double var0) {
      this.d = var0;
      return this;
   }

   public PathfinderTargetCondition d() {
      this.e = false;
      return this;
   }

   public PathfinderTargetCondition e() {
      this.f = false;
      return this;
   }

   public PathfinderTargetCondition a(@Nullable Predicate<EntityLiving> var0) {
      this.g = var0;
      return this;
   }

   public boolean a(@Nullable EntityLiving var0, EntityLiving var1) {
      if (var0 == var1) {
         return false;
      } else if (!var1.ei()) {
         return false;
      } else if (this.g != null && !this.g.test(var1)) {
         return false;
      } else {
         if (var0 == null) {
            if (this.c && (!var1.eh() || var1.H.ah() == EnumDifficulty.a)) {
               return false;
            }
         } else {
            if (this.c && (!var0.c(var1) || !var0.a(var1.ae()) || var0.p(var1))) {
               return false;
            }

            if (this.d > 0.0) {
               double var2 = this.f ? var1.y(var0) : 1.0;
               double var4 = Math.max(this.d * var2, 2.0);
               double var6 = var0.i(var1.dl(), var1.dn(), var1.dr());
               if (var6 > var4 * var4) {
                  return false;
               }
            }

            if (this.e && var0 instanceof EntityInsentient var2 && !var2.I().a(var1)) {
               return false;
            }
         }

         return true;
      }
   }
}
