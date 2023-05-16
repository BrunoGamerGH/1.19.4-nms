package net.minecraft.network.protocol.game;

import java.time.Instant;
import net.minecraft.commands.arguments.ArgumentSignatures;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.chat.LastSeenMessages;
import net.minecraft.network.protocol.Packet;

public record ServerboundChatCommandPacket(
   String command, Instant timeStamp, long salt, ArgumentSignatures argumentSignatures, LastSeenMessages.b lastSeenMessages
) implements Packet<PacketListenerPlayIn> {
   private final String a;
   private final Instant b;
   private final long c;
   private final ArgumentSignatures d;
   private final LastSeenMessages.b e;

   public ServerboundChatCommandPacket(PacketDataSerializer var0) {
      this(var0.e(256), var0.v(), var0.readLong(), new ArgumentSignatures(var0), new LastSeenMessages.b(var0));
   }

   public ServerboundChatCommandPacket(String var0, Instant var1, long var2, ArgumentSignatures var4, LastSeenMessages.b var5) {
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
      this.d.a(var0);
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

   public ArgumentSignatures e() {
      return this.d;
   }

   public LastSeenMessages.b f() {
      return this.e;
   }
}
