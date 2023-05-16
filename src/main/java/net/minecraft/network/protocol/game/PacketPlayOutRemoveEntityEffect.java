package net.minecraft.network.protocol.game;

import javax.annotation.Nullable;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.effect.MobEffectList;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.World;

public class PacketPlayOutRemoveEntityEffect implements Packet<PacketListenerPlayOut> {
   private final int a;
   private final MobEffectList b;

   public PacketPlayOutRemoveEntityEffect(int var0, MobEffectList var1) {
      this.a = var0;
      this.b = var1;
   }

   public PacketPlayOutRemoveEntityEffect(PacketDataSerializer var0) {
      this.a = var0.m();
      this.b = var0.a(BuiltInRegistries.e);
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.d(this.a);
      var0.a(BuiltInRegistries.e, this.b);
   }

   public void a(PacketListenerPlayOut var0) {
      var0.a(this);
   }

   @Nullable
   public Entity a(World var0) {
      return var0.a(this.a);
   }

   @Nullable
   public MobEffectList a() {
      return this.b;
   }
}
