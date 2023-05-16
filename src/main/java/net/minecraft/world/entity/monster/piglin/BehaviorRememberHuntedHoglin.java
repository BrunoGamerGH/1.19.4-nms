package net.minecraft.world.entity.monster.piglin;

import com.mojang.datafixers.kinds.App;
import java.util.function.Function;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.behavior.declarative.Trigger;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

public class BehaviorRememberHuntedHoglin {
   public static BehaviorControl<EntityLiving> a() {
      return BehaviorBuilder.a(
         (Function<BehaviorBuilder.b<EntityLiving>, ? extends App<BehaviorBuilder.c<EntityLiving>, Trigger<EntityLiving>>>)(var0 -> var0.group(
                  var0.b(MemoryModuleType.o), var0.a(MemoryModuleType.ag)
               )
               .apply(var0, (var1, var2) -> (var3, var4, var5) -> {
                     EntityLiving var7 = var0.b(var1);
                     if (var7.ae() == EntityTypes.W && var7.ep()) {
                        var2.a(true, (long)PiglinAI.d.a(var4.H.z));
                     }
      
                     return true;
                  }))
      );
   }
}
