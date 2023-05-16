package net.minecraft.world.entity.ai.behavior;

import com.mojang.datafixers.kinds.App;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.Holder;
import net.minecraft.network.protocol.game.PacketDebug;
import net.minecraft.world.entity.EntityCreature;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.behavior.declarative.Trigger;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.village.poi.VillagePlaceType;
import net.minecraft.world.entity.npc.EntityVillager;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.level.pathfinder.PathEntity;

public class BehaviorLeaveJob {
   public static BehaviorControl<EntityVillager> a(float var0) {
      return BehaviorBuilder.a(
         (Function<BehaviorBuilder.b<EntityVillager>, ? extends App<BehaviorBuilder.c<EntityVillager>, Trigger<EntityVillager>>>)(var1 -> var1.group(
                  var1.b(MemoryModuleType.d), var1.c(MemoryModuleType.c), var1.b(MemoryModuleType.g), var1.a(MemoryModuleType.m), var1.a(MemoryModuleType.n)
               )
               .apply(
                  var1,
                  (var2, var3, var4, var5, var6) -> (var6x, var7, var8) -> {
                        if (var7.y_()) {
                           return false;
                        } else if (var7.gd().b() != VillagerProfession.b) {
                           return false;
                        } else {
                           BlockPosition var10 = var1.<GlobalPos>b(var2).b();
                           Optional<Holder<VillagePlaceType>> var11 = var6x.w().c(var10);
                           if (var11.isEmpty()) {
                              return true;
                           } else {
                              var1.<List<EntityLiving>>b(var4)
                                 .stream()
                                 .filter(var1xxx -> var1xxx instanceof EntityVillager && var1xxx != var7)
                                 .map(var0xxxx -> (EntityVillager)var0xxxx)
                                 .filter(EntityLiving::bq)
                                 .filter(var2xx -> a(var11.get(), var2xx, var10))
                                 .findFirst()
                                 .ifPresent(var6xx -> {
                                    var5.b();
                                    var6.b();
                                    var2.b();
                                    if (var6xx.dH().c(MemoryModuleType.c).isEmpty()) {
                                       BehaviorUtil.a(var6xx, var10, var0, 1);
                                       var6xx.dH().a(MemoryModuleType.d, GlobalPos.a(var6x.ab(), var10));
                                       PacketDebug.c(var6x, var10);
                                    }
                                 });
                              return true;
                           }
                        }
                     }
               ))
      );
   }

   private static boolean a(Holder<VillagePlaceType> var0, EntityVillager var1, BlockPosition var2) {
      boolean var3 = var1.dH().c(MemoryModuleType.d).isPresent();
      if (var3) {
         return false;
      } else {
         Optional<GlobalPos> var4 = var1.dH().c(MemoryModuleType.c);
         VillagerProfession var5 = var1.gd().b();
         if (var5.b().test(var0)) {
            return var4.isEmpty() ? a(var1, var2, var0.a()) : var4.get().b().equals(var2);
         } else {
            return false;
         }
      }
   }

   private static boolean a(EntityCreature var0, BlockPosition var1, VillagePlaceType var2) {
      PathEntity var3 = var0.G().a(var1, var2.c());
      return var3 != null && var3.j();
   }
}
