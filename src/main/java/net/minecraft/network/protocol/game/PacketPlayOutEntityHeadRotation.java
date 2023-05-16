package net.minecraft.network.protocol.game;

import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.World;

public class PacketPlayOutEntityHeadRotation implements Packet<PacketListenerPlayOut> {
   private final int a;
   private final byte b;

   public PacketPlayOutEntityHeadRotation(Entity var0, byte var1) {
      this.a = var0.af();
      this.b = var1;
   }

   public PacketPlayOutEntityHeadRotation(PacketDataSerializer var0) {
      this.a = var0.m();
      this.b = var0.readByte();
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.d(this.a);
      var0.writeByte(this.b);
   }

   public void a(PacketListenerPlayOut var0) {
      var0.a(this);
   }

   public Entity a(World var0) {
      return var0.a(this.a);
   }

   public byte a() {
      return this.b;
   }
}
