package net.minecraft.world.entity.ai.behavior;

import com.mojang.datafixers.kinds.App;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.behavior.declarative.Trigger;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.village.poi.VillagePlaceType;
import net.minecraft.world.entity.npc.EntityVillager;
import net.minecraft.world.entity.npc.VillagerProfession;

public class BehaviorBetterJob {
   public static BehaviorControl<EntityVillager> a() {
      return BehaviorBuilder.a(
         (Function<BehaviorBuilder.b<EntityVillager>, ? extends App<BehaviorBuilder.c<EntityVillager>, Trigger<EntityVillager>>>)(var0 -> var0.group(
                  var0.b(MemoryModuleType.c), var0.b(MemoryModuleType.g)
               )
               .apply(
                  var0,
                  (var1, var2) -> (var3, var4, var5) -> {
                        GlobalPos var7 = var0.b(var1);
                        var3.w()
                           .c(var7.b())
                           .ifPresent(
                              var4x -> var0.<List<EntityLiving>>b(var2)
                                    .stream()
                                    .filter(var1xxx -> var1xxx instanceof EntityVillager && var1xxx != var4)
                                    .map(var0xxxx -> (EntityVillager)var0xxxx)
                                    .filter(EntityLiving::bq)
                                    .filter(var2xxx -> a(var7, var4x, var2xxx))
                                    .reduce(var4, BehaviorBetterJob::a)
                           );
                        return true;
                     }
               ))
      );
   }

   private static EntityVillager a(EntityVillager var0, EntityVillager var1) {
      EntityVillager var2;
      EntityVillager var3;
      if (var0.r() > var1.r()) {
         var2 = var0;
         var3 = var1;
      } else {
         var2 = var1;
         var3 = var0;
      }

      var3.dH().b(MemoryModuleType.c);
      return var2;
   }

   private static boolean a(GlobalPos var0, Holder<VillagePlaceType> var1, EntityVillager var2) {
      Optional<GlobalPos> var3 = var2.dH().c(MemoryModuleType.c);
      return var3.isPresent() && var0.equals(var3.get()) && a(var1, var2.gd().b());
   }

   private static boolean a(Holder<VillagePlaceType> var0, VillagerProfession var1) {
      return var1.b().test(var0);
   }
}
