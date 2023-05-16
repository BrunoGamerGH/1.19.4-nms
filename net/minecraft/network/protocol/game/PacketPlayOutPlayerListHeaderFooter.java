package net.minecraft.network.protocol.game;

import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.protocol.Packet;

public class PacketPlayOutPlayerListHeaderFooter implements Packet<PacketListenerPlayOut> {
   public final IChatBaseComponent a;
   public final IChatBaseComponent b;

   public PacketPlayOutPlayerListHeaderFooter(IChatBaseComponent var0, IChatBaseComponent var1) {
      this.a = var0;
      this.b = var1;
   }

   public PacketPlayOutPlayerListHeaderFooter(PacketDataSerializer var0) {
      this.a = var0.l();
      this.b = var0.l();
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.a(this.a);
      var0.a(this.b);
   }

   public void a(PacketListenerPlayOut var0) {
      var0.a(this);
   }

   public IChatBaseComponent a() {
      return this.a;
   }

   public IChatBaseComponent c() {
      return this.b;
   }
}
