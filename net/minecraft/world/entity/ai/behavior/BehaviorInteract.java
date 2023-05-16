package net.minecraft.world.entity.ai.behavior;

import com.mojang.datafixers.kinds.App;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.behavior.declarative.Trigger;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryTarget;
import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;

public class BehaviorInteract {
   public static <T extends EntityLiving> BehaviorControl<EntityLiving> a(
      EntityTypes<? extends T> var0, int var1, MemoryModuleType<T> var2, float var3, int var4
   ) {
      return a(var0, var1, var0x -> true, var0x -> true, var2, var3, var4);
   }

   public static <E extends EntityLiving, T extends EntityLiving> BehaviorControl<E> a(
      EntityTypes<? extends T> var0, int var1, Predicate<E> var2, Predicate<T> var3, MemoryModuleType<T> var4, float var5, int var6
   ) {
      int var7 = var1 * var1;
      Predicate<EntityLiving> var8 = var2x -> var0.equals(var2x.ae()) && var3.test((T)var2x);
      return BehaviorBuilder.a(
         (Function<BehaviorBuilder.b<E>, ? extends App<BehaviorBuilder.c<E>, Trigger<E>>>)(var6x -> var6x.group(
                  var6x.a(var4), var6x.a(MemoryModuleType.n), var6x.c(MemoryModuleType.m), var6x.b(MemoryModuleType.h)
               )
               .apply(var6x, (var6xx, var7x, var8x, var9) -> (var10, var11, var12) -> {
                     NearestVisibleLivingEntities var14 = var6x.b(var9);
                     if (var2.test((E)var11) && var14.d(var8)) {
                        Optional<EntityLiving> var15 = var14.a(var3xxxx -> var3xxxx.f(var11) <= (double)var7 && var8.test(var3xxxx));
                        var15.ifPresent(var5xxxx -> {
                           var6xx.a(var5xxxx);
                           var7x.a(new BehaviorPositionEntity(var5xxxx, true));
                           var8x.a(new MemoryTarget(new BehaviorPositionEntity(var5xxxx, false), var5, var6));
                        });
                        return true;
                     } else {
                        return false;
                     }
                  }))
      );
   }
}
