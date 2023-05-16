package net.minecraft.world.entity.boss.enderdragon.phases;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import net.minecraft.world.entity.boss.enderdragon.EntityEnderDragon;

public class DragonControllerPhase<T extends IDragonController> {
   private static DragonControllerPhase<?>[] l = new DragonControllerPhase[0];
   public static final DragonControllerPhase<DragonControllerHold> a = a(DragonControllerHold.class, "HoldingPattern");
   public static final DragonControllerPhase<DragonControllerStrafe> b = a(DragonControllerStrafe.class, "StrafePlayer");
   public static final DragonControllerPhase<DragonControllerLandingFly> c = a(DragonControllerLandingFly.class, "LandingApproach");
   public static final DragonControllerPhase<DragonControllerLanding> d = a(DragonControllerLanding.class, "Landing");
   public static final DragonControllerPhase<DragonControllerFly> e = a(DragonControllerFly.class, "Takeoff");
   public static final DragonControllerPhase<DragonControllerLandedFlame> f = a(DragonControllerLandedFlame.class, "SittingFlaming");
   public static final DragonControllerPhase<DragonControllerLandedSearch> g = a(DragonControllerLandedSearch.class, "SittingScanning");
   public static final DragonControllerPhase<DragonControllerLandedAttack> h = a(DragonControllerLandedAttack.class, "SittingAttacking");
   public static final DragonControllerPhase<DragonControllerCharge> i = a(DragonControllerCharge.class, "ChargingPlayer");
   public static final DragonControllerPhase<DragonControllerDying> j = a(DragonControllerDying.class, "Dying");
   public static final DragonControllerPhase<DragonControllerHover> k = a(DragonControllerHover.class, "Hover");
   private final Class<? extends IDragonController> m;
   private final int n;
   private final String o;

   private DragonControllerPhase(int var0, Class<? extends IDragonController> var1, String var2) {
      this.n = var0;
      this.m = var1;
      this.o = var2;
   }

   public IDragonController a(EntityEnderDragon var0) {
      try {
         Constructor<? extends IDragonController> var1 = this.a();
         return var1.newInstance(var0);
      } catch (Exception var3) {
         throw new Error(var3);
      }
   }

   protected Constructor<? extends IDragonController> a() throws NoSuchMethodException {
      return this.m.getConstructor(EntityEnderDragon.class);
   }

   public int b() {
      return this.n;
   }

   @Override
   public String toString() {
      return this.o + " (#" + this.n + ")";
   }

   public static DragonControllerPhase<?> a(int var0) {
      return var0 >= 0 && var0 < l.length ? l[var0] : a;
   }

   public static int c() {
      return l.length;
   }

   private static <T extends IDragonController> DragonControllerPhase<T> a(Class<T> var0, String var1) {
      DragonControllerPhase<T> var2 = new DragonControllerPhase<>(l.length, var0, var1);
      l = Arrays.copyOf(l, l.length + 1);
      l[var2.b()] = var2;
      return var2;
   }
}
