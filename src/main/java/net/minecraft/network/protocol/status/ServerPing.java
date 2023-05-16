package net.minecraft.network.protocol.status;

import com.mojang.authlib.GameProfile;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import net.minecraft.SharedConstants;
import net.minecraft.WorldVersion;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.util.ExtraCodecs;

public record ServerPing(
   IChatBaseComponent description,
   Optional<ServerPing.ServerPingPlayerSample> players,
   Optional<ServerPing.ServerData> version,
   Optional<ServerPing.a> favicon,
   boolean enforcesSecureChat
) {
   private final IChatBaseComponent b;
   private final Optional<ServerPing.ServerPingPlayerSample> c;
   private final Optional<ServerPing.ServerData> d;
   private final Optional<ServerPing.a> e;
   private final boolean f;
   public static final Codec<ServerPing> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               ExtraCodecs.b.optionalFieldOf("description", CommonComponents.a).forGetter(ServerPing::a),
               ServerPing.ServerPingPlayerSample.a.optionalFieldOf("players").forGetter(ServerPing::b),
               ServerPing.ServerData.a.optionalFieldOf("version").forGetter(ServerPing::c),
               ServerPing.a.c.optionalFieldOf("favicon").forGetter(ServerPing::d),
               Codec.BOOL.optionalFieldOf("enforcesSecureChat", false).forGetter(ServerPing::e)
            )
            .apply(var0, ServerPing::new)
   );

   public ServerPing(
      IChatBaseComponent var0,
      Optional<ServerPing.ServerPingPlayerSample> var1,
      Optional<ServerPing.ServerData> var2,
      Optional<ServerPing.a> var3,
      boolean var4
   ) {
      this.b = var0;
      this.c = var1;
      this.d = var2;
      this.e = var3;
      this.f = var4;
   }

   public IChatBaseComponent a() {
      return this.b;
   }

   public Optional<ServerPing.ServerPingPlayerSample> b() {
      return this.c;
   }

   public Optional<ServerPing.ServerData> c() {
      return this.d;
   }

   public Optional<ServerPing.a> d() {
      return this.e;
   }

   public boolean e() {
      return this.f;
   }

   public static record ServerData(String name, int protocol) {
      private final String b;
      private final int c;
      public static final Codec<ServerPing.ServerData> a = RecordCodecBuilder.create(
         var0 -> var0.group(
                  Codec.STRING.fieldOf("name").forGetter(ServerPing.ServerData::b), Codec.INT.fieldOf("protocol").forGetter(ServerPing.ServerData::c)
               )
               .apply(var0, ServerPing.ServerData::new)
      );

      public ServerData(String var0, int var1) {
         this.b = var0;
         this.c = var1;
      }

      public static ServerPing.ServerData a() {
         WorldVersion var0 = SharedConstants.b();
         return new ServerPing.ServerData(var0.c(), var0.e());
      }
   }

   public static record ServerPingPlayerSample(int max, int online, List<GameProfile> sample) {
      private final int b;
      private final int c;
      private final List<GameProfile> d;
      private static final Codec<GameProfile> e = RecordCodecBuilder.create(
         var0 -> var0.group(UUIDUtil.b.fieldOf("id").forGetter(GameProfile::getId), Codec.STRING.fieldOf("name").forGetter(GameProfile::getName))
               .apply(var0, GameProfile::new)
      );
      public static final Codec<ServerPing.ServerPingPlayerSample> a = RecordCodecBuilder.create(
         var0 -> var0.group(
                  Codec.INT.fieldOf("max").forGetter(ServerPing.ServerPingPlayerSample::a),
                  Codec.INT.fieldOf("online").forGetter(ServerPing.ServerPingPlayerSample::b),
                  e.listOf().optionalFieldOf("sample", List.of()).forGetter(ServerPing.ServerPingPlayerSample::c)
               )
               .apply(var0, ServerPing.ServerPingPlayerSample::new)
      );

      public ServerPingPlayerSample(int var0, int var1, List<GameProfile> var2) {
         this.b = var0;
         this.c = var1;
         this.d = var2;
      }

      public int a() {
         return this.b;
      }

      public int b() {
         return this.c;
      }

      public List<GameProfile> c() {
         return this.d;
      }
   }

   public static record a(byte[] iconBytes) {
      private final byte[] d;
      public static final int a = 64;
      public static final int b = 64;
      private static final String e = "data:image/png;base64,";
      public static final Codec<ServerPing.a> c = Codec.STRING.comapFlatMap(var0 -> {
         if (!var0.startsWith("data:image/png;base64,")) {
            return DataResult.error(() -> "Unknown format");
         } else {
            try {
               String var1 = var0.substring("data:image/png;base64,".length()).replaceAll("\n", "");
               byte[] var2 = Base64.getDecoder().decode(var1.getBytes(StandardCharsets.UTF_8));
               return DataResult.success(new ServerPing.a(var2));
            } catch (IllegalArgumentException var3) {
               return DataResult.error(() -> "Malformed base64 server icon");
            }
         }
      }, var0 -> "data:image/png;base64," + new String(Base64.getEncoder().encode(var0.d), StandardCharsets.UTF_8));

      public a(byte[] var0) {
         this.d = var0;
      }

      public byte[] a() {
         return this.d;
      }
   }
}
