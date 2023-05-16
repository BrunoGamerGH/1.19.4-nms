package net.minecraft.network;

import net.minecraft.network.chat.IChatBaseComponent;

public interface PacketListener {
   void a(IChatBaseComponent var1);

   boolean a();

   default boolean b() {
      return true;
   }
}
