package net.minecraft.world.entity.ai.behavior;

import com.mojang.datafixers.kinds.App;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import net.minecraft.core.IPosition;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.behavior.declarative.Trigger;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryTarget;

public class StayCloseToTarget {
   public static BehaviorControl<EntityLiving> a(
      Function<EntityLiving, Optional<BehaviorPosition>> var0, Predicate<EntityLiving> var1, int var2, int var3, float var4
   ) {
      return BehaviorBuilder.a(
         (Function<BehaviorBuilder.b<EntityLiving>, ? extends App<BehaviorBuilder.c<EntityLiving>, Trigger<EntityLiving>>>)(var5 -> var5.group(
                  var5.a(MemoryModuleType.n), var5.a(MemoryModuleType.m)
               )
               .apply(var5, (var5x, var6) -> (var7, var8, var9) -> {
                     Optional<BehaviorPosition> var11 = var0.apply(var8);
                     if (!var11.isEmpty() && var1.test(var8)) {
                        BehaviorPosition var12 = var11.get();
                        if (var8.de().a((IPosition)var12.a(), (double)var3)) {
                           return false;
                        } else {
                           BehaviorPosition var13 = var11.get();
                           var5x.a(var13);
                           var6.a(new MemoryTarget(var13, var4, var2));
                           return true;
                        }
                     } else {
                        return false;
                     }
                  }))
      );
   }
}
