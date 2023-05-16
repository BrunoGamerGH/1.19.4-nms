package net.minecraft.world.entity.ai.behavior;

import com.mojang.datafixers.kinds.App;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.IPosition;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityCreature;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.behavior.declarative.Trigger;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryTarget;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.phys.Vec3D;

public class BehaviorWalkAway {
   public static BehaviorControl<EntityCreature> a(MemoryModuleType<BlockPosition> var0, float var1, int var2, boolean var3) {
      return a(var0, var1, var2, var3, Vec3D::c);
   }

   public static OneShot<EntityCreature> b(MemoryModuleType<? extends Entity> var0, float var1, int var2, boolean var3) {
      return a(var0, var1, var2, var3, Entity::de);
   }

   private static <T> OneShot<EntityCreature> a(MemoryModuleType<T> var0, float var1, int var2, boolean var3, Function<T, Vec3D> var4) {
      return BehaviorBuilder.a(
         (Function<BehaviorBuilder.b<EntityCreature>, ? extends App<BehaviorBuilder.c<EntityCreature>, Trigger<EntityCreature>>>)(var5 -> var5.group(
                  var5.a(MemoryModuleType.m), var5.b(var0)
               )
               .apply(var5, (var5x, var6) -> (var7, var8, var9) -> {
                     Optional<MemoryTarget> var11 = var5.a(var5x);
                     if (var11.isPresent() && !var3) {
                        return false;
                     } else {
                        Vec3D var12 = var8.de();
                        Vec3D var13 = var4.apply(var5.b(var6));
                        if (!var12.a((IPosition)var13, (double)var2)) {
                           return false;
                        } else {
                           if (var11.isPresent() && var11.get().b() == var1) {
                              Vec3D var14 = var11.get().a().a().d(var12);
                              Vec3D var15 = var13.d(var12);
                              if (var14.b(var15) < 0.0) {
                                 return false;
                              }
                           }
      
                           for(int var14 = 0; var14 < 10; ++var14) {
                              Vec3D var15 = LandRandomPos.b(var8, 16, 7, var13);
                              if (var15 != null) {
                                 var5x.a(new MemoryTarget(var15, var1, 0));
                                 break;
                              }
                           }
      
                           return true;
                        }
                     }
                  }))
      );
   }
}
