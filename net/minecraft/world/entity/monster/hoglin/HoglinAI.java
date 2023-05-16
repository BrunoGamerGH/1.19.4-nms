package net.minecraft.world.entity.monster.hoglin;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.BlockPosition;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.util.TimeRange;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.EntityAgeable;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.ai.BehaviorController;
import net.minecraft.world.entity.ai.behavior.BehaviorAttack;
import net.minecraft.world.entity.ai.behavior.BehaviorAttackTargetForget;
import net.minecraft.world.entity.ai.behavior.BehaviorAttackTargetSet;
import net.minecraft.world.entity.ai.behavior.BehaviorFollowAdult;
import net.minecraft.world.entity.ai.behavior.BehaviorGateSingle;
import net.minecraft.world.entity.ai.behavior.BehaviorLook;
import net.minecraft.world.entity.ai.behavior.BehaviorLookWalk;
import net.minecraft.world.entity.ai.behavior.BehaviorMakeLoveAnimal;
import net.minecraft.world.entity.ai.behavior.BehaviorNop;
import net.minecraft.world.entity.ai.behavior.BehaviorPacify;
import net.minecraft.world.entity.ai.behavior.BehaviorRemoveMemory;
import net.minecraft.world.entity.ai.behavior.BehaviorStrollRandomUnconstrained;
import net.minecraft.world.entity.ai.behavior.BehaviorUtil;
import net.minecraft.world.entity.ai.behavior.BehaviorWalkAway;
import net.minecraft.world.entity.ai.behavior.BehaviorWalkAwayOutOfRange;
import net.minecraft.world.entity.ai.behavior.BehavorMove;
import net.minecraft.world.entity.ai.behavior.SetEntityLookTargetSometimes;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.schedule.Activity;

public class HoglinAI {
   public static final int a = 8;
   public static final int b = 4;
   private static final UniformInt c = TimeRange.a(5, 20);
   private static final int d = 200;
   private static final int e = 8;
   private static final int f = 15;
   private static final int g = 40;
   private static final int h = 15;
   private static final int i = 200;
   private static final UniformInt j = UniformInt.a(5, 16);
   private static final float k = 1.0F;
   private static final float l = 1.3F;
   private static final float m = 0.6F;
   private static final float n = 0.4F;
   private static final float o = 0.6F;

   protected static BehaviorController<?> a(BehaviorController<EntityHoglin> var0) {
      b(var0);
      c(var0);
      d(var0);
      e(var0);
      var0.a(ImmutableSet.of(Activity.a));
      var0.b(Activity.b);
      var0.f();
      return var0;
   }

   private static void b(BehaviorController<EntityHoglin> var0) {
      var0.a(Activity.a, 0, ImmutableList.of(new BehaviorLook(45, 90), new BehavorMove()));
   }

   private static void c(BehaviorController<EntityHoglin> var0) {
      var0.a(
         Activity.b,
         10,
         ImmutableList.of(
            BehaviorPacify.a(MemoryModuleType.av, 200),
            new BehaviorMakeLoveAnimal(EntityTypes.W, 0.6F),
            BehaviorWalkAway.a(MemoryModuleType.av, 1.0F, 8, true),
            BehaviorAttackTargetSet.a(HoglinAI::d),
            BehaviorBuilder.a(EntityHoglin::r, BehaviorWalkAway.b(MemoryModuleType.ap, 0.4F, 8, false)),
            SetEntityLookTargetSometimes.a(8.0F, UniformInt.a(30, 60)),
            BehaviorFollowAdult.a(j, 0.6F),
            a()
         )
      );
   }

   private static void d(BehaviorController<EntityHoglin> var0) {
      var0.a(
         Activity.k,
         10,
         ImmutableList.of(
            BehaviorPacify.a(MemoryModuleType.av, 200),
            new BehaviorMakeLoveAnimal(EntityTypes.W, 0.6F),
            BehaviorWalkAwayOutOfRange.a(1.0F),
            BehaviorBuilder.a(EntityHoglin::r, BehaviorAttack.a(40)),
            BehaviorBuilder.a(EntityAgeable::y_, BehaviorAttack.a(15)),
            BehaviorAttackTargetForget.a(),
            BehaviorRemoveMemory.a(HoglinAI::i, MemoryModuleType.o)
         ),
         MemoryModuleType.o
      );
   }

   private static void e(BehaviorController<EntityHoglin> var0) {
      var0.a(
         Activity.n,
         10,
         ImmutableList.of(
            BehaviorWalkAway.b(MemoryModuleType.z, 1.3F, 15, false),
            a(),
            SetEntityLookTargetSometimes.a(8.0F, UniformInt.a(30, 60)),
            BehaviorRemoveMemory.a(HoglinAI::e, MemoryModuleType.z)
         ),
         MemoryModuleType.z
      );
   }

   private static BehaviorGateSingle<EntityHoglin> a() {
      return new BehaviorGateSingle<>(
         ImmutableList.of(Pair.of(BehaviorStrollRandomUnconstrained.a(0.4F), 2), Pair.of(BehaviorLookWalk.a(0.4F, 3), 2), Pair.of(new BehaviorNop(30, 60), 1))
      );
   }

   protected static void a(EntityHoglin var0) {
      BehaviorController<EntityHoglin> var1 = var0.dH();
      Activity var2 = var1.g().orElse(null);
      var1.a(ImmutableList.of(Activity.k, Activity.n, Activity.b));
      Activity var3 = var1.g().orElse(null);
      if (var2 != var3) {
         b(var0).ifPresent(var0::b);
      }

      var0.v(var1.a(MemoryModuleType.o));
   }

   protected static void a(EntityHoglin var0, EntityLiving var1) {
      if (!var0.y_()) {
         if (var1.ae() == EntityTypes.aw && f(var0)) {
            e(var0, var1);
            c(var0, var1);
         } else {
            h(var0, var1);
         }
      }
   }

   private static void c(EntityHoglin var0, EntityLiving var1) {
      g(var0).forEach(var1x -> d(var1x, var1));
   }

   private static void d(EntityHoglin var0, EntityLiving var1) {
      BehaviorController<EntityHoglin> var3 = var0.dH();
      EntityLiving var2 = BehaviorUtil.a(var0, var3.c(MemoryModuleType.z), var1);
      var2 = BehaviorUtil.a(var0, var3.c(MemoryModuleType.o), var2);
      e(var0, var2);
   }

   private static void e(EntityHoglin var0, EntityLiving var1) {
      var0.dH().b(MemoryModuleType.o);
      var0.dH().b(MemoryModuleType.m);
      var0.dH().a(MemoryModuleType.z, var1, (long)c.a(var0.H.z));
   }

   private static Optional<? extends EntityLiving> d(EntityHoglin var0) {
      return !c(var0) && !i(var0) ? var0.dH().c(MemoryModuleType.l) : Optional.empty();
   }

   static boolean a(EntityHoglin var0, BlockPosition var1) {
      Optional<BlockPosition> var2 = var0.dH().c(MemoryModuleType.av);
      return var2.isPresent() && var2.get().a(var1, 8.0);
   }

   private static boolean e(EntityHoglin var0) {
      return var0.r() && !f(var0);
   }

   private static boolean f(EntityHoglin var0) {
      if (var0.y_()) {
         return false;
      } else {
         int var1 = var0.dH().c(MemoryModuleType.ar).orElse(0);
         int var2 = var0.dH().c(MemoryModuleType.as).orElse(0) + 1;
         return var1 > var2;
      }
   }

   protected static void b(EntityHoglin var0, EntityLiving var1) {
      BehaviorController<EntityHoglin> var2 = var0.dH();
      var2.b(MemoryModuleType.aw);
      var2.b(MemoryModuleType.r);
      if (var0.y_()) {
         d(var0, var1);
      } else {
         f(var0, var1);
      }
   }

   private static void f(EntityHoglin var0, EntityLiving var1) {
      if (!var0.dH().c(Activity.n) || var1.ae() != EntityTypes.aw) {
         if (var1.ae() != EntityTypes.W) {
            if (!BehaviorUtil.a(var0, var1, 4.0)) {
               if (Sensor.c(var0, var1)) {
                  g(var0, var1);
                  h(var0, var1);
               }
            }
         }
      }
   }

   private static void g(EntityHoglin var0, EntityLiving var1) {
      BehaviorController<EntityHoglin> var2 = var0.dH();
      var2.b(MemoryModuleType.E);
      var2.b(MemoryModuleType.r);
      var2.a(MemoryModuleType.o, var1, 200L);
   }

   private static void h(EntityHoglin var0, EntityLiving var1) {
      g(var0).forEach(var1x -> i(var1x, var1));
   }

   private static void i(EntityHoglin var0, EntityLiving var1) {
      if (!c(var0)) {
         Optional<EntityLiving> var2 = var0.dH().c(MemoryModuleType.o);
         EntityLiving var3 = BehaviorUtil.a(var0, var2, var1);
         g(var0, var3);
      }
   }

   public static Optional<SoundEffect> b(EntityHoglin var0) {
      return var0.dH().g().map(var1 -> a(var0, var1));
   }

   private static SoundEffect a(EntityHoglin var0, Activity var1) {
      if (var1 == Activity.n || var0.w()) {
         return SoundEffects.kJ;
      } else if (var1 == Activity.k) {
         return SoundEffects.kE;
      } else {
         return h(var0) ? SoundEffects.kJ : SoundEffects.kD;
      }
   }

   private static List<EntityHoglin> g(EntityHoglin var0) {
      return var0.dH().c(MemoryModuleType.ao).orElse(ImmutableList.of());
   }

   private static boolean h(EntityHoglin var0) {
      return var0.dH().a(MemoryModuleType.av);
   }

   private static boolean i(EntityHoglin var0) {
      return var0.dH().a(MemoryModuleType.r);
   }

   protected static boolean c(EntityHoglin var0) {
      return var0.dH().a(MemoryModuleType.aw);
   }
}
