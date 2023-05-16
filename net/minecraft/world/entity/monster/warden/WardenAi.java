package net.minecraft.world.entity.monster.warden;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.List;
import java.util.function.Function;
import net.minecraft.core.BlockPosition;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ai.BehaviorController;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.entity.ai.behavior.BehaviorAttack;
import net.minecraft.world.entity.ai.behavior.BehaviorAttackTargetForget;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.BehaviorGateSingle;
import net.minecraft.world.entity.ai.behavior.BehaviorLook;
import net.minecraft.world.entity.ai.behavior.BehaviorLookTarget;
import net.minecraft.world.entity.ai.behavior.BehaviorNop;
import net.minecraft.world.entity.ai.behavior.BehaviorStrollRandomUnconstrained;
import net.minecraft.world.entity.ai.behavior.BehaviorSwim;
import net.minecraft.world.entity.ai.behavior.BehaviorTarget;
import net.minecraft.world.entity.ai.behavior.BehaviorWalkAwayOutOfRange;
import net.minecraft.world.entity.ai.behavior.BehavorMove;
import net.minecraft.world.entity.ai.behavior.GoToTargetLocation;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.behavior.declarative.Trigger;
import net.minecraft.world.entity.ai.behavior.warden.Digging;
import net.minecraft.world.entity.ai.behavior.warden.Emerging;
import net.minecraft.world.entity.ai.behavior.warden.ForceUnmount;
import net.minecraft.world.entity.ai.behavior.warden.Roar;
import net.minecraft.world.entity.ai.behavior.warden.SetRoarTarget;
import net.minecraft.world.entity.ai.behavior.warden.SetWardenLookTarget;
import net.minecraft.world.entity.ai.behavior.warden.Sniffing;
import net.minecraft.world.entity.ai.behavior.warden.SonicBoom;
import net.minecraft.world.entity.ai.behavior.warden.TryToSniff;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.schedule.Activity;

public class WardenAi {
   private static final float d = 0.5F;
   private static final float e = 0.7F;
   private static final float f = 1.2F;
   private static final int g = 18;
   private static final int h = MathHelper.f(100.0F);
   public static final int a = MathHelper.f(133.59999F);
   public static final int b = MathHelper.f(84.0F);
   private static final int i = MathHelper.f(83.2F);
   public static final int c = 1200;
   private static final int j = 100;
   private static final List<SensorType<? extends Sensor<? super Warden>>> k = List.of(SensorType.d, SensorType.v);
   private static final List<MemoryModuleType<?>> l = List.of(
      MemoryModuleType.g,
      MemoryModuleType.h,
      MemoryModuleType.k,
      MemoryModuleType.l,
      MemoryModuleType.L,
      MemoryModuleType.n,
      MemoryModuleType.m,
      MemoryModuleType.E,
      MemoryModuleType.t,
      MemoryModuleType.o,
      MemoryModuleType.p,
      MemoryModuleType.B,
      MemoryModuleType.ax,
      MemoryModuleType.ay,
      MemoryModuleType.az,
      MemoryModuleType.aA,
      MemoryModuleType.aB,
      MemoryModuleType.aC,
      MemoryModuleType.aD,
      MemoryModuleType.aE,
      MemoryModuleType.aF,
      MemoryModuleType.aG,
      MemoryModuleType.aH,
      MemoryModuleType.aI,
      MemoryModuleType.aJ,
      MemoryModuleType.aK
   );
   private static final BehaviorControl<Warden> m = BehaviorBuilder.a(
      (Function<BehaviorBuilder.b<Warden>, ? extends App<BehaviorBuilder.c<Warden>, Trigger<Warden>>>)(var0 -> var0.group(var0.a(MemoryModuleType.aD))
            .apply(var0, var1 -> (var2, var3, var4) -> {
                  if (var0.a(var1).isPresent()) {
                     var1.a(Unit.a, 1200L);
                  }
      
                  return true;
               }))
   );

   public static void a(Warden var0) {
      var0.dH().a(ImmutableList.of(Activity.y, Activity.z, Activity.x, Activity.k, Activity.w, Activity.v, Activity.b));
   }

   protected static BehaviorController<?> a(Warden var0, Dynamic<?> var1) {
      BehaviorController.b<Warden> var2 = BehaviorController.a(l, k);
      BehaviorController<Warden> var3 = var2.a(var1);
      a(var3);
      b(var3);
      c(var3);
      d(var3);
      g(var3);
      a(var0, var3);
      e(var3);
      f(var3);
      var3.a(ImmutableSet.of(Activity.a));
      var3.b(Activity.b);
      var3.f();
      return var3;
   }

   private static void a(BehaviorController<Warden> var0) {
      var0.a(Activity.a, 0, ImmutableList.of(new BehaviorSwim(0.8F), SetWardenLookTarget.a(), new BehaviorLook(45, 90), new BehavorMove()));
   }

   private static void b(BehaviorController<Warden> var0) {
      var0.a(Activity.y, 5, ImmutableList.of(new Emerging(a)), MemoryModuleType.aB);
   }

   private static void c(BehaviorController<Warden> var0) {
      var0.a(
         Activity.z,
         ImmutableList.of(Pair.of(0, new ForceUnmount()), Pair.of(1, new Digging(h))),
         ImmutableSet.of(Pair.of(MemoryModuleType.ax, MemoryStatus.b), Pair.of(MemoryModuleType.aD, MemoryStatus.b))
      );
   }

   private static void d(BehaviorController<Warden> var0) {
      var0.a(
         Activity.b,
         10,
         ImmutableList.of(
            SetRoarTarget.a(Warden::fT),
            TryToSniff.a(),
            new BehaviorGateSingle(
               ImmutableMap.of(MemoryModuleType.aA, MemoryStatus.b),
               ImmutableList.of(Pair.of(BehaviorStrollRandomUnconstrained.a(0.5F), 2), Pair.of(new BehaviorNop(30, 60), 1))
            )
         )
      );
   }

   private static void e(BehaviorController<Warden> var0) {
      var0.a(Activity.w, 5, ImmutableList.of(SetRoarTarget.a(Warden::fT), GoToTargetLocation.a(MemoryModuleType.ay, 2, 0.7F)), MemoryModuleType.ay);
   }

   private static void f(BehaviorController<Warden> var0) {
      var0.a(Activity.v, 5, ImmutableList.of(SetRoarTarget.a(Warden::fT), new Sniffing(i)), MemoryModuleType.aA);
   }

   private static void g(BehaviorController<Warden> var0) {
      var0.a(Activity.x, 10, ImmutableList.of(new Roar()), MemoryModuleType.ax);
   }

   private static void a(Warden var0, BehaviorController<Warden> var1) {
      var1.a(
         Activity.k,
         10,
         ImmutableList.of(
            m,
            BehaviorAttackTargetForget.a(var1x -> !var0.fS().d() || !var0.a(var1x), WardenAi::b, false),
            BehaviorLookTarget.a(var1x -> a(var0, var1x), (float)var0.b(GenericAttributes.b)),
            BehaviorWalkAwayOutOfRange.a(1.2F),
            new SonicBoom(),
            BehaviorAttack.a(18)
         ),
         MemoryModuleType.o
      );
   }

   private static boolean a(Warden var0, EntityLiving var1) {
      return var0.dH().c(MemoryModuleType.o).filter(var1x -> var1x == var1).isPresent();
   }

   private static void b(Warden var0, EntityLiving var1) {
      if (!var0.a(var1)) {
         var0.b(var1);
      }

      a((EntityLiving)var0);
   }

   public static void a(EntityLiving var0) {
      if (var0.dH().a(MemoryModuleType.aD)) {
         var0.dH().a(MemoryModuleType.aD, Unit.a, 1200L);
      }
   }

   public static void a(Warden var0, BlockPosition var1) {
      if (var0.H.p_().a(var1) && !var0.fT().isPresent() && !var0.dH().c(MemoryModuleType.o).isPresent()) {
         a((EntityLiving)var0);
         var0.dH().a(MemoryModuleType.aF, Unit.a, 100L);
         var0.dH().a(MemoryModuleType.n, new BehaviorTarget(var1), 100L);
         var0.dH().a(MemoryModuleType.ay, var1, 100L);
         var0.dH().b(MemoryModuleType.m);
      }
   }
}
