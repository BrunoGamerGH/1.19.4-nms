package net.minecraft.world.entity.ai.behavior;

import com.mojang.datafixers.kinds.App;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityCreature;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.behavior.declarative.Trigger;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryTarget;
import net.minecraft.world.entity.ai.util.AirAndWaterRandomPos;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.phys.Vec3D;

public class BehaviorStrollRandomUnconstrained {
   private static final int a = 10;
   private static final int b = 7;
   private static final int[][] c = new int[][]{{1, 1}, {3, 3}, {5, 5}, {6, 5}, {7, 7}, {10, 7}};

   public static OneShot<EntityCreature> a(float var0) {
      return a(var0, true);
   }

   public static OneShot<EntityCreature> a(float var0, boolean var1) {
      return a(var0, var0x -> LandRandomPos.a(var0x, 10, 7), var1 ? var0x -> true : var0x -> !var0x.aW());
   }

   public static BehaviorControl<EntityCreature> a(float var0, int var1, int var2) {
      return a(var0, var2x -> LandRandomPos.a(var2x, var1, var2), var0x -> true);
   }

   public static BehaviorControl<EntityCreature> b(float var0) {
      return a(var0, var0x -> a(var0x, 10, 7), var0x -> true);
   }

   public static BehaviorControl<EntityCreature> c(float var0) {
      return a(var0, BehaviorStrollRandomUnconstrained::a, Entity::aW);
   }

   private static OneShot<EntityCreature> a(float var0, Function<EntityCreature, Vec3D> var1, Predicate<EntityCreature> var2) {
      return BehaviorBuilder.a(
         (Function<BehaviorBuilder.b<EntityCreature>, ? extends App<BehaviorBuilder.c<EntityCreature>, Trigger<EntityCreature>>>)(var3 -> var3.group(
                  var3.c(MemoryModuleType.m)
               )
               .apply(var3, var3x -> (var4, var5, var6) -> {
                     if (!var2.test(var5)) {
                        return false;
                     } else {
                        Optional<Vec3D> var8 = Optional.ofNullable(var1.apply(var5));
                        var3x.a(var8.map(var1xxxx -> new MemoryTarget(var1xxxx, var0, 0)));
                        return true;
                     }
                  }))
      );
   }

   @Nullable
   private static Vec3D a(EntityCreature var0) {
      Vec3D var1 = null;
      Vec3D var2 = null;

      for(int[] var6 : c) {
         if (var1 == null) {
            var2 = BehaviorUtil.a(var0, var6[0], var6[1]);
         } else {
            var2 = var0.de().e(var0.de().a(var1).d().d((double)var6[0], (double)var6[1], (double)var6[0]));
         }

         if (var2 == null || var0.H.b_(BlockPosition.a(var2)).c()) {
            return var1;
         }

         var1 = var2;
      }

      return var2;
   }

   @Nullable
   private static Vec3D a(EntityCreature var0, int var1, int var2) {
      Vec3D var3 = var0.j(0.0F);
      return AirAndWaterRandomPos.a(var0, var1, var2, -2, var3.c, var3.e, (float) (Math.PI / 2));
   }
}
