package net.minecraft.server.packs;

import com.google.gson.JsonObject;
import com.mojang.logging.LogUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import javax.annotation.Nullable;
import net.minecraft.server.packs.metadata.ResourcePackMetaParser;
import net.minecraft.server.packs.resources.IoSupplier;
import net.minecraft.util.ChatDeserializer;
import org.slf4j.Logger;

public abstract class ResourcePackAbstract implements IResourcePack {
   private static final Logger a = LogUtils.getLogger();
   private final String d;
   private final boolean e;

   protected ResourcePackAbstract(String var0, boolean var1) {
      this.d = var0;
      this.e = var1;
   }

   @Nullable
   @Override
   public <T> T a(ResourcePackMetaParser<T> var0) throws IOException {
      IoSupplier<InputStream> var1 = this.a(new String[]{"pack.mcmeta"});
      if (var1 == null) {
         return null;
      } else {
         Object var4;
         try (InputStream var2 = var1.get()) {
            var4 = a(var0, var2);
         }

         return (T)var4;
      }
   }

   @Nullable
   public static <T> T a(ResourcePackMetaParser<T> var0, InputStream var1) {
      JsonObject var2;
      try (BufferedReader var3 = new BufferedReader(new InputStreamReader(var1, StandardCharsets.UTF_8))) {
         var2 = ChatDeserializer.a(var3);
      } catch (Exception var9) {
         a.error("Couldn't load {} metadata", var0.a(), var9);
         return null;
      }

      if (!var2.has(var0.a())) {
         return null;
      } else {
         try {
            return var0.a(ChatDeserializer.t(var2, var0.a()));
         } catch (Exception var7) {
            a.error("Couldn't load {} metadata", var0.a(), var7);
            return null;
         }
      }
   }

   @Override
   public String a() {
      return this.d;
   }

   @Override
   public boolean b() {
      return this.e;
   }
}
