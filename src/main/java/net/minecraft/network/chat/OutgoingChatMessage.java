package net.minecraft.network.chat;

import net.minecraft.server.level.EntityPlayer;

public interface OutgoingChatMessage {
   IChatBaseComponent a();

   void a(EntityPlayer var1, boolean var2, ChatMessageType.a var3);

   static OutgoingChatMessage a(PlayerChatMessage var0) {
      return (OutgoingChatMessage)(var0.g() ? new OutgoingChatMessage.a(var0.c()) : new OutgoingChatMessage.b(var0));
   }

   public static record a(IChatBaseComponent content) implements OutgoingChatMessage {
      private final IChatBaseComponent a;

      public a(IChatBaseComponent var0) {
         this.a = var0;
      }

      @Override
      public void a(EntityPlayer var0, boolean var1, ChatMessageType.a var2) {
         var0.b.a(this.a, var2);
      }
   }

   public static record b(PlayerChatMessage message) implements OutgoingChatMessage {
      private final PlayerChatMessage a;

      public b(PlayerChatMessage var0) {
         this.a = var0;
      }

      @Override
      public IChatBaseComponent a() {
         return this.a.c();
      }

      @Override
      public void a(EntityPlayer var0, boolean var1, ChatMessageType.a var2) {
         PlayerChatMessage var3 = this.a.a(var1);
         if (!var3.i()) {
            var0.b.a(var3, var2);
         }
      }

      public PlayerChatMessage b() {
         return this.a;
      }
   }
}
