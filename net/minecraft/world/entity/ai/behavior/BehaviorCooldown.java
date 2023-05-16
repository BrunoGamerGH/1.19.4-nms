package net.minecraft.world.entity.ai.behavior;

import com.mojang.datafixers.kinds.App;
import java.util.function.Function;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.behavior.declarative.Trigger;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

public class BehaviorCooldown {
   private static final int a = 36;

   public static BehaviorControl<EntityLiving> a() {
      return BehaviorBuilder.a(
         (Function<BehaviorBuilder.b<EntityLiving>, ? extends App<BehaviorBuilder.c<EntityLiving>, Trigger<EntityLiving>>>)(var0 -> var0.group(
                  var0.a(MemoryModuleType.x), var0.a(MemoryModuleType.y), var0.a(MemoryModuleType.A)
               )
               .apply(
                  var0,
                  (var1, var2, var3) -> (var4, var5, var6) -> {
                        boolean var8 = var0.a(var1).isPresent()
                           || var0.a(var3).isPresent()
                           || var0.<EntityLiving>a(var2).filter(var1xx -> var1xx.f(var5) <= 36.0).isPresent();
                        if (!var8) {
                           var1.b();
                           var2.b();
                           var5.dH().a(var4.V(), var4.U());
                        }
         
                        return true;
                     }
               ))
      );
   }
}
