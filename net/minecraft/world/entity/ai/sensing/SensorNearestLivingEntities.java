package net.minecraft.world.entity.ai.sensing;

import com.google.common.collect.ImmutableSet;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ai.BehaviorController;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;
import net.minecraft.world.phys.AxisAlignedBB;

public class SensorNearestLivingEntities<T extends EntityLiving> extends Sensor<T> {
   @Override
   protected void a(WorldServer var0, T var1) {
      AxisAlignedBB var2 = var1.cD().c((double)this.b(), (double)this.c(), (double)this.b());
      List<EntityLiving> var3 = var0.a(EntityLiving.class, var2, var1x -> var1x != var1 && var1x.bq());
      var3.sort(Comparator.comparingDouble(var1::f));
      BehaviorController<?> var4 = var1.dH();
      var4.a(MemoryModuleType.g, var3);
      var4.a(MemoryModuleType.h, new NearestVisibleLivingEntities(var1, var3));
   }

   protected int b() {
      return 16;
   }

   protected int c() {
      return 16;
   }

   @Override
   public Set<MemoryModuleType<?>> a() {
      return ImmutableSet.of(MemoryModuleType.g, MemoryModuleType.h);
   }
}
