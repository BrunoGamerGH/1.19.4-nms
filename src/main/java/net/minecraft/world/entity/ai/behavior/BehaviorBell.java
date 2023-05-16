package net.minecraft.world.entity.ai.behavior;

import com.mojang.datafixers.kinds.App;
import java.util.function.Function;
import net.minecraft.core.GlobalPos;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.behavior.declarative.Trigger;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryTarget;
import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;

public class BehaviorBell {
   private static final float a = 0.3F;

   public static OneShot<EntityLiving> a() {
      return BehaviorBuilder.a(
         (Function<BehaviorBuilder.b<EntityLiving>, ? extends App<BehaviorBuilder.c<EntityLiving>, Trigger<EntityLiving>>>)(var0 -> var0.group(
                  var0.a(MemoryModuleType.m), var0.a(MemoryModuleType.n), var0.b(MemoryModuleType.e), var0.b(MemoryModuleType.h), var0.c(MemoryModuleType.q)
               )
               .apply(
                  var0,
                  (var1, var2, var3, var4, var5) -> (var6, var7, var8) -> {
                        GlobalPos var10 = var0.b(var3);
                        NearestVisibleLivingEntities var11 = var0.b(var4);
                        if (var6.r_().a(100) == 0
                           && var6.ab() == var10.a()
                           && var10.b().a(var7.de(), 4.0)
                           && var11.d(var0xxx -> EntityTypes.bf.equals(var0xxx.ae()))) {
                           var11.a(var1xx -> EntityTypes.bf.equals(var1xx.ae()) && var1xx.f(var7) <= 32.0).ifPresent(var3xx -> {
                              var5.a(var3xx);
                              var2.a(new BehaviorPositionEntity(var3xx, true));
                              var1.a(new MemoryTarget(new BehaviorPositionEntity(var3xx, false), 0.3F, 1));
                           });
                           return true;
                        } else {
                           return false;
                        }
                     }
               ))
      );
   }
}
