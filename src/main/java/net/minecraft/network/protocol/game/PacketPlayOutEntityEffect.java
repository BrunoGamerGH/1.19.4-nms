package net.minecraft.network.protocol.game;

import javax.annotation.Nullable;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.DynamicOpsNBT;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectList;

public class PacketPlayOutEntityEffect implements Packet<PacketListenerPlayOut> {
   private static final int a = 1;
   private static final int b = 2;
   private static final int c = 4;
   private final int d;
   private final MobEffectList e;
   private final byte f;
   private final int g;
   private final byte h;
   @Nullable
   private final MobEffect.a i;

   public PacketPlayOutEntityEffect(int var0, MobEffect var1) {
      this.d = var0;
      this.e = var1.c();
      this.f = (byte)(var1.e() & 0xFF);
      this.g = var1.d();
      byte var2 = 0;
      if (var1.f()) {
         var2 = (byte)(var2 | 1);
      }

      if (var1.g()) {
         var2 = (byte)(var2 | 2);
      }

      if (var1.h()) {
         var2 = (byte)(var2 | 4);
      }

      this.h = var2;
      this.i = var1.a().orElse(null);
   }

   public PacketPlayOutEntityEffect(PacketDataSerializer var0) {
      this.d = var0.m();
      this.e = var0.a(BuiltInRegistries.e);
      this.f = var0.readByte();
      this.g = var0.m();
      this.h = var0.readByte();
      this.i = var0.c(var0x -> var0x.a(DynamicOpsNBT.a, MobEffect.a.a));
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.d(this.d);
      var0.a(BuiltInRegistries.e, this.e);
      var0.writeByte(this.f);
      var0.d(this.g);
      var0.writeByte(this.h);
      var0.a(this.i, (var0x, var1x) -> var0x.a(DynamicOpsNBT.a, MobEffect.a.a, var1x));
   }

   public void a(PacketListenerPlayOut var0) {
      var0.a(this);
   }

   public int a() {
      return this.d;
   }

   public MobEffectList c() {
      return this.e;
   }

   public byte d() {
      return this.f;
   }

   public int e() {
      return this.g;
   }

   public boolean f() {
      return (this.h & 2) == 2;
   }

   public boolean g() {
      return (this.h & 1) == 1;
   }

   public boolean h() {
      return (this.h & 4) == 4;
   }

   @Nullable
   public MobEffect.a i() {
      return this.i;
   }
}
