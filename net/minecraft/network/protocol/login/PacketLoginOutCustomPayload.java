package net.minecraft.network.protocol.login;

import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.MinecraftKey;

public class PacketLoginOutCustomPayload implements Packet<PacketLoginOutListener> {
   private static final int a = 1048576;
   private final int b;
   private final MinecraftKey c;
   private final PacketDataSerializer d;

   public PacketLoginOutCustomPayload(int var0, MinecraftKey var1, PacketDataSerializer var2) {
      this.b = var0;
      this.c = var1;
      this.d = var2;
   }

   public PacketLoginOutCustomPayload(PacketDataSerializer var0) {
      this.b = var0.m();
      this.c = var0.t();
      int var1 = var0.readableBytes();
      if (var1 >= 0 && var1 <= 1048576) {
         this.d = new PacketDataSerializer(var0.readBytes(var1));
      } else {
         throw new IllegalArgumentException("Payload may not be larger than 1048576 bytes");
      }
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.d(this.b);
      var0.a(this.c);
      var0.writeBytes(this.d.copy());
   }

   public void a(PacketLoginOutListener var0) {
      var0.a(this);
   }

   public int a() {
      return this.b;
   }

   public MinecraftKey c() {
      return this.c;
   }

   public PacketDataSerializer d() {
      return this.d;
   }
}
