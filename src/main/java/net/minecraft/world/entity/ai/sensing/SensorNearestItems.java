package net.minecraft.world.entity.ai.sensing;

import com.google.common.collect.ImmutableSet;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.ai.BehaviorController;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.item.EntityItem;

public class SensorNearestItems extends Sensor<EntityInsentient> {
   private static final long c = 32L;
   private static final long d = 16L;
   public static final int a = 32;

   @Override
   public Set<MemoryModuleType<?>> a() {
      return ImmutableSet.of(MemoryModuleType.K);
   }

   protected void a(WorldServer var0, EntityInsentient var1) {
      BehaviorController<?> var2 = var1.dH();
      List<EntityItem> var3 = var0.a(EntityItem.class, var1.cD().c(32.0, 16.0, 32.0), var0x -> true);
      var3.sort(Comparator.comparingDouble(var1::f));
      Optional<EntityItem> var4 = var3.stream().filter(var1x -> var1.k(var1x.i())).filter(var1x -> var1x.a(var1, 32.0)).filter(var1::B).findFirst();
      var2.a(MemoryModuleType.K, var4);
   }
}
