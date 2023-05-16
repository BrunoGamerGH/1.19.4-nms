package net.minecraft.world.entity.ai.sensing;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.monster.warden.Warden;

public class WardenEntitySensor extends SensorNearestLivingEntities<Warden> {
   @Override
   public Set<MemoryModuleType<?>> a() {
      return ImmutableSet.copyOf(Iterables.concat(super.a(), List.of(MemoryModuleType.B)));
   }

   protected void a(WorldServer var0, Warden var1) {
      super.a(var0, var1);
      a(var1, var0x -> var0x.ae() == EntityTypes.bt)
         .or(() -> a(var1, var0xx -> var0xx.ae() != EntityTypes.bt))
         .ifPresentOrElse(var1x -> var1.dH().a(MemoryModuleType.B, var1x), () -> var1.dH().b(MemoryModuleType.B));
   }

   private static Optional<EntityLiving> a(Warden var0, Predicate<EntityLiving> var1) {
      return var0.dH().c(MemoryModuleType.g).stream().flatMap(Collection::stream).filter(var0::a).filter(var1).findFirst();
   }

   @Override
   protected int b() {
      return 24;
   }

   @Override
   protected int c() {
      return 24;
   }
}
