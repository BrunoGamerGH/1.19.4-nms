package net.minecraft.world.entity.ai.behavior.warden;

import com.mojang.datafixers.kinds.App;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.behavior.declarative.Trigger;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.monster.warden.Warden;

public class SetRoarTarget {
   public static <E extends Warden> BehaviorControl<E> a(Function<E, Optional<? extends EntityLiving>> var0) {
      return BehaviorBuilder.a(
         (Function<BehaviorBuilder.b<E>, ? extends App<BehaviorBuilder.c<E>, Trigger<E>>>)(var1 -> var1.group(
                  var1.c(MemoryModuleType.ax), var1.c(MemoryModuleType.o), var1.a(MemoryModuleType.E)
               )
               .apply(var1, (var1x, var2, var3) -> (var3x, var4, var5) -> {
                     Optional<? extends EntityLiving> var7 = var0.apply((E)var4);
                     if (var7.filter(var4::a).isEmpty()) {
                        return false;
                     } else {
                        var1x.a(var7.get());
                        var3.b();
                        return true;
                     }
                  }))
      );
   }
}
