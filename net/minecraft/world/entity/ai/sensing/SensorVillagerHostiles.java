package net.minecraft.world.entity.ai.sensing;

import com.google.common.collect.ImmutableMap;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

public class SensorVillagerHostiles extends NearestVisibleLivingEntitySensor {
   private static final ImmutableMap<EntityTypes<?>, Float> a = ImmutableMap.builder()
      .put(EntityTypes.y, 8.0F)
      .put(EntityTypes.G, 12.0F)
      .put(EntityTypes.Z, 8.0F)
      .put(EntityTypes.aa, 12.0F)
      .put(EntityTypes.ay, 15.0F)
      .put(EntityTypes.aD, 12.0F)
      .put(EntityTypes.be, 8.0F)
      .put(EntityTypes.bg, 10.0F)
      .put(EntityTypes.bo, 10.0F)
      .put(EntityTypes.bp, 8.0F)
      .put(EntityTypes.br, 8.0F)
      .build();

   @Override
   protected boolean a(EntityLiving var0, EntityLiving var1) {
      return this.b(var1) && this.e(var0, var1);
   }

   private boolean e(EntityLiving var0, EntityLiving var1) {
      float var2 = a.get(var1.ae());
      return var1.f(var0) <= (double)(var2 * var2);
   }

   @Override
   protected MemoryModuleType<EntityLiving> b() {
      return MemoryModuleType.A;
   }

   private boolean b(EntityLiving var0) {
      return a.containsKey(var0.ae());
   }
}
