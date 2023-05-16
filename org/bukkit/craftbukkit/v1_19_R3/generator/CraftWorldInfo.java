package org.bukkit.craftbukkit.v1_19_R3.generator;

import java.util.UUID;
import net.minecraft.world.level.dimension.DimensionManager;
import net.minecraft.world.level.storage.Convertable;
import net.minecraft.world.level.storage.IWorldDataServer;
import net.minecraft.world.level.storage.WorldDataServer;
import org.bukkit.World.Environment;
import org.bukkit.craftbukkit.v1_19_R3.util.WorldUUID;
import org.bukkit.generator.WorldInfo;

public class CraftWorldInfo implements WorldInfo {
   private final String name;
   private final UUID uuid;
   private final Environment environment;
   private final long seed;
   private final int minHeight;
   private final int maxHeight;

   public CraftWorldInfo(IWorldDataServer worldDataServer, Convertable.ConversionSession session, Environment environment, DimensionManager dimensionManager) {
      this.name = worldDataServer.g();
      this.uuid = WorldUUID.getUUID(session.c.f().toFile());
      this.environment = environment;
      this.seed = ((WorldDataServer)worldDataServer).A().b();
      this.minHeight = dimensionManager.n();
      this.maxHeight = dimensionManager.n() + dimensionManager.o();
   }

   public CraftWorldInfo(String name, UUID uuid, Environment environment, long seed, int minHeight, int maxHeight) {
      this.name = name;
      this.uuid = uuid;
      this.environment = environment;
      this.seed = seed;
      this.minHeight = minHeight;
      this.maxHeight = maxHeight;
   }

   public String getName() {
      return this.name;
   }

   public UUID getUID() {
      return this.uuid;
   }

   public Environment getEnvironment() {
      return this.environment;
   }

   public long getSeed() {
      return this.seed;
   }

   public int getMinHeight() {
      return this.minHeight;
   }

   public int getMaxHeight() {
      return this.maxHeight;
   }
}
