package net.minecraft.world.entity.ai.sensing;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.animal.frog.Frog;

public class FrogAttackablesSensor extends NearestVisibleLivingEntitySensor {
   public static final float a = 10.0F;

   @Override
   protected boolean a(EntityLiving var0, EntityLiving var1) {
      return !var0.dH().a(MemoryModuleType.T) && Sensor.c(var0, var1) && Frog.m(var1) && !this.e(var0, var1) ? var1.a(var0, 10.0) : false;
   }

   private boolean e(EntityLiving var0, EntityLiving var1) {
      List<UUID> var2 = var0.dH().c(MemoryModuleType.Z).orElseGet(ArrayList::new);
      return var2.contains(var1.cs());
   }

   @Override
   protected MemoryModuleType<EntityLiving> b() {
      return MemoryModuleType.B;
   }
}
