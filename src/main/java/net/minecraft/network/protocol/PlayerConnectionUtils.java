package net.minecraft.network.protocol;

import com.mojang.logging.LogUtils;
import net.minecraft.network.PacketListener;
import net.minecraft.server.CancelledPacketHandleException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.server.network.PlayerConnection;
import net.minecraft.util.thread.IAsyncTaskHandler;
import org.slf4j.Logger;

public class PlayerConnectionUtils {
   private static final Logger a = LogUtils.getLogger();

   public static <T extends PacketListener> void a(Packet<T> packet, T t0, WorldServer worldserver) throws CancelledPacketHandleException {
      a(packet, t0, worldserver.n());
   }

   public static <T extends PacketListener> void a(Packet<T> packet, T t0, IAsyncTaskHandler<?> iasynctaskhandler) throws CancelledPacketHandleException {
      if (!iasynctaskhandler.bn()) {
         iasynctaskhandler.c(() -> {
            if (!MinecraftServer.getServer().hasStopped() && (!(t0 instanceof PlayerConnection) || !((PlayerConnection)t0).processedDisconnect)) {
               if (t0.a()) {
                  try {
                     packet.a(t0);
                  } catch (Exception var3) {
                     if (t0.b()) {
                        throw var3;
                     }

                     a.error("Failed to handle packet {}, suppressing error", packet, var3);
                  }
               } else {
                  a.debug("Ignoring packet due to disconnection: {}", packet);
               }
            }
         });
         throw CancelledPacketHandleException.a;
      } else if (MinecraftServer.getServer().hasStopped() || t0 instanceof PlayerConnection && ((PlayerConnection)t0).processedDisconnect) {
         throw CancelledPacketHandleException.a;
      }
   }
}
