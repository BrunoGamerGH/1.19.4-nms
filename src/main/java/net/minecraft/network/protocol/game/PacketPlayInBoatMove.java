package net.minecraft.network.protocol.game;

import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;

public class PacketPlayInBoatMove implements Packet<PacketListenerPlayIn> {
   private final boolean a;
   private final boolean b;

   public PacketPlayInBoatMove(boolean var0, boolean var1) {
      this.a = var0;
      this.b = var1;
   }

   public PacketPlayInBoatMove(PacketDataSerializer var0) {
      this.a = var0.readBoolean();
      this.b = var0.readBoolean();
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.writeBoolean(this.a);
      var0.writeBoolean(this.b);
   }

   public void a(PacketListenerPlayIn var0) {
      var0.a(this);
   }

   public boolean a() {
      return this.a;
   }

   public boolean c() {
      return this.b;
   }
}
