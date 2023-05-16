package net.minecraft.network;

import com.mojang.logging.LogUtils;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.protocol.EnumProtocolDirection;
import net.minecraft.network.protocol.game.PacketPlayOutKickDisconnect;
import org.slf4j.Logger;

public class NetworkManagerServer extends NetworkManager {
   private static final Logger i = LogUtils.getLogger();
   private static final IChatBaseComponent j = IChatBaseComponent.c("disconnect.exceeded_packet_rate");
   private final int k;

   public NetworkManagerServer(int var0) {
      super(EnumProtocolDirection.a);
      this.k = var0;
   }

   @Override
   protected void b() {
      super.b();
      float var0 = this.n();
      if (var0 > (float)this.k) {
         i.warn("Player exceeded rate-limit (sent {} packets per second)", var0);
         this.a(new PacketPlayOutKickDisconnect(j), PacketSendListener.a(() -> this.a(j)));
         this.l();
      }
   }
}
