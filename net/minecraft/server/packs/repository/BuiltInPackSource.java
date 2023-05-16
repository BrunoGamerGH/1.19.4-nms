package net.minecraft.server.packs.repository;

import com.mojang.logging.LogUtils;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.packs.EnumResourcePackType;
import net.minecraft.server.packs.IResourcePack;
import net.minecraft.server.packs.ResourcePackVanilla;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

public abstract class BuiltInPackSource implements ResourcePackSource {
   private static final Logger b = LogUtils.getLogger();
   public static final String a = "vanilla";
   private final EnumResourcePackType c;
   private final ResourcePackVanilla d;
   private final MinecraftKey e;

   public BuiltInPackSource(EnumResourcePackType var0, ResourcePackVanilla var1, MinecraftKey var2) {
      this.c = var0;
      this.d = var1;
      this.e = var2;
   }

   @Override
   public void a(Consumer<ResourcePackLoader> var0) {
      ResourcePackLoader var1 = this.a(this.d);
      if (var1 != null) {
         var0.accept(var1);
      }

      this.b(var0);
   }

   @Nullable
   protected abstract ResourcePackLoader a(IResourcePack var1);

   protected abstract IChatBaseComponent a(String var1);

   public ResourcePackVanilla a() {
      return this.d;
   }

   private void b(Consumer<ResourcePackLoader> var0) {
      Map<String, Function<String, ResourcePackLoader>> var1 = new HashMap<>();
      this.a(var1::put);
      var1.forEach((var1x, var2x) -> {
         ResourcePackLoader var3 = var2x.apply(var1x);
         if (var3 != null) {
            var0.accept(var3);
         }
      });
   }

   protected void a(BiConsumer<String, Function<String, ResourcePackLoader>> var0) {
      this.d.a(this.c, this.e, var1x -> this.a(var1x, var0));
   }

   protected void a(@Nullable Path var0, BiConsumer<String, Function<String, ResourcePackLoader>> var1) {
      if (var0 != null && Files.isDirectory(var0)) {
         try {
            ResourcePackSourceFolder.a(var0, true, (var1x, var2x) -> var1.accept(a(var1x), var1xx -> this.a(var1xx, var2x, this.a(var1xx))));
         } catch (IOException var4) {
            b.warn("Failed to discover packs in {}", var0, var4);
         }
      }
   }

   private static String a(Path var0) {
      return StringUtils.removeEnd(var0.getFileName().toString(), ".zip");
   }

   @Nullable
   protected abstract ResourcePackLoader a(String var1, ResourcePackLoader.c var2, IChatBaseComponent var3);
}
