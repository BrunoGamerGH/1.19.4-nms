package net.minecraft.network.protocol.game;

import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.EntityLiving;

public record ClientboundHurtAnimationPacket(int id, float yaw) implements Packet<PacketListenerPlayOut> {
   private final int a;
   private final float b;

   public ClientboundHurtAnimationPacket(EntityLiving var0) {
      this(var0.af(), var0.ex());
   }

   public ClientboundHurtAnimationPacket(PacketDataSerializer var0) {
      this(var0.m(), var0.readFloat());
   }

   public ClientboundHurtAnimationPacket(int var0, float var1) {
      this.a = var0;
      this.b = var1;
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.d(this.a);
      var0.writeFloat(this.b);
   }

   public void a(PacketListenerPlayOut var0) {
      var0.a(this);
   }

   public float c() {
      return this.b;
   }
}
