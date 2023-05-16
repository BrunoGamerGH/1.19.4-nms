package net.minecraft.network.chat;

import com.mojang.authlib.GameProfile;
import java.time.Duration;
import java.util.UUID;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.util.SignatureValidator;
import net.minecraft.world.entity.player.ProfilePublicKey;

public record RemoteChatSession(UUID sessionId, ProfilePublicKey profilePublicKey) {
   private final UUID a;
   private final ProfilePublicKey b;

   public RemoteChatSession(UUID var0, ProfilePublicKey var1) {
      this.a = var0;
      this.b = var1;
   }

   public SignedMessageValidator a() {
      return new SignedMessageValidator.a(this.b.a());
   }

   public SignedMessageChain.b a(UUID var0) {
      return new SignedMessageChain(var0, this.a).a(this.b);
   }

   public RemoteChatSession.a b() {
      return new RemoteChatSession.a(this.a, this.b.b());
   }

   public UUID c() {
      return this.a;
   }

   public ProfilePublicKey d() {
      return this.b;
   }

   public static record a(UUID sessionId, ProfilePublicKey.a profilePublicKey) {
      private final UUID a;
      private final ProfilePublicKey.a b;

      public a(UUID var0, ProfilePublicKey.a var1) {
         this.a = var0;
         this.b = var1;
      }

      public static RemoteChatSession.a a(PacketDataSerializer var0) {
         return new RemoteChatSession.a(var0.o(), new ProfilePublicKey.a(var0));
      }

      public static void a(PacketDataSerializer var0, RemoteChatSession.a var1) {
         var0.a(var1.a);
         var1.b.a(var0);
      }

      public RemoteChatSession a(GameProfile var0, SignatureValidator var1, Duration var2) throws ProfilePublicKey.b {
         return new RemoteChatSession(this.a, ProfilePublicKey.a(var1, var0.getId(), this.b, var2));
      }
   }
}
