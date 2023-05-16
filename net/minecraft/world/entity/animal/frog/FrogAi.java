package net.minecraft.world.entity.animal.frog;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPosition;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.tags.TagsBlock;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.ai.BehaviorController;
import net.minecraft.world.entity.ai.behavior.AnimalPanic;
import net.minecraft.world.entity.ai.behavior.BehaviorAttackTargetForget;
import net.minecraft.world.entity.ai.behavior.BehaviorAttackTargetSet;
import net.minecraft.world.entity.ai.behavior.BehaviorGate;
import net.minecraft.world.entity.ai.behavior.BehaviorGateSingle;
import net.minecraft.world.entity.ai.behavior.BehaviorLook;
import net.minecraft.world.entity.ai.behavior.BehaviorLookWalk;
import net.minecraft.world.entity.ai.behavior.BehaviorMakeLoveAnimal;
import net.minecraft.world.entity.ai.behavior.BehaviorStrollRandomUnconstrained;
import net.minecraft.world.entity.ai.behavior.BehaviorUtil;
import net.minecraft.world.entity.ai.behavior.BehavorMove;
import net.minecraft.world.entity.ai.behavior.CountDownCooldownTicks;
import net.minecraft.world.entity.ai.behavior.Croak;
import net.minecraft.world.entity.ai.behavior.FollowTemptation;
import net.minecraft.world.entity.ai.behavior.LongJumpMidJump;
import net.minecraft.world.entity.ai.behavior.LongJumpToPreferredBlock;
import net.minecraft.world.entity.ai.behavior.LongJumpToRandomPos;
import net.minecraft.world.entity.ai.behavior.SetEntityLookTargetSometimes;
import net.minecraft.world.entity.ai.behavior.TryFindLand;
import net.minecraft.world.entity.ai.behavior.TryFindLandNearWater;
import net.minecraft.world.entity.ai.behavior.TryLaySpawnOnWaterNearLand;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.item.crafting.RecipeItemStack;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.pathfinder.PathfinderNormal;

public class FrogAi {
   private static final float a = 2.0F;
   private static final float b = 1.0F;
   private static final float c = 1.0F;
   private static final float d = 1.0F;
   private static final float e = 0.75F;
   private static final UniformInt f = UniformInt.a(100, 140);
   private static final int g = 2;
   private static final int h = 4;
   private static final float i = 1.5F;
   private static final float j = 1.25F;

   protected static void a(Frog var0, RandomSource var1) {
      var0.dH().a(MemoryModuleType.R, f.a(var1));
   }

   protected static BehaviorController<?> a(BehaviorController<Frog> var0) {
      b(var0);
      c(var0);
      d(var0);
      e(var0);
      g(var0);
      f(var0);
      var0.a(ImmutableSet.of(Activity.a));
      var0.b(Activity.b);
      var0.f();
      return var0;
   }

   private static void b(BehaviorController<Frog> var0) {
      var0.a(
         Activity.a,
         0,
         ImmutableList.of(
            new AnimalPanic(2.0F),
            new BehaviorLook(45, 90),
            new BehavorMove(),
            new CountDownCooldownTicks(MemoryModuleType.O),
            new CountDownCooldownTicks(MemoryModuleType.R)
         )
      );
   }

   private static void c(BehaviorController<Frog> var0) {
      var0.a(
         Activity.b,
         ImmutableList.of(
            Pair.of(0, SetEntityLookTargetSometimes.a(EntityTypes.bt, 6.0F, UniformInt.a(30, 60))),
            Pair.of(0, new BehaviorMakeLoveAnimal(EntityTypes.O, 1.0F)),
            Pair.of(1, new FollowTemptation(var0x -> 1.25F)),
            Pair.of(2, BehaviorAttackTargetSet.a(FrogAi::b, var0x -> var0x.dH().c(MemoryModuleType.B))),
            Pair.of(3, TryFindLand.a(6, 1.0F)),
            Pair.of(
               4,
               new BehaviorGateSingle(
                  ImmutableMap.of(MemoryModuleType.m, MemoryStatus.b),
                  ImmutableList.of(
                     Pair.of(BehaviorStrollRandomUnconstrained.a(1.0F), 1),
                     Pair.of(BehaviorLookWalk.a(1.0F, 3), 1),
                     Pair.of(new Croak(), 3),
                     Pair.of(BehaviorBuilder.a(Entity::ax), 2)
                  )
               )
            )
         ),
         ImmutableSet.of(Pair.of(MemoryModuleType.S, MemoryStatus.b), Pair.of(MemoryModuleType.W, MemoryStatus.b))
      );
   }

   private static void d(BehaviorController<Frog> var0) {
      var0.a(
         Activity.t,
         ImmutableList.of(
            Pair.of(0, SetEntityLookTargetSometimes.a(EntityTypes.bt, 6.0F, UniformInt.a(30, 60))),
            Pair.of(1, new FollowTemptation(var0x -> 1.25F)),
            Pair.of(2, BehaviorAttackTargetSet.a(FrogAi::b, var0x -> var0x.dH().c(MemoryModuleType.B))),
            Pair.of(3, TryFindLand.a(8, 1.5F)),
            Pair.of(
               5,
               new BehaviorGate(
                  ImmutableMap.of(MemoryModuleType.m, MemoryStatus.b),
                  ImmutableSet.of(),
                  BehaviorGate.Order.a,
                  BehaviorGate.Execution.b,
                  ImmutableList.of(
                     Pair.of(BehaviorStrollRandomUnconstrained.c(0.75F), 1),
                     Pair.of(BehaviorStrollRandomUnconstrained.a(1.0F, true), 1),
                     Pair.of(BehaviorLookWalk.a(1.0F, 3), 1),
                     Pair.of(BehaviorBuilder.a(Entity::aW), 5)
                  )
               )
            )
         ),
         ImmutableSet.of(Pair.of(MemoryModuleType.S, MemoryStatus.b), Pair.of(MemoryModuleType.W, MemoryStatus.a))
      );
   }

   private static void e(BehaviorController<Frog> var0) {
      var0.a(
         Activity.u,
         ImmutableList.of(
            Pair.of(0, SetEntityLookTargetSometimes.a(EntityTypes.bt, 6.0F, UniformInt.a(30, 60))),
            Pair.of(1, BehaviorAttackTargetSet.a(FrogAi::b, var0x -> var0x.dH().c(MemoryModuleType.B))),
            Pair.of(2, TryFindLandNearWater.a(8, 1.0F)),
            Pair.of(3, TryLaySpawnOnWaterNearLand.a(Blocks.sh)),
            Pair.of(
               4,
               new BehaviorGateSingle(
                  ImmutableList.of(
                     Pair.of(BehaviorStrollRandomUnconstrained.a(1.0F), 2),
                     Pair.of(BehaviorLookWalk.a(1.0F, 3), 1),
                     Pair.of(new Croak(), 2),
                     Pair.of(BehaviorBuilder.a(Entity::ax), 1)
                  )
               )
            )
         ),
         ImmutableSet.of(Pair.of(MemoryModuleType.S, MemoryStatus.b), Pair.of(MemoryModuleType.X, MemoryStatus.a))
      );
   }

   private static void f(BehaviorController<Frog> var0) {
      var0.a(
         Activity.q,
         ImmutableList.of(
            Pair.of(0, new LongJumpMidJump(f, SoundEffects.iB)),
            Pair.of(1, new LongJumpToPreferredBlock<>(f, 2, 4, 1.5F, var0x -> SoundEffects.iA, TagsBlock.bF, 0.5F, FrogAi::a))
         ),
         ImmutableSet.of(
            Pair.of(MemoryModuleType.N, MemoryStatus.b),
            Pair.of(MemoryModuleType.r, MemoryStatus.b),
            Pair.of(MemoryModuleType.R, MemoryStatus.b),
            Pair.of(MemoryModuleType.W, MemoryStatus.b)
         )
      );
   }

   private static void g(BehaviorController<Frog> var0) {
      var0.a(Activity.s, 0, ImmutableList.of(BehaviorAttackTargetForget.a(), new ShootTongue(SoundEffects.iC, SoundEffects.ix)), MemoryModuleType.o);
   }

   private static <E extends EntityInsentient> boolean a(E var0, BlockPosition var1) {
      World var2 = var0.H;
      BlockPosition var3 = var1.d();
      if (var2.b_(var1).c() && var2.b_(var3).c() && var2.b_(var1.c()).c()) {
         IBlockData var4 = var2.a_(var1);
         IBlockData var5 = var2.a_(var3);
         if (!var4.a(TagsBlock.bF) && !var5.a(TagsBlock.bF)) {
            PathType var6 = PathfinderNormal.a(var2, var1.j());
            PathType var7 = PathfinderNormal.a(var2, var3.j());
            return var6 != PathType.e && (!var4.h() || var7 != PathType.e) ? LongJumpToRandomPos.a(var0, var1) : true;
         } else {
            return true;
         }
      } else {
         return false;
      }
   }

   private static boolean b(Frog var0) {
      return !BehaviorUtil.a(var0);
   }

   public static void a(Frog var0) {
      var0.dH().a(ImmutableList.of(Activity.s, Activity.u, Activity.q, Activity.t, Activity.b));
   }

   public static RecipeItemStack a() {
      return Frog.bS;
   }
}
