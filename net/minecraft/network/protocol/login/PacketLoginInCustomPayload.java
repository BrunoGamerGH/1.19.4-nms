package net.minecraft.network.protocol.login;

import javax.annotation.Nullable;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;

public class PacketLoginInCustomPayload implements Packet<PacketLoginInListener> {
   private static final int a = 1048576;
   private final int b;
   @Nullable
   private final PacketDataSerializer c;

   public PacketLoginInCustomPayload(int var0, @Nullable PacketDataSerializer var1) {
      this.b = var0;
      this.c = var1;
   }

   public PacketLoginInCustomPayload(PacketDataSerializer var0) {
      this.b = var0.m();
      this.c = var0.c(var0x -> {
         int var1x = var0x.readableBytes();
         if (var1x >= 0 && var1x <= 1048576) {
            return new PacketDataSerializer(var0x.readBytes(var1x));
         } else {
            throw new IllegalArgumentException("Payload may not be larger than 1048576 bytes");
         }
      });
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.d(this.b);
      var0.a(this.c, (var0x, var1x) -> var0x.writeBytes(var1x.slice()));
   }

   public void a(PacketLoginInListener var0) {
      var0.a(this);
   }

   public int a() {
      return this.b;
   }

   @Nullable
   public PacketDataSerializer c() {
      return this.c;
   }
}
