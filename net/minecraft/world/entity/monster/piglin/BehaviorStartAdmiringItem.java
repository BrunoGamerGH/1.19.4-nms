package net.minecraft.world.entity.monster.piglin;

import com.mojang.datafixers.kinds.App;
import java.util.function.Function;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.behavior.declarative.Trigger;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.item.EntityItem;

public class BehaviorStartAdmiringItem {
   public static BehaviorControl<EntityLiving> a(int var0) {
      return BehaviorBuilder.a(
         (Function<BehaviorBuilder.b<EntityLiving>, ? extends App<BehaviorBuilder.c<EntityLiving>, Trigger<EntityLiving>>>)(var1 -> var1.group(
                  var1.b(MemoryModuleType.K), var1.c(MemoryModuleType.ac), var1.c(MemoryModuleType.af), var1.c(MemoryModuleType.ae)
               )
               .apply(var1, (var2, var3, var4, var5) -> (var4x, var5x, var6) -> {
                     EntityItem var8 = var1.b(var2);
                     if (!PiglinAI.a(var8.i())) {
                        return false;
                     } else {
                        var3.a(true, (long)var0);
                        return true;
                     }
                  }))
      );
   }
}
