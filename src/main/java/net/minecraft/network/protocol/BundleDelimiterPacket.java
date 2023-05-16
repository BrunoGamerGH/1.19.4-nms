package net.minecraft.network.protocol;

import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.PacketListener;

public class BundleDelimiterPacket<T extends PacketListener> implements Packet<T> {
   @Override
   public final void a(PacketDataSerializer var0) {
   }

   @Override
   public final void a(T var0) {
      throw new AssertionError("This packet should be handled by pipeline");
   }
}
