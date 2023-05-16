package net.minecraft.world.entity.ai.behavior;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Const.Mu;
import com.mojang.datafixers.util.Unit;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.behavior.declarative.MemoryAccessor;
import net.minecraft.world.entity.ai.behavior.declarative.Trigger;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;

public class BehaviorAttackTargetSet {
   public static <E extends EntityInsentient> BehaviorControl<E> a(Function<E, Optional<? extends EntityLiving>> function) {
      return a(entityinsentient -> true, function);
   }

   public static <E extends EntityInsentient> BehaviorControl<E> a(Predicate<E> predicate, Function<E, Optional<? extends EntityLiving>> function) {
      return BehaviorBuilder.a(
         (Function<BehaviorBuilder.b<E>, ? extends App<BehaviorBuilder.c<E>, Trigger<E>>>)(behaviorbuilder_b -> behaviorbuilder_b.group(
                  behaviorbuilder_b.c(MemoryModuleType.o), behaviorbuilder_b.a(MemoryModuleType.E)
               )
               .apply(
                  behaviorbuilder_b,
                  (memoryaccessor, memoryaccessor1) -> (worldserver, entityinsentient, i) -> {
                        if (!predicate.test((E)entityinsentient)) {
                           return false;
                        } else {
                           Optional<? extends EntityLiving> optional = function.apply((E)entityinsentient);
                           if (optional.isEmpty()) {
                              return false;
                           } else {
                              EntityLiving entityliving = optional.get();
                              if (!entityinsentient.c(entityliving)) {
                                 return false;
                              } else {
                                 EntityTargetEvent event = CraftEventFactory.callEntityTargetLivingEvent(
                                    entityinsentient,
                                    entityliving,
                                    entityliving instanceof EntityPlayer ? TargetReason.CLOSEST_PLAYER : TargetReason.CLOSEST_ENTITY
                                 );
                                 if (event.isCancelled()) {
                                    return false;
                                 } else if (event.getTarget() == null) {
                                    memoryaccessor.b();
                                    return true;
                                 } else {
                                    entityliving = ((CraftLivingEntity)event.getTarget()).getHandle();
                                    memoryaccessor.a(entityliving);
                                    memoryaccessor1.b();
                                    return true;
                                 }
                              }
                           }
                        }
                     }
               ))
      );
   }
}
