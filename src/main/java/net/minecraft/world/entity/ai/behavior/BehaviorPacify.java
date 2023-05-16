package net.minecraft.world.entity.ai.behavior;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.util.Function3;
import java.util.function.Function;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.behavior.declarative.Trigger;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

public class BehaviorPacify {
   public static BehaviorControl<EntityLiving> a(MemoryModuleType<?> var0, int var1) {
      return BehaviorBuilder.a(
         (Function<BehaviorBuilder.b<EntityLiving>, ? extends App<BehaviorBuilder.c<EntityLiving>, Trigger<EntityLiving>>>)(var2 -> var2.group(
                  var2.a(MemoryModuleType.o), var2.c(MemoryModuleType.aw), var2.b(var0)
               )
               .apply(var2, var2.a(() -> "[BecomePassive if " + var0 + " present]", (Function3)(var1xx, var2x, var3) -> (var3x, var4, var5) -> {
                     var2x.a(true, (long)var1);
                     var1xx.b();
                     return true;
                  })))
      );
   }
}
