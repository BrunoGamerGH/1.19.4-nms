package net.minecraft.world.entity.monster.piglin;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import java.util.Optional;
import net.minecraft.core.GlobalPos;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.ai.BehaviorController;
import net.minecraft.world.entity.ai.behavior.BehaviorAttack;
import net.minecraft.world.entity.ai.behavior.BehaviorAttackTargetForget;
import net.minecraft.world.entity.ai.behavior.BehaviorAttackTargetSet;
import net.minecraft.world.entity.ai.behavior.BehaviorForgetAnger;
import net.minecraft.world.entity.ai.behavior.BehaviorGateSingle;
import net.minecraft.world.entity.ai.behavior.BehaviorInteract;
import net.minecraft.world.entity.ai.behavior.BehaviorInteractDoor;
import net.minecraft.world.entity.ai.behavior.BehaviorLook;
import net.minecraft.world.entity.ai.behavior.BehaviorLookInteract;
import net.minecraft.world.entity.ai.behavior.BehaviorLookTarget;
import net.minecraft.world.entity.ai.behavior.BehaviorNop;
import net.minecraft.world.entity.ai.behavior.BehaviorStrollPlace;
import net.minecraft.world.entity.ai.behavior.BehaviorStrollPosition;
import net.minecraft.world.entity.ai.behavior.BehaviorStrollRandomUnconstrained;
import net.minecraft.world.entity.ai.behavior.BehaviorUtil;
import net.minecraft.world.entity.ai.behavior.BehaviorWalkAwayOutOfRange;
import net.minecraft.world.entity.ai.behavior.BehavorMove;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.schedule.Activity;

public class PiglinBruteAI {
   private static final int a = 600;
   private static final int b = 20;
   private static final double c = 0.0125;
   private static final int d = 8;
   private static final int e = 8;
   private static final double f = 12.0;
   private static final float g = 0.6F;
   private static final int h = 2;
   private static final int i = 100;
   private static final int j = 5;

   protected static BehaviorController<?> a(EntityPiglinBrute var0, BehaviorController<EntityPiglinBrute> var1) {
      b(var0, var1);
      c(var0, var1);
      d(var0, var1);
      var1.a(ImmutableSet.of(Activity.a));
      var1.b(Activity.b);
      var1.f();
      return var1;
   }

   protected static void a(EntityPiglinBrute var0) {
      GlobalPos var1 = GlobalPos.a(var0.H.ab(), var0.dg());
      var0.dH().a(MemoryModuleType.b, var1);
   }

   private static void b(EntityPiglinBrute var0, BehaviorController<EntityPiglinBrute> var1) {
      var1.a(Activity.a, 0, ImmutableList.of(new BehaviorLook(45, 90), new BehavorMove(), BehaviorInteractDoor.a(), BehaviorForgetAnger.a()));
   }

   private static void c(EntityPiglinBrute var0, BehaviorController<EntityPiglinBrute> var1) {
      var1.a(Activity.b, 10, ImmutableList.of(BehaviorAttackTargetSet.a(PiglinBruteAI::a), a(), b(), BehaviorLookInteract.a(EntityTypes.bt, 4)));
   }

   private static void d(EntityPiglinBrute var0, BehaviorController<EntityPiglinBrute> var1) {
      var1.a(
         Activity.k,
         10,
         ImmutableList.of(
            BehaviorAttackTargetForget.a(var1x -> !a((EntityPiglinAbstract)var0, var1x)), BehaviorWalkAwayOutOfRange.a(1.0F), BehaviorAttack.a(20)
         ),
         MemoryModuleType.o
      );
   }

   private static BehaviorGateSingle<EntityPiglinBrute> a() {
      return new BehaviorGateSingle<>(
         ImmutableList.of(
            Pair.of(BehaviorLookTarget.a(EntityTypes.bt, 8.0F), 1),
            Pair.of(BehaviorLookTarget.a(EntityTypes.aw, 8.0F), 1),
            Pair.of(BehaviorLookTarget.a(EntityTypes.ax, 8.0F), 1),
            Pair.of(BehaviorLookTarget.a(8.0F), 1),
            Pair.of(new BehaviorNop(30, 60), 1)
         )
      );
   }

   private static BehaviorGateSingle<EntityPiglinBrute> b() {
      return new BehaviorGateSingle<>(
         ImmutableList.of(
            Pair.of(BehaviorStrollRandomUnconstrained.a(0.6F), 2),
            Pair.of(BehaviorInteract.a(EntityTypes.aw, 8, MemoryModuleType.q, 0.6F, 2), 2),
            Pair.of(BehaviorInteract.a(EntityTypes.ax, 8, MemoryModuleType.q, 0.6F, 2), 2),
            Pair.of(BehaviorStrollPlace.a(MemoryModuleType.b, 0.6F, 2, 100), 2),
            Pair.of(BehaviorStrollPosition.a(MemoryModuleType.b, 0.6F, 5), 2),
            Pair.of(new BehaviorNop(30, 60), 1)
         )
      );
   }

   protected static void b(EntityPiglinBrute var0) {
      BehaviorController<EntityPiglinBrute> var1 = var0.dH();
      Activity var2 = var1.g().orElse(null);
      var1.a(ImmutableList.of(Activity.k, Activity.b));
      Activity var3 = var1.g().orElse(null);
      if (var2 != var3) {
         d(var0);
      }

      var0.v(var1.a(MemoryModuleType.o));
   }

   private static boolean a(EntityPiglinAbstract var0, EntityLiving var1) {
      return a(var0).filter(var1x -> var1x == var1).isPresent();
   }

   private static Optional<? extends EntityLiving> a(EntityPiglinAbstract var0) {
      Optional<EntityLiving> var1 = BehaviorUtil.a(var0, MemoryModuleType.aa);
      if (var1.isPresent() && Sensor.d(var0, var1.get())) {
         return var1;
      } else {
         Optional<? extends EntityLiving> var2 = a(var0, MemoryModuleType.l);
         return var2.isPresent() ? var2 : var0.dH().c(MemoryModuleType.L);
      }
   }

   private static Optional<? extends EntityLiving> a(EntityPiglinAbstract var0, MemoryModuleType<? extends EntityLiving> var1) {
      return var0.dH().c(var1).filter(var1x -> var1x.a(var0, 12.0));
   }

   protected static void a(EntityPiglinBrute var0, EntityLiving var1) {
      if (!(var1 instanceof EntityPiglinAbstract)) {
         PiglinAI.a(var0, var1);
      }
   }

   protected static void b(EntityPiglinBrute var0, EntityLiving var1) {
      var0.dH().b(MemoryModuleType.E);
      var0.dH().a(MemoryModuleType.aa, var1.cs(), 600L);
   }

   protected static void c(EntityPiglinBrute var0) {
      if ((double)var0.H.z.i() < 0.0125) {
         d(var0);
      }
   }

   private static void d(EntityPiglinBrute var0) {
      var0.dH().g().ifPresent(var1 -> {
         if (var1 == Activity.k) {
            var0.fZ();
         }
      });
   }
}
