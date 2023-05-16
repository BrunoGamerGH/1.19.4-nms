package net.minecraft.network.protocol.game;

import java.util.HashSet;
import java.util.Set;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.MinecraftKey;

public record ClientboundUpdateEnabledFeaturesPacket(Set<MinecraftKey> features) implements Packet<PacketListenerPlayOut> {
   private final Set<MinecraftKey> a;

   public ClientboundUpdateEnabledFeaturesPacket(PacketDataSerializer var0) {
      this(var0.a(HashSet::new, PacketDataSerializer::t));
   }

   public ClientboundUpdateEnabledFeaturesPacket(Set<MinecraftKey> var0) {
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
