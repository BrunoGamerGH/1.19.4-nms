package net.minecraft.world.entity.ai.behavior;

import com.mojang.datafixers.kinds.App;
import java.util.function.Function;
import java.util.function.Predicate;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.behavior.declarative.Trigger;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryTarget;

public class BehaviorLookWalk {
   public static OneShot<EntityLiving> a(float var0, int var1) {
      return a(var0x -> true, var1x -> var0, var1);
   }

   public static OneShot<EntityLiving> a(Predicate<EntityLiving> var0, Function<EntityLiving, Float> var1, int var2) {
      return BehaviorBuilder.a(
         (Function<BehaviorBuilder.b<EntityLiving>, ? extends App<BehaviorBuilder.c<EntityLiving>, Trigger<EntityLiving>>>)(var3 -> var3.group(
                  var3.c(MemoryModuleType.m), var3.b(MemoryModuleType.n)
               )
               .apply(var3, (var4, var5) -> (var6, var7, var8) -> {
                     if (!var0.test(var7)) {
                        return false;
                     } else {
                        var4.a(new MemoryTarget(var3.b(var5), var1.apply(var7), var2));
                        return true;
                     }
                  }))
      );
   }
}
