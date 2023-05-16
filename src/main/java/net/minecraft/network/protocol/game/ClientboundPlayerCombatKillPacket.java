package net.minecraft.network.protocol.game;

import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.damagesource.CombatTracker;

public class ClientboundPlayerCombatKillPacket implements Packet<PacketListenerPlayOut> {
   private final int a;
   private final int b;
   private final IChatBaseComponent c;

   public ClientboundPlayerCombatKillPacket(CombatTracker var0, IChatBaseComponent var1) {
      this(var0.h().af(), var0.j(), var1);
   }

   public ClientboundPlayerCombatKillPacket(int var0, int var1, IChatBaseComponent var2) {
      this.a = var0;
      this.b = var1;
      this.c = var2;
   }

   public ClientboundPlayerCombatKillPacket(PacketDataSerializer var0) {
      this.a = var0.m();
      this.b = var0.readInt();
      this.c = var0.l();
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.d(this.a);
      var0.writeInt(this.b);
      var0.a(this.c);
   }

   public void a(PacketListenerPlayOut var0) {
      var0.a(this);
   }

   @Override
   public boolean b() {
      return true;
   }

   public int a() {
      return this.b;
   }

   public int c() {
      return this.a;
   }

   public IChatBaseComponent d() {
      return this.c;
   }
}
