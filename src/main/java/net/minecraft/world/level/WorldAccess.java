package net.minecraft.world.level;

import net.minecraft.server.level.WorldServer;
import net.minecraft.world.entity.Entity;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

public interface WorldAccess extends GeneratorAccess {
   WorldServer C();

   default void a_(Entity entity) {
      this.addFreshEntityWithPassengers(entity, SpawnReason.DEFAULT);
   }

   default void addFreshEntityWithPassengers(Entity entity, SpawnReason reason) {
      entity.cO().forEach(e -> this.addFreshEntity(e, reason));
   }

   @Override
   default WorldServer getMinecraftWorld() {
      return this.C();
   }
}
