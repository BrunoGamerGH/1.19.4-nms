package net.minecraft.world.entity.ai.behavior;

import com.mojang.datafixers.kinds.App;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.GlobalPos;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.behavior.declarative.Trigger;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryTarget;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.npc.EntityVillager;
import net.minecraft.world.phys.Vec3D;

public class BehaviorWalkAwayBlock {
   public static OneShot<EntityVillager> a(MemoryModuleType<GlobalPos> var0, float var1, int var2, int var3, int var4) {
      return BehaviorBuilder.a(
         (Function<BehaviorBuilder.b<EntityVillager>, ? extends App<BehaviorBuilder.c<EntityVillager>, Trigger<EntityVillager>>>)(var5 -> var5.group(
                  var5.a(MemoryModuleType.E), var5.c(MemoryModuleType.m), var5.b(var0)
               )
               .apply(var5, (var6, var7, var8) -> (var9, var10, var11) -> {
                     GlobalPos var13 = var5.b(var8);
                     Optional<Long> var14 = var5.a(var6);
                     if (var13.a() == var9.ab() && (!var14.isPresent() || var9.U() - var14.get() <= (long)var4)) {
                        if (var13.b().k(var10.dg()) > var3) {
                           Vec3D var15 = null;
                           int var16 = 0;
                           int var17 = 1000;
      
                           while(var15 == null || BlockPosition.a(var15).k(var10.dg()) > var3) {
                              var15 = DefaultRandomPos.a(var10, 15, 7, Vec3D.c(var13.b()), (float) (Math.PI / 2));
                              if (++var16 == 1000) {
                                 var10.a(var0);
                                 var8.b();
                                 var6.a(var11);
                                 return true;
                              }
                           }
      
                           var7.a(new MemoryTarget(var15, var1, var2));
                        } else if (var13.b().k(var10.dg()) > var2) {
                           var7.a(new MemoryTarget(var13.b(), var1, var2));
                        }
                     } else {
                        var10.a(var0);
                        var8.b();
                        var6.a(var11);
                     }
      
                     return true;
                  }))
      );
   }
}
