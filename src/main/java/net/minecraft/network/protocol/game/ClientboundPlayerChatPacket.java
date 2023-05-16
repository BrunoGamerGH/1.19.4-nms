package net.minecraft.network.protocol.game;

import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.chat.ChatMessageType;
import net.minecraft.network.chat.FilterMask;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.chat.MessageSignature;
import net.minecraft.network.chat.SignedMessageBody;
import net.minecraft.network.protocol.Packet;

public record ClientboundPlayerChatPacket(
   UUID sender,
   int index,
   @Nullable MessageSignature signature,
   SignedMessageBody.a body,
   @Nullable IChatBaseComponent unsignedContent,
   FilterMask filterMask,
   ChatMessageType.b chatType
) implements Packet<PacketListenerPlayOut> {
   private final UUID a;
   private final int b;
   @Nullable
   private final MessageSignature c;
   private final SignedMessageBody.a d;
   @Nullable
   private final IChatBaseComponent e;
   private final FilterMask f;
   private final ChatMessageType.b g;

   public ClientboundPlayerChatPacket(PacketDataSerializer var0) {
      this(
         var0.o(),
         var0.m(),
         var0.c(MessageSignature::a),
         new SignedMessageBody.a(var0),
         var0.c(PacketDataSerializer::l),
         FilterMask.a(var0),
         new ChatMessageType.b(var0)
      );
   }

   public ClientboundPlayerChatPacket(
      UUID var0,
      int var1,
      @Nullable MessageSignature var2,
      SignedMessageBody.a var3,
      @Nullable IChatBaseComponent var4,
      FilterMask var5,
      ChatMessageType.b var6
   ) {
      this.a = var0;
      this.b = var1;
      this.c = var2;
      this.d = var3;
      this.e = var4;
      this.f = var5;
      this.g = var6;
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.a(this.a);
      var0.d(this.b);
      var0.a(this.c, MessageSignature::a);
      this.d.a(var0);
      var0.a(this.e, PacketDataSerializer::a);
      FilterMask.a(var0, this.f);
      this.g.a(var0);
   }

   public void a(PacketListenerPlayOut var0) {
      var0.a(this);
   }

   @Override
   public boolean b() {
      return true;
   }

   public int c() {
      return this.b;
   }

   @Nullable
   public MessageSignature d() {
      return this.c;
   }

   public SignedMessageBody.a e() {
      return this.d;
   }

   @Nullable
   public IChatBaseComponent f() {
      return this.e;
   }

   public FilterMask g() {
      return this.f;
   }

   public ChatMessageType.b h() {
      return this.g;
   }
}
