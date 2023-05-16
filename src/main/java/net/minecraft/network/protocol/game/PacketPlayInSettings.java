package net.minecraft.network.protocol.game;

import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.EnumMainHand;
import net.minecraft.world.entity.player.EnumChatVisibility;

public record PacketPlayInSettings(
   String language,
   int viewDistance,
   EnumChatVisibility chatVisibility,
   boolean chatColors,
   int modelCustomisation,
   EnumMainHand mainHand,
   boolean textFilteringEnabled,
   boolean allowsListing
) implements Packet<PacketListenerPlayIn> {
   public final String b;
   public final int c;
   private final EnumChatVisibility d;
   private final boolean e;
   private final int f;
   private final EnumMainHand g;
   private final boolean h;
   private final boolean i;
   public static final int a = 16;

   public PacketPlayInSettings(PacketDataSerializer var0) {
      this(
         var0.e(16),
         var0.readByte(),
         var0.b(EnumChatVisibility.class),
         var0.readBoolean(),
         var0.readUnsignedByte(),
         var0.b(EnumMainHand.class),
         var0.readBoolean(),
         var0.readBoolean()
      );
   }

   public PacketPlayInSettings(String var0, int var1, EnumChatVisibility var2, boolean var3, int var4, EnumMainHand var5, boolean var6, boolean var7) {
      this.b = var0;
      this.c = var1;
      this.d = var2;
      this.e = var3;
      this.f = var4;
      this.g = var5;
      this.h = var6;
      this.i = var7;
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.a(this.b);
      var0.writeByte(this.c);
      var0.a(this.d);
      var0.writeBoolean(this.e);
      var0.writeByte(this.f);
      var0.a(this.g);
      var0.writeBoolean(this.h);
      var0.writeBoolean(this.i);
   }

   public void a(PacketListenerPlayIn var0) {
      var0.a(this);
   }

   public String a() {
      return this.b;
   }
}
