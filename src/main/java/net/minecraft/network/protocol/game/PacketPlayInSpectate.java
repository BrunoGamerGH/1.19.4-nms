package net.minecraft.network.protocol.game;

import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.entity.Entity;

public class PacketPlayInSpectate implements Packet<PacketListenerPlayIn> {
   private final UUID a;

   public PacketPlayInSpectate(UUID var0) {
      this.a = var0;
   }

   public PacketPlayInSpectate(PacketDataSerializer var0) {
      this.a = var0.o();
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.a(this.a);
   }

   public void a(PacketListenerPlayIn var0) {
      var0.a(this);
   }

   @Nullable
   public Entity a(WorldServer var0) {
      return var0.a(this.a);
   }
}
