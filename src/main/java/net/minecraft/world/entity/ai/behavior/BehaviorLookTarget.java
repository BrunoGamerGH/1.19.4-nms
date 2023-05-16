package net.minecraft.world.entity.ai.behavior;

import com.mojang.datafixers.kinds.App;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumCreatureType;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.behavior.declarative.Trigger;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;

public class BehaviorLookTarget {
   public static BehaviorControl<EntityLiving> a(EnumCreatureType var0, float var1) {
      return a(var1x -> var0.equals(var1x.ae().f()), var1);
   }

   public static OneShot<EntityLiving> a(EntityTypes<?> var0, float var1) {
      return a(var1x -> var0.equals(var1x.ae()), var1);
   }

   public static OneShot<EntityLiving> a(float var0) {
      return a(var0x -> true, var0);
   }

   public static OneShot<EntityLiving> a(Predicate<EntityLiving> var0, float var1) {
      float var2 = var1 * var1;
      return BehaviorBuilder.a(
         (Function<BehaviorBuilder.b<EntityLiving>, ? extends App<BehaviorBuilder.c<EntityLiving>, Trigger<EntityLiving>>>)(var2x -> var2x.group(
                  var2x.c(MemoryModuleType.n), var2x.b(MemoryModuleType.h)
               )
               .apply(
                  var2x,
                  (var3, var4) -> (var5, var6, var7) -> {
                        Optional<EntityLiving> var9 = var2x.<NearestVisibleLivingEntities>b(var4)
                           .a(var0.and(var2xxxx -> var2xxxx.f(var6) <= (double)var2 && !var6.u(var2xxxx)));
                        if (var9.isEmpty()) {
                           return false;
                        } else {
                           var3.a(new BehaviorPositionEntity(var9.get(), true));
                           return true;
                        }
                     }
               ))
      );
   }
}
