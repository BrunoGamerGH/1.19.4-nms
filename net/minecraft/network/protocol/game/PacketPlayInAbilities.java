package net.minecraft.network.protocol.game;

import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.player.PlayerAbilities;

public class PacketPlayInAbilities implements Packet<PacketListenerPlayIn> {
   private static final int a = 2;
   private final boolean b;

   public PacketPlayInAbilities(PlayerAbilities var0) {
      this.b = var0.b;
   }

   public PacketPlayInAbilities(PacketDataSerializer var0) {
      byte var1 = var0.readByte();
      this.b = (var1 & 2) != 0;
   }

   @Override
   public void a(PacketDataSerializer var0) {
      byte var1 = 0;
      if (this.b) {
         var1 = (byte)(var1 | 2);
      }

      var0.writeByte(var1);
   }

   public void a(PacketListenerPlayIn var0) {
      var0.a(this);
   }

   public boolean a() {
      return this.b;
   }
}
