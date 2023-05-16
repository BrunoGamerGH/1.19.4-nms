package net.minecraft.network.protocol.game;

import java.util.BitSet;
import javax.annotation.Nullable;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.lighting.LightEngine;

public class PacketPlayOutLightUpdate implements Packet<PacketListenerPlayOut> {
   private final int a;
   private final int b;
   private final ClientboundLightUpdatePacketData c;

   public PacketPlayOutLightUpdate(ChunkCoordIntPair var0, LightEngine var1, @Nullable BitSet var2, @Nullable BitSet var3, boolean var4) {
      this.a = var0.e;
      this.b = var0.f;
      this.c = new ClientboundLightUpdatePacketData(var0, var1, var2, var3, var4);
   }

   public PacketPlayOutLightUpdate(PacketDataSerializer var0) {
      this.a = var0.m();
      this.b = var0.m();
      this.c = new ClientboundLightUpdatePacketData(var0, this.a, this.b);
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.d(this.a);
      var0.d(this.b);
      this.c.a(var0);
   }

   public void a(PacketListenerPlayOut var0) {
      var0.a(this);
   }

   public int a() {
      return this.a;
   }

   public int c() {
      return this.b;
   }

   public ClientboundLightUpdatePacketData d() {
      return this.c;
   }
}
