package net.minecraft.server.network;

import com.google.common.primitives.Ints;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.exceptions.AuthenticationUnavailableException;
import com.mojang.authlib.properties.Property;
import com.mojang.logging.LogUtils;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.security.PrivateKey;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import javax.annotation.Nullable;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import net.minecraft.DefaultUncaughtExceptionHandler;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.PacketSendListener;
import net.minecraft.network.TickablePacketListener;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.chat.IChatMutableComponent;
import net.minecraft.network.protocol.game.PacketPlayOutKickDisconnect;
import net.minecraft.network.protocol.login.PacketLoginInCustomPayload;
import net.minecraft.network.protocol.login.PacketLoginInEncryptionBegin;
import net.minecraft.network.protocol.login.PacketLoginInListener;
import net.minecraft.network.protocol.login.PacketLoginInStart;
import net.minecraft.network.protocol.login.PacketLoginOutDisconnect;
import net.minecraft.network.protocol.login.PacketLoginOutEncryptionBegin;
import net.minecraft.network.protocol.login.PacketLoginOutSetCompression;
import net.minecraft.network.protocol.login.PacketLoginOutSuccess;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.util.CryptographyException;
import net.minecraft.util.MinecraftEncryption;
import net.minecraft.util.RandomSource;
import org.apache.commons.lang3.Validate;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.craftbukkit.v1_19_R3.util.Waitable;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerPreLoginEvent;
import org.bukkit.event.player.PlayerPreLoginEvent.Result;
import org.slf4j.Logger;

public class LoginListener implements PacketLoginInListener, TickablePacketListener {
   private static final AtomicInteger a = new AtomicInteger(0);
   static final Logger b = LogUtils.getLogger();
   private static final int c = 600;
   private static final RandomSource d = RandomSource.a();
   private final byte[] e;
   final MinecraftServer f;
   public final NetworkManager g;
   LoginListener.EnumProtocolState h = LoginListener.EnumProtocolState.a;
   private int i;
   @Nullable
   GameProfile j;
   private final String k = "";
   @Nullable
   private EntityPlayer l;

   public LoginListener(MinecraftServer minecraftserver, NetworkManager networkmanager) {
      this.f = minecraftserver;
      this.g = networkmanager;
      this.e = Ints.toByteArray(d.f());
   }

   @Override
   public void c() {
      if (this.h == LoginListener.EnumProtocolState.e) {
         this.d();
      } else if (this.h == LoginListener.EnumProtocolState.f) {
         EntityPlayer entityplayer = this.f.ac().a(this.j.getId());
         if (entityplayer == null) {
            this.h = LoginListener.EnumProtocolState.e;
            this.a(this.l);
            this.l = null;
         }
      }

      if (this.i++ == 600) {
         this.b(IChatBaseComponent.c("multiplayer.disconnect.slow_login"));
      }
   }

   @Deprecated
   public void disconnect(String s) {
      this.b(IChatBaseComponent.b(s));
   }

   @Override
   public boolean a() {
      return this.g.h();
   }

   public void b(IChatBaseComponent ichatbasecomponent) {
      try {
         b.info("Disconnecting {}: {}", this.e(), ichatbasecomponent.getString());
         this.g.a(new PacketLoginOutDisconnect(ichatbasecomponent));
         this.g.a(ichatbasecomponent);
      } catch (Exception var3) {
         b.error("Error whilst disconnecting player", var3);
      }
   }

   public void initUUID() {
      UUID uuid;
      if (this.g.spoofedUUID != null) {
         uuid = this.g.spoofedUUID;
      } else {
         uuid = UUIDUtil.a(this.j.getName());
      }

      this.j = new GameProfile(uuid, this.j.getName());
      if (this.g.spoofedProfile != null) {
         for(Property property : this.g.spoofedProfile) {
            if (HandshakeListener.PROP_PATTERN.matcher(property.getName()).matches()) {
               this.j.getProperties().put(property.getName(), property);
            }
         }
      }
   }

   public void d() {
      this.f.U();
      EntityPlayer s = this.f.ac().canPlayerLogin(this, this.j);
      if (s != null) {
         this.h = LoginListener.EnumProtocolState.g;
         if (this.f.av() >= 0 && !this.g.d()) {
            this.g.a(new PacketLoginOutSetCompression(this.f.av()), PacketSendListener.a(() -> this.g.a(this.f.av(), true)));
         }

         this.g.a(new PacketLoginOutSuccess(this.j));
         EntityPlayer entityplayer = this.f.ac().a(this.j.getId());

         try {
            EntityPlayer entityplayer1 = this.f.ac().getPlayerForLogin(this.j, s);
            if (entityplayer != null) {
               this.h = LoginListener.EnumProtocolState.f;
               this.l = entityplayer1;
            } else {
               this.a(entityplayer1);
            }
         } catch (Exception var5) {
            b.error("Couldn't place player in world", var5);
            IChatMutableComponent ichatmutablecomponent = IChatBaseComponent.c("multiplayer.disconnect.invalid_player_data");
            this.g.a(new PacketPlayOutKickDisconnect(ichatmutablecomponent));
            this.g.a(ichatmutablecomponent);
         }
      }
   }

   private void a(EntityPlayer entityplayer) {
      this.f.ac().a(this.g, entityplayer);
   }

   @Override
   public void a(IChatBaseComponent ichatbasecomponent) {
      b.info("{} lost connection: {}", this.e(), ichatbasecomponent.getString());
   }

   public String e() {
      return this.j != null ? this.j + " (" + this.g.c() + ")" : String.valueOf(this.g.c());
   }

   @Override
   public void a(PacketLoginInStart packetlogininstart) {
      Validate.validState(this.h == LoginListener.EnumProtocolState.a, "Unexpected hello packet", new Object[0]);
      Validate.validState(a(packetlogininstart.a()), "Invalid characters in username", new Object[0]);
      GameProfile gameprofile = this.f.N();
      if (gameprofile != null && packetlogininstart.a().equalsIgnoreCase(gameprofile.getName())) {
         this.j = gameprofile;
         this.h = LoginListener.EnumProtocolState.e;
      } else {
         this.j = new GameProfile(null, packetlogininstart.a());
         if (this.f.U() && !this.g.d()) {
            this.h = LoginListener.EnumProtocolState.b;
            this.g.a(new PacketLoginOutEncryptionBegin("", this.f.L().getPublic().getEncoded(), this.e));
         } else {
            (new Thread("User Authenticator #" + a.incrementAndGet()) {
               @Override
               public void run() {
                  try {
                     LoginListener.this.initUUID();
                     LoginListener.this.new LoginHandler().fireEvents();
                  } catch (Exception var2) {
                     LoginListener.this.disconnect("Failed to verify username!");
                     LoginListener.this.f.server.getLogger().log(Level.WARNING, "Exception verifying " + LoginListener.this.j.getName(), (Throwable)var2);
                  }
               }
            }).start();
         }
      }
   }

   public static boolean a(String s) {
      return s.chars().filter(i -> i <= 32 || i >= 127).findAny().isEmpty();
   }

   @Override
   public void a(PacketLoginInEncryptionBegin packetlogininencryptionbegin) {
      Validate.validState(this.h == LoginListener.EnumProtocolState.b, "Unexpected key packet", new Object[0]);

      final String s;
      try {
         PrivateKey privatekey = this.f.L().getPrivate();
         if (!packetlogininencryptionbegin.a(this.e, privatekey)) {
            throw new IllegalStateException("Protocol error");
         }

         SecretKey secretkey = packetlogininencryptionbegin.a(privatekey);
         Cipher cipher = MinecraftEncryption.a(2, secretkey);
         Cipher cipher1 = MinecraftEncryption.a(1, secretkey);
         s = new BigInteger(MinecraftEncryption.a("", this.f.L().getPublic(), secretkey)).toString(16);
         this.h = LoginListener.EnumProtocolState.c;
         this.g.a(cipher, cipher1);
      } catch (CryptographyException var7) {
         throw new IllegalStateException("Protocol error", var7);
      }

      Thread thread = new Thread("User Authenticator #" + a.incrementAndGet()) {
         @Override
         public void run() {
            GameProfile gameprofile = LoginListener.this.j;

            try {
               LoginListener.this.j = LoginListener.this.f.am().hasJoinedServer(new GameProfile(null, gameprofile.getName()), s, this.getAddress());
               if (LoginListener.this.j != null) {
                  if (!LoginListener.this.g.h()) {
                     return;
                  }

                  LoginListener.this.new LoginHandler().fireEvents();
               } else if (LoginListener.this.f.O()) {
                  LoginListener.b.warn("Failed to verify username but will let them in anyway!");
                  LoginListener.this.j = gameprofile;
                  LoginListener.this.h = LoginListener.EnumProtocolState.e;
               } else {
                  LoginListener.this.b(IChatBaseComponent.c("multiplayer.disconnect.unverified_username"));
                  LoginListener.b.error("Username '{}' tried to join with an invalid session", gameprofile.getName());
               }
            } catch (AuthenticationUnavailableException var3) {
               if (LoginListener.this.f.O()) {
                  LoginListener.b.warn("Authentication servers are down but will let them in anyway!");
                  LoginListener.this.j = gameprofile;
                  LoginListener.this.h = LoginListener.EnumProtocolState.e;
               } else {
                  LoginListener.this.b(IChatBaseComponent.c("multiplayer.disconnect.authservers_down"));
                  LoginListener.b.error("Couldn't verify username because servers are unavailable");
               }
            } catch (Exception var4) {
               LoginListener.this.disconnect("Failed to verify username!");
               LoginListener.this.f.server.getLogger().log(Level.WARNING, "Exception verifying " + gameprofile.getName(), (Throwable)var4);
            }
         }

         @Nullable
         private InetAddress getAddress() {
            SocketAddress socketaddress = LoginListener.this.g.c();
            return LoginListener.this.f.V() && socketaddress instanceof InetSocketAddress ? ((InetSocketAddress)socketaddress).getAddress() : null;
         }
      };
      thread.setUncaughtExceptionHandler(new DefaultUncaughtExceptionHandler(b));
      thread.start();
   }

   @Override
   public void a(PacketLoginInCustomPayload packetloginincustompayload) {
      this.b(IChatBaseComponent.c("multiplayer.disconnect.unexpected_query_response"));
   }

   protected GameProfile a(GameProfile gameprofile) {
      UUID uuid = UUIDUtil.a(gameprofile.getName());
      return new GameProfile(uuid, gameprofile.getName());
   }

   private static enum EnumProtocolState {
      a,
      b,
      c,
      d,
      e,
      f,
      g;
   }

   public class LoginHandler {
      public void fireEvents() throws Exception {
         String playerName = LoginListener.this.j.getName();
         InetAddress address = ((InetSocketAddress)LoginListener.this.g.c()).getAddress();
         UUID uniqueId = LoginListener.this.j.getId();
         final CraftServer server = LoginListener.this.f.server;
         AsyncPlayerPreLoginEvent asyncEvent = new AsyncPlayerPreLoginEvent(playerName, address, uniqueId);
         server.getPluginManager().callEvent(asyncEvent);
         if (PlayerPreLoginEvent.getHandlerList().getRegisteredListeners().length != 0) {
            final PlayerPreLoginEvent event = new PlayerPreLoginEvent(playerName, address, uniqueId);
            if (asyncEvent.getResult() != Result.ALLOWED) {
               event.disallow(asyncEvent.getResult(), asyncEvent.getKickMessage());
            }

            Waitable<Result> waitable = new Waitable<Result>() {
               protected Result evaluate() {
                  server.getPluginManager().callEvent(event);
                  return event.getResult();
               }
            };
            LoginListener.this.f.processQueue.add(waitable);
            if (waitable.get() != Result.ALLOWED) {
               LoginListener.this.disconnect(event.getKickMessage());
               return;
            }
         } else if (asyncEvent.getLoginResult() != org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result.ALLOWED) {
            LoginListener.this.disconnect(asyncEvent.getKickMessage());
            return;
         }

         LoginListener.b.info("UUID of player {} is {}", LoginListener.this.j.getName(), LoginListener.this.j.getId());
         LoginListener.this.h = LoginListener.EnumProtocolState.e;
      }
   }
}
