package net.minecraft;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.logging.LogUtils;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;
import net.minecraft.server.packs.EnumResourcePackType;
import net.minecraft.util.ChatDeserializer;
import net.minecraft.world.level.storage.DataVersion;
import org.slf4j.Logger;

public class MinecraftVersion implements WorldVersion {
   private static final Logger b = LogUtils.getLogger();
   public static final WorldVersion a = new MinecraftVersion();
   private final String c;
   private final String d;
   private final boolean e;
   private final DataVersion f;
   private final int g;
   private final int h;
   private final int i;
   private final Date j;

   private MinecraftVersion() {
      this.c = UUID.randomUUID().toString().replaceAll("-", "");
      this.d = "1.19.4";
      this.e = true;
      this.f = new DataVersion(3337, "main");
      this.g = SharedConstants.c();
      this.h = 13;
      this.i = 12;
      this.j = new Date();
   }

   private MinecraftVersion(JsonObject var0) {
      this.c = ChatDeserializer.h(var0, "id");
      this.d = ChatDeserializer.h(var0, "name");
      this.e = ChatDeserializer.j(var0, "stable");
      this.f = new DataVersion(ChatDeserializer.n(var0, "world_version"), ChatDeserializer.a(var0, "series_id", DataVersion.a));
      this.g = ChatDeserializer.n(var0, "protocol_version");
      JsonObject var1 = ChatDeserializer.t(var0, "pack_version");
      this.h = ChatDeserializer.n(var1, "resource");
      this.i = ChatDeserializer.n(var1, "data");
      this.j = Date.from(ZonedDateTime.parse(ChatDeserializer.h(var0, "build_time")).toInstant());
   }

   public static WorldVersion a() {
      try {
         MinecraftVersion var2;
         try (InputStream var0 = MinecraftVersion.class.getResourceAsStream("/version.json")) {
            if (var0 == null) {
               b.warn("Missing version information!");
               return a;
            }

            try (InputStreamReader var1 = new InputStreamReader(var0)) {
               var2 = new MinecraftVersion(ChatDeserializer.a(var1));
            }
         }

         return var2;
      } catch (JsonParseException | IOException var8) {
         throw new IllegalStateException("Game version information is corrupt", var8);
      }
   }

   @Override
   public String b() {
      return this.c;
   }

   @Override
   public String c() {
      return this.d;
   }

   @Override
   public DataVersion d() {
      return this.f;
   }

   @Override
   public int e() {
      return this.g;
   }

   @Override
   public int a(EnumResourcePackType var0) {
      return var0 == EnumResourcePackType.b ? this.i : this.h;
   }

   @Override
   public Date f() {
      return this.j;
   }

   @Override
   public boolean g() {
      return this.e;
   }
}
