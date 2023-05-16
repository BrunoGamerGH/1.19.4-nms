package net.minecraft.network.protocol.game;

import com.google.common.collect.Lists;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.SectionPosition;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagLongArray;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.entity.TileEntityTypes;
import net.minecraft.world.level.chunk.Chunk;
import net.minecraft.world.level.chunk.ChunkSection;
import net.minecraft.world.level.levelgen.HeightMap;

public class ClientboundLevelChunkPacketData {
   private static final int a = 2097152;
   private final NBTTagCompound b;
   private final byte[] c;
   private final List<ClientboundLevelChunkPacketData.a> d;

   public ClientboundLevelChunkPacketData(Chunk var0) {
      this.b = new NBTTagCompound();

      for(Entry<HeightMap.Type, HeightMap> var2 : var0.e()) {
         if (var2.getKey().b()) {
            this.b.a(var2.getKey().a(), new NBTTagLongArray(var2.getValue().a()));
         }
      }

      this.c = new byte[a(var0)];
      a(new PacketDataSerializer(this.c()), var0);
      this.d = Lists.newArrayList();

      for(Entry<BlockPosition, TileEntity> var2 : var0.E().entrySet()) {
         this.d.add(ClientboundLevelChunkPacketData.a.a(var2.getValue()));
      }
   }

   public ClientboundLevelChunkPacketData(PacketDataSerializer var0, int var1, int var2) {
      this.b = var0.p();
      if (this.b == null) {
         throw new RuntimeException("Can't read heightmap in packet for [" + var1 + ", " + var2 + "]");
      } else {
         int var3 = var0.m();
         if (var3 > 2097152) {
            throw new RuntimeException("Chunk Packet trying to allocate too much memory on read.");
         } else {
            this.c = new byte[var3];
            var0.readBytes(this.c);
            this.d = var0.a(ClientboundLevelChunkPacketData.a::new);
         }
      }
   }

   public void a(PacketDataSerializer var0) {
      var0.a(this.b);
      var0.d(this.c.length);
      var0.writeBytes(this.c);
      var0.a(this.d, (var0x, var1x) -> var1x.a(var0x));
   }

   private static int a(Chunk var0) {
      int var1 = 0;

      for(ChunkSection var5 : var0.d()) {
         var1 += var5.k();
      }

      return var1;
   }

   private ByteBuf c() {
      ByteBuf var0 = Unpooled.wrappedBuffer(this.c);
      var0.writerIndex(0);
      return var0;
   }

   public static void a(PacketDataSerializer var0, Chunk var1) {
      for(ChunkSection var5 : var1.d()) {
         var5.c(var0);
      }
   }

   public Consumer<ClientboundLevelChunkPacketData.b> a(int var0, int var1) {
      return var2x -> this.a(var2x, var0, var1);
   }

   private void a(ClientboundLevelChunkPacketData.b var0, int var1, int var2) {
      int var3 = 16 * var1;
      int var4 = 16 * var2;
      BlockPosition.MutableBlockPosition var5 = new BlockPosition.MutableBlockPosition();

      for(ClientboundLevelChunkPacketData.a var7 : this.d) {
         int var8 = var3 + SectionPosition.b(var7.a >> 4);
         int var9 = var4 + SectionPosition.b(var7.a);
         var5.d(var8, var7.b, var9);
         var0.accept(var5, var7.c, var7.d);
      }
   }

   public PacketDataSerializer a() {
      return new PacketDataSerializer(Unpooled.wrappedBuffer(this.c));
   }

   public NBTTagCompound b() {
      return this.b;
   }

   static class a {
      final int a;
      final int b;
      final TileEntityTypes<?> c;
      @Nullable
      final NBTTagCompound d;

      private a(int var0, int var1, TileEntityTypes<?> var2, @Nullable NBTTagCompound var3) {
         this.a = var0;
         this.b = var1;
         this.c = var2;
         this.d = var3;
      }

      private a(PacketDataSerializer var0) {
         this.a = var0.readByte();
         this.b = var0.readShort();
         this.c = var0.a(BuiltInRegistries.l);
         this.d = var0.p();
      }

      void a(PacketDataSerializer var0) {
         var0.writeByte(this.a);
         var0.writeShort(this.b);
         var0.a(BuiltInRegistries.l, this.c);
         var0.a(this.d);
      }

      static ClientboundLevelChunkPacketData.a a(TileEntity var0) {
         NBTTagCompound var1 = var0.aq_();
         BlockPosition var2 = var0.p();
         int var3 = SectionPosition.b(var2.u()) << 4 | SectionPosition.b(var2.w());
         return new ClientboundLevelChunkPacketData.a(var3, var2.v(), var0.u(), var1.g() ? null : var1);
      }
   }

   @FunctionalInterface
   public interface b {
      void accept(BlockPosition var1, TileEntityTypes<?> var2, @Nullable NBTTagCompound var3);
   }
}
