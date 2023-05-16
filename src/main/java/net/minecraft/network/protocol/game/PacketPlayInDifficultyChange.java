package net.minecraft.network.protocol.game;

import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.EnumDifficulty;

public class PacketPlayInDifficultyChange implements Packet<PacketListenerPlayIn> {
   private final EnumDifficulty a;

   public PacketPlayInDifficultyChange(EnumDifficulty var0) {
      this.a = var0;
   }

   public void a(PacketListenerPlayIn var0) {
      var0.a(this);
   }

   public PacketPlayInDifficultyChange(PacketDataSerializer var0) {
      this.a = EnumDifficulty.a(var0.readUnsignedByte());
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.writeByte(this.a.a());
   }

   public EnumDifficulty a() {
      return this.a;
   }
}
