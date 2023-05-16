package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import java.util.Optional;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.entity.EntityAgeable;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumCreatureType;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.entity.npc.EntityVillager;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.raid.Raid;

public class Behaviors {
   private static final float a = 0.4F;

   public static ImmutableList<Pair<Integer, ? extends BehaviorControl<? super EntityVillager>>> a(VillagerProfession var0, float var1) {
      return ImmutableList.of(
         Pair.of(0, new BehaviorSwim(0.8F)),
         Pair.of(0, BehaviorInteractDoor.a()),
         Pair.of(0, new BehaviorLook(45, 90)),
         Pair.of(0, new BehaviorPanic()),
         Pair.of(0, BehaviorWake.a()),
         Pair.of(0, BehaviorBellAlert.a()),
         Pair.of(0, BehaviorRaid.a()),
         Pair.of(0, BehaviorPositionValidate.a(var0.b(), MemoryModuleType.c)),
         Pair.of(0, BehaviorPositionValidate.a(var0.c(), MemoryModuleType.d)),
         Pair.of(1, new BehavorMove()),
         Pair.of(2, BehaviorBetterJob.a()),
         Pair.of(3, new BehaviorInteractPlayer(var1)),
         new Pair[]{
            Pair.of(5, BehaviorFindAdmirableItem.a(var1, false, 4)),
            Pair.of(6, BehaviorFindPosition.a(var0.c(), MemoryModuleType.c, MemoryModuleType.d, true, Optional.empty())),
            Pair.of(7, new BehaviorPotentialJobSite(var1)),
            Pair.of(8, BehaviorLeaveJob.a(var1)),
            Pair.of(10, BehaviorFindPosition.a(var0x -> var0x.a(PoiTypes.n), MemoryModuleType.b, false, Optional.of((byte)14))),
            Pair.of(10, BehaviorFindPosition.a(var0x -> var0x.a(PoiTypes.o), MemoryModuleType.e, true, Optional.of((byte)14))),
            Pair.of(10, BehaviorCareer.a()),
            Pair.of(10, BehaviorProfession.a())
         }
      );
   }

   public static ImmutableList<Pair<Integer, ? extends BehaviorControl<? super EntityVillager>>> b(VillagerProfession var0, float var1) {
      BehaviorWork var2;
      if (var0 == VillagerProfession.g) {
         var2 = new BehaviorWorkComposter();
      } else {
         var2 = new BehaviorWork();
      }

      return ImmutableList.of(
         b(),
         Pair.of(
            5,
            new BehaviorGateSingle(
               ImmutableList.of(
                  Pair.of(var2, 7),
                  Pair.of(BehaviorStrollPosition.a(MemoryModuleType.c, 0.4F, 4), 2),
                  Pair.of(BehaviorStrollPlace.a(MemoryModuleType.c, 0.4F, 1, 10), 5),
                  Pair.of(BehaviorStrollPlaceList.a(MemoryModuleType.f, var1, 1, 6, MemoryModuleType.c), 5),
                  Pair.of(new BehaviorFarm(), var0 == VillagerProfession.g ? 2 : 5),
                  Pair.of(new BehaviorBonemeal(), var0 == VillagerProfession.g ? 4 : 7)
               )
            )
         ),
         Pair.of(10, new BehaviorTradePlayer(400, 1600)),
         Pair.of(10, BehaviorLookInteract.a(EntityTypes.bt, 4)),
         Pair.of(2, BehaviorWalkAwayBlock.a(MemoryModuleType.c, var1, 9, 100, 1200)),
         Pair.of(3, new BehaviorVillageHeroGift(100)),
         Pair.of(99, BehaviorSchedule.a())
      );
   }

   public static ImmutableList<Pair<Integer, ? extends BehaviorControl<? super EntityVillager>>> a(float var0) {
      return ImmutableList.of(
         Pair.of(0, new BehavorMove(80, 120)),
         a(),
         Pair.of(5, BehaviorPlay.a()),
         Pair.of(
            5,
            new BehaviorGateSingle(
               ImmutableMap.of(MemoryModuleType.i, MemoryStatus.b),
               ImmutableList.of(
                  Pair.of(BehaviorInteract.a(EntityTypes.bf, 8, MemoryModuleType.q, var0, 2), 2),
                  Pair.of(BehaviorInteract.a(EntityTypes.m, 8, MemoryModuleType.q, var0, 2), 1),
                  Pair.of(BehaviorStrollRandom.a(var0), 1),
                  Pair.of(BehaviorLookWalk.a(var0, 2), 1),
                  Pair.of(new BehaviorBedJump(var0), 2),
                  Pair.of(new BehaviorNop(20, 40), 2)
               )
            )
         ),
         Pair.of(99, BehaviorSchedule.a())
      );
   }

   public static ImmutableList<Pair<Integer, ? extends BehaviorControl<? super EntityVillager>>> c(VillagerProfession var0, float var1) {
      return ImmutableList.of(
         Pair.of(2, BehaviorWalkAwayBlock.a(MemoryModuleType.b, var1, 1, 150, 1200)),
         Pair.of(3, BehaviorPositionValidate.a(var0x -> var0x.a(PoiTypes.n), MemoryModuleType.b)),
         Pair.of(3, new BehaviorSleep()),
         Pair.of(
            5,
            new BehaviorGateSingle(
               ImmutableMap.of(MemoryModuleType.b, MemoryStatus.b),
               ImmutableList.of(
                  Pair.of(BehaviorWalkHome.a(var1), 1),
                  Pair.of(BehaviorStrollInside.a(var1), 4),
                  Pair.of(BehaviorNearestVillage.a(var1, 4), 2),
                  Pair.of(new BehaviorNop(20, 40), 2)
               )
            )
         ),
         b(),
         Pair.of(99, BehaviorSchedule.a())
      );
   }

   public static ImmutableList<Pair<Integer, ? extends BehaviorControl<? super EntityVillager>>> d(VillagerProfession var0, float var1) {
      return ImmutableList.of(
         Pair.of(2, TriggerGate.a(ImmutableList.of(Pair.of(BehaviorStrollPosition.a(MemoryModuleType.e, 0.4F, 40), 2), Pair.of(BehaviorBell.a(), 2)))),
         Pair.of(10, new BehaviorTradePlayer(400, 1600)),
         Pair.of(10, BehaviorLookInteract.a(EntityTypes.bt, 4)),
         Pair.of(2, BehaviorWalkAwayBlock.a(MemoryModuleType.e, var1, 6, 100, 200)),
         Pair.of(3, new BehaviorVillageHeroGift(100)),
         Pair.of(3, BehaviorPositionValidate.a(var0x -> var0x.a(PoiTypes.o), MemoryModuleType.e)),
         Pair.of(
            3,
            new BehaviorGate(
               ImmutableMap.of(),
               ImmutableSet.of(MemoryModuleType.q),
               BehaviorGate.Order.a,
               BehaviorGate.Execution.a,
               ImmutableList.of(Pair.of(new BehaviorTradeVillager(), 1))
            )
         ),
         a(),
         Pair.of(99, BehaviorSchedule.a())
      );
   }

   public static ImmutableList<Pair<Integer, ? extends BehaviorControl<? super EntityVillager>>> e(VillagerProfession var0, float var1) {
      return ImmutableList.of(
         Pair.of(
            2,
            new BehaviorGateSingle(
               ImmutableList.of(
                  Pair.of(BehaviorInteract.a(EntityTypes.bf, 8, MemoryModuleType.q, var1, 2), 2),
                  Pair.of(BehaviorInteract.a(EntityTypes.bf, 8, EntityAgeable::O_, EntityAgeable::O_, MemoryModuleType.r, var1, 2), 1),
                  Pair.of(BehaviorInteract.a(EntityTypes.m, 8, MemoryModuleType.q, var1, 2), 1),
                  Pair.of(BehaviorStrollRandom.a(var1), 1),
                  Pair.of(BehaviorLookWalk.a(var1, 2), 1),
                  Pair.of(new BehaviorBedJump(var1), 1),
                  Pair.of(new BehaviorNop(30, 60), 1)
               )
            )
         ),
         Pair.of(3, new BehaviorVillageHeroGift(100)),
         Pair.of(3, BehaviorLookInteract.a(EntityTypes.bt, 4)),
         Pair.of(3, new BehaviorTradePlayer(400, 1600)),
         Pair.of(
            3,
            new BehaviorGate(
               ImmutableMap.of(),
               ImmutableSet.of(MemoryModuleType.q),
               BehaviorGate.Order.a,
               BehaviorGate.Execution.a,
               ImmutableList.of(Pair.of(new BehaviorTradeVillager(), 1))
            )
         ),
         Pair.of(
            3,
            new BehaviorGate(
               ImmutableMap.of(),
               ImmutableSet.of(MemoryModuleType.r),
               BehaviorGate.Order.a,
               BehaviorGate.Execution.a,
               ImmutableList.of(Pair.of(new BehaviorMakeLove(), 1))
            )
         ),
         a(),
         Pair.of(99, BehaviorSchedule.a())
      );
   }

   public static ImmutableList<Pair<Integer, ? extends BehaviorControl<? super EntityVillager>>> f(VillagerProfession var0, float var1) {
      float var2 = var1 * 1.5F;
      return ImmutableList.of(
         Pair.of(0, BehaviorCooldown.a()),
         Pair.of(1, BehaviorWalkAway.b(MemoryModuleType.A, var2, 6, false)),
         Pair.of(1, BehaviorWalkAway.b(MemoryModuleType.y, var2, 6, false)),
         Pair.of(3, BehaviorStrollRandom.a(var2, 2, 2)),
         b()
      );
   }

   public static ImmutableList<Pair<Integer, ? extends BehaviorControl<? super EntityVillager>>> g(VillagerProfession var0, float var1) {
      return ImmutableList.of(
         Pair.of(0, BehaviorBellRing.a()),
         Pair.of(
            0,
            TriggerGate.a(
               ImmutableList.of(
                  Pair.of(BehaviorWalkAwayBlock.a(MemoryModuleType.e, var1 * 1.5F, 2, 150, 200), 6), Pair.of(BehaviorStrollRandom.a(var1 * 1.5F), 2)
               )
            )
         ),
         b(),
         Pair.of(99, BehaviorRaidReset.a())
      );
   }

   public static ImmutableList<Pair<Integer, ? extends BehaviorControl<? super EntityVillager>>> h(VillagerProfession var0, float var1) {
      return ImmutableList.of(
         Pair.of(
            0,
            BehaviorBuilder.a(
               BehaviorBuilder.a(Behaviors::b),
               TriggerGate.a(ImmutableList.of(Pair.of(BehaviorOutside.a(var1), 5), Pair.of(BehaviorStrollRandom.a(var1 * 1.1F), 2)))
            )
         ),
         Pair.of(0, new BehaviorCelebrate(600, 600)),
         Pair.of(2, BehaviorBuilder.a(BehaviorBuilder.a(Behaviors::a), BehaviorHome.a(24, var1 * 1.4F, 1))),
         b(),
         Pair.of(99, BehaviorRaidReset.a())
      );
   }

   public static ImmutableList<Pair<Integer, ? extends BehaviorControl<? super EntityVillager>>> i(VillagerProfession var0, float var1) {
      int var2 = 2;
      return ImmutableList.of(Pair.of(0, BehaviorHide.a(15, 3)), Pair.of(1, BehaviorHome.a(32, var1 * 1.25F, 2)), b());
   }

   private static Pair<Integer, BehaviorControl<EntityLiving>> a() {
      return Pair.of(
         5,
         new BehaviorGateSingle(
            ImmutableList.of(
               Pair.of(BehaviorLookTarget.a(EntityTypes.m, 8.0F), 8),
               Pair.of(BehaviorLookTarget.a(EntityTypes.bf, 8.0F), 2),
               Pair.of(BehaviorLookTarget.a(EntityTypes.bt, 8.0F), 2),
               Pair.of(BehaviorLookTarget.a(EnumCreatureType.b, 8.0F), 1),
               Pair.of(BehaviorLookTarget.a(EnumCreatureType.f, 8.0F), 1),
               Pair.of(BehaviorLookTarget.a(EnumCreatureType.d, 8.0F), 1),
               Pair.of(BehaviorLookTarget.a(EnumCreatureType.e, 8.0F), 1),
               Pair.of(BehaviorLookTarget.a(EnumCreatureType.g, 8.0F), 1),
               Pair.of(BehaviorLookTarget.a(EnumCreatureType.a, 8.0F), 1),
               Pair.of(new BehaviorNop(30, 60), 2)
            )
         )
      );
   }

   private static Pair<Integer, BehaviorControl<EntityLiving>> b() {
      return Pair.of(
         5,
         new BehaviorGateSingle(
            ImmutableList.of(
               Pair.of(BehaviorLookTarget.a(EntityTypes.bf, 8.0F), 2),
               Pair.of(BehaviorLookTarget.a(EntityTypes.bt, 8.0F), 2),
               Pair.of(new BehaviorNop(30, 60), 8)
            )
         )
      );
   }

   private static boolean a(WorldServer var0, EntityLiving var1) {
      Raid var2 = var0.c(var1.dg());
      return var2 != null && var2.v() && !var2.e() && !var2.f();
   }

   private static boolean b(WorldServer var0, EntityLiving var1) {
      Raid var2 = var0.c(var1.dg());
      return var2 != null && var2.e();
   }
}
