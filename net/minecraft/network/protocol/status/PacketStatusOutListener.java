package net.minecraft.network.protocol.status;

import net.minecraft.network.PacketListener;

public interface PacketStatusOutListener extends PacketListener {
   void a(PacketStatusOutServerInfo var1);

   void a(PacketStatusOutPong var1);
}
