package net.minecraft.world.entity.ai.behavior;

import com.mojang.datafixers.kinds.App;
import java.util.function.Function;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.behavior.declarative.Trigger;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryTarget;

public class BehaviorStartRiding {
   private static final int a = 1;

   public static BehaviorControl<EntityLiving> a(float var0) {
      return BehaviorBuilder.a(
         (Function<BehaviorBuilder.b<EntityLiving>, ? extends App<BehaviorBuilder.c<EntityLiving>, Trigger<EntityLiving>>>)(var1 -> var1.group(
                  var1.a(MemoryModuleType.n), var1.c(MemoryModuleType.m), var1.b(MemoryModuleType.s)
               )
               .apply(var1, (var2, var3, var4) -> (var5, var6, var7) -> {
                     if (var6.bL()) {
                        return false;
                     } else {
                        Entity var9 = var1.b(var4);
                        if (var9.a(var6, 1.0)) {
                           var6.k(var9);
                        } else {
                           var2.a(new BehaviorPositionEntity(var9, true));
                           var3.a(new MemoryTarget(new BehaviorPositionEntity(var9, false), var0, 1));
                        }
      
                        return true;
                     }
                  }))
      );
   }
}
