package net.minecraft.network.protocol.game;

import javax.annotation.Nullable;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.MinecraftKey;

public class PacketPlayOutSelectAdvancementTab implements Packet<PacketListenerPlayOut> {
   @Nullable
   private final MinecraftKey a;

   public PacketPlayOutSelectAdvancementTab(@Nullable MinecraftKey var0) {
      this.a = var0;
   }

   public void a(PacketListenerPlayOut var0) {
      var0.a(this);
   }

   public PacketPlayOutSelectAdvancementTab(PacketDataSerializer var0) {
      this.a = var0.c(PacketDataSerializer::t);
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.a(this.a, PacketDataSerializer::a);
   }

   @Nullable
   public MinecraftKey a() {
      return this.a;
   }
}
