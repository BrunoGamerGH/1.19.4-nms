package net.minecraft.world.entity.ai.behavior;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.IdF.Mu;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.behavior.declarative.MemoryAccessor;
import net.minecraft.world.entity.ai.behavior.declarative.Trigger;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.village.poi.VillagePlaceType;
import net.minecraft.world.entity.npc.EntityVillager;
import net.minecraft.world.entity.npc.VillagerProfession;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftVillager;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.bukkit.event.entity.VillagerCareerChangeEvent;
import org.bukkit.event.entity.VillagerCareerChangeEvent.ChangeReason;

public class BehaviorCareer {
   public static BehaviorControl<EntityVillager> a() {
      return BehaviorBuilder.a(
         (Function<BehaviorBuilder.b<EntityVillager>, ? extends App<BehaviorBuilder.c<EntityVillager>, Trigger<EntityVillager>>>)(behaviorbuilder_b -> behaviorbuilder_b.group(
                  behaviorbuilder_b.b(MemoryModuleType.d), behaviorbuilder_b.a(MemoryModuleType.c)
               )
               .apply(
                  behaviorbuilder_b,
                  (memoryaccessor, memoryaccessor1) -> (worldserver, entityvillager, i) -> {
                        GlobalPos globalpos = behaviorbuilder_b.b(memoryaccessor);
                        if (!globalpos.b().a(entityvillager.de(), 2.0) && !entityvillager.gc()) {
                           return false;
                        } else {
                           memoryaccessor.b();
                           memoryaccessor1.a(globalpos);
                           worldserver.a(entityvillager, (byte)14);
                           if (entityvillager.gd().b() != VillagerProfession.b) {
                              return true;
                           } else {
                              MinecraftServer minecraftserver = worldserver.n();
                              Optional.ofNullable(minecraftserver.a(globalpos.a()))
                                 .flatMap(worldserver1 -> worldserver1.w().c(globalpos.b()))
                                 .flatMap(holder -> BuiltInRegistries.z.s().filter(villagerprofession -> villagerprofession.b().test(holder)).findFirst())
                                 .ifPresent(
                                    villagerprofession -> {
                                       VillagerCareerChangeEvent event = CraftEventFactory.callVillagerCareerChangeEvent(
                                          entityvillager, CraftVillager.nmsToBukkitProfession(villagerprofession), ChangeReason.EMPLOYED
                                       );
                                       if (!event.isCancelled()) {
                                          entityvillager.a(entityvillager.gd().a(CraftVillager.bukkitToNmsProfession(event.getProfession())));
                                          entityvillager.c(worldserver);
                                       }
                                    }
                                 );
                              return true;
                           }
                        }
                     }
               ))
      );
   }
}
