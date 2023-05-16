package net.minecraft.network.protocol.game;

import javax.annotation.Nullable;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.World;

public class PacketPlayOutCamera implements Packet<PacketListenerPlayOut> {
   private final int a;

   public PacketPlayOutCamera(Entity var0) {
      this.a = var0.af();
   }

   public PacketPlayOutCamera(PacketDataSerializer var0) {
      this.a = var0.m();
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.d(this.a);
   }

   public void a(PacketListenerPlayOut var0) {
      var0.a(this);
   }

   @Nullable
   public Entity a(World var0) {
      return var0.a(this.a);
   }
}
