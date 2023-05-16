package net.minecraft.world.entity.ai.behavior;

import com.mojang.datafixers.kinds.App;
import java.util.function.BiPredicate;
import java.util.function.Function;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.behavior.declarative.Trigger;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

public class BehaviorStopRiding {
   public static <E extends EntityLiving> BehaviorControl<E> a(int var0, BiPredicate<E, Entity> var1) {
      return BehaviorBuilder.a(
         (Function<BehaviorBuilder.b<E>, ? extends App<BehaviorBuilder.c<E>, Trigger<E>>>)(var2 -> var2.group(var2.a(MemoryModuleType.s))
               .apply(var2, var3 -> (var4, var5, var6) -> {
                     Entity var8 = var5.cV();
                     Entity var9 = var2.<Entity>a(var3).orElse(null);
                     if (var8 == null && var9 == null) {
                        return false;
                     } else {
                        Entity var10 = var8 == null ? var9 : var8;
                        if (a(var5, var10, var0) && !var1.test((E)var5, var10)) {
                           return false;
                        } else {
                           var5.bz();
                           var3.b();
                           return true;
                        }
                     }
                  }))
      );
   }

   private static boolean a(EntityLiving var0, Entity var1, int var2) {
      return var1.bq() && var1.a(var0, (double)var2) && var1.H == var0.H;
   }
}
