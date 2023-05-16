package net.minecraft.world.entity.ai.behavior;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.K1;
import com.mojang.datafixers.kinds.OptionalBox.Mu;
import java.util.function.Function;
import java.util.function.Predicate;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.behavior.declarative.MemoryAccessor;
import net.minecraft.world.entity.ai.behavior.declarative.Trigger;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryTarget;
import net.minecraft.world.entity.animal.allay.Allay;
import net.minecraft.world.entity.item.EntityItem;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;

public class BehaviorFindAdmirableItem {
   public static BehaviorControl<EntityLiving> a(float f, boolean flag, int i) {
      return a(entityliving -> true, f, flag, i);
   }

   public static <E extends EntityLiving> BehaviorControl<E> a(Predicate<E> predicate, float f, boolean flag, int i) {
      return BehaviorBuilder.a(
         (Function<BehaviorBuilder.b<E>, ? extends App<BehaviorBuilder.c<E>, Trigger<E>>>)(behaviorbuilder_b -> {
            BehaviorBuilder<E, ? extends MemoryAccessor<? extends K1, MemoryTarget>> behaviorbuilder = flag
               ? behaviorbuilder_b.a(MemoryModuleType.m)
               : behaviorbuilder_b.c(MemoryModuleType.m);
            return behaviorbuilder_b.group(
                  behaviorbuilder_b.a(MemoryModuleType.n), behaviorbuilder, behaviorbuilder_b.b(MemoryModuleType.K), behaviorbuilder_b.a(MemoryModuleType.aO)
               )
               .apply(
                  behaviorbuilder_b,
                  (memoryaccessor, memoryaccessor1, memoryaccessor2, memoryaccessor3) -> (worldserver, entityliving, j) -> {
                        EntityItem entityitem = behaviorbuilder_b.b(memoryaccessor2);
                        if (behaviorbuilder_b.a(memoryaccessor3).isEmpty()
                           && predicate.test((E)entityliving)
                           && entityitem.a(entityliving, (double)i)
                           && entityliving.H.p_().a(entityitem.dg())) {
                           if (entityliving instanceof Allay) {
                              EntityTargetEvent event = CraftEventFactory.callEntityTargetEvent(entityliving, entityitem, TargetReason.CLOSEST_ENTITY);
                              if (event.isCancelled()) {
                                 return false;
                              }
         
                              if (!(event.getTarget() instanceof EntityItem)) {
                                 memoryaccessor2.b();
                              }
         
                              entityitem = (EntityItem)((CraftEntity)event.getTarget()).getHandle();
                           }
         
                           MemoryTarget memorytarget = new MemoryTarget(new BehaviorPositionEntity(entityitem, false), f, 0);
                           memoryaccessor.a(new BehaviorPositionEntity(entityitem, true));
                           memoryaccessor1.a(memorytarget);
                           return true;
                        } else {
                           return false;
                        }
                     }
               );
         })
      );
   }
}
