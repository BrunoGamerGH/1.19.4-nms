package net.minecraft.network.protocol.game;

import java.util.List;
import java.util.UUID;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;

public record ClientboundPlayerInfoRemovePacket(List<UUID> profileIds) implements Packet<PacketListenerPlayOut> {
   private final List<UUID> a;

   public ClientboundPlayerInfoRemovePacket(PacketDataSerializer var0) {
      this(var0.a(PacketDataSerializer::o));
   }

   public ClientboundPlayerInfoRemovePacket(List<UUID> var0) {
      this.a = var0;
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.a(this.a, PacketDataSerializer::a);
   }

   public void a(PacketListenerPlayOut var0) {
      var0.a(this);
   }
}
