package net.minecraft.world.entity.monster.piglin;

import com.mojang.datafixers.kinds.App;
import java.util.List;
import java.util.function.Function;
import net.minecraft.world.entity.ai.behavior.OneShot;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.behavior.declarative.Trigger;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.monster.hoglin.EntityHoglin;

public class BehaviorHuntHoglin {
   public static OneShot<EntityPiglin> a() {
      return BehaviorBuilder.a(
         (Function<BehaviorBuilder.b<EntityPiglin>, ? extends App<BehaviorBuilder.c<EntityPiglin>, Trigger<EntityPiglin>>>)(var0 -> var0.group(
                  var0.b(MemoryModuleType.aj), var0.c(MemoryModuleType.aa), var0.c(MemoryModuleType.ag), var0.a(MemoryModuleType.an)
               )
               .apply(var0, (var1, var2, var3, var4) -> (var3x, var4x, var5) -> {
                     if (!var4x.y_() && !var0.<List>a(var4).map(var0xxx -> var0xxx.stream().anyMatch(BehaviorHuntHoglin::a)).isPresent()) {
                        EntityHoglin var7 = var0.b(var1);
                        PiglinAI.c(var4x, var7);
                        PiglinAI.c((EntityPiglinAbstract)var4x);
                        PiglinAI.b(var4x, var7);
                        var0.<List>a(var4).ifPresent(var0xxx -> var0xxx.forEach(PiglinAI::c));
                        return true;
                     } else {
                        return false;
                     }
                  }))
      );
   }

   private static boolean a(EntityPiglinAbstract var0) {
      return var0.dH().a(MemoryModuleType.ag);
   }
}
