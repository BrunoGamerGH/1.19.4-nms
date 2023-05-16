package net.minecraft.network.protocol.game;

import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;

public class PacketPlayInDifficultyLock implements Packet<PacketListenerPlayIn> {
   private final boolean a;

   public PacketPlayInDifficultyLock(boolean var0) {
      this.a = var0;
   }

   public void a(PacketListenerPlayIn var0) {
      var0.a(this);
   }

   public PacketPlayInDifficultyLock(PacketDataSerializer var0) {
      this.a = var0.readBoolean();
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.writeBoolean(this.a);
   }

   public boolean a() {
      return this.a;
   }
}
