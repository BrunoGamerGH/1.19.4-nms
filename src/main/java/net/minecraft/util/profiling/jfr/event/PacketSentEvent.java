package net.minecraft.util.profiling.jfr.event;

import java.net.SocketAddress;
import jdk.jfr.EventType;
import jdk.jfr.Label;
import jdk.jfr.Name;
import net.minecraft.obfuscate.DontObfuscate;

@Name("minecraft.PacketSent")
@Label("Network Packet Sent")
@DontObfuscate
public class PacketSentEvent extends PacketEvent {
   public static final String NAME = "minecraft.PacketSent";
   public static final EventType TYPE = EventType.getEventType(PacketSentEvent.class);

   public PacketSentEvent(int var0, int var1, SocketAddress var2, int var3) {
      super(var0, var1, var2, var3);
   }
}
