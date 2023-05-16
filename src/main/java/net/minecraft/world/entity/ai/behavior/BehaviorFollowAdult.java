package net.minecraft.world.entity.ai.behavior;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.IdF.Mu;
import com.mojang.datafixers.util.Unit;
import java.util.function.Function;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.EntityAgeable;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.behavior.declarative.MemoryAccessor;
import net.minecraft.world.entity.ai.behavior.declarative.Trigger;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryTarget;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;

public class BehaviorFollowAdult {
   public static OneShot<EntityAgeable> a(UniformInt uniformint, float f) {
      return a(uniformint, entityliving -> f);
   }

   public static OneShot<EntityAgeable> a(UniformInt uniformint, Function<EntityLiving, Float> function) {
      return BehaviorBuilder.a(
         (Function<BehaviorBuilder.b<EntityAgeable>, ? extends App<BehaviorBuilder.c<EntityAgeable>, Trigger<EntityAgeable>>>)(behaviorbuilder_b -> behaviorbuilder_b.group(
                  behaviorbuilder_b.b(MemoryModuleType.J), behaviorbuilder_b.a(MemoryModuleType.n), behaviorbuilder_b.c(MemoryModuleType.m)
               )
               .apply(
                  behaviorbuilder_b,
                  (memoryaccessor, memoryaccessor1, memoryaccessor2) -> (worldserver, entityageable, i) -> {
                        if (!entityageable.y_()) {
                           return false;
                        } else {
                           EntityLiving entityageable1 = (EntityAgeable)behaviorbuilder_b.b(memoryaccessor);
                           if (entityageable.a(entityageable1, (double)(uniformint.b() + 1)) && !entityageable.a(entityageable1, (double)uniformint.a())) {
                              EntityTargetLivingEntityEvent event = CraftEventFactory.callEntityTargetLivingEvent(
                                 entityageable, entityageable1, TargetReason.FOLLOW_LEADER
                              );
                              if (event.isCancelled()) {
                                 return false;
                              } else if (event.getTarget() == null) {
                                 memoryaccessor.b();
                                 return true;
                              } else {
                                 EntityLiving var13 = ((CraftLivingEntity)event.getTarget()).getHandle();
                                 MemoryTarget memorytarget = new MemoryTarget(
                                    new BehaviorPositionEntity(var13, false), function.apply(entityageable), uniformint.a() - 1
                                 );
                                 memoryaccessor1.a(new BehaviorPositionEntity(var13, true));
                                 memoryaccessor2.a(memorytarget);
                                 return true;
                              }
                           } else {
                              return false;
                           }
                        }
                     }
               ))
      );
   }
}
