package net.minecraft.network.protocol.game;

import net.minecraft.core.BlockPosition;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;

public class PacketPlayInUpdateSign implements Packet<PacketListenerPlayIn> {
   private static final int a = 384;
   private final BlockPosition b;
   private final String[] c;

   public PacketPlayInUpdateSign(BlockPosition var0, String var1, String var2, String var3, String var4) {
      this.b = var0;
      this.c = new String[]{var1, var2, var3, var4};
   }

   public PacketPlayInUpdateSign(PacketDataSerializer var0) {
      this.b = var0.f();
      this.c = new String[4];

      for(int var1 = 0; var1 < 4; ++var1) {
         this.c[var1] = var0.e(384);
      }
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.a(this.b);

      for(int var1 = 0; var1 < 4; ++var1) {
         var0.a(this.c[var1]);
      }
   }

   public void a(PacketListenerPlayIn var0) {
      var0.a(this);
   }

   public BlockPosition a() {
      return this.b;
   }

   public String[] c() {
      return this.c;
   }
}
