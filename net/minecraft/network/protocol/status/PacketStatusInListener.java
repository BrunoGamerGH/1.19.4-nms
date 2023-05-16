package net.minecraft.network.protocol.status;

import net.minecraft.network.protocol.game.ServerPacketListener;

public interface PacketStatusInListener extends ServerPacketListener {
   void a(PacketStatusInPing var1);

   void a(PacketStatusInStart var1);
}
