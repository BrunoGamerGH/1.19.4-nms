package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.Maps;
import com.mojang.datafixers.kinds.App;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.function.Function;
import net.minecraft.core.BlockPosition;
import net.minecraft.world.entity.EntityCreature;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.behavior.declarative.MemoryAccessor;
import net.minecraft.world.entity.ai.behavior.declarative.Trigger;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryTarget;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.phys.Vec3D;

public class BehaviorPlay {
   private static final int a = 20;
   private static final int b = 8;
   private static final float c = 0.6F;
   private static final float d = 0.6F;
   private static final int e = 5;
   private static final int f = 10;

   public static BehaviorControl<EntityCreature> a() {
      return BehaviorBuilder.a(
         (Function<BehaviorBuilder.b<EntityCreature>, ? extends App<BehaviorBuilder.c<EntityCreature>, Trigger<EntityCreature>>>)(var0 -> var0.group(
                  var0.b(MemoryModuleType.i), var0.c(MemoryModuleType.m), var0.a(MemoryModuleType.n), var0.a(MemoryModuleType.q)
               )
               .apply(var0, (var1, var2, var3, var4) -> (var5, var6, var7) -> {
                     if (var5.r_().a(10) != 0) {
                        return false;
                     } else {
                        List<EntityLiving> var9 = var0.b(var1);
                        Optional<EntityLiving> var10 = var9.stream().filter(var1xx -> a((EntityLiving)var6, var1xx)).findAny();
                        if (!var10.isPresent()) {
                           Optional<EntityLiving> var11 = a(var9);
                           if (var11.isPresent()) {
                              a(var4, var3, var2, var11.get());
                              return true;
                           } else {
                              var9.stream().findAny().ifPresent(var3xx -> a(var4, var3, var2, var3xx));
                              return true;
                           }
                        } else {
                           for(int var11 = 0; var11 < 10; ++var11) {
                              Vec3D var12 = LandRandomPos.a(var6, 20, 8);
                              if (var12 != null && var5.b(BlockPosition.a(var12))) {
                                 var2.a(new MemoryTarget(var12, 0.6F, 0));
                                 break;
                              }
                           }
      
                           return true;
                        }
                     }
                  }))
      );
   }

   private static void a(
      MemoryAccessor<?, EntityLiving> var0, MemoryAccessor<?, BehaviorPosition> var1, MemoryAccessor<?, MemoryTarget> var2, EntityLiving var3
   ) {
      var0.a(var3);
      var1.a(new BehaviorPositionEntity(var3, true));
      var2.a(new MemoryTarget(new BehaviorPositionEntity(var3, false), 0.6F, 1));
   }

   private static Optional<EntityLiving> a(List<EntityLiving> var0) {
      Map<EntityLiving, Integer> var1 = b(var0);
      return var1.entrySet()
         .stream()
         .sorted(Comparator.comparingInt(Entry::getValue))
         .filter(var0x -> var0x.getValue() > 0 && var0x.getValue() <= 5)
         .map(Entry::getKey)
         .findFirst();
   }

   private static Map<EntityLiving, Integer> b(List<EntityLiving> var0) {
      Map<EntityLiving, Integer> var1 = Maps.newHashMap();
      var0.stream().filter(BehaviorPlay::b).forEach(var1x -> var1.compute(a(var1x), (var0xx, var1xx) -> var1xx == null ? 1 : var1xx + 1));
      return var1;
   }

   private static EntityLiving a(EntityLiving var0) {
      return var0.dH().c(MemoryModuleType.q).get();
   }

   private static boolean b(EntityLiving var0) {
      return var0.dH().c(MemoryModuleType.q).isPresent();
   }

   private static boolean a(EntityLiving var0, EntityLiving var1) {
      return var1.dH().c(MemoryModuleType.q).filter(var1x -> var1x == var0).isPresent();
   }
}
