package net.minecraft.network.protocol.game;

import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.damagesource.CombatTracker;

public class ClientboundPlayerCombatEndPacket implements Packet<PacketListenerPlayOut> {
   private final int a;
   private final int b;

   public ClientboundPlayerCombatEndPacket(CombatTracker var0) {
      this(var0.j(), var0.f());
   }

   public ClientboundPlayerCombatEndPacket(int var0, int var1) {
      this.a = var0;
      this.b = var1;
   }

   public ClientboundPlayerCombatEndPacket(PacketDataSerializer var0) {
      this.b = var0.m();
      this.a = var0.readInt();
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.d(this.b);
      var0.writeInt(this.a);
   }

   public void a(PacketListenerPlayOut var0) {
      var0.a(this);
   }
}
