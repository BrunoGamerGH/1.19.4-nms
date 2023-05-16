package net.minecraft.world.entity.ai.behavior;

import com.mojang.datafixers.kinds.App;
import java.util.function.Function;
import java.util.function.Predicate;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.behavior.declarative.Trigger;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

public class BehaviorRemoveMemory {
   public static <E extends EntityLiving> BehaviorControl<E> a(Predicate<E> var0, MemoryModuleType<?> var1) {
      return BehaviorBuilder.a(
         (Function<BehaviorBuilder.b<E>, ? extends App<BehaviorBuilder.c<E>, Trigger<E>>>)(var2 -> var2.group(var2.b(var1))
               .apply(var2, var1xx -> (var2x, var3, var4) -> {
                     if (var0.test((E)var3)) {
                        var1xx.b();
                        return true;
                     } else {
                        return false;
                     }
                  }))
      );
   }
}
