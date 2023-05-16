package net.minecraft.network.protocol.game;

import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.MinecraftKey;

public class PacketPlayInCustomPayload implements Packet<PacketListenerPlayIn> {
   private static final int b = 32767;
   public static final MinecraftKey a = new MinecraftKey("brand");
   public final MinecraftKey c;
   public final PacketDataSerializer d;

   public PacketPlayInCustomPayload(MinecraftKey var0, PacketDataSerializer var1) {
      this.c = var0;
      this.d = var1;
   }

   public PacketPlayInCustomPayload(PacketDataSerializer var0) {
      this.c = var0.t();
      int var1 = var0.readableBytes();
      if (var1 >= 0 && var1 <= 32767) {
         this.d = new PacketDataSerializer(var0.readBytes(var1));
      } else {
         throw new IllegalArgumentException("Payload may not be larger than 32767 bytes");
      }
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.a(this.c);
      var0.writeBytes(this.d);
   }

   public void a(PacketListenerPlayIn var0) {
      var0.a(this);
      this.d.release();
   }

   public MinecraftKey a() {
      return this.c;
   }

   public PacketDataSerializer c() {
      return this.d;
   }
}
