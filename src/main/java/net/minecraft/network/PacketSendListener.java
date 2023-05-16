package net.minecraft.network;

import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.network.protocol.Packet;

public interface PacketSendListener {
   static PacketSendListener a(final Runnable var0) {
      return new PacketSendListener() {
         @Override
         public void a() {
            var0.run();
         }

         @Nullable
         @Override
         public Packet<?> b() {
            var0.run();
            return null;
         }
      };
   }

   static PacketSendListener a(final Supplier<Packet<?>> var0) {
      return new PacketSendListener() {
         @Nullable
         @Override
         public Packet<?> b() {
            return var0.get();
         }
      };
   }

   default void a() {
   }

   @Nullable
   default Packet<?> b() {
      return null;
   }
}
