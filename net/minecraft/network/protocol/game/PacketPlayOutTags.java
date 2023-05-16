package net.minecraft.network.protocol.game;

import java.util.Map;
import net.minecraft.core.IRegistry;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagNetworkSerialization;

public class PacketPlayOutTags implements Packet<PacketListenerPlayOut> {
   private final Map<ResourceKey<? extends IRegistry<?>>, TagNetworkSerialization.a> a;

   public PacketPlayOutTags(Map<ResourceKey<? extends IRegistry<?>>, TagNetworkSerialization.a> var0) {
      this.a = var0;
   }

   public PacketPlayOutTags(PacketDataSerializer var0) {
      this.a = var0.a(var0x -> ResourceKey.a(var0x.t()), TagNetworkSerialization.a::b);
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.a(this.a, (var0x, var1x) -> var0x.a(var1x.a()), (var0x, var1x) -> var1x.a(var0x));
   }

   public void a(PacketListenerPlayOut var0) {
      var0.a(this);
   }

   public Map<ResourceKey<? extends IRegistry<?>>, TagNetworkSerialization.a> a() {
      return this.a;
   }
}
