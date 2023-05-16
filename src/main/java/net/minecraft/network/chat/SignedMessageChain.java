package net.minecraft.network.chat;

import com.mojang.logging.LogUtils;
import java.time.Instant;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.util.SignatureValidator;
import net.minecraft.util.Signer;
import net.minecraft.world.entity.player.ProfilePublicKey;
import org.slf4j.Logger;

public class SignedMessageChain {
   private static final Logger a = LogUtils.getLogger();
   @Nullable
   private SignedMessageLink b;

   public SignedMessageChain(UUID var0, UUID var1) {
      this.b = SignedMessageLink.a(var0, var1);
   }

   public SignedMessageChain.c a(Signer var0) {
      return var1x -> {
         SignedMessageLink var2 = this.a();
         return var2 == null ? null : new MessageSignature(var0.sign(var2x -> PlayerChatMessage.a(var2x, var2, var1x)));
      };
   }

   public SignedMessageChain.b a(ProfilePublicKey var0) {
      SignatureValidator var1 = var0.a();
      return (var2x, var3) -> {
         SignedMessageLink var4 = this.a();
         if (var4 == null) {
            throw new SignedMessageChain.a(IChatBaseComponent.c("chat.disabled.chain_broken"), false);
         } else if (var0.b().a()) {
            throw new SignedMessageChain.a(IChatBaseComponent.c("chat.disabled.expiredProfileKey"), false);
         } else {
            PlayerChatMessage var5 = new PlayerChatMessage(var4, var2x, var3, null, FilterMask.c);
            if (!var5.a(var1)) {
               throw new SignedMessageChain.a(IChatBaseComponent.c("multiplayer.disconnect.unsigned_chat"), true);
            } else {
               if (var5.a(Instant.now())) {
                  a.warn("Received expired chat: '{}'. Is the client/server system time unsynchronized?", var3.a());
               }

               return var5;
            }
         }
      };
   }

   @Nullable
   private SignedMessageLink a() {
      SignedMessageLink var0 = this.b;
      if (var0 != null) {
         this.b = var0.a();
      }

      return var0;
   }

   public static class a extends ThrowingComponent {
      private final boolean a;

      public a(IChatBaseComponent var0, boolean var1) {
         super(var0);
         this.a = var1;
      }

      public boolean a() {
         return this.a;
      }
   }

   @FunctionalInterface
   public interface b {
      SignedMessageChain.b a = (var0, var1) -> {
         throw new SignedMessageChain.a(IChatBaseComponent.c("chat.disabled.missingProfileKey"), false);
      };

      static SignedMessageChain.b unsigned(UUID var0) {
         return (var1, var2) -> PlayerChatMessage.a(var0, var2.a());
      }

      PlayerChatMessage unpack(@Nullable MessageSignature var1, SignedMessageBody var2) throws SignedMessageChain.a;
   }

   @FunctionalInterface
   public interface c {
      SignedMessageChain.c a = var0 -> null;

      @Nullable
      MessageSignature pack(SignedMessageBody var1);
   }
}
