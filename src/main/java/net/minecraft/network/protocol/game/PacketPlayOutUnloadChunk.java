package net.minecraft.network.protocol.game;

import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;

public class PacketPlayOutUnloadChunk implements Packet<PacketListenerPlayOut> {
   private final int a;
   private final int b;

   public PacketPlayOutUnloadChunk(int var0, int var1) {
      this.a = var0;
      this.b = var1;
   }

   public PacketPlayOutUnloadChunk(PacketDataSerializer var0) {
      this.a = var0.readInt();
      this.b = var0.readInt();
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.writeInt(this.a);
      var0.writeInt(this.b);
   }

   public void a(PacketListenerPlayOut var0) {
      var0.a(this);
   }

   public int a() {
      return this.a;
   }

   public int c() {
      return this.b;
   }
}
