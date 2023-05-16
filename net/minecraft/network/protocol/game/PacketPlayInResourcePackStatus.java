package net.minecraft.network.protocol.game;

import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;

public class PacketPlayInResourcePackStatus implements Packet<PacketListenerPlayIn> {
   public final PacketPlayInResourcePackStatus.EnumResourcePackStatus a;

   public PacketPlayInResourcePackStatus(PacketPlayInResourcePackStatus.EnumResourcePackStatus var0) {
      this.a = var0;
   }

   public PacketPlayInResourcePackStatus(PacketDataSerializer var0) {
      this.a = var0.b(PacketPlayInResourcePackStatus.EnumResourcePackStatus.class);
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.a(this.a);
   }

   public void a(PacketListenerPlayIn var0) {
      var0.a(this);
   }

   public PacketPlayInResourcePackStatus.EnumResourcePackStatus a() {
      return this.a;
   }

   public static enum EnumResourcePackStatus {
      a,
      b,
      c,
      d;
   }
}
