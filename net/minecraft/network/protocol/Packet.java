package net.minecraft.network.protocol;

import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.PacketListener;

public interface Packet<T extends PacketListener> {
   void a(PacketDataSerializer var1);

   void a(T var1);

   default boolean b() {
      return false;
   }
}
