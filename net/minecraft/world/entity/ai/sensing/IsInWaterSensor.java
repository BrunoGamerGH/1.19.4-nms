package net.minecraft.world.entity.ai.sensing;

import com.google.common.collect.ImmutableSet;
import java.util.Set;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

public class IsInWaterSensor extends Sensor<EntityLiving> {
   @Override
   public Set<MemoryModuleType<?>> a() {
      return ImmutableSet.of(MemoryModuleType.W);
   }

   @Override
   protected void a(WorldServer var0, EntityLiving var1) {
      if (var1.aT()) {
         var1.dH().a(MemoryModuleType.W, Unit.a);
      } else {
         var1.dH().b(MemoryModuleType.W);
      }
   }
}
