package net.minecraft.world.entity.ai.behavior;

import com.mojang.datafixers.kinds.App;
import java.util.function.Function;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ai.BehaviorController;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.behavior.declarative.Trigger;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.schedule.Activity;

public class BehaviorRaid {
   public static BehaviorControl<EntityLiving> a() {
      return BehaviorBuilder.a(
         (Function<BehaviorBuilder.b<EntityLiving>, ? extends App<BehaviorBuilder.c<EntityLiving>, Trigger<EntityLiving>>>)(var0 -> var0.a(
               (Trigger<EntityLiving>)((var0x, var1, var2) -> {
                  if (var0x.z.a(20) != 0) {
                     return false;
                  } else {
                     BehaviorController<?> var4 = var1.dH();
                     Raid var5 = var0x.c(var1.dg());
                     if (var5 != null) {
                        if (var5.c() && !var5.b()) {
                           var4.b(Activity.h);
                           var4.a(Activity.h);
                        } else {
                           var4.b(Activity.i);
                           var4.a(Activity.i);
                        }
                     }
      
                     return true;
                  }
               })
            ))
      );
   }
}
