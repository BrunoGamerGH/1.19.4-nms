package net.minecraft.server.packs.resources;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.mojang.logging.LogUtils;
import java.io.IOException;
import java.io.Reader;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.util.ChatDeserializer;
import net.minecraft.util.profiling.GameProfilerFiller;
import org.slf4j.Logger;

public abstract class ResourceDataJson extends ResourceDataAbstract<Map<MinecraftKey, JsonElement>> {
   private static final Logger a = LogUtils.getLogger();
   private final Gson b;
   private final String c;

   public ResourceDataJson(Gson var0, String var1) {
      this.b = var0;
      this.c = var1;
   }

   protected Map<MinecraftKey, JsonElement> a(IResourceManager var0, GameProfilerFiller var1) {
      Map<MinecraftKey, JsonElement> var2 = Maps.newHashMap();
      FileToIdConverter var3 = FileToIdConverter.a(this.c);

      for(Entry<MinecraftKey, IResource> var5 : var3.a(var0).entrySet()) {
         MinecraftKey var6 = var5.getKey();
         MinecraftKey var7 = var3.b(var6);

         try (Reader var8 = var5.getValue().e()) {
            JsonElement var9 = ChatDeserializer.a(this.b, var8, JsonElement.class);
            JsonElement var10 = (JsonElement)var2.put(var7, var9);
            if (var10 != null) {
               throw new IllegalStateException("Duplicate data file ignored with ID " + var7);
            }
         } catch (IllegalArgumentException | IOException | JsonParseException var14) {
            a.error("Couldn't parse data file {} from {}", new Object[]{var7, var6, var14});
         }
      }

      return var2;
   }
}
