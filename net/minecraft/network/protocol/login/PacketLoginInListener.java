package net.minecraft.network.protocol.login;

import net.minecraft.network.protocol.game.ServerPacketListener;

public interface PacketLoginInListener extends ServerPacketListener {
   void a(PacketLoginInStart var1);

   void a(PacketLoginInEncryptionBegin var1);

   void a(PacketLoginInCustomPayload var1);
}
