package net.minecraft.world.entity.ai.behavior;

import com.mojang.datafixers.kinds.App;
import java.util.function.Function;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.behavior.declarative.Trigger;
import net.minecraft.world.entity.schedule.Activity;

public class BehaviorWake {
   public static BehaviorControl<EntityLiving> a() {
      return BehaviorBuilder.a(
         (Function<BehaviorBuilder.b<EntityLiving>, ? extends App<BehaviorBuilder.c<EntityLiving>, Trigger<EntityLiving>>>)(var0 -> var0.a(
               (Trigger<EntityLiving>)((var0x, var1, var2) -> {
                  if (!var1.dH().c(Activity.e) && var1.fu()) {
                     var1.fv();
                     return true;
                  } else {
                     return false;
                  }
               })
            ))
      );
   }
}
