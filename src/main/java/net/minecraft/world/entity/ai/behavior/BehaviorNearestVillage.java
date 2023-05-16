package net.minecraft.world.entity.ai.behavior;

import com.mojang.datafixers.kinds.App;
import java.util.function.Function;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.SectionPosition;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.behavior.declarative.Trigger;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryTarget;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.entity.ai.village.poi.VillagePlace;
import net.minecraft.world.entity.npc.EntityVillager;
import net.minecraft.world.phys.Vec3D;

public class BehaviorNearestVillage {
   public static BehaviorControl<EntityVillager> a(float var0, int var1) {
      return BehaviorBuilder.a(
         (Function<BehaviorBuilder.b<EntityVillager>, ? extends App<BehaviorBuilder.c<EntityVillager>, Trigger<EntityVillager>>>)(var2 -> var2.group(
                  var2.c(MemoryModuleType.m)
               )
               .apply(var2, var2x -> (var3, var4, var5) -> {
                     if (var3.b(var4.dg())) {
                        return false;
                     } else {
                        VillagePlace var7 = var3.w();
                        int var8 = var7.a(SectionPosition.a(var4.dg()));
                        Vec3D var9 = null;
      
                        for(int var10 = 0; var10 < 5; ++var10) {
                           Vec3D var11 = LandRandomPos.a(var4, 15, 7, var1xxxx -> (double)(-var7.a(SectionPosition.a(var1xxxx))));
                           if (var11 != null) {
                              int var12 = var7.a(SectionPosition.a(BlockPosition.a(var11)));
                              if (var12 < var8) {
                                 var9 = var11;
                                 break;
                              }
      
                              if (var12 == var8) {
                                 var9 = var11;
                              }
                           }
                        }
      
                        if (var9 != null) {
                           var2x.a(new MemoryTarget(var9, var0, var1));
                        }
      
                        return true;
                     }
                  }))
      );
   }
}
