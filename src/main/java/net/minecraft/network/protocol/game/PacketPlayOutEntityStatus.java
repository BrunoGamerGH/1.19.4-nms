package net.minecraft.network.protocol.game;

import javax.annotation.Nullable;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.World;

public class PacketPlayOutEntityStatus implements Packet<PacketListenerPlayOut> {
   private final int a;
   private final byte b;

   public PacketPlayOutEntityStatus(Entity var0, byte var1) {
      this.a = var0.af();
      this.b = var1;
   }

   public PacketPlayOutEntityStatus(PacketDataSerializer var0) {
      this.a = var0.readInt();
      this.b = var0.readByte();
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.writeInt(this.a);
      var0.writeByte(this.b);
   }

   public void a(PacketListenerPlayOut var0) {
      var0.a(this);
   }

   @Nullable
   public Entity a(World var0) {
      return var0.a(this.a);
   }

   public byte a() {
      return this.b;
   }
}
