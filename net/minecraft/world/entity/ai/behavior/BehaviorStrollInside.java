package net.minecraft.world.entity.ai.behavior;

import com.mojang.datafixers.kinds.App;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import net.minecraft.core.BlockPosition;
import net.minecraft.world.entity.EntityCreature;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.behavior.declarative.Trigger;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryTarget;

public class BehaviorStrollInside {
   public static BehaviorControl<EntityCreature> a(float var0) {
      return BehaviorBuilder.a(
         (Function<BehaviorBuilder.b<EntityCreature>, ? extends App<BehaviorBuilder.c<EntityCreature>, Trigger<EntityCreature>>>)(var1 -> var1.group(
                  var1.c(MemoryModuleType.m)
               )
               .apply(
                  var1,
                  var1x -> (var2, var3, var4) -> {
                        if (var2.g(var3.dg())) {
                           return false;
                        } else {
                           BlockPosition var6 = var3.dg();
                           List<BlockPosition> var7 = BlockPosition.b(var6.b(-1, -1, -1), var6.b(1, 1, 1)).map(BlockPosition::i).collect(Collectors.toList());
                           Collections.shuffle(var7);
                           var7.stream()
                              .filter(var1xxx -> !var2.g(var1xxx))
                              .filter(var2x -> var2.a(var2x, var3))
                              .filter(var2x -> var2.g(var3))
                              .findFirst()
                              .ifPresent(var2x -> var1x.a(new MemoryTarget(var2x, var0, 0)));
                           return true;
                        }
                     }
               ))
      );
   }
}
