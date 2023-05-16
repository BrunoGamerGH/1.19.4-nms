package net.minecraft.network.protocol.game;

import java.time.Instant;
import javax.annotation.Nullable;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.chat.LastSeenMessages;
import net.minecraft.network.chat.MessageSignature;
import net.minecraft.network.protocol.Packet;

public record PacketPlayInChat(String message, Instant timeStamp, long salt, @Nullable MessageSignature signature, LastSeenMessages.b lastSeenMessages)
   implements Packet<PacketListenerPlayIn> {
   private final String a;
   private final Instant b;
   private final long c;
   @Nullable
   private final MessageSignature d;
   private final LastSeenMessages.b e;

   public PacketPlayInChat(PacketDataSerializer var0) {
      this(var0.e(256), var0.v(), var0.readLong(), var0.c(MessageSignature::a), new LastSeenMessages.b(var0));
   }

   public PacketPlayInChat(String var0, Instant var1, long var2, @Nullable MessageSignature var4, LastSeenMessages.b var5) {
      this.a = var0;
      this.b = var1;
      this.c = var2;
      this.d = var4;
      this.e = var5;
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.a(this.a, 256);
      var0.a(this.b);
      var0.writeLong(this.c);
      var0.a(this.d, MessageSignature::a);
      this.e.a(var0);
   }

   public void a(PacketListenerPlayIn var0) {
      var0.a(this);
   }

   public Instant c() {
      return this.b;
   }

   public long d() {
      return this.c;
   }

   @Nullable
   public MessageSignature e() {
      return this.d;
   }

   public LastSeenMessages.b f() {
      return this.e;
   }
}
