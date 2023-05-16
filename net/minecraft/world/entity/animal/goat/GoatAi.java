package net.minecraft.world.entity.animal.goat;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.ai.BehaviorController;
import net.minecraft.world.entity.ai.behavior.AnimalPanic;
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
import net.minecraft.world.entity.ai.behavior.LongJumpMidJump;
import net.minecraft.world.entity.ai.behavior.LongJumpToRandomPos;
import net.minecraft.world.entity.ai.behavior.PrepareRamNearestTarget;
import net.minecraft.world.entity.ai.behavior.RamTarget;
import net.minecraft.world.entity.ai.behavior.SetEntityLookTargetSometimes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.targeting.PathfinderTargetCondition;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeItemStack;

public class GoatAi {
   public static final int a = 20;
   public static final int b = 7;
   private static final UniformInt i = UniformInt.a(5, 16);
   private static final float j = 1.0F;
   private static final float k = 1.0F;
   private static final float l = 1.25F;
   private static final float m = 1.25F;
   private static final float n = 2.0F;
   private static final float o = 1.25F;
   private static final UniformInt p = UniformInt.a(600, 1200);
   public static final int c = 5;
   public static final int d = 5;
   public static final float e = 1.5F;
   private static final UniformInt q = UniformInt.a(600, 6000);
   private static final UniformInt r = UniformInt.a(100, 300);
   private static final PathfinderTargetCondition s = PathfinderTargetCondition.a().a(var0 -> !var0.ae().equals(EntityTypes.U) && var0.H.p_().a(var0.cD()));
   private static final float t = 3.0F;
   public static final int f = 4;
   public static final float g = 2.5F;
   public static final float h = 1.0F;

   protected static void a(Goat var0, RandomSource var1) {
      var0.dH().a(MemoryModuleType.R, p.a(var1));
      var0.dH().a(MemoryModuleType.U, q.a(var1));
   }

   protected static BehaviorController<?> a(BehaviorController<Goat> var0) {
      b(var0);
      c(var0);
      d(var0);
      e(var0);
      var0.a(ImmutableSet.of(Activity.a));
      var0.b(Activity.b);
      var0.f();
      return var0;
   }

   private static void b(BehaviorController<Goat> var0) {
      var0.a(
         Activity.a,
         0,
         ImmutableList.of(
            new BehaviorSwim(0.8F),
            new AnimalPanic(2.0F),
            new BehaviorLook(45, 90),
            new BehavorMove(),
            new CountDownCooldownTicks(MemoryModuleType.O),
            new CountDownCooldownTicks(MemoryModuleType.R),
            new CountDownCooldownTicks(MemoryModuleType.U)
         )
      );
   }

   private static void c(BehaviorController<Goat> var0) {
      var0.a(
         Activity.b,
         ImmutableList.of(
            Pair.of(0, SetEntityLookTargetSometimes.a(EntityTypes.bt, 6.0F, UniformInt.a(30, 60))),
            Pair.of(0, new BehaviorMakeLoveAnimal(EntityTypes.U, 1.0F)),
            Pair.of(1, new FollowTemptation(var0x -> 1.25F)),
            Pair.of(2, BehaviorFollowAdult.a(i, 1.25F)),
            Pair.of(
               3,
               new BehaviorGateSingle(
                  ImmutableList.of(
                     Pair.of(BehaviorStrollRandomUnconstrained.a(1.0F), 2), Pair.of(BehaviorLookWalk.a(1.0F, 3), 2), Pair.of(new BehaviorNop(30, 60), 1)
                  )
               )
            )
         ),
         ImmutableSet.of(Pair.of(MemoryModuleType.V, MemoryStatus.b), Pair.of(MemoryModuleType.S, MemoryStatus.b))
      );
   }

   private static void d(BehaviorController<Goat> var0) {
      var0.a(
         Activity.q,
         ImmutableList.of(
            Pair.of(0, new LongJumpMidJump(p, SoundEffects.jN)),
            Pair.of(1, new LongJumpToRandomPos<>(p, 5, 5, 1.5F, var0x -> var0x.gc() ? SoundEffects.jI : SoundEffects.jy))
         ),
         ImmutableSet.of(
            Pair.of(MemoryModuleType.N, MemoryStatus.b),
            Pair.of(MemoryModuleType.r, MemoryStatus.b),
            Pair.of(MemoryModuleType.m, MemoryStatus.b),
            Pair.of(MemoryModuleType.R, MemoryStatus.b)
         )
      );
   }

   private static void e(BehaviorController<Goat> var0) {
      var0.a(
         Activity.r,
         ImmutableList.of(
            Pair.of(
               0,
               new RamTarget(
                  var0x -> var0x.gc() ? r : q,
                  s,
                  3.0F,
                  var0x -> var0x.y_() ? 1.0 : 2.5,
                  var0x -> var0x.gc() ? SoundEffects.jL : SoundEffects.jB,
                  var0x -> var0x.gc() ? SoundEffects.jM : SoundEffects.jC
               )
            ),
            Pair.of(
               1,
               new PrepareRamNearestTarget<>(var0x -> var0x.gc() ? r.a() : q.a(), 4, 7, 1.25F, s, 20, var0x -> var0x.gc() ? SoundEffects.jK : SoundEffects.jA)
            )
         ),
         ImmutableSet.of(Pair.of(MemoryModuleType.N, MemoryStatus.b), Pair.of(MemoryModuleType.r, MemoryStatus.b), Pair.of(MemoryModuleType.U, MemoryStatus.b))
      );
   }

   public static void a(Goat var0) {
      var0.dH().a(ImmutableList.of(Activity.r, Activity.q, Activity.b));
   }

   public static RecipeItemStack a() {
      return RecipeItemStack.a(Items.oE);
   }
}
