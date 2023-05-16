package net.minecraft.network.protocol.game;

import javax.annotation.Nullable;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.sounds.SoundCategory;

public class PacketPlayOutStopSound implements Packet<PacketListenerPlayOut> {
   private static final int a = 1;
   private static final int b = 2;
   @Nullable
   private final MinecraftKey c;
   @Nullable
   private final SoundCategory d;

   public PacketPlayOutStopSound(@Nullable MinecraftKey var0, @Nullable SoundCategory var1) {
      this.c = var0;
      this.d = var1;
   }

   public PacketPlayOutStopSound(PacketDataSerializer var0) {
      int var1 = var0.readByte();
      if ((var1 & 1) > 0) {
         this.d = var0.b(SoundCategory.class);
      } else {
         this.d = null;
      }

      if ((var1 & 2) > 0) {
         this.c = var0.t();
      } else {
         this.c = null;
      }
   }

   @Override
   public void a(PacketDataSerializer var0) {
      if (this.d != null) {
         if (this.c != null) {
            var0.writeByte(3);
            var0.a(this.d);
            var0.a(this.c);
         } else {
            var0.writeByte(1);
            var0.a(this.d);
         }
      } else if (this.c != null) {
         var0.writeByte(2);
         var0.a(this.c);
      } else {
         var0.writeByte(0);
      }
   }

   @Nullable
   public MinecraftKey a() {
      return this.c;
   }

   @Nullable
   public SoundCategory c() {
      return this.d;
   }

   public void a(PacketListenerPlayOut var0) {
      var0.a(this);
   }
}
