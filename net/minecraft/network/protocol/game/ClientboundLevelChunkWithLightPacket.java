package net.minecraft.network.protocol.game;

import java.util.BitSet;
import javax.annotation.Nullable;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.chunk.Chunk;
import net.minecraft.world.level.lighting.LightEngine;

public class ClientboundLevelChunkWithLightPacket implements Packet<PacketListenerPlayOut> {
   private final int a;
   private final int b;
   private final ClientboundLevelChunkPacketData c;
   private final ClientboundLightUpdatePacketData d;

   public ClientboundLevelChunkWithLightPacket(Chunk var0, LightEngine var1, @Nullable BitSet var2, @Nullable BitSet var3, boolean var4) {
      ChunkCoordIntPair var5 = var0.f();
      this.a = var5.e;
      this.b = var5.f;
      this.c = new ClientboundLevelChunkPacketData(var0);
      this.d = new ClientboundLightUpdatePacketData(var5, var1, var2, var3, var4);
   }

   public ClientboundLevelChunkWithLightPacket(PacketDataSerializer var0) {
      this.a = var0.readInt();
      this.b = var0.readInt();
      this.c = new ClientboundLevelChunkPacketData(var0, this.a, this.b);
      this.d = new ClientboundLightUpdatePacketData(var0, this.a, this.b);
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.writeInt(this.a);
      var0.writeInt(this.b);
      this.c.a(var0);
      this.d.a(var0);
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

   public ClientboundLevelChunkPacketData d() {
      return this.c;
   }

   public ClientboundLightUpdatePacketData e() {
      return this.d;
   }
}
