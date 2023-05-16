package net.minecraft.world.entity.ai.behavior;

import com.mojang.datafixers.kinds.App;
import java.util.function.Function;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.behavior.declarative.Trigger;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.schedule.Activity;

public class BehaviorBellAlert {
   public static BehaviorControl<EntityLiving> a() {
      return BehaviorBuilder.a(
         (Function<BehaviorBuilder.b<EntityLiving>, ? extends App<BehaviorBuilder.c<EntityLiving>, Trigger<EntityLiving>>>)(var0 -> var0.group(
                  var0.b(MemoryModuleType.D)
               )
               .apply(var0, var0x -> (var0xx, var1, var2) -> {
                     Raid var4 = var0xx.c(var1.dg());
                     if (var4 == null) {
                        var1.dH().a(Activity.j);
                     }
      
                     return true;
                  }))
      );
   }
}
