package net.minecraft.network.protocol.login;

import java.util.Optional;
import java.util.UUID;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;

public record PacketLoginInStart(String name, Optional<UUID> profileId) implements Packet<PacketLoginInListener> {
   private final String a;
   private final Optional<UUID> b;

   public PacketLoginInStart(PacketDataSerializer var0) {
      this(var0.e(16), var0.b(PacketDataSerializer::o));
   }

   public PacketLoginInStart(String var0, Optional<UUID> var1) {
      this.a = var0;
      this.b = var1;
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.a(this.a, 16);
      var0.a(this.b, PacketDataSerializer::a);
   }

   public void a(PacketLoginInListener var0) {
      var0.a(this);
   }

   public Optional<UUID> c() {
      return this.b;
   }
}
