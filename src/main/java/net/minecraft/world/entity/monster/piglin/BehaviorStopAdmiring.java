package net.minecraft.world.entity.monster.piglin;

import com.mojang.datafixers.kinds.App;
import java.util.function.Function;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.behavior.declarative.Trigger;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.item.Items;

public class BehaviorStopAdmiring {
   public static BehaviorControl<EntityPiglin> a() {
      return BehaviorBuilder.a(
         (Function<BehaviorBuilder.b<EntityPiglin>, ? extends App<BehaviorBuilder.c<EntityPiglin>, Trigger<EntityPiglin>>>)(var0 -> var0.group(
                  var0.c(MemoryModuleType.ac)
               )
               .apply(var0, var0x -> (var0xx, var1, var2) -> {
                     if (!var1.eL().b() && !var1.eL().a(Items.ut)) {
                        PiglinAI.a(var1, true);
                        return true;
                     } else {
                        return false;
                     }
                  }))
      );
   }
}
