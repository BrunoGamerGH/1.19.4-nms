package net.minecraft.network.protocol.game;

import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.EnumHand;

public class PacketPlayOutOpenBook implements Packet<PacketListenerPlayOut> {
   private final EnumHand a;

   public PacketPlayOutOpenBook(EnumHand var0) {
      this.a = var0;
   }

   public PacketPlayOutOpenBook(PacketDataSerializer var0) {
      this.a = var0.b(EnumHand.class);
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.a(this.a);
   }

   public void a(PacketListenerPlayOut var0) {
      var0.a(this);
   }

   public EnumHand a() {
      return this.a;
   }
}
