package net.minecraft.world.entity.ai.behavior;

import com.mojang.datafixers.kinds.App;
import java.util.function.Function;
import net.minecraft.core.BlockPosition;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.behavior.declarative.Trigger;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

public class GoToTargetLocation {
   private static BlockPosition a(EntityInsentient var0, BlockPosition var1) {
      RandomSource var2 = var0.H.z;
      return var1.b(a(var2), 0, a(var2));
   }

   private static int a(RandomSource var0) {
      return var0.a(3) - 1;
   }

   public static <E extends EntityInsentient> OneShot<E> a(MemoryModuleType<BlockPosition> var0, int var1, float var2) {
      return BehaviorBuilder.a(
         (Function<BehaviorBuilder.b<E>, ? extends App<BehaviorBuilder.c<E>, Trigger<E>>>)(var3 -> var3.group(
                  var3.b(var0), var3.c(MemoryModuleType.o), var3.c(MemoryModuleType.m), var3.a(MemoryModuleType.n)
               )
               .apply(var3, (var3x, var4, var5, var6) -> (var4x, var5x, var6x) -> {
                     BlockPosition var8 = var3.b(var3x);
                     boolean var9 = var8.a(var5x.dg(), (double)var1);
                     if (!var9) {
                        BehaviorUtil.a(var5x, a(var5x, var8), var2, var1);
                     }
      
                     return true;
                  }))
      );
   }
}
