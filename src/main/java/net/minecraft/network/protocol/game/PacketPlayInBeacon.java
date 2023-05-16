package net.minecraft.network.protocol.game;

import java.util.Optional;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.effect.MobEffectList;

public class PacketPlayInBeacon implements Packet<PacketListenerPlayIn> {
   private final Optional<MobEffectList> a;
   private final Optional<MobEffectList> b;

   public PacketPlayInBeacon(Optional<MobEffectList> var0, Optional<MobEffectList> var1) {
      this.a = var0;
      this.b = var1;
   }

   public PacketPlayInBeacon(PacketDataSerializer var0) {
      this.a = var0.b(var0x -> var0x.a(BuiltInRegistries.e));
      this.b = var0.b(var0x -> var0x.a(BuiltInRegistries.e));
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.a(this.a, (var0x, var1x) -> var0x.a(BuiltInRegistries.e, var1x));
      var0.a(this.b, (var0x, var1x) -> var0x.a(BuiltInRegistries.e, var1x));
   }

   public void a(PacketListenerPlayIn var0) {
      var0.a(this);
   }

   public Optional<MobEffectList> a() {
      return this.a;
   }

   public Optional<MobEffectList> c() {
      return this.b;
   }
}
