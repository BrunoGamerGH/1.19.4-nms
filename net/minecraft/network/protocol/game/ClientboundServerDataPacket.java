package net.minecraft.network.protocol.game;

import java.util.Optional;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.protocol.Packet;

public class ClientboundServerDataPacket implements Packet<PacketListenerPlayOut> {
   private final IChatBaseComponent a;
   private final Optional<byte[]> b;
   private final boolean c;

   public ClientboundServerDataPacket(IChatBaseComponent var0, Optional<byte[]> var1, boolean var2) {
      this.a = var0;
      this.b = var1;
      this.c = var2;
   }

   public ClientboundServerDataPacket(PacketDataSerializer var0) {
      this.a = var0.l();
      this.b = var0.b(PacketDataSerializer::b);
      this.c = var0.readBoolean();
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.a(this.a);
      var0.a(this.b, PacketDataSerializer::a);
      var0.writeBoolean(this.c);
   }

   public void a(PacketListenerPlayOut var0) {
      var0.a(this);
   }

   public IChatBaseComponent a() {
      return this.a;
   }

   public Optional<byte[]> c() {
      return this.b;
   }

   public boolean d() {
      return this.c;
   }
}
