package net.minecraft.server.network;

import net.minecraft.network.NetworkManager;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.protocol.handshake.PacketHandshakingInListener;
import net.minecraft.network.protocol.handshake.PacketHandshakingInSetProtocol;
import net.minecraft.server.MinecraftServer;

public class MemoryServerHandshakePacketListenerImpl implements PacketHandshakingInListener {
   private final MinecraftServer a;
   private final NetworkManager b;

   public MemoryServerHandshakePacketListenerImpl(MinecraftServer var0, NetworkManager var1) {
      this.a = var0;
      this.b = var1;
   }

   @Override
   public void a(PacketHandshakingInSetProtocol var0) {
      this.b.a(var0.a());
      this.b.a(new LoginListener(this.a, this.b));
   }

   @Override
   public void a(IChatBaseComponent var0) {
   }

   @Override
   public boolean a() {
      return this.b.h();
   }
}
