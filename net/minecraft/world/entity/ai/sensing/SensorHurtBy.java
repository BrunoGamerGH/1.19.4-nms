package net.minecraft.world.entity.ai.sensing;

import com.google.common.collect.ImmutableSet;
import java.util.Set;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ai.BehaviorController;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

public class SensorHurtBy extends Sensor<EntityLiving> {
   @Override
   public Set<MemoryModuleType<?>> a() {
      return ImmutableSet.of(MemoryModuleType.x, MemoryModuleType.y);
   }

   @Override
   protected void a(WorldServer var0, EntityLiving var1) {
      BehaviorController<?> var2 = var1.dH();
      DamageSource var3 = var1.eq();
      if (var3 != null) {
         var2.a(MemoryModuleType.x, var1.eq());
         Entity var4 = var3.d();
         if (var4 instanceof EntityLiving) {
            var2.a(MemoryModuleType.y, (EntityLiving)var4);
         }
      } else {
         var2.b(MemoryModuleType.x);
      }

      var2.c(MemoryModuleType.y).ifPresent(var2x -> {
         if (!var2x.bq() || var2x.H != var0) {
            var2.b(MemoryModuleType.y);
         }
      });
   }
}
