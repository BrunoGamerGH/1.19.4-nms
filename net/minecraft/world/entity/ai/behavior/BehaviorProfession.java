package net.minecraft.world.entity.ai.behavior;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Const.Mu;
import com.mojang.datafixers.util.Unit;
import java.util.function.Function;
import net.minecraft.core.GlobalPos;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.behavior.declarative.MemoryAccessor;
import net.minecraft.world.entity.ai.behavior.declarative.Trigger;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.npc.EntityVillager;
import net.minecraft.world.entity.npc.VillagerData;
import net.minecraft.world.entity.npc.VillagerProfession;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftVillager;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.bukkit.event.entity.VillagerCareerChangeEvent;
import org.bukkit.event.entity.VillagerCareerChangeEvent.ChangeReason;

public class BehaviorProfession {
   public static BehaviorControl<EntityVillager> a() {
      return BehaviorBuilder.a(
         (Function<BehaviorBuilder.b<EntityVillager>, ? extends App<BehaviorBuilder.c<EntityVillager>, Trigger<EntityVillager>>>)(behaviorbuilder_b -> behaviorbuilder_b.group(
                  behaviorbuilder_b.c(MemoryModuleType.c)
               )
               .apply(
                  behaviorbuilder_b,
                  memoryaccessor -> (worldserver, entityvillager, i) -> {
                        VillagerData villagerdata = entityvillager.gd();
                        if (villagerdata.b() != VillagerProfession.b
                           && villagerdata.b() != VillagerProfession.m
                           && entityvillager.r() == 0
                           && villagerdata.c() <= 1) {
                           VillagerCareerChangeEvent event = CraftEventFactory.callVillagerCareerChangeEvent(
                              entityvillager, CraftVillager.nmsToBukkitProfession(VillagerProfession.b), ChangeReason.LOSING_JOB
                           );
                           if (event.isCancelled()) {
                              return false;
                           } else {
                              entityvillager.a(entityvillager.gd().a(CraftVillager.bukkitToNmsProfession(event.getProfession())));
                              entityvillager.c(worldserver);
                              return true;
                           }
                        } else {
                           return false;
                        }
                     }
               ))
      );
   }
}
