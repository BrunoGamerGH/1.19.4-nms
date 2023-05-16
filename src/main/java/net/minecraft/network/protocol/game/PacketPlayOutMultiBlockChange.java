package net.minecraft.network.protocol.game;

import it.unimi.dsi.fastutil.shorts.ShortIterator;
import it.unimi.dsi.fastutil.shorts.ShortSet;
import java.util.function.BiConsumer;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.SectionPosition;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.chunk.ChunkSection;

public class PacketPlayOutMultiBlockChange implements Packet<PacketListenerPlayOut> {
   private static final int a = 12;
   private final SectionPosition b;
   private final short[] c;
   private final IBlockData[] d;
   private final boolean e;

   public PacketPlayOutMultiBlockChange(SectionPosition sectionposition, ShortSet shortset, ChunkSection chunksection, boolean flag) {
      this.b = sectionposition;
      this.e = flag;
      int i = shortset.size();
      this.c = new short[i];
      this.d = new IBlockData[i];
      int j = 0;

      for(ShortIterator shortiterator = shortset.iterator(); shortiterator.hasNext(); ++j) {
         short short0 = shortiterator.next();
         this.c[j] = short0;
         this.d[j] = chunksection != null ? chunksection.a(SectionPosition.a(short0), SectionPosition.b(short0), SectionPosition.c(short0)) : Blocks.a.o();
      }
   }

   public PacketPlayOutMultiBlockChange(SectionPosition sectionposition, ShortSet shortset, IBlockData[] states, boolean flag) {
      this.b = sectionposition;
      this.e = flag;
      this.c = shortset.toShortArray();
      this.d = states;
   }

   public PacketPlayOutMultiBlockChange(PacketDataSerializer packetdataserializer) {
      this.b = SectionPosition.a(packetdataserializer.readLong());
      this.e = packetdataserializer.readBoolean();
      int i = packetdataserializer.m();
      this.c = new short[i];
      this.d = new IBlockData[i];

      for(int j = 0; j < i; ++j) {
         long k = packetdataserializer.n();
         this.c[j] = (short)((int)(k & 4095L));
         this.d[j] = Block.o.a((int)(k >>> 12));
      }
   }

   @Override
   public void a(PacketDataSerializer packetdataserializer) {
      packetdataserializer.writeLong(this.b.s());
      packetdataserializer.writeBoolean(this.e);
      packetdataserializer.d(this.c.length);

      for(int i = 0; i < this.c.length; ++i) {
         packetdataserializer.b((long)Block.i(this.d[i]) << 12 | (long)this.c[i]);
      }
   }

   public void a(PacketListenerPlayOut packetlistenerplayout) {
      packetlistenerplayout.a(this);
   }

   public void a(BiConsumer<BlockPosition, IBlockData> biconsumer) {
      BlockPosition.MutableBlockPosition blockposition_mutableblockposition = new BlockPosition.MutableBlockPosition();

      for(int i = 0; i < this.c.length; ++i) {
         short short0 = this.c[i];
         blockposition_mutableblockposition.d(this.b.d(short0), this.b.e(short0), this.b.f(short0));
         biconsumer.accept(blockposition_mutableblockposition, this.d[i]);
      }
   }

   public boolean a() {
      return this.e;
   }
}
