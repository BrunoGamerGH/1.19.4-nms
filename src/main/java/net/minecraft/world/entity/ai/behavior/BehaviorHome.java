package net.minecraft.world.entity.ai.behavior;

import com.mojang.datafixers.kinds.App;
import java.util.function.Function;
import net.minecraft.core.GlobalPos;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.behavior.declarative.Trigger;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryTarget;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.entity.ai.village.poi.VillagePlace;

public class BehaviorHome {
   public static OneShot<EntityLiving> a(int var0, float var1, int var2) {
      return BehaviorBuilder.a(
         (Function<BehaviorBuilder.b<EntityLiving>, ? extends App<BehaviorBuilder.c<EntityLiving>, Trigger<EntityLiving>>>)(var3 -> var3.group(
                  var3.c(MemoryModuleType.m),
                  var3.a(MemoryModuleType.b),
                  var3.a(MemoryModuleType.C),
                  var3.a(MemoryModuleType.t),
                  var3.a(MemoryModuleType.n),
                  var3.a(MemoryModuleType.r),
                  var3.a(MemoryModuleType.q)
               )
               .apply(
                  var3,
                  (var4, var5, var6, var7, var8, var9, var10) -> (var11, var12, var13) -> {
                        var11.w()
                           .d(var0xxxx -> var0xxxx.a(PoiTypes.n), var0xxxx -> true, var12.dg(), var2 + 1, VillagePlace.Occupancy.c)
                           .filter(var2xxxx -> var2xxxx.a(var12.de(), (double)var2))
                           .or(
                              () -> var11.w()
                                    .a(var0xxxxx -> var0xxxxx.a(PoiTypes.n), var0xxxxx -> true, VillagePlace.Occupancy.c, var12.dg(), var0, var12.dZ())
                           )
                           .or(() -> var3.<GlobalPos>a(var5).map(GlobalPos::b))
                           .ifPresent(var10xx -> {
                              var7.b();
                              var8.b();
                              var9.b();
                              var10.b();
                              var6.a(GlobalPos.a(var11.ab(), var10xx));
                              if (!var10xx.a(var12.de(), (double)var2)) {
                                 var4.a(new MemoryTarget(var10xx, var1, var2));
                              }
                           });
                        return true;
                     }
               ))
      );
   }
}
