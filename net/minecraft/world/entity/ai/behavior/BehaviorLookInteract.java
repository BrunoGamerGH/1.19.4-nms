package net.minecraft.world.entity.ai.behavior;

import com.mojang.datafixers.kinds.App;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.behavior.declarative.Trigger;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;

public class BehaviorLookInteract {
   public static BehaviorControl<EntityLiving> a(EntityTypes<?> var0, int var1) {
      int var2 = var1 * var1;
      return BehaviorBuilder.a(
         (Function<BehaviorBuilder.b<EntityLiving>, ? extends App<BehaviorBuilder.c<EntityLiving>, Trigger<EntityLiving>>>)(var2x -> var2x.group(
                  var2x.a(MemoryModuleType.n), var2x.c(MemoryModuleType.q), var2x.b(MemoryModuleType.h)
               )
               .apply(
                  var2x,
                  (var3, var4, var5) -> (var6, var7, var8) -> {
                        Optional<EntityLiving> var10 = var2x.<NearestVisibleLivingEntities>b(var5)
                           .a(var3xx -> var3xx.f(var7) <= (double)var2 && var0.equals(var3xx.ae()));
                        if (var10.isEmpty()) {
                           return false;
                        } else {
                           EntityLiving var11 = var10.get();
                           var4.a(var11);
                           var3.a(new BehaviorPositionEntity(var11, true));
                           return true;
                        }
                     }
               ))
      );
   }
}
