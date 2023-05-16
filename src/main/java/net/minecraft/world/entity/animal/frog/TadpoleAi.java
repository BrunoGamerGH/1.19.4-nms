package net.minecraft.world.entity.animal.frog;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.ai.BehaviorController;
import net.minecraft.world.entity.ai.behavior.AnimalPanic;
import net.minecraft.world.entity.ai.behavior.BehaviorGate;
import net.minecraft.world.entity.ai.behavior.BehaviorLook;
import net.minecraft.world.entity.ai.behavior.BehaviorLookWalk;
import net.minecraft.world.entity.ai.behavior.BehaviorStrollRandomUnconstrained;
import net.minecraft.world.entity.ai.behavior.BehavorMove;
import net.minecraft.world.entity.ai.behavior.CountDownCooldownTicks;
import net.minecraft.world.entity.ai.behavior.FollowTemptation;
import net.minecraft.world.entity.ai.behavior.SetEntityLookTargetSometimes;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.schedule.Activity;

public class TadpoleAi {
   private static final float a = 2.0F;
   private static final float b = 0.5F;
   private static final float c = 1.25F;

   protected static BehaviorController<?> a(BehaviorController<Tadpole> var0) {
      b(var0);
      c(var0);
      var0.a(ImmutableSet.of(Activity.a));
      var0.b(Activity.b);
      var0.f();
      return var0;
   }

   private static void b(BehaviorController<Tadpole> var0) {
      var0.a(
         Activity.a, 0, ImmutableList.of(new AnimalPanic(2.0F), new BehaviorLook(45, 90), new BehavorMove(), new CountDownCooldownTicks(MemoryModuleType.O))
      );
   }

   private static void c(BehaviorController<Tadpole> var0) {
      var0.a(
         Activity.b,
         ImmutableList.of(
            Pair.of(0, SetEntityLookTargetSometimes.a(EntityTypes.bt, 6.0F, UniformInt.a(30, 60))),
            Pair.of(1, new FollowTemptation(var0x -> 1.25F)),
            Pair.of(
               2,
               new BehaviorGate(
                  ImmutableMap.of(MemoryModuleType.m, MemoryStatus.b),
                  ImmutableSet.of(),
                  BehaviorGate.Order.a,
                  BehaviorGate.Execution.b,
                  ImmutableList.of(
                     Pair.of(BehaviorStrollRandomUnconstrained.c(0.5F), 2), Pair.of(BehaviorLookWalk.a(0.5F, 3), 3), Pair.of(BehaviorBuilder.a(Entity::aW), 5)
                  )
               )
            )
         )
      );
   }

   public static void a(Tadpole var0) {
      var0.dH().a(ImmutableList.of(Activity.b));
   }
}
