package net.minecraft.network.protocol.game;

import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;

public class ClientboundPlayerCombatEnterPacket implements Packet<PacketListenerPlayOut> {
   public ClientboundPlayerCombatEnterPacket() {
   }

   public ClientboundPlayerCombatEnterPacket(PacketDataSerializer var0) {
   }

   @Override
   public void a(PacketDataSerializer var0) {
   }

   public void a(PacketListenerPlayOut var0) {
      var0.a(this);
   }
}
