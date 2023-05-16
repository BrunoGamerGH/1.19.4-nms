package net.minecraft.network.protocol.login;

import com.mojang.authlib.GameProfile;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;

public class PacketLoginOutSuccess implements Packet<PacketLoginOutListener> {
   private final GameProfile a;

   public PacketLoginOutSuccess(GameProfile var0) {
      this.a = var0;
   }

   public PacketLoginOutSuccess(PacketDataSerializer var0) {
      this.a = var0.z();
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.a(this.a);
   }

   public void a(PacketLoginOutListener var0) {
      var0.a(this);
   }

   public GameProfile a() {
      return this.a;
   }
}
