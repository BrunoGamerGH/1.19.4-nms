package net.minecraft.world.entity.animal.allay;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.GlobalPos;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ai.BehaviorController;
import net.minecraft.world.entity.ai.behavior.AnimalPanic;
import net.minecraft.world.entity.ai.behavior.BehaviorFindAdmirableItem;
import net.minecraft.world.entity.ai.behavior.BehaviorGateSingle;
import net.minecraft.world.entity.ai.behavior.BehaviorLook;
import net.minecraft.world.entity.ai.behavior.BehaviorLookWalk;
import net.minecraft.world.entity.ai.behavior.BehaviorNop;
import net.minecraft.world.entity.ai.behavior.BehaviorPosition;
import net.minecraft.world.entity.ai.behavior.BehaviorPositionEntity;
import net.minecraft.world.entity.ai.behavior.BehaviorStrollRandomUnconstrained;
import net.minecraft.world.entity.ai.behavior.BehaviorSwim;
import net.minecraft.world.entity.ai.behavior.BehaviorTarget;
import net.minecraft.world.entity.ai.behavior.BehavorMove;
import net.minecraft.world.entity.ai.behavior.CountDownCooldownTicks;
import net.minecraft.world.entity.ai.behavior.GoAndGiveItemsToTarget;
import net.minecraft.world.entity.ai.behavior.SetEntityLookTargetSometimes;
import net.minecraft.world.entity.ai.behavior.StayCloseToTarget;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.Blocks;

public class AllayAi {
   private static final float a = 1.0F;
   private static final float b = 2.25F;
   private static final float c = 1.75F;
   private static final float d = 2.5F;
   private static final int e = 4;
   private static final int f = 16;
   private static final int g = 6;
   private static final int h = 30;
   private static final int i = 60;
   private static final int j = 600;
   private static final int k = 32;
   private static final int l = 20;

   protected static BehaviorController<?> a(BehaviorController<Allay> var0) {
      b(var0);
      c(var0);
      var0.a(ImmutableSet.of(Activity.a));
      var0.b(Activity.b);
      var0.f();
      return var0;
   }

   private static void b(BehaviorController<Allay> var0) {
      var0.a(
         Activity.a,
         0,
         ImmutableList.of(
            new BehaviorSwim(0.8F),
            new AnimalPanic(2.5F),
            new BehaviorLook(45, 90),
            new BehavorMove(),
            new CountDownCooldownTicks(MemoryModuleType.aN),
            new CountDownCooldownTicks(MemoryModuleType.aO)
         )
      );
   }

   private static void c(BehaviorController<Allay> var0) {
      var0.a(
         Activity.b,
         ImmutableList.of(
            Pair.of(0, BehaviorFindAdmirableItem.a(var0x -> true, 1.75F, true, 32)),
            Pair.of(1, new GoAndGiveItemsToTarget(AllayAi::b, 2.25F, 20)),
            Pair.of(2, StayCloseToTarget.a(AllayAi::b, Predicate.not(AllayAi::c), 4, 16, 2.25F)),
            Pair.of(3, SetEntityLookTargetSometimes.a(6.0F, UniformInt.a(30, 60))),
            Pair.of(
               4,
               new BehaviorGateSingle(
                  ImmutableList.of(
                     Pair.of(BehaviorStrollRandomUnconstrained.b(1.0F), 2), Pair.of(BehaviorLookWalk.a(1.0F, 3), 2), Pair.of(new BehaviorNop(30, 60), 1)
                  )
               )
            )
         ),
         ImmutableSet.of()
      );
   }

   public static void a(Allay var0) {
      var0.dH().a(ImmutableList.of(Activity.b));
   }

   public static void a(EntityLiving var0, BlockPosition var1) {
      BehaviorController<?> var2 = var0.dH();
      GlobalPos var3 = GlobalPos.a(var0.Y().ab(), var1);
      Optional<GlobalPos> var4 = var2.c(MemoryModuleType.aM);
      if (var4.isEmpty()) {
         var2.a(MemoryModuleType.aM, var3);
         var2.a(MemoryModuleType.aN, 600);
      } else if (var4.get().equals(var3)) {
         var2.a(MemoryModuleType.aN, 600);
      }
   }

   private static Optional<BehaviorPosition> b(EntityLiving var0) {
      BehaviorController<?> var1 = var0.dH();
      Optional<GlobalPos> var2 = var1.c(MemoryModuleType.aM);
      if (var2.isPresent()) {
         GlobalPos var3 = var2.get();
         if (a(var0, var1, var3)) {
            return Optional.of(new BehaviorTarget(var3.b().c()));
         }

         var1.b(MemoryModuleType.aM);
      }

      return d(var0);
   }

   private static boolean c(EntityLiving var0) {
      BehaviorController<?> var1 = var0.dH();
      return var1.a(MemoryModuleType.K);
   }

   private static boolean a(EntityLiving var0, BehaviorController<?> var1, GlobalPos var2) {
      Optional<Integer> var3 = var1.c(MemoryModuleType.aN);
      World var4 = var0.Y();
      return var4.ab() == var2.a() && var4.a_(var2.b()).a(Blocks.aX) && var3.isPresent();
   }

   private static Optional<BehaviorPosition> d(EntityLiving var0) {
      return a(var0).map(var0x -> new BehaviorPositionEntity(var0x, true));
   }

   public static Optional<EntityPlayer> a(EntityLiving var0) {
      World var1 = var0.Y();
      if (!var1.k_() && var1 instanceof WorldServer var2) {
         Optional<UUID> var3 = var0.dH().c(MemoryModuleType.aL);
         if (var3.isPresent()) {
            Entity var4 = var2.a(var3.get());
            if (var4 instanceof EntityPlayer var5 && (var5.d.d() || var5.d.e()) && var5.a(var0, 64.0)) {
               return Optional.of(var5);
            }

            return Optional.empty();
         }
      }

      return Optional.empty();
   }
}
