package net.minecraft.network.protocol.game;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffect;

public class PacketPlayOutNamedSoundEffect implements Packet<PacketListenerPlayOut> {
   public static final float a = 8.0F;
   private final Holder<SoundEffect> b;
   private final SoundCategory c;
   private final int d;
   private final int e;
   private final int f;
   private final float g;
   private final float h;
   private final long i;

   public PacketPlayOutNamedSoundEffect(
      Holder<SoundEffect> var0, SoundCategory var1, double var2, double var4, double var6, float var8, float var9, long var10
   ) {
      this.b = var0;
      this.c = var1;
      this.d = (int)(var2 * 8.0);
      this.e = (int)(var4 * 8.0);
      this.f = (int)(var6 * 8.0);
      this.g = var8;
      this.h = var9;
      this.i = var10;
   }

   public PacketPlayOutNamedSoundEffect(PacketDataSerializer var0) {
      this.b = var0.a(BuiltInRegistries.c.t(), SoundEffect::b);
      this.c = var0.b(SoundCategory.class);
      this.d = var0.readInt();
      this.e = var0.readInt();
      this.f = var0.readInt();
      this.g = var0.readFloat();
      this.h = var0.readFloat();
      this.i = var0.readLong();
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.a(BuiltInRegistries.c.t(), this.b, (var0x, var1x) -> var1x.a(var0x));
      var0.a(this.c);
      var0.writeInt(this.d);
      var0.writeInt(this.e);
      var0.writeInt(this.f);
      var0.writeFloat(this.g);
      var0.writeFloat(this.h);
      var0.writeLong(this.i);
   }

   public Holder<SoundEffect> a() {
      return this.b;
   }

   public SoundCategory c() {
      return this.c;
   }

   public double d() {
      return (double)((float)this.d / 8.0F);
   }

   public double e() {
      return (double)((float)this.e / 8.0F);
   }

   public double f() {
      return (double)((float)this.f / 8.0F);
   }

   public float g() {
      return this.g;
   }

   public float h() {
      return this.h;
   }

   public long i() {
      return this.i;
   }

   public void a(PacketListenerPlayOut var0) {
      var0.a(this);
   }
}
