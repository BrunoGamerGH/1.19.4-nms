package net.minecraft.world.entity.monster.piglin;

import com.mojang.datafixers.kinds.App;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.behavior.declarative.Trigger;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

public class BehaviorAdmireTimeout {
   public static BehaviorControl<EntityLiving> a(int var0, int var1) {
      return BehaviorBuilder.a(
         (Function<BehaviorBuilder.b<EntityLiving>, ? extends App<BehaviorBuilder.c<EntityLiving>, Trigger<EntityLiving>>>)(var2 -> var2.group(
                  var2.b(MemoryModuleType.ac), var2.b(MemoryModuleType.K), var2.a(MemoryModuleType.ad), var2.a(MemoryModuleType.ae)
               )
               .apply(var2, (var3, var4, var5, var6) -> (var6x, var7, var8) -> {
                     if (!var7.eL().b()) {
                        return false;
                     } else {
                        Optional<Integer> var10 = var2.a(var5);
                        if (var10.isEmpty()) {
                           var5.a(0);
                        } else {
                           int var11 = var10.get();
                           if (var11 > var0) {
                              var3.b();
                              var5.b();
                              var6.a(true, (long)var1);
                           } else {
                              var5.a(var11 + 1);
                           }
                        }
      
                        return true;
                     }
                  }))
      );
   }
}
