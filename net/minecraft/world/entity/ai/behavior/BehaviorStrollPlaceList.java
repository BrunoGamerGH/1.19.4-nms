package net.minecraft.world.entity.ai.behavior;

import com.mojang.datafixers.kinds.App;
import java.util.List;
import java.util.function.Function;
import net.minecraft.core.GlobalPos;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.behavior.declarative.Trigger;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryTarget;
import net.minecraft.world.entity.npc.EntityVillager;
import org.apache.commons.lang3.mutable.MutableLong;

public class BehaviorStrollPlaceList {
   public static BehaviorControl<EntityVillager> a(MemoryModuleType<List<GlobalPos>> var0, float var1, int var2, int var3, MemoryModuleType<GlobalPos> var4) {
      MutableLong var5 = new MutableLong(0L);
      return BehaviorBuilder.a(
         (Function<BehaviorBuilder.b<EntityVillager>, ? extends App<BehaviorBuilder.c<EntityVillager>, Trigger<EntityVillager>>>)(var6 -> var6.group(
                  var6.a(MemoryModuleType.m), var6.b(var0), var6.b(var4)
               )
               .apply(var6, (var5xx, var6x, var7) -> (var8, var9, var10) -> {
                     List<GlobalPos> var12 = var6.b(var6x);
                     GlobalPos var13 = var6.b(var7);
                     if (var12.isEmpty()) {
                        return false;
                     } else {
                        GlobalPos var14 = var12.get(var8.r_().a(var12.size()));
                        if (var14 != null && var8.ab() == var14.a() && var13.b().a(var9.de(), (double)var3)) {
                           if (var10 > var5.getValue()) {
                              var5xx.a(new MemoryTarget(var14.b(), var1, var2));
                              var5.setValue(var10 + 100L);
                           }
      
                           return true;
                        } else {
                           return false;
                        }
                     }
                  }))
      );
   }
}
