package net.minecraft.network.protocol.game;

import net.minecraft.core.BlockPosition;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;

public class PacketPlayOutSpawnPosition implements Packet<PacketListenerPlayOut> {
   public final BlockPosition a;
   private final float b;

   public PacketPlayOutSpawnPosition(BlockPosition var0, float var1) {
      this.a = var0;
      this.b = var1;
   }

   public PacketPlayOutSpawnPosition(PacketDataSerializer var0) {
      this.a = var0.f();
      this.b = var0.readFloat();
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.a(this.a);
      var0.writeFloat(this.b);
   }

   public void a(PacketListenerPlayOut var0) {
      var0.a(this);
   }

   public BlockPosition a() {
      return this.a;
   }

   public float c() {
      return this.b;
   }
}
