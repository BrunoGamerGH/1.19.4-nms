package net.minecraft.network.protocol.game;

import javax.annotation.Nullable;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.Entity;

public class PacketPlayOutAttachEntity implements Packet<PacketListenerPlayOut> {
   private final int a;
   private final int b;

   public PacketPlayOutAttachEntity(Entity var0, @Nullable Entity var1) {
      this.a = var0.af();
      this.b = var1 != null ? var1.af() : 0;
   }

   public PacketPlayOutAttachEntity(PacketDataSerializer var0) {
      this.a = var0.readInt();
      this.b = var0.readInt();
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.writeInt(this.a);
      var0.writeInt(this.b);
   }

   public void a(PacketListenerPlayOut var0) {
      var0.a(this);
   }

   public int a() {
      return this.a;
   }

   public int c() {
      return this.b;
   }
}
