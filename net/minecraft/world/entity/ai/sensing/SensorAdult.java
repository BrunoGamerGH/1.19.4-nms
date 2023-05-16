package net.minecraft.world.entity.ai.sensing;

import com.google.common.collect.ImmutableSet;
import java.util.Optional;
import java.util.Set;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.entity.EntityAgeable;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;

public class SensorAdult extends Sensor<EntityAgeable> {
   @Override
   public Set<MemoryModuleType<?>> a() {
      return ImmutableSet.of(MemoryModuleType.J, MemoryModuleType.h);
   }

   protected void a(WorldServer var0, EntityAgeable var1) {
      var1.dH().c(MemoryModuleType.h).ifPresent(var1x -> this.a(var1, var1x));
   }

   private void a(EntityAgeable var0, NearestVisibleLivingEntities var1) {
      Optional<EntityAgeable> var2 = var1.a(var1x -> var1x.ae() == var0.ae() && !var1x.y_()).map(EntityAgeable.class::cast);
      var0.dH().a(MemoryModuleType.J, var2);
   }
}
