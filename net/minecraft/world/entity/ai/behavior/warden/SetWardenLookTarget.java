package net.minecraft.world.entity.ai.behavior.warden;

import com.mojang.datafixers.kinds.App;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.core.BlockPosition;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.BehaviorTarget;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.behavior.declarative.Trigger;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

public class SetWardenLookTarget {
   public static BehaviorControl<EntityLiving> a() {
      return BehaviorBuilder.a(
         (Function<BehaviorBuilder.b<EntityLiving>, ? extends App<BehaviorBuilder.c<EntityLiving>, Trigger<EntityLiving>>>)(var0 -> var0.group(
                  var0.a(MemoryModuleType.n), var0.a(MemoryModuleType.ay), var0.a(MemoryModuleType.ax), var0.c(MemoryModuleType.o)
               )
               .apply(var0, (var1, var2, var3, var4) -> (var4x, var5, var6) -> {
                     Optional<BlockPosition> var8 = var0.<EntityLiving>a(var3).map(Entity::dg).or(() -> var0.a(var2));
                     if (var8.isEmpty()) {
                        return false;
                     } else {
                        var1.a(new BehaviorTarget(var8.get()));
                        return true;
                     }
                  }))
      );
   }
}
