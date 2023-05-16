package net.minecraft.world.entity.monster.piglin;

import com.mojang.datafixers.kinds.App;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.behavior.declarative.Trigger;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.item.EntityItem;

public class BehaviorStopAdmiringItem<E extends EntityPiglin> {
   public static BehaviorControl<EntityLiving> a(int var0) {
      return BehaviorBuilder.a(
         (Function<BehaviorBuilder.b<EntityLiving>, ? extends App<BehaviorBuilder.c<EntityLiving>, Trigger<EntityLiving>>>)(var1 -> var1.group(
                  var1.b(MemoryModuleType.ac), var1.a(MemoryModuleType.K)
               )
               .apply(var1, (var2, var3) -> (var4, var5, var6) -> {
                     if (!var5.eL().b()) {
                        return false;
                     } else {
                        Optional<EntityItem> var8 = var1.a(var3);
                        if (var8.isPresent() && var8.get().a(var5, (double)var0)) {
                           return false;
                        } else {
                           var2.b();
                           return true;
                        }
                     }
                  }))
      );
   }
}
