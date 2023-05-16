package net.minecraft.network.protocol.game;

import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.EnumDifficulty;

public class PacketPlayOutServerDifficulty implements Packet<PacketListenerPlayOut> {
   private final EnumDifficulty a;
   private final boolean b;

   public PacketPlayOutServerDifficulty(EnumDifficulty var0, boolean var1) {
      this.a = var0;
      this.b = var1;
   }

   public PacketPlayOutServerDifficulty(PacketDataSerializer var0) {
      this.a = EnumDifficulty.a(var0.readUnsignedByte());
      this.b = var0.readBoolean();
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.writeByte(this.a.a());
      var0.writeBoolean(this.b);
   }

   public void a(PacketListenerPlayOut var0) {
      var0.a(this);
   }

   public boolean a() {
      return this.b;
   }

   public EnumDifficulty c() {
      return this.a;
   }
}
