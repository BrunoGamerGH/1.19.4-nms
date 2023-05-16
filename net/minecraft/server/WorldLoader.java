package net.minecraft.server;

import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import net.minecraft.commands.CommandDispatcher;
import net.minecraft.core.IRegistryCustom;
import net.minecraft.core.LayeredRegistryAccess;
import net.minecraft.resources.RegistryDataLoader;
import net.minecraft.server.packs.EnumResourcePackType;
import net.minecraft.server.packs.IResourcePack;
import net.minecraft.server.packs.repository.ResourcePackRepository;
import net.minecraft.server.packs.resources.IReloadableResourceManager;
import net.minecraft.server.packs.resources.IResourceManager;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.WorldDataConfiguration;
import org.slf4j.Logger;

public class WorldLoader {
   private static final Logger a = LogUtils.getLogger();

   public static <D, R> CompletableFuture<R> a(WorldLoader.c var0, WorldLoader.f<D> var1, WorldLoader.e<D, R> var2, Executor var3, Executor var4) {
      try {
         Pair<WorldDataConfiguration, IReloadableResourceManager> var5 = var0.a.a();
         IReloadableResourceManager var6 = (IReloadableResourceManager)var5.getSecond();
         LayeredRegistryAccess<RegistryLayer> var7 = RegistryLayer.a();
         LayeredRegistryAccess<RegistryLayer> var8 = b(var6, var7, RegistryLayer.b, RegistryDataLoader.a);
         IRegistryCustom.Dimension var9 = var8.b(RegistryLayer.c);
         IRegistryCustom.Dimension var10 = RegistryDataLoader.a(var6, var9, RegistryDataLoader.b);
         WorldDataConfiguration var11 = (WorldDataConfiguration)var5.getFirst();
         WorldLoader.b<D> var12 = var1.get(new WorldLoader.a(var6, var11, var9, var10));
         LayeredRegistryAccess<RegistryLayer> var13 = var8.a(RegistryLayer.c, var12.b);
         IRegistryCustom.Dimension var14 = var13.b(RegistryLayer.d);
         return DataPackResources.a(var6, var14, var11.b(), var0.b(), var0.c(), var3, var4).whenComplete((var1x, var2x) -> {
            if (var2x != null) {
               var6.close();
            }
         }).thenApplyAsync(var5x -> {
            var5x.a(var14);
            return var2.create(var6, var5x, var13, var12.a);
         }, var4);
      } catch (Exception var15) {
         return CompletableFuture.failedFuture(var15);
      }
   }

   private static IRegistryCustom.Dimension a(
      IResourceManager var0, LayeredRegistryAccess<RegistryLayer> var1, RegistryLayer var2, List<RegistryDataLoader.b<?>> var3
   ) {
      IRegistryCustom.Dimension var4 = var1.b(var2);
      return RegistryDataLoader.a(var0, var4, var3);
   }

   public static LayeredRegistryAccess<RegistryLayer> b(
      IResourceManager var0, LayeredRegistryAccess<RegistryLayer> var1, RegistryLayer var2, List<RegistryDataLoader.b<?>> var3
   ) {
      IRegistryCustom.Dimension var4 = a(var0, var1, var2, var3);
      return var1.a(var2, var4);
   }

   public static record a(
      IResourceManager resources,
      WorldDataConfiguration dataConfiguration,
      IRegistryCustom.Dimension datapackWorldgen,
      IRegistryCustom.Dimension datapackDimensions
   ) {
      private final IResourceManager a;
      private final WorldDataConfiguration b;
      private final IRegistryCustom.Dimension c;
      private final IRegistryCustom.Dimension d;

      public a(IResourceManager var0, WorldDataConfiguration var1, IRegistryCustom.Dimension var2, IRegistryCustom.Dimension var3) {
         this.a = var0;
         this.b = var1;
         this.c = var2;
         this.d = var3;
      }
   }

   public static record b<D>(D cookie, IRegistryCustom.Dimension finalDimensions) {
      final D a;
      final IRegistryCustom.Dimension b;

      public b(D var0, IRegistryCustom.Dimension var1) {
         this.a = var0;
         this.b = var1;
      }
   }

   public static record c(WorldLoader.d packConfig, CommandDispatcher.ServerType commandSelection, int functionCompilationLevel) {
      final WorldLoader.d a;
      private final CommandDispatcher.ServerType b;
      private final int c;

      public c(WorldLoader.d var0, CommandDispatcher.ServerType var1, int var2) {
         this.a = var0;
         this.b = var1;
         this.c = var2;
      }
   }

   public static record d(ResourcePackRepository packRepository, WorldDataConfiguration initialDataConfig, boolean safeMode, boolean initMode) {
      private final ResourcePackRepository a;
      private final WorldDataConfiguration b;
      private final boolean c;
      private final boolean d;

      public d(ResourcePackRepository var0, WorldDataConfiguration var1, boolean var2, boolean var3) {
         this.a = var0;
         this.b = var1;
         this.c = var2;
         this.d = var3;
      }

      public Pair<WorldDataConfiguration, IReloadableResourceManager> a() {
         FeatureFlagSet var0 = this.d ? FeatureFlags.d.a() : this.b.b();
         WorldDataConfiguration var1 = MinecraftServer.a(this.a, this.b.a(), this.c, var0);
         if (!this.d) {
            var1 = var1.a(this.b.b());
         }

         List<IResourcePack> var2 = this.a.g();
         IReloadableResourceManager var3 = new ResourceManager(EnumResourcePackType.b, var2);
         return Pair.of(var1, var3);
      }

      public ResourcePackRepository b() {
         return this.a;
      }

      public WorldDataConfiguration c() {
         return this.b;
      }

      public boolean d() {
         return this.c;
      }

      public boolean e() {
         return this.d;
      }
   }

   @FunctionalInterface
   public interface e<D, R> {
      R create(IReloadableResourceManager var1, DataPackResources var2, LayeredRegistryAccess<RegistryLayer> var3, D var4);
   }

   @FunctionalInterface
   public interface f<D> {
      WorldLoader.b<D> get(WorldLoader.a var1);
   }
}
