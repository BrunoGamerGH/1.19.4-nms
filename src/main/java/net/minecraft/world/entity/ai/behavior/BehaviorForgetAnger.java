package net.minecraft.world.entity.ai.behavior;

import com.mojang.datafixers.kinds.App;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.behavior.declarative.Trigger;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.level.GameRules;

public class BehaviorForgetAnger {
   public static BehaviorControl<EntityLiving> a() {
      return BehaviorBuilder.a(
         (Function<BehaviorBuilder.b<EntityLiving>, ? extends App<BehaviorBuilder.c<EntityLiving>, Trigger<EntityLiving>>>)(var0 -> var0.group(
                  var0.b(MemoryModuleType.aa)
               )
               .apply(
                  var0,
                  var1 -> (var2, var3, var4) -> {
                        Optional.ofNullable(var2.a(var0.b(var1)))
                           .map(var0xxx -> var0xxx instanceof EntityLiving var1xx ? var1xx : null)
                           .filter(EntityLiving::ep)
                           .filter(var1xx -> var1xx.ae() != EntityTypes.bt || var2.W().b(GameRules.J))
                           .ifPresent(var1xx -> var1.b());
                        return true;
                     }
               ))
      );
   }
}
