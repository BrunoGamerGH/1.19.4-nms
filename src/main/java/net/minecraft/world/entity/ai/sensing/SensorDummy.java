package net.minecraft.world.entity.ai.sensing;

import com.google.common.collect.ImmutableSet;
import java.util.Set;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

public class SensorDummy extends Sensor<EntityLiving> {
   @Override
   protected void a(WorldServer var0, EntityLiving var1) {
   }

   @Override
   public Set<MemoryModuleType<?>> a() {
      return ImmutableSet.of();
   }
}
