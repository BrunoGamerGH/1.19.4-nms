package net.minecraft.world.entity.animal.axolotl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import java.util.Optional;
import net.minecraft.core.BlockPosition;
import net.minecraft.tags.TagsItem;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.ai.BehaviorController;
import net.minecraft.world.entity.ai.behavior.BehaviorAttack;
import net.minecraft.world.entity.ai.behavior.BehaviorAttackTargetForget;
import net.minecraft.world.entity.ai.behavior.BehaviorAttackTargetSet;
import net.minecraft.world.entity.ai.behavior.BehaviorFollowAdult;
import net.minecraft.world.entity.ai.behavior.BehaviorGate;
import net.minecraft.world.entity.ai.behavior.BehaviorGateSingle;
import net.minecraft.world.entity.ai.behavior.BehaviorLook;
import net.minecraft.world.entity.ai.behavior.BehaviorLookWalk;
import net.minecraft.world.entity.ai.behavior.BehaviorMakeLoveAnimal;
import net.minecraft.world.entity.ai.behavior.BehaviorPosition;
import net.minecraft.world.entity.ai.behavior.BehaviorRemoveMemory;
import net.minecraft.world.entity.ai.behavior.BehaviorStrollRandomUnconstrained;
import net.minecraft.world.entity.ai.behavior.BehaviorUtil;
import net.minecraft.world.entity.ai.behavior.BehaviorWalkAwayOutOfRange;
import net.minecraft.world.entity.ai.behavior.BehavorMove;
import net.minecraft.world.entity.ai.behavior.CountDownCooldownTicks;
import net.minecraft.world.entity.ai.behavior.FollowTemptation;
import net.minecraft.world.entity.ai.behavior.SetEntityLookTargetSometimes;
import net.minecraft.world.entity.ai.behavior.TryFindWater;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.item.crafting.RecipeItemStack;
import net.minecraft.world.level.World;

public class AxolotlAi {
   private static final UniformInt a = UniformInt.a(5, 16);
   private static final float b = 0.2F;
   private static final float c = 0.15F;
   private static final float d = 0.5F;
   private static final float e = 0.6F;
   private static final float f = 0.6F;

   protected static BehaviorController<?> a(BehaviorController<Axolotl> var0) {
      d(var0);
      e(var0);
      c(var0);
      b(var0);
      var0.a(ImmutableSet.of(Activity.a));
      var0.b(Activity.b);
      var0.f();
      return var0;
   }

   private static void b(BehaviorController<Axolotl> var0) {
      var0.a(
         Activity.p,
         ImmutableList.of(Pair.of(0, new PlayDead()), Pair.of(1, BehaviorRemoveMemory.a(BehaviorUtil::a, MemoryModuleType.M))),
         ImmutableSet.of(Pair.of(MemoryModuleType.M, MemoryStatus.a)),
         ImmutableSet.of(MemoryModuleType.M)
      );
   }

   private static void c(BehaviorController<Axolotl> var0) {
      var0.a(
         Activity.k,
         0,
         ImmutableList.of(
            BehaviorAttackTargetForget.a(Axolotl::a),
            BehaviorWalkAwayOutOfRange.a(AxolotlAi::b),
            BehaviorAttack.a(20),
            BehaviorRemoveMemory.a(BehaviorUtil::a, MemoryModuleType.o)
         ),
         MemoryModuleType.o
      );
   }

   private static void d(BehaviorController<Axolotl> var0) {
      var0.a(
         Activity.a, 0, ImmutableList.of(new BehaviorLook(45, 90), new BehavorMove(), ValidatePlayDead.a(), new CountDownCooldownTicks(MemoryModuleType.O))
      );
   }

   private static void e(BehaviorController<Axolotl> var0) {
      var0.a(
         Activity.b,
         ImmutableList.of(
            Pair.of(0, SetEntityLookTargetSometimes.a(EntityTypes.bt, 6.0F, UniformInt.a(30, 60))),
            Pair.of(1, new BehaviorMakeLoveAnimal(EntityTypes.f, 0.2F)),
            Pair.of(
               2, new BehaviorGateSingle(ImmutableList.of(Pair.of(new FollowTemptation(AxolotlAi::d), 1), Pair.of(BehaviorFollowAdult.a(a, AxolotlAi::c), 1)))
            ),
            Pair.of(3, BehaviorAttackTargetSet.a(AxolotlAi::b)),
            Pair.of(3, TryFindWater.a(6, 0.15F)),
            Pair.of(
               4,
               new BehaviorGate(
                  ImmutableMap.of(MemoryModuleType.m, MemoryStatus.b),
                  ImmutableSet.of(),
                  BehaviorGate.Order.a,
                  BehaviorGate.Execution.b,
                  ImmutableList.of(
                     Pair.of(BehaviorStrollRandomUnconstrained.c(0.5F), 2),
                     Pair.of(BehaviorStrollRandomUnconstrained.a(0.15F, false), 2),
                     Pair.of(BehaviorLookWalk.a(AxolotlAi::a, AxolotlAi::d, 3), 3),
                     Pair.of(BehaviorBuilder.a(Entity::aW), 5),
                     Pair.of(BehaviorBuilder.a(Entity::ax), 5)
                  )
               )
            )
         )
      );
   }

   private static boolean a(EntityLiving var0) {
      World var1 = var0.H;
      Optional<BehaviorPosition> var2 = var0.dH().c(MemoryModuleType.n);
      if (var2.isPresent()) {
         BlockPosition var3 = var2.get().b();
         return var1.B(var3) == var0.aW();
      } else {
         return false;
      }
   }

   public static void a(Axolotl var0) {
      BehaviorController<Axolotl> var1 = var0.dH();
      Activity var2 = var1.g().orElse(null);
      if (var2 != Activity.p) {
         var1.a(ImmutableList.of(Activity.p, Activity.k, Activity.b));
         if (var2 == Activity.k && var1.g().orElse(null) != Activity.k) {
            var1.a(MemoryModuleType.T, true, 2400L);
         }
      }
   }

   private static float b(EntityLiving var0) {
      return var0.aW() ? 0.6F : 0.15F;
   }

   private static float c(EntityLiving var0) {
      return var0.aW() ? 0.6F : 0.15F;
   }

   private static float d(EntityLiving var0) {
      return var0.aW() ? 0.5F : 0.15F;
   }

   private static Optional<? extends EntityLiving> b(Axolotl var0) {
      return BehaviorUtil.a(var0) ? Optional.empty() : var0.dH().c(MemoryModuleType.B);
   }

   public static RecipeItemStack a() {
      return RecipeItemStack.a(TagsItem.az);
   }
}
