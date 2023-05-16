package net.minecraft.network.protocol.game;

import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;

public class PacketPlayInClientCommand implements Packet<PacketListenerPlayIn> {
   private final PacketPlayInClientCommand.EnumClientCommand a;

   public PacketPlayInClientCommand(PacketPlayInClientCommand.EnumClientCommand var0) {
      this.a = var0;
   }

   public PacketPlayInClientCommand(PacketDataSerializer var0) {
      this.a = var0.b(PacketPlayInClientCommand.EnumClientCommand.class);
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.a(this.a);
   }

   public void a(PacketListenerPlayIn var0) {
      var0.a(this);
   }

   public PacketPlayInClientCommand.EnumClientCommand a() {
      return this.a;
   }

   public static enum EnumClientCommand {
      a,
      b;
   }
}
