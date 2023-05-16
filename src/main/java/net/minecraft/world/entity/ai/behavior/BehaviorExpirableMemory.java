package net.minecraft.world.entity.ai.behavior;

import com.mojang.datafixers.kinds.App;
import java.util.function.Function;
import java.util.function.Predicate;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.behavior.declarative.Trigger;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

public class BehaviorExpirableMemory {
   public static <E extends EntityLiving, T> BehaviorControl<E> a(
      Predicate<E> var0, MemoryModuleType<? extends T> var1, MemoryModuleType<T> var2, UniformInt var3
   ) {
      return BehaviorBuilder.a(
         (Function<BehaviorBuilder.b<E>, ? extends App<BehaviorBuilder.c<E>, Trigger<E>>>)(var4 -> var4.group(var4.b(var1), var4.c(var2))
               .apply(var4, (var3xx, var4x) -> (var5, var6, var7) -> {
                     if (!var0.test((E)var6)) {
                        return false;
                     } else {
                        var4x.a(var4.b(var3xx), (long)var3.a(var5.z));
                        return true;
                     }
                  }))
      );
   }
}
