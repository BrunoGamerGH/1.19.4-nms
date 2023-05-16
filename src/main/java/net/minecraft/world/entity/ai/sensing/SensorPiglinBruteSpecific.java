package net.minecraft.world.entity.ai.sensing;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ai.BehaviorController;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;
import net.minecraft.world.entity.boss.wither.EntityWither;
import net.minecraft.world.entity.monster.EntitySkeletonWither;
import net.minecraft.world.entity.monster.piglin.EntityPiglinAbstract;

public class SensorPiglinBruteSpecific extends Sensor<EntityLiving> {
   @Override
   public Set<MemoryModuleType<?>> a() {
      return ImmutableSet.of(MemoryModuleType.h, MemoryModuleType.L, MemoryModuleType.am);
   }

   @Override
   protected void a(WorldServer var0, EntityLiving var1) {
      BehaviorController<?> var2 = var1.dH();
      List<EntityPiglinAbstract> var3 = Lists.newArrayList();
      NearestVisibleLivingEntities var4 = var2.c(MemoryModuleType.h).orElse(NearestVisibleLivingEntities.a());
      Optional<EntityInsentient> var5 = var4.a(var0x -> var0x instanceof EntitySkeletonWither || var0x instanceof EntityWither)
         .map(EntityInsentient.class::cast);

      for(EntityLiving var8 : var2.c(MemoryModuleType.g).orElse(ImmutableList.of())) {
         if (var8 instanceof EntityPiglinAbstract && ((EntityPiglinAbstract)var8).fT()) {
            var3.add((EntityPiglinAbstract)var8);
         }
      }

      var2.a(MemoryModuleType.L, var5);
      var2.a(MemoryModuleType.am, var3);
   }
}
