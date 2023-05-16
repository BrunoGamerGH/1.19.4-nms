package net.minecraft.network.protocol.game;

import net.minecraft.core.BlockPosition;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;

public class PacketPlayOutWorldEvent implements Packet<PacketListenerPlayOut> {
   private final int a;
   private final BlockPosition b;
   private final int c;
   private final boolean d;

   public PacketPlayOutWorldEvent(int var0, BlockPosition var1, int var2, boolean var3) {
      this.a = var0;
      this.b = var1.i();
      this.c = var2;
      this.d = var3;
   }

   public PacketPlayOutWorldEvent(PacketDataSerializer var0) {
      this.a = var0.readInt();
      this.b = var0.f();
      this.c = var0.readInt();
      this.d = var0.readBoolean();
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.writeInt(this.a);
      var0.a(this.b);
      var0.writeInt(this.c);
      var0.writeBoolean(this.d);
   }

   public void a(PacketListenerPlayOut var0) {
      var0.a(this);
   }

   public boolean a() {
      return this.d;
   }

   public int c() {
      return this.a;
   }

   public int d() {
      return this.c;
   }

   public BlockPosition e() {
      return this.b;
   }
}
