package net.minecraft.world.entity.ai.behavior;

import com.mojang.datafixers.kinds.App;
import java.util.function.BiPredicate;
import java.util.function.Function;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.behavior.declarative.Trigger;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.level.GameRules;

public class BehaviorCelebrateDeath {
   public static BehaviorControl<EntityLiving> a(int var0, BiPredicate<EntityLiving, EntityLiving> var1) {
      return BehaviorBuilder.a(
         (Function<BehaviorBuilder.b<EntityLiving>, ? extends App<BehaviorBuilder.c<EntityLiving>, Trigger<EntityLiving>>>)(var2 -> var2.group(
                  var2.b(MemoryModuleType.o), var2.a(MemoryModuleType.aa), var2.c(MemoryModuleType.ah), var2.a(MemoryModuleType.ai)
               )
               .apply(var2, (var3, var4, var5, var6) -> (var7, var8, var9) -> {
                     EntityLiving var11 = var2.b(var3);
                     if (!var11.ep()) {
                        return false;
                     } else {
                        if (var1.test(var8, var11)) {
                           var6.a(true, (long)var0);
                        }
      
                        var5.a(var11.dg(), (long)var0);
                        if (var11.ae() != EntityTypes.bt || var7.W().b(GameRules.J)) {
                           var3.b();
                           var4.b();
                        }
      
                        return true;
                     }
                  }))
      );
   }
}
