package net.minecraft.server.packs;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
import java.util.function.BiConsumer;
import javax.annotation.Nullable;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.packs.metadata.ResourcePackMetaParser;
import net.minecraft.server.packs.resources.IoSupplier;

public interface IResourcePack extends AutoCloseable {
   String b = ".mcmeta";
   String c = "pack.mcmeta";

   @Nullable
   IoSupplier<InputStream> a(String... var1);

   @Nullable
   IoSupplier<InputStream> a(EnumResourcePackType var1, MinecraftKey var2);

   void a(EnumResourcePackType var1, String var2, String var3, IResourcePack.a var4);

   Set<String> a(EnumResourcePackType var1);

   @Nullable
   <T> T a(ResourcePackMetaParser<T> var1) throws IOException;

   String a();

   default boolean b() {
      return false;
   }

   @Override
   void close();

   @FunctionalInterface
   public interface a extends BiConsumer<MinecraftKey, IoSupplier<InputStream>> {
   }
}
