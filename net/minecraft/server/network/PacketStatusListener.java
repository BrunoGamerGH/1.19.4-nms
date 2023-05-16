package net.minecraft.server.network;

import com.mojang.authlib.GameProfile;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import net.minecraft.SharedConstants;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.protocol.status.PacketStatusInListener;
import net.minecraft.network.protocol.status.PacketStatusInPing;
import net.minecraft.network.protocol.status.PacketStatusInStart;
import net.minecraft.network.protocol.status.PacketStatusOutPong;
import net.minecraft.network.protocol.status.PacketStatusOutServerInfo;
import net.minecraft.network.protocol.status.ServerPing;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.EntityPlayer;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftChatMessage;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftIconCache;
import org.bukkit.entity.Player;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.util.CachedServerIcon;
import org.spigotmc.SpigotConfig;

public class PacketStatusListener implements PacketStatusInListener {
   private static final IChatBaseComponent a = IChatBaseComponent.c("multiplayer.status.request_handled");
   private final ServerPing b;
   private final NetworkManager c;
   private boolean d;

   public PacketStatusListener(ServerPing serverping, NetworkManager networkmanager) {
      this.b = serverping;
      this.c = networkmanager;
   }

   @Override
   public void a(IChatBaseComponent ichatbasecomponent) {
   }

   @Override
   public boolean a() {
      return this.c.h();
   }

   @Override
   public void a(PacketStatusInStart packetstatusinstart) {
      if (this.d) {
         this.c.a(a);
      } else {
         this.d = true;
         MinecraftServer server = MinecraftServer.getServer();
         final Object[] players = server.ac().k.toArray();

         class 1ServerListPingEvent extends ServerListPingEvent {
            CraftIconCache icon;

            _ServerListPingEvent/* $QF was: 1ServerListPingEvent*/(MinecraftServer var2) {
               super(PacketStatusListener.this.c.hostname, ((InetSocketAddress)PacketStatusListener.this.c.c()).getAddress(), var2.aa(), var2.ac().n());
               this.icon = var2.server.getServerIcon();
            }

            public void setServerIcon(CachedServerIcon icon) {
               if (!(icon instanceof CraftIconCache)) {
                  throw new IllegalArgumentException(icon + " was not created by " + CraftServer.class);
               } else {
                  this.icon = (CraftIconCache)icon;
               }
            }

            public Iterator<Player> iterator() throws UnsupportedOperationException {
               return new Iterator<Player>() {
                  int i;
                  int ret = Integer.MIN_VALUE;
                  EntityPlayer player;

                  @Override
                  public boolean hasNext() {
                     if (this.player != null) {
                        return true;
                     } else {
                        Object[] currentPlayers = players;
                        int length = currentPlayers.length;

                        for(int i = this.i; i < length; ++i) {
                           EntityPlayer player = (EntityPlayer)currentPlayers[i];
                           if (player != null) {
                              this.i = i + 1;
                              this.player = player;
                              return true;
                           }
                        }

                        return false;
                     }
                  }

                  public Player next() {
                     if (!this.hasNext()) {
                        throw new NoSuchElementException();
                     } else {
                        EntityPlayer player = this.player;
                        this.player = null;
                        this.ret = this.i - 1;
                        return player.getBukkitEntity();
                     }
                  }

                  @Override
                  public void remove() {
                     Object[] currentPlayers = players;
                     int i = this.ret;
                     if (i >= 0 && currentPlayers[i] != null) {
                        currentPlayers[i] = null;
                     } else {
                        throw new IllegalStateException();
                     }
                  }
               };
            }
         }

         1ServerListPingEvent event = new 1ServerListPingEvent(server);
         server.server.getPluginManager().callEvent(event);
         List<GameProfile> profiles = new ArrayList(players.length);

         for(Object player : players) {
            if (player != null) {
               EntityPlayer entityPlayer = (EntityPlayer)player;
               if (entityPlayer.V()) {
                  profiles.add(entityPlayer.fI());
               } else {
                  profiles.add(MinecraftServer.g);
               }
            }
         }

         if (!server.aj() && !profiles.isEmpty()) {
            Collections.shuffle(profiles);
            profiles = profiles.subList(0, Math.min(profiles.size(), SpigotConfig.playerSample));
         }

         ServerPing.ServerPingPlayerSample playerSample = new ServerPing.ServerPingPlayerSample(
            event.getMaxPlayers(), profiles.size(), server.aj() ? Collections.emptyList() : profiles
         );
         ServerPing ping = new ServerPing(
            CraftChatMessage.fromString(event.getMotd(), true)[0],
            Optional.of(playerSample),
            Optional.of(new ServerPing.ServerData(server.getServerModName() + " " + server.G(), SharedConstants.b().e())),
            event.icon.value != null ? Optional.of(new ServerPing.a(event.icon.value)) : Optional.empty(),
            server.aw()
         );
         this.c.a(new PacketStatusOutServerInfo(ping));
      }
   }

   @Override
   public void a(PacketStatusInPing packetstatusinping) {
      this.c.a(new PacketStatusOutPong(packetstatusinping.a()));
      this.c.a(a);
   }
}
