package net.minecraft.world.entity.ai.behavior;

import com.mojang.datafixers.kinds.App;
import java.util.function.Function;
import net.minecraft.util.MathHelper;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.behavior.declarative.Trigger;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;

public class BehaviorRetreat {
   public static OneShot<EntityInsentient> a(int var0, float var1) {
      return BehaviorBuilder.a(
         (Function<BehaviorBuilder.b<EntityInsentient>, ? extends App<BehaviorBuilder.c<EntityInsentient>, Trigger<EntityInsentient>>>)(var2 -> var2.group(
                  var2.c(MemoryModuleType.m), var2.a(MemoryModuleType.n), var2.b(MemoryModuleType.o), var2.b(MemoryModuleType.h)
               )
               .apply(var2, (var3, var4, var5, var6) -> (var6x, var7, var8) -> {
                     EntityLiving var10 = var2.b(var5);
                     if (var10.a(var7, (double)var0) && var2.<NearestVisibleLivingEntities>b(var6).a(var10)) {
                        var4.a(new BehaviorPositionEntity(var10, true));
                        var7.D().a(-var1, 0.0F);
                        var7.f(MathHelper.c(var7.dw(), var7.aV, 0.0F));
                        return true;
                     } else {
                        return false;
                     }
                  }))
      );
   }
}
