package net.minecraft.network.protocol;

import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.PacketListener;

public abstract class BundlePacket<T extends PacketListener> implements Packet<T> {
   private final Iterable<Packet<T>> a;

   protected BundlePacket(Iterable<Packet<T>> var0) {
      this.a = var0;
   }

   public final Iterable<Packet<T>> a() {
      return this.a;
   }

   @Override
   public final void a(PacketDataSerializer var0) {
   }
}
