package net.minecraft.world.entity.ai.behavior;

import com.mojang.datafixers.kinds.App;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.behavior.declarative.Trigger;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;

@Deprecated
public class SetEntityLookTargetSometimes {
   public static BehaviorControl<EntityLiving> a(float var0, UniformInt var1) {
      return a(var0, var1, var0x -> true);
   }

   public static BehaviorControl<EntityLiving> a(EntityTypes<?> var0, float var1, UniformInt var2) {
      return a(var1, var2, var1x -> var0.equals(var1x.ae()));
   }

   private static BehaviorControl<EntityLiving> a(float var0, UniformInt var1, Predicate<EntityLiving> var2) {
      float var3 = var0 * var0;
      SetEntityLookTargetSometimes.a var4 = new SetEntityLookTargetSometimes.a(var1);
      return BehaviorBuilder.a(
         (Function<BehaviorBuilder.b<EntityLiving>, ? extends App<BehaviorBuilder.c<EntityLiving>, Trigger<EntityLiving>>>)(var3x -> var3x.group(
                  var3x.c(MemoryModuleType.n), var3x.b(MemoryModuleType.h)
               )
               .apply(var3x, (var4x, var5) -> (var6, var7, var8) -> {
                     Optional<EntityLiving> var10 = var3x.<NearestVisibleLivingEntities>b(var5).a(var2.and(var2xxxx -> var2xxxx.f(var7) <= (double)var3));
                     if (var10.isEmpty()) {
                        return false;
                     } else if (!var4.a(var6.z)) {
                        return false;
                     } else {
                        var4x.a(new BehaviorPositionEntity(var10.get(), true));
                        return true;
                     }
                  }))
      );
   }

   public static final class a {
      private final UniformInt a;
      private int b;

      public a(UniformInt var0) {
         if (var0.a() <= 1) {
            throw new IllegalArgumentException();
         } else {
            this.a = var0;
         }
      }

      public boolean a(RandomSource var0) {
         if (this.b == 0) {
            this.b = this.a.a(var0) - 1;
            return false;
         } else {
            return --this.b == 0;
         }
      }
   }
}
