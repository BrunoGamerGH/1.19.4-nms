package net.minecraft.world.entity.ai.sensing;

import com.google.common.collect.ImmutableSet;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.IEntitySelector;
import net.minecraft.world.entity.ai.BehaviorController;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.player.EntityHuman;

public class SensorNearestPlayers extends Sensor<EntityLiving> {
   @Override
   public Set<MemoryModuleType<?>> a() {
      return ImmutableSet.of(MemoryModuleType.j, MemoryModuleType.k, MemoryModuleType.l);
   }

   @Override
   protected void a(WorldServer var0, EntityLiving var1) {
      List<EntityHuman> var2 = var0.v()
         .stream()
         .filter(IEntitySelector.f)
         .filter(var1x -> var1.a(var1x, 16.0))
         .sorted(Comparator.comparingDouble(var1::f))
         .collect(Collectors.toList());
      BehaviorController<?> var3 = var1.dH();
      var3.a(MemoryModuleType.j, var2);
      List<EntityHuman> var4 = var2.stream().filter(var1x -> b(var1, var1x)).collect(Collectors.toList());
      var3.a(MemoryModuleType.k, var4.isEmpty() ? null : var4.get(0));
      Optional<EntityHuman> var5 = var4.stream().filter(var1x -> c(var1, var1x)).findFirst();
      var3.a(MemoryModuleType.l, var5);
   }
}
