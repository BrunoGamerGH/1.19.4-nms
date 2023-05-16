package net.minecraft.network.protocol.game;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.util.List;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.chunk.Chunk;
import net.minecraft.world.level.chunk.ChunkSection;

public record ClientboundChunksBiomesPacket(List<ClientboundChunksBiomesPacket.a> chunkBiomeData) implements Packet<PacketListenerPlayOut> {
   private final List<ClientboundChunksBiomesPacket.a> a;
   private static final int b = 2097152;

   public ClientboundChunksBiomesPacket(PacketDataSerializer var0) {
      this(var0.a(ClientboundChunksBiomesPacket.a::new));
   }

   public ClientboundChunksBiomesPacket(List<ClientboundChunksBiomesPacket.a> var0) {
      this.a = var0;
   }

   public static ClientboundChunksBiomesPacket a(List<Chunk> var0) {
      return new ClientboundChunksBiomesPacket(var0.stream().map(ClientboundChunksBiomesPacket.a::new).toList());
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.a(this.a, (var0x, var1x) -> var1x.a(var0x));
   }

   public void a(PacketListenerPlayOut var0) {
      var0.a(this);
   }

   public static record a(ChunkCoordIntPair pos, byte[] buffer) {
      private final ChunkCoordIntPair a;
      private final byte[] b;

      public a(Chunk var0) {
         this(var0.f(), new byte[a(var0)]);
         a(new PacketDataSerializer(this.d()), var0);
      }

      public a(PacketDataSerializer var0) {
         this(var0.g(), var0.b(2097152));
      }

      public a(ChunkCoordIntPair var0, byte[] var1) {
         this.a = var0;
         this.b = var1;
      }

      private static int a(Chunk var0) {
         int var1 = 0;

         for(ChunkSection var5 : var0.d()) {
            var1 += var5.j().c();
         }

         return var1;
      }

      public PacketDataSerializer a() {
         return new PacketDataSerializer(Unpooled.wrappedBuffer(this.b));
      }

      private ByteBuf d() {
         ByteBuf var0 = Unpooled.wrappedBuffer(this.b);
         var0.writerIndex(0);
         return var0;
      }

      public static void a(PacketDataSerializer var0, Chunk var1) {
         for(ChunkSection var5 : var1.d()) {
            var5.j().b(var0);
         }
      }

      public void a(PacketDataSerializer var0) {
         var0.a(this.a);
         var0.a(this.b);
      }

      public ChunkCoordIntPair b() {
         return this.a;
      }

      public byte[] c() {
         return this.b;
      }
   }
}
