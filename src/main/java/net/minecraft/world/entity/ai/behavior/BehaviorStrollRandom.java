package net.minecraft.world.entity.ai.behavior;

import com.mojang.datafixers.kinds.App;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.SectionPosition;
import net.minecraft.world.entity.EntityCreature;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.behavior.declarative.Trigger;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryTarget;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.phys.Vec3D;

public class BehaviorStrollRandom {
   private static final int a = 10;
   private static final int b = 7;

   public static OneShot<EntityCreature> a(float var0) {
      return a(var0, 10, 7);
   }

   public static OneShot<EntityCreature> a(float var0, int var1, int var2) {
      return BehaviorBuilder.a(
         (Function<BehaviorBuilder.b<EntityCreature>, ? extends App<BehaviorBuilder.c<EntityCreature>, Trigger<EntityCreature>>>)(var3 -> var3.group(
                  var3.c(MemoryModuleType.m)
               )
               .apply(var3, var3x -> (var4, var5, var6) -> {
                     BlockPosition var8 = var5.dg();
                     Vec3D var9;
                     if (var4.b(var8)) {
                        var9 = LandRandomPos.a(var5, var1, var2);
                     } else {
                        SectionPosition var10 = SectionPosition.a(var8);
                        SectionPosition var11 = BehaviorUtil.a(var4, var10, 2);
                        if (var11 != var10) {
                           var9 = DefaultRandomPos.a(var5, var1, var2, Vec3D.c(var11.q()), (float) (Math.PI / 2));
                        } else {
                           var9 = LandRandomPos.a(var5, var1, var2);
                        }
                     }
      
                     var3x.a(Optional.ofNullable(var9).map(var1xxxx -> new MemoryTarget(var1xxxx, var0, 0)));
                     return true;
                  }))
      );
   }
}
