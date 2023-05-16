package net.minecraft.network.protocol.game;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.world.entity.Entity;

public class PacketPlayOutEntitySound implements Packet<PacketListenerPlayOut> {
   private final Holder<SoundEffect> a;
   private final SoundCategory b;
   private final int c;
   private final float d;
   private final float e;
   private final long f;

   public PacketPlayOutEntitySound(Holder<SoundEffect> var0, SoundCategory var1, Entity var2, float var3, float var4, long var5) {
      this.a = var0;
      this.b = var1;
      this.c = var2.af();
      this.d = var3;
      this.e = var4;
      this.f = var5;
   }

   public PacketPlayOutEntitySound(PacketDataSerializer var0) {
      this.a = var0.a(BuiltInRegistries.c.t(), SoundEffect::b);
      this.b = var0.b(SoundCategory.class);
      this.c = var0.m();
      this.d = var0.readFloat();
      this.e = var0.readFloat();
      this.f = var0.readLong();
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.a(BuiltInRegistries.c.t(), this.a, (var0x, var1x) -> var1x.a(var0x));
      var0.a(this.b);
      var0.d(this.c);
      var0.writeFloat(this.d);
      var0.writeFloat(this.e);
      var0.writeLong(this.f);
   }

   public Holder<SoundEffect> a() {
      return this.a;
   }

   public SoundCategory c() {
      return this.b;
   }

   public int d() {
      return this.c;
   }

   public float e() {
      return this.d;
   }

   public float f() {
      return this.e;
   }

   public long g() {
      return this.f;
   }

   public void a(PacketListenerPlayOut var0) {
      var0.a(this);
   }
}
