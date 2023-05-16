package net.minecraft.network.protocol.game;

import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;

public class PacketPlayInItemName implements Packet<PacketListenerPlayIn> {
   private final String a;

   public PacketPlayInItemName(String var0) {
      this.a = var0;
   }

   public PacketPlayInItemName(PacketDataSerializer var0) {
      this.a = var0.s();
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.a(this.a);
   }

   public void a(PacketListenerPlayIn var0) {
      var0.a(this);
   }

   public String a() {
      return this.a;
   }
}
