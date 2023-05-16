package net.minecraft.world.entity.monster.piglin;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.tags.TagsItem;
import net.minecraft.util.RandomSource;
import net.minecraft.util.TimeRange;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityCreature;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.ai.BehaviorController;
import net.minecraft.world.entity.ai.behavior.BehaviorAttack;
import net.minecraft.world.entity.ai.behavior.BehaviorAttackTargetForget;
import net.minecraft.world.entity.ai.behavior.BehaviorAttackTargetSet;
import net.minecraft.world.entity.ai.behavior.BehaviorCelebrateDeath;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.BehaviorCrossbowAttack;
import net.minecraft.world.entity.ai.behavior.BehaviorExpirableMemory;
import net.minecraft.world.entity.ai.behavior.BehaviorFindAdmirableItem;
import net.minecraft.world.entity.ai.behavior.BehaviorForgetAnger;
import net.minecraft.world.entity.ai.behavior.BehaviorGateSingle;
import net.minecraft.world.entity.ai.behavior.BehaviorInteract;
import net.minecraft.world.entity.ai.behavior.BehaviorInteractDoor;
import net.minecraft.world.entity.ai.behavior.BehaviorLook;
import net.minecraft.world.entity.ai.behavior.BehaviorLookInteract;
import net.minecraft.world.entity.ai.behavior.BehaviorLookTarget;
import net.minecraft.world.entity.ai.behavior.BehaviorLookWalk;
import net.minecraft.world.entity.ai.behavior.BehaviorNop;
import net.minecraft.world.entity.ai.behavior.BehaviorRemoveMemory;
import net.minecraft.world.entity.ai.behavior.BehaviorRetreat;
import net.minecraft.world.entity.ai.behavior.BehaviorStartRiding;
import net.minecraft.world.entity.ai.behavior.BehaviorStopRiding;
import net.minecraft.world.entity.ai.behavior.BehaviorStrollRandomUnconstrained;
import net.minecraft.world.entity.ai.behavior.BehaviorUtil;
import net.minecraft.world.entity.ai.behavior.BehaviorWalkAway;
import net.minecraft.world.entity.ai.behavior.BehaviorWalkAwayOutOfRange;
import net.minecraft.world.entity.ai.behavior.BehavorMove;
import net.minecraft.world.entity.ai.behavior.GoToTargetLocation;
import net.minecraft.world.entity.ai.behavior.OneShot;
import net.minecraft.world.entity.ai.behavior.SetEntityLookTargetSometimes;
import net.minecraft.world.entity.ai.behavior.TriggerGate;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.entity.item.EntityItem;
import net.minecraft.world.entity.monster.hoglin.EntityHoglin;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.item.EnumArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemArmor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTableInfo;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameterSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameters;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.event.entity.PiglinBarterEvent;

public class PiglinAI {
   public static final int a = 8;
   public static final int b = 4;
   public static final Item c = Items.nQ;
   private static final int e = 16;
   private static final int f = 600;
   private static final int g = 120;
   private static final int h = 9;
   private static final int i = 200;
   private static final int j = 200;
   private static final int k = 300;
   protected static final UniformInt d = TimeRange.a(30, 120);
   private static final int l = 100;
   private static final int m = 400;
   private static final int n = 8;
   private static final UniformInt o = TimeRange.a(10, 40);
   private static final UniformInt p = TimeRange.a(10, 30);
   private static final UniformInt q = TimeRange.a(5, 20);
   private static final int r = 20;
   private static final int s = 200;
   private static final int t = 12;
   private static final int u = 8;
   private static final int v = 14;
   private static final int w = 8;
   private static final int x = 5;
   private static final float y = 0.75F;
   private static final int z = 6;
   private static final UniformInt A = TimeRange.a(5, 7);
   private static final UniformInt B = TimeRange.a(5, 7);
   private static final float C = 0.1F;
   private static final float D = 1.0F;
   private static final float E = 1.0F;
   private static final float F = 0.8F;
   private static final float G = 1.0F;
   private static final float H = 1.0F;
   private static final float I = 0.6F;
   private static final float J = 0.6F;

   protected static BehaviorController<?> a(EntityPiglin entitypiglin, BehaviorController<EntityPiglin> behaviorcontroller) {
      a(behaviorcontroller);
      b(behaviorcontroller);
      d(behaviorcontroller);
      b(entitypiglin, behaviorcontroller);
      c(behaviorcontroller);
      e(behaviorcontroller);
      f(behaviorcontroller);
      behaviorcontroller.a(ImmutableSet.of(Activity.a));
      behaviorcontroller.b(Activity.b);
      behaviorcontroller.f();
      return behaviorcontroller;
   }

   protected static void a(EntityPiglin entitypiglin, RandomSource randomsource) {
      int i = d.a(randomsource);
      entitypiglin.dH().a(MemoryModuleType.ag, true, (long)i);
   }

   private static void a(BehaviorController<EntityPiglin> behaviorcontroller) {
      behaviorcontroller.a(
         Activity.a,
         0,
         ImmutableList.of(
            new BehaviorLook(45, 90),
            new BehavorMove(),
            BehaviorInteractDoor.a(),
            e(),
            f(),
            BehaviorStopAdmiring.a(),
            BehaviorStartAdmiringItem.a(120),
            BehaviorCelebrateDeath.a(300, PiglinAI::a),
            BehaviorForgetAnger.a()
         )
      );
   }

   private static void b(BehaviorController<EntityPiglin> behaviorcontroller) {
      behaviorcontroller.a(
         Activity.b,
         10,
         ImmutableList.of(
            BehaviorLookTarget.a(PiglinAI::b, 14.0F),
            BehaviorAttackTargetSet.a(EntityPiglinAbstract::fT, PiglinAI::i),
            BehaviorBuilder.a(EntityPiglin::q, BehaviorHuntHoglin.a()),
            d(),
            g(),
            b(),
            c(),
            BehaviorLookInteract.a(EntityTypes.bt, 4)
         )
      );
   }

   private static void b(EntityPiglin entitypiglin, BehaviorController<EntityPiglin> behaviorcontroller) {
      behaviorcontroller.a(
         Activity.k,
         10,
         ImmutableList.of(
            BehaviorAttackTargetForget.a(entityliving -> !b(entitypiglin, entityliving)),
            BehaviorBuilder.a(PiglinAI::c, BehaviorRetreat.a(5, 0.75F)),
            BehaviorWalkAwayOutOfRange.a(1.0F),
            BehaviorAttack.a(20),
            new BehaviorCrossbowAttack(),
            BehaviorRememberHuntedHoglin.a(),
            BehaviorRemoveMemory.a(PiglinAI::h, MemoryModuleType.o)
         ),
         MemoryModuleType.o
      );
   }

   private static void c(BehaviorController<EntityPiglin> behaviorcontroller) {
      behaviorcontroller.a(
         Activity.l,
         10,
         ImmutableList.of(
            d(),
            BehaviorLookTarget.a(PiglinAI::b, 14.0F),
            BehaviorAttackTargetSet.a(EntityPiglinAbstract::fT, PiglinAI::i),
            BehaviorBuilder.a(entitypiglin -> !entitypiglin.ga(), GoToTargetLocation.a(MemoryModuleType.ah, 2, 1.0F)),
            BehaviorBuilder.a(EntityPiglin::ga, GoToTargetLocation.a(MemoryModuleType.ah, 4, 0.6F)),
            new BehaviorGateSingle(
               ImmutableList.of(
                  Pair.of(BehaviorLookTarget.a(EntityTypes.aw, 8.0F), 1),
                  Pair.of(BehaviorStrollRandomUnconstrained.a(0.6F, 2, 1), 1),
                  Pair.of(new BehaviorNop(10, 20), 1)
               )
            )
         ),
         MemoryModuleType.ah
      );
   }

   private static void d(BehaviorController<EntityPiglin> behaviorcontroller) {
      behaviorcontroller.a(
         Activity.m,
         10,
         ImmutableList.of(BehaviorFindAdmirableItem.a(PiglinAI::v, 1.0F, true, 9), BehaviorStopAdmiringItem.a(9), BehaviorAdmireTimeout.a(200, 200)),
         MemoryModuleType.ac
      );
   }

   private static void e(BehaviorController<EntityPiglin> behaviorcontroller) {
      behaviorcontroller.a(
         Activity.n,
         10,
         ImmutableList.of(BehaviorWalkAway.b(MemoryModuleType.z, 1.0F, 12, true), b(), c(), BehaviorRemoveMemory.a(PiglinAI::l, MemoryModuleType.z)),
         MemoryModuleType.z
      );
   }

   private static void f(BehaviorController<EntityPiglin> behaviorcontroller) {
      behaviorcontroller.a(
         Activity.o,
         10,
         ImmutableList.of(
            BehaviorStartRiding.a(0.8F),
            BehaviorLookTarget.a(PiglinAI::b, 8.0F),
            BehaviorBuilder.a(
               BehaviorBuilder.a(Entity::bL),
               TriggerGate.a(ImmutableList.builder().addAll(a()).add(Pair.of(BehaviorBuilder.a((Predicate<EntityLiving>)(entitypiglin -> true)), 1)).build())
            ),
            BehaviorStopRiding.a(8, PiglinAI::a)
         ),
         MemoryModuleType.s
      );
   }

   private static ImmutableList<Pair<OneShot<EntityLiving>, Integer>> a() {
      return ImmutableList.of(
         Pair.of(BehaviorLookTarget.a(EntityTypes.bt, 8.0F), 1),
         Pair.of(BehaviorLookTarget.a(EntityTypes.aw, 8.0F), 1),
         Pair.of(BehaviorLookTarget.a(8.0F), 1)
      );
   }

   private static BehaviorGateSingle<EntityLiving> b() {
      return new BehaviorGateSingle<>(ImmutableList.builder().addAll(a()).add(Pair.of(new BehaviorNop(30, 60), 1)).build());
   }

   private static BehaviorGateSingle<EntityPiglin> c() {
      return new BehaviorGateSingle<>(
         ImmutableList.of(
            Pair.of(BehaviorStrollRandomUnconstrained.a(0.6F), 2),
            Pair.of(BehaviorInteract.a(EntityTypes.aw, 8, MemoryModuleType.q, 0.6F, 2), 2),
            Pair.of(BehaviorBuilder.a(PiglinAI::f, BehaviorLookWalk.a(0.6F, 3)), 2),
            Pair.of(new BehaviorNop(30, 60), 1)
         )
      );
   }

   private static BehaviorControl<EntityCreature> d() {
      return BehaviorWalkAway.a(MemoryModuleType.av, 1.0F, 8, false);
   }

   private static BehaviorControl<EntityPiglin> e() {
      return BehaviorExpirableMemory.a(EntityPiglin::y_, MemoryModuleType.L, MemoryModuleType.z, B);
   }

   private static BehaviorControl<EntityPiglin> f() {
      return BehaviorExpirableMemory.a(PiglinAI::h, MemoryModuleType.aq, MemoryModuleType.z, A);
   }

   protected static void a(EntityPiglin entitypiglin) {
      BehaviorController<EntityPiglin> behaviorcontroller = entitypiglin.dH();
      Activity activity = behaviorcontroller.g().orElse(null);
      behaviorcontroller.a(ImmutableList.of(Activity.m, Activity.k, Activity.n, Activity.l, Activity.o, Activity.b));
      Activity activity1 = behaviorcontroller.g().orElse(null);
      if (activity != activity1) {
         Optional<SoundEffect> optional = c(entitypiglin);
         optional.ifPresent(entitypiglin::b);
      }

      entitypiglin.v(behaviorcontroller.a(MemoryModuleType.o));
      if (!behaviorcontroller.a(MemoryModuleType.s) && f(entitypiglin)) {
         entitypiglin.bz();
      }

      if (!behaviorcontroller.a(MemoryModuleType.ah)) {
         behaviorcontroller.b(MemoryModuleType.ai);
      }

      entitypiglin.x(behaviorcontroller.a(MemoryModuleType.ai));
   }

   private static boolean f(EntityPiglin entitypiglin) {
      if (!entitypiglin.y_()) {
         return false;
      } else {
         Entity entity = entitypiglin.cV();
         return entity instanceof EntityPiglin && ((EntityPiglin)entity).y_() || entity instanceof EntityHoglin && ((EntityHoglin)entity).y_();
      }
   }

   protected static void a(EntityPiglin entitypiglin, EntityItem entityitem) {
      k(entitypiglin);
      ItemStack itemstack;
      if (entityitem.i().a(Items.rp) && !CraftEventFactory.callEntityPickupItemEvent(entitypiglin, entityitem, 0, false).isCancelled()) {
         entitypiglin.a(entityitem, entityitem.i().K());
         itemstack = entityitem.i();
         entityitem.ai();
      } else {
         if (CraftEventFactory.callEntityPickupItemEvent(entitypiglin, entityitem, entityitem.i().K() - 1, false).isCancelled()) {
            return;
         }

         entitypiglin.a(entityitem, 1);
         itemstack = a(entityitem);
      }

      if (isLovedItem(itemstack, entitypiglin)) {
         entitypiglin.dH().b(MemoryModuleType.ad);
         c(entitypiglin, itemstack);
         d((EntityLiving)entitypiglin);
      } else if (c(itemstack) && !q(entitypiglin)) {
         o(entitypiglin);
      } else {
         boolean flag = !entitypiglin.equipItemIfPossible(itemstack, entityitem).equals(ItemStack.b);
         if (!flag) {
            d(entitypiglin, itemstack);
         }
      }
   }

   private static void c(EntityPiglin entitypiglin, ItemStack itemstack) {
      if (u(entitypiglin)) {
         entitypiglin.b(entitypiglin.b(EnumHand.b));
      }

      entitypiglin.o(itemstack);
   }

   private static ItemStack a(EntityItem entityitem) {
      ItemStack itemstack = entityitem.i();
      ItemStack itemstack1 = itemstack.a(1);
      if (itemstack.b()) {
         entityitem.ai();
      } else {
         entityitem.a(itemstack);
      }

      return itemstack1;
   }

   protected static void a(EntityPiglin entitypiglin, boolean flag) {
      ItemStack itemstack = entitypiglin.b(EnumHand.b);
      entitypiglin.a(EnumHand.b, ItemStack.b);
      if (entitypiglin.fT()) {
         boolean flag1 = isBarterCurrency(itemstack, entitypiglin);
         if (flag && flag1) {
            PiglinBarterEvent event = CraftEventFactory.callPiglinBarterEvent(entitypiglin, g(entitypiglin), itemstack);
            if (!event.isCancelled()) {
               a(entitypiglin, event.getOutcome().stream().map(CraftItemStack::asNMSCopy).collect(Collectors.toList()));
            }
         } else if (!flag1) {
            boolean flag2 = !entitypiglin.i(itemstack).b();
            if (!flag2) {
               d(entitypiglin, itemstack);
            }
         }
      } else {
         boolean flag1 = !entitypiglin.i(itemstack).b();
         if (!flag1) {
            ItemStack itemstack1 = entitypiglin.eK();
            if (isLovedItem(itemstack1, entitypiglin)) {
               d(entitypiglin, itemstack1);
            } else {
               a(entitypiglin, Collections.singletonList(itemstack1));
            }

            entitypiglin.n(itemstack);
         }
      }
   }

   protected static void b(EntityPiglin entitypiglin) {
      if (r(entitypiglin) && !entitypiglin.eL().b()) {
         entitypiglin.b(entitypiglin.eL());
         entitypiglin.a(EnumHand.b, ItemStack.b);
      }
   }

   private static void d(EntityPiglin entitypiglin, ItemStack itemstack) {
      ItemStack itemstack1 = entitypiglin.l(itemstack);
      b(entitypiglin, Collections.singletonList(itemstack1));
   }

   private static void a(EntityPiglin entitypiglin, List<ItemStack> list) {
      Optional<EntityHuman> optional = entitypiglin.dH().c(MemoryModuleType.k);
      if (optional.isPresent()) {
         a(entitypiglin, optional.get(), list);
      } else {
         b(entitypiglin, list);
      }
   }

   private static void b(EntityPiglin entitypiglin, List<ItemStack> list) {
      a(entitypiglin, list, p(entitypiglin));
   }

   private static void a(EntityPiglin entitypiglin, EntityHuman entityhuman, List<ItemStack> list) {
      a(entitypiglin, list, entityhuman.de());
   }

   private static void a(EntityPiglin entitypiglin, List<ItemStack> list, Vec3D vec3d) {
      if (!list.isEmpty()) {
         entitypiglin.a(EnumHand.b);

         for(ItemStack itemstack : list) {
            BehaviorUtil.a(entitypiglin, itemstack, vec3d.b(0.0, 1.0, 0.0));
         }
      }
   }

   private static List<ItemStack> g(EntityPiglin entitypiglin) {
      LootTable loottable = entitypiglin.H.n().aH().a(LootTables.aA);
      List<ItemStack> list = loottable.a(
         new LootTableInfo.Builder((WorldServer)entitypiglin.H).a(LootContextParameters.a, entitypiglin).a(entitypiglin.H.z).a(LootContextParameterSets.i)
      );
      return list;
   }

   private static boolean a(EntityLiving entityliving, EntityLiving entityliving1) {
      return entityliving1.ae() != EntityTypes.W ? false : RandomSource.a(entityliving.H.U()).i() < 0.1F;
   }

   protected static boolean a(EntityPiglin entitypiglin, ItemStack itemstack) {
      if (entitypiglin.y_() && itemstack.a(TagsItem.U)) {
         return false;
      } else if (itemstack.a(TagsItem.S)) {
         return false;
      } else if (t(entitypiglin) && entitypiglin.dH().a(MemoryModuleType.o)) {
         return false;
      } else if (isBarterCurrency(itemstack, entitypiglin)) {
         return v(entitypiglin);
      } else {
         boolean flag = entitypiglin.m(itemstack);
         return itemstack.a(Items.rp)
            ? flag
            : (c(itemstack) ? !q(entitypiglin) && flag : (!a(itemstack) ? entitypiglin.p(itemstack) : v(entitypiglin) && flag));
      }
   }

   protected static boolean isLovedItem(ItemStack itemstack, EntityPiglin piglin) {
      return a(itemstack) || piglin.interestItems.contains(itemstack.c()) || piglin.allowedBarterItems.contains(itemstack.c());
   }

   protected static boolean a(ItemStack itemstack) {
      return itemstack.a(TagsItem.T);
   }

   private static boolean a(EntityPiglin entitypiglin, Entity entity) {
      if (!(entity instanceof EntityInsentient)) {
         return false;
      } else {
         EntityInsentient entityinsentient = (EntityInsentient)entity;
         return !entityinsentient.y_()
            || !entityinsentient.bq()
            || g((EntityLiving)entitypiglin)
            || g(entityinsentient)
            || entityinsentient instanceof EntityPiglin && entityinsentient.cV() == null;
      }
   }

   private static boolean b(EntityPiglin entitypiglin, EntityLiving entityliving) {
      return i(entitypiglin).filter(entityliving1 -> entityliving1 == entityliving).isPresent();
   }

   private static boolean h(EntityPiglin entitypiglin) {
      BehaviorController<EntityPiglin> behaviorcontroller = entitypiglin.dH();
      if (behaviorcontroller.a(MemoryModuleType.aq)) {
         EntityLiving entityliving = behaviorcontroller.c(MemoryModuleType.aq).get();
         return entitypiglin.a(entityliving, 6.0);
      } else {
         return false;
      }
   }

   private static Optional<? extends EntityLiving> i(EntityPiglin entitypiglin) {
      BehaviorController<EntityPiglin> behaviorcontroller = entitypiglin.dH();
      if (h(entitypiglin)) {
         return Optional.empty();
      } else {
         Optional<EntityLiving> optional = BehaviorUtil.a(entitypiglin, MemoryModuleType.aa);
         if (optional.isPresent() && Sensor.d(entitypiglin, optional.get())) {
            return optional;
         } else {
            if (behaviorcontroller.a(MemoryModuleType.ab)) {
               Optional optional1 = behaviorcontroller.c(MemoryModuleType.l);
               if (optional1.isPresent()) {
                  return optional1;
               }
            }

            Optional optional1 = behaviorcontroller.c(MemoryModuleType.L);
            if (optional1.isPresent()) {
               return optional1;
            } else {
               Optional<EntityHuman> optional2 = behaviorcontroller.c(MemoryModuleType.al);
               return optional2.isPresent() && Sensor.c(entitypiglin, optional2.get()) ? optional2 : Optional.empty();
            }
         }
      }
   }

   public static void a(EntityHuman entityhuman, boolean flag) {
      List<EntityPiglin> list = entityhuman.H.a(EntityPiglin.class, entityhuman.cD().g(16.0));
      list.stream().filter(PiglinAI::d).filter(entitypiglin -> !flag || BehaviorUtil.b(entitypiglin, entityhuman)).forEach(entitypiglin -> {
         if (entitypiglin.H.W().b(GameRules.K)) {
            d((EntityPiglinAbstract)entitypiglin, entityhuman);
         } else {
            c((EntityPiglinAbstract)entitypiglin, entityhuman);
         }
      });
   }

   public static EnumInteractionResult a(EntityPiglin entitypiglin, EntityHuman entityhuman, EnumHand enumhand) {
      ItemStack itemstack = entityhuman.b(enumhand);
      if (b(entitypiglin, itemstack)) {
         ItemStack itemstack1 = itemstack.a(1);
         c(entitypiglin, itemstack1);
         d((EntityLiving)entitypiglin);
         k(entitypiglin);
         return EnumInteractionResult.b;
      } else {
         return EnumInteractionResult.d;
      }
   }

   protected static boolean b(EntityPiglin entitypiglin, ItemStack itemstack) {
      return !t(entitypiglin) && !r(entitypiglin) && entitypiglin.fT() && isBarterCurrency(itemstack, entitypiglin);
   }

   protected static void a(EntityPiglin entitypiglin, EntityLiving entityliving) {
      if (!(entityliving instanceof EntityPiglin)) {
         if (u(entitypiglin)) {
            a(entitypiglin, false);
         }

         BehaviorController<EntityPiglin> behaviorcontroller = entitypiglin.dH();
         behaviorcontroller.b(MemoryModuleType.ah);
         behaviorcontroller.b(MemoryModuleType.ai);
         behaviorcontroller.b(MemoryModuleType.ac);
         if (entityliving instanceof EntityHuman) {
            behaviorcontroller.a(MemoryModuleType.af, true, 400L);
         }

         e(entitypiglin).ifPresent(entityliving1 -> {
            if (entityliving1.ae() != entityliving.ae()) {
               behaviorcontroller.b(MemoryModuleType.z);
            }
         });
         if (entitypiglin.y_()) {
            behaviorcontroller.a(MemoryModuleType.z, entityliving, 100L);
            if (Sensor.d(entitypiglin, entityliving)) {
               b((EntityPiglinAbstract)entitypiglin, entityliving);
            }
         } else if (entityliving.ae() == EntityTypes.W && n(entitypiglin)) {
            e(entitypiglin, entityliving);
            c(entitypiglin, entityliving);
         } else {
            a((EntityPiglinAbstract)entitypiglin, entityliving);
         }
      }
   }

   protected static void a(EntityPiglinAbstract entitypiglinabstract, EntityLiving entityliving) {
      if (!entitypiglinabstract.dH().c(Activity.n) && Sensor.d(entitypiglinabstract, entityliving) && !BehaviorUtil.a(entitypiglinabstract, entityliving, 4.0)
         )
       {
         if (entityliving.ae() == EntityTypes.bt && entitypiglinabstract.H.W().b(GameRules.K)) {
            d(entitypiglinabstract, entityliving);
            a(entitypiglinabstract);
         } else {
            c(entitypiglinabstract, entityliving);
            b(entitypiglinabstract, entityliving);
         }
      }
   }

   public static Optional<SoundEffect> c(EntityPiglin entitypiglin) {
      return entitypiglin.dH().g().map(activity -> a(entitypiglin, activity));
   }

   private static SoundEffect a(EntityPiglin entitypiglin, Activity activity) {
      return activity == Activity.k
         ? SoundEffects.rF
         : (
            entitypiglin.fS()
               ? SoundEffects.rK
               : (
                  activity == Activity.n && j(entitypiglin)
                     ? SoundEffects.rK
                     : (
                        activity == Activity.m
                           ? SoundEffects.rD
                           : (
                              activity == Activity.l
                                 ? SoundEffects.rG
                                 : (e((EntityLiving)entitypiglin) ? SoundEffects.rI : (s(entitypiglin) ? SoundEffects.rK : SoundEffects.rE))
                           )
                     )
               )
         );
   }

   private static boolean j(EntityPiglin entitypiglin) {
      BehaviorController<EntityPiglin> behaviorcontroller = entitypiglin.dH();
      return !behaviorcontroller.a(MemoryModuleType.z) ? false : behaviorcontroller.c(MemoryModuleType.z).get().a(entitypiglin, 12.0);
   }

   protected static List<EntityPiglinAbstract> d(EntityPiglin entitypiglin) {
      return entitypiglin.dH().c(MemoryModuleType.an).orElse(ImmutableList.of());
   }

   private static List<EntityPiglinAbstract> e(EntityPiglinAbstract entitypiglinabstract) {
      return entitypiglinabstract.dH().c(MemoryModuleType.am).orElse(ImmutableList.of());
   }

   public static boolean a(EntityLiving entityliving) {
      for(ItemStack itemstack : entityliving.bI()) {
         Item item = itemstack.c();
         if (item instanceof ItemArmor && ((ItemArmor)item).d() == EnumArmorMaterial.d) {
            return true;
         }
      }

      return false;
   }

   private static void k(EntityPiglin entitypiglin) {
      entitypiglin.dH().b(MemoryModuleType.m);
      entitypiglin.G().n();
   }

   private static BehaviorControl<EntityLiving> g() {
      SetEntityLookTargetSometimes.a setentitylooktargetsometimes_a = new SetEntityLookTargetSometimes.a(o);
      return BehaviorExpirableMemory.a(
         entityliving -> entityliving.y_() && setentitylooktargetsometimes_a.a(entityliving.H.z), MemoryModuleType.ak, MemoryModuleType.s, p
      );
   }

   protected static void b(EntityPiglinAbstract entitypiglinabstract, EntityLiving entityliving) {
      e(entitypiglinabstract).forEach(entitypiglinabstract1 -> {
         if (entityliving.ae() != EntityTypes.W || entitypiglinabstract1.q() && ((EntityHoglin)entityliving).fY()) {
            e(entitypiglinabstract1, entityliving);
         }
      });
   }

   protected static void a(EntityPiglinAbstract entitypiglinabstract) {
      e(entitypiglinabstract).forEach(entitypiglinabstract1 -> b(entitypiglinabstract1).ifPresent(entityhuman -> c(entitypiglinabstract1, entityhuman)));
   }

   protected static void c(EntityPiglinAbstract entitypiglinabstract, EntityLiving entityliving) {
      if (Sensor.d(entitypiglinabstract, entityliving)) {
         entitypiglinabstract.dH().b(MemoryModuleType.E);
         entitypiglinabstract.dH().a(MemoryModuleType.aa, entityliving.cs(), 600L);
         if (entityliving.ae() == EntityTypes.W && entitypiglinabstract.q()) {
            c(entitypiglinabstract);
         }

         if (entityliving.ae() == EntityTypes.bt && entitypiglinabstract.H.W().b(GameRules.K)) {
            entitypiglinabstract.dH().a(MemoryModuleType.ab, true, 600L);
         }
      }
   }

   private static void d(EntityPiglinAbstract entitypiglinabstract, EntityLiving entityliving) {
      Optional<EntityHuman> optional = b(entitypiglinabstract);
      if (optional.isPresent()) {
         c(entitypiglinabstract, optional.get());
      } else {
         c(entitypiglinabstract, entityliving);
      }
   }

   private static void e(EntityPiglinAbstract entitypiglinabstract, EntityLiving entityliving) {
      Optional<EntityLiving> optional = f(entitypiglinabstract);
      EntityLiving entityliving1 = BehaviorUtil.a(entitypiglinabstract, optional, entityliving);
      if (!optional.isPresent() || optional.get() != entityliving1) {
         c(entitypiglinabstract, entityliving1);
      }
   }

   private static Optional<EntityLiving> f(EntityPiglinAbstract entitypiglinabstract) {
      return BehaviorUtil.a(entitypiglinabstract, MemoryModuleType.aa);
   }

   public static Optional<EntityLiving> e(EntityPiglin entitypiglin) {
      return entitypiglin.dH().a(MemoryModuleType.z) ? entitypiglin.dH().c(MemoryModuleType.z) : Optional.empty();
   }

   public static Optional<EntityHuman> b(EntityPiglinAbstract entitypiglinabstract) {
      return entitypiglinabstract.dH().a(MemoryModuleType.l) ? entitypiglinabstract.dH().c(MemoryModuleType.l) : Optional.empty();
   }

   private static void c(EntityPiglin entitypiglin, EntityLiving entityliving) {
      d(entitypiglin)
         .stream()
         .filter(entitypiglinabstract -> entitypiglinabstract instanceof EntityPiglin)
         .forEach(entitypiglinabstract -> d((EntityPiglin)entitypiglinabstract, entityliving));
   }

   private static void d(EntityPiglin entitypiglin, EntityLiving entityliving) {
      BehaviorController<EntityPiglin> behaviorcontroller = entitypiglin.dH();
      EntityLiving entityliving1 = BehaviorUtil.a(entitypiglin, behaviorcontroller.c(MemoryModuleType.z), entityliving);
      entityliving1 = BehaviorUtil.a(entitypiglin, behaviorcontroller.c(MemoryModuleType.o), entityliving1);
      e(entitypiglin, entityliving1);
   }

   private static boolean l(EntityPiglin entitypiglin) {
      BehaviorController<EntityPiglin> behaviorcontroller = entitypiglin.dH();
      if (!behaviorcontroller.a(MemoryModuleType.z)) {
         return true;
      } else {
         EntityLiving entityliving = behaviorcontroller.c(MemoryModuleType.z).get();
         EntityTypes<?> entitytypes = entityliving.ae();
         return entitytypes == EntityTypes.W ? m(entitypiglin) : (a(entitytypes) ? !behaviorcontroller.b(MemoryModuleType.aq, entityliving) : false);
      }
   }

   private static boolean m(EntityPiglin entitypiglin) {
      return !n(entitypiglin);
   }

   private static boolean n(EntityPiglin entitypiglin) {
      int i = entitypiglin.dH().c(MemoryModuleType.ar).orElse(0) + 1;
      int j = entitypiglin.dH().c(MemoryModuleType.as).orElse(0);
      return j > i;
   }

   private static void e(EntityPiglin entitypiglin, EntityLiving entityliving) {
      entitypiglin.dH().b(MemoryModuleType.aa);
      entitypiglin.dH().b(MemoryModuleType.o);
      entitypiglin.dH().b(MemoryModuleType.m);
      entitypiglin.dH().a(MemoryModuleType.z, entityliving, (long)q.a(entitypiglin.H.z));
      c((EntityPiglinAbstract)entitypiglin);
   }

   protected static void c(EntityPiglinAbstract entitypiglinabstract) {
      entitypiglinabstract.dH().a(MemoryModuleType.ag, true, (long)d.a(entitypiglinabstract.H.z));
   }

   private static void o(EntityPiglin entitypiglin) {
      entitypiglin.dH().a(MemoryModuleType.au, true, 200L);
   }

   private static Vec3D p(EntityPiglin entitypiglin) {
      Vec3D vec3d = LandRandomPos.a(entitypiglin, 4, 2);
      return vec3d == null ? entitypiglin.de() : vec3d;
   }

   private static boolean q(EntityPiglin entitypiglin) {
      return entitypiglin.dH().a(MemoryModuleType.au);
   }

   protected static boolean d(EntityPiglinAbstract entitypiglinabstract) {
      return entitypiglinabstract.dH().c(Activity.b);
   }

   private static boolean c(EntityLiving entityliving) {
      return entityliving.b(Items.uT);
   }

   private static void d(EntityLiving entityliving) {
      entityliving.dH().a(MemoryModuleType.ac, true, 120L);
   }

   private static boolean r(EntityPiglin entitypiglin) {
      return entitypiglin.dH().a(MemoryModuleType.ac);
   }

   private static boolean isBarterCurrency(ItemStack itemstack, EntityPiglin piglin) {
      return b(itemstack) || piglin.allowedBarterItems.contains(itemstack.c());
   }

   private static boolean b(ItemStack itemstack) {
      return itemstack.a(c);
   }

   private static boolean c(ItemStack itemstack) {
      return itemstack.a(TagsItem.V);
   }

   private static boolean s(EntityPiglin entitypiglin) {
      return entitypiglin.dH().a(MemoryModuleType.av);
   }

   private static boolean e(EntityLiving entityliving) {
      return entityliving.dH().a(MemoryModuleType.at);
   }

   private static boolean f(EntityLiving entityliving) {
      return !e(entityliving);
   }

   public static boolean b(EntityLiving entityliving) {
      return entityliving.ae() == EntityTypes.bt && entityliving.b(PiglinAI::a);
   }

   private static boolean t(EntityPiglin entitypiglin) {
      return entitypiglin.dH().a(MemoryModuleType.af);
   }

   private static boolean g(EntityLiving entityliving) {
      return entityliving.dH().a(MemoryModuleType.x);
   }

   private static boolean u(EntityPiglin entitypiglin) {
      return !entitypiglin.eL().b();
   }

   private static boolean v(EntityPiglin entitypiglin) {
      return entitypiglin.eL().b() || !isLovedItem(entitypiglin.eL(), entitypiglin);
   }

   public static boolean a(EntityTypes<?> entitytypes) {
      return entitytypes == EntityTypes.bs || entitytypes == EntityTypes.bo;
   }
}
