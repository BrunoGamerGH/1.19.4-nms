package net.minecraft.world.entity.ai.behavior;

import com.mojang.datafixers.kinds.App;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.behavior.declarative.Trigger;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryTarget;
import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;

public class BehaviorWalkAwayOutOfRange {
   private static final int a = 1;

   public static BehaviorControl<EntityInsentient> a(float var0) {
      return a(var1 -> var0);
   }

   public static BehaviorControl<EntityInsentient> a(Function<EntityLiving, Float> var0) {
      return BehaviorBuilder.a(
         (Function<BehaviorBuilder.b<EntityInsentient>, ? extends App<BehaviorBuilder.c<EntityInsentient>, Trigger<EntityInsentient>>>)(var1 -> var1.group(
                  var1.a(MemoryModuleType.m), var1.a(MemoryModuleType.n), var1.b(MemoryModuleType.o), var1.a(MemoryModuleType.h)
               )
               .apply(var1, (var2, var3, var4, var5) -> (var6, var7, var8) -> {
                     EntityLiving var10 = var1.b(var4);
                     Optional<NearestVisibleLivingEntities> var11 = var1.a(var5);
                     if (var11.isPresent() && var11.get().a(var10) && BehaviorUtil.a(var7, var10, 1)) {
                        var2.b();
                     } else {
                        var3.a(new BehaviorPositionEntity(var10, true));
                        var2.a(new MemoryTarget(new BehaviorPositionEntity(var10, false), var0.apply(var7), 0));
                     }
      
                     return true;
                  }))
      );
   }
}
