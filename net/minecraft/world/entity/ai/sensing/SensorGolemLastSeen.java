package net.minecraft.world.entity.ai.sensing;

import com.google.common.collect.ImmutableSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

public class SensorGolemLastSeen extends Sensor<EntityLiving> {
   private static final int a = 200;
   private static final int c = 600;

   public SensorGolemLastSeen() {
      this(200);
   }

   public SensorGolemLastSeen(int var0) {
      super(var0);
   }

   @Override
   protected void a(WorldServer var0, EntityLiving var1) {
      a(var1);
   }

   @Override
   public Set<MemoryModuleType<?>> a() {
      return ImmutableSet.of(MemoryModuleType.g);
   }

   public static void a(EntityLiving var0) {
      Optional<List<EntityLiving>> var1 = var0.dH().c(MemoryModuleType.g);
      if (var1.isPresent()) {
         boolean var2 = var1.get().stream().anyMatch(var0x -> var0x.ae().equals(EntityTypes.ac));
         if (var2) {
            b(var0);
         }
      }
   }

   public static void b(EntityLiving var0) {
      var0.dH().a(MemoryModuleType.F, true, 600L);
   }
}
