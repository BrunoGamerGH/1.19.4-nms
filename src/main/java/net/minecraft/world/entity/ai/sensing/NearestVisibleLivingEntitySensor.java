package net.minecraft.world.entity.ai.sensing;

import com.google.common.collect.ImmutableSet;
import java.util.Optional;
import java.util.Set;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;

public abstract class NearestVisibleLivingEntitySensor extends Sensor<EntityLiving> {
   protected abstract boolean a(EntityLiving var1, EntityLiving var2);

   protected abstract MemoryModuleType<EntityLiving> b();

   @Override
   public Set<MemoryModuleType<?>> a() {
      return ImmutableSet.of(this.b());
   }

   @Override
   protected void a(WorldServer var0, EntityLiving var1) {
      var1.dH().a(this.b(), this.b(var1));
   }

   private Optional<EntityLiving> b(EntityLiving var0) {
      return this.a(var0).flatMap(var1x -> var1x.a(var1xx -> this.a(var0, var1xx)));
   }

   protected Optional<NearestVisibleLivingEntities> a(EntityLiving var0) {
      return var0.dH().c(MemoryModuleType.h);
   }
}
