package net.minecraft.server.network;

import com.google.gson.Gson;
import com.mojang.authlib.properties.Property;
import com.mojang.util.UUIDTypeAdapter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.regex.Pattern;
import net.minecraft.SharedConstants;
import net.minecraft.network.EnumProtocol;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.chat.IChatMutableComponent;
import net.minecraft.network.protocol.handshake.PacketHandshakingInListener;
import net.minecraft.network.protocol.handshake.PacketHandshakingInSetProtocol;
import net.minecraft.network.protocol.login.PacketLoginOutDisconnect;
import net.minecraft.network.protocol.status.ServerPing;
import net.minecraft.server.MinecraftServer;
import org.apache.logging.log4j.LogManager;
import org.spigotmc.SpigotConfig;

public class HandshakeListener implements PacketHandshakingInListener {
   private static final Gson gson = new Gson();
   static final Pattern HOST_PATTERN = Pattern.compile("[0-9a-f\\.:]{0,45}");
   static final Pattern PROP_PATTERN = Pattern.compile("\\w{0,16}");
   private static final HashMap<InetAddress, Long> throttleTracker = new HashMap<>();
   private static int throttleCounter = 0;
   private static final IChatBaseComponent a = IChatBaseComponent.b("Ignoring status request");
   private final MinecraftServer b;
   private final NetworkManager c;

   public HandshakeListener(MinecraftServer minecraftserver, NetworkManager networkmanager) {
      this.b = minecraftserver;
      this.c = networkmanager;
   }

   @Override
   public void a(PacketHandshakingInSetProtocol packethandshakinginsetprotocol) {
      this.c.hostname = packethandshakinginsetprotocol.c + ":" + packethandshakinginsetprotocol.d;
      switch(packethandshakinginsetprotocol.a()) {
         case c:
            ServerPing serverping = this.b.aq();
            if (this.b.ai() && serverping != null) {
               this.c.a(EnumProtocol.c);
               this.c.a(new PacketStatusListener(serverping, this.c));
            } else {
               this.c.a(a);
            }
            break;
         case d:
            this.c.a(EnumProtocol.d);

            try {
               long currentTime = System.currentTimeMillis();
               long connectionThrottle = this.b.server.getConnectionThrottle();
               InetAddress address = ((InetSocketAddress)this.c.c()).getAddress();
               synchronized(throttleTracker) {
                  if (throttleTracker.containsKey(address)
                     && !"127.0.0.1".equals(address.getHostAddress())
                     && currentTime - throttleTracker.get(address) < connectionThrottle) {
                     throttleTracker.put(address, currentTime);
                     IChatMutableComponent chatmessage = IChatBaseComponent.b("Connection throttled! Please wait before reconnecting.");
                     this.c.a(new PacketLoginOutDisconnect(chatmessage));
                     this.c.a(chatmessage);
                     return;
                  }

                  throttleTracker.put(address, currentTime);
                  ++throttleCounter;
                  if (throttleCounter > 200) {
                     throttleCounter = 0;
                     Iterator iter = throttleTracker.entrySet().iterator();

                     while(iter.hasNext()) {
                        Entry<InetAddress, Long> entry = (Entry)iter.next();
                        if (entry.getValue() > connectionThrottle) {
                           iter.remove();
                        }
                     }
                  }
               }
            } catch (Throwable var11) {
               LogManager.getLogger().debug("Failed to check connection throttle", var11);
            }

            if (packethandshakinginsetprotocol.c() != SharedConstants.b().e()) {
               IChatMutableComponent ichatmutablecomponent;
               if (packethandshakinginsetprotocol.c() < 754) {
                  ichatmutablecomponent = IChatBaseComponent.b(
                     MessageFormat.format(SpigotConfig.outdatedClientMessage.replaceAll("'", "''"), SharedConstants.b().c())
                  );
               } else {
                  ichatmutablecomponent = IChatBaseComponent.b(
                     MessageFormat.format(SpigotConfig.outdatedServerMessage.replaceAll("'", "''"), SharedConstants.b().c())
                  );
               }

               this.c.a(new PacketLoginOutDisconnect(ichatmutablecomponent));
               this.c.a(ichatmutablecomponent);
            } else {
               this.c.a(new LoginListener(this.b, this.c));
               String[] split = packethandshakinginsetprotocol.c.split("\u0000");
               if (SpigotConfig.bungee) {
                  if (split.length != 3 && split.length != 4 || !HOST_PATTERN.matcher(split[1]).matches()) {
                     IChatBaseComponent chatmessage = IChatBaseComponent.b(
                        "If you wish to use IP forwarding, please enable it in your BungeeCord config as well!"
                     );
                     this.c.a(new PacketLoginOutDisconnect(chatmessage));
                     this.c.a(chatmessage);
                     return;
                  }

                  packethandshakinginsetprotocol.c = split[0];
                  this.c.n = new InetSocketAddress(split[1], ((InetSocketAddress)this.c.c()).getPort());
                  this.c.spoofedUUID = UUIDTypeAdapter.fromString(split[2]);
                  if (split.length == 4) {
                     this.c.spoofedProfile = (Property[])gson.fromJson(split[3], Property[].class);
                  }
               } else if ((split.length == 3 || split.length == 4) && HOST_PATTERN.matcher(split[1]).matches()) {
                  IChatBaseComponent chatmessage = IChatBaseComponent.b("Unknown data in login hostname, did you forget to enable BungeeCord in spigot.yml?");
                  this.c.a(new PacketLoginOutDisconnect(chatmessage));
                  this.c.a(chatmessage);
                  return;
               }
            }
            break;
         default:
            throw new UnsupportedOperationException("Invalid intention " + packethandshakinginsetprotocol.a());
      }
   }

   @Override
   public void a(IChatBaseComponent ichatbasecomponent) {
   }

   @Override
   public boolean a() {
      return this.c.h();
   }
}
