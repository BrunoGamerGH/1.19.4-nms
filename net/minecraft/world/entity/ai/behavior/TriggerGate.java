package net.minecraft.world.entity.ai.behavior;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.function.Function;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.behavior.declarative.Trigger;

public class TriggerGate {
   public static <E extends EntityLiving> OneShot<E> a(List<Pair<? extends Trigger<? super E>, Integer>> var0) {
      return a(var0, BehaviorGate.Order.b, BehaviorGate.Execution.a);
   }

   public static <E extends EntityLiving> OneShot<E> a(
      List<Pair<? extends Trigger<? super E>, Integer>> var0, BehaviorGate.Order var1, BehaviorGate.Execution var2
   ) {
      ShufflingList<Trigger<? super E>> var3 = new ShufflingList<>();
      var0.forEach(var1x -> var3.a((Trigger<? super E>)var1x.getFirst(), var1x.getSecond()));
      return BehaviorBuilder.a(
         (Function<BehaviorBuilder.b<E>, ? extends App<BehaviorBuilder.c<E>, Trigger<E>>>)(var3x -> var3x.a((Trigger<E>)((var3xx, var4, var5) -> {
               if (var1 == BehaviorGate.Order.b) {
                  var3.a();
               }
   
               for(Trigger<? super E> var8 : var3) {
                  if (var8.trigger(var3xx, var4, var5) && var2 == BehaviorGate.Execution.a) {
                     break;
                  }
               }
   
               return true;
            })))
      );
   }
}
