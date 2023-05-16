package net.minecraft.network.protocol.game;

import javax.annotation.Nullable;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.protocol.Packet;

public class PacketPlayOutResourcePackSend implements Packet<PacketListenerPlayOut> {
   public static final int a = 40;
   private final String b;
   private final String c;
   private final boolean d;
   @Nullable
   private final IChatBaseComponent e;

   public PacketPlayOutResourcePackSend(String var0, String var1, boolean var2, @Nullable IChatBaseComponent var3) {
      if (var1.length() > 40) {
         throw new IllegalArgumentException("Hash is too long (max 40, was " + var1.length() + ")");
      } else {
         this.b = var0;
         this.c = var1;
         this.d = var2;
         this.e = var3;
      }
   }

   public PacketPlayOutResourcePackSend(PacketDataSerializer var0) {
      this.b = var0.s();
      this.c = var0.e(40);
      this.d = var0.readBoolean();
      this.e = var0.c(PacketDataSerializer::l);
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.a(this.b);
      var0.a(this.c);
      var0.writeBoolean(this.d);
      var0.a(this.e, PacketDataSerializer::a);
   }

   public void a(PacketListenerPlayOut var0) {
      var0.a(this);
   }

   public String a() {
      return this.b;
   }

   public String c() {
      return this.c;
   }

   public boolean d() {
      return this.d;
   }

   @Nullable
   public IChatBaseComponent e() {
      return this.e;
   }
}
