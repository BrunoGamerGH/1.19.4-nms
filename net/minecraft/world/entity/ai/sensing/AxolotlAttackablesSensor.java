package net.minecraft.world.entity.ai.sensing;

import net.minecraft.tags.TagsEntity;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

public class AxolotlAttackablesSensor extends NearestVisibleLivingEntitySensor {
   public static final float a = 8.0F;

   @Override
   protected boolean a(EntityLiving var0, EntityLiving var1) {
      return this.f(var0, var1) && var1.aW() && (this.b(var1) || this.e(var0, var1)) && Sensor.c(var0, var1);
   }

   private boolean e(EntityLiving var0, EntityLiving var1) {
      return !var0.dH().a(MemoryModuleType.T) && var1.ae().a(TagsEntity.h);
   }

   private boolean b(EntityLiving var0) {
      return var0.ae().a(TagsEntity.g);
   }

   private boolean f(EntityLiving var0, EntityLiving var1) {
      return var1.f(var0) <= 64.0;
   }

   @Override
   protected MemoryModuleType<EntityLiving> b() {
      return MemoryModuleType.B;
   }
}
