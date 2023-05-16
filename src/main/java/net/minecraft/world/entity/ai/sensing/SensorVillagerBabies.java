package net.minecraft.world.entity.ai.sensing;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import java.util.List;
import java.util.Set;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;

public class SensorVillagerBabies extends Sensor<EntityLiving> {
   @Override
   public Set<MemoryModuleType<?>> a() {
      return ImmutableSet.of(MemoryModuleType.i);
   }

   @Override
   protected void a(WorldServer var0, EntityLiving var1) {
      var1.dH().a(MemoryModuleType.i, this.a(var1));
   }

   private List<EntityLiving> a(EntityLiving var0) {
      return ImmutableList.copyOf(this.c(var0).b(this::b));
   }

   private boolean b(EntityLiving var0) {
      return var0.ae() == EntityTypes.bf && var0.y_();
   }

   private NearestVisibleLivingEntities c(EntityLiving var0) {
      return var0.dH().c(MemoryModuleType.h).orElse(NearestVisibleLivingEntities.a());
   }
}
