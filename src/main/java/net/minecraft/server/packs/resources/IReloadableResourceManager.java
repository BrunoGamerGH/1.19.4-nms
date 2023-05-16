package net.minecraft.server.packs.resources;

public interface IReloadableResourceManager extends IResourceManager, AutoCloseable {
   @Override
   void close();
}
