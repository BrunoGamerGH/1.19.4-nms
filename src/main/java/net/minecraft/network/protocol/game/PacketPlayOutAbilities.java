package net.minecraft.network.protocol.game;

import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.player.PlayerAbilities;

public class PacketPlayOutAbilities implements Packet<PacketListenerPlayOut> {
   private static final int a = 1;
   private static final int b = 2;
   private static final int c = 4;
   private static final int d = 8;
   private final boolean e;
   private final boolean f;
   private final boolean g;
   private final boolean h;
   private final float i;
   private final float j;

   public PacketPlayOutAbilities(PlayerAbilities var0) {
      this.e = var0.a;
      this.f = var0.b;
      this.g = var0.c;
      this.h = var0.d;
      this.i = var0.a();
      this.j = var0.b();
   }

   public PacketPlayOutAbilities(PacketDataSerializer var0) {
      byte var1 = var0.readByte();
      this.e = (var1 & 1) != 0;
      this.f = (var1 & 2) != 0;
      this.g = (var1 & 4) != 0;
      this.h = (var1 & 8) != 0;
      this.i = var0.readFloat();
      this.j = var0.readFloat();
   }

   @Override
   public void a(PacketDataSerializer var0) {
      byte var1 = 0;
      if (this.e) {
         var1 = (byte)(var1 | 1);
      }

      if (this.f) {
         var1 = (byte)(var1 | 2);
      }

      if (this.g) {
         var1 = (byte)(var1 | 4);
      }

      if (this.h) {
         var1 = (byte)(var1 | 8);
      }

      var0.writeByte(var1);
      var0.writeFloat(this.i);
      var0.writeFloat(this.j);
   }

   public void a(PacketListenerPlayOut var0) {
      var0.a(this);
   }

   public boolean a() {
      return this.e;
   }

   public boolean c() {
      return this.f;
   }

   public boolean d() {
      return this.g;
   }

   public boolean e() {
      return this.h;
   }

   public float f() {
      return this.i;
   }

   public float g() {
      return this.j;
   }
}
