package net.minecraft.server;

import net.minecraft.core.LayeredRegistryAccess;
import net.minecraft.server.packs.resources.IReloadableResourceManager;
import net.minecraft.world.level.storage.SaveData;

public record WorldStem(
   IReloadableResourceManager resourceManager, DataPackResources dataPackResources, LayeredRegistryAccess<RegistryLayer> registries, SaveData worldData
) implements AutoCloseable {
   private final IReloadableResourceManager a;
   private final DataPackResources b;
   private final LayeredRegistryAccess<RegistryLayer> c;
   private final SaveData d;

   public WorldStem(IReloadableResourceManager var0, DataPackResources var1, LayeredRegistryAccess<RegistryLayer> var2, SaveData var3) {
      this.a = var0;
      this.b = var1;
      this.c = var2;
      this.d = var3;
   }

   @Override
   public void close() {
      this.a.close();
   }
}
