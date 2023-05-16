package net.minecraft.world.entity.ai.behavior;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.IdF.Mu;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
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

public class BehaviorAttackTargetForget {
   private static final int a = 200;

   public static <E extends EntityInsentient> BehaviorControl<E> a(BiConsumer<E, EntityLiving> biconsumer) {
      return a(entityliving -> false, biconsumer, true);
   }

   public static <E extends EntityInsentient> BehaviorControl<E> a(Predicate<EntityLiving> predicate) {
      return a(predicate, (entityinsentient, entityliving) -> {
      }, true);
   }

   public static <E extends EntityInsentient> BehaviorControl<E> a() {
      return a(entityliving -> false, (entityinsentient, entityliving) -> {
      }, true);
   }

   public static <E extends EntityInsentient> BehaviorControl<E> a(Predicate<EntityLiving> predicate, BiConsumer<E, EntityLiving> biconsumer, boolean flag) {
      return BehaviorBuilder.a(
         (Function<BehaviorBuilder.b<E>, ? extends App<BehaviorBuilder.c<E>, Trigger<E>>>)(behaviorbuilder_b -> behaviorbuilder_b.group(
                  behaviorbuilder_b.b(MemoryModuleType.o), behaviorbuilder_b.a(MemoryModuleType.E)
               )
               .apply(
                  behaviorbuilder_b,
                  (memoryaccessor, memoryaccessor1) -> (worldserver, entityinsentient, i) -> {
                        EntityLiving entityliving = behaviorbuilder_b.b(memoryaccessor);
                        if (entityinsentient.c(entityliving)
                           && (!flag || !a(entityinsentient, behaviorbuilder_b.a(memoryaccessor1)))
                           && entityliving.bq()
                           && entityliving.H == entityinsentient.H
                           && !predicate.test(entityliving)) {
                           return true;
                        } else {
                           EntityLiving old = entityinsentient.dH().c(MemoryModuleType.o).orElse(null);
                           EntityTargetEvent event = CraftEventFactory.callEntityTargetLivingEvent(
                              entityinsentient, null, old != null && !old.bq() ? TargetReason.TARGET_DIED : TargetReason.FORGOT_TARGET
                           );
                           if (event.isCancelled()) {
                              return false;
                           } else if (event.getTarget() == null) {
                              memoryaccessor.b();
                              return true;
                           } else {
                              entityliving = ((CraftLivingEntity)event.getTarget()).getHandle();
                              biconsumer.accept((E)entityinsentient, entityliving);
                              memoryaccessor.b();
                              return true;
                           }
                        }
                     }
               ))
      );
   }

   private static boolean a(EntityLiving entityliving, Optional<Long> optional) {
      return optional.isPresent() && entityliving.H.U() - optional.get() > 200L;
   }
}
