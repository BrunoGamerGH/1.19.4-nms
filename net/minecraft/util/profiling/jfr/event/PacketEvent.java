package net.minecraft.util.profiling.jfr.event;

import java.net.SocketAddress;
import jdk.jfr.Category;
import jdk.jfr.DataAmount;
import jdk.jfr.Enabled;
import jdk.jfr.Event;
import jdk.jfr.Label;
import jdk.jfr.Name;
import jdk.jfr.StackTrace;

@Category({"Minecraft", "Network"})
@StackTrace(false)
@Enabled(false)
public abstract class PacketEvent extends Event {
   @Name("protocolId")
   @Label("Protocol Id")
   public final int protocolId;
   @Name("packetId")
   @Label("Packet Id")
   public final int packetId;
   @Name("remoteAddress")
   @Label("Remote Address")
   public final String remoteAddress;
   @Name("bytes")
   @Label("Bytes")
   @DataAmount
   public final int bytes;

   public PacketEvent(int var0, int var1, SocketAddress var2, int var3) {
      this.protocolId = var0;
      this.packetId = var1;
      this.remoteAddress = var2.toString();
      this.bytes = var3;
   }

   public static final class a {
      public static final String a = "remoteAddress";
      public static final String b = "protocolId";
      public static final String c = "packetId";
      public static final String d = "bytes";

      private a() {
      }
   }
}
