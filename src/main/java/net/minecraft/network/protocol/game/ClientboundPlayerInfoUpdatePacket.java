package net.minecraft.network.protocol.game;

import com.google.common.base.MoreObjects;
import com.mojang.authlib.GameProfile;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.Optionull;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.chat.RemoteChatSession;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.level.EnumGamemode;

public class ClientboundPlayerInfoUpdatePacket implements Packet<PacketListenerPlayOut> {
   private final EnumSet<ClientboundPlayerInfoUpdatePacket.a> a;
   private final List<ClientboundPlayerInfoUpdatePacket.b> b;

   public ClientboundPlayerInfoUpdatePacket(EnumSet<ClientboundPlayerInfoUpdatePacket.a> var0, Collection<EntityPlayer> var1) {
      this.a = var0;
      this.b = var1.stream().map(ClientboundPlayerInfoUpdatePacket.b::new).toList();
   }

   public ClientboundPlayerInfoUpdatePacket(ClientboundPlayerInfoUpdatePacket.a var0, EntityPlayer var1) {
      this.a = EnumSet.of(var0);
      this.b = List.of(new ClientboundPlayerInfoUpdatePacket.b(var1));
   }

   public static ClientboundPlayerInfoUpdatePacket a(Collection<EntityPlayer> var0) {
      EnumSet<ClientboundPlayerInfoUpdatePacket.a> var1 = EnumSet.of(
         ClientboundPlayerInfoUpdatePacket.a.a,
         ClientboundPlayerInfoUpdatePacket.a.b,
         ClientboundPlayerInfoUpdatePacket.a.c,
         ClientboundPlayerInfoUpdatePacket.a.d,
         ClientboundPlayerInfoUpdatePacket.a.e,
         ClientboundPlayerInfoUpdatePacket.a.f
      );
      return new ClientboundPlayerInfoUpdatePacket(var1, var0);
   }

   public ClientboundPlayerInfoUpdatePacket(PacketDataSerializer var0) {
      this.a = var0.a(ClientboundPlayerInfoUpdatePacket.a.class);
      this.b = var0.a((PacketDataSerializer.a<ClientboundPlayerInfoUpdatePacket.b>)(var0x -> {
         ClientboundPlayerInfoUpdatePacket.c var1x = new ClientboundPlayerInfoUpdatePacket.c(var0x.o());

         for(ClientboundPlayerInfoUpdatePacket.a var3 : this.a) {
            var3.g.read(var1x, var0x);
         }

         return var1x.a();
      }));
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.a(this.a, ClientboundPlayerInfoUpdatePacket.a.class);
      var0.a(this.b, (var0x, var1x) -> {
         var0x.a(var1x.a());

         for(ClientboundPlayerInfoUpdatePacket.a var3 : this.a) {
            var3.h.write(var0x, var1x);
         }
      });
   }

   public void a(PacketListenerPlayOut var0) {
      var0.a(this);
   }

   public EnumSet<ClientboundPlayerInfoUpdatePacket.a> a() {
      return this.a;
   }

   public List<ClientboundPlayerInfoUpdatePacket.b> c() {
      return this.b;
   }

   public List<ClientboundPlayerInfoUpdatePacket.b> d() {
      return this.a.contains(ClientboundPlayerInfoUpdatePacket.a.a) ? this.b : List.of();
   }

   @Override
   public String toString() {
      return MoreObjects.toStringHelper(this).add("actions", this.a).add("entries", this.b).toString();
   }

   public static enum a {
      a((var0, var1) -> {
         GameProfile var2 = new GameProfile(var0.a, var1.e(16));
         var2.getProperties().putAll(var1.A());
         var0.b = var2;
      }, (var0, var1) -> {
         var0.a(var1.b().getName(), 16);
         var0.a(var1.b().getProperties());
      }),
      b((var0, var1) -> var0.g = var1.c(RemoteChatSession.a::a), (var0, var1) -> var0.a(var1.g, RemoteChatSession.a::a)),
      c((var0, var1) -> var0.e = EnumGamemode.a(var1.m()), (var0, var1) -> var0.d(var1.e().a())),
      d((var0, var1) -> var0.c = var1.readBoolean(), (var0, var1) -> var0.writeBoolean(var1.c())),
      e((var0, var1) -> var0.d = var1.m(), (var0, var1) -> var0.d(var1.d())),
      f((var0, var1) -> var0.f = var1.c(PacketDataSerializer::l), (var0, var1) -> var0.a(var1.f(), PacketDataSerializer::a));

      final ClientboundPlayerInfoUpdatePacket.a.a g;
      final ClientboundPlayerInfoUpdatePacket.a.b h;

      private a(ClientboundPlayerInfoUpdatePacket.a.a var2, ClientboundPlayerInfoUpdatePacket.a.b var3) {
         this.g = var2;
         this.h = var3;
      }

      public interface a {
         void read(ClientboundPlayerInfoUpdatePacket.c var1, PacketDataSerializer var2);
      }

      public interface b {
         void write(PacketDataSerializer var1, ClientboundPlayerInfoUpdatePacket.b var2);
      }
   }

   public static record b(
      UUID profileId,
      GameProfile profile,
      boolean listed,
      int latency,
      EnumGamemode gameMode,
      @Nullable IChatBaseComponent displayName,
      @Nullable RemoteChatSession.a chatSession
   ) {
      private final UUID a;
      private final GameProfile b;
      private final boolean c;
      private final int d;
      private final EnumGamemode e;
      @Nullable
      private final IChatBaseComponent f;
      @Nullable
      final RemoteChatSession.a g;

      b(EntityPlayer var0) {
         this(var0.cs(), var0.fI(), true, var0.e, var0.d.b(), var0.J(), Optionull.a(var0.X(), RemoteChatSession::b));
      }

      public b(UUID var0, GameProfile var1, boolean var2, int var3, EnumGamemode var4, @Nullable IChatBaseComponent var5, @Nullable RemoteChatSession.a var6) {
         this.a = var0;
         this.b = var1;
         this.c = var2;
         this.d = var3;
         this.e = var4;
         this.f = var5;
         this.g = var6;
      }
   }

   static class c {
      final UUID a;
      GameProfile b;
      boolean c;
      int d;
      EnumGamemode e;
      @Nullable
      IChatBaseComponent f;
      @Nullable
      RemoteChatSession.a g;

      c(UUID var0) {
         this.e = EnumGamemode.e;
         this.a = var0;
         this.b = new GameProfile(var0, null);
      }

      ClientboundPlayerInfoUpdatePacket.b a() {
         return new ClientboundPlayerInfoUpdatePacket.b(this.a, this.b, this.c, this.d, this.e, this.f, this.g);
      }
   }
}
