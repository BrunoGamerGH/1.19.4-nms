package net.minecraft.world.entity.animal.axolotl;

import com.mojang.datafixers.kinds.App;
import java.util.function.Function;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.behavior.declarative.Trigger;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

public class ValidatePlayDead {
   public static BehaviorControl<EntityLiving> a() {
      return BehaviorBuilder.a(
         (Function<BehaviorBuilder.b<EntityLiving>, ? extends App<BehaviorBuilder.c<EntityLiving>, Trigger<EntityLiving>>>)(var0 -> var0.group(
                  var0.b(MemoryModuleType.M), var0.a(MemoryModuleType.y)
               )
               .apply(var0, (var1, var2) -> (var3, var4, var5) -> {
                     int var7 = var0.<Integer>b(var1);
                     if (var7 <= 0) {
                        var1.b();
                        var2.b();
                        var4.dH().f();
                     } else {
                        var1.a(var7 - 1);
                     }
      
                     return true;
                  }))
      );
   }
}
