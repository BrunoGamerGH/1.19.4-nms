package net.minecraft.server.network;

import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.EntityPlayer;

public interface ServerPlayerConnection {
   EntityPlayer f();

   void a(Packet<?> var1);
}
