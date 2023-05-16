package net.minecraft.network.protocol.game;

import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;

public class PacketPlayOutUpdateTime implements Packet<PacketListenerPlayOut> {
   private final long a;
   private final long b;

   public PacketPlayOutUpdateTime(long var0, long var2, boolean var4) {
      this.a = var0;
      long var5 = var2;
      if (!var4) {
         var5 = -var2;
         if (var5 == 0L) {
            var5 = -1L;
         }
      }

      this.b = var5;
   }

   public PacketPlayOutUpdateTime(PacketDataSerializer var0) {
      this.a = var0.readLong();
      this.b = var0.readLong();
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.writeLong(this.a);
      var0.writeLong(this.b);
   }

   public void a(PacketListenerPlayOut var0) {
      var0.a(this);
   }

   public long a() {
      return this.a;
   }

   public long c() {
      return this.b;
   }
}
