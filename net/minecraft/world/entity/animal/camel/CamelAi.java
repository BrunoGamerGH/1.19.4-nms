package net.minecraft.world.entity.animal.camel;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import java.util.function.Predicate;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.EntityCreature;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.ai.BehaviorController;
import net.minecraft.world.entity.ai.behavior.AnimalPanic;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BehaviorFollowAdult;
import net.minecraft.world.entity.ai.behavior.BehaviorGateSingle;
import net.minecraft.world.entity.ai.behavior.BehaviorLook;
import net.minecraft.world.entity.ai.behavior.BehaviorLookWalk;
import net.minecraft.world.entity.ai.behavior.BehaviorMakeLoveAnimal;
import net.minecraft.world.entity.ai.behavior.BehaviorNop;
import net.minecraft.world.entity.ai.behavior.BehaviorStrollRandomUnconstrained;
import net.minecraft.world.entity.ai.behavior.BehaviorSwim;
import net.minecraft.world.entity.ai.behavior.BehavorMove;
import net.minecraft.world.entity.ai.behavior.CountDownCooldownTicks;
import net.minecraft.world.entity.ai.behavior.FollowTemptation;
import net.minecraft.world.entity.ai.behavior.RandomLookAround;
import net.minecraft.world.entity.ai.behavior.SetEntityLookTargetSometimes;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.item.crafting.RecipeItemStack;

public class CamelAi {
   private static final float a = 4.0F;
   private static final float b = 2.0F;
   private static final float c = 2.5F;
   private static final float d = 2.5F;
   private static final float e = 1.0F;
   private static final UniformInt f = UniformInt.a(5, 16);
   private static final ImmutableList<SensorType<? extends Sensor<? super Camel>>> g = ImmutableList.of(SensorType.c, SensorType.f, SensorType.s, SensorType.n);
   private static final ImmutableList<MemoryModuleType<?>> h = ImmutableList.of(
      MemoryModuleType.Y,
      MemoryModuleType.x,
      MemoryModuleType.y,
      MemoryModuleType.m,
      MemoryModuleType.n,
      MemoryModuleType.E,
      MemoryModuleType.t,
      MemoryModuleType.h,
      MemoryModuleType.N,
      MemoryModuleType.O,
      MemoryModuleType.P,
      MemoryModuleType.Q,
      new MemoryModuleType[]{MemoryModuleType.r, MemoryModuleType.J}
   );

   protected static void a(Camel var0, RandomSource var1) {
   }

   public static BehaviorController.b<Camel> a() {
      return BehaviorController.a(h, g);
   }

   protected static BehaviorController<?> a(BehaviorController<Camel> var0) {
      b(var0);
      c(var0);
      var0.a(ImmutableSet.of(Activity.a));
      var0.b(Activity.b);
      var0.f();
      return var0;
   }

   private static void b(BehaviorController<Camel> var0) {
      var0.a(
         Activity.a,
         0,
         ImmutableList.of(
            new BehaviorSwim(0.8F),
            new CamelAi.a(4.0F),
            new BehaviorLook(45, 90),
            new BehavorMove(),
            new CountDownCooldownTicks(MemoryModuleType.O),
            new CountDownCooldownTicks(MemoryModuleType.P)
         )
      );
   }

   private static void c(BehaviorController<Camel> var0) {
      var0.a(
         Activity.b,
         ImmutableList.of(
            Pair.of(0, SetEntityLookTargetSometimes.a(EntityTypes.bt, 6.0F, UniformInt.a(30, 60))),
            Pair.of(1, new BehaviorMakeLoveAnimal(EntityTypes.l, 1.0F)),
            Pair.of(2, new FollowTemptation(var0x -> 2.5F)),
            Pair.of(3, BehaviorBuilder.a(Predicate.not(Camel::r), BehaviorFollowAdult.a(f, 2.5F))),
            Pair.of(4, new RandomLookAround(UniformInt.a(150, 250), 30.0F, 0.0F, 0.0F)),
            Pair.of(
               5,
               new BehaviorGateSingle(
                  ImmutableMap.of(MemoryModuleType.m, MemoryStatus.b),
                  ImmutableList.of(
                     Pair.of(BehaviorBuilder.a(Predicate.not(Camel::r), BehaviorStrollRandomUnconstrained.a(2.0F)), 1),
                     Pair.of(BehaviorBuilder.a(Predicate.not(Camel::r), BehaviorLookWalk.a(2.0F, 3)), 1),
                     Pair.of(new CamelAi.b(20), 1),
                     Pair.of(new BehaviorNop(30, 60), 1)
                  )
               )
            )
         )
      );
   }

   public static void a(Camel var0) {
      var0.dH().a(ImmutableList.of(Activity.b));
   }

   public static RecipeItemStack b() {
      return Camel.bS;
   }

   public static class a extends AnimalPanic {
      public a(float var0) {
         super(var0);
      }

      @Override
      protected void b(WorldServer var0, EntityCreature var1, long var2) {
         if (var1 instanceof Camel var4) {
            var4.gf();
         }

         super.b(var0, var1, var2);
      }
   }

   public static class b extends Behavior<Camel> {
      private final int c;

      public b(int var0) {
         super(ImmutableMap.of());
         this.c = var0 * 20;
      }

      protected boolean a(WorldServer var0, Camel var1) {
         return !var1.aT() && var1.gg() >= (long)this.c && !var1.fI() && var1.ax() && !var1.cL();
      }

      protected void a(WorldServer var0, Camel var1, long var2) {
         if (var1.ga()) {
            var1.ge();
         } else if (!var1.fS()) {
            var1.gd();
         }
      }
   }
}
