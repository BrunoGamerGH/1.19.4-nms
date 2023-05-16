package net.minecraft.world.entity.animal.sniffer;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import net.minecraft.core.BlockPosition;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.EntityCreature;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.ai.BehaviorController;
import net.minecraft.world.entity.ai.behavior.AnimalPanic;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BehaviorGateSingle;
import net.minecraft.world.entity.ai.behavior.BehaviorLook;
import net.minecraft.world.entity.ai.behavior.BehaviorLookTarget;
import net.minecraft.world.entity.ai.behavior.BehaviorLookWalk;
import net.minecraft.world.entity.ai.behavior.BehaviorMakeLoveAnimal;
import net.minecraft.world.entity.ai.behavior.BehaviorNop;
import net.minecraft.world.entity.ai.behavior.BehaviorPosition;
import net.minecraft.world.entity.ai.behavior.BehaviorStrollRandomUnconstrained;
import net.minecraft.world.entity.ai.behavior.BehaviorSwim;
import net.minecraft.world.entity.ai.behavior.BehavorMove;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.MemoryTarget;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.schedule.Activity;
import org.slf4j.Logger;

public class SnifferAi {
   private static final Logger c = LogUtils.getLogger();
   private static final int d = 6;
   static final List<SensorType<? extends Sensor<? super Sniffer>>> a = ImmutableList.of(SensorType.c, SensorType.f, SensorType.d);
   static final List<MemoryModuleType<?>> b = ImmutableList.of(
      MemoryModuleType.n,
      MemoryModuleType.m,
      MemoryModuleType.E,
      MemoryModuleType.t,
      MemoryModuleType.Y,
      MemoryModuleType.aQ,
      MemoryModuleType.aR,
      MemoryModuleType.aS,
      MemoryModuleType.aF,
      MemoryModuleType.aP,
      MemoryModuleType.h,
      MemoryModuleType.r,
      new MemoryModuleType[0]
   );
   private static final int e = 9600;
   private static final float f = 1.0F;
   private static final float g = 2.0F;
   private static final float h = 1.25F;

   protected static BehaviorController<?> a(BehaviorController<Sniffer> var0) {
      b(var0);
      e(var0);
      c(var0);
      d(var0);
      var0.a(Set.of(Activity.a));
      var0.b(Activity.b);
      var0.f();
      return var0;
   }

   private static void b(BehaviorController<Sniffer> var0) {
      var0.a(Activity.a, 0, ImmutableList.of(new BehaviorSwim(0.8F), new AnimalPanic(2.0F) {
         @Override
         protected void b(WorldServer var0, EntityCreature var1, long var2) {
            var1.dH().b(MemoryModuleType.aR);
            var1.dH().b(MemoryModuleType.aQ);
            ((Sniffer)var1).a(Sniffer.a.a);
            super.b(var0, var1, var2);
         }
      }, new BehavorMove(10000, 15000)));
   }

   private static void c(BehaviorController<Sniffer> var0) {
      var0.a(
         Activity.v,
         ImmutableList.of(Pair.of(0, new SnifferAi.e())),
         Set.of(Pair.of(MemoryModuleType.Y, MemoryStatus.b), Pair.of(MemoryModuleType.aQ, MemoryStatus.a), Pair.of(MemoryModuleType.m, MemoryStatus.a))
      );
   }

   private static void d(BehaviorController<Sniffer> var0) {
      var0.a(
         Activity.z,
         ImmutableList.of(Pair.of(0, new SnifferAi.a(160, 180)), Pair.of(0, new SnifferAi.c(40))),
         Set.of(Pair.of(MemoryModuleType.Y, MemoryStatus.b), Pair.of(MemoryModuleType.m, MemoryStatus.b), Pair.of(MemoryModuleType.aR, MemoryStatus.a))
      );
   }

   private static void e(BehaviorController<Sniffer> var0) {
      var0.a(
         Activity.b,
         ImmutableList.of(
            Pair.of(0, new BehaviorLook(45, 90)),
            Pair.of(0, new SnifferAi.b(40, 100)),
            Pair.of(
               0,
               new BehaviorGateSingle(
                  ImmutableList.of(
                     Pair.of(BehaviorLookWalk.a(1.0F, 3), 2),
                     Pair.of(new SnifferAi.d(40, 80), 1),
                     Pair.of(new SnifferAi.f(40, 80), 1),
                     Pair.of(new BehaviorMakeLoveAnimal(EntityTypes.aN, 1.0F), 1),
                     Pair.of(BehaviorLookTarget.a(EntityTypes.bt, 6.0F), 1),
                     Pair.of(BehaviorStrollRandomUnconstrained.a(1.0F), 1),
                     Pair.of(new BehaviorNop(5, 20), 2)
                  )
               )
            )
         ),
         Set.of(Pair.of(MemoryModuleType.aR, MemoryStatus.b))
      );
   }

   static void a(Sniffer var0) {
      var0.dH().a(ImmutableList.of(Activity.z, Activity.v, Activity.b));
   }

   static class a extends Behavior<Sniffer> {
      a(int var0, int var1) {
         super(
            Map.of(
               MemoryModuleType.Y,
               MemoryStatus.b,
               MemoryModuleType.m,
               MemoryStatus.b,
               MemoryModuleType.aR,
               MemoryStatus.a,
               MemoryModuleType.aF,
               MemoryStatus.b
            ),
            var0,
            var1
         );
      }

      protected boolean a(WorldServer var0, Sniffer var1) {
         return !var1.r() && !var1.aT();
      }

      protected boolean a(WorldServer var0, Sniffer var1, long var2) {
         return var1.dH().c(MemoryModuleType.aR).isPresent() && !var1.r();
      }

      protected void b(WorldServer var0, Sniffer var1, long var2) {
         var1.a(Sniffer.a.f);
      }

      protected void c(WorldServer var0, Sniffer var1, long var2) {
         var1.dH().a(MemoryModuleType.aF, Unit.a, 9600L);
      }
   }

   static class b extends Behavior<Sniffer> {
      b(int var0, int var1) {
         super(Map.of(MemoryModuleType.aS, MemoryStatus.a), var0, var1);
      }

      protected boolean a(WorldServer var0, Sniffer var1, long var2) {
         return true;
      }

      protected void b(WorldServer var0, Sniffer var1, long var2) {
         var1.a(Sniffer.a.b);
      }

      protected void c(WorldServer var0, Sniffer var1, long var2) {
         var1.a(Sniffer.a.a);
         var1.dH().b(MemoryModuleType.aS);
      }
   }

   static class c extends Behavior<Sniffer> {
      c(int var0) {
         super(
            Map.of(
               MemoryModuleType.Y,
               MemoryStatus.b,
               MemoryModuleType.m,
               MemoryStatus.b,
               MemoryModuleType.aR,
               MemoryStatus.a,
               MemoryModuleType.aF,
               MemoryStatus.a
            ),
            var0,
            var0
         );
      }

      protected boolean a(WorldServer var0, Sniffer var1) {
         return true;
      }

      protected boolean a(WorldServer var0, Sniffer var1, long var2) {
         return var1.dH().c(MemoryModuleType.aR).isPresent();
      }

      protected void b(WorldServer var0, Sniffer var1, long var2) {
         var1.a(Sniffer.a.g);
      }

      protected void c(WorldServer var0, Sniffer var1, long var2) {
         boolean var4 = this.a(var2);
         var1.a(Sniffer.a.a).w(var4);
         var1.dH().b(MemoryModuleType.aR);
         var1.dH().a(MemoryModuleType.aS, true);
      }
   }

   static class d extends Behavior<Sniffer> {
      d(int var0, int var1) {
         super(Map.of(MemoryModuleType.aR, MemoryStatus.b, MemoryModuleType.aQ, MemoryStatus.b, MemoryModuleType.aS, MemoryStatus.b), var0, var1);
      }

      protected boolean a(WorldServer var0, Sniffer var1, long var2) {
         return true;
      }

      protected void b(WorldServer var0, Sniffer var1, long var2) {
         var1.a(Sniffer.a.c);
      }

      protected void c(WorldServer var0, Sniffer var1, long var2) {
         var1.a(Sniffer.a.a);
      }
   }

   static class e extends Behavior<Sniffer> {
      e() {
         super(Map.of(MemoryModuleType.m, MemoryStatus.a, MemoryModuleType.Y, MemoryStatus.b, MemoryModuleType.aQ, MemoryStatus.a), 600);
      }

      protected boolean a(WorldServer var0, Sniffer var1) {
         return !var1.r() && !var1.aT();
      }

      protected boolean a(WorldServer var0, Sniffer var1, long var2) {
         if (var1.r() && !var1.aT()) {
            return false;
         } else {
            Optional<BlockPosition> var4 = var1.dH().c(MemoryModuleType.m).map(MemoryTarget::a).map(BehaviorPosition::b);
            Optional<BlockPosition> var5 = var1.dH().c(MemoryModuleType.aQ);
            return !var4.isEmpty() && !var5.isEmpty() ? var5.get().equals(var4.get()) : false;
         }
      }

      protected void b(WorldServer var0, Sniffer var1, long var2) {
         var1.a(Sniffer.a.e);
      }

      protected void c(WorldServer var0, Sniffer var1, long var2) {
         if (var1.fY()) {
            var1.dH().a(MemoryModuleType.aR, true);
         }

         var1.dH().b(MemoryModuleType.m);
         var1.dH().b(MemoryModuleType.aQ);
      }
   }

   static class f extends Behavior<Sniffer> {
      f(int var0, int var1) {
         super(Map.of(MemoryModuleType.m, MemoryStatus.b, MemoryModuleType.aQ, MemoryStatus.b, MemoryModuleType.aF, MemoryStatus.b), var0, var1);
      }

      protected boolean a(WorldServer var0, Sniffer var1) {
         return !var1.y_() && !var1.aT();
      }

      protected boolean a(WorldServer var0, Sniffer var1, long var2) {
         return !var1.r();
      }

      protected void b(WorldServer var0, Sniffer var1, long var2) {
         var1.a(Sniffer.a.d);
      }

      protected void c(WorldServer var0, Sniffer var1, long var2) {
         boolean var4 = this.a(var2);
         var1.a(Sniffer.a.a);
         if (var4) {
            var1.fS().ifPresent(var1x -> {
               var1.dH().a(MemoryModuleType.aQ, var1x);
               var1.dH().a(MemoryModuleType.m, new MemoryTarget(var1x, 1.25F, 0));
            });
         }
      }
   }
}
