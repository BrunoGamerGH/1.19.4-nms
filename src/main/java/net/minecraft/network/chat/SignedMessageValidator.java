package net.minecraft.network.chat;

import javax.annotation.Nullable;
import net.minecraft.util.SignatureValidator;

@FunctionalInterface
public interface SignedMessageValidator {
   SignedMessageValidator a = var0 -> !var0.h();
   SignedMessageValidator b = var0 -> false;

   boolean updateAndValidate(PlayerChatMessage var1);

   public static class a implements SignedMessageValidator {
      private final SignatureValidator c;
      @Nullable
      private PlayerChatMessage d;
      private boolean e = true;

      public a(SignatureValidator var0) {
         this.c = var0;
      }

      private boolean a(PlayerChatMessage var0) {
         if (var0.equals(this.d)) {
            return true;
         } else {
            return this.d == null || var0.j().a(this.d.j());
         }
      }

      @Override
      public boolean updateAndValidate(PlayerChatMessage var0) {
         this.e = this.e && var0.a(this.c) && this.a(var0);
         if (!this.e) {
            return false;
         } else {
            this.d = var0;
            return true;
         }
      }
   }
}
