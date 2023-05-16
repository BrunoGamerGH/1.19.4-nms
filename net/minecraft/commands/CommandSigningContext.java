package net.minecraft.commands;

import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.network.chat.PlayerChatMessage;

public interface CommandSigningContext {
   CommandSigningContext a = new CommandSigningContext() {
      @Nullable
      @Override
      public PlayerChatMessage a(String var0) {
         return null;
      }
   };

   @Nullable
   PlayerChatMessage a(String var1);

   public static record a(Map<String, PlayerChatMessage> arguments) implements CommandSigningContext {
      private final Map<String, PlayerChatMessage> b;

      public a(Map<String, PlayerChatMessage> var0) {
         this.b = var0;
      }

      @Nullable
      @Override
      public PlayerChatMessage a(String var0) {
         return this.b.get(var0);
      }

      public Map<String, PlayerChatMessage> a() {
         return this.b;
      }
   }
}
